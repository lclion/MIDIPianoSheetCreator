package com.lclion.midiparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

//! Convert MIDI Numbers to corresponding note key used in the QWERTY keyboard.
/**
 * 
 * @author LC Lion
 *
 */
public class KeyConverter
{
	private static ArrayList<String> keyboardKeys = new ArrayList<String>();

	public KeyConverter()
	{
		setupDefaultKeys();
		init();
	}

	/**
	 * \brief Setup default keys as question marks
	 * 
	 * If the requested keys are out of range, display a default question mark, indicating it is out of range.
	 * 
	 */
	private void setupDefaultKeys()
	{
		// MIDI Key Numbers range from 21 to 109? or 128?
		for (int x = 0; x < 129; ++x)
		{
			// Default key to a question mark, in case an out of range note requests it
			keyboardKeys.add(x, "?");
		}
	}

	// Encapsulate these - limit it to 1 functionality for OOP Encapsulation and less modification, more extensibility.
	private void init()
	{

		InputStream input = getClass().getResourceAsStream("/resources/data/MIDIGMOD_MSDOS.csv");

		Scanner sc;
		sc = new Scanner(input).useDelimiter(",");
		while (sc.hasNextLine())
		{
			int midiNum = Integer.parseInt(sc.next());

			String keyChar;
			keyChar = sc.nextLine();
			keyChar = keyChar.replace(",", "");
			// System.out.println(keyChar);

			keyboardKeys.set(midiNum, keyChar);
		}
		sc.close();
	}

	// Returns the translated note from MIDI to computer key
	public String convertMidiToKeyboard(int midiNum)
	{
		return keyboardKeys.get(midiNum);
	}

	// Return
	public int convertKeyboardToMidi(String keyboardChar)
	{
		int y = 0;
		for (int x = 0; x < 129; ++x)
		{
			if (keyboardKeys.get(x).contains(keyboardChar))
			{
				y = x;
				break;
			}
		}

		return y;
	}

}
