package yezan.sceneGraph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

public class ImageNode extends Node
{
	Image			image;
	/**
	 * if empty, image will be white
	 */
	public boolean	empty=false;

	public ImageNode(Image image)
	{
		super("Image Node "+Node.seqNumber);
		setImage(image);
	}
	
	public ImageNode(String name, Image image)
	{
		super(name);
		setImage(image);
	}
	
	
	@Override
	protected void paint(Graphics g) 
	{
		final Graphics2D g2D = (Graphics2D) g.create();
		
		if ( !empty )
		{
			g2D.drawImage(image, bounds.x, bounds.y, bounds.width, bounds.height, null);
		}
		else
		{
			g2D.setPaint(Color.white);
			g2D.fill(bounds);
		}
		
		g2D.dispose();
	}

	@Override
	public Rectangle getBounds() {
		return bounds;
	}

	@Override
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	public Image getImage()
	{
		return image;
	}
	
	public void setImage(Image image)
	{
		this.image = image;
		if ( bounds == null)
		{
			bounds = new Rectangle(0, 0, 0, 0);
		}
		if ( image != null )
		{
			bounds.width = image.getWidth(null);
			bounds.height = image.getHeight(null);
		}
	}
	
}
