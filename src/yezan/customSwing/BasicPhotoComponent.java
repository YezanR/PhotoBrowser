package yezan.customSwing;


import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BasicPhotoComponent extends JComponent implements ChangeListener
{
	private static final long serialVersionUID = 1L;

	private PhotoModel model;
	
	public BasicPhotoComponent()
	{
		setModel(new PhotoModel());
		model.setImage("ar.jpg");
		updateUI();
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
	
	public PhotoModel getModel()
	{
		return model;
	}

	@Override 
	public String getUIClassID()
	{
		return PhotoUI.UI_CLASS_ID;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) 
	{

	}
}
