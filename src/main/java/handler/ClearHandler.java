package handler;

import java.io.*;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import request_result.Clear_Responce;
import java.net.HttpURLConnection;
import java.io.OutputStream;
import service.Clear_Service;

public class ClearHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;
        Read_Write_JSON readWriteJson = new Read_Write_JSON();
        try {

            // Validate post request
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                Gson gson = new Gson();
                Clear_Service service = new Clear_Service();
                Clear_Responce result = service.clear();

                // check result response for success, send appropriate response
                if(result.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else{
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

                // build the responce body
                OutputStream resBody = exchange.getResponseBody();
                String result_final = gson.toJson(result);
                readWriteJson.writeString(result_final, resBody);
                resBody.close();
                success = true;
            }

            if (!success) {
                // Send bad request
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            // Throw internal server errror responce
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}