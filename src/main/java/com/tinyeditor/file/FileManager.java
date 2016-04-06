package main.java.com.tinyeditor.file;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;

public class FileManager {
	public static Image loadImage(File file) {
		try {
			String imageURL = file.toURI().toURL().toString();
			return new Image(imageURL);
		} catch (MalformedURLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not open the image");
			alert.setContentText("Could not open the image from the file:\n"+file.getPath());
			alert.showAndWait();
		}
		return null;
	}

	public static void saveImage(File file, Image image) {
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not save the image");
			alert.setContentText("Could not save the image to the file:\n"+file.getPath());
			alert.showAndWait();
		}
	}
}
