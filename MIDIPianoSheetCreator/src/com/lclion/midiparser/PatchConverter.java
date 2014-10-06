package com.lclion.midiparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

//! Convert MIDI Patch Numbers to corresponding instrument name.
/**
* 
* @author LC Lion
*
*/
public class PatchConverter
{
	private static ArrayList<String> patchName = new ArrayList<String>();

	public PatchConverter()
	{
		

/*		//final String dir = System.getProperty("user.dir");
		File file = null;
		try
		{
			file = new File(getClass().getResource("/resources/data/patchNames.csv").toURI());
		} catch (URISyntaxException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		InputStream input = getClass().getResourceAsStream("/resources/data/patchNames.csv");
		InputStream in = getClass().getResourceAsStream("/resources/data/patchNames.csv");
		System.out.println("resource: " + getClass().getResourceAsStream("/resources/data/patchNames.csv").toString());
		
		Scanner sc;
		sc = new Scanner(in).useDelimiter(",");
		while (sc.hasNextLine())
		{
			int patchNum = Integer.parseInt(sc.next());

			String instrumentName;
			instrumentName = sc.nextLine();
			instrumentName = instrumentName.replace(",", "");
			// System.out.println(keyChar);
			//System.out.println("num : " + patchNum + " instr: " + instrumentName);
			patchName.add(patchNum, instrumentName);
			
		}
		sc.close();

	}

	// Returns the translated note from MIDI to computer key
	public String getPatchName(int patchNum)
	{
		String returnValue = null;
		if(patchNum < 0 || patchNum > (patchName.size()-1))	// must be within index
			returnValue = "Unknown Instrument"; 
		else
			returnValue = patchName.get(patchNum);
		
		return returnValue;
	}

}
