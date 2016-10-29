package yezan.sceneGraph;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class ShapeNode extends Node
{
	Shape 			shape;
		
	Color			backgroundColor=null;
	
	public ShapeNode(String name) 
	{
		super(name);
	}
	
	public ShapeNode(String name, Shape shape) 
	{
		super(name);
		this.shape = shape;
		setBounds(this.shape.getBounds());
	}

	@Override
	protected void paint(Graphics g)
	{
		if ( getBounds() != null && shape != null)
		{
			// prologue: save current graphic context
			final Graphics2D g2D = (Graphics2D) g.create();
			
			if ( this.stroke != null )
			{
				g2D.setStroke(this.stroke);
			}
			if ( this.paint != null )
			{
				g2D.setPaint(this.paint);
			}
			//** Debug
			System.out.println("Painting Shape node "+name+" at: "+bounds);
			//**//
			Rectangle b = getBounds();
			if ( shape instanceof Rectangle )
			{
				((Rectangle) shape).setBounds(b.x, b.y, b.width, b.height);
				//g2D.drawRect(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
			}
			else if ( shape instanceof Ellipse2D)
			{
				( (Ellipse2D.Double) shape ).width = b.width;
				( (Ellipse2D.Double) shape ).height = b.height;
				( (Ellipse2D.Double) shape ).x = b.x;
				( (Ellipse2D.Double) shape ).y = b.y;
				//g2D.drawOval(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
			}
			if ( backgroundColor != null )
			{
				System.out.println("Draw shape outline");
				g2D.fill(shape);
			}
			else
			{
				System.out.println("Fill shape");
				g2D.draw(shape);
			}
			//g2D.draw(shape);
			//epilogue: restore old graphic context
			g2D.dispose();
		}
	}

	
	public static ShapeNode createRectangle()
	{
		return new ShapeNode("Simple rectangle", new Rectangle(0, 0, 0, 0));
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

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}
	
	public void setBackgroundColor(Color color)
	{
		backgroundColor = color;
	}
	
	public void  setColor(Color color)
	{
		this.paint = color;
	}
	
}
