package async.processors;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AsyncRequestProcessor implements Runnable {
	
	private AsyncContext asyncCtx;

	public AsyncRequestProcessor() {
	}
	
	public AsyncRequestProcessor(AsyncContext context){
		this.asyncCtx = context;
	}
	
	@Override
	public void run() {
		try{
			PrintWriter out = asyncCtx.getResponse().getWriter();
			out.write("This request is being handeled Asynchronously");
		}catch(IOException IOe){
			IOe.printStackTrace();
		}
		asyncCtx.complete();
	}
	
	public AsyncContext getContext(){
		return this.asyncCtx;
	}
	
	public HttpServletRequest getRequest(){
		return (HttpServletRequest)this.asyncCtx.getRequest();
	}
	
	public HttpServletResponse getResponse(){
		return (HttpServletResponse)this.asyncCtx.getResponse();
	}
}
