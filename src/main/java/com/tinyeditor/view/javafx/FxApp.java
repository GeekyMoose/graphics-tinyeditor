package com.tinyeditor.view.javafx;

import com.tinyeditor.editor.Editor;
import com.tinyeditor.main.Constants;
import com.tinyeditor.view.javafx.editor.EditorFxController;
import com.tinyeditor.view.javafx.editor.EditorFxLoader;
import com.tinyeditor.view.javafx.rootlayout.RootLayoutController;
import com.tinyeditor.view.javafx.toolsbox.loader.ToolsBoxLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/*
 * Dev note:
 * FxApp play the role of main point for the view but also main controller at
 * the same time.
 * This explain that this class exceptionally init some models and works as a controller.
 */
/**
 * JavaFx Application.
 * Start the application with JavaFX View rendering.
 *
 * @since   Apr 27, 2016
 * @author  Constantin MASSON
 */
public class FxApp extends Application{
	private Stage       primaryStage;
	private BorderPane  rootLayout;


	// *************************************************************************
	// Start / Loading functions
	// *************************************************************************
	@Override
	public void start(Stage pStage) throws IOException {
		this.primaryStage = pStage;
		this.loadRootLayout();
		this.primaryStage.setTitle("TinyEditor");
		this.primaryStage.show();
		this.loadComponents();
	}

	/**
	 * Initialize the Root Layout and set it to primaryStage.
	 *
	 * @throws IOException if error occurs.
	 */
	private void loadRootLayout() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/resources/fxml/app/rootLayout.fxml"));
		this.rootLayout = loader.load();
		//Set controller
		RootLayoutController c = loader.getController();
		c.setMainApp(this);
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
		//Load the Editor component (Create the editor component here)
		EditorFxController editorFxController = EditorFxLoader.load(this, new Editor());

		//Place components in rootLayout
		this.rootLayout.setRight(ToolsBoxLoader.load(this, editorFxController));
		this.rootLayout.setCenter(editorFxController.getView());

		//Load default image in editor
		editorFxController.updateImage(new Image(Constants.DEFAULT_IMG));
	}


	// *************************************************************************
	// Getters - Setters
	// *************************************************************************
	public Stage getPrimaryStage(){
		return this.primaryStage;
	}
}
