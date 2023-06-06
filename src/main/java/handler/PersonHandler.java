package handler;

import java.io.*;
import java.net.*;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import request_result.PersonFamily_Responce;
import request_result.Person_Request;
import request_result.Person_Responce;
import service.Person_Service;

public class PersonHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Read_Write_JSON rwj = new Read_Write_JSON();
        boolean success = false;

        try {
            // Verify get requesnt
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                Gson gson = new Gson();
                // Get the HTTP request headers
                Headers reqHeaders = exchange.getRequestHeaders();

                // Check to see if an "Authorization" header is present
                if (reqHeaders.containsKey("Authorization")) {

                    // Extract the auth token from the "Authorization" header
                    String authToken = reqHeaders.getFirst("Authorization");

                    // Build uri String and split
                    String uri = exchange.getRequestURI().toString();
                    String[] personInfo = uri.split("/");

                    // Verify proper size
                    if (personInfo.length > 1 && personInfo.length < 4) {
                        // build person service and execute
                        Person_Service service = new Person_Service();
                        Person_Responce result = null;
                        PersonFamily_Responce resultMulti = null;

                        // Call correct service base on uri length
                        if (personInfo.length == 3) {
                            Person_Request req = new Person_Request(personInfo[2], authToken);
                            result = service.getPersonObject(req);
                        }
                        else {
                            resultMulti = service.getPersonFamily(authToken);
                        }

                        // Check responce for success based on length then send
                        if(personInfo.length == 3 && result.getSuccess() || personInfo.length == 2 && resultMulti.getSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        }
                        else{
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }

                        // build output stream and convert to json based on result type
                        OutputStream resBody = (OutputStream) exchange.getResponseBody();
                        String result_final = null;
                        if (result != null) {
                            result_final = gson.toJson(result);

                        }
                        else {
                            result_final = gson.toJson(resultMulti);
                        }
                        rwj.writeString(result_final, resBody);
                        resBody.close();
                    }
                    success = true;
                }
            }

            if (!success) {
                // If failed blame the request
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            // If failed blame the server
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}

