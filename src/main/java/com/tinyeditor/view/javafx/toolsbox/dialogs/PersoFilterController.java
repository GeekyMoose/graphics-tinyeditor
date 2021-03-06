package com.tinyeditor.view.javafx.toolsbox.dialogs;

import com.tinyeditor.modules.filter.convolution.PersonalFilter;
import com.tinyeditor.view.javafx.toolsbox.controllers.AbsFilterController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Controller for Personal Filter.
 *
 * @Since   March 9, 2016
 * @author  Constantin MASSON
 */
public class PersoFilterController extends AbsFilterController{
	// ************************************************************************
	// Inner Attributes
	// ************************************************************************
	private Stage   dialogStage;
	private int     matrixNbRow;
	private int     matrixNbColumn;
	private int     offset;
	private int     divisor;


	// ************************************************************************
	// FX Components
	// ************************************************************************
	@FXML
	private TextField   divisorTextField;
	@FXML
	private TextField   offsetTextField;

	//Matrix management
	@FXML
	private TextField   matrixNbRowTextField;
	@FXML
	private TextField   matrixNbColumnTextField;
	@FXML
	private GridPane    matrixGridPane;
	@FXML
	private AnchorPane  matrixAnchorPane;



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
		if(!this.isValid() || !this.isValidMatrixContent()){
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.initOwner(this.dialogStage);
			alert.setTitle("Invalid input");
			alert.setHeaderText("Invalid input");
			alert.setContentText("Please check if all data are written and valid");
			alert.showAndWait();
			return false;
		}
		//Create the new filter and save it in the editor model.
		PersonalFilter filter = this.createPersoFilterFromInput();
		this.editor.getModel().setPersonalFilter(filter);
		this.dialogStage.close();
		return true;
	}

	@FXML
	private boolean handleSetMatrixSizeButton(){
		if(!this.isValidMatrixSize()){
			Alert alert = new Alert(Alert.AlertType.WARNING);
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
		this.reloadGridPane();
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
	// Other / Filter create functions
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
		int[][]m	= this.editor.getModel().getPersonalFilter().getMatrix();
		int divisor	= this.editor.getModel().getPersonalFilter().getDivisor();
		int offset	= this.editor.getModel().getPersonalFilter().getOffset();
		this.setData(divisor, offset,m);
	}


	// ************************************************************************
	// Tools / Asset Functions
	// ************************************************************************

	/**
	 * Check whether the values are valid.
	 *
	 * @return True if is valid, otherwise, return false.
	 */
	public boolean isValid(){
		try{
			int div		= Integer.parseInt(this.divisorTextField.getText());
			int offset	= Integer.parseInt(this.offsetTextField.getText());
			if(div < 1 || offset < 0){ return false; }
			return true;
		} catch(NumberFormatException e){
			return false;
		}
	}

	/**
	 * Check whether matrix size is valid
	 *
	 * @return True if valid, otherwise, return false.
	 */
	private boolean isValidMatrixSize(){
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
	 * Check whether each element in matrix are a number
	 *
	 * @return
	 */
	private boolean isValidMatrixContent(){
		try{
			for(Node node : this.matrixGridPane.getChildren()){
				TextField tf = (TextField)node;
				Integer.parseInt(tf.getText());
			}
		}
		catch(NumberFormatException e){
			return false;
		}
		return true;
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
				tf.setPrefWidth(100);
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
			try{
				TextField tf = (TextField)node;
				m[y][x] = Integer.parseInt(tf.getText());
			}
			catch(NumberFormatException e){
				m[y][x] = 0;
			}
			x++;
			if(x >= this.matrixNbColumn){
				x = 0;
				y++;
			}
		}
		return m;
	}


	// ************************************************************************
	// Getters - Setters
	// ************************************************************************
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
	public Stage getDialogStage(){
		return this.dialogStage;
	}
}
