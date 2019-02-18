package mongo;

import async.mongo.documents.Person;

public interface DBUtils {
	
	public String getPerson(String key);
	
	public String getAllPersons();
	
	public void addPerson(Person person);
}
