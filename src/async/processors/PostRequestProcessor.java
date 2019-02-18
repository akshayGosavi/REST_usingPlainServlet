package async.processors;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;
import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import async.mongo.documents.Person;
import mongo.DBConnect;
import mongo.DBUtils;

public class PostRequestProcessor extends AsyncRequestProcessor {

	public PostRequestProcessor() {
		super();
	}
	
	public PostRequestProcessor(AsyncContext context){
		super(context);
	}
	
	@Override
	public void run() {
		HttpServletRequest req = getRequest();	
		HttpServletResponse resp = getResponse();
		try {
			PrintWriter out = resp.getWriter();
			String pathInfo = req.getPathInfo();
			if(pathInfo == null || pathInfo.equals("/database")){
				// get the JSON sent with POST
			    String payload = req.getReader().lines().collect(Collectors.joining());
				if(payload.length() == 0){ // nothing to post
					resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
				} else{
					try{
					save(createPerson(payload));
					}catch(Exception e){
						System.out.println("Error while saving new object " + e.getMessage());
						System.out.println(payload.trim());
						resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
					}
				}
				out.write("Object Saved.");
			}else {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}
		} catch (IOException e){
			e.printStackTrace();
		}
		getContext().complete();
	}
	
	private void save(Person person) throws Exception{
		if(person.validate()){ // add only if its a valid object
			DBUtils dbUtils = new DBConnect();
			dbUtils.addPerson(person);
		} else {
			throw new RuntimeErrorException(new Error("Not Valid Data."));
		}
	}
	
	private Person createPerson(String input){
		JSONObject json = new JSONObject(input);
		Person person = new Person();
		// set all values 
		person.initFromJSON(json);
		return person;
	}
	
}
