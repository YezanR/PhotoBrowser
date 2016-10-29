package com.hci.photoBrowser.model;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.hci.photoBrowser.utils.ImageFileFilter;
import com.hci.photoBrowser.utils.PhotoBrowserListener;
import com.hci.photoBrowser.utils.PhotoBrowserUtilities;

/**
 * Class for the abstract or model of Photo Browser
 * @author yezan
 *
 */
public class PhotoBrowserModel 
{
	//Constants
	public final static String fileString = "File";
	
	public final static String viewString = "View";
	
	public final static String importString = "Import";
	public final static String deleteString = "Delete";
	public final static String exitString = "Exit";
	public final static String photoViewerString = "PhotoViewer (Default)";
	public final static String browserString = "Browser";
	public final static String splitModeString = "Split mode";
	public final static String familyString = "Family";
	public final static String vacationString = "Vacation";
	public final static String schoolString = "School";
	
	String 					windowTitle = "Photo Browser";
	ArrayList<Image>		photos;
	
	ArrayList<PhotoBrowserListener>	 photoBrowserListeners;
	ArrayList<ChangeListener>		 changeListeners;

	public PhotoBrowserModel()
	{
		photos = new ArrayList<Image>();
		photoBrowserListeners = new ArrayList<PhotoBrowserListener>();
		changeListeners	= new ArrayList<ChangeListener>();
	}
	
	public void addChangeListener(ChangeListener l)
	{
		changeListeners.add(l);
	}
	
	public void addPhotoBrowserListener(PhotoBrowserListener l)
	{
		photoBrowserListeners.add(l);
	}
	
	public void fireStateChanged()
	{
		ChangeEvent event = new ChangeEvent(this);
		for (ChangeListener changeListener : changeListeners) {
			changeListener.stateChanged(event);
		}
	}
	
	public ArrayList<Image> getPhotos()
	{
		return photos;
	}
	
	public String getWindowTitle() {
		return windowTitle;
	}

	public void loadPhotos(String directory) 
	{
		try
		{
			notifyPhotosLoading();
			File photosDirectory = new File(directory);
			if ( photosDirectory.isDirectory() )
			{
				for ( File file: photosDirectory.listFiles(new ImageFileFilter()))
				{
					if ( file.isDirectory() )
					{
						loadPhotos(file.getName());
					}
					else
					{
						Image image = PhotoBrowserUtilities.readImage(file);
						photos.add(image);
					}
				}
				notifyPhotosLoaded();
			}
			else
			{
				//TODO: Handle error
				System.out.println(directory+" is not a directory!");
			}
		}
		catch ( NullPointerException e)
		{
			System.out.println(e.getMessage());
		}
	}

	public void notifyPhotosLoaded()
	{
		for ( PhotoBrowserListener l : photoBrowserListeners)
		{
			l.onPhotosLoaded();
		}
	}
	
	public void notifyPhotosLoading()
	{
		for ( PhotoBrowserListener l : photoBrowserListeners)
		{
			l.onPhotosLoading();
		}
	}
	
	
	
	public void removeChangeListener(ChangeListener l)
	{
		changeListeners.remove(l);
	}
	
	public void removePhotoBrowserListener(PhotoBrowserListener l)
	{
		photoBrowserListeners.remove(l);
	}

	public void setWindowTitle(String windowTitle) 
	{
		this.windowTitle = windowTitle;
		fireStateChanged();
	}
	
	
	
}
