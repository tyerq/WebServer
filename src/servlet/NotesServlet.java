package servlet;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class NotesServlet implements Servlet {

	class Note{
		String title;
		String datetime;
		String post;
		
		public Note(String title, String post) {
			this.title = title;
			this.datetime = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy").format(new Date());
			this.post = post;
		}
	}
	
	private static ArrayList<Note> notes;
	
	@Override
	public void init(ServletConfig arg0) throws ServletException {
		if(notes == null)
			notes = new ArrayList<Note>();
	}

	public void service(HttpRequest req, HttpResponse resp)
			throws ServletException, IOException {

		if(req.getUri().endsWith("notes"))
			showAll(req, resp);
		else if(req.getUri().endsWith("show"))
			showNote(req, resp);
		else if(req.getUri().endsWith("show"))
			editNote(req, resp);
			
		
	}

	private void showAll(HttpRequest req, HttpResponse resp) {

		Map<String, String[]> params = req.getParameterMap();
		System.out.println(params.toString());
		if(params.containsKey("author") && params.containsKey("post"))
			addNote(params.get("author")[0], params.get("post")[0]);
		
		
		resp.setProtocol(req.getProtocol());
		resp.setStatus("200 OK");
		resp.generateStatusLine();
		resp.generateHeaders();
		
		resp.append("<html>");
		resp.append("<head>\n\r\t<title>Notes</title>\n\r</head>");
		resp.append("<body>\n\r\t");
		resp.append("<h1>Forum about nothing</h1>");
		for(int i=0 ;i<notes.size(); i++){
			
			resp.append("<div>\n\r\t");
			resp.append("<div>Author: " + p.author + ".</div>\n\r\t");
			resp.append("<div>" + p.post + ".</div>\n\r");
			resp.append("</div><br><br>");
		}
		
		
		resp.append("<form method='POST'>\n\r\t");
		resp.append("<input type='text' name='author'/>\n\r\t");
		resp.append("<input type='text' name='post' rows=5/>\n\r");
		resp.append("<input type='submit'/>\n\r");
		resp.append("</form><br><br>");
		
		resp.append("\n\r</body>");
		resp.append("</html>");
		
		resp.send();

	}
	
	private void showNote() {
		// TODO Auto-generated method stub

	}
	
	private void editNote() {
		// TODO Auto-generated method stub

	}
	
	private void addNote(String post) {
		
	}
	
	@Override
	public void service(ServletRequest req, ServletResponse resp)
			throws ServletException, IOException {
		
		service((HttpRequest) req, (HttpResponse) resp);
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
