package com.tinyeditor.editor;

/**
 * Represent the Editor content of the application.
 *
 * @since   Apr 28, 2016
 * @author  Constantin MASSON
 */
public class Editor {
	private ImageEditor imageEditor;

	public Editor(){
		this.imageEditor = new ImageEditor();
	}

	public ImageEditor getImageEditor(){
		return this.imageEditor;
	}
}
