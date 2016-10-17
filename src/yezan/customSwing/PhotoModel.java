package yezan.customSwing;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class PhotoModel implements ButtonModel 
{
	private static final long serialVersionUID = 1L;
	
	private boolean 		flipped=false;
	private Annotation 		annotation=null;
	private String 			imgSource=null;
	private Image 			image=null;
	
	private ArrayList<ChangeListener> changeListeners;
	// keep references to all strokes location
	private ArrayList<Point> strokes;

	
	public PhotoModel()
	{
		super();
		changeListeners = new ArrayList<ChangeListener>();
		strokes = new ArrayList<Point>();
	}
	
	
	public void addStroke(Point location) 
	{
		strokes.add(location);
		fireStateChange();
	}
	
	public boolean isFlipped()
	{
		return flipped;
	}
	
	public void setFlipped(boolean value)
	{
		if ( flipped != value )
		{
			flipped = value;
			fireStateChange();
		}
	}
	
	public void setAnnotation(Annotation annotation)
	{
		this.annotation = annotation;
		fireStateChange();
	}

	@Override
	public void addChangeListener(ChangeListener l) {
		if ( changeListeners != null )
		{
			changeListeners.add(l);
		}
	}

	public Image getImage() {
		return image;
	}
	
	public Annotation getAnnotation()
	{
		return annotation;
	}
	
	public ArrayList<Point> getStrokes()
	{
		return strokes;
	}

	public void setImage(Image image) {
		this.image = image;
		fireStateChange();
	}
	
	public void setImage(String imgSource)
	{
		try 
		{
			image = ImageIO.read(new File(imgSource));
			this.imgSource = imgSource;
			// notify that image has been updated
			fireStateChange();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void removeChangeListener(ChangeListener l) {
		if ( changeListeners != null)
		{
			changeListeners.remove(l);
		}
	}
	
	protected void fireStateChange()
	{
		ChangeEvent event = new ChangeEvent(this);
		for (ChangeListener changeListener : changeListeners) {
			changeListener.stateChanged(event);
		}
	}
	
	@Override
	public Object[] getSelectedObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isArmed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSelected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPressed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRollover() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setArmed(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSelected(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEnabled(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPressed(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRollover(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMnemonic(int key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getMnemonic() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setActionCommand(String s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getActionCommand() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGroup(ButtonGroup group) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addActionListener(ActionListener l) {

	}

	@Override
	public void removeActionListener(ActionListener l) {

	}

	@Override
	public void addItemListener(ItemListener l) {
	}

	@Override
	public void removeItemListener(ItemListener l) {

	}

	
}
