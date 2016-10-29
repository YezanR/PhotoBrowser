package yezan.sceneGraph;

import java.awt.Graphics;
import java.awt.Rectangle;

public class RootNode extends Node
{

	public RootNode() 
	{
		super();
		bounds	= null;
	}

	@Override
	protected void paint(Graphics context) 
	{
		System.out.println("Painting RootNode");
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
	
}
