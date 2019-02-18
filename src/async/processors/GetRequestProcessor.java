package async.processors;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import mongo.DBConnect;
import mongo.DBUtils;

public class GetRequestProcessor extends AsyncRequestProcessor {

	public GetRequestProcessor() {
		super();
	}
	
	public GetRequestProcessor(AsyncContext context){
		super(context);
	}
	
	@Override
	public void run() {
		HttpServletRequest req = getRequest();	
		HttpServletResponse resp = getResponse();	
		try {
			PrintWriter out = resp.getWriter();
			String pathInfo = req.getPathInfo();
			if(pathInfo == null || pathInfo.equals("/database")){ // get all objects
				System.out.println("Fetching all objects.");
				String result = fetchAll();
				out.write(result);
				out.flush();
			} else {
				String[] parts= pathInfo.split("/");
				if(parts.length <= 1){ 
					// path doesn't follow defined "/<Servlet>/<id>" format
					resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
				}
				String key = parts[2]; // parts should be [, database, <id>]
				System.out.println("Fetching object with key : "+  key);
				out.write(fetch(key).toString());
				out.flush();
			}
		} catch(IOException e){
			e.printStackTrace();
		}
		getContext().complete();
	}

	private String fetch(String key){
		DBUtils dbUtils = new DBConnect();
		String result = dbUtils.getPerson(key);
		return result== null ? "NOTHING FOUND." : new JSONObject(result).toString(2);
	}
	
	private String fetchAll(){
		DBUtils dbUtils = new DBConnect();
		StringBuilder buf = new StringBuilder().append("["); // starting [ as we are converting to array
		String persons = dbUtils.getAllPersons();  
		buf.append( persons.substring(0, persons.length()-1)).append("]"); // remove last , and then attach closing ]
		String result = buf.toString();
		JSONArray arr = new JSONArray(result);
		return result== null ? "NOTHING FOUND." : arr.toString(2);
	}
}
