package com.lclion.midiparser;

import java.util.ArrayList;
import java.util.TreeMap;

//! Setups all notes and how long the notes last.
/**
* 
* @author LC Lion
*
*/
public class NoteValue
{
	private TreeMap<String, Integer> tmMidiParsedData = null;
	
	protected long octupleNote;
	protected long quadrupleNote;
	protected long wholeNote;
	protected long halfNote;
	protected long quarterNote;
	protected long eighthNote;
	protected long sixteenthNote;
	protected long thirtySecondNote;
	protected long sixtyFourthNote;
	protected long hundredTwentyEighthNote;
	protected long twoHundredFiftySixthNote;
	
	public void setupNotesByQuarterNote(int quarterNote)
	{
		this.quarterNote = quarterNote;
		octupleNote = quarterNote * 16;
		quadrupleNote = quarterNote * 8;
		wholeNote = quarterNote * 4;
		halfNote = quarterNote * 2;
		
		eighthNote = wholeNote / 8;
		sixteenthNote = wholeNote / 16;
		thirtySecondNote = wholeNote / 32;
		sixtyFourthNote = wholeNote / 64;
		hundredTwentyEighthNote = wholeNote / 128;
		twoHundredFiftySixthNote = wholeNote / 256;
	}
	
	public String determineNoteValue(long noteValue)
	{
		String noteStringValue = null;
		
		if(noteValue == octupleNote)
		{
			noteStringValue = "octupleNote";
		}
		if(noteValue == quadrupleNote)
		{
			noteStringValue = "quadrupleNote";
		}
		if(noteValue == wholeNote)
		{
			noteStringValue = "wholeNote";
		}
		if(noteValue == halfNote)
		{
			noteStringValue = "halfNote";
		}
		if(noteValue == quarterNote)
		{
			noteStringValue = "quarterNote";
		}
		if(noteValue == eighthNote)
		{
			noteStringValue = "eighthNote";
		}
		if(noteValue == sixteenthNote)
		{
			noteStringValue = "sixteenthNote";
		}
		if(noteValue == thirtySecondNote)
		{
			noteStringValue = "thirtySecondNote";
		}
		if(noteValue == sixtyFourthNote)
		{
			noteStringValue = "sixtyFourthNote";
		}
		if(noteValue == hundredTwentyEighthNote)
		{
			noteStringValue = "hundredTwentyEighthNote";
		}
		if(noteValue == twoHundredFiftySixthNote)
		{
			noteStringValue = "twoHundredFiftySixthNote";
		}
		
		
		
		/*octupleNote;
		quadrupleNote;
		wholeNote;
		halfNote;
		quarterNote;
		eighthNote;
		sixteenthNote;
		thirtySecondNote;
		sixtyFourthNote;
		hundredTwentyEighthNote;
		twoHundredFiftySixthNote;*/
		
		
		
		return noteStringValue;
	}
	
	
	protected long getOctupleNote()
	{
		return octupleNote;
	}

	protected long getQuadrupleNote()
	{
		return quadrupleNote;
	}

	protected long getWholeNote()
	{
		return wholeNote;
	}

	protected long getHalfNote()
	{
		return halfNote;
	}

	protected long getQuarterNote()
	{
		return quarterNote;
	}

	protected long getEighthNote()
	{
		return eighthNote;
	}

	protected long getSixteenthNote()
	{
		return sixteenthNote;
	}

	protected long getThirtySecondNote()
	{
		return thirtySecondNote;
	}

	protected long getSixtyFourthNote()
	{
		return sixtyFourthNote;
	}

	protected long getHundredTwentyEighthNote()
	{
		return hundredTwentyEighthNote;
	}

	protected long getTwoHundredFiftySixthNote()
	{
		return twoHundredFiftySixthNote;
	}
}
