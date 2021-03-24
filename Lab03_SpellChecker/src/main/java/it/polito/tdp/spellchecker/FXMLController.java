package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import it.polito.tdp.spellchecker.model.Dictionary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
    private Label txtTime;
    
    @FXML
    private ComboBox<String> boxLingua;
    
    @FXML
    private Label txtNumberOfError;
    
    @FXML
    private Button btnSpellCheck;
    
    @FXML
    private Button btnClearText;
 

    @FXML
    void doClearText(ActionEvent event) {
    	txtInput.clear();
    	txtResult.clear();
    	this.txtNumberOfError.setText("Number of error");
    	this.txtTime.setText("Tempo impiegato");
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	
    	double start;
    	double stop;
    	
    	start=System.nanoTime();
    	
    	String testoInserito = txtInput.getText().replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_'()\\[\\]\"]","");
    	String[] testo = testoInserito.split(" ");
    	LinkedList<String> listaParole = new LinkedList<String>();
    	for(String s : testo)
    		listaParole.add(s);
    	
    	LinkedList<RichWord> paroleErrate = new LinkedList<RichWord>(model.spellCheckText(listaParole));
    	
    	stop = System.nanoTime();
    	txtTime.setText(""+(stop-start)+" nanosecondi");
    	
    	String risultato = "";
    	for(RichWord rw : paroleErrate) {
    		risultato = risultato +rw.getParola()+"\n";
    	}
    	txtResult.setText(risultato); 
    	txtNumberOfError.setText(""+paroleErrate.size());
    }
    
    @FXML
    void handleLingua(ActionEvent event) {
    	model.loadDictonary(boxLingua.getValue());
    	this.btnClearText.setDisable(false);
		this.btnSpellCheck.setDisable(false);
    }

    @FXML
    void initialize() {
        assert boxLingua != null : "fx:id=\"boxLingua\" was not injected: check your FXML file 'Scene.fxml'.";
    	assert txtInput != null : "fx:id=\"txtInput\" was not injected: check your FXML file 'Scene.fxml'.";
       	assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
       	assert txtTime != null : "fx:id=\"txtTime\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNumberOfError != null : "fx:id=\"txtNumberOfError\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnClearText != null : "fx:id=\"btnClearText\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSpellCheck != null : "fx:id=\"btnSpellCheck\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Dictionary model) {		
		this.model = model;
		boxLingua.getItems().addAll("English", "Italian");
	}
}


