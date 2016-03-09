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

	// ************************************************************************
	// Attributes Model
	// ************************************************************************
	private BrightnessFilter brightnessFilter = new BrightnessFilter();

	private ImageEditor imageEditor = new ImageEditor();


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
}
