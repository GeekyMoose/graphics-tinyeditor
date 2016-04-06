package main.java.com.tinyeditor.controller;

import main.java.com.tinyeditor.file.FileManager;
import main.java.com.tinyeditor.MainApp;

import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

public class RootLayoutController{
	private MainApp mainApp;
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
		File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
		if (file != null) {
			// Load image
			Image image = FileManager.loadImage(file);
			if (image != null) {
				this.mainApp.getImageEditor().setImage(image);
				this.mainApp.updateImg();
			}
		}
	}

	@FXML
	private void handleResetImage(){
		this.mainApp.getImageEditor().reset();
		this.mainApp.updateImg();
	}

	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
	}
}
