package handler;

import java.io.*;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import request_result.Login_Request;
import request_result.Login_Responce;
import java.net.HttpURLConnection;
import java.io.OutputStream;
import service.Login_Service;

public class LoginHandler implements HttpHandler {

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


                Login_Request request = (Login_Request) gson.fromJson(reqData, Login_Request.class);

                // Build login service and run
                Login_Service service = new Login_Service();
                Login_Responce result = service.login(request);

                // check responce for success and respond accordingly
                if (result.getSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

                // Build the response body and send
                OutputStream resBody = (OutputStream) exchange.getResponseBody();
                String result_final = gson.toJson(result);
                rwj.writeString(result_final, resBody);
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
            // Blame the server for exceptions
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
