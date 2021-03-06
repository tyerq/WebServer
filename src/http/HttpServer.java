package http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
		System.out.println("Server running ...\n");

		ExecutorService pool = Executors.newFixedThreadPool(10);
		int i = 0;
		while (true) {
			try {
				System.out.println("Waiting for a connection ...");
				final Socket socket = server.accept();

				System.out.println("\n" + ++i + ". Got a connection with "
						+ socket.getInetAddress().toString());

				Runnable connection = new Runnable() {
					
					@Override
					public void run() {

						try {
							HttpRequest req = new HttpRequest(
									socket.getInputStream());
							HttpResponse resp = new HttpResponse(
									socket.getOutputStream());

							RequestProcessor processor;

							if (req != null && req.getUri().startsWith("/st/")) {
								processor = new StaticResourseRequestProcessor();
							} else {
								processor = new ServletRequestProcessor();
							}

							processor.process(req, resp);

							System.out
									.println("Succcesfully sent a responce." + '\n');

							socket.close();
						} catch (IOException e) {
							System.err.println("Something went wrong :(");
							e.printStackTrace();
						}
					}
				};
				
				//double time = System.currentTimeMillis();
				pool.submit(connection);
				//System.out.println("\ntime not listening: " + (System.currentTimeMillis() - time) + "msec.\n");

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
