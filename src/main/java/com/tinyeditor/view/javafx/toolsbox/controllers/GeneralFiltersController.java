package com.tinyeditor.view.javafx.toolsbox.controllers;

import com.tinyeditor.modules.filter.functional.BrightnessFilter;
import com.tinyeditor.modules.filter.functional.ContrastFilter;
import com.tinyeditor.modules.filter.functional.GammaCorrectionFilter;
import com.tinyeditor.modules.filter.functional.InversionFilter;
import com.tinyeditor.view.javafx.FxApp;
import com.tinyeditor.view.javafx.editor.EditorFxController;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

/**
 * Controller for General Filters.
 *
 * @since   Apr 28, 2016
 * @author  Constantin MASSON
 */
public class GeneralFiltersController {
	private FxApp               mainApp; //Parent FxApplication
	private EditorFxController  editor; //Data managed (Through another controller)

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
		this.editor.applyFilter(new InversionFilter());
	}

	@FXML
	private void handleBrightnessSlider(){
		int value = (int)this.brightnessSlider.getValue();
		this.brightnessValueLabel.setText(String.valueOf(value));
		BrightnessFilter filter = new BrightnessFilter();
		filter.setCoef(value);
		//Apply filter only if checkbox selected
		if(this.brightnessCheckBox.isSelected()){
			this.editor.applyFilter(filter);
		}
	}

	@FXML
	private void handleContrastSlider(){
		double value = this.contrastSlider.getValue();
		this.contrastValueLabel.setText(String.valueOf(value));
		ContrastFilter filter = new ContrastFilter();
		filter.setCoef(value);
		//Apply filter if checkbox selected
		if(this.contrastCheckBox.isSelected()){
			this.editor.applyFilter(filter);
		}
	}

	@FXML
	private void handleGammaSlider(){
		double value = this.gammaSlider.getValue();
		this.gammaValueLabel.setText(String.valueOf(value));
		GammaCorrectionFilter filter = new GammaCorrectionFilter();
		filter.setCoef(value);
		//Apply if checkbox selected
		if(this.gammaCheckBox.isSelected()){
			this.editor.applyFilter(filter);
		}
	}


	// ************************************************************************
	// Getters - Setters
	// ************************************************************************
	/**
	 * Set the linked mainApp
	 * */
	public void setMainApp(FxApp mainApp){
		this.mainApp = mainApp;
	}

	/**
	 * Set the editor managed by the filters
	 * */
	public void setEditor(EditorFxController controller){
		this.editor = controller;
	}
}
