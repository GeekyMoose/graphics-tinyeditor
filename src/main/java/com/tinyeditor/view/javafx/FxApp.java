package com.tinyeditor.view.javafx;

import com.tinyeditor.editor.Editor;
import com.tinyeditor.view.javafx.rootlayout.RootLayoutController;
import com.tinyeditor.view.javafx.toolsbox.loader.ToolsBoxLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

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

	//Model (Since the program must start from FxApp, the model creation is here)
	private Editor      editor;


	// *************************************************************************
	// Start / Loading functions
	// *************************************************************************
	@Override
	public void start(Stage pStage) throws IOException {
		this.primaryStage = pStage;
		this.loadRootLayout();
		this.primaryStage.setTitle("TinyEditor");
		this.primaryStage.show();
		this.initModels();
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
		this.rootLayout.setRight(ToolsBoxLoader.getLoadedToolsBox(this));
	}

	private void initModels(){
		this.editor = new Editor();
	}


	// *************************************************************************
	// Getters - Setters
	// *************************************************************************
	public Stage getPrimaryStage(){
		return this.primaryStage;
	}
	public Editor getEditor(){
		return this.editor;
	}
}
