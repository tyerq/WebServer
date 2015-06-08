package http;

public class ServletRequestProcessor implements RequestProcessor {

	public static String servletUrls = "servletUrls.txt";
	
	@Override
	public void process(HttpRequest req, HttpResponse res) {
		
	}
	
	private void getServletName() {
		// TODO Auto-generated method stub

	}
	
	private void loadServletClass(String name) {
		// TODO Auto-generated method stub

	}
	
	private void sendNotFound(HttpResponse resp) {

		resp.clear();
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
