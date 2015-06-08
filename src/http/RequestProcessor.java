package http;

public interface RequestProcessor {

	public void process(HttpRequest req, HttpResponse res);
}
