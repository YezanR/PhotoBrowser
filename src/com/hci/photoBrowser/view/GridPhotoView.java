package com.hci.photoBrowser.view;

import java.util.List;

import yezan.customSwing.controller.BasicPhotoComponent;

public class GridPhotoView extends PhotoView {

	private static final long serialVersionUID = 1L;

	public GridPhotoView(List<BasicPhotoComponent> photos)
	{
		super(photos);
		for ( BasicPhotoComponent photo : photos)
		{
			this.add(photo);
		}
	}
	
	@Override
	public void selectPhoto(BasicPhotoComponent photo) 
	{

	}
	
	@Override
	public void addPhoto(BasicPhotoComponent photo)
	{
		this.add(photo);
		this.revalidate();
	}

}
