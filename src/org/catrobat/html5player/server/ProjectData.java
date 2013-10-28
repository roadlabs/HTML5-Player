package org.catrobat.html5player.server;
import java.io.Serializable;
import java.util.Map;


public class ProjectData implements Serializable {
	
   /**
	 * 
	 */
   private static final long serialVersionUID = -456054473035809588L;
   private  String xml = "";
   private  Map<String,String> images;
   private  Map<String,String> sounds;
	
   protected ProjectData(String xml, Map<String,String> images, Map<String,String> sounds) {
//	   images = new HashMap<String,String>();
//	   sounds = new HashMap<String,String>();
	   this.xml = xml;
	   this.images = images;
	   this.sounds = sounds;
   }
   
	public  String getXml() {
		return xml;
	}
//	public  void setXml(String xml) {
//		this.xml = xml;
//	}
	
//	public void addImage(String name, String entry){
//		images.put(name, entry);
//	}
	
	public String getImage(String name){
		return images.get(name);
	}
	
//	public void addSound(String name, String entry){
//		sounds.put(name, entry);
//	}
	
	public String getSound(String name){
		return sounds.get(name);
	}
	
	public String toTestString(){
	  return "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!images in ProjectData: "+this.images.size();
	}
   
}
