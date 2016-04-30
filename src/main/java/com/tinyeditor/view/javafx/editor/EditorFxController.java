package com.tinyeditor.view.javafx.editor;

import com.tinyeditor.editor.Editor;
import com.tinyeditor.main.Constants;
import com.tinyeditor.modules.filter.asset.ImageFilter;
import com.tinyeditor.view.javafx.FxApp;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 *
 * @since   Apr 29, 2016
 * @author  Constantin MASSON
 */
public class EditorFxController {
	private FxApp       mainApp;
	private BorderPane  view; //The managed view
	private Editor      model; //The model editor

	@FXML
	private ImageView imageViewPanel;


	// *************************************************************************
	// Image functions
	// *************************************************************************
	public void updateImage(Image newImage){
		this.model.getImageEditor().update(newImage); //Update model
		this.imageViewPanel.setImage(newImage); //Update view
	}

	/**
	 * Apply a specific filter to the image and update model and display
	 *
	 * @param filter filter to apply
	 */
	public void applyFilter(ImageFilter filter){
		Image currentImg = this.model.getImageEditor().getProcessImage();
		if(currentImg == null){
			return;
		}
		this.updateImage(filter.applyFilter(currentImg));
	}


	// *************************************************************************
	// Getters - Setters
	// *************************************************************************
	public void setMainApp(FxApp app){
		this.mainApp = app;
	}
	public void setView(BorderPane view){
		this.view = view;
	}
	public void setModel(Editor editor){
		this.model = editor;
	}
	public BorderPane getView(){
		return this.view;
	}
}
