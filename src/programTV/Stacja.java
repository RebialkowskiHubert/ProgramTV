package programTV;

import java.util.ArrayList;

public class Stacja {
	String kanal;
	ArrayList<Program> listaprog=new ArrayList<Program>();
	
	public Stacja() {}
	public Stacja(String kanal, ArrayList<Program> listaprog) {
		super();
		this.kanal = kanal;
		this.listaprog = listaprog;
	}
	public String getKanal() {
		return kanal;
	}
	public void setKanal(String kanal) {
		this.kanal = kanal;
	}
	public ArrayList<Program> getListaprog() {
		return listaprog;
	}
	public void setListaprog(ArrayList<Program> listaprog) {
		this.listaprog = listaprog;
	}
}
