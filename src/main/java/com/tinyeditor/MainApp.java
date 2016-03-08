/*
 * Tiny Editor is a samll version of image editor
 *
 * Date:	March 7, 2016
 * Author:	Constantin MASSON
 */
package main.java.com.tinyeditor;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;


/**
 * Entry point for application.
 * JavaFX display.
 *
 * @since	March 7, 2016
 * @author	Constantin MASSON
 */
public class MainApp extends Application{
	private Stage		primaryStage;
	private BorderPane	rootLayout;

	@Override
	public void start(Stage pStage){
		this.primaryStage = pStage;
		this.primaryStage.setTitle("Computer Graphics - Task 1");
		this.initRootLayout();
		this.initPageView();
		this.primaryStage.show();
	}

	/**
	 * Initialize the Root Layout and set it to primaryStage
	 */
	private void initRootLayout(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("views/RootLayout.fxml"));
			this.rootLayout = (BorderPane)loader.load();

			//Load and place scene container
			Scene scene = new Scene(this.rootLayout);
			this.primaryStage.setScene(scene);
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
	}

	private void initPageView(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("views/Page.fxml"));
			AnchorPane page = (AnchorPane)loader.load();
			this.rootLayout.setCenter(page);
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
	}

	public static void main(String[] args){
		launch(args);
	}
}
