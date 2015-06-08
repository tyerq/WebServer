package http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class HttpServer {

	protected static String SERVER_IP = "localhost";
	protected static int SERVER_PORT = 8080;

	protected ServerSocket server;

	public void start() {
		System.out.println("Starting server on " + SERVER_IP + ":"
				+ SERVER_PORT + " ...");

		try {
			server = new ServerSocket(SERVER_PORT, 0,
					InetAddress.getByName(SERVER_IP));
		} catch (UnknownHostException e) {
			System.err.println("Bad host :(");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Something went wrong :(");
			e.printStackTrace();
			System.exit(1);
		}

		System.out.println("Server started.\n");
		run();
	}

	protected void run() {
		System.out.println("Server running ...");

		while (true) {
			try {
				System.out.println("Waiting for a connection ...");
				Socket socket = server.accept();
				
				System.out.println("\nGot a connection with " + socket.getInetAddress() + '\n');

				HttpRequest req = new HttpRequest(socket.getInputStream());
				HttpResponse resp = new HttpResponse(socket.getOutputStream());

				RequestProcessor processor;

				if (req.getUri().startsWith("/servlet/")) {
					processor = new ServletRequestProcessor();
				} else {
					processor = new StaticResourseRequestProcessor();
				}

				processor.process(req, resp);

				socket.close();
			} catch (IOException e) {
				System.err.println("Something went wrong :(");
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	public static void main(String[] args) {

		HttpServer srv = new HttpServer();
		srv.start();
	}
}
