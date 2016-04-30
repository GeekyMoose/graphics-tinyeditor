package com.tinyeditor.view.javafx.rootlayout;

import com.tinyeditor.editor.Editor;
import com.tinyeditor.main.Constants;
import com.tinyeditor.view.javafx.FxApp;
import com.tinyeditor.view.javafx.editor.EditorFxController;
import com.tinyeditor.view.javafx.editor.EditorFxLoader;
import com.tinyeditor.view.javafx.toolsbox.loader.ToolsBoxLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Loader for the RootLayout.
 * RootLayout is the main view component of the FX Application.
 *
 * RootLayoutLoader is the first loaded to be called by the application.
 *
 * @since   Apr 30, 2016
 * @author  Constantin MASSON
 */
public class RootLayoutLoader {
	// *************************************************************************
	// Attributes
	// *************************************************************************
	private final String            PATH = "/resources/fxml/app";
	private FxApp                   mainApp;
	private Stage                   primaryStage;
	private BorderPane              rootLayout;
	private RootLayoutController    controller;
	private EditorFxController      editorFxController;


	// *************************************************************************
	// Public access - Loader
	// *************************************************************************

	/**
	 * Load the rootLayout in the given Stage.
	 * Loaded layout will be placed in the primaryStage scene.
	 *
	 * @param mainApp       Application to link with
	 * @param primaryStage  Main Stage where to load rootLayout
	 * @return
	 * @throws IOException  if unable to load rootLayout.
	 */
	public static void load(FxApp mainApp, Stage primaryStage) throws IOException {
		RootLayoutLoader loader = new RootLayoutLoader(mainApp, primaryStage);
		loader.loadRootLayout();
		loader.loadComponents();
	}


	// *************************************************************************
	// Private - Initialization
	// *************************************************************************
	private RootLayoutLoader(FxApp mainApp, Stage primaryStage) throws IOException {
		this.mainApp            = mainApp;
		this.primaryStage       = primaryStage;
		this.editorFxController = EditorFxLoader.load(mainApp, new Editor());
	}

	/**
	 * Initialize the Root Layout and set it to primaryStage.
	 *
	 * @throws IOException  if error occurs.
	 */
	private void loadRootLayout() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource(PATH+"/rootLayout.fxml"));
		this.rootLayout = loader.load();
		//Set controller
		this.controller  = loader.getController();
		this.controller.setMainApp(this.mainApp);
		this.controller.setEditor(this.editorFxController);
		//Load and place scene container
		Scene scene = new Scene(this.rootLayout);
		this.primaryStage.setScene(scene);
	}

	/**
	 * Load all components used and displayed by the application.
	 * Must be called at the end since all other elements must be loaded.
	 *
	 * @throws IOException if error occurs.
	 */
	private void loadComponents() throws IOException {
		//Place components in rootLayout
		this.rootLayout.setRight(ToolsBoxLoader.load(this.mainApp, this.editorFxController));
		this.rootLayout.setCenter(this.editorFxController.getView());

		//Load default image in editor
		this.editorFxController.updateImage(new Image(Constants.DEFAULT_IMG));
	}
}
