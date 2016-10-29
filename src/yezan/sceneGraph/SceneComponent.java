package yezan.sceneGraph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;

public class SceneComponent extends JComponent
{
	private static final long serialVersionUID = 1L;
	
	RootNode 		rootNode=null;
	String 			name;
	
	Node			selectedNode;
	
	//View related members
	public Color	selectionColor;
	public Stroke   selectionStroke;
	
	//Mouse event related memebers
	Point			mousePressLocation = new Point(0, 0);
	
	public static boolean DISPLAY_LAYOUT_BOUNDS=true;
	
	public SceneComponent(String name)
	{
		super();
		this.name = name == null ? "Scene "+serialVersionUID : name;
		this.rootNode = new RootNode();
		this.rootNode.bounds = new Rectangle(this.getLocation().x, this.getLocation().y, 0, 0);
		
		// register scene as a Mouse Listener and Mouse Motion listener
		this.addMouseListener(new SceneComponentMouseListener(this));
		this.addMouseMotionListener(new SceneComponentMouseMotionListener(this));
		
		//View
		selectionColor = Color.blue;
		selectionStroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
		
		//Mouse events
	}

	public RootNode getRootNode() {
		return rootNode;
	}
	
	
	public Node getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(Node selectedNode) 
	{
		System.out.println(selectedNode.getName()+" is not a selectable node");
		if ( selectedNode.selectable )
		{
			// if there is a previously selected node, then remove it's selection
			if ( this.selectedNode != null)
			{
				this.selectedNode.removeChild("Selection Node");
			}
			this.selectedNode = selectedNode;
			drawSelection();
		}
	}
	
	//View
	private void drawSelection()
	{
//		if ( getGraphics() != null )
//		{
			// refresh to erase previous selection
//			this.paint(getGraphics());
			// draw the new selection
//			Graphics2D g2D = (Graphics2D) getGraphics().create();
//			g2D.setColor(selectionColor);
//			g2D.setStroke(selectionStroke);
//			g2D.draw(selectedNode.getAbsoluteBounds());
			
			ShapeNode selectionNode = new ShapeNode("Selection Node", new Rectangle(0, 0, selectedNode.getBounds().width, selectedNode.getBounds().height));
			selectionNode.setColor(selectionColor);
			selectionNode.setStroke(selectionStroke);
			
			selectedNode.addChild(selectionNode);
			
			paint(getGraphics());
//			g2D.dispose();
//		}
	}
	

	/**
	 *  Render the whole scene-graph
	 */
	public void paint(Graphics g)
	{
		super.paint(g);
		System.out.println("Rendering Scene:");
		rootNode.paintNode(g);
	}
	
	/***
	 * Finds a node which contains p
	 * @param p
	 * @return The found node or null
	 */
	public Node findNode(Point p)
	{
		if ( getRootNode().children.size() > 0 )
		{
			return rootNode.find(p);
		}
		return null;
	}
	
	/**
	 * Finds node
	 * @param node
	 * @return
	 */
	public Node findNode(Node node)
	{
		if ( rootNode != null )
		{
			return node.get(node);
		}
		return null;
	}
	
	public Node findNodeByName(String name)
	{
		if ( rootNode != null )
		{
			return rootNode.get(name);
		}
		return null;
	}
	
	public void translateNode(Node node, int dx, int dy)
	{
		node.translate(dx, dy);
		paint(this.getGraphics());
	}
	
	/***
	 * Moves node to location
	 * @param node
	 * @param location
	 */
	public void moveNode(Node node, Point location)
	{
		node.move(location);
		paint(this.getGraphics());
	}
	
}


class SceneComponentMouseMotionListener extends MouseMotionAdapter
{
	SceneComponent 		scene;
	
	public SceneComponentMouseMotionListener(SceneComponent scene) 
	{
		this.scene = scene;
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		int newX = scene.mousePressLocation.x + e.getX();
		int newY = scene.mousePressLocation.y + e.getY();
		if ( scene.selectedNode != null)
		{
			scene.moveNode(scene.selectedNode, new Point(newX, newY));
		}
	}	
}

class SceneComponentMouseListener extends MouseAdapter
{
	SceneComponent scene;
	
	public SceneComponentMouseListener(SceneComponent scene)
	{
		this.scene = scene;
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		Node node = scene.findNode(e.getPoint());
		if ( node != null)
		{
			scene.setSelectedNode(node);
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		if ( scene.selectedNode != null )
		{
			scene.mousePressLocation = new Point(scene.selectedNode.getBounds().getLocation().x - e.getX(),
												scene.selectedNode.getBounds().getLocation().y - e.getY());
		}
	}
}

