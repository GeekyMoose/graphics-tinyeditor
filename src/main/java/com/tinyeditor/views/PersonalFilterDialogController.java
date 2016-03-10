package main.java.com.tinyeditor.views;

import main.java.com.tinyeditor.filter.convolution.PersonalFilter;
import main.java.com.tinyeditor.MainApp;

import java.lang.NumberFormatException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


/**
 * Controller for Person Filter Dialog
 */
public class PersonalFilterDialogController{
	// ************************************************************************
	// FX Attributes
	// ************************************************************************
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
	@FXML
	private AnchorPane matrixAnchorPane;


	// ************************************************************************
	// Inner Attributes
	// ************************************************************************
	private MainApp mainApp;
	private Stage dialogStage;
	private int matrixNbRow;
	private int matrixNbColumn;
	private int offset;
	private int divisor;


	// ************************************************************************
	// Init functions
	// ************************************************************************
	@FXML
	private void initialize(){
		int[][]m = {{0,0,0},{0,0,0},{0,0,0}};
		this.setData(1, 0, m);
	}


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
		PersonalFilter filter = this.createPersoFilterFromInput();
		this.mainApp.setPersonalFilter(filter);
		this.dialogStage.close();
		return true;
	}

	@FXML
	private boolean handleSetMatrixSizeButton(){
		if(!this.isValidMatrixSize()){
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(this.dialogStage);
			alert.setTitle("Invalid input");
			alert.setHeaderText("Invalid matrix size");
			alert.setContentText("Matrix size must be 4, 5, 7, or 9");
			alert.showAndWait();
			return false;
		}
		int nbRow = Integer.parseInt(this.matrixNbRowTextField.getText());
		int nbCol = Integer.parseInt(this.matrixNbColumnTextField.getText());
		this.setMatrixSize(nbRow, nbCol);
		return true;
	}

	@FXML
	private void handleCancelButton(){
		this.dialogStage.close();
	}

	@FXML
	private void handleSetBlurPreset(){
		int[][]m = {{1,1,1},{1,1,1},{1,1,1}};
		this.setData(9, 0, m);
	}
	@FXML
	private void handleSetEdgePreset(){
		int[][]m = {{0,-1,0},{0,1,0},{0,0,0}};
		this.setData(1, 0, m);
	}
	@FXML
	private void handleSetEmbossPreset(){
		int[][]m = {{-1,0,1},{-1,1,1},{-1,0,1}};
		this.setData(1, 0, m);
	}
	@FXML
	public void handleResetValues(){
		this.resetValues();
	}


	// ************************************************************************
	// Other / Filter crea functions
	// ************************************************************************
	/* Data must be valid before */
	public PersonalFilter createPersoFilterFromInput(){
		int div		= Integer.parseInt(this.divisorTextField.getText());
		int offset	= Integer.parseInt(this.offsetTextField.getText());
		int[][]matrix = getMatrixFromGridPane();
		PersonalFilter f = new PersonalFilter(matrix, offset, div);
		return f;
	}
	public void resetValues(){
		int[][]m	= this.mainApp.getPersonalFilter().getMatrix();
		int divisor	= this.mainApp.getPersonalFilter().getDivisor();
		int offset	= this.mainApp.getPersonalFilter().getOffset();
		this.setData(divisor, offset,m);
	}


	// ************************************************************************
	// Tools / Asset Functions
	// ************************************************************************
	/* Check whether values are valid */
	public boolean isValid(){
		try{
			int div		= Integer.parseInt(this.divisorTextField.getText());
			int offset	= Integer.parseInt(this.offsetTextField.getText());
			if(div < 1){return false; }
		}
		catch(NumberFormatException e){
			return false;
		}
		return true;
	}

	/* Check whether matrix size is valid */
	public boolean isValidMatrixSize(){
		try{
			int mNbR	= Integer.parseInt(this.matrixNbRowTextField.getText());
			int mNbC	= Integer.parseInt(this.matrixNbColumnTextField.getText());
			if(mNbR != 3 && mNbR != 5 && mNbR != 7 && mNbR != 9){ return false; }
			if(mNbC != 3 && mNbC != 5 && mNbC != 7 && mNbC != 9){ return false; }
		}
		catch(NumberFormatException e){
			return false;
		}
		return true;
	}

	/**
	 * Set data for displayed dialog.
	 */
	private void setData(int divisor, int offset, int[][]m){
		this.divisorTextField.setText(Integer.toString(divisor));
		this.offsetTextField.setText(Integer.toString(offset));
		int nbRow = m.length;
		int nbCol = m[0].length;
		this.setMatrixSize(nbRow, nbCol);
		this.reloadGridPane();
		this.setValuesMatrixGridPane(m);
	}


	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
	}

	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
	}


	// ************************************************************************
	// Tools / Asset Functions - Matrix management
	// ************************************************************************
	
	/* Set the matrix size and reset the displayed matrix - Size must be valid*/
	private void setMatrixSize(int row, int col){
		this.matrixNbRow	= row;
		this.matrixNbColumn	= col;
	}

	/* Reset the gridPane display */
	private void reloadGridPane(){
		this.matrixNbRowTextField.setText(Integer.toString(this.matrixNbRow));
		this.matrixNbColumnTextField.setText(Integer.toString(this.matrixNbColumn));
		this.matrixGridPane.getChildren().clear();
		for(int x=0; x<this.matrixNbColumn; x++){
			for(int y=0; y<this.matrixNbRow; y++){
				TextField tf = new TextField();
				tf.setPrefWidth(25);
				tf.setText("0");
				this.matrixGridPane.add(tf, x, y);
			}
		}
	}

	/* Set value of each matrix element in GridPane */
	private void setValuesMatrixGridPane(int[][]m){
		int y = 0, x = 0, pos = 0;
		for(Node node : this.matrixGridPane.getChildren()) {
			TextField tf = (TextField)node;
			tf.setText(Integer.toString(m[y][x]));
			x++;
			if(x >= this.matrixNbColumn){
				x = 0;
				y++;
			}
		}
	}

	/* Get Matrix values from dialog */
	private int[][] getMatrixFromGridPane(){
		int[][]m = new int[this.matrixNbRow][this.matrixNbColumn];
		int y = 0, x = 0, pos = 0;
		for(Node node : this.matrixGridPane.getChildren()) {
			TextField tf = (TextField)node;
			m[y][x] = Integer.parseInt(tf.getText());
			x++;
			if(x >= this.matrixNbColumn){
				x = 0;
				y++;
			}
		}
		return m;
	}
}


