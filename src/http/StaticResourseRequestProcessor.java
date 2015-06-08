package http;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class StaticResourseRequestProcessor implements RequestProcessor {

	@Override
	public void process(HttpRequest req, HttpResponse resp) {
		
		resp.setProtocol(req.getProtocol());
		resp.setStatus("200 OK");
		resp.generateStatusLine();
		resp.generateHeaders();
		try {
			//System.err.println(new File(req.getUri().substring(1)).getAbsolutePath());
			resp.sendStaticResource(req.getUri().substring(1));
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			resp.clear();
			resp.setStatus("404 File Not Found");
			resp.generateStatusLine();
			resp.generateHeaders();
			resp.append("<html>");
			resp.append("<head>\n\r\t<title>404</title>\n\r</head>");
			resp.append("<body>\n\r\t<h1>404 file not found :(</h1>\n\r</body>");
			resp.append("</html>");
			resp.send();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
