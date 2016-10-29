package com.hci.photoBrowser.utils;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Class providing useful functions for processing and handling images  
 * @author yezan
 *
 */
public class PhotoBrowserUtilities 
{
	
	public static Image readImage(File file)
	{
		try 
		{
			return ImageIO.read(file);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return null;
		}
	}
}
