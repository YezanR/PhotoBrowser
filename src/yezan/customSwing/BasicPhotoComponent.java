package yezan.customSwing;


import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BasicPhotoComponent extends JComponent implements ChangeListener
{
	private static final long serialVersionUID = 1L;

	private PhotoModel 		model;
	//Defaullt image to show when the image isn't loaded
	public 	static Image     defaultImage;
	private static String	 defaultImageSrc="ar.jpg"; 
	
	private Point  annotationPosition=null;
	
	public BasicPhotoComponent()
	{
		setModel(new PhotoModel());
		updateUI();
		model.setImage(defaultImageSrc);
		setFocusable(true);
	}
	
	public BasicPhotoComponent(Image image)
	{
		setModel(new PhotoModel());
		updateUI();
		setFocusable(true);
		if ( image != null)
		{
			model.setImage(image);
		}
		else
		{
			model.setImage(defaultImageSrc);
		}
	}
	
	public BasicPhotoComponent(String imageSrc)
	{
		setModel(new PhotoModel());
		updateUI();
		setFocusable(true);
		if ( imageSrc != null)
		{
			model.setImage(imageSrc);
		}
		else
		{
			model.setImage(defaultImageSrc);
		}
	}
	
	public void setUI(PhotoUI ui)
	{
		super.setUI(ui);
	}
	
	public void updateUI()
	{
		setUI((PhotoUI) UIManager.getUI(this));
		invalidate();
	}
	
	public void setModel(PhotoModel m)
	{
		if ( model == null )
		{
			model = m;
			model.addChangeListener(this);
		}
		else
		{
			m.removeChangeListener(this);
		}
	}
	
	public Annotation getAnnotation()
	{
		return model.getAnnotation();
	}
	
	public PhotoModel getModel()
	{
		return model;
	}
	
	public Image getImage()
	{
		return model.getImage();
	}

	@Override 
	public String getUIClassID()
	{
		return PhotoUI.UI_CLASS_ID;
	}
	
	public ArrayList<Point> getStrokes()
	{
		return model.getStrokes();
	}
	
	public boolean isFlipped()
	{
		return model.isFlipped();
	}
	
	@Override
	public void stateChanged(ChangeEvent e) 
	{
		repaint();
	}
	
	public void setImage(Image image)
	{
		model.setImage(image);
	}
	
	public void onDoubleClick()
	{
		flip();
	}
	
	public void onKeyType(char character)
	{
		if ( model.isFlipped() )
		{
			System.out.println("You typed: "+character);
			Annotation annotation = model.getAnnotation();
			if ( annotation == null )
			{
				annotation = new Annotation();
			}
			String oldStr = annotation.getText();
			oldStr += character;
			System.out.println("Key type -> Annotation position = "+annotationPosition);
			annotation.setLocation(annotationPosition);
			annotation.setText(oldStr);
			model.setAnnotation(annotation);
		}
	}
	
	public void onMouseEnter()
	{
		if ( model.isFlipped() )
		{
			setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		}
	}
	
	public void onMouseExit()
	{
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		System.out.println("Mouse exit -> Annotation position "+annotationPosition);
	}
	
	public void onSimpleClick(Point location)
	{
		if ( model.isFlipped() )
		{
			if ( annotationPosition == null)
			{
				annotationPosition = location;
				System.out.println("Simple click -> Annotation position = "+annotationPosition);
			}
		}
	}
	
	public void onMouseDrag(Point location)
	{
		if ( model.isFlipped() )
		{
			model.addStroke(location);
		}
	}
	

	public void addStroke(Point location)
	{
		model.addStroke(location);
	}
	
	public void flip()
	{
		model.setFlipped(!model.isFlipped());
		Cursor cursor = model.isFlipped() ? new Cursor(Cursor.CROSSHAIR_CURSOR): new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(cursor);
	}
}
