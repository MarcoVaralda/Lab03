package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Dictionary {
	
	LinkedList<String> dizionario = new LinkedList<String>();
	
	/**
	 * Carica i dizionari della lingua scelta (Italiano o Inglese)
	 * @param language
	 */
	public void loadDictonary(String language) {
		
		if(language.toLowerCase().equals("english")) {
			try {
				FileReader fr = new FileReader("src/main/resources/English.txt");
				BufferedReader br = new BufferedReader(fr);
				
				String word;
				while( (word=br.readLine())!=null ) {
					dizionario.add(word);
				}
				br.close();		
				fr.close();
			}
			catch(IOException ioe) {
				System.out.println("Errore nella lettura del file!!");
			}
		}
		
		if(language.toLowerCase().equals("italian")) {
			try {
				FileReader fr = new FileReader("src/main/resources/Italian.txt");
				BufferedReader br = new BufferedReader(fr);
				
				String parola;
				while( (parola=br.readLine())!=null ) {
					dizionario.add(parola);
				}
				br.close();	
				fr.close();
			}
			catch(IOException ioe) {
				System.out.println("Errore nella lettura del file!!");
			}
		}
	}
	
	/**
	 * Esegue il controllo di errore e restituisce la lista di RichWord errate
	 * @param inputTextList
	 * @return
	 */
	public List<RichWord> spellCheckText(List<String> inputTextList) {
		LinkedList<RichWord> paroleErrate = new LinkedList<RichWord>();
		
		for(String s : inputTextList)
			if(!dizionario.contains(s.toLowerCase())) {
				RichWord rw = new RichWord(s,false);
				paroleErrate.add(rw);
			}
		
		return paroleErrate;
	}
	
	public List<RichWord> spellCheckTextLinear(List<String> inputTextList) {
        LinkedList<RichWord> paroleErrate = new LinkedList<RichWord>();
		
		for(String s : inputTextList) {
			for(String ss : dizionario)
				if(ss.toLowerCase().equals(s.toLowerCase())) {
					RichWord rw = new RichWord(s,false);
					paroleErrate.add(rw);
				}
		}
		
		return paroleErrate;
	}
	
	public List<RichWord> spellCheckTextDichotomic(List<String> inputTextList) {
        LinkedList<RichWord> paroleErrate = new LinkedList<RichWord>();
        RichWord parola;
        int index;
		
		for(String s : inputTextList) {
			parola = new RichWord(s,false);
			index = Collections.binarySearch(dizionario, s); // Cerca se nella lista è presente la stringa,
			                                                 // se sì, restituisce l'indice in cui l'ha trovata
			
			if(index>=0)
				parola.setCorretta();
			
			if(!parola.isCorretta())
				paroleErrate.add(parola);
		}
		
		return paroleErrate;
	}
	
}
