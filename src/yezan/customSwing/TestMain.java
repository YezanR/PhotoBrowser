package yezan.customSwing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

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

		JPanel mainPanel = new JPanel(new BorderLayout());
		//mainPanel.add(new JButton("diki"));
		//mainPanel.add(new JButton("diki2"));
		//mainPanel.add(new JButton("diki3"));
		
		BasicPhotoComponent bpc = new BasicPhotoComponent();
		mainPanel.add(bpc);
		
		w.getContentPane().add(mainPanel);
		w.setVisible(true);
	}
}
