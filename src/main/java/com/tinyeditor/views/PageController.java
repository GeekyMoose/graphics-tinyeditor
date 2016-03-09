package main.java.com.tinyeditor.views;

import  main.java.com.tinyeditor.image.*;
import  main.java.com.tinyeditor.filter.*;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.control.CheckBox;
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
	private CheckBox	inversionBox;
	@FXML
	private CheckBox	blurFilterBox;
	@FXML
	private CheckBox	sharpenFilterBox;
	@FXML
	private CheckBox	edgeDectectionFilterBox;
	@FXML
	private Slider		contrastFilterSlider;
	@FXML
	private Slider		gammaCorrectionFilterSlider;



	// ************************************************************************
	// Attributes Model
	// ************************************************************************
	private BrightnessFilter	brightnessFilter	= new BrightnessFilter();
	private ContrastFilter		contrastFilter		= new ContrastFilter();
	private ImageEditor 		imageEditor			= new ImageEditor();
	private GammaCorrectionFilter	gammaCorrectionFilter = new GammaCorrectionFilter();


	// ************************************************************************
	// Initialization - Constructors
	// ************************************************************************
	@FXML
	private void initialize(){
		//@TODO Temporary
		this.imageEditor= new ImageEditor();
		this.imageEditorView.setImage(this.imageEditor.getProcessImage());
	}


	// ************************************************************************
	// Handler General filters
	// ************************************************************************
	@FXML
	private void handleInversionBox(){
		InversionFilter filter = new InversionFilter();
		Image currentImg = this.imageEditorView.getImage();
		Image newImg = filter.applyFilter(currentImg);
		this.imageEditorView.setImage(newImg);
	}

	@FXML
	private void handleBrightnessSlider(){
		int value = (int)this.brightnessSlider.getValue();
		BrightnessFilter filter = this.brightnessFilter;
		filter.setCoef(value);
		Image currentImg = this.imageEditorView.getImage();
		Image newImg = filter.applyFilter(currentImg);
		this.imageEditorView.setImage(newImg);
	}

	@FXML
	private void handleContrastSlider(){
		int value = (int)this.contrastFilterSlider.getValue();
		this.contrastFilter.setCoef(value);
		Image currentImg = this.imageEditorView.getImage();
		Image newImg = this.contrastFilter.applyFilter(currentImg);
		this.imageEditorView.setImage(newImg);
	}

	@FXML
	private void handelGammaCorrectionFilterSlider(){
		double value = this.gammaCorrectionFilterSlider.getValue();
		this.gammaCorrectionFilter.setCoef(value);
		Image currentImg = this.imageEditorView.getImage();
		Image newImg = this.gammaCorrectionFilter.applyFilter(currentImg);
		this.imageEditorView.setImage(newImg);
	}


	// ************************************************************************
	// Handler Convolution filters
	// ************************************************************************
	@FXML
	private void handleBlurFilterBox(){
		BlurFilter filter = new BlurFilter();
		Image currentImg = this.imageEditorView.getImage();
		Image newImage = filter.applyFilter(currentImg);
		this.imageEditorView.setImage(newImage);
	}
	
	@FXML
	private void handleSharpenFilterBox(){
		SharpenFilter filter = new SharpenFilter();
		Image currentImg = this.imageEditorView.getImage();
		Image newImage = filter.applyFilter(currentImg);
		this.imageEditorView.setImage(newImage);
	}

	@FXML
	private void handleEdgeDetectionFilterBox(){
		EdgeDetectionFilter filter = new EdgeDetectionFilter();
		Image currentImg = this.imageEditorView.getImage();
		Image newImage = filter.applyFilter(currentImg);
		this.imageEditorView.setImage(newImage);
	}

	@FXML
	private void handleEmbossFilterBox(){
		EmbossFilter filter = new EmbossFilter();
		Image currentImg = this.imageEditorView.getImage();
		Image newImage = filter.applyFilter(currentImg);
		this.imageEditorView.setImage(newImage);
	}

	@FXML
	private void handleGaussianSmoothingFilterBox(){
		GaussianSmoothingFilter filter = new GaussianSmoothingFilter();
		Image currentImg = this.imageEditorView.getImage();
		Image newImage = filter.applyFilter(currentImg);
		this.imageEditorView.setImage(newImage);
	}
}
