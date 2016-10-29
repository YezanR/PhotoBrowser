package com.hci.photoBrowser.utils;

import java.io.File;
import java.io.FileFilter;

public class ImageFileFilter implements FileFilter {

	private final String[] acceptedFileExtensions = new String[]{"jpg", "gif", "png"}; 
	
	@Override
	public boolean accept(File file) {
		
		for ( String extension: acceptedFileExtensions)
		{
			if ( file.getName().toLowerCase().endsWith(extension) )
			{
				return true;
			}
		}
		return false;
	}

}
