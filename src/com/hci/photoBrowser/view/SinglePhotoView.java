package com.hci.photoBrowser.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import yezan.customSwing.controller.BasicPhotoComponent;

public class SinglePhotoView extends PhotoView
{
	private static final long serialVersionUID = 1L;
	
	JPanel rightContainer;
	JPanel leftContainer;
		
	public SinglePhotoView(List<BasicPhotoComponent> photos)
	{
		super(photos);
		
		rightContainer = new JPanel(new GridBagLayout());
		leftContainer = new JPanel(new GridBagLayout());
		nextButton = new JButton("Next");
		previousButton = new JButton("Previous");
		nextButton.setPreferredSize(new Dimension(115, 40));
		previousButton.setPreferredSize(new Dimension(115, 40));
			
		rightContainer.add(nextButton);
		leftContainer.add(previousButton);

		this.setLayout(new BorderLayout());
		this.add(rightContainer, BorderLayout.EAST);
		this.add(leftContainer, BorderLayout.WEST);
	}
	
	@Override
	public void addPhoto(BasicPhotoComponent photo) {
		
	}

	@Override
	public void selectPhoto(BasicPhotoComponent photo)
	{
		if ( focusedPhoto != null )
		{
			this.remove(focusedPhoto);
		}
		if ( photo != null)
		{
			focusedPhoto = photo;
			focusedPhoto.setSize(640, 480);
			this.add(focusedPhoto, BorderLayout.CENTER);
			int index = photos.indexOf(photo);
			previousButton.setVisible(index != 0);
			nextButton.setVisible(index != photos.size() - 1);
		}
		this.revalidate();
	}
	
	
}
