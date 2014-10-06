package com.lclion.midiparser;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

//! Uses style documents to colourise its corresponding note values into colours.
/**
* 
* @author LC Lion
*
*/
public class NoteColourConverter extends NoteValue
{
	StyledDocument sd = null;
	ArrayList<Long> eventTriggerTime = null;
	int quarterNote = 0;
	
	// Not much reason to put these in constructor unless they are used multiple times in various functions.
	// You have to consider if these are global vars or not. (within the scope of this class)
	public NoteColourConverter(StyledDocument sd, ArrayList<Long> eventTriggerTime, int quarterNote)
	{
		this.sd = sd;
		this.eventTriggerTime = eventTriggerTime;
		this.quarterNote = quarterNote;
		// Need info on note values, i.e. quarter note, whole note	
		setupNotesByQuarterNote(quarterNote);
	}
	
	public StyledDocument ColouriseNotes()
	{
		System.out.println();
		System.out.println("Note Values: ");
		//Let's start colourising!
		int elementIndex = 0;
		
		String space = " ";
		String fullText = null;
		try
		{
			fullText = sd.getText(0, sd.getLength());
		} catch (BadLocationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int iterator = 0;
		long nextEventTick = 0L;
		long currentTick = 0L;
		int length = 0; // length of the current note
		int currentPosition = 0; // The current position of note
		int endPosition = 0; // end position of current note
		int lastCurrentPosition = 0;
		int lastLength = 0;
		
		// We got noteValue, find which type it is and apply appropriate colour to the StyledDocument
		//determineNoteValue(noteValue);
		final StyleContext cont = StyleContext.getDefaultStyleContext();
		
		// Unknown Note Value, default to grey
		final AttributeSet grey = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.LIGHT_GRAY);
		
		final AttributeSet darkGreen = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(0, 100, 0));
		final AttributeSet green = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(0, 204, 0));
		final AttributeSet lightGreen = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(51, 255, 51));
		final AttributeSet lime = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(128, 255, 0));
		final AttributeSet darkYellow = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(204, 204, 0));
		final AttributeSet orange = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(255, 128, 0));
		
		final AttributeSet lightRed = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(255, 151, 151));
		final AttributeSet red = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(255, 51, 51));
		final AttributeSet darkRed = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(153, 0, 0));
		
		/*final AttributeSet lightRed = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(255, 51, 51));
		final AttributeSet red = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(255, 0, 0));
		final AttributeSet darkRed = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(153, 0, 0));*/
		
	
		
		for (long currentTrigger : eventTriggerTime)
		{
			elementIndex = eventTriggerTime.indexOf(currentTrigger);
			
			// Check there is a next note to compare it to.
			if((elementIndex+1) <= (eventTriggerTime.size()-1))	//-1
			{
				long noteValue = (eventTriggerTime.get(elementIndex+1) - currentTrigger);
				
				
				
				endPosition = fullText.indexOf(space, currentPosition);
				length = endPosition - currentPosition;
				
				// Default to Grey incase it cannot find colour
				sd.setCharacterAttributes(currentPosition, length, red, false);
				
				// Add colour note logic here
				if(noteValue > octupleNote)
				{
					sd.setCharacterAttributes(currentPosition, length, darkGreen, false);
				}
				if(noteValue <= octupleNote)
				{
					if(noteValue >= (octupleNote*0.75))
						sd.setCharacterAttributes(currentPosition, length, darkGreen, false);
				}
				if(noteValue <= (quadrupleNote*1.25))
				{
					if(noteValue >= (quadrupleNote*0.75))
					sd.setCharacterAttributes(currentPosition, length, green, false);
				}
				if(noteValue <= (wholeNote*1.25))
				{
					if(noteValue >= (wholeNote*0.75))
					sd.setCharacterAttributes(currentPosition, length, lightGreen, false);
				}
				if(noteValue <= (halfNote*1.25))
				{
					if(noteValue >= (halfNote*0.75))
					sd.setCharacterAttributes(currentPosition, length, lime, false);
				}
				if(noteValue <= (quarterNote*1.25))
				{
					if(noteValue >=	(quarterNote*0.75))
					sd.setCharacterAttributes(currentPosition, length, darkYellow, false);
				}
				if(noteValue <= (eighthNote*1.25))
				{
					if(noteValue >=	(eighthNote*0.75))
					sd.setCharacterAttributes(currentPosition, length, orange, false);
				}
				if(noteValue <= (sixteenthNote*1.25))
				{
					if(noteValue >=	(sixteenthNote*0.75))
					sd.setCharacterAttributes(currentPosition, length, lightRed, false);
				}
				if(noteValue <= (thirtySecondNote*1.25))
				{
					if(noteValue >=	(thirtySecondNote*0.75))
					sd.setCharacterAttributes(currentPosition, length, red, false);
				}				
				if(noteValue <= (sixtyFourthNote*1.25))
				{
					if(noteValue >=	(sixtyFourthNote*0.75))
					sd.setCharacterAttributes(currentPosition, length, darkRed, false);
				}
				if(noteValue <= (hundredTwentyEighthNote*1.25))
				{
					if(noteValue >=	(hundredTwentyEighthNote*0.75))
					sd.setCharacterAttributes(currentPosition, length, darkRed, false);
				}
				if(noteValue <= (twoHundredFiftySixthNote*1.25))
				{
					if(noteValue >=	(twoHundredFiftySixthNote*0.75))
					sd.setCharacterAttributes(currentPosition, length, darkRed, false);
				}
				if(noteValue <= twoHundredFiftySixthNote)
				{
					sd.setCharacterAttributes(currentPosition, length, darkRed, false);
				}

				
				// Configure for the next loop
				lastLength = length;
				lastCurrentPosition = currentPosition;

				// If there is additional space for some reason, skip it
				if (endPosition + 1 != fullText.length())
				{
					while (fullText.charAt(endPosition + 1) == ' ')
					{ // Scan through until there is no more space
						System.out.println("Yes, space is there");
						endPosition += 1;
					}
				}

				// Configure for the next loop
				endPosition += 1;
				currentPosition = endPosition;
				// tpKeyEditor.setCaretPosition(currentPosition);
				length = 0;
				
				//System.out.println(determineNoteValue(noteValue) + ":" + noteValue + " ");
				
			}
			
		}
		System.out.println("quarterNote : " + quarterNote);
		
		return sd;
	}
}
