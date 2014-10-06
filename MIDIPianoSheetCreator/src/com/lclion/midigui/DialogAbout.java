package com.lclion.midigui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.BoxLayout;

import java.awt.Component;

import javax.swing.Box;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//! A dialog showing software information.
/**
 * Java Swing components used to render text and images in this dialog is declared locally in constructor, refer to source code for component details.
 * @author LC Lion
 *
 */
public class DialogAbout extends JDialog
{

	//! A content panel that stores various components.
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog. Initialises Java Swing Components that may not be displayed on the Collaboration diagram as it is declared in local scope.
	 * @param parent The parent of this dialog. Used to set the dialog relative to it's parent.
	 */
	public DialogAbout( JFrame parent )
	{
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		//pack();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo( parent );
		
		
		
		setTitle("About");
		setBounds(100, 100, 394, 274);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
		{
			JLabel lblMidiToVirtual = new JLabel("Midi to Computer Keys Converter");
			lblMidiToVirtual.setAlignmentX(0.5f);
			lblMidiToVirtual.setAlignmentY(0.0f);
			contentPanel.add(lblMidiToVirtual);
		}
		{
			JLabel lblNewLabel = new JLabel("alpha version 0.8");
			lblNewLabel.setAlignmentX(0.5f);
			lblNewLabel.setAlignmentY(0.0f);
			contentPanel.add(lblNewLabel);
		}
		{
			
	        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/images/icongrey_small.png"));
	        JLabel lblImage = new JLabel(icon);
	        lblImage.setAlignmentX(0.5f);
	        lblImage.setAlignmentY(0.0f);
	        contentPanel.add(lblImage);
		}
		{
			Component verticalStrut = Box.createVerticalStrut(20);
			contentPanel.add(verticalStrut);
		}
		{
			JLabel lblWrittenByLittle = new JLabel("A simple MIDI to Computer Keyboard Sheet converter");
			lblWrittenByLittle.setAlignmentX(0.5f);
			lblWrittenByLittle.setAlignmentY(0.0f);
			contentPanel.add(lblWrittenByLittle);
		}
		{
			JLabel lblForUseIn = new JLabel("For use in Virtual Piano and \"Playable Piano\" Addon for Garry's Mod");
			lblForUseIn.setAlignmentX(0.5f);
			lblForUseIn.setAlignmentY(0.0f);
			contentPanel.add(lblForUseIn);
		}
		{
			Component verticalStrut = Box.createVerticalStrut(20);
			contentPanel.add(verticalStrut);
		}
		{
			JLabel lblCodedByLittle = new JLabel("This software is in alpha stage");
			lblCodedByLittle.setAlignmentX(0.5f);
			lblCodedByLittle.setAlignmentY(0.0f);
			contentPanel.add(lblCodedByLittle);
		}
		{
			JLabel lblThereMayBe = new JLabel("There may be bugs and/or missing functionality");
			lblThereMayBe.setAlignmentX(0.5f);
			lblThereMayBe.setAlignmentY(0.0f);
			contentPanel.add(lblThereMayBe);
		}
		{
			JLabel lblCreatedByLittle = new JLabel("Created by Little Cute Lion, 2014");
			lblCreatedByLittle.setAlignmentX(0.5f);
			lblCreatedByLittle.setAlignmentY(0.0f);
			contentPanel.add(lblCreatedByLittle);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Close");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						setVisible(false);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
