package servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjRepository {

	private static Map<String,Object> repo;
	
	static {
		repo = new HashMap<>();
	}
	
	public void save(String key, Object obj){
		repo.put(key, obj);
	}
	
	public Object fetch(String key){
		if(!repo.isEmpty()){
			if(repo.containsKey(key)){
				return repo.get(key);
			}
		}
		return null;
	}
	
	public List<Object> fetchAll(){
		List<Object> ret = new ArrayList<>();
		ret.addAll(repo.values());
		return ret;
	}
}
