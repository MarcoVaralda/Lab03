package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import it.polito.tdp.spellchecker.model.Dictionary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Dictionary model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private TextArea txtInput;

    @FXML
    private TextArea txtResult;
    
    @FXML
    private ComboBox<String> boxLingua;
 

    @FXML
    void doClearText(ActionEvent event) {
    	txtInput.clear();
    	txtResult.clear();
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	
    	String testoInserito = txtInput.getText().replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_'()\\[\\]\"]","");
    	String[] testo = testoInserito.split(" ");
    	LinkedList<String> listaParole = new LinkedList<String>();
    	for(String s : testo)
    		listaParole.add(s);
    	
    	LinkedList<RichWord> paroleErrate = new LinkedList<RichWord>(model.spellCheckText(listaParole));
    	
    	String risultato = "";
    	for(RichWord rw : paroleErrate) {
    		risultato = risultato +rw.getParola()+"\n";
    	}
    	txtResult.setText(risultato);    	
    }
    
    @FXML
    void handleLingua(ActionEvent event) {
    	model.loadDictonary(boxLingua.getValue());
    }

    @FXML
    void initialize() {
        assert boxLingua != null : "fx:id=\"boxLingua\" was not injected: check your FXML file 'Scene.fxml'.";
    	assert txtInput != null : "fx:id=\"txtInput\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Dictionary model) {		
		this.model = model;
		boxLingua.getItems().addAll("English", "Italian");
	}
}


