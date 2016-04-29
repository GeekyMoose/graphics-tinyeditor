package com.tinyeditor.view.javafx.editor;

import com.tinyeditor.view.javafx.FxApp;
import javafx.fxml.FXML;
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

	@FXML
	private ImageView imageView;


	// *************************************************************************
	// Getters - Setters
	// *************************************************************************
	public void setMainApp(FxApp app){
		this.mainApp = app;
	}
	public void setView(BorderPane view){
		this.view = view;
	}
	public BorderPane getView(){
		return this.view;
	}
}
