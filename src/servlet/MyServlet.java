package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private static ObjRepository repo = new ObjRepository();
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		out.write("Hey! whoever your are. Nice to meet you.");
		
		String pathInfo = req.getPathInfo();
		if(pathInfo == null || pathInfo.equals("/")){ // get all
			List<Object> result = repo.fetchAll();
			for (Object obj : result) {
				out.write(obj.toString());
			}
			out.flush();
			return;
		}
		String[] parts= pathInfo.split("/");
		
		if(parts.length > 1){ // path doesn't follow standard "/MyServlet/<id>"
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		String key = parts[0]; // this will be the id of the object to be retrieved
		out.write(repo.fetch(key).toString());
		out.flush();
		return;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		out.write("Hey! whoever your are. Nice to meet you.");
		
		String pathInfo = req.getPathInfo();

		if(pathInfo == null || pathInfo.equals("/")){
			String queryString = req.getQueryString();
			String[] params = queryString.split("&");
			
			if(params.length != 2){
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			} else{
				repo.save(params[0], params[1]);
			}
			
			/*StringBuilder buffer = new StringBuilder();
		    BufferedReader reader = req.getReader();
		    String line;
		    while ((line = reader.readLine()) != null) {
		        buffer.append(line);
		    }
		    String payload = buffer.toString();*/
		    
			out.write("Hey! whatever you were trying to save is saved.");
		}else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
	}

	
}
