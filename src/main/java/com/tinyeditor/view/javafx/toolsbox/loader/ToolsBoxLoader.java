package com.tinyeditor.view.javafx.toolsbox.loader;

import com.tinyeditor.view.javafx.FxApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;

import java.io.IOException;

/**
 * Loader for Tools Panel.
 * ToolsPanel is the box with all possible filters, colors to apply etc.
 *
 * @since   Apr 27, 2016
 * @author  Constantin MASSON
 */
public abstract class ToolsBoxLoader {
	private static final String PATH = "/resources/fxml/toolsbox";


	// *************************************************************************
	// General
	// *************************************************************************

	/**
	 * Load and get the ToolsBox elements.
	 * Models managed by this toolsBox are recovered from parameter.
	 *
	 * @param app           Parent application.
	 * @return              The ToolsBox view.
	 * @throws IOException  if unable to load.
	 */
	public static Accordion getLoadedToolsBox(FxApp app) throws IOException {
		Accordion accordion = new Accordion();
		//Load and add each filters...
		accordion.getPanes().add(loadGeneralFilters(app));
		accordion.getPanes().add(loadConvolutionFilters(app));
		accordion.getPanes().add(loadDitheringFilters(app));
		accordion.getPanes().add(loadColorFilters(app));
		return accordion;
	}


	// *************************************************************************
	// Content Loaders
	// *************************************************************************
	private static TitledPane loadGeneralFilters(FxApp app) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ToolsBoxLoader.class.getResource(PATH+"/generalFilters.fxml"));
		return loader.load(); //Throw IOException if error
	}

	private static TitledPane loadConvolutionFilters(FxApp app) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ToolsBoxLoader.class.getResource(PATH+"/convolutionFilters.fxml"));
		return loader.load();
	}

	private static TitledPane loadDitheringFilters(FxApp app) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ToolsBoxLoader.class.getResource(PATH + "/ditheringFilters.fxml"));
		return loader.load();
	}

	private static TitledPane loadColorFilters(FxApp app) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ToolsBoxLoader.class.getResource(PATH + "/colorFilters.fxml"));
		return loader.load();
	}
}
