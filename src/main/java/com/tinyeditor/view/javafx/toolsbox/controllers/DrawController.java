package com.tinyeditor.view.javafx.toolsbox.controllers;

import com.tinyeditor.view.javafx.FxApp;
import com.tinyeditor.view.javafx.editor.EditorFxController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;

/**
 * Controller for the Draw Module in ToolsBox
 *
 * @since   May 4, 2016
 * @author  Constantin MASSON
 */
public class DrawController{
	private FxApp               mainApp;
	private EditorFxController  editor;
	private DrawPoint           startPoint;
	private DrawPoint           endPoint;
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
		this.startPoint     = new DrawPoint();
		this.endPoint       = new DrawPoint();
		this.isFirstClick   = true;
	}


	// ************************************************************************
	// Main methods
	// ************************************************************************

	/**
	 * Start drawing according to the selected button
	 */
	private void handlerDraw(){
		if(this.midPointLineBtn.isSelected()){
			System.out.println("Draw Line");
		}
		else if(this.midPointCercleBtn.isSelected()){
			System.out.println("Draw Circle");
		}
		else if(this.guptaSproullLineBtn.isSelected()){
			System.out.println("Draw Line AA");
		}
	}


	// ************************************************************************
	// Handler Nested Classes
	// ************************************************************************
	private class MouseReleasedEvent implements EventHandler<MouseEvent>{
		@Override
		public void handle(MouseEvent event) {
			//If first click, set startPoint, otherwise, set endPoint and Draw element.
			if(DrawController.this.isFirstClick){
				DrawController.this.startPoint.x = (int)event.getX();
				DrawController.this.startPoint.y = (int)event.getY();
				DrawController.this.isFirstClick = false;
			}
			else{
				DrawController.this.endPoint.x = (int)event.getX();
				DrawController.this.endPoint.y = (int)event.getY();
				DrawController.this.isFirstClick = true;
				DrawController.this.handlerDraw();
			}
		}
	}

	private class DrawPoint{
		private int x = 0;
		private int y = 0;
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
