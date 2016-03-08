package main.java.com.tinyeditor.views;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;


/**
 * General controller for page (The main editor element)
 *
 * @since	March 8, 2016
 * @author	Constantin MASSON
 */
public class PageController{
	@FXML
	private Slider		brightnessSlider;

	@FXML
	private void initialize(){
	}

	@FXML
	private void handleBrightnessSlider(){
		double value = this.brightnessSlider.getValue();
	}
}
