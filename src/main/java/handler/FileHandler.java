package handler;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;
import java.nio.file.Files;
import java.io.File;

public class FileHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;

        try {
            // Verify get request
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                // build the path from the uri string
                String path = exchange.getRequestURI().toString();
                if(path.equals(null) || path.equals("/")){
                    path = "web/index.html";
                }
                else{
                    path = "web" + path;
                }

                // build file from the path
                File target = new File(path);
                if(target.exists() && target.isFile()) {
                    // Return success if file is made
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    // Return file
                    Files.copy(target.toPath(), exchange.getResponseBody());
                }
                else{
                    // Return file not found
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                    Files.copy(new File("web/HTML/404.html").toPath(), exchange.getResponseBody());
                }
                success = true;
                exchange.getResponseBody().close();
            }

            if (!success) {
                // Bad request
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            // Failure on server side
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
