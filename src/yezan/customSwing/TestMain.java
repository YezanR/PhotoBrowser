package yezan.customSwing;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class TestMain 
{
	public static void main(String[] args)
	{
		UIManager.put(PhotoUI.UI_CLASS_ID, "yezan.customSwing.BasicPhotoUI");
		JFrame w = new JFrame("Test photoComponent");
		w.setSize(900, 650);
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel mainPanel = new JPanel();
		//mainPanel.add(new JButton("d"));
		//mainPanel.add(new JButton("d2"));
		//mainPanel.add(new JButton("d3"));
		
		BasicPhotoComponent bpc  = new BasicPhotoComponent();
		//BasicPhotoComponent bpc1 = new BasicPhotoComponent();
		//BasicPhotoComponent bpc2 = new BasicPhotoComponent();
		//BasicPhotoComponent bpc3 = new BasicPhotoComponent();
		
		mainPanel.add(bpc);
		//mainPanel.add(bpc1);
		//mainPanel.add(bpc2);
		//mainPanel.add(bpc3);
		
		
		w.getContentPane().add(mainPanel);
		w.setVisible(true);
	}
}
