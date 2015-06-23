package servlet;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class PostServlet implements Servlet {
	
	private static ArrayList<Post> posts;
	
	@Override
	public void init(ServletConfig arg0) throws ServletException {
		if(posts == null)
			posts = new ArrayList<Post>();
	}

	public void service(HttpRequest req, HttpResponse resp)
			throws ServletException, IOException {

		
		Map<String, String[]> params = req.getParameterMap();
		System.out.println(params.toString());
		if(params.containsKey("author") && params.containsKey("post"))
			addPost(params.get("author")[0], params.get("post")[0]);
		
		
		resp.setProtocol(req.getProtocol());
		resp.setStatus("200 OK");
		resp.generateStatusLine();
		resp.generateHeaders();
		
		resp.append("<!DOCTYPE html>\n");
		resp.append("<html>\n");
		resp.append("<head>\n\t<title>Forum</title>\n</head>\n");
		resp.append("<body>\n\t");
		resp.append("<h1>Forum about nothing</h1>\n\t");
		for(Post p : posts){
			//System.err.println(posts.toString());
			//System.err.println("ps: " + p.author + ": " + p.post);
			resp.append("<div>\n\t\t");
			resp.append("<div>Author: " + p.author + "</div>\n\t\t");
			resp.append("<div>\n\t\t\t" + p.post + "\n\t\t</div>\n\t");
			resp.append("</div>\n\t");
		}
		
		resp.append("<form method='POST'>\n\t\t");
		resp.append("<input type='text' name='author' placeholder='author'/>\n\t\t");
		resp.append("<br><textarea name='post' cols='26' rows='5'/></textarea>\n\t\t");
		resp.append("<br><input type='submit' value='Add!'/>\n\t");
		resp.append("</form>\n");
		
		resp.append("</body>\n");
		resp.append("</html>");
		
		resp.send();
	}

	private void addPost(String author, String post) {
		posts.add(new Post(author, post));
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
