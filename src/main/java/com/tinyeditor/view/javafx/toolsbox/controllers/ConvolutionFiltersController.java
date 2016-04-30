package com.tinyeditor.view.javafx.toolsbox.controllers;

import com.tinyeditor.modules.filter.convolution.*;
import javafx.fxml.FXML;

/**
 * Controller for convolution Filters component.
 *
 * @since   Apr 28, 2016
 * @author  Constantin MASSON
 */
public class ConvolutionFiltersController extends AbsFilterController{
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
	private void handlePersoFilterBox(){
		//@TODO
	}
}
