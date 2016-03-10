package main.java.com.tinyeditor.views;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


/**
 * Controller for Person Filter Dialog
 */
public class PersonalFilterDialogController{
	// ************************************************************************
	// FX Attributes - Attributes
	// ************************************************************************
	private Stage dialogStage;

	@FXML
	private TextField divisorTextField;
	@FXML
	private TextField offsetTextField;

	//Matrix management
	@FXML
	private TextField matrixNbRowTextField;
	@FXML
	private TextField matrixNbColumnTextField;
	@FXML
	private GridPane matrixGridPane;


	// ************************************************************************
	// Handler functions
	// ************************************************************************
	@FXML
	private boolean handleValidButton(){
		if(!this.isValid()){
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(this.dialogStage);
			alert.setTitle("Invalid input");
			alert.setHeaderText("Invalid input");
			alert.setContentText("Please check if all data are writen and valid");
			alert.showAndWait();
			return false;
		}
		this.createPersoFilterFromInput();
		this.dialogStage.close();
		return true;
	}

	@FXML
	private void handleCancelButton(){
		this.dialogStage.close();
	}

	@FXML
	private void handleSetBlurPreset(){
		int[][]m = {{1,1,1},{1,1,1},{1,1,1}};
		this.setData(9, 0, 3, 3, m);
	}
	@FXML
	private void handleSetEdgePreset(){
		int[][]m = {{0,-1,0},{0,1,0},{0,0,0}};
		this.setData(1, 0, 3, 3, m);
	}
	@FXML
	private void handleSetEmbossPreset(){
		int[][]m = {{-1,0,1},{-1,1,1},{-1,0,1}};
		this.setData(1, 0, 3, 3, m);
	}


	// ************************************************************************
	// Tools / Asset Functions
	// ************************************************************************
	/* Check whether values are valid */
	public boolean isValid(){
		if(this.divisorTextField.getText().equals("")){return false;}
		if(this.offsetTextField.getText().equals("")){return false;}
		if(this.matrixNbRowTextField.getText().equals("")){return false;}
		if(this.matrixNbColumnTextField.getText().equals("")){return false;}

		int div		= Integer.parseInt(this.divisorTextField.getText());
		int offset	= Integer.parseInt(this.offsetTextField.getText());
		int mNbR	= Integer.parseInt(this.matrixNbRowTextField.getText());
		int mNbC	= Integer.parseInt(this.matrixNbColumnTextField.getText());
		if(div < 1){return false; }
		if(mNbR != 3 && mNbR != 5 && mNbR != 7 && mNbR != 9){ return false; }
		if(mNbC != 3 && mNbR != 5 && mNbR != 7 && mNbR != 9){ return false; }
		return true;
	}

	/* Data must be valid before */
	public void createPersoFilterFromInput(){
		int div		= Integer.parseInt(this.divisorTextField.getText());
		int offset	= Integer.parseInt(this.offsetTextField.getText());
		int mNbR	= Integer.parseInt(this.matrixNbRowTextField.getText());
		int mNbC	= Integer.parseInt(this.matrixNbColumnTextField.getText());

		int[][]matrix = new int[mNbR][mNbC];
	}

	/**
	 * Set data for displayed dialog.
	 */
	private void setData(int divisor, int offset, int mNbR, int mNbC, int[][]m){
		this.divisorTextField.setText(Integer.toString(divisor));
		this.offsetTextField.setText(Integer.toString(offset));
		this.matrixNbRowTextField.setText(Integer.toString(mNbR));
		this.matrixNbColumnTextField.setText(Integer.toString(mNbC));
	}

	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
	}
}


