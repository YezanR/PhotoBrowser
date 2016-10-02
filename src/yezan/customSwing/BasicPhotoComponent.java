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

	private PhotoModel model;
	
	public BasicPhotoComponent()
	{
		setModel(new PhotoModel());
		updateUI();
		model.setImage("ar.jpg");
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
	
	public void onDoubleClick()
	{
		flip();
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
	}
	
	public void onSimpleClick(Point location)
	{
		if ( model.isFlipped() )
		{
			model.addStroke(location);
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
