package servlet;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class HelloServlet implements Servlet{

	@Override
	public void init(ServletConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	public void service(HttpRequest req, HttpResponse resp)
			throws ServletException, IOException {
		
		String greetee;
		if(req.getParameterMap().get("name")!=null)
			greetee = req.getParameterMap().get("name")[0];
		else
			greetee = "World";
		
		resp.setProtocol(req.getProtocol());
		resp.setStatus("200 OK");
		resp.generateStatusLine();
		resp.generateHeaders();
		resp.append("<h2>Hello " + greetee + "!</h2>");
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
