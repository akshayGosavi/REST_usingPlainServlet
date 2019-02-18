package async.mongo.documents;

import org.bson.Document;
import org.json.JSONObject;

public class Name {

	private String m_firstName;
	private String m_middleName;
	private String m_lastName;
	
	public String getFirstName() {
		return m_firstName;
	}
	public void setFirstName(String firstName) {
		this.m_firstName = firstName;
	}
	public String getMiddleName() {
		return m_middleName;
	}
	public void setMiddleName(String middleName) {
		this.m_middleName = middleName;
	}
	public String getLastName() {
		return m_lastName;
	}
	public void setLastName(String lastName) {
		this.m_lastName = lastName;
	}
	
	@Override
	public String toString() {
		return getFirstName() + " " + getLastName();
	}
	
	public Document getNameDoc(){
		Document name = new Document()
				.append("firstName", getFirstName())
				.append("middleName", getMiddleName())
				.append("lastName", getLastName());
		return name;
	}
	
	public void initFromJSON(JSONObject name){
		if(null != name){
			if(name.has("firstName"))
				setFirstName(name.getString("firstName"));
			if(name.has("middleName"))
				setMiddleName(name.getString("middleName"));
			if(name.has("lastName"))
				setLastName(name.getString("lastName"));
		}
	}
	
	public boolean validate(){
		if(getFirstName().isEmpty() || getLastName().isEmpty())
			return false;
		
		return true;
	}
}
