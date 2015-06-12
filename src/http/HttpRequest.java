package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class HttpRequest implements ServletRequest {

	private StringBuilder raw;

	private String method;
	private String uri;
	private String protocol;
	private Map<String, String[]> headers;
	private Map<String, String[]> parameters;

	public String getRaw() {
		return raw.toString();
	}

	public String getMethod() {
		return method;
	}

	public String getUri() {
		return uri;
	}

	public String getProtocol() {
		return protocol;
	}

	public Map<String, String[]> getHeaderMap() {
		return headers;
	}

	public String getHeader(String name) {
		return headers.get(name)[0];
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return parameters;
	}

	public String getParameter(String name) {
		return parameters.get(name)[0];
	}

	@Override
	public Enumeration<String> getParameterNames() {
		return (Enumeration<String>) parameters.keySet();
	}

	@Override
	public String[] getParameterValues(String arg0) {
		return parameters.values().toArray(new String[0]);
	}

	public HttpRequest(InputStream input) {
		// System.err.println("---inside request----");

		raw = new StringBuilder();

		BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
		try {
			String line = buffer.readLine();
			raw.append(line + "\n");

			parseStatusLine(line);

			headers = new HashMap<String, String[]>();
			parameters = new HashMap<String, String[]>();
			while ((line = buffer.readLine()) != null && !line.isEmpty()) {
				// System.err.println("---" + (++i) +
				// ") inside while(true)----");

				// System.err.println(line);
				raw.append(line + "\n");

				parseHeaders(line);
				// System.err.println("---" + i + ") next iteration----");
			}
			// System.err.println("---ended while----");

			if (method.equals("GET")) {
				int uriEnd = uri.indexOf('?');
				if (uriEnd > -1) {
					parseParameters(uri.substring(uri.indexOf('?') + 1));
					uri = uri.substring(0, uriEnd);
					System.out.println("reqUri: " + uri);
				}
			} else if (line != null)
				if (headers.get("Content-Length") != null) {
					char[] params = new char[Integer.valueOf(headers
							.get("Content-Length")[0])];
					buffer.read(params);
					parseParameters(new String(params));
				}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void parseStatusLine(String line) {
		if (line == null || line.isEmpty()) {
			return;
		}

		try {
			method = line.substring(0, line.indexOf("/") - 1);
			line = line.substring(line.indexOf("/"));
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Something wrong with your request header :(");
			e.printStackTrace();
		}

		uri = line.substring(0, line.indexOf(' ', line.indexOf(" ")));
		protocol = line.substring(line.indexOf(' ', line.indexOf(" ")));
	}

	private void parseHeaders(String line) {
		if (line == null || line.isEmpty()) {
			return;
		}
		String name = line.substring(0, line.indexOf(':'));
		String[] values = line.substring(name.length() + 2).split(",");
		headers.put(name, values);
	}

	private void parseParameters(String line) {
		if (line == null || line.isEmpty() || line.indexOf('=') == -1) {
			return;
		}
		String[] params = line.split("&");
		for (String param : params) {
			int dev = param.indexOf('=');
			// System.err.println("req: " + param.substring(0, dev)+ ": " +
			// param.substring(dev + 1));
			String name = param.substring(0, dev);
			String values = null;
			try {
				values = URLDecoder.decode(param.substring(dev + 1), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			parameters.put(name, values.split(","));
		}
	}

	@Override
	public AsyncContext getAsyncContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getAttribute(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCharacterEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getContentLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getContentLengthLong() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DispatcherType getDispatcherType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLocalAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLocalName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLocalPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<Locale> getLocales() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRealPath(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRemoteAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRemoteHost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRemotePort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getScheme() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServerName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getServerPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ServletContext getServletContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAsyncStarted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAsyncSupported() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSecure() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeAttribute(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAttribute(String arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCharacterEncoding(String arg0)
			throws UnsupportedEncodingException {
		// TODO Auto-generated method stub

	}

	@Override
	public AsyncContext startAsync() throws IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AsyncContext startAsync(ServletRequest arg0, ServletResponse arg1)
			throws IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}
}
