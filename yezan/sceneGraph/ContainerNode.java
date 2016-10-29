package yezan.sceneGraph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class ContainerNode extends Node
{	
	public ContainerNode(String name) 
	{
		super(name);
		selectable = false;
	}
	
	public ContainerNode()
	{
		super();
		selectable = false;
	}

	@Override
	protected void paint(Graphics g) 
	{
		if (Scene.DISPLAY_LAYOUT_BOUNDS && bounds != null )
		{
			final Graphics2D g2D = (Graphics2D) g.create();

			g2D.setPaint(Color.red);
			//** Debug
			System.out.println("Painting Container node "+name+" at :"+bounds);
			//**//
			g2D.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
			g2D.dispose();
			//** Debug
			System.out.println("Restoring previous graphic context :"+
			((Graphics2D) g).getTransform());
			//**//
		}
	}

	@Override
	public Rectangle getBounds() 
	{
		return this.bounds;
	}

	@Override
	public void setBounds(Rectangle bounds) 
	{
		this.bounds = bounds;
	}
	
}
