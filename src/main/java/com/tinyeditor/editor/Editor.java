package com.tinyeditor.editor;

import com.tinyeditor.modules.filter.convolution.PersonalFilter;

/**
 * Represent the Editor content of the application.
 *
 * @since   Apr 28, 2016
 * @author  Constantin MASSON
 */
public class Editor {
	private ImageEditor imageEditor;
	private PersonalFilter personalFilter;

	public Editor(){
		this.imageEditor    = new ImageEditor();
		this.personalFilter = new PersonalFilter(); //Default perso filter
	}

	// *************************************************************************
	// Getters - Setters
	// *************************************************************************
	public ImageEditor getImageEditor(){
		return this.imageEditor;
	}
	public PersonalFilter getPersonalFilter(){
		return this.personalFilter;
	}
	public void setPersonalFilter(PersonalFilter filter){
		this.personalFilter = filter;
	}
}
