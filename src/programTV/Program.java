package programTV;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Program {
	String start, nazwa, kopis, dopis;
	
	public Program(){}
	public Program(String start, String nazwa, String kopis, String dopis) {
		this.start=start;
		this.nazwa=nazwa;
		this.kopis=kopis;
		this.dopis=dopis;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	public String getKopis() {
		return kopis;
	}
	public void setKopis(String kopis) {
		this.kopis = kopis;
	}
	public String getDopis() {
		return dopis;
	}
	public void setDopis(String dopis) {
		this.dopis = dopis;
	}
}
