package handler;

import java.io.*;
import java.net.*;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import request_result.EventAll_Responce;
import request_result.Event_Request;
import request_result.Event_Responce;
import service.Event_Service;


public class EventHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;
        Read_Write_JSON readWriteJson = new Read_Write_JSON();

        try {
            // Verify get request
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                Gson gson = new Gson();

                // Get the HTTP request headers
                Headers reqHeaders = exchange.getRequestHeaders();

                // Check to see if an "Authorization" header is present
                if (reqHeaders.containsKey("Authorization")) {

                    // Extract the auth token from the "Authorization" header
                    String authToken = reqHeaders.getFirst("Authorization");

                    // Get url strings and split into an array
                    String uri = exchange.getRequestURI().toString();
                    String[] eventInfo = uri.split("/");

                    // Validate the array lengths to decide which git request to run
                    if (eventInfo.length > 1 && eventInfo.length < 4) {
                        Event_Request req = null;
                        Event_Service service = new Event_Service();
                        Event_Responce result = null;
                        EventAll_Responce resultMulti = null;

                        // Get specific event else get all events
                        if (eventInfo.length == 3) {
                            req = new Event_Request(eventInfo[2], authToken);
                            result = service.getEventObject(req);
                        }
                        else {
                            resultMulti = service.getFamilyEvents(authToken);
                        }

                        // Check if get was sucessful send proper responce
                        if(eventInfo.length == 3 && result.isSucces() || eventInfo.length == 2 && resultMulti.isSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        }
                        else{
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }

                        // build the response body
                        OutputStream resBody = (OutputStream) exchange.getResponseBody();
                        String result_final = null;

                        // convert to json
                        if (result != null) {
                            result_final = gson.toJson(result);

                        }
                        else {
                            result_final = gson.toJson(resultMulti);
                        }
                        readWriteJson.writeString(result_final, resBody);
                        resBody.close();
                    }
                    success = true;
                }
            }

            if (!success) {
                // Send bad request statement
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            // Respond with server side error
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
