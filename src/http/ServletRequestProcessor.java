package http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

public class ServletRequestProcessor implements RequestProcessor {

	public static String servletUrls = "WebContent/WEB-INF/servletUrls.txt";

	@Override
	public void process(HttpRequest req, HttpResponse resp) {

		try {
			Servlet servlet = loadServletClass(getServletName(req.getUri()));

			if(servlet == null){
				sendNotFound(req, resp);
				return;
			}
			
			servlet.init(null);
			servlet.service(req, resp);
			servlet.destroy();
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | ServletException | IOException e) {
			e.printStackTrace();
			sendNotFound(req, resp);
		}
	}

	private String getServletName(String uri) {
		try {
			BufferedReader buffer = new BufferedReader(new FileReader(new File(
					servletUrls)));

			String line;
			while ((line = buffer.readLine()) != null) {
				String[] uriToServlet = line.split("->");
				if (uriToServlet[0].equals(uri)){
					return uriToServlet[1];
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private Servlet loadServletClass(String name)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		if (name == null)
			return null;

		Class<Servlet> servletClass = (Class<Servlet>) Class.forName(name);

		return servletClass.newInstance();
	}

	private void sendNotFound(HttpRequest req, HttpResponse resp) {

		resp.clear();
		resp.setProtocol(req.getProtocol());
		resp.setStatus("404 Not Found");
		resp.generateStatusLine();
		resp.generateHeaders();
		resp.append("<html>");
		resp.append("<head>\n\r\t<title>404</title>\n\r</head>");
		resp.append("<body>\n\r\t<h1>404 nothing found at this url :(</h1>\n\r</body>");
		resp.append("</html>");
		resp.send();
	}

}
