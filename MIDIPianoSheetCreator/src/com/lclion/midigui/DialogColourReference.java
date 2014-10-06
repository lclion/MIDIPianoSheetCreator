package com.lclion.midigui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JLayeredPane;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import java.awt.Color;

import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.Component;
import java.awt.Font;

//! A dialog that shows the note values in colour for users to use as reference.
/**
 * 
 * @author LC Lion
 *
 */
public class DialogColourReference extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JCheckBox chckbxAlwaysOnTop;

	/**
	 * Create the Colour Reference dialog.
	 */
	public DialogColourReference(final JFrame parent)
	{
		setResizable(false);
		setBounds(100, 100, 264, 435);
		
		WindowFocusListener focusListener = new WindowFocusListener()
		{
			@Override
			public void windowGainedFocus(WindowEvent arg0)
			{
				// TODO Auto-generated method stub
				parent.setVisible(true);
				if(!chckbxAlwaysOnTop.isEnabled())
				parent.toFront();
			}

			@Override
			public void windowLostFocus(WindowEvent arg0)
			{
				// TODO Auto-generated method stub
			}
			
		};
		this.addWindowFocusListener(focusListener);
		
		// Set a modal exclusion even if other JDialogs has ModalityType.APPLICATION_MODAL
		// Example, if another JDialog has exclusive window focus, this window will still be movable
		setModalExclusionType(JDialog.ModalExclusionType.APPLICATION_EXCLUDE);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setAlwaysOnTop(true);
		
		setTitle("Colour Reference");
		setIconImage(new ImageIcon(getClass().getResource("/resources/images/icongrey.png")).getImage());
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
		{
			JLabel lblColourNoteValueReference = new JLabel("Colour Note Value Reference");
			lblColourNoteValueReference.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblColourNoteValueReference.setAlignmentX(0.5f);
			lblColourNoteValueReference.setAlignmentY(0.0f);
			contentPanel.add(lblColourNoteValueReference);
			
			ImageIcon icon = new ImageIcon(getClass().getResource("/resources/images/icongrey_small.png"));
	        {
	        	JLabel lblRecogniseHowLong = new JLabel("Recognise how long a note lasts by Colour!");
	        	lblRecogniseHowLong.setFont(new Font("Tahoma", Font.PLAIN, 11));
	        	lblRecogniseHowLong.setAlignmentX(0.5f);
	        	lblRecogniseHowLong.setAlignmentY(0.0f);
	        	contentPanel.add(lblRecogniseHowLong);
	        }
	        JLabel lblImage = new JLabel(icon);
	        lblImage.setAlignmentX(0.5f);
	        lblImage.setAlignmentY(0.0f);
	        contentPanel.add(lblImage);
	        {
	        	JLayeredPane layeredPane = new JLayeredPane();
	        	layeredPane.setBorder(new TitledBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, null), "Colour Keys", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	        	contentPanel.add(layeredPane);
	        	{
	        		JLabel label = new JLabel("octupleNote");
	        		label.setForeground(new Color(0, 100, 0));
	        		label.setBounds(10, 23, 95, 14);
	        		layeredPane.add(label);
	        	}
	        	{
	        		JLabel label = new JLabel("quadrupleNote");
	        		label.setForeground(new Color(0, 204, 0));
	        		label.setBounds(10, 48, 95, 14);
	        		layeredPane.add(label);
	        	}
	        	{
	        		JLabel label = new JLabel("wholeNote");
	        		label.setForeground(new Color(51, 255, 51));
	        		label.setBounds(10, 73, 95, 14);
	        		layeredPane.add(label);
	        	}
	        	{
	        		JLabel label = new JLabel("halfNote");
	        		label.setForeground(new Color(128, 255, 0));
	        		label.setBounds(10, 98, 100, 14);
	        		layeredPane.add(label);
	        	}
	        	{
	        		JLabel label = new JLabel("quarterNote");
	        		label.setForeground(new Color(204, 204, 0));
	        		label.setBounds(10, 123, 95, 14);
	        		layeredPane.add(label);
	        	}
	        	{
	        		JLabel label = new JLabel("eighthNote");
	        		label.setForeground(new Color(255, 128, 0));
	        		label.setBounds(10, 148, 95, 14);
	        		layeredPane.add(label);
	        	}
	        	{
	        		JLabel label = new JLabel("sixteenthNote");
	        		label.setForeground(new Color(255, 151, 151));
	        		label.setBounds(10, 173, 95, 14);
	        		layeredPane.add(label);
	        	}
	        	{
	        		JLabel label = new JLabel("thirtySecondNote");
	        		label.setForeground(Color.RED);
	        		label.setBounds(10, 198, 95, 14);
	        		layeredPane.add(label);
	        	}
	        	{
	        		JLabel label = new JLabel("sixtyFourthNote");
	        		label.setForeground(new Color(153, 0, 0));
	        		label.setBounds(10, 223, 158, 14);
	        		layeredPane.add(label);
	        	}
	        	{
	        		JLabel label = new JLabel("hundredTwentyEighthNote");
	        		label.setForeground(new Color(153, 0, 0));
	        		label.setBounds(10, 248, 158, 14);
	        		layeredPane.add(label);
	        	}
	        	{
	        		JLabel label = new JLabel("twoHundredFiftySixthNote");
	        		label.setForeground(new Color(153, 0, 0));
	        		label.setBounds(10, 273, 158, 14);
	        		layeredPane.add(label);
	        	}
	        }
			
			
			
			
		}
		
		chckbxAlwaysOnTop = new JCheckBox("Always on Top");
		chckbxAlwaysOnTop.setAlignmentX(Component.CENTER_ALIGNMENT);
		//chckbxAlwaysOnTop.setAlignmentY(0.0f);
		chckbxAlwaysOnTop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxAlwaysOnTop.isSelected())
					setAlwaysOnTop(true);
				else
					setAlwaysOnTop(false);
			}
		});
		chckbxAlwaysOnTop.setSelected(true);
		chckbxAlwaysOnTop.setFocusPainted(false);
		contentPanel.add(chckbxAlwaysOnTop);
		
		
	}
}
