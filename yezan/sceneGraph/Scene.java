package yezan.sceneGraph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

public class Scene extends JPanel 
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
	
	public Scene(String name)
	{
		super();
		this.name = name;
		this.rootNode = new RootNode();
		this.rootNode.bounds = new Rectangle(this.getLocation().x, this.getLocation().y, 0, 0);
		
		// register scene as a Mouse Listener and Mouse Motion listener
		this.addMouseListener(new SceneMouseListener(this));
		this.addMouseMotionListener(new SceneMouseMotionListener(this));
		
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
			this.selectedNode = selectedNode;
			drawSelection();
		}
	}
	
	//View
	private void drawSelection()
	{
		if ( getGraphics() != null )
		{
			// refresh to erase previous selection
			this.paint(getGraphics());
			// draw the new selection
			Graphics2D g2D = (Graphics2D) getGraphics().create();
			g2D.setColor(selectionColor);
			g2D.setStroke(selectionStroke);
			g2D.draw(selectedNode.getAbsoluteBounds());
			g2D.dispose();
		}
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
	 * @return The found node
	 */
	public Node findNode(Point p)
	{
		return rootNode.find(p);
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

class SceneMouseListener extends MouseAdapter
{
	Scene scene;
	
	public SceneMouseListener(Scene scene)
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

class SceneMouseMotionListener extends MouseMotionAdapter
{
	Scene 		scene;
	
	public SceneMouseMotionListener(Scene scene) 
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

