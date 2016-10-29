package yezan.sceneGraph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.font.TextLayout;

public class TextNode extends Node
{
	String		   text;
	
	public  int	   characterWidthBound=10;
	public  int	   characterHeightBound=20;
	
	Point 		   location;
	
	public TextNode(String text)
	{
		super(text);
		if ( bounds == null)
		{
			bounds = new Rectangle(0, 0, 0, 0);
		}
		location = new Point(0, 0);
		setText(text);
	}
	
	public TextNode(String text, Point location)
	{
		super(text);
		if ( location == null )
		{
			bounds = new Rectangle(0, 0, 0, 0);
		}
		bounds = new Rectangle(location.x, location.y, 0, 0);
		this.location = location;
		setText(text);
	}
	
	@Override
	protected void paint(Graphics g) 
	{
		if ( getBounds() != null)
		{
			final Graphics2D g2D = (Graphics2D) g.create();
			
			g2D.setStroke(this.stroke);
			g2D.setPaint(this.paint == null ? Color.BLACK: this.paint);
		
			TextLayout textLayout = new TextLayout(text, g2D.getFont(), g2D.getFontRenderContext());

			// as of drawString has a different origin (at the bottom-right corner of the first character) dawing should be adapted to bounds 
			//g2D.drawString(text, bounds.x, bounds.y+characterHeightBound);
			textLayout.draw(g2D, bounds.x, bounds.y+characterHeightBound);
			
			//Rectangle2D bounds = textLayout.getBounds();
			/*bounds.setRect(bounds.getX()+this.bounds.getX(),
			                  bounds.getY()+this.bounds.getY(),
			                  bounds.getWidth(),
			                  bounds.getHeight());*/
			//this.bounds.setRect(bounds);
			
			//g2D.draw(bounds);
			
			g2D.dispose();
		}
	}

	@Override
	public Rectangle getBounds() 
	{
		return bounds;
	}

	@Override
	public void setBounds(Rectangle bounds) 
	{
		this.bounds = bounds;
	}
	
	public void appendText(String text)
	{
		setText(this.text + text);
	}
	
	public void setText(String text)
	{
		this.text = text;
		if ( bounds != null)
		{
			bounds.width = (this.text.length() | 1) * characterWidthBound;
			bounds.height = characterHeightBound;
		}
	}
	
	public void setColor(Color color)
	{
		this.paint = color;
	}

}
