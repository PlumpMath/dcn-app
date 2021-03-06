package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Map;
import java.util.LinkedHashMap;
import play.db.ebean.Model;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.UniqueConstraint;
import com.avaje.ebean.validation.NotNull;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
public class Node extends Model {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	public Long id;

	@NotNull
	public String name;

	@ManyToOne
	@NotNull
	public Area area;

	public static Finder<Long, Node> find = new Finder(Long.class, Node.class);
	
	public static Node get(Long id){
		return find.byId(id);
	}
	
	public static List<Node> all() {
		return find.all();
	}		

	public static void delete(Long id) {
		find.byId(id).delete();
	}

	public static Map<String,String> asMap(){
		List<Node> nodes=find.select("id,name").orderBy("name").findList();
		return asMap(nodes);
	}

	private static Map<String,String> asMap(List<Node> nodes){
		Map<String,String> nodeMap=new LinkedHashMap<String,String>();
		for(Node node: nodes){
			nodeMap.put(node.id.toString(),node.name);
		}
		return nodeMap;
	}

	public static Map<String,String> asMapByAreaId(String areaid){
		List<Node> nodes=find.select("id,name").where().eq("area_id",areaid).orderBy("name").findList();
		return asMap(nodes);
	}

	public static boolean isNameExists(Long id,String name){
		if(id==0){
			return find.select("id").where().eq("name",name).findList().size()==0?false:true;			
		}
		return find.select("id").where().ne("id",id).eq("name",name).findList().size()==0?false:true;		
	}
	
}
