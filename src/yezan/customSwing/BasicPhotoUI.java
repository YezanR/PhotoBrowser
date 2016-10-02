package yezan.customSwing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

public class BasicPhotoUI extends PhotoUI implements MouseListener, MouseMotionListener
{
	float 	thumbnailScaleFactor=0.16f;
	int   			marginX=20;
	int 			marginY=20;
	int				strokeWidth=5;
	int				strokeHeight=5;
	
	static Dimension defaultSize = new Dimension(96, 54);
	
	public BasicPhotoUI()
	{
		super();
	}

	public static ComponentUI createUI(JComponent c)
	{
		//((BasicPhotoComponent) c).setSize(defaultSize);  
		return new BasicPhotoUI();
	}
	
	public Dimension getPreferredSize(JComponent c)
	{
		BasicPhotoComponent bpc = (BasicPhotoComponent) c;
		Image image = bpc.getImage();
		return image != null ? new Dimension((int) (image.getWidth(null)*thumbnailScaleFactor/*+marginX*2*/),
				(int) (image.getHeight(null)*thumbnailScaleFactor/*+marginY*2*/)) : new Dimension(128, 96);
	}
	
	public void installUI(JComponent c)
	{
		((BasicPhotoComponent) c).addMouseListener(this);
		((BasicPhotoComponent) c).addMouseMotionListener(this);
	}
	
	public void uninstallUI(JComponent c)
	{
		((BasicPhotoComponent) c).removeMouseListener(this);
		((BasicPhotoComponent) c).removeMouseMotionListener(this);
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		BasicPhotoComponent bpc = (BasicPhotoComponent) e.getComponent();
		// case of double click
		if ( e.getClickCount() == 2)
		{
			// notify component of double click
			bpc.onDoubleClick();
		}
		// case of simple click
		else if ( e.getClickCount() == 1)
		{
			bpc.onSimpleClick(e.getPoint());
		}
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		System.out.println("Mouse entered "+ ((BasicPhotoComponent) e.getSource()).getSize());
		((BasicPhotoComponent) e.getSource()).onMouseEnter();
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		((BasicPhotoComponent) e.getSource()).onMouseExit();
	}
	
	
//	@Override
//	public void paint(Graphics g, JComponent c)
//	{
//		super.paint(g, c);
//		System.out.println("=== Begin Painting photoComponent ====");
//		Graphics2D g2D = (Graphics2D) g; 		
//		
//		Dimension totalSize = getPreferredSize(c);
//		// calculate thumbnail dimensions
//		Dimension thumbnailSize = new Dimension(totalSize.width-marginX*2, totalSize.height-marginY*2);
//
//		System.out.println("Drawing Image container at "+c.getLocation()+"; Dimensions : "+totalSize);
//		Rectangle imageContainer = new Rectangle(c.getLocation(), getPreferredSize(c));
//		
//		// Draw image container
//		g2D.setPaint(new Color(189, 195, 199)); //rgb(236, 240, 241)
//		g2D.fill(imageContainer);
//		
//		// calculate thumbnail or white rectangle location, in general it's the content to be drawn inside
//		// the image container
//		Point thumbnailLocation = new Point(imageContainer.getLocation().x + marginX,
//				 imageContainer.getLocation().y + marginY);
//		
//		// if the photo is flipped then draw white background
//		if ( ((BasicPhotoComponent) c).isFlipped() )
//		{
//			g2D.setPaint(Color.white);
//			g2D.fill(new Rectangle(thumbnailLocation, thumbnailSize));
//		}
//		// else draw image
//		else
//		{
//			g2D.drawImage(((BasicPhotoComponent) c).getImage(), thumbnailLocation.x, 
//					   	  thumbnailLocation.y, 
//					      thumbnailSize.width,
//					      thumbnailSize.height,
//					      null);
//		}
//		System.out.println("=== End Painting photoComponent ===");
//	}
	
	@Override
	public void paint(Graphics g, JComponent c)
	{
		super.paint(g, c);
		System.out.println("=== Begin Painting photoComponent ====");
		Graphics2D g2D = (Graphics2D) g; 		
		
		// size of the component
		Dimension totalSize =  c.getSize();
		// calculate thumbnail dimensions
		Dimension thumbnailSize = getPreferredSize(c);

		System.out.println("Drawing Image container at "+c.getLocation()+"; Dimensions : "+totalSize);
		Rectangle imageContainer = new Rectangle(c.getLocation(), totalSize);
		
		// Draw image container
		g2D.setPaint(new Color(189, 195, 199)); //rgb(236, 240, 241)
		g2D.fill(imageContainer);
		
		// calculate thumbnail or white rectangle location, in general it's the content to be drawn inside
		// the image container
		Point thumbnailLocation = new Point(totalSize.width/2-thumbnailSize.width/2,
											totalSize.height/2-thumbnailSize.height/2);
		
		BasicPhotoComponent bpc = (BasicPhotoComponent) c;
		// if the photo is flipped then draw white background
		if ( bpc.isFlipped() )
		{
			g2D.setPaint(Color.white);
			g2D.fill(new Rectangle(thumbnailLocation, thumbnailSize));
			// draw strokes ( if there are any )
			ArrayList<Point> strokes = bpc.getStrokes();
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2D.setPaint(Color.black);
			g2D.setStroke(new BasicStroke(strokeWidth));
			for (Point stroke : strokes) 
			{
				g2D.drawLine(stroke.x, stroke.y, stroke.x, stroke.y);
			}
		}
		// else draw image
		else
		{
			g2D.drawImage( bpc.getImage(), thumbnailLocation.x, 
					   	  thumbnailLocation.y, 
					      thumbnailSize.width,
					      thumbnailSize.height,
					      null);
		}
		System.out.println("=== End Painting photoComponent ===");
	}

	@Override
	public void mouseDragged(MouseEvent e) 
	{
		((BasicPhotoComponent) e.getSource()).onMouseDrag(e.getPoint()); 
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
	
	}
	
}
