package main.java.com.tinyeditor.views;

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
		this.matrixNbRow	= 3;
		this.matrixNbColumn	= 3;
		this.matrixNbRowTextField.setText("3");
		this.matrixNbColumnTextField.setText("3");
		int[][]m = {{0,0,0},{0,0,0},{0,0,0}};
		setMatrixGridPane(m);
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
		this.createPersoFilterFromInput(); //@TODO
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
		this.setMatrixSize();
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
	// Other / Filter crea functions
	// ************************************************************************
	/* Data must be valid before */
	public void createPersoFilterFromInput(){
		int div		= Integer.parseInt(this.divisorTextField.getText());
		int offset	= Integer.parseInt(this.offsetTextField.getText());
		int[][]matrix = getMatrixFromGridPane();
	}


	// ************************************************************************
	// Tools / Asset Functions
	// ************************************************************************
	/* Check whether values are valid */
	public boolean isValid(){
		if(this.divisorTextField.getText().equals("")){return false;}
		if(this.offsetTextField.getText().equals("")){return false;}
		int div		= Integer.parseInt(this.divisorTextField.getText());
		int offset	= Integer.parseInt(this.offsetTextField.getText());
		if(div < 1){return false; }
		return true;
	}

	/* Check whether matrix size is valid */
	public boolean isValidMatrixSize(){
		if(this.matrixNbRowTextField.getText().equals("")){return false;}
		if(this.matrixNbColumnTextField.getText().equals("")){return false;}
		int mNbR	= Integer.parseInt(this.matrixNbRowTextField.getText());
		int mNbC	= Integer.parseInt(this.matrixNbColumnTextField.getText());
		if(mNbR != 3 && mNbR != 5 && mNbR != 7 && mNbR != 9){ return false; }
		if(mNbC != 3 && mNbR != 5 && mNbR != 7 && mNbR != 9){ return false; }
		return true;
	}

	/**
	 * Set data for displayed dialog.
	 */
	private void setData(int divisor, int offset, int mNbR, int mNbC, int[][]m){
		this.divisorTextField.setText(Integer.toString(divisor));
		this.offsetTextField.setText(Integer.toString(offset));
		this.matrixNbRowTextField.setText(Integer.toString(mNbR));
		this.matrixNbColumnTextField.setText(Integer.toString(mNbC));
		this.setMatrixGridPane(m);
	}

	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
	}


	// ************************************************************************
	// Tools / Asset Functions - Matrix management
	// ************************************************************************
	private void setMatrixSize(){
		this.matrixNbRow	= Integer.parseInt(this.matrixNbRowTextField.getText());
		int matrixNbColumn	= Integer.parseInt(this.matrixNbColumnTextField.getText());
	}

	/* Set value of each matrix element in GridPane */
	private void setMatrixGridPane(int[][]m){
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


