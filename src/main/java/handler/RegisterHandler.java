package handler;

import java.io.*;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import request_result.Register_Request;
import java.net.HttpURLConnection;
import java.io.OutputStream;
import request_result.Register_Responce;
import service.Register_Service;

public class RegisterHandler implements HttpHandler {


    @Override
    public void handle(HttpExchange exchange) throws IOException {

        Read_Write_JSON rwj = new Read_Write_JSON();

        boolean success = false;

        try {
            // Verify post request
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                Gson gson = new Gson();

                // Get the request body input stream
                InputStream reqBody = exchange.getRequestBody();

                // Read JSON string from the input stream
                String reqData = rwj.readString(reqBody);


                Register_Request request = (Register_Request)gson.fromJson(reqData, Register_Request.class);

                // Build register service and execute
                Register_Service service = new Register_Service();
                Register_Responce result = service.register(request);

                // Check response for success and respond accordingly
                if(result.getSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else{
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

                // Build responce body and send
                OutputStream resBody = (OutputStream)exchange.getResponseBody();
                String result_final = gson.toJson(result);
                rwj.writeString(result_final, resBody);
                resBody.close();
                success = true;
            }

            if (!success) {
                // If failed blame request
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            // If failed blame server
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

}