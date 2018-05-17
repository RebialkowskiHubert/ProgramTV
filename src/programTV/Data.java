package programTV;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="programTV")
public class Data {	
	String dzien;
	ArrayList<Stacja> listastacja;
	
	public Data(String dzien, ArrayList<Stacja> listastacja) {
		super();
		this.dzien = dzien;
		this.listastacja = listastacja;
	}

	public Data(){}

	public String getDzien() {
		return dzien;
	}
	
	@XmlElement(name="data")
	public void setDzien(String dzien) {
		this.dzien = dzien;
	}
	
	public ArrayList<Stacja> getListakan() {
		return listastacja;
	}
	
	@XmlElement(name="kanal")
	public void setListakan(ArrayList<Stacja> listastacja) {
		this.listastacja=listastacja;
	}
}
