package com.tinyeditor.view.javafx.toolsbox.dialogs;

import com.tinyeditor.view.javafx.FxApp;
import com.tinyeditor.view.javafx.editor.EditorFxController;
import com.tinyeditor.view.javafx.toolsbox.controllers.AbsFilterController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Loader for Personal Filter.
 *
 * @since   Apr 30, 2016
 * @author  Constantin MASSON
 */
public class PersoFilterLoader extends AbsFilterController{
	private final String            PATH = "/resources/fxml/toolsbox/dialogs";
	private PersoFilterController   controller;



	// *************************************************************************
	// Public access - Loader
	// *************************************************************************

	/**
	 * Load the dialog stage and return its controller.
	 * Dialog is displayed in the meanwhile.
	 *
	 *
	 * @param mainApp       Application where filter is running.
	 * @return              Its controller.
	 * @throws IOException  if unable to load.
	 */
	public static PersoFilterController load(FxApp mainApp, EditorFxController c) throws IOException {
		PersoFilterLoader filter = new PersoFilterLoader(mainApp, c);
		filter.loadDialog();
		return filter.getController();
	}


	// *************************************************************************
	// Private - Initialization
	// *************************************************************************
	private PersoFilterLoader(FxApp mainApp, EditorFxController editorFxController){
		this.mainApp    = mainApp;
		this.editor     = editorFxController;
	}

	private void loadDialog() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource(PATH+"/personalFilterDialog.fxml"));
		AnchorPane elt = loader.load();

		//Create and set the dialog page
		Scene scene         = new Scene(elt);
		Stage dialogStage   = new Stage();
		dialogStage.setTitle("Set personal Filter");
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(this.mainApp.getPrimaryStage());
		dialogStage.setScene(scene);

		this.controller = loader.getController();
		this.controller.setDialogStage(dialogStage);
		this.controller.setMainApp(this.mainApp);
		this.controller.setEditor(this.editor);
	}


	// *************************************************************************
	// Getters - Setters
	// *************************************************************************
	private PersoFilterController getController(){
		return this.controller;
	}
}
