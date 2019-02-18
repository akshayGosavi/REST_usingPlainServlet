package async;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) servletContextEvent.getServletContext().getAttribute("executor");
		executor.shutdown();
		System.out.println("Everyting cleaned up.Adios!");
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 50000L,TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));
		servletContextEvent.getServletContext().setAttribute("executor",executor);
		System.out.println("Everyting is setup.Let's rock and roll.");
	}

}
