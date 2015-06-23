package servlet;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import service.NotesService;
import service.NotesServiceImpl;
import domain.Note;

public class NotesServlet implements Servlet {

	private static NotesService notes;

	@Override
	public void init(ServletConfig arg0) throws ServletException {
		if (notes == null)
			notes = new NotesServiceImpl();
	}

	public void service(HttpRequest req, HttpResponse resp)
			throws ServletException, IOException {

		if (req.getUri().endsWith("notes"))
			showAll(req, resp);
		else if (req.getUri().endsWith("read"))
			readNote(req, resp);
		else if (req.getUri().endsWith("edit"))
			editNote(req, resp);
		else if (req.getUri().endsWith("delete"))
			deleteNote(req, resp);
		else
			sendNotFound(req, resp);

	}

	private void showAll(HttpRequest req, HttpResponse resp) {

		Map<String, String[]> params = req.getParameterMap();
		if (params.containsKey("title") && params.containsKey("text"))
			notes.addNote(params.get("title")[0], params.get("text")[0]);

		resp.setProtocol(req.getProtocol());
		resp.setStatus("200 OK");
		resp.generateStatusLine();
		resp.generateHeaders();

		resp.append("<!DOCTYPE html>\n");
		resp.append("<html>\n");
		resp.append("<head>\n\t<title>Notes</title>\n</head>\n");
		resp.append("<body>\n\t");
		resp.append("<h1>All notes</h2>\n\t");
		for (Note note : notes.getAllNotes()) {
			resp.append("<div>\n\t\t");
			resp.append("<a href='/notes/read?id=" + note.getId()
					+ "'>read</a>\t|\t" + note.getCreated() + "\t|\t"
					+ note.getTitle() + "</div>\n\t\t");
			resp.append("</div>\n\t");
		}

		resp.append("<form method='POST'>\n\t\t");
		resp.append("<input type='text' name='title' placeholder='title'/>\n\t\t");
		resp.append("<br><textarea name='text' cols='26' rows='5'/></textarea>\n\t\t");
		resp.append("<br><input type='submit' value='add'/>\n\t");
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

		String id = "";
		try {
			id = req.getParameterMap().get("id")[0];
		} catch (NumberFormatException e) {
			resp.append("No such note!");
			resp.send();
			return;
		}
		Note note = notes.getNote(id);
		if (note == null) {
			resp.append("No such note!");
			resp.send();
			return;
		}

		resp.append("<!DOCTYPE html>\n");
		resp.append("<html>\n");
		resp.append("<head>\n\t<title>Notes</title>\n</head>\n");
		resp.append("<body>\n\t");
		resp.append("<a href='/notes'>back</a>\n\t");
		resp.append("<h2>" + note.getTitle() + "</h2>\n\t");
		resp.append("<h6>" + note.getCreated() + "</h6>\n\t");
		if (note.getEdited() != null)
			resp.append("<h6>edited:\t" + note.getEdited() + "</h6>\n\t");
		resp.append("<form method='POST' action='/notes/edit'>\n\t\t");
		resp.append("<br><input type='text' name='title' value='"
				+ note.getTitle() + "'/>\n\t");
		resp.append("<br><textarea name='text' cols='46' rows='10'/>"
				+ note.getText() + "</textarea>\n\t\t");
		resp.append("<br><input type='hidden' name='id' value='" + note.getId()
				+ "'/>\n\t");
		resp.append("<br><input type='submit' value='edit'/>\n\t");
		resp.append("</form>\n");
		

		resp.append("<form method='POST' action='/notes/delete'>\n\t\t");
		resp.append("<br><input type='hidden' name='id' value='" + note.getId()
				+ "'/>\n\t");
		resp.append("<input type='submit' value='delete'/>\n\t");
		resp.append("</form>\n");

		resp.send();
	}

	private void editNote(HttpRequest req, HttpResponse resp) {

		String id = "";
		try {
			Map<String, String[]> params = req.getParameterMap();
			id = params.get("id")[0];
			Note note = new Note(id);
			note.setEdited(new SimpleDateFormat("HH:mm:ss dd/MM/yyyy")
					.format(new Date()));
			note.setTitle(params.get("title")[0]);
			note.setText(params.get("text")[0]);
			notes.editNote(note);
		} catch (NullPointerException | NumberFormatException e) {
			resp.append("No such note!");
			resp.send();
			return;
		}

		readNote(req, resp);

	}

	private void deleteNote(HttpRequest req, HttpResponse resp) {

		String id = "";
		try {
			Map<String, String[]> params = req.getParameterMap();
			id = params.get("id")[0];
			notes.deleteNote(id);
		} catch (NullPointerException | NumberFormatException e) {
			resp.append("No such note!");
			resp.send();
			return;
		}

		showAll(req, resp);

	}

	private void sendNotFound(HttpRequest req, HttpResponse resp) {

		resp.clear();
		resp.setProtocol(req.getProtocol());
		resp.setStatus("404 Not Found");
		resp.generateStatusLine();
		resp.generateHeaders();
		resp.append("<!DOCTYPE html>\n");
		resp.append("<html>\n");
		resp.append("<head>\n\t<title>404</title>\n</head>");
		resp.append("<body>\n\t<h1>404 nothing found at this url :(</h1>\n</body>\n");
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
