package yezan.sceneGraph;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public abstract class Node 
{
	// identify a node
	int 				id;
	public static int	seqNumber=0;
	String 				name;

	Node 				parent=null;
	ArrayList<Node> 	children;
	
	boolean				visible=true;
	Graphics2D			graphic2DContext;
	Rectangle			bounds;
	public boolean		selectable=true;
	
	Stroke				stroke;
	Paint 				paint;
	
	public Node()
	{
		id=seqNumber++;
		children = new ArrayList<Node>();
		this.parent = null;
		this.name = "Node ("+id+")";
	}
	
	
	
	public Node(String name)
	{
		id=seqNumber++;
		children = new ArrayList<Node>();
		this.parent = null;
		this.name = name;
	}
	
	protected void paintNode(Graphics gc)
	{
		if ( visible )
		{
			graphic2DContext = (Graphics2D) gc.create();
			apply2DTransform(graphic2DContext);
//			if ( graphic2DContext == null)
//			{
//				graphic2DContext = (Graphics2D) gc.create();
//			}
//			apply2DTransform(graphic2DContext); 
			paint(graphic2DContext);
			for (Node child : children) 
			{
				child.paintNode(graphic2DContext);
			}
			graphic2DContext.dispose();
		}
	}
	
	
	protected abstract void paint(Graphics g);
	
	public void addChild(Node child)
	{
		if ( child != null )
		{
			child.parent = this;
			child.graphic2DContext = this.graphic2DContext;
			children.add(child);
		}
	}
	
	public void removeChild(Node child)
	{
		children.remove(child);
	}
	
	public void removeChild(String name)
	{
		for ( Node n : children)
		{
			if ( n.name == name )
			{
				children.remove(n);
				return;
			}
		}
	}
	
	public boolean equals(Node other)
	{
		return other != null ? this.id == other.id: false;
	}
	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}
	
	public abstract Rectangle getBounds();
	
	public abstract void setBounds(Rectangle bounds);

	public boolean isVisible() 
	{
		return visible;
	}

	public void setVisible(boolean visible) 
	{
		this.visible = visible;
		for ( Node child : children)
		{
			child.setVisible(visible);
		}
	}
	
	private void apply2DTransform(Graphics2D g2D)
	{
		if ( parent != null)
		{
			AffineTransform tx = AffineTransform.getTranslateInstance(parent.getBounds().x, parent.getBounds().y);
			g2D.transform(tx);
			System.out.println("Applying tranform : "+g2D.getTransform());
		}
	}
	
	/***
	 * Get absolute position and only position, relative to rootNode for this node
	 * 
	 * @return
	 */
	public Rectangle getAbsoluteBounds()
	{
		int originX = (int) this.graphic2DContext.getTransform().getTranslateX();
		int originY	= (int) this.graphic2DContext.getTransform().getTranslateY();
		int x = (int) getBounds().getX();
		int y = (int) getBounds().getY();
		x += originX;
		y += originY;
		return new Rectangle(x, y, getBounds().width, getBounds().height);
	}
	
	/***
	 * Checks if a point is inside a node
	 * @param p
	 * @return
	 */
	public boolean contains(Point p)
	{
		return getAbsoluteBounds().contains(p);
	}
	
	/***
	 * Find node that contains point p
	 * @param p
	 * @return
	 */
	public Node find(Point p)
	{
		Node foundNode=null;
						          // to avoid case of root node
		if ( !this.contains(p) && this.parent != null )
		{
			return null;
		}
		foundNode = this;
		for ( Node node : children)
		{
			Node n = node.find(p);
			if ( n!=null)
			{
				foundNode = n;
			}
		}
		// if no such node has been found, then root node will be considered as found
		// which is not true. Therefore we need to avoid the case of root node
		return foundNode != null ? foundNode.parent != null ? foundNode: null: null;
	}
	
	/**
	 * Find the first node that equals node
	 * @param node: the node to look for
	 * @return the found node
	 */
	public Node get(Node node)
	{
		if ( this.equals(node) )
		{
			return this;
		}
		Node foundNode = null;
		for ( Node n : children )
		{
			foundNode = n.get(node);
			if ( foundNode != null)
			{
				return foundNode;
			}
		}
		return null;
	}
	
	
	/**
	 * Find the first node with such name
	 * @param name: the name of node to look for
	 * @return the found node
	 */
	public Node get(String name)
	{
		if ( this.name == name )
		{
			return this;
		}
		Node foundNode = null;
		for ( Node n : children )
		{
			foundNode = n.get(name);
			if ( foundNode != null)
			{
				return foundNode;
			}
		}
		return null;
	}
	
	public Graphics2D getGraphics2D()
	{
		return graphic2DContext;
	}
	
	public void setGraphics2D(Graphics2D g2D)
	{
		graphic2DContext = g2D;
	}
	
	
	
	public Node getParent() {
		return parent;
	}



	public void setParent(Node parent) {
		this.parent = parent;
	}



	public Stroke getStroke() {
		return stroke;
	}



	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}



	public void move(Point p)
	{
		this.getBounds().setLocation(p);
	}
	
	public void translate(int dx, int dy)
	{
		Point origin = this.getBounds().getLocation();
		this.setBounds(new Rectangle(origin.x +dx, origin.y +dy, getBounds().width, getBounds().height));
	}
}
