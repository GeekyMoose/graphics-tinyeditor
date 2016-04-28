package com.tinyeditor.view.javafx.toolsbox.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

/**
 * Controller for General Filters.
 *
 * @since   Apr 28, 2016
 * @author  Constantin MASSON
 */
public class GeneralFiltersController {
	// *************************************************************************
	// Components
	// *************************************************************************
	@FXML
	private Slider  brightnessSlider;
	@FXML
	private Slider  contrastSlider;
	@FXML
	private Slider  gammaSlider;
	@FXML
	private Label   brightnessValueLabel;
	@FXML
	private Label   contrastValueLabel;
	@FXML
	private Label   gammaValueLabel;


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
}
