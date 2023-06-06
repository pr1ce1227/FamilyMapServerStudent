package handler;
import java.io.*;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import java.net.HttpURLConnection;
import java.io.OutputStream;
import request_result.Fill_Responce;
import service.Fill_Service;

public class FillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;
        Read_Write_JSON readWriteJson = new Read_Write_JSON();

        try {
            // Verify post request
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                Gson gson = new Gson();

                // Build Uri string
                String uri = exchange.getRequestURI().toString();
                String[] inputs = uri.split("/");

                // Verify it's the correct length
                if(inputs.length < 5 && inputs.length > 2) {
                    String user = inputs[2];
                    int generations = 4;
                    if(inputs.length > 3){
                        generations = Integer.parseInt(inputs[3]);
                    }

                    // Build and run service
                    Fill_Service service = new Fill_Service();
                    Fill_Responce result = service.fill(user, generations);

                    // Check response for succces and send proper reply
                    if(result.isSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }
                    else{
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }

                    // Buildt response body and send
                    OutputStream resBody = (OutputStream) exchange.getResponseBody();
                    String result_final = gson.toJson(result);
                    readWriteJson.writeString(result_final, resBody);
                    resBody.close();
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
            // If failed blame server
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
