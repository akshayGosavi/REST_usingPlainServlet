package async;

import java.io.IOException;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyAsyncListener implements AsyncListener {

	@Override
	public void onComplete(AsyncEvent evt) throws IOException {
	}

	@Override
	public void onError(AsyncEvent evt) throws IOException {
		System.out.println("operation resulted in error :( .");
	}

	@Override
	public void onStartAsync(AsyncEvent evt) throws IOException {
	}

	@Override
	public void onTimeout(AsyncEvent evt) throws IOException {
		System.out.println("Times up..! process called timeout.");
		evt.getAsyncContext().complete();
	}
	

}
