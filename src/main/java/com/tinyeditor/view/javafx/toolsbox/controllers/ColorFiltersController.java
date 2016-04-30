package com.tinyeditor.view.javafx.toolsbox.controllers;

import com.tinyeditor.modules.filter.colors.UniformQuantizationFilter;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;

/**
 * Controller for color filters component.
 *
 * @since   Apr 28, 2016
 * @author  Constantin MASSON
 */
public class ColorFiltersController extends AbsFilterController{
	// *************************************************************************
	// Components
	// *************************************************************************
	@FXML
	private Slider      uniformRedPalletSlider;
	@FXML
	private Slider      uniformGreenPalletSlider;
	@FXML
	private Slider      uniformBluePalletSlider;
	@FXML
	private CheckBox    uniformQuantizationCheckBox;


	// *************************************************************************
	// Handlers
	// *************************************************************************
	@FXML
	private void handleUniformQuantization(){
		//If not activated, do nothing
		if(!this.uniformQuantizationCheckBox.isSelected()){
			return;
		}
		int rp = (int)this.uniformRedPalletSlider.getValue();
		int gp = (int)this.uniformGreenPalletSlider.getValue();
		int bp = (int)this.uniformBluePalletSlider.getValue();
		UniformQuantizationFilter filter = new UniformQuantizationFilter();
		filter.setRedK(rp);
		filter.setGreenK(gp);
		filter.setBlueK(bp);
		this.applyFilter(filter);
	}
}
