package com.hci.photoBrowser.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import yezan.customSwing.controller.BasicPhotoComponent;

public abstract class PhotoView extends JPanel 
{
	private static final long serialVersionUID = 1L;

	protected BasicPhotoComponent		focusedPhoto;
	List<BasicPhotoComponent>			photos;
	
	public JButton			nextButton=null;
	public JButton			previousButton=null;
	
	public PhotoView(List<BasicPhotoComponent> photos) 
	{
		super();
		if ( photos == null)
		{
			this.photos = new ArrayList<BasicPhotoComponent>();
		}
		else
		{
			this.photos = photos;
		}
	}
	
	public PhotoView()
	{
		super();
		this.photos = new ArrayList<BasicPhotoComponent>();
	}
	
	public abstract void  selectPhoto(BasicPhotoComponent photo);
	
	public abstract void  addPhoto(BasicPhotoComponent photo);
}
