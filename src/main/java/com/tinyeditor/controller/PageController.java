package com.tinyeditor.controller;

import com.tinyeditor.MainApp;
import com.tinyeditor.filter.asset.*;
import com.tinyeditor.filter.convolution.*;
import com.tinyeditor.filter.functional.*;
import com.tinyeditor.filter.dithering.*;
import com.tinyeditor.filter.colors.*;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;


/**
 * General controller for page (The main editor element)
 *
 * @since	March 8, 2016
 * @author	Constantin MASSON
 */
public class PageController{
	// ************************************************************************
	// Attributes FXML
	// ************************************************************************
	@FXML
	private Slider		brightnessSlider;
	@FXML
	private ImageView	imageEditorView;
	@FXML
	private Slider		contrastFilterSlider;
	@FXML
	private Slider		gammaCorrectionFilterSlider;
	@FXML
	private Slider		averageDitheringSlider;
	@FXML
	private Slider		orderedDitheringSlider;
	@FXML
	private Slider		orderedDitheringSliderN;
	@FXML
	private Slider		ErrorDiffusionSlider;
	@FXML
	private Slider		uniformRedPalletSlider;
	@FXML
	private Slider		uniformGreenPalletSlider;
	@FXML
	private Slider		uniformBluePalletSlider;



	// ************************************************************************
	// Attributes Model
	// ************************************************************************
	private MainApp		mainApp;

	// Functional Filters
	private BrightnessFilter		brightnessFilter		= new BrightnessFilter();
	private ContrastFilter			contrastFilter			= new ContrastFilter();
	private GammaCorrectionFilter	gammaCorrectionFilter	= new GammaCorrectionFilter();
	private ImageFilter				inversionFilter			= new InversionFilter();

	// Convolution Filters
	private ImageFilter blurFilter				= new BlurFilter();
	private ImageFilter sharpenFilter			= new SharpenFilter();
	private ImageFilter edgeDetectionFilter		= new EdgeDetectionFilter();
	private ImageFilter gaussianSmoothingFilter	= new GaussianSmoothingFilter();
	private ImageFilter embossFilter			= new EmbossFilter();

	//Dithering filters
	private AverageFilter			averageFilter			= new AverageFilter();
	private OrderedDitheringFilter	orderedDitheringFilter	= new OrderedDitheringFilter();
	private ErrorDiffusionFilter	errorDiffusionFilter	= new ErrorDiffusionFilter();

	//Color filters
	private UniformQuantizationFilter uniformQuantizationFilter = new UniformQuantizationFilter();


	// ************************************************************************
	// Initialization - Constructors
	// ************************************************************************
	@FXML
	private void initialize(){
	}


	// ************************************************************************
	// Handler Functional  filters
	// ************************************************************************
	@FXML
	private void handleInversionBox(){
		this.startApplyFilter(this.inversionFilter);
	}
	@FXML
	private void handleBrightnessSlider(){
		int value = (int)this.brightnessSlider.getValue();
		this.brightnessFilter.setCoef(value);
		this.startApplyFilter(this.brightnessFilter);
	}
	@FXML
	private void handleContrastSlider(){
		this.contrastFilter.setCoef(this.contrastFilterSlider.getValue());
		this.startApplyFilter(this.contrastFilter);
	}
	@FXML
	private void handelGammaCorrectionFilterSlider(){
		this.gammaCorrectionFilter.setCoef(this.gammaCorrectionFilterSlider.getValue());
		this.startApplyFilter(this.gammaCorrectionFilter);
	}


	// ************************************************************************
	// Handler Convolution filters
	// ************************************************************************
	@FXML
	private void handleBlurFilterBox(){
		this.startApplyFilter(this.blurFilter);
	}
	@FXML
	private void handleSharpenFilterBox(){
		this.startApplyFilter(this.sharpenFilter);
	}
	@FXML
	private void handleEdgeDetectionFilterBox(){
		this.startApplyFilter(this.edgeDetectionFilter);
	}
	@FXML
	private void handleEmbossFilterBox(){
		this.startApplyFilter(this.embossFilter);
	}
	@FXML
	private void handleGaussianSmoothingFilterBox(){
		this.startApplyFilter(this.gaussianSmoothingFilter);
	}
	@FXML
	private void handlePersoFilterBox(){
		this.startApplyFilter(this.mainApp.getPersonalFilter());
	}


	// ************************************************************************
	// Handler for dithering filters
	// ************************************************************************
	@FXML
	private void handleAverageFilter(){
		int value = (int)this.averageDitheringSlider.getValue();
		this.averageFilter.setK(value);
		this.startApplyFilter(this.averageFilter);
	}
	@FXML
	private void handleOrderedDitheringFilter(){
		int value = (int)this.orderedDitheringSlider.getValue();
		int valueN = (int)this.orderedDitheringSliderN.getValue();
		this.orderedDitheringFilter.setK(value);
		this.orderedDitheringFilter.setN(valueN);
		this.startApplyFilter(this.orderedDitheringFilter);
	}
	@FXML
	private void handleErrorDiffusionFilter(){
		int value = (int)this.ErrorDiffusionSlider.getValue();
		this.errorDiffusionFilter.setK(value);
		this.startApplyFilter(this.errorDiffusionFilter);
	}


	// ************************************************************************
	// Handler For dialog and other
	// ************************************************************************
	@FXML
	private void handleUniformQuantization(){
		int rp = (int)this.uniformRedPalletSlider.getValue();
		int gp = (int)this.uniformGreenPalletSlider.getValue();
		int bp = (int)this.uniformBluePalletSlider.getValue();
		this.uniformQuantizationFilter.setRedK(rp);
		this.uniformQuantizationFilter.setGreenK(gp);
		this.uniformQuantizationFilter.setBlueK(bp);
		this.startApplyFilter(this.uniformQuantizationFilter);
	}


	// ************************************************************************
	// Handler For dialog and other
	// ************************************************************************
	@FXML
	private void handlePersoFilterEdit(){
		this.mainApp.showPersonalFilterDialog();
	}

	// ************************************************************************
	// Controller Inner Functions
	// ************************************************************************
	/* Apply a given filter to current image and update */
	private void startApplyFilter(ImageFilter filter){
		Image currentImg = this.imageEditorView.getImage();
		if(currentImg == null){
			return;
		}
		Image newImage = filter.applyFilter(currentImg);
		this.imageEditorView.setImage(newImage);
	}

	/* Set the linked mainApp */
	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
	}

	public void updateImg(Image img){
		this.imageEditorView.setImage(img);
	}
}
