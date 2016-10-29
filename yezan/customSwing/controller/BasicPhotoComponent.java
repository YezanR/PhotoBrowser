package yezan.customSwing.controller;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import yezan.customSwing.Annotation;
import yezan.customSwing.model.PhotoModel;
import yezan.customSwing.view.PhotoUI;
import yezan.sceneGraph.ImageNode;
import yezan.sceneGraph.SceneComponent;
import yezan.sceneGraph.ShapeNode;
import yezan.sceneGraph.TextNode;
import yezan.sceneGraph.Node;




public class BasicPhotoComponent extends SceneComponent implements ChangeListener
{
	private static final long serialVersionUID = 1L;

	private ArrayList<PhotoComponentListener> photoListeners;
	
	private PhotoModel 		model;
	//Default image to show when the image isn't loaded
	public 	static Image     defaultImage;
	private static String	 defaultImageSrc="ar.jpg"; 
	
	private Point  			annotationPosition=null;
	
	private ImageNode		image=null;
	private ShapeNode		imageContainer=null;
	
	// The node which creation / edition is in progress
	private Node 			currentNode=null;
	
	PhotoMode	   			mode = PhotoMode.DEFAULT_MODE;
	State		   			state;	
	
	public BasicPhotoComponent()
	{
		super(null);
		setModel(new PhotoModel());	
		updateUI();
		
		initScene();
		
		model.setImage(defaultImageSrc);
		setFocusable(true);
		
		photoListeners = new ArrayList<PhotoComponentListener>();
	}
	
	public BasicPhotoComponent(Image image)
	{
		super(null);
		setModel(new PhotoModel());		
		updateUI();
		
		initScene();
		
		setFocusable(true);
		if ( image != null)
		{
			model.setImage(image);
		}
		else
		{
			model.setImage(defaultImageSrc);
		}
		
		photoListeners = new ArrayList<PhotoComponentListener>();
	}
	
	public BasicPhotoComponent(String imageSrc)
	{
		super(null);
		setModel(new PhotoModel());
		updateUI();
		
		initScene();
		
		setFocusable(true);
		if ( imageSrc != null)
		{
			model.setImage(imageSrc);
		}
		else
		{
			model.setImage(defaultImageSrc);
		}
		
		photoListeners = new ArrayList<PhotoComponentListener>();
	}
	
	public void addPhotoListener(PhotoComponentListener l)
	{
		photoListeners.add(l);
	}
	
	/**
	 * Initialize scene architecture
	 */
	public void initScene()
	{
		imageContainer = ShapeNode.createRectangle();
		imageContainer.setName("Image container");
		image 		   = new ImageNode(model.getImage());
		image.selectable = false;
		
		this.getRootNode().addChild(imageContainer);
		this.getRootNode().addChild(image);
	}
	
	public void setUI(PhotoUI ui)
	{
		super.setUI(ui);
	}
	
	public void updateUI()
	{
		setUI((PhotoUI) UIManager.getUI(this));
		invalidate();
	}
	
	public void setModel(PhotoModel m)
	{
		if ( model == null )
		{
			model = m;
			model.addChangeListener(this);
		}
		else
		{
			m.removeChangeListener(this);
		}
	}
	
	
	private void fireEvent(PhotoEvent event)
	{
		switch (event)
		{
		case PHOTO_SELECTED:
			for ( PhotoComponentListener l : photoListeners)
			{
				l.onPhotoComponentSelected();
			}
			break;
		default:
			
		}
	}
	
	public Annotation getAnnotation()
	{
		return model.getAnnotation();
	}
	
	public PhotoModel getModel()
	{
		return model;
	}
	
//	public Image getImage()
//	{
//		return model.getImage();
//	}
	
	public ImageNode getPhoto()
	{
		return image;
	}
	
	public ShapeNode getImageContainer()
	{
		return imageContainer;
	}

	@Override 
	public String getUIClassID()
	{
		return PhotoUI.UI_CLASS_ID;
	}
	
	public ArrayList<Point> getStrokes()
	{
		return model.getStrokes();
	}
	
	public boolean isFlipped()
	{
		return model.isFlipped();
	}
	
	public void removePhotoListener(PhotoComponentListener l)
	{
		photoListeners.remove(l);
	}
	
	@Override
	public void stateChanged(ChangeEvent e) 
	{
		/** TODO: Optimization -> check only for changed elements **/
		image.setImage(model.getImage());
		
		// if the component is flipped, then we'll have an empty image
		image.empty = isFlipped();
		
		repaint();
	}
	
	public void setImage(Image image)
	{
		model.setImage(image);
	}
	
	public void onDoubleClick()
	{
		System.out.println("Double click!");
		flip();
	}
	
//	public void onKeyType(char character)
//	{
//		if ( mode == PhotoMode.TEXT_MODE )
//		{
//			if ( state == State.EDIT_NODE )
//			{
//				System.out.println("You typed: "+character);
//				Annotation annotation = model.getAnnotation();
//				if ( annotation == null )
//				{
//					annotation = new Annotation();
//				}
//				String oldStr = annotation.getText();
//				oldStr += character;
//				System.out.println("Key type -> Annotation position = "+annotationPosition);
//				annotation.setLocation(annotationPosition);
//				annotation.setText(oldStr);
//				model.setAnnotation(annotation);
//			}
//		}
//	}
	
	public void onKeyType(char character)
	{
		if ( mode == PhotoMode.TEXT_MODE )
		{
			if ( state == State.EDIT_NODE )
			{
				if ( currentNode instanceof TextNode )
				{
					
				}
			}
		}
	}
		
	
	public void onMouseEnter()
	{
		changeCursor(mode);
		/*if ( model.isFlipped() )
		{
			setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		}*/
	}
	
	public void onMouseExit()
	{
		if ( mode != PhotoMode.DEFAULT_MODE )
		{
			changeCursor(PhotoMode.DEFAULT_MODE);
		}
	}
	
	public void onSimpleClick(Point location)
	{	
		switch ( mode )
		{
		case DEFAULT_MODE:
			fireEvent(PhotoEvent.PHOTO_SELECTED);
			break;
		case TEXT_MODE:
			if ( state == State.SPAWN_NODE )
			{
				currentNode =  new TextNode("");
				getRootNode().addChild(currentNode);
				state = State.EDIT_NODE;
				setSelectedNode(currentNode);
				currentNode.setBounds(new Rectangle(location.x, location.y, 0, 0));
				System.out.println("Simple click -> Annotation position = "+annotationPosition);
			}
		default:
			
		}
	}
	
	public void onMouseDrag(Point location)
	{
		/*if ( model.isFlipped() )
		{
			model.addStroke(location);
		}*/
		if ( mode == PhotoMode.STROKE_MODE )
		{
			model.addStroke(location);
		}
	}
	

	public void addStroke(Point location)
	{
		model.addStroke(location);
	}
	
	public void flip()
	{
		model.setFlipped(!model.isFlipped());
		mode = model.isFlipped() ? PhotoMode.SELECTION_MODE : PhotoMode.DEFAULT_MODE;
		state = model.isFlipped() ? State.CREATE_NODE : State.NONE;
		changeCursor(mode);
	}
	
	public void changeCursor(PhotoMode mode)
	{
		switch ( mode )
		{
		case DEFAULT_MODE:
		case SELECTION_MODE:
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			break;
		case TEXT_MODE:
			setCursor(new Cursor(Cursor.TEXT_CURSOR));
			break;
		case STROKE_MODE:
			setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			break;	
		}
	}
	
	public void select()
	{
		ShapeNode selection = new ShapeNode("Selection Node", new Rectangle(0, 0, imageContainer.getBounds().width, imageContainer.getBounds().height));
		selection.setStroke(new BasicStroke(3));
		selection.setColor(Color.blue);
		this.imageContainer.addChild(selection);
		
		paint(getGraphics());
	}
	
	public void deselect()
	{
		this.imageContainer.removeChild("Selection Node");
		paint(getGraphics());
	}
	
}

enum PhotoMode
{
	DEFAULT_MODE, /*flipped*/ SELECTION_MODE, /*flipped*/TEXT_MODE, /*flipped*/STROKE_MODE;
}

/**
 * State:
 * 	- NONE
 * 	- SPAWN_NODE: Select where to create (spawn) the node
 *  - CREATE_NODE: Create the node (empty)
 *  - EDIT_NODE: Edit the previously created node 
 * @author yezan
 *
 */
enum State
{
	NONE, SPAWN_NODE, CREATE_NODE, EDIT_NODE;
}

enum PhotoEvent
{
	PHOTO_SELECTED;
}

