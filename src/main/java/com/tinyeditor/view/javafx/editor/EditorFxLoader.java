package com.tinyeditor.view.javafx.editor;

import com.tinyeditor.editor.Editor;
import com.tinyeditor.view.javafx.FxApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * Load the Editor component.
 *
 * @since   Apr 29, 2016
 * @author  Constantin MASSON
 */
public class EditorFxLoader {
	private final String        PATH = "/resources/fxml/app";
	private FxApp               fxApp;
	private EditorFxController  controller;
	private Editor              model; //Model managed


	// *************************************************************************
	// Public access - Loader
	// *************************************************************************

	/**
	 * Create the Editor Panel loader and load it.
	 * Return its controller.
	 *
	 * @param app           Parent application where editor is living.
	 * @return              The Editor's controller.
	 * @throws IOException  if unable to create and load.
	 */
	public static EditorFxController load(FxApp app, Editor editor) throws IOException {
		EditorFxLoader loader = new EditorFxLoader(app, editor);
		loader.loadEditorPanel();
		return loader.getController();
	}


	// *************************************************************************
	// Private - Initialization
	// *************************************************************************
	private EditorFxLoader(FxApp app, Editor editor){
		this.model  = editor;
		this.fxApp  = app;
	}

	/**
	 * Load the Editor Panel.
	 * Create and set its controller.
	 *
	 * @throws IOException  if unable to load
	 */
	private void loadEditorPanel() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(EditorFxLoader.class.getResource(PATH+"/editorPanel.fxml"));
		BorderPane elt = loader.load();
		//Set controller
		this.controller = loader.getController();
		this.controller.setMainApp(this.fxApp);
		this.controller.setView(elt);
		this.controller.setModel(this.model);
	}


	// *************************************************************************
	// Getters
	// *************************************************************************
	public EditorFxController getController(){
		return this.controller;
	}
}
