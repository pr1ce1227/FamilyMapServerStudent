package handler;

import java.io.*;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import java.net.HttpURLConnection;
import java.io.OutputStream;
import request_result.Load_Request;
import request_result.Load_Responce;
import service.Load_Service;


public class LoadHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;
        Read_Write_JSON readWriteJson = new Read_Write_JSON();

        try {
            // Verify post request
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                Gson gson = new Gson();

                // Get the request body input stream
                InputStream reqBody = exchange.getRequestBody();

                // Read JSON string from the input stream
                String reqData = readWriteJson.readString(reqBody);

                // Build and run load service
                Load_Request request = (Load_Request)gson.fromJson(reqData, Load_Request.class);
                Load_Service service = new Load_Service();
                Load_Responce result = service.load(request);

                // Check response for success and send proper response
                if(result.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else{
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

                // Build response body and send
                OutputStream resBody = (OutputStream)exchange.getResponseBody();
                String result_final = gson.toJson(result);
                readWriteJson.writeString(result_final, resBody);
                resBody.close();
                success = true;
            }

            if (!success) {
                // If failed then blame the request
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            // If failed then blame the server
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
