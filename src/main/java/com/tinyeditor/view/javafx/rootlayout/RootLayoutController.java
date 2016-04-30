package com.tinyeditor.view.javafx.rootlayout;

import com.tinyeditor.modules.file.FileManager;
import com.tinyeditor.view.javafx.FxApp;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;

/**
 *
 * @since   Apr 28, 2016
 * @author  Constantin MASSON
 */
public class RootLayoutController{
	private FxApp fxApp;


	// ************************************************************************
	// Menu actions
	// ************************************************************************
	@FXML
	private void handleOpenFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Image");
		fileChooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter(
					"Image files (*.jpg, *.jpeg, *.png, *.gif, *.bmp)",
					"*.jpg", "*.jpeg", "*.png", "*.gif", "*.bmp"
				)
		);
		File file = fileChooser.showOpenDialog(fxApp.getPrimaryStage());
		if (file != null) {
			// Load editor
			Image image = FileManager.loadImage(file);
			if (image != null) {
				//Do nothing
			}
		}
	}

	@FXML
	private void handleResetImage(){
	}

	public void setMainApp(FxApp mainApp){
		this.fxApp = mainApp;
	}

}
