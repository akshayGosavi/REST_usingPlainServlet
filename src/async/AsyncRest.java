package async;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import async.processors.GetRequestProcessor;
import async.processors.PostRequestProcessor;

@WebServlet(urlPatterns={"/database","/database/*"}, asyncSupported= true)
public class AsyncRest extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AsyncContext asyncContext = req.startAsync();
		asyncContext.addListener(new MyAsyncListener());
		asyncContext.setTimeout(60000);
		ThreadPoolExecutor executor = (ThreadPoolExecutor) req.getServletContext().getAttribute("executor");
		executor.execute(new GetRequestProcessor(asyncContext));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AsyncContext asyncContext = req.startAsync();
		asyncContext.addListener(new MyAsyncListener());
		asyncContext.setTimeout(60000);
		ThreadPoolExecutor executor = (ThreadPoolExecutor) req.getServletContext().getAttribute("executor");
		executor.execute(new PostRequestProcessor(asyncContext));
	}
	
	

}
