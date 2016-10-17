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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

public class BasicPhotoUI extends PhotoUI implements MouseListener, MouseMotionListener, KeyListener
{
	float 			thumbnailScaleFactor=0.16f;
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
		return image != null ? new Dimension((int) (image.getWidth(null)*thumbnailScaleFactor+marginX*2),
				(int) (image.getHeight(null)*thumbnailScaleFactor+marginY*2)) : new Dimension(128+marginX*2, 96+marginY*2);
	}
	
	public Dimension getThumbnailSize(JComponent c)
	{
		BasicPhotoComponent bpc = (BasicPhotoComponent) c;
		Image image = bpc.getImage();
		return image != null ? new Dimension((int) (image.getWidth(null)*thumbnailScaleFactor),
				(int) (image.getHeight(null)*thumbnailScaleFactor)) : new Dimension(128, 96);
	}
	
	public void installUI(JComponent c)
	{
		BasicPhotoComponent bpc = ((BasicPhotoComponent) c);
		bpc.addMouseListener(this);
		bpc.addMouseMotionListener(this);
		bpc.addKeyListener(this);
	}
	
	public void uninstallUI(JComponent c)
	{
		BasicPhotoComponent bpc = ((BasicPhotoComponent) c);
		c.removeMouseListener(this);
		c.removeMouseMotionListener(this);
		c.removeKeyListener(this);
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		BasicPhotoComponent bpc = (BasicPhotoComponent) e.getSource();
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
		System.out.println("Mouse entered "+ ((BasicPhotoComponent) e.getSource()));
		((BasicPhotoComponent) e.getSource()).onMouseEnter();
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		((BasicPhotoComponent) e.getSource()).onMouseExit();
		
	}
	
	
	@Override
	public void paint(Graphics g, JComponent c)
	{
		super.paint(g, c);
		System.out.println("=== Begin Painting photoComponent ====");
		Graphics2D g2D = (Graphics2D) g.create(); 		
		
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

			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2D.setPaint(Color.black);
			// draw annotation ( if it exists )
			Annotation annotation = bpc.getAnnotation();	
			if ( annotation != null && annotation.getLocation() != null)
			{
				System.out.println("Draw string "+annotation.getText()+" at : ("+annotation.getLocation().x
						+", "+annotation.getLocation().y+")");
				g2D.drawString(annotation.getText(), annotation.getLocation().x, annotation.getLocation().y);
				
			}
			//draw strokes ( if there are any )
			ArrayList<Point> strokes = bpc.getStrokes();
			g2D.setStroke(new BasicStroke(strokeWidth));
			Point previousPoint = null;
			for (Point stroke : strokes) 
			{
				if ( previousPoint == null )
				{
					g2D.drawLine(stroke.x, stroke.y, stroke.x, stroke.y);
				}
				else
				{
					g2D.drawLine(previousPoint.x, previousPoint.y, stroke.x, stroke.y);
				}
				previousPoint = stroke;
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
		g2D.dispose();
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

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("Key typed");
		((BasicPhotoComponent) e.getSource()).onKeyType(e.getKeyChar());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
	
}
