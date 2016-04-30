package com.tinyeditor.view.javafx.toolsbox.controllers;

import com.tinyeditor.modules.filter.dithering.AverageFilter;
import com.tinyeditor.modules.filter.dithering.ErrorDiffusionFilter;
import com.tinyeditor.modules.filter.dithering.OrderedDitheringFilter;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;

/**
 * Controller for Dithering Filters component.
 *
 * @since   Apr 28, 2016
 * @author  Constantin MASSON
 */
public class DitheringFiltersController extends AbsFilterController{
	// *************************************************************************
	// Components
	// *************************************************************************
	@FXML
	private Slider      averageDitheringSlider;
	@FXML
	private Slider      orderedDitheringSliderK;
	@FXML
	private Slider      orderedDitheringSliderN;
	@FXML
	private Slider      errorDiffusionSlider;
	@FXML
	private CheckBox    averageCheckBox;
	@FXML
	private CheckBox    orderedCheckBox;
	@FXML
	private CheckBox    errorCheckBox;


	// *************************************************************************
	// Handlers
	// *************************************************************************
	@FXML
	private void handleAverageFilter(){
		//If filter is not selected, just skipp action
		if(!this.averageCheckBox.isSelected()){
			return;
		}
		int value = (int)this.averageDitheringSlider.getValue();
		AverageFilter filter = new AverageFilter();
		filter.setK(value);
		this.applyFilter(filter);
	}
	@FXML
	private void handleOrderedDitheringFilter(){
		//If filter is not selected, just skipp action
		if(!this.orderedCheckBox.isSelected()){
			return;
		}
		int valueK = (int)this.orderedDitheringSliderK.getValue();
		int valueN = (int)this.orderedDitheringSliderN.getValue();
		OrderedDitheringFilter filter = new OrderedDitheringFilter();
		filter.setK(valueK);
		filter.setN(valueN);
		this.applyFilter(filter);
	}
	@FXML
	private void handleErrorDiffusionFilter(){
		//If filter is not selected, just skipp action
		if(!this.errorCheckBox.isSelected()){
			return;
		}
		int value = (int)this.errorDiffusionSlider.getValue();
		ErrorDiffusionFilter filter = new ErrorDiffusionFilter();
		filter.setK(value);
		this.applyFilter(filter);
	}
}
