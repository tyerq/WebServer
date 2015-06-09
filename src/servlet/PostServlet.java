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

	class Post{
		String author;
		String post;
		
		public Post(String author, String post) {
			this.author = author;
			this.post = post;
		}
	}
	
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
		
		resp.append("<html>");
		resp.append("<head>\n\r\t<title>Forum</title>\n\r</head>");
		resp.append("<body>\n\r\t");
		resp.append("<h1>Forum about nothing</h1>");
		for(Post p : posts){
			//System.err.println(posts.toString());
			System.err.println("ps: " + p.author + ": " + p.post);
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
