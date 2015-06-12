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

import servlet.PostServlet.Post;

public class NotesServlet implements Servlet {

	class Note {
		String title;
		String created;
		String edited;
		String note;

		public Note(String title, String note) {
			this.title = title;
			this.created = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy")
					.format(new Date());
			this.edited = "";
			this.note = note;
		}
	}

	private static ArrayList<Note> notes;

	@Override
	public void init(ServletConfig arg0) throws ServletException {
		if (notes == null)
			notes = new ArrayList<Note>();
	}

	public void service(HttpRequest req, HttpResponse resp)
			throws ServletException, IOException {

		if (req.getUri().endsWith("notes"))
			showAll(req, resp);
		else if (req.getUri().endsWith("read"))
			readNote(req, resp);
		else if (req.getUri().endsWith("edit"))
			editNote(req, resp);
		else
			sendNotFound(req, resp);

	}

	private void showAll(HttpRequest req, HttpResponse resp) {

		Map<String, String[]> params = req.getParameterMap();
		if (params.containsKey("title") && params.containsKey("note"))
			addNote(params.get("title")[0], params.get("note")[0]);

		resp.setProtocol(req.getProtocol());
		resp.setStatus("200 OK");
		resp.generateStatusLine();
		resp.generateHeaders();

		resp.append("<!DOCTYPE html>\n");
		resp.append("<html>\n");
		resp.append("<head>\n\t<title>Notes</title>\n</head>\n");
		resp.append("<body>\n\t");
		resp.append("<h1>All notes</h2>\n\t");
		for (int i = 0; i < notes.size(); i++) {
			resp.append("<div>\n\t\t");
			resp.append("<a href='/notes/read?num=" + i + "'>read</a>\t|\t"
					+ notes.get(i).created + "\t|\t" + notes.get(i).title
					+ "</div>\n\t\t");
			resp.append("</div>\n\t");
		}

		resp.append("<form method='POST'>\n\t\t");
		resp.append("<input type='text' name='title' placeholder='title'/>\n\t\t");
		resp.append("<br><textarea name='note' cols='26' rows='5'/></textarea>\n\t\t");
		resp.append("<br><input type='submit' value='Add!'/>\n\t");
		resp.append("</form>\n");

		resp.append("</body>\n");
		resp.append("</html>");

		resp.send();

	}

	private void readNote(HttpRequest req, HttpResponse resp) {

		resp.setProtocol(req.getProtocol());
		resp.setStatus("200 OK");
		resp.generateStatusLine();
		resp.generateHeaders();

		int num = -1;
		Note note = null;
		try {
			num = Integer.parseInt(req.getParameterMap().get("num")[0]);
			note = notes.get(num);
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			resp.append("No such note!");
			resp.send();
			return;
		}

		resp.append("<!DOCTYPE html>\n");
		resp.append("<html>\n");
		resp.append("<head>\n\t<title>Notes</title>\n</head>\n");
		resp.append("<body>\n\t");
		resp.append("<a href='/notes'>back</a>\n\t");
		resp.append("<h2>" + note.title + "</h2>\n\t");
		resp.append("<h6>" + note.created + "</h6>\n\t");
		if (!note.edited.equals(""))
			resp.append("<h6>edited:\t" + note.edited + "</h6>\n\t");
		resp.append("<form method='POST' action='/notes/edit'>\n\t\t");
		resp.append("<textarea name='note' cols='46' rows='10'/>" + note.note
				+ "</textarea>\n\t\t");
		resp.append("<br><input type='hidden' name='num' value='" + num + "'/>\n\t");
		resp.append("<br><input type='submit' value='edit'/>\n\t");
		resp.append("</form>\n");

		resp.send();
	}

	private void editNote(HttpRequest req, HttpResponse resp) {

		int num = -1;
		Note note = null;
		try {
			Map<String, String[]> params = req.getParameterMap();
			num = Integer.parseInt(params.get("num")[0]);
			note = notes.get(num);
			note.edited = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy")
			.format(new Date());
			note.note = params.get("note")[0];
		} catch (NullPointerException | NumberFormatException | IndexOutOfBoundsException e) {
			resp.append("No such note!");
			resp.send();
			return;
		}

		readNote(req, resp);

	}

	private void addNote(String title, String note) {
		notes.add(new Note(title, note));
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
