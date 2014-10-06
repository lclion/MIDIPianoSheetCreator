/* THIS CLASS USES SUBSTANCE LOOK AND FEEL, SUBSTANCE COPYRIGHT INFORMATION:
* Copyright (c) 2005-2010 Substance Kirill Grouchnikov. All Rights Reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
* o Redistributions of source code must retain the above copyright notice,
* this list of conditions and the following disclaimer.
*
* o Redistributions in binary form must reproduce the above copyright notice,
* this list of conditions and the following disclaimer in the documentation
* and/or other materials provided with the distribution.
*
* o Neither the name of Substance Kirill Grouchnikov nor the names of
* its contributors may be used to endorse or promote products derived
* from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
* AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
* PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
* EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
* PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
* OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
* WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
* OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
* EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

/*
 * 
 */


package com.lclion.midigui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel;

/**
 * \mainpage Welcome to MIDI Piano Sheet Creator Documentation file.
 * 
 * \section intro Introduction to Software
 * 
 MIDI Piano Sheet Creator is a Java Swing application that takes in MIDI Files to convert into readable sheet music
 that can be read and played for the Virtual Piano and the GMod's Playable Piano using a standard QWERTY computer keyboard. 

 It comes with many extra features in hopes that it will be useful for people to read and play the piano sheets.
 * 
 * \section about About this Documentation
 * 
 This documentation is oriented towards Java programmers who wishes to extend or tweak the functionalities of the software.

 This software is licensed under GNU GPL 3.0 and is free and open source.

 Documentation created using Doxygen 1.8.
 * 
 */

//! 
/**
 * The Main Class. Initialises Java Swing's Look and Feel. Also sets up the Java Swing Event Dispatch Thread (EDT)
 * 
 * @author LC Lion
 * 
 * 
 */
public class InitMain
{
	// ! The starting point of the program
	public static void main(String[] args)
	{
		// Attempt to apply Java Swing's Look and Feel.
		tryLookAndFeel();

		// Initiate the main software's window
		initEventDispatchThread();
	}

	/**
	 * \brief Initiate the main program
	 * 
	 * Setup the Java Swing's Event Dispatch Thread and attempt to run the main window.
	 */
	private static void initEventDispatchThread()
	{
		// Java Swing Event Dispatch Thread (EDT)
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					// Disclaimer Message before launching software
					//JOptionPane.showMessageDialog(new JFrame(), "This software is still in development and is an incomplete software.\nIt is intended solely for testing purposes only.\nThe software contains incomplete features and possible bugs.", "MIDI Converter Disclaimer", JOptionPane.WARNING_MESSAGE);
					
					// Init Main GUI, nuclear launch detected.
					JFrameMIDIPianoSheetCreator frame = new JFrameMIDIPianoSheetCreator();

					// Signal Debug / Command Prompt
					System.out.println("Program launched. Keep this window open for debug out");

				} catch (Exception e)
				{
					e.printStackTrace();
					System.out.println();
					System.out.println("Program launched but something went wrong. See stack trace. Exiting.");
				}
			}
		});
	}

	/**
	 * \brief Attempt to initiate various swing look and feels.
	 * 
	 * If the Look and Feel fails for any reason, it will safely fall back to Java Default Look and Feel
	 */
	private static void tryLookAndFeel()
	{
		// Try the following Look and Feel.
		// If all fails, it will default to Java's Default Look and Feel
		try
		{
			// Try to initiate the default theme (last fail safe)
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			// Try to initiate the Nimbus theme
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
			{
				if ("Nimbus".equals(info.getName()))
				{
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}

			// Try to initiate the Windows Theme.
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Try to initiate (the preferred) Substantiate Look and Feel Theme. Java will fall back to the previously initialised theme
		// if this fails
		try
		{
			// Unknown reasons why it has to be instantiated twice, else you may get a Null Pointer Exception.
			UIManager.setLookAndFeel(new SubstanceGraphiteLookAndFeel());
			UIManager.setLookAndFeel(new SubstanceGraphiteLookAndFeel());
		} catch (UnsupportedLookAndFeelException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
