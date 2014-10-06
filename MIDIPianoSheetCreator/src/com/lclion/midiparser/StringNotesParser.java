package com.lclion.midiparser;

import java.util.ArrayList;
import java.util.Scanner;

public class StringNotesParser
{
	private String stringToParse = null;
	private Scanner sc = null;
	private ArrayList<String> eventTriggerNotes = new ArrayList<String>();
	private int counter = 0;
	
	public StringNotesParser(String stringToParse)
	{
		this.stringToParse = stringToParse;
		sc = new Scanner(this.stringToParse).useDelimiter(" ");
		setupEventTriggerNotes();
	}
	
	private void setupEventTriggerNotes()
	{
		while (sc.hasNext())
		{
			eventTriggerNotes.add(sc.next());
		}
	}
	
	public String getNextNotes()
	{
		
		return sc.next();
	}

	public int getOffSet()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public int getLength()
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	
}
