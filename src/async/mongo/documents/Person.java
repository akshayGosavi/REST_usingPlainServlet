package async.mongo.documents;

import org.bson.Document;
import org.json.JSONObject;

public class Person{
	
	private int m_ssn;
	private Name m_name;
	
	public int getSsn() {
		return m_ssn;
	}
	public void setSsn(int ssn) {
		this.m_ssn = ssn;
	}
	public Name getName() {
		return m_name;
	}
	public void setName(Name name) {
		this.m_name = name;
	}
	
	@Override
	public String toString() {
		return getSsn() + " " + getName().toString(); 
	}	
	
	public Document getPersonDoc(){
		Document person = new Document()
				.append("ssn", getSsn())
				.append("name", getName().getNameDoc());
		return person;
	}
	
	public void initFromJSON(JSONObject person){
		if(null != person){
			if(person.has("ssn"))
				setSsn(person.getInt("ssn"));
			if(person.has("name")){
				JSONObject name = person.getJSONObject("name");
				if(null == getName())
					setName(new Name());
				getName().initFromJSON(name);
			}
		}
	}
	
	public boolean validate(){
		if(getSsn() > 0 && getName().validate())
			return true;
		
		return false;
	}
}
