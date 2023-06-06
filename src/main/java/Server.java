import java.io.*;
import java.net.*;
import handler.*;
import com.sun.net.httpserver.*;

public class Server {

	private static final int MAX_WAITING_CONNECTIONS = 12;

	private HttpServer server;

	public Server() throws FileNotFoundException {
	}

	private void run(String portNumber) {

		System.out.println("Initializing HTTP Server");
		
		try {
			server = HttpServer.create(
						new InetSocketAddress(Integer.parseInt(portNumber)),
						MAX_WAITING_CONNECTIONS);
		}
		catch (IOException e) {
			e.printStackTrace();
			return;
		}

		//////////////
		// Handlers //
		//////////////

		server.setExecutor(null);

		System.out.println("Creating contexts");

		server.createContext("/clear", new ClearHandler());

		server.createContext("/person", new PersonHandler());

		server.createContext("/user/register", new RegisterHandler());

		server.createContext("/user/login", new LoginHandler());

		server.createContext("/load", new LoadHandler());

		server.createContext("/fill", new FillHandler());

		server.createContext("/event", new EventHandler());

		server.createContext("/", new FileHandler());

		// Start the server
		System.out.println("Starting server");
		server.start();
		System.out.println("Server started");
	}

    // Main method to call when running server
	public static void main(String[] args) throws FileNotFoundException {
		String portNumber = args[0];
		new Server().run(portNumber);
	}
}

