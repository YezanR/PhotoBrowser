package com.hci.photoBrowser.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToggleButton;

import com.hci.photoBrowser.model.PhotoBrowserModel;

import yezan.customSwing.controller.BasicPhotoComponent;

/**
 *  A basic UI for the Photo browser
 * @author yezan
 *
 */
public class BasicPhotoBrowserUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public JPanel mainPanel;
	public JPanel footer;
	public JPanel toolbar;
	JPanel contentPanel;
	PhotoView	  photoView;
	public JMenuBar mainMenuBar;
	public JMenu fileMenu;
	public JMenu viewMenu;
	public JMenuItem importMenuItem;
	public JMenuItem deleteMenuItem;
	public JMenuItem exitMenuItem;
	public JLabel statusBar;
	public JLabel categoryLabel;
	public JRadioButtonMenuItem photoViewerRadio;
	public JRadioButtonMenuItem browserRadio;
	public JRadioButtonMenuItem splitModeRadio;
	public ButtonGroup viewGroup;
	public JToggleButton familyToggleBtn;
	public JToggleButton vacationToggleBtn;
	public JToggleButton schoolToggleBtn;
	public ButtonGroup categoryGroup;

	private final Dimension thisSize = new Dimension(960, 720);
	private final Dimension thisMaxSize = new Dimension(1000, 850);
	private final Dimension thisMinSize = new Dimension(400, 250);
	private final Dimension toolbarSize = new Dimension(150, 450);
	
	/**
	 * List of all photos in the view
	 */
	private ArrayList<BasicPhotoComponent>	photos;

	public BasicPhotoBrowserUI(String windowTitle) {
		super();

		this.setTitle(windowTitle);
		this.setSize(thisSize);
		this.setMinimumSize(thisMinSize);
		this.setMaximumSize(thisMaxSize);

		// Panels initialization
		mainPanel = new JPanel(new BorderLayout());
		footer = new JPanel(new FlowLayout());
		toolbar = new JPanel(new FlowLayout());
		contentPanel = new JPanel();
		// Menu initialization
		mainMenuBar = new JMenuBar();

		fileMenu = new JMenu(PhotoBrowserModel.fileString);
		importMenuItem = new JMenuItem(PhotoBrowserModel.importString);
		deleteMenuItem = new JMenuItem(PhotoBrowserModel.deleteString);
		exitMenuItem = new JMenuItem(PhotoBrowserModel.exitString);
		fileMenu.add(importMenuItem);
		fileMenu.add(deleteMenuItem);
		fileMenu.add(exitMenuItem);

		viewMenu = new JMenu(PhotoBrowserModel.viewString);
		photoViewerRadio = new JRadioButtonMenuItem(PhotoBrowserModel.photoViewerString);
		browserRadio = new JRadioButtonMenuItem(PhotoBrowserModel.browserString);
		splitModeRadio = new JRadioButtonMenuItem(PhotoBrowserModel.splitModeString);
		viewGroup = new ButtonGroup();
		viewGroup.add(photoViewerRadio);
		viewGroup.add(browserRadio);
		viewGroup.add(splitModeRadio);
		viewMenu.add(photoViewerRadio);
		viewMenu.add(browserRadio);
		viewMenu.add(splitModeRadio);

		mainMenuBar.add(fileMenu);
		mainMenuBar.add(viewMenu);

		categoryLabel = new JLabel("Category");

		familyToggleBtn = new JToggleButton(PhotoBrowserModel.familyString);
		familyToggleBtn.setPreferredSize(new Dimension(120, 30));
		vacationToggleBtn = new JToggleButton(PhotoBrowserModel.vacationString);
		vacationToggleBtn.setPreferredSize(new Dimension(120, 30));
		schoolToggleBtn = new JToggleButton(PhotoBrowserModel.schoolString);
		schoolToggleBtn.setPreferredSize(new Dimension(120, 30));
		categoryGroup = new ButtonGroup();
		categoryGroup.add(familyToggleBtn);
		categoryGroup.add(vacationToggleBtn);
		categoryGroup.add(schoolToggleBtn);

		toolbar.add(categoryLabel);
		toolbar.add(familyToggleBtn);
		toolbar.add(vacationToggleBtn);
		toolbar.add(schoolToggleBtn);

		JButton textTool = new JButton("Text");
		toolbar.add(textTool);

		toolbar.setPreferredSize(toolbarSize);
		toolbar.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(189, 195, 199)));

		// Status bar init
		statusBar = new JLabel("Status bar");
		footer.add(statusBar);
		footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(189, 195, 199)));

		mainPanel.add(mainMenuBar, BorderLayout.NORTH);
		mainPanel.add(footer, BorderLayout.SOUTH);
		mainPanel.add(toolbar, BorderLayout.EAST);
		//mainPanel.add(contentPanel, BorderLayout.CENTER);

		this.getContentPane().add(mainPanel);
		this.setVisible(true);
		
		photos = new ArrayList<BasicPhotoComponent>();
	}

	public void addPhoto(Image image)
	{
		BasicPhotoComponent photo = new BasicPhotoComponent(image);
		photos.add(photo);
		photoView.addPhoto(photo);
		contentPanel.revalidate();
	}

	public JPanel getContentPanel()
	{
		return contentPanel;
	}
	
	public ArrayList<BasicPhotoComponent> getPhotos() 
	{
		return photos;
	}
	
	public PhotoView getPhotoView()
	{
		return photoView;
	}
	
	public void setPhotos(ArrayList<BasicPhotoComponent> photos) {
		this.photos = photos;
	}
		
	/**
	 * Set List of photos by creating photo components from a list of images
	 * @param images
	 */
	public void setPhotos(List<Image> images)
	{
		for (Image image : images)
		{
			BasicPhotoComponent photo = new BasicPhotoComponent(image);
			photos.add(photo);
		}
	}
	
	
	public void setPhotoView(PhotoView view, int selectedPhotoIndex)
	{
		if ( photoView != null)
		{
			mainPanel.remove(photoView);
		}
		photoView = view;
		// remove all content view
		//contentPanel.removeAll();
		// Set new photo view
		//contentPanel.add(photoView);
		mainPanel.add(photoView, BorderLayout.CENTER);
		if ( photoView != null)
		{
			if ( selectedPhotoIndex != -1)
			{
				photoView.selectPhoto(photos.get(selectedPhotoIndex));
			}
		}
		// refresh
		mainPanel.repaint();
	}
	
	public void selectPhoto(BasicPhotoComponent photo)
	{
		photoView.selectPhoto(photo);
		mainPanel.revalidate();
	}
	
	public void selectPhoto(int indexofPhoto)
	{
		try
		{
			photoView.selectPhoto(photos.get(indexofPhoto));
			mainPanel.revalidate();
		}
		catch (IndexOutOfBoundsException | NullPointerException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	
	
}
