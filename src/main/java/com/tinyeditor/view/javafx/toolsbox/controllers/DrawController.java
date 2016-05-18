package com.tinyeditor.view.javafx.toolsbox.controllers;

import com.tinyeditor.modules.draw.GuptaSproull;
import com.tinyeditor.modules.draw.MidpointCircle;
import com.tinyeditor.modules.draw.MidpointLine;
import com.tinyeditor.modules.filling.FloodFill;
import com.tinyeditor.view.javafx.FxApp;
import com.tinyeditor.view.javafx.editor.EditorFxController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;

import java.awt.geom.Point2D;
import java.util.LinkedList;

/**
 * Controller for the Draw Module in ToolsBox
 *
 * @since   May 4, 2016
 * @author  Constantin MASSON
 */
public class DrawController{
	private FxApp                   mainApp;
	private EditorFxController      editor;
	private LinkedList<BuffPoint>   listTmpPoints;
	private boolean                 isFirstClick; //True when draw the startPoint

	@FXML
	private ColorPicker colorPicker;
	@FXML
	private RadioButton midPointLineBtn;
	@FXML
	private RadioButton midPointCercleBtn;
	@FXML
	private RadioButton guptaSproullLineBtn;
	@FXML
	private RadioButton floodFill4AlgoBtn;
	@FXML
	private RadioButton floodFill8AlgoBtn;


	// ************************************************************************
	// Initialization
	// ************************************************************************
	@FXML
	private void initialize(){
		this.isFirstClick = true;
		this.listTmpPoints = new LinkedList<>();
	}


	// ************************************************************************
	// Main methods
	// ************************************************************************

	/**
	 * Start drawing according to the selected button.
	 */
	private void handlerDraw(){
		Image image = this.editor.getModel().getImageEditor().getProcessImage();
		if(this.midPointLineBtn.isSelected()){
			//Exactly 2 points must have been set.
			if(this.listTmpPoints.size() != 2){
				return;
			}
			BuffPoint p1 = this.listTmpPoints.remove();
			BuffPoint p2 = this.listTmpPoints.remove();
			image = MidpointLine.draw(image, p1.x, p1.y, p2.x, p2.y, colorPicker.getValue());
			//Now, list should be empty (We removed the 2 elements)
		}
		else if(this.midPointCercleBtn.isSelected()){
			//Exactly 2 points must have been set.
			if(this.listTmpPoints.size() != 2){
				return;
			}
			BuffPoint p1 = this.listTmpPoints.remove();
			BuffPoint p2 = this.listTmpPoints.remove();
			int radius = (int) Point2D.distance(p1.x, p1.y, p2.x, p2.y);
			image = MidpointCircle.draw(image, p1.x, p1.y, radius, colorPicker.getValue());
		}
		else if(this.guptaSproullLineBtn.isSelected()){
			//Exactly 2 points must have been set.
			if(this.listTmpPoints.size() != 2){
				return;
			}
			BuffPoint p1 = this.listTmpPoints.remove();
			BuffPoint p2 = this.listTmpPoints.remove();
			image = GuptaSproull.draw(image, p1.x, p1.y, p2.x, p2.y, colorPicker.getValue());
		}
		else if(this.floodFill4AlgoBtn.isSelected()){
			BuffPoint p1 = this.listTmpPoints.remove();
			PixelReader pr = image.getPixelReader();
			image = FloodFill.floodFill4(image, p1.x, p1.y, pr.getColor(p1.x,p1.y), colorPicker.getValue());
		}
		else if(this.floodFill8AlgoBtn.isSelected()){
			BuffPoint p1 = this.listTmpPoints.remove();
			PixelReader pr = image.getPixelReader();
			image = FloodFill.floodFill8(image, p1.x, p1.y, pr.getColor(p1.x,p1.y), colorPicker.getValue());
		}
		this.editor.updateImage(image);
	}


	// ************************************************************************
	// Handler Nested Classes
	// ************************************************************************

	/**
	 * Manage a Release click event.
	 */
	private class MouseReleasedEvent implements EventHandler<MouseEvent>{
		@Override
		public void handle(MouseEvent event) {
			//@TODO Manage the change of image ratio
			/*
			 * Currently, the mouse click doesn't work with image displayed in
			 * the ViewImage with a different size than its real.
			 * This is due to the mouseEvent that return the position in the Panel
			 * (Instead of image pixel.).
			 * A function must be added to translate the mouse pos to pixel coordinate.
			 */
			Image img = editor.getImageViewPanel().getImage();
			if(editor.getImageViewPanel().getFitWidth() != img.getWidth()
					|| editor.getImageViewPanel().getFitHeight() != img.getHeight())
			{
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Warning");
				alert.setHeaderText("Operation under development.");
				alert.setContentText("Works only for image not resized in the viewport.");
				alert.showAndWait();
				return;
			}

			//Create the point and place it in list of tmp points.
			BuffPoint p = new BuffPoint();
			p.x = (int)event.getX();
			p.y = (int)event.getY();
			DrawController.this.listTmpPoints.add(p);

			//Call the draw function
			DrawController.this.handlerDraw();
		}
	}

	/**
	 * Manage the action to do when a new radio button is selected.
	 * Clear the list of temporary points.
	 */
	private class BtnChangeListener implements ChangeListener<Toggle>{
		@Override
		public void changed(ObservableValue<? extends Toggle> ob, Toggle oldT, Toggle newT) {
			//Clear the list of points
			DrawController.this.listTmpPoints.clear();
		}
	}


	// *************************************************************************
	// Getters - Setters
	// *************************************************************************
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

		//Recover the toggle group and add a change listener.
		ToggleGroup group = this.midPointLineBtn.getToggleGroup();
		group.selectedToggleProperty().addListener(new BtnChangeListener());
	}




	// *************************************************************************
	// Getters - Setters
	// *************************************************************************

	/**
	 * A really simple point representation for this controller.
	 */
	private class BuffPoint{
		public int x;
		public int y;

		private BuffPoint(){
			this.x = 0;
			this.y = 0;
		}
		private BuffPoint(int x, int y){
			this.x = x;
			this.y = y;
		}
	}
}
