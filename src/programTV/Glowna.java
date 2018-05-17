package programTV;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Glowna {
	static Glowna g;
	static String plikxml="./database/";
	ArrayList<Stacja> listastacja=new ArrayList<Stacja>();
	boolean flag=true, l=false, licz=false, stop=false;
	String sdata, skanal, sstart, snazwa, skopis, sdopis;
	SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
	BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	
	public Glowna() throws IOException, JAXBException, ParseException {
		wybierz();
	}
	
	public static void main(String[] args) throws IOException, JAXBException, ParseException {
		System.out.println("Witaj w Programie TV!");
		g=new Glowna();
	}
	
	public void wybierz() throws IOException, JAXBException, ParseException {
		System.out.println("Wpisz numer opcji ukazanej poniżej.\n1.import\n2.export\n3.show\n4.details\n5.forget\n6.exit");
		String opc=br.readLine();
		if(opc.equals("1") || opc.equals("2") || opc.equals("3") || opc.equals("4") || opc.equals("5") || opc.equals("6")) {
			int opt=Integer.parseInt(opc);
			switch(opt) {
				case 1:
					importuj();
					break;
				case 2:
					eksportuj();
					break;
				case 3:
					pokaz();
					break;
				case 4:
					szczegoly();
					break;
				case 5:
					zapomnij();
					break;
				case 6:
					System.exit(0);
					break;
			}
		}
		else {
			System.out.println("Wybrano złą opcję. Proszę wybrać ponownie.");
			wybierz();
		}
	}
	
	public void importuj() throws IOException, JAXBException, ParseException {
		System.out.println("Wpisz numer opcji ukazanej poniżej.\n1.insert\n2.update\n3.exit");
		String opc=br.readLine();
		if(opc.equals("1"))
			dodajDate();
		else if(opc.equals("2"))
			modyfikuj();
		else if(opc.equals("3"))
			wybierz();
		else {
			System.out.println("Wybrano zły numer opcji. Proszę wybrać ponownie.");
			importuj();
		}
	}
	
	public void eksportuj() throws IOException, ParseException, JAXBException {
		System.out.print("Proszę wprowadzić nazwy stacji, oddzielając je spacją: ");
		String st=br.readLine();
		String[] tabs=st.split(" ");
		System.out.print("Proszę wprowadzić datę, od której ma zostać wyeksportowany program w formacie dzień-miesiąc-rok (dd-MM-yyyy): ");
		String sdz1=br.readLine();
		if(sdz1.matches("[0-3]+[0-9]+-+[0-1]+[0-9]+-+[0-2]+[0-9]+[0-9]+[0-9]")) {
			Calendar cal1=Calendar.getInstance();
			cal1.setTime(sdf.parse(sdz1));
			System.out.print("Proszę wprowadzić datę, do której ma zostać wyeksportowany program w formacie dzień-miesiąc-rok (dd-MM-yyyy): ");
			String sdz2=br.readLine();
			if(sdz2.matches("[0-3]+[0-9]+-+[0-1]+[0-9]+-+[0-2]+[0-9]+[0-9]+[0-9]")) {
				Calendar cal2=Calendar.getInstance();
				cal2.setTime(sdf.parse(sdz2));;
				JAXBContext jc = JAXBContext.newInstance(Data.class);  
				Unmarshaller m = jc.createUnmarshaller();
				ArrayList<Stacja> listastacja=new ArrayList<Stacja>();
				ArrayList<Program> listaprog=new ArrayList<Program>();
				ArrayList<Data> listadata=new ArrayList<Data>();
				while(stop==false) {
					if(licz==true)
						stop=true;
					File f=new File(plikxml+sdz1+".xml");
					if(f.exists()) {
						ArrayList<Stacja> listst=new ArrayList<Stacja>();
						Data d = (Data) m.unmarshal(f);
						listastacja=d.getListakan();
						for(int i=0; i<listastacja.size(); i++) {
							Stacja s=new Stacja();
							s=listastacja.get(i);
							for(int j=0; j<tabs.length; j++) {
								if(s.getKanal().equals(tabs[j])) {
									listaprog=s.getListaprog();
									Stacja s1=new Stacja(tabs[j], listaprog);
									ArrayList<Stacja> listst2=new ArrayList<Stacja>();
									listst2.add(s1);
									listst.addAll(listst2);
								}
							}
						}
						Data d1=new Data(sdz1, listst);
						listadata.add(d1);
					}
					cal1.add(Calendar.DAY_OF_MONTH, 1);
					sdz1=String.valueOf(cal1.get(Calendar.DAY_OF_MONTH)+"-0"+(cal1.get(Calendar.MONTH)+1)+"-"+cal1.get(Calendar.YEAR));
					if(cal1.equals(cal2))
						licz=true;
				}
				Eksport e=new Eksport();
				e.setListadata(listadata);
				JAXBContext jc1 = JAXBContext.newInstance(Eksport.class);
				Marshaller m1 = jc1.createMarshaller();
				m1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				if(l==false) {
					File xml = new File("program.xml");
					for(int i=0; i<listadata.size(); i++)
						m1.marshal(e, xml);
					System.out.println("Wyeksportowano pomyślnie.");
				}
				if(l==true) {
					StringWriter sw=new StringWriter();
					sw.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
					sw.write("<?xml-stylesheet type=\"text/xsl\" href=\"arkusz.xsl\"?>\n");
					m1.setProperty(Marshaller.JAXB_FRAGMENT, true);
					m1.marshal(e, sw);
					String s=sw.toString();
					PrintWriter pw=new PrintWriter("programTV.xml");
					pw.write(s);
					pw.close();
					System.out.println("Wyeksportowano pomyślnie.");
				}
				l=false;
				licz=false;
				stop=false;
			}
			else {
				System.out.println("Wprowadzono złą datę do. Spróbuj ponownie.");
				eksportuj();
			}
		}
		else {
			System.out.println("Wprowadzono złą datę od. Spróbuj ponownie.");
			eksportuj();
		}
		wybierz();
	}
	
	public void pokaz() throws IOException, ParseException, JAXBException {
		l=true;
		eksportuj();
	}
	
	public void szczegoly() throws IOException, JAXBException, ParseException {
		System.out.print("Proszę wprowadzić datę emisji audycji w formacie dzień-miesiąc-rok(dd-MM-yyyy): ");
		String sdata=br.readLine();
		if(sdata.matches("[0-3]+[0-9]+-+[0-1]+[0-9]+-+[0-2]+[0-9]+[0-9]+[0-9]")) {
			System.out.print("Proszę wprowadzić nazwę stacji: ");
			String skanal=br.readLine();
			System.out.print("Proszę wprowadzić godzinę rozpoczęcia audycji: ");
			String sgodz=br.readLine();
			if(sgodz.matches("[0-2]+[0-9]+:+[0-5]+[0-9]")) {
				JAXBContext jc1 = JAXBContext.newInstance(Data.class);  
				Unmarshaller m1 = jc1.createUnmarshaller();
				File f=new File(plikxml+sdata+".xml");
				Data d = (Data) m1.unmarshal(f);
				ArrayList<Stacja> listastacja=new ArrayList<Stacja>();
				ArrayList<Program> listaprog=new ArrayList<Program>();
				ArrayList<Data> listadata=new ArrayList<Data>();
				Program p1;
				Stacja s1;
				Data d1;
				Eksport e1;
				listastacja=d.getListakan();
				for(int i=0; i<listastacja.size(); i++) {
					Stacja s=new Stacja();
					s=listastacja.get(i);
					if(s.getKanal().equals(skanal))
						listaprog=s.getListaprog();
				}
				for(int j=0; j<listaprog.size(); j++) {
					Program p=new Program();
					p=listaprog.get(j);
					if(p.getStart().equals(sgodz)) {
						p1=new Program(sgodz, p.getNazwa(), p.getKopis(), p.getDopis());
						listaprog.clear();
						listaprog.add(p1);
						s1=new Stacja(skanal, listaprog);
						listastacja.clear();
						listastacja.add(s1);
						d1=new Data(sdata, listastacja);
						listadata.add(d1);
						e1=new Eksport(listadata);
						JAXBContext jc2 = JAXBContext.newInstance(Eksport.class);
						Marshaller m2 = jc2.createMarshaller();
						m2.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
						m2.setProperty(Marshaller.JAXB_FRAGMENT, true);
						StringWriter sw=new StringWriter();
						sw.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
						sw.write("<?xml-stylesheet type=\"text/xsl\" href=\"opis.xsl\"?>\n");
						m2.marshal(e1, sw);
						String s=sw.toString();
						PrintWriter pw=new PrintWriter("Opis-program.xml");
						pw.write(s);
						pw.close();
						System.out.println("Wygenerowano pomyślnie.");
					}
				}
			}
			else {
				System.out.println("Wprowadzono złą godzinę. Spróbuj ponownie.");
				szczegoly();
			}
		}
		else {
			System.out.println("Wprowadzono złą date. Spróbuj ponownie.");
			szczegoly();
		}
		wybierz();
	}
	
	public void zapomnij() throws IOException, JAXBException, ParseException {
		System.out.print("Proszę wprowadzić datę w formacie dzień-miesiąc-rok (dd-MM-yyyy): ");
		String dz=br.readLine();
		if(dz.matches("[0-3]+[0-9]+-+[0-1]+[0-9]+-+[0-2]+[0-9]+[0-9]+[0-9]")) {
			File plik=new File(plikxml+dz+".xml");
			if(plik.delete())
				System.out.println("Program usunięto pomyślnie");
			else
				System.out.println("Program nie został usunięty, sprawdź poprawność danych.");
		}
		else {
			System.out.println("Wprowadzono złą datę. Spróbuj ponownie.");
			zapomnij();
		}
		wybierz();
	}
	
	public void dodajDate() throws IOException, JAXBException, ParseException {
		System.out.print("Proszę wprowadzić datę w formacie dzień-miesiąc-rok (dd-MM-yyyy): ");
		sdata=br.readLine();
		if(sdata.matches("[0-3]+[0-9]+-+[0-1]+[0-9]+-+[0-2]+[0-9]+[0-9]+[0-9]"))
			dodajKanal();
		else {
			System.out.println("Wprowadzono złą datę. Spróbuj ponownie.");
			dodajDate();
		}
	}
	
	public void dodajKanal() throws IOException, JAXBException, ParseException {
		System.out.print("Proszę wprowadzić nazwę kanału: ");
		skanal=br.readLine();
		Stacja s=new Stacja();
		s.kanal=skanal;
		while(flag==true) {
			System.out.print("Proszę wprowadzić czas rozpoczęcia audycji: ");
			sstart=br.readLine();
			if(sstart.matches("[0-2]+[0-9]+:+[0-5]+[0-9]")) {
				System.out.print("Proszę wprowadzić nazwę audycji: ");
				snazwa=br.readLine();
				System.out.print("Proszę wprowadzić krótki opis audycji: ");
				skopis=br.readLine();
				System.out.print("Proszę wprowadzić długi opis audycji: ");
				sdopis=br.readLine();
				s.listaprog.add(new Program(sstart, snazwa, skopis, sdopis));
				System.out.println("Czy dodać kolejną audycję?\n1.Yes\n2.No");
				String opc=br.readLine();
				if(opc.equals("1"))
					flag=true;
				else if(opc.equals("2"))
					flag=false;
			}
			else
				System.out.println("Wprowadzono zły format godziny.");
			
		}
		listastacja.add(new Stacja(skanal, s.listaprog));
		flag=true;
		System.out.println("Czy dodać kolejną stację?\n1.Yes\n2.No");
		String opc=br.readLine();
		if(opc.equals("1"))
			dodajKanal();
		else if(opc.equals("2")) {
			Data d=new Data();
			d.setDzien(sdata);
			d.setListakan(listastacja);
			JAXBContext jc = JAXBContext.newInstance(Data.class);  
			Marshaller m = jc.createMarshaller();   
		    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			File xml = new File(plikxml+sdata+".xml");  
			m.marshal(d, xml);
			listastacja.clear();
			s.listaprog.clear();
			wybierz();
		}
		else {
			System.out.println("Wybrano złą opcję.");
			wybierz();
		}
	}
	
	public void modyfikuj() throws JAXBException, IOException, ParseException {
		System.out.print("Proszę wprowadzić nazwę stacji: ");
		String st=br.readLine();
		System.out.print("Proszę wprowadzić datę w formacie dzień-miesiąc-rok (dd-MM-yyyy): ");
		String dz=br.readLine();
		if(dz.matches("[0-3]+[0-9]+-+[0-1]+[0-9]+-+[0-2]+[0-9]+[0-9]+[0-9]")) {
			JAXBContext jc = JAXBContext.newInstance(Data.class);  
			Unmarshaller m = jc.createUnmarshaller();  
			File xml = new File(plikxml+dz+".xml");  
			Data d = (Data) m.unmarshal(xml);
			System.out.println(d.getDzien());
			ArrayList<Stacja> listastacja=d.getListakan();
			ArrayList<Program> listaprog=new ArrayList<Program>();
			Stacja s=new Stacja();
			for(int i=0; i<listastacja.size(); i++) {
				s=listastacja.get(i);
				if(s.getKanal().equals(st)) {
					s.getKanal();
					listaprog=s.getListaprog();
				}
			}
			int i=0;
			for(Program p:listaprog) {  
			    i++;  
			    System.out.println(i+" "+p.getStart()+" "+p.getNazwa()+" - "+p.getKopis());  
			}
			System.out.print("Proszę wprowadzić numer audycji, który chcesz zmodyfikować: ");
			String sid=br.readLine();
			if(sid.matches("^[0-9]+$")) {
				int id=Integer.parseInt(sid)-1;
				if(id>i || id<i) {
					Program p=new Program();
					p=listaprog.get(id);
					System.out.println("Wybrano: "+(id-1)+" "+p.getStart()+" "+p.getNazwa()+" - "+p.getKopis());
					System.out.print("Proszę wprowadzić czas rozpoczęcia audycji: ");
					sstart=br.readLine();
					if(sstart.matches("[0-2]+[0-9]+:+[0-5]+[0-9]")) {
						System.out.print("Proszę wprowadzić nazwę audycji: ");
						snazwa=br.readLine();
						System.out.print("Proszę wprowadzić krótki opis audycji: ");
						skopis=br.readLine();
						System.out.print("Proszę wprowadzić długi opis audycji: ");
						sdopis=br.readLine();
						Program p1=new Program(sstart, snazwa, skopis, sdopis);
						listaprog.set(id, p1);
						Data d1=new Data();
						d1.setDzien(dz);
						d1.setListakan(listastacja);
						JAXBContext jc1 = JAXBContext.newInstance(Data.class);  
						Marshaller m1 = jc1.createMarshaller();   
					    m1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
						File xml1 = new File(plikxml+dz+".xml");  
						m1.marshal(d, xml1);
						listaprog.clear();
					}
					else {
						System.out.println("Wprowadzono zły format godziny. Spróbuj ponownie.");
						modyfikuj();
					}
				}
				else {
					System.out.println("Wprowadzono zły numer audycji.");
					modyfikuj();
				}
			}
			else {
				System.out.println("Numer audycji jest liczbą");
				modyfikuj();
			}
		}
		else {
			System.out.println("Wprowadzono złą datę. Spróbuj ponownie.");
			modyfikuj();
		}
		wybierz();
	}
}
