package programTV;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Eksport {
	ArrayList<Data> listadata=new ArrayList<Data>();
	
	public Eksport(){}
	public Eksport(ArrayList<Data> listadata) {
		this.listadata=listadata;
	}
	public ArrayList<Data> getListadata() {
		return listadata;
	}
	@XmlElement
	public void setListadata(ArrayList<Data> listadata) {
		this.listadata=listadata;
	}
}
