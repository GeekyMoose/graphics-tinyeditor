package com.tinyeditor.view.javafx.toolsbox.loader;

import com.tinyeditor.view.javafx.FxApp;
import com.tinyeditor.view.javafx.editor.EditorFxController;
import com.tinyeditor.view.javafx.toolsbox.controllers.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Loader for Tools Panel.
 * ToolsPanel is the box with all possible filters, colors to apply etc.
 * Each filter view is inflated, loaded (Controller is created) and then,
 * added in the toolsBox component.
 *
 * The loaded ToolsBox component can be recovered in the application.
 *
 * @since   Apr 27, 2016
 * @author  Constantin MASSON
 */
public class ToolsBoxLoader {
	private final String        PATH = "/resources/fxml/toolsbox";
	private FxApp               fxApp;
	private EditorFxController  editor;


	// *************************************************************************
	// Public access - Loader
	// *************************************************************************
	/**
	 * Load the ToolsBox component and return its JX elements.
	 *
	 * @param app           Application to link with
	 * @param c             Element managed by the ToolsBox
	 * @return              ToolsBox FX Component
	 * @throws IOException  if unable to load all ToolsBox components
	 */
	public static TabPane load(FxApp app, EditorFxController c) throws IOException {
		ToolsBoxLoader tbl = new ToolsBoxLoader(app, c);
		return tbl.loadToolsBox();
	}


	// *************************************************************************
	// Private - Initialization
	// *************************************************************************

	/**
	 * Create a new ToolsBoxLoader.
	 * Usually, only one is temporary created through 'load' function therefore,
	 * constructor is private.
	 *
	 * @param app   Application to link with
	 * @param c     Element managed by the ToolsBox
	 */
	private ToolsBoxLoader(FxApp app, EditorFxController c){
		this.fxApp  = app;
		this.editor = c;
	}

	/**
	 * Load and get the ToolsBox elements.
	 * Models managed by this toolsBox are recovered from parameter.
	 *
	 * @return              The ToolsBox view.
	 * @throws IOException  if unable to load.
	 */
	private TabPane loadToolsBox() throws IOException {
		TabPane tabPane = new TabPane();

		//Create the Filters Tab
		Accordion accordion = new Accordion();
		//Load and add each filters...
		accordion.getPanes().add(this.loadGeneralFilters());
		accordion.getPanes().add(this.loadConvolutionFilters());
		accordion.getPanes().add(this.loadDitheringFilters());
		accordion.getPanes().add(this.loadColorFilters());
		//Create the tabPane tab and place it in the tabPane
		Tab filterTab = new Tab();
		filterTab.setText("Filters");
		filterTab.setContent(accordion);
		filterTab.setClosable(false);
		filterTab.setDisable(false);
		tabPane.getTabs().add(filterTab);

		//Create the Draw Tab
		Tab drawTab = new Tab();
		drawTab.setClosable(false);
		drawTab.setDisable(false);
		drawTab.setText("Draw");
		drawTab.setContent(this.loadDrawComponent());
		tabPane.getTabs().add(drawTab);
		return tabPane;
	}


	// *************************************************************************
	// Content Loaders (Filters)
	// *************************************************************************
	private TitledPane loadGeneralFilters() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource(this.PATH+"/generalFilters.fxml"));
		TitledPane elt = loader.load(); //Throw IOException if error
		//Init controller
		GeneralFiltersController c = loader.getController();
		c.setMainApp(this.fxApp);
		c.setEditor(this.editor);
		return elt;
	}

	private TitledPane loadConvolutionFilters() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource(PATH+"/convolutionFilters.fxml"));
		TitledPane elt = loader.load();
		//Init Controller
		ConvolutionFiltersController c = loader.getController();
		c.setMainApp(this.fxApp);
		c.setEditor(this.editor);
		return elt;
	}

	private TitledPane loadDitheringFilters() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource(PATH + "/ditheringFilters.fxml"));
		TitledPane elt = loader.load();
		//Init Controller
		DitheringFiltersController c = loader.getController();
		c.setMainApp(this.fxApp);
		c.setEditor(this.editor);
		return elt;
	}

	private TitledPane loadColorFilters() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource(PATH + "/colorFilters.fxml"));
		TitledPane elt = loader.load();
		//Init Controller
		ColorFiltersController c = loader.getController();
		c.setMainApp(this.fxApp);
		c.setEditor(this.editor);
		return elt;
	}


	// *************************************************************************
	// Content Loaders (Draw)
	// *************************************************************************
	private AnchorPane loadDrawComponent() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource(PATH+"/draw.fxml"));
		AnchorPane elt = loader.load();
		//Init Controller
		DrawController c = loader.getController();
		c.setMainApp(this.fxApp);
		c.setEditor(this.editor);
		return elt;
	}
}
