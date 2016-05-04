package com.tinyeditor.view.javafx.toolsbox.controllers;

import com.tinyeditor.modules.draw.GuptaSproull;
import com.tinyeditor.modules.draw.MidpointCircle;
import com.tinyeditor.modules.draw.MidpointLine;
import com.tinyeditor.view.javafx.FxApp;
import com.tinyeditor.view.javafx.editor.EditorFxController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.awt.geom.Point2D;

/**
 * Controller for the Draw Module in ToolsBox
 *
 * @since   May 4, 2016
 * @author  Constantin MASSON
 */
public class DrawController{
	private FxApp               mainApp;
	private EditorFxController  editor;
	private int                 x1; //Start point
	private int                 y1;
	private int                 x2; //End point
	private int                 y2;
	private boolean             isFirstClick; //True when draw the startPoint

	@FXML
	private ColorPicker colorPicker;
	@FXML
	private RadioButton midPointLineBtn;
	@FXML
	private RadioButton midPointCercleBtn;
	@FXML
	private RadioButton guptaSproullLineBtn;


	// ************************************************************************
	// Initialization
	// ************************************************************************
	@FXML
	private void initialize(){
		this.isFirstClick = true;
	}


	// ************************************************************************
	// Main methods
	// ************************************************************************

	/**
	 * Start drawing according to the selected button
	 */
	private void handlerDraw(){
		Image image = this.editor.getModel().getImageEditor().getProcessImage();
		if(this.midPointLineBtn.isSelected()){
			image = MidpointLine.draw(image, x1, y1, x2, y2, colorPicker.getValue());
		}
		else if(this.midPointCercleBtn.isSelected()){
			int radius = (int) Point2D.distance(x1, y1, x2, y2);
			image = MidpointCircle.draw(image, x1, y1, radius, colorPicker.getValue());
		}
		else if(this.guptaSproullLineBtn.isSelected()){
			image = GuptaSproull.draw(image, x1, y1, x2, y2, colorPicker.getValue());
		}
		this.editor.updateImage(image);
	}


	// ************************************************************************
	// Handler Nested Classes
	// ************************************************************************
	private class MouseReleasedEvent implements EventHandler<MouseEvent>{
		@Override
		public void handle(MouseEvent event) {
			//If first click, set startPoint, otherwise, set endPoint and Draw element.
			if(DrawController.this.isFirstClick){
				DrawController.this.x1 = (int)event.getX();
				DrawController.this.y1 = (int)event.getY();
				DrawController.this.isFirstClick = false;
			}
			else{
				DrawController.this.x2 = (int)event.getX();
				DrawController.this.y2 = (int)event.getY();
				DrawController.this.isFirstClick = true;
				DrawController.this.handlerDraw();
			}
		}
	}


	// ************************************************************************
	// Getters - Setters
	// ************************************************************************
	/**
	 * Set the linked mainApp.
	 * Application is the main FX component running the program.
	 *
	 * @param mainApp Parent application to link with this controller
	 */
	public void setMainApp(FxApp mainApp){
		this.mainApp = mainApp;
	}

	/**
	 * Set the editor where to draw.
	 *
	 * @param editorController Element managed by the filter.
	 */
	public void setEditor(EditorFxController editorController){
		this.editor = editorController;

		//Start listening to this editor
		this.editor.getImageViewPanel().addEventHandler(
				MouseEvent.MOUSE_RELEASED,
				new MouseReleasedEvent()
		);
	}
}
