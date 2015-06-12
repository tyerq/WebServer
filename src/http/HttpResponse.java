package http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

public class HttpResponse implements ServletResponse {

	private OutputStream output;
	private BufferedWriter buffer;
	private StringBuilder raw;
	private String status;
	private String protocol;

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getProtocol() {
		return protocol;
	}

	public void generateStatusLine() {
		raw.append(protocol + " " + status + "\r\n");
	}

	public void generateHeaders() {
		raw.append("\r\n");
	}

	public void append(String str) {
		raw.append(str);
	}

	public void clear() {
		raw.delete(0, raw.length());
	}

	public void send() {
		try {
			buffer.write(raw.toString());
			buffer.flush();
			// System.out.println('\n' + raw.toString() + '\n');
			buffer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendStaticResource(String path) throws IOException, FileNotFoundException {

		BufferedReader res = new BufferedReader(new FileReader(new File(path)));
		String line = null;
		while ((line = res.readLine()) != null)
			raw.append(line + '\n');

		res.close();
		send();
	}

	public HttpResponse(OutputStream output) {
		// System.err.println("---inside response----");

		this.output = output;

		buffer = new BufferedWriter(new OutputStreamWriter(this.output));
		raw = new StringBuilder();

	}

	@Override
	public void flushBuffer() throws IOException {
		buffer.flush();

	}

	@Override
	public int getBufferSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCharacterEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCommitted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetBuffer() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBufferSize(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCharacterEncoding(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setContentLength(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setContentLengthLong(long arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setContentType(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLocale(Locale arg0) {
		// TODO Auto-generated method stub

	}

}
