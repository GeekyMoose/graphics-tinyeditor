package com.tinyeditor.view.javafx.toolsbox.controllers;

import com.tinyeditor.modules.filter.convolution.*;
import com.tinyeditor.view.javafx.toolsbox.dialogs.PersoFilterController;
import com.tinyeditor.view.javafx.toolsbox.dialogs.PersoFilterLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;

/**
 * Controller for convolution Filters component.
 *
 * @since   Apr 28, 2016
 * @author  Constantin MASSON
 */
public class ConvolutionFiltersController extends AbsFilterController{
	@FXML
	private CheckBox persoFilterCheckBox;


	// *************************************************************************
	// Handlers
	// *************************************************************************

	@FXML
	private void handleBlurFilterBoxClick(){
		this.applyFilter(new BlurFilter());
	}
	@FXML
	private void handleSharpenFilterBoxClick(){
		this.applyFilter(new SharpenFilter());
	}
	@FXML
	private void handleEdgeDetectionFilterBoxClick(){
		this.applyFilter(new EdgeDetectionFilter());
	}
	@FXML
	private void handleEmbossFilterBoxClick(){
		this.applyFilter(new EmbossFilter());
	}
	@FXML
	private void handleGaussianFilterBoxClick(){
		this.applyFilter(new GaussianSmoothingFilter());
	}
	@FXML
	private void handleEditPersoFilter(){
		PersoFilterController persoFilterController = null;
		try {
			persoFilterController = PersoFilterLoader.load(this.mainApp, this.editor);
		} catch (Exception e) {
			System.err.println("[ERR] Unable to load the edit perso filter dialog: "+e.getMessage());
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Unable to load the filter editor");
			alert.setContentText("Unfortunately, the filter editor didn't manage to start.");
			alert.showAndWait();
			return;
		}
		persoFilterController.getDialogStage().showAndWait();
	}
	@FXML
	private void persoFilterCheckBoxClicked(){
		if(this.persoFilterCheckBox.isSelected()){
			this.applyFilter(this.editor.getModel().getPersonalFilter());
		}
	}
}
