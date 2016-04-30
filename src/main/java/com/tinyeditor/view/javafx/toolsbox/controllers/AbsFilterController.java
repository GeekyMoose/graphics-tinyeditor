package com.tinyeditor.view.javafx.toolsbox.controllers;

import com.tinyeditor.modules.filter.asset.ImageFilter;
import com.tinyeditor.view.javafx.FxApp;
import com.tinyeditor.view.javafx.editor.EditorFxController;
import javafx.scene.image.Image;

/**
 * Generic class for all Filter Controller.
 * All filter controller are linked with their FxApplication and the Editor their work on.
 *
 * @since   Apr 30, 2016
 * @author  Constantin MASSON
 */
public abstract class AbsFilterController {
	// ************************************************************************
	// Attributes
	// ************************************************************************
	protected FxApp               mainApp; //Parent FxApplication
	protected EditorFxController  editor; //Data managed (Through another controller)


	// ************************************************************************
	// Functions
	// ************************************************************************

	/**
	 * Apply a specific filter to the image and update model and display
	 *
	 * @param filter filter to apply
	 */
	protected void applyFilter(ImageFilter filter){
		Image currentImg = this.editor.getModel().getImageEditor().getProcessImage();
		if(currentImg == null){
			return;
		}
		this.editor.updateImage(filter.applyFilter(currentImg));
	}


	// ************************************************************************
	// Getters - Setters
	// ************************************************************************
	/**
	 * Set the linked mainApp.
	 * Application is the main FX component running the program.
	 *
	 * @param mainApp   Parent application to link with this controller
	 */
	public void setMainApp(FxApp mainApp){
		this.mainApp = mainApp;
	}

	/**
	 * Set the editor managed by the filters.
	 * Parameter is a controller, however, it is used as the common model element
	 * of a controller.
	 *
	 * @param editorController Element managed by the filter.
	 */
	public void setEditor(EditorFxController editorController){
		this.editor = editorController;
	}
}
