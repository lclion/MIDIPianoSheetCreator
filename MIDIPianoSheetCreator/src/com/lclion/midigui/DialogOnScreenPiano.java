package com.lclion.midigui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;

//! Displays a preview of a 61-keyed piano, indicating the current note played from the main window.
/**
* 
* @author LC Lion
*
*/
public class DialogOnScreenPiano extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JLayeredPane layeredPane;
	private JLabel label;
	private JButton btnOne;
	private JButton btnTwo;
	private JButton btnExclamation;

	/**
	 * Create the dialog.
	 */
	public DialogOnScreenPiano(final JFrame parent)
	{
		setBounds(100, 100, 570, 300);

		setTitle("On-Screen Piano");
		setIconImage(new ImageIcon(getClass().getResource("/resources/images/icongrey.png")).getImage());

		WindowFocusListener focusListener = new WindowFocusListener()
		{
			@Override
			public void windowGainedFocus(WindowEvent arg0)
			{
				// TODO Auto-generated method stub
				// if(!chckbxAlwaysOnTop.isEnabled())
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

		setAlwaysOnTop(true);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		layeredPane = new JLayeredPane();
		layeredPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "On-Screen Piano", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		layeredPane.setBounds(10, 48, 534, 174);
		contentPanel.add(layeredPane);
		
		btnOne = new JButton("1");
		btnOne.setBounds(10, 21, 23, 142);
		//layeredPane.add(btnOne, 0, -1);
		btnOne.setSelected(true);
		btnOne.setEnabled(true);
		btnOne.setBackground(Color.WHITE);
		btnOne.setForeground(Color.WHITE);
		btnOne.setFocusable(true);
		layeredPane.add(btnOne);
		//layeredPane.setComponentZOrder(btnOne, 2);
		
		btnTwo = new JButton("2");
		btnTwo.setBounds(32, 21, 23, 142);
		layeredPane.add(btnTwo, 0, -1);
		//layeredPane.setComponentZOrder(btnTwo, 2);
		
		btnExclamation = new JButton("!");
		btnExclamation.setBounds(21, 22, 23, 97);
		layeredPane.add(btnExclamation, 1, -1);
		//layeredPane.setComponentZOrder(btnExclamation, 1);
		
		label = new JLabel("Current Note");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.BOLD, 16));
		label.setBounds(10, 11, 534, 26);
		contentPanel.add(label);

	}
}
