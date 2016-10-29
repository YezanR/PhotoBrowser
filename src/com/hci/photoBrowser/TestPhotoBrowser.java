package com.hci.photoBrowser;

import java.awt.Dimension;


import com.hci.photoBrowser.controller.BasicPhotoBrowser;


public class TestPhotoBrowser 
{
	public static void main(String[] args)
	{
		BasicPhotoBrowser photoBrowser = new BasicPhotoBrowser();
		photoBrowser.getView().setSize(new Dimension(1280, 960));
		photoBrowser.setSource("/home/yezan/Pictures");	
	}
}
