package com.tinyeditor.view.javafx.toolsbox.controllers;

import com.tinyeditor.modules.filter.asset.ImageFilter;
import com.tinyeditor.view.javafx.FxApp;
import com.tinyeditor.view.javafx.editor.EditorFxController;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;

/**
 * Controller for General Filters.
 *
 * @since   Apr 28, 2016
 * @author  Constantin MASSON
 */
public class GeneralFiltersController {
	private FxApp mainApp; //Parent FxApplication
	private EditorFxController editor; //Data managed (Through another controller)

	// *************************************************************************
	// Components
	// *************************************************************************
	@FXML
	private Slider      brightnessSlider;
	@FXML
	private Slider      contrastSlider;
	@FXML
	private Slider      gammaSlider;
	@FXML
	private Label       brightnessValueLabel;
	@FXML
	private Label       contrastValueLabel;
	@FXML
	private Label       gammaValueLabel;
	@FXML
	private CheckBox    brightnessCheckBox;
	@FXML
	private CheckBox    contrastCheckBox;
	@FXML
	private CheckBox    gammaCheckBox;


	// *************************************************************************
	// Handlers
	// *************************************************************************
	@FXML
	private void handleInversionBox(){
	}

	@FXML
	private void handleBrightnessSlider(){
		int value = (int)this.brightnessSlider.getValue();
		this.brightnessValueLabel.setText(String.valueOf(value));
	}

	@FXML
	private void handleContrastSlider(){
		int value = (int)this.contrastSlider.getValue();
		this.contrastValueLabel.setText(String.valueOf(value));
	}

	@FXML
	private void handleGammaSlider(){
		int value = (int)this.gammaSlider.getValue();
		this.gammaValueLabel.setText(String.valueOf(value));
	}


	// ************************************************************************
	// Controller Inner Functions
	// ************************************************************************
	/* Apply a given filter to current image and update */
	private void startApplyFilter(ImageFilter filter){
		Image currentImg = this.mainApp.getEditor().getImage().getProcessImage();
		if(currentImg == null){
			return;
		}
		Image newImage = filter.applyFilter(currentImg);
		//this.imageEditorView.setImage(newImage);
	}

	/** Set the linked mainApp */
	public void setMainApp(FxApp mainApp){
		this.mainApp = mainApp;
	}
	/** Set the editor managed by the filters */
	public void setEditor(EditorFxController controller){
		this.editor = controller;
	}
}
