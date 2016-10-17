package com.hci.photoBrowser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import yezan.customSwing.*;

public class PhotoBrowser 
{
	// View 
	private JFrame			mainWindow;
	private JPanel 			mainPanel;
	private JPanel 			footer;
	private JPanel 			toolbar;
	private JPanel			contentPanel;
	private JMenuBar		mainMenuBar;
	private JMenu			fileMenu;
	private JMenu			viewMenu;
	private JMenuItem   	importMenuItem;
	private JMenuItem   	deleteMenuItem;
	private JMenuItem		exitMenuItem;
	private JLabel			statusBar;
	private JLabel			categoryLabel;
    private JRadioButtonMenuItem 	photoViewerRadio;
    private JRadioButtonMenuItem 	browserRadio;
    private JRadioButtonMenuItem 	splitModeRadio;
    private ButtonGroup  			viewGroup;
    private JToggleButton			familyToggleBtn;
    private JToggleButton			vacationToggleBtn;
    private JToggleButton			schoolToggleBtn;
    private ButtonGroup				categoryGroup;
  
    private final Dimension mainWindowSize = new Dimension(960, 720);
    private final Dimension mainWindowMaxSize = new Dimension(1000, 850);
    private final Dimension mainWindowMinSize = new Dimension(400, 250);
    private final Dimension toolbarSize = new Dimension(150, 450);

	// Model 
	public final static String windowTitle = "Photo Browser";
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
	

	// Initialize and draw UI elements (View)
	private void setupUI()
	{
		// main window initialization
		mainWindow = new JFrame();
		mainWindow.setTitle(windowTitle);
		mainWindow.setSize(mainWindowSize);
		mainWindow.setMinimumSize(mainWindowMinSize);
		mainWindow.setMaximumSize(mainWindowMaxSize);

		
		// Panels initialization
		mainPanel = new JPanel(new BorderLayout());
		footer    = new JPanel(new FlowLayout());
		toolbar	  = new JPanel(new FlowLayout());
		contentPanel = new JPanel();
		// Menu initialization
		mainMenuBar = new JMenuBar();
		
		fileMenu = new JMenu(fileString);
		importMenuItem = new JMenuItem(importString);
		deleteMenuItem = new JMenuItem(deleteString);
		exitMenuItem = new JMenuItem(exitString);
		fileMenu.add(importMenuItem);
		fileMenu.add(deleteMenuItem);
		fileMenu.add(exitMenuItem);
		
		viewMenu = new JMenu(viewString);
		photoViewerRadio = new JRadioButtonMenuItem(photoViewerString);
		browserRadio = new JRadioButtonMenuItem(browserString);
		splitModeRadio = new JRadioButtonMenuItem(splitModeString);
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
		
		familyToggleBtn = new JToggleButton(familyString);
		familyToggleBtn.setPreferredSize(new Dimension(120, 30));
		vacationToggleBtn = new JToggleButton(vacationString);
		vacationToggleBtn.setPreferredSize(new Dimension(120, 30));
		schoolToggleBtn = new JToggleButton(schoolString);
		schoolToggleBtn.setPreferredSize(new Dimension(120, 30));
		categoryGroup = new ButtonGroup();
		categoryGroup.add(familyToggleBtn);
		categoryGroup.add(vacationToggleBtn);
		categoryGroup.add(schoolToggleBtn);
		
		toolbar.add(categoryLabel);
		toolbar.add(familyToggleBtn);
		toolbar.add(vacationToggleBtn);
		toolbar.add(schoolToggleBtn);
		
		toolbar.setPreferredSize(toolbarSize);
		toolbar.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(189, 195, 199)));
		
		BasicPhotoComponent bpc  = new BasicPhotoComponent();
		BasicPhotoComponent bpc1 = new BasicPhotoComponent();
		BasicPhotoComponent bpc2 = new BasicPhotoComponent();
		
		contentPanel.add(bpc);
		contentPanel.add(bpc1);
		contentPanel.add(bpc2);
		
		// Status bar init
		statusBar = new JLabel("Status bar");
		footer.add(statusBar);
		footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(189, 195, 199)));
		
		mainPanel.add(mainMenuBar, BorderLayout.NORTH);
		mainPanel.add(footer, BorderLayout.SOUTH);
		mainPanel.add(toolbar, BorderLayout.EAST);
		mainPanel.add(contentPanel, BorderLayout.CENTER);
		
				
		mainWindow.getContentPane().add(mainPanel);
		mainWindow.setVisible(true);
	}
	
	// set actions and listeners to UI elements
	private void setupUIActions()
	{
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		importMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter( "JPG & GIF Images", "jpg", "gif");
				fileChooser.setFileFilter(filter);				
				int returnVal = fileChooser.showOpenDialog(mainWindow);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println("You chose file "+fileChooser.getSelectedFile().getPath());
					BasicPhotoComponent bpc = new BasicPhotoComponent(fileChooser.getSelectedFile().getPath());
					contentPanel.add(bpc);
				}
				updateStatusBar(importString);
			}
		});
		
		deleteMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateStatusBar(deleteString);
			}
		});
		
		exitMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		photoViewerRadio.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if ( e.getStateChange() == 1)
				{
					updateStatusBar(photoViewerString);
				}
			}
		});
		
		browserRadio.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if ( e.getStateChange() == 1)
				{
					updateStatusBar(browserString);
				}
			}
		});
		
		splitModeRadio.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if ( e.getStateChange() == 1)
				{
					updateStatusBar(splitModeString);
				}
			}
		});
		
		familyToggleBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateStatusBar(familyString);
			}
		});
		
		schoolToggleBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateStatusBar(schoolString);
			}
		});
		
		vacationToggleBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateStatusBar(vacationString);
			}
		});
	}
	
	private void updateStatusBar(String msg)
	{
		statusBar.setText(msg);
	}
	
	public PhotoBrowser()
	{
		setupUI();
		setupUIActions();
	}
	
	
	
	public static void main(String[] args)
	{
		UIManager.put(PhotoUI.UI_CLASS_ID, "yezan.customSwing.BasicPhotoUI");
		
		PhotoBrowser pb = new PhotoBrowser();
	}
}
