package com.hci.photoBrowser.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.hci.photoBrowser.model.PhotoBrowserModel;
import com.hci.photoBrowser.utils.PhotoBrowserListener;
import com.hci.photoBrowser.view.BasicPhotoBrowserUI;
import com.hci.photoBrowser.view.GridPhotoView;
import com.hci.photoBrowser.view.SinglePhotoView;

import yezan.customSwing.controller.BasicPhotoComponent;
import yezan.customSwing.view.PhotoUI;

/**
 * A basic controller for the Photo Browser
 * 
 * @author yezan
 *
 */
public class BasicPhotoBrowser implements ChangeListener, PhotoBrowserListener {

	PhotoBrowserModel model;
	BasicPhotoBrowserUI view;

	BasicPhotoComponent currentPhoto;

	public BasicPhotoBrowser() {
		// Register a PhotoUI class for BasicPhotoComponent
		UIManager.put(PhotoUI.UI_CLASS_ID, "yezan.customSwing.BasicPhotoUI");

		setModel(new PhotoBrowserModel());
		view = new BasicPhotoBrowserUI(model.getWindowTitle());

		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		view.importMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
				fileChooser.setFileFilter(filter);
				int returnVal = fileChooser.showOpenDialog(view);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println("You chose file " + fileChooser.getSelectedFile().getPath());
					BasicPhotoComponent bpc = new BasicPhotoComponent(fileChooser.getSelectedFile().getPath());
					view.getPhotoView().add(bpc);
					view.revalidate();
				}
				updateStatusBar(PhotoBrowserModel.importString);
			}
		});

		view.deleteMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateStatusBar(PhotoBrowserModel.deleteString);
			}
		});

		view.exitMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		view.photoViewerRadio.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					updateStatusBar(PhotoBrowserModel.photoViewerString);
					view.setPhotoView(new SinglePhotoView(view.getPhotos()), 0);
					bindControllerButtons();
					currentPhoto = view.getPhotos().get(0);
				}
			}
		});

		view.browserRadio.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					updateStatusBar(PhotoBrowserModel.browserString);
					view.setPhotoView(new GridPhotoView(view.getPhotos()), -1);
					currentPhoto = null;
				}
			}
		});

		view.splitModeRadio.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					updateStatusBar(PhotoBrowserModel.splitModeString);
					bindControllerButtons();
				}
			}
		});

		view.familyToggleBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateStatusBar(PhotoBrowserModel.familyString);
			}
		});

		view.schoolToggleBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateStatusBar(PhotoBrowserModel.schoolString);
			}
		});

		view.vacationToggleBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateStatusBar(PhotoBrowserModel.vacationString);
			}
		});
	}

	/**
	 *  Add action listeners to photo view controller buttons (Next, previous ...)
	 */
	public void bindControllerButtons() {
		
		if (view.getPhotoView().nextButton != null) {
			
			view.getPhotoView().nextButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					int currentIndex = view.getPhotos().indexOf(currentPhoto);
					if (currentIndex > -1) {
						view.selectPhoto(currentIndex+1);
						currentPhoto = view.getPhotos().get(currentIndex+1);
					}
				}

			});
		}

		if (view.getPhotoView().previousButton != null) {
			
			view.getPhotoView().previousButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					int currentIndex = view.getPhotos().indexOf(currentPhoto);
					if (currentIndex > -1) {
						view.selectPhoto(currentIndex-1);
						currentPhoto = view.getPhotos().get(currentIndex-1);
					}
				}

			});
		}
	}

	public PhotoBrowserModel getModel() {
		return model;
	}

	public BasicPhotoBrowserUI getView() {
		return view;
	}

	@Override
	public void onPhotosLoaded() {
		view.setPhotos(model.getPhotos());
		view.setPhotoView(new GridPhotoView(view.getPhotos()), -1);
		currentPhoto = null;
		view.revalidate();
		System.out.println("Photos loaded!");
		updateStatusBar("Photos loaded");
	}

	@Override
	public void onPhotosLoading() {
		System.out.println("Photos loading ...");
		updateStatusBar("Photos loading");
	}

	public void setModel(PhotoBrowserModel model) {
		this.model = model;
		if (model != null) {
			this.model.addChangeListener(this);
			this.model.addPhotoBrowserListener(this);
		} else {
			this.model.removeChangeListener(this);
			this.model.removePhotoBrowserListener(this);
		}
	}

	public void setSource(String directory) {
		model.loadPhotos(directory);
	}

	public void setView(BasicPhotoBrowserUI view) {
		this.view = view;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO: Handle all states, not just window title !
		view.setTitle(model.getWindowTitle());
	}

	public void updateStatusBar(String msg) {
		view.statusBar.setText(msg);
	}

}
