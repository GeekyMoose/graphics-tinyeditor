package com.tinyeditor.view.javafx;

import com.tinyeditor.view.javafx.rootlayout.RootLayoutLoader;
import javafx.application.Application;
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
	private Stage primaryStage;


	// *************************************************************************
	// Start / Loading functions
	// *************************************************************************
	@Override
	public void start(Stage pStage) throws IOException {
		this.primaryStage = pStage;
		RootLayoutLoader.load(this, this.primaryStage);
		this.primaryStage.setTitle("TinyEditor");
		this.primaryStage.show();
	}


	// *************************************************************************
	// Getters - Setters
	// *************************************************************************
	public Stage getPrimaryStage(){
		return this.primaryStage;
	}
}
