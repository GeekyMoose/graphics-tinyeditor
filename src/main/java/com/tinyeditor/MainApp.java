/*
 * Tiny Editor is a samll version of image editor
 *
 * Date:	March 7, 2016
 * Author:	Constantin MASSON
 */
package main.java.com.tinyeditor;

import main.java.com.tinyeditor.controller.PageController;
import main.java.com.tinyeditor.controller.PersonalFilterDialogController;
import main.java.com.tinyeditor.filter.convolution.PersonalFilter;
import main.java.com.tinyeditor.controller.RootLayoutController;
import main.java.com.tinyeditor.image.ImageEditor;

import java.io.IOException;

import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Modality;
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

	//Controller
	private PageController pageController;

	//Editor elements @TODO to place in better way
	private ImageEditor imageEditor;

	
	// ************************************************************************
	// Initialization - Constructors
	// ************************************************************************
	@Override
	public void start(Stage pStage){
		this.primaryStage = pStage;
		this.primaryStage.setTitle("Computer Graphics - Task 1");
		this.initRootLayout();
		this.initPageView();
		this.primaryStage.show();
		this.imageEditor = new ImageEditor();
	}

	/** Initialize the Root Layout and set it to primaryStage */
	private void initRootLayout(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/main/java/resources/fxml/RootLayout.fxml"));
			this.rootLayout = (BorderPane)loader.load();

			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);

			//Load and place scene container
			Scene scene = new Scene(this.rootLayout);
			this.primaryStage.setScene(scene);
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
	}

	/** Initialize the main page view */
	private void initPageView(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/main/java/resources/fxml/Page.fxml"));
			AnchorPane page = (AnchorPane)loader.load();
			this.rootLayout.setCenter(page);

			this.pageController = loader.getController();
			this.pageController.setMainApp(this);
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
	}

	public static void main(String[] args){
		launch(args);
	}


	// ************************************************************************
	// Dialog - Extra functions
	// ************************************************************************
	public void showPersonalFilterDialog(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/main/java/resources/fxml/PersonalFilterDialog.fxml"));
			AnchorPane pageDialog = (AnchorPane)loader.load();

			//Create and set the dialog page
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Set personal Filter");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(this.primaryStage);
			Scene scene = new Scene(pageDialog);
			dialogStage.setScene(scene);

			PersonalFilterDialogController c = loader.getController();
			c.setDialogStage(dialogStage);
			c.setMainApp(this);
			c.resetValues();
			dialogStage.showAndWait();
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
	}


	// ************************************************************************
	// //@TODO TEMPORARY - Play the role of a model to keep data
	// ************************************************************************
	private PersonalFilter f = new PersonalFilter(); //Default one
	public void setPersonalFilter(PersonalFilter f){
		this.f = f;
	}
	public PersonalFilter getPersonalFilter(){
		return this.f;
	}
	public Stage getPrimaryStage(){
		return this.primaryStage;
	}
	public ImageEditor getImageEditor(){
		return this.imageEditor;
	}
	public void updateImg(){
		if(this.imageEditor.getProcessImage() != null){
			this.pageController.updateImg(this.imageEditor.getProcessImage());
		}
	}
}
