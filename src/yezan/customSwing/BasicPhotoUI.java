package yezan.customSwing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

public class BasicPhotoUI extends PhotoUI implements MouseListener
{
	float 	thumbnailScaleFactor=0.16f;
	int   	marginX=20;
	int 	marginY=20;
	
	
	public BasicPhotoUI()
	{
		super();
	}

	public static ComponentUI createUI(JComponent c)
	{
		return new BasicPhotoUI();
	}
	
	public void installUI(JComponent c)
	{
		((BasicPhotoComponent) c).addMouseListener(this);
	}
	
	public void uninstallUI(JComponent c)
	{
		((BasicPhotoComponent) c).removeMouseListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
	
	
	@Override
	public void paint(Graphics g, JComponent c)
	{
		super.paint(g, c);
		System.out.println("=== Begin Painting photoComponent ====");
		Graphics2D g2D = (Graphics2D) g; 		
		
		BasicPhotoComponent bpc = (BasicPhotoComponent) c;
		Image img = bpc.getModel().getImage(); 
				
		System.out.println("Drawing rectangle ("+c.getX()+", "+c.getY()+", "+img.getWidth(null)*thumbnailScaleFactor+", "+
				img.getHeight(null)/thumbnailScaleFactor+")");
		
		Rectangle imageContainer = new Rectangle(c.getX(), 
				c.getY(),
				(int)(img.getWidth(null)*thumbnailScaleFactor+marginX*2),
				(int)(img.getHeight(null)*thumbnailScaleFactor+marginY*2));
		
		g2D.setPaint(new Color(189, 195, 199)); //rgb(236, 240, 241)
	
		g2D.fill(imageContainer);
		
		g2D.drawImage(img, (int) imageContainer.getX()+marginX, 
						   (int) imageContainer.getY()+marginY, 
						   (int) imageContainer.getWidth()-marginX*2,
						   (int) imageContainer.getHeight()-marginY*2,
						   null);
		
		System.out.println("=== End Painting photoComponent ===");
	}
	
}
