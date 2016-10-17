package yezan.customSwing;
import java.awt.Point;
/**
 * 
 * Class for representing a photo annotation
 * @author yezan
 *
 */
public class Annotation 
{
	Point  location;
	String text;
	
	public Annotation(String text, Point location)
	{
		this.location = location;
		this.text = text;
	}
	
	public Annotation()
	{
		location = new Point(0, 0);
		text	 = "";
	}
	
	public Point getLocation() 
	{
		return location;
	}
	public void setLocation(Point location) 
	{
		this.location = location;
	}
	public String getText() 
	{
		return text;
	}
	
	public void setText(String text) 
	{
		this.text = text;
	}

}
