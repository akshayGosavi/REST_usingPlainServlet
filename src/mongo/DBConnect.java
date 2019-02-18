package mongo;

import static com.mongodb.client.model.Filters.eq;

import java.util.concurrent.CountDownLatch;
import org.bson.Document;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;

import async.mongo.documents.Person;

@SuppressWarnings("deprecation")
public class DBConnect implements DBUtils{
	
	private static MongoClient client;
	private static MongoDatabase db;
	
	public void initConnection(){
		System.out.println("Initializing database connections");
		client = MongoClients.create(); // default server localhost and port 27017
		db = client.getDatabase("appivo");		
	}

	@Override
	public String getPerson(String key) {
		if(db == null){
			initConnection();
		}
		MongoCollection<Document> personsCollection = db.getCollection("persons");
		CountDownLatch waiter = new CountDownLatch(1);
		StringBuffer buffer = new StringBuffer();

		// ssn will act as unique_id
        personsCollection.find(eq("ssn", Long.valueOf(key))).forEach((final Document document) -> buffer.append(document.toJson()) , 
        			(final Void result, final Throwable t) -> waiter.countDown());
        
        try{
        	waiter.await();
        } catch(InterruptedException e){
        	e.printStackTrace();
        }
		return buffer.toString();
	}

	@Override
	public String getAllPersons() {
		if(db == null){
			initConnection();
		}
		MongoCollection<Document> personsCollection = db.getCollection("persons");
		CountDownLatch waiter = new CountDownLatch(1);
		StringBuffer buffer = new StringBuffer();

        personsCollection.find().forEach((final Document document) -> buffer.append(document.toJson()).append(",") , 
        			(final Void result, final Throwable t) -> waiter.countDown());
        
        try{
        	waiter.await();
        } catch(InterruptedException e){
        	e.printStackTrace();
        }
		return buffer.toString();
	}

	@Override
	public void addPerson(Person person) {
		if(db == null)
			initConnection();
		
		MongoCollection<Document> personsCollection = db.getCollection("persons");
		personsCollection.insertOne(person.getPersonDoc(), (final Void result, final Throwable t) -> System.out.println("Inserted!"));
	}
}
