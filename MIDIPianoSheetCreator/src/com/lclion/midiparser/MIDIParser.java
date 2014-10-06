package com.lclion.midiparser;

import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

//! The main parser that parses MIDI file into readable notes
/**
 * A class that parses MIDI Files directly, and outputs to alpha numeric keys.
 * 
 * @author LC Lion
 */
public class MIDIParser// extends SwingWorker<String, Integer>
{
	private static final int NOTE_ON = 0x90;
	private static final int NOTE_OFF = 0x80;
	public static final int TIME_SIGNATURE = 0x58;
	private Sequencer sequencer = null;
	private Sequence sequence = null;
	private static KeyConverter keyConverter = new KeyConverter();
	private String fileName = null;
	private String strTimeSignatureInfo = null;
	private String completeNotes = "";

	// Store MIDI Event's NOTE ON Timing values. Does not differentiate between Tracks, only whatever NOTE ON event is triggered.
	private ArrayList<Long> eventTriggerTime = new ArrayList<Long>();
	private ArrayList<String> eventTriggerNote = new ArrayList<String>();

	//
	private TreeMap<Long, ArrayList<Integer>> tmMidiParsedData = null;
	private TreeMap<Integer, ArrayList<Long>> tmTracksEvents = null;
	// Create TreeMap to store parsed midi data
	// TreeMap<Key, Value>
	// Key = MIDI Time in Ticks
	// Value = ArrayList of Notes (in MIDI Numbers) that is played at the current Key / MIDI Event

	// When required, use TreeMap.entrySet()? to retrieve the hash number.

	private long checkTick = 0;
	private long midiTickLength = 0;

	private int beatsPerMeasure = 0; // The number of beats per measure (aka 'bar' in some countries) (The top number in a musical time signature)
	private int noteValueInMeasure = 0; // The note value to represent the beat in a measure (The bottom number in a musical time signature)

	private float measureLength = 0; // The length of a m
	private int measureMultiplier = 1; // from options, how many measure per line the user wants, default it to one
	private float currentMeasure = 0;

	private String upperKeys = "";
	private String lowerKeys = "";
	private String numericKeys = "";
	private String otherKeys = "";

	public MIDIParser(File file)
	{

		try
		{
			// File name without extension (.mid)
			this.fileName = file.getName().substring(0, file.getName().length() - 4);
			
			this.sequence = MidiSystem.getSequence(file);
			this.sequencer = MidiSystem.getSequencer();
			this.sequencer.setSequence(sequence);
			midiTickLength = this.sequence.getTickLength();

		} catch (InvalidMidiDataException e)
		{
			// Unable to determine sequence from file, corrupt header
			System.out.println();
			System.out.println("MidiParser: InvalidMidiDataException");
			e.printStackTrace();
			JOptionPane.showMessageDialog(new JFrame(), "Unable to load " + file.getName() + " (Corrupt MIDI Header?)", "Invalid MIDI File", JOptionPane.ERROR_MESSAGE);
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			System.out.println();
			System.out.println("MidiParser: IOException");
			e.printStackTrace();
			JOptionPane.showMessageDialog(new JFrame(), "Unable to find file, please try again.", "File Not Found", JOptionPane.ERROR_MESSAGE);
		} catch (MidiUnavailableException e)
		{
			// TODO Auto-generated catch block
			System.out.println();
			System.out.println("MidiParser: MidiUnavailableException");
			e.printStackTrace();
			JOptionPane.showMessageDialog(new JFrame(), "Unable to find file, please try again.", "File Not Found", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * The main MIDI parse loop logic Extracts necessary data from the MIDI, and converts them to Computer Keyboard Keys. It also saves the parsed midi data to a .csv file for my autoplay script. Complies with MidiCSV-1.1 output format for more information about the formatting of passed MIDI data, visit http://www.fourmilab.ch/webtools/midicsv/ This class is in the process of deprecation, following DRY principles (Takes too long to parse)
	 * 
	 * @deprecated
	 * @return the parsed notes in String
	 */
	@Deprecated
	public String parseMIDI()
	{
		// Move these to class scope as private, if required by other method/functions.
		String currentKeyNotes = "";

		// long checkTick = 0;
		boolean firstParse = true;
		ArrayList<Integer> currentMidiNumbers = new ArrayList<Integer>();

		int trackNumber = 0;
		MidiEvent event;
		MidiMessage message;
		ShortMessage sm;
		long checkNextTick = 0; // the next tick in midi that needs to be checked. overrides the for loop iterator
		boolean firstRun = true;

		// let's follow Alice and see how deep the rabbit hole goes...
		// Check every 1 tick in every track for keys in that tick, then write to data for conversion.
		// TODO: please optmize this, maybe for every encountered tick in tracks, but not every 1 tick
		/*
		 * re-write midiparser for efficiency problem: checks for every tick solution: make it check for every .getTick
		 */
		System.out.println();
		System.out.println("Number of Tracks: " + sequence.getTracks().length);
		System.out.println();

		for (long checkTick = 0; checkTick <= sequence.getTickLength(); ++checkTick)
		{
			// System.out.println("Current Tick : " + checkTick + "/" + sequence.getTickLength());
			trackNumber = 0;
			for (Track track : sequence.getTracks())
			{
				trackNumber++;
				for (int i = 0; i < track.size(); i++)
				{
					event = track.get(i);
					message = event.getMessage();

					// MIDI MetaMessage
					/*
					 * if (firstRun == true) { if (message instanceof MetaMessage) { MetaMessage metaMessage = ((MetaMessage) message); byte[] abMessage = metaMessage.getMessage(); byte[] abData = metaMessage.getData(); int nDataLength = metaMessage.getLength();
					 * 
					 * if (TIME_SIGNATURE == metaMessage.getType()) { //strTimeSignatureInfo = "Time Signature: " + (abData[0] & 0xFF) + "/" + (1 << (abData[1] & 0xFF)) + ", MIDI clocks per metronome tick: " + (abData[2] & 0xFF) + ", 1/32 per 24 MIDI clocks: " + (abData[3] & 0xFF); } } }
					 */

					// MIDI Event Message
					if (checkTick == event.getTick())
					{
						if (message instanceof ShortMessage)
						{
							sm = (ShortMessage) message;
							if (sm.getCommand() == NOTE_ON)
							{
								int key = sm.getData1();
								// int octave = (key / 12) - 1;
								// int note = key % 12;
								int velocity = sm.getData2();
								if (velocity != 0) // We don't need velocity 0 message.
								{
									if (firstRun == true)
									{
										if (strTimeSignatureInfo != null)
										{
											System.out.println();
											System.out.println("Parsing:");
										}

										eventTriggerTime.add(event.getTick());
										firstRun = false;
									}
									else
									{
										int size = eventTriggerTime.size() - 1;
										if (eventTriggerTime.get(size) != event.getTick()) // Don't add duplicates
											eventTriggerTime.add(event.getTick());
									}

									if (!currentMidiNumbers.contains(key))
										currentMidiNumbers.add(key);

									// All the commented code below is the same as doing two lines of code above this.. you dumbass, check API next time lol
									/*
									 * // We could do a for loop for all checks with currentMidiNumbers.size(), but methods bring OutOfBoundIndex Error if (currentMidiNumbers.size() == 0) { // if it is the first key, just add it. currentMidiNumbers.add(key); } else if (currentMidiNumbers.size() == 1) { // compare to check if there is duplicate if (key != currentMidiNumbers.get(0)) { currentMidiNumbers.add(key); } } // Check for duplicate keys, we don't need them as piano is a single instrument else if (currentMidiNumbers.size() > 1) // Must have more than 1 to compare to! { // compare to check if there is duplicate boolean hasDuplicate = false; for (int x = 0; x <= currentMidiNumbers.size() - 1; ++x) {
									 * 
									 * if (key == currentMidiNumbers.get(x)) { hasDuplicate = true; break; }
									 * 
									 * } if (!hasDuplicate) { currentMidiNumbers.add(key); } }
									 */
								}
							}
						}
						if (event.getTick() > checkTick)
							break;
					}

				}
			}

			if (currentMidiNumbers.size() == 1)
			{
				char currentKey = keyConverter.convertMidiToKeyboard(currentMidiNumbers.get(0)).charAt(0);
				currentKeyNotes += currentKey;
			}
			if (currentMidiNumbers.size() > 1)
			{
				currentKeyNotes = convertMidiNumToKeys(currentMidiNumbers);
				// System.out.println("currentKeyNotes is: " + currentKeyNotes);
			}

			// All tracks are done for this tick, lets clear and format data for the next loop
			if (!currentKeyNotes.isEmpty())
			{
				// System.out.print(" " + currentKeyNotes);
				System.out.print(currentKeyNotes + " ");

				// Reverse the notes for consistency format to autoplay program
				// new StringBuffer(currentKeyNotes).reverse().toString();
				boolean hasNewLine = false;

				// event's time value plus the delta value to get the correct position relative to currentMeasure
				if ((float) checkTick + 1.0f > currentMeasure)
				{
					currentMeasure += measureLength;
					completeNotes = completeNotes + " \r\n";
					hasNewLine = true;
				}

				if (currentKeyNotes.length() > 1)
				{
					if (firstParse || hasNewLine == true)
					{ // Don't add space if it's the very first parse
						completeNotes = completeNotes + "[" + currentKeyNotes + "]";
						firstParse = false;
						hasNewLine = false;
					}
					else
						completeNotes = completeNotes + " [" + currentKeyNotes + "]";
				}
				else
				{
					if (firstParse || hasNewLine == true)
					{ // Don't add space if it's the very first parse
						completeNotes = completeNotes + currentKeyNotes;
						firstParse = false;
						hasNewLine = false;
					}
					else
						completeNotes = completeNotes + " " + currentKeyNotes;
				}

				// Reset currentKeyNotes for next MIDI Event
				currentKeyNotes = "";
				// completeNotes += " " + "\n"; //newline works

				// clear for next loop
				currentMidiNumbers.clear();
				upperKeys = lowerKeys = numericKeys = otherKeys = "";

			}
		}

		/*
		 * byte b; RandomAccessFile f = new RandomAccessFile(dir + "/name.csv", "rw"); long length = f.length() - 1; do { length -= 1; f.seek(length); b = f.readByte(); } while (b != 10); f.setLength(length + 1);
		 */

		// Clear ArrayList to save RAM
		currentMidiNumbers = null;

		System.out.println();
		System.out.println("Final Output: ");
		System.out.println("Hidden for now, refer to textPane");
		// System.out.println(completeNotes);
		System.out.println();
		System.out.println("Parsing Complete");

		setTimeSignature();
		System.out.println();
		System.out.println("eventTriggerTime: " + eventTriggerTime.size());

		for (int i = 0; i < eventTriggerTime.size() - 1; i++)
		{
			System.out.print(eventTriggerTime.get(i).toString() + " ");
		}

		return completeNotes;
	}

	/**
	 * Retrieve the timing information of MIDI Events that has Note On, and all subsequential notes that are played on that time. The data is stored in a TreeMap
	 * 
	 * @param selectedTracks
	 * The selected tracks from the midi. If an empty array is passed (as null), it will parse all tracks
	 */
	public void retrieveMidiData(int[] selectedTracks)
	{
		System.out.println();
		System.out.println("retrieveMidiData()");

		getTimeSignature();
		setTimeSignature();

		// Create TreeMap to store parsed midi data
		tmMidiParsedData = new TreeMap<Long, ArrayList<Integer>>();
		// TreeMap<Key, Value>
		// Key = MIDI Time in Ticks
		// Value = ArrayList of Notes (in MIDI Numbers) that is played at the current Key / MIDI Event

		// When required, use TreeMap.entrySet()? to retrieve the hash number.
		System.out.println("Selected Track Indexesss : " + Arrays.toString(selectedTracks));

		int trackNumber = 0;
		for (Track track : sequence.getTracks())
		{
			trackNumber++;
			

			// We only want to extract the selected tracks from the Import track options
			if(selectedTracks != null)
			{
				for(int num: selectedTracks){
					if(num == trackNumber)
					{
						
						// Parse Data for selected tracks
						for (int i = 0; i < track.size(); i++)
						{
							MidiEvent event = track.get(i);
							MidiMessage message = event.getMessage();
							if (message instanceof ShortMessage)
							{
								ShortMessage sm = (ShortMessage) message;
								if (sm.getCommand() == NOTE_ON)
								{
									int key = sm.getData1();
									int octave = (key / 12) - 1;
									int note = key % 12;
									int velocity = sm.getData2();
									if (velocity != 0)
									{
										if (!tmMidiParsedData.containsKey(event.getTick()))
										{
											tmMidiParsedData.put(event.getTick(), new ArrayList<Integer>());
											tmMidiParsedData.get(event.getTick()).add(key);
										}
										else
										{ // Check here to see if there are duplicates already in it, do not add it if there is.
											if (!tmMidiParsedData.get(event.getTick()).contains(key))
												tmMidiParsedData.get(event.getTick()).add(key);
										}
									}
								}
								if (sm.getCommand() == ShortMessage.PROGRAM_CHANGE)
								{
									int programNum = sm.getData1();
									System.out.println("patchNum : " + programNum);
									System.out.println("Channel : " + sm.getChannel());
									// wtf it works lol
								}

							}
						}
						
						
						
					}
				}
			}
			else
			{
				// Parse all tracks
				for (int i = 0; i < track.size(); i++)
				{
					MidiEvent event = track.get(i);
					MidiMessage message = event.getMessage();
					if (message instanceof ShortMessage)
					{
						ShortMessage sm = (ShortMessage) message;
						if (sm.getCommand() == NOTE_ON)
						{
							int key = sm.getData1();
							int octave = (key / 12) - 1;
							int note = key % 12;
							int velocity = sm.getData2();
							if (velocity != 0)
							{
								if (!tmMidiParsedData.containsKey(event.getTick()))
								{
									tmMidiParsedData.put(event.getTick(), new ArrayList<Integer>());
									tmMidiParsedData.get(event.getTick()).add(key);
								}
								else
								{ // Check here to see if there are duplicates already in it, do not add it if there is.
									if (!tmMidiParsedData.get(event.getTick()).contains(key))
										tmMidiParsedData.get(event.getTick()).add(key);
								}
							}
						}
						if (sm.getCommand() == ShortMessage.PROGRAM_CHANGE)
						{
							int programNum = sm.getData1();
							System.out.println("patchNum : " + programNum);
							System.out.println("Channel : " + sm.getChannel());
							// wtf it works lol
						}

					}
				}
				
				
			}

		}

		// System.out.println("Map Size: " + tmMidiParsedData.size());
		// System.out.println("Map value : " + tmMidiParsedData);

		// We got the raw data for MIDI Times and MIDI Numbers, let's parse these to alpha numerics
		parseData(tmMidiParsedData);

		System.out.println();

	}
	

	// encapsulation problem, is this really necessary - you may be breaking unnecessary OOP
	public TreeMap<Long, ArrayList<Integer>> getTmMidiParsedData()
	{
		return tmMidiParsedData;
	}

	private void addKey(char keyToAdd)
	{
		// For consistency, in a series of notes in the same MIDI tick, have: Numbers, lowercase, uppercase, otherChars - in that order
		// Maybe have
		if (Character.isUpperCase(keyToAdd))
			upperKeys += keyToAdd;
		else if (Character.isLowerCase(keyToAdd))
			lowerKeys += keyToAdd;
		else if (Character.isDigit(keyToAdd))
			numericKeys += keyToAdd;
		else
			otherKeys += keyToAdd;

		// return originalKeys;
	}

	private String convertMidiNumToKeys(ArrayList<Integer> MIDINumbers)
	{
		String sortedKeys = "";
		if (MIDINumbers.size() > 1)
		{
			Collections.sort(MIDINumbers);
			for (int x = 0; x <= MIDINumbers.size() - 1; ++x)
			{
				char currentKey = keyConverter.convertMidiToKeyboard(MIDINumbers.get(x)).charAt(0);
				addKey(currentKey);

				// Find out the last key, if it is upper
				if (x == MIDINumbers.size() - 1)
				{
					if (Character.isUpperCase(currentKey) || !Character.isLetterOrDigit(currentKey))
						sortedKeys = numericKeys + lowerKeys + otherKeys + upperKeys;
					else if (Character.isLowerCase(currentKey))
						sortedKeys = otherKeys + upperKeys + numericKeys + lowerKeys;
					else
						sortedKeys = otherKeys + upperKeys + numericKeys + lowerKeys;
				}
			}
		}
		else
		{ // It's just one
			char currentKey = keyConverter.convertMidiToKeyboard(MIDINumbers.get(0)).charAt(0);
			sortedKeys = String.valueOf(currentKey);
		}
		// if(!upperKeys.isEmpty())
		// System.out.println(sortedKeys);

		return sortedKeys;

	}

	private String formatBrackets(String currentNotes)
	{
		if (currentNotes.length() > 1)
		{
			currentNotes = "[" + currentNotes + "] ";
		}
		else
		{
			currentNotes = currentNotes + " ";
		}
		return currentNotes;
	}

	private String formatTime(long time, String currentNotes)
	{
		// event's time value plus the delta value to get the correct position relative to currentMeasure
		// Update: above comment is wrong, check your float/double/long whatever conversion properly
		// Update: I don't know, putting magic number offset for now
		// Update: It was the wrong increment from the beginning, nothing to do with conversion errors
		if (time >= currentMeasure)
		{
			currentMeasure += measureLength;
			currentNotes = "\r\n" + currentNotes;
		}
		return currentNotes;
	}

	private void parseData(TreeMap<Long, ArrayList<Integer>> rawData)
	{
		String currentNotes = null;

		for (Entry<Long, ArrayList<Integer>> entry : rawData.entrySet())
		{
			currentNotes = convertMidiNumToKeys(entry.getValue());
			currentNotes = formatBrackets(currentNotes);
			currentNotes = formatTime(entry.getKey(), currentNotes);
			completeNotes += currentNotes;

			// Reset variables for the next set of Midi Numbers
			upperKeys = lowerKeys = numericKeys = otherKeys = "";

			// Add to an ArrayList to keep track of time if this information is required later by other classes
			eventTriggerTime.add(entry.getKey());
			eventTriggerNote.add(currentNotes);

		}

		System.out.println();
		System.out.println("eventTriggerTime: " + eventTriggerTime.size());

		for (int i = 0; i < eventTriggerTime.size() - 1; i++)
		{
			System.out.print(eventTriggerTime.get(i).toString() + " ");
		}

	}

	private void setTimeSignature()
	{
		// properly encapsulate these as return values
		// clean these up please, and apply oop
		if (strTimeSignatureInfo != null)
		{
			System.out.println("MIDI MetaMessage Time Signature detected: ");
			System.out.println(strTimeSignatureInfo);
		}
		else
		{
			System.out.println("MIDI has no Time Signature MetaMessage!! Defaulting to 4/4");
			beatsPerMeasure = 4;
			noteValueInMeasure = 4;
		}
		if (sequence.getDivisionType() == 0)
			System.out.println("MIDI Division Type is PPQ (0)");
		else
			System.out.println("MIDI Division Type is SMPTE: " + sequence.getDivisionType());

		System.out.println();
		System.out.println("TickLength: " + sequencer.getTickLength());
		System.out.println("MicrosecondLength: " + sequencer.getMicrosecondLength());
		System.out.println("TempoFactor: " + sequencer.getTempoFactor());
		System.out.println("TempoInBPM: " + sequencer.getTempoInBPM());
		System.out.println("TempoInMPQ: " + sequencer.getTempoInMPQ());
		System.out.println();

		/*
		 * Old Deprecated
		 * 
		 * float quarterNote = this.sequencer.getTempoInMPQ() / 1000.0f;
		 * 
		 * 
		 * float ticksPerSecond = this.sequence.getResolution() * (Math.round(this.sequencer.getTempoInBPM()) / 60.0F);
		 * 
		 * float tickLength = ticksPerSecond / 1000.0F;
		 * 
		 * System.out.println("ticksPerSecond: " + ticksPerSecond); System.out.println("tickLength: " + tickLength); System.out.println("beatsPerMeasure: " + this.beatsPerMeasure); System.out.println("noteValueInMeasure: " + this.noteValueInMeasure); System.out.println();
		 * 
		 * float wholeNote = (tickLength * quarterNote) * 4.0F; System.out.println("wholeNote: " + wholeNote);
		 * 
		 * float noteValueInMeasureInTicks = wholeNote / this.noteValueInMeasure; System.out.println("noteValueInMeasureInTicks: " + noteValueInMeasureInTicks);
		 * 
		 * float beatsPerMeasureInTicks = this.beatsPerMeasure * noteValueInMeasureInTicks; System.out.println("beatsPerMeasureInTicks: " + beatsPerMeasureInTicks); System.out.println();
		 * 
		 * this.measureLength = beatsPerMeasureInTicks;
		 * 
		 * this.currentMeasure += this.measureLength;
		 * 
		 * System.out.println("One measure/bar has: " + this.measureLength + " Ticks");
		 */

		measureLength = 0;
		currentMeasure = 0;

		measureLength = (sequence.getResolution() * beatsPerMeasure);

		if (measureMultiplier == 0)
		{
			measureLength = sequence.getTickLength();
		}
		else
		{
			measureLength *= measureMultiplier;
		}

		currentMeasure = currentMeasure + measureLength;

		System.out.println("One measure/bar has: " + measureLength + " Ticks");
	}

	private void getTimeSignature()
	{
		MidiEvent event;
		MidiMessage message;
		ShortMessage sm;
		int trackNumber = 0;
		System.out.println();

		mainLoop: for (Track track : sequence.getTracks())
		{
			++trackNumber;
			for (int i = 0; i < track.size(); i++)
			{
				event = track.get(i);
				message = event.getMessage();

				// MIDI MetaMessage
				if (message instanceof MetaMessage)
				{
					MetaMessage metaMessage = ((MetaMessage) message);
					byte[] abMessage = metaMessage.getMessage();
					byte[] abData = metaMessage.getData();
					int nDataLength = metaMessage.getLength();

					if (TIME_SIGNATURE == metaMessage.getType())
					{
						System.out.println("Track Number: " + trackNumber);
						strTimeSignatureInfo = "Time Signature: " + (abData[0] & 0xFF) + "/" + (1 << (abData[1] & 0xFF)) + ", MIDI clocks per metronome tick: " + (abData[2] & 0xFF) + ", 1/32 per 24 MIDI clocks: " + (abData[3] & 0xFF);
						System.out.println("strTimeSignatureInfo: " + strTimeSignatureInfo);

						beatsPerMeasure = (int) abData[0];
						// beatsPerMeasure = 3;
						noteValueInMeasure = (int) 1 << abData[1];

						// We mainly want to get the first time signature, that is normally located in Track 0 or 1.
						break mainLoop;
					}
				}

			}
		}
		System.out.println();
	}

	/**
	 * For the selected midi, output a .csv file containing parsed midi data for Note_on events only. the csv is used in scripts and is not used in this program It strictly follows the MidiCSV1-1 output conventions for more information regarding the convention format used, visit http://www.fourmilab.ch/webtools/midicsv/
	 */
	public void saveMidiCSV()
	{

		try
		{
			final String dir = System.getProperty("user.dir");
			Writer newTextFile;
			newTextFile = new BufferedWriter(new FileWriter(dir + "/Data/" + fileName + ".csv"));

			BufferedWriter fw = new BufferedWriter(newTextFile);

			boolean firstRun = true;

			int trackNumber = 0;

			for (Track track : sequence.getTracks())
			{
				trackNumber++;
				for (int i = 0; i < track.size(); i++)
				{
					MidiEvent event = track.get(i);
					MidiMessage message = event.getMessage();
					if (message instanceof ShortMessage)
					{
						ShortMessage sm = (ShortMessage) message;
						if (sm.getCommand() == NOTE_ON)
						{
							int key = sm.getData1();
							int octave = (key / 12) - 1;
							int note = key % 12;
							int velocity = sm.getData2();
							if (velocity != 0)
							{
								if (firstRun == true)
								{
									fw.write(trackNumber + "," + event.getTick() + "," + " Note_on_c," + sm.getChannel() + "," + key + "," + velocity);
									firstRun = false;
								}
								else
								{
									fw.newLine();
									fw.write(trackNumber + "," + event.getTick() + "," + " Note_on_c," + sm.getChannel() + "," + key + "," + velocity);
								}
							}
						}
					}
				}
			}
			fw.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getParsedData()
	{
		// TODO Auto-generated method stub
		return completeNotes;
	}

	/**
	 * Gets the total number of events in all tracks, through an arraylist
	 * 
	 */
	public ArrayList<Integer> getTotalTrackEventsNumber()
	{
		ArrayList<Integer> trackEvents = new ArrayList<Integer>();

		// Add a dummy 0, as MIDI Tracks cannot have Track 0
		trackEvents.add(0);

		int trackNumber = 0;
		int totalEvents = 0;
		for (Track track : sequence.getTracks())
		{
			trackNumber++;
			System.out.println("trackNumber : " + trackNumber);
			for (int i = 0; i < track.size(); i++)
			{
				MidiEvent event = track.get(i);
				MidiMessage message = event.getMessage();
				if (message instanceof ShortMessage)
				{
					ShortMessage sm = (ShortMessage) message;
					if (sm.getCommand() == NOTE_ON)
					{
						totalEvents += 1;
						continue;
					}
				}
			}
			trackEvents.add(totalEvents / 2); // Divide by 2 to get the correct number of notes (not sure why, but when compared with synthesia software, this seems to be correct)
			totalEvents = 0;
			System.out.println("trackEvents: " + trackEvents.get(trackNumber));
		}

		return trackEvents;

	}
	
	/**
	 * Returns the total number of events in all tracks, through a TreeMap
	 * TreeMap: <Key>, <Value>
	 * Key: The track number in Integer. Track starts at 1, not zero.
	 * Value: An ArrayList of all events in that track.
	 * 
	 */
	public TreeMap<Integer, ArrayList<Long>> retrieveTracksEvents()
	{
		tmTracksEvents = new TreeMap<Integer, ArrayList<Long>>();

		int trackNumber = 0;
		for (Track track : sequence.getTracks())
		{
			trackNumber++;
			System.out.println("trackNumber : " + trackNumber);
			for (int i = 0; i < track.size(); i++)
			{
				MidiEvent event = track.get(i);
				MidiMessage message = event.getMessage();
				if (message instanceof ShortMessage)
				{
					ShortMessage sm = (ShortMessage) message;
					if (sm.getCommand() == NOTE_ON)
					{
						
						if (!tmTracksEvents.containsKey(trackNumber))
						{
							tmTracksEvents.put(trackNumber, new ArrayList<Long>());
							tmTracksEvents.get(trackNumber).add(event.getTick());
						}
						else
						{ // Check here to see if there are duplicates already in it, do not add it if there is.
							if (!tmTracksEvents.get(trackNumber).contains(event.getTick()))
								tmTracksEvents.get(trackNumber).add(event.getTick());
						}
						continue;
					}
				}
			}
		}

		return tmTracksEvents;

	}
	

	public int getPatchNumber(int trackNum)
	{
		System.out.println("TrackNum Value : " + trackNum);
		int programNum = -1; // if -1 value is returned, it means a patch number was not found in the specified track

		Track[] track = sequence.getTracks();
		for (int i = 0; i < track[trackNum].size(); i++)
		{
			MidiEvent event = track[trackNum].get(i);
			MidiMessage message = event.getMessage();
			if (message instanceof ShortMessage)
			{
				ShortMessage sm = (ShortMessage) message;
				if (sm.getCommand() == ShortMessage.PROGRAM_CHANGE)
				{
					programNum = sm.getData1();
					break; // We found the patch number
				}
			}
		}

		// If no patch number was found (still -1), it means it probably was specified in the previous track
		int length = track.length - 1; // so it starts from proper array starting from 0
		System.out.println("Track.Length = " + length);

		if (programNum == -1)
		{
			if (trackNum != 0) // We still have to return a value of -1 for programNum, if there are no previous tracks
			{
				trackNum -= 1;
				for (int i = 0; i < track[trackNum].size(); i++)
				{
					MidiEvent event = track[trackNum].get(i);
					MidiMessage message = event.getMessage();
					if (message instanceof ShortMessage)
					{
						ShortMessage sm = (ShortMessage) message;
						if (sm.getCommand() == ShortMessage.PROGRAM_CHANGE)
						{
							programNum = sm.getData1();
							break; // We found the patch number
						}
					}
				}
			}
		}

		return programNum;
	}

	public int getChannelNumber(int trackNum)
	{
		int channelNum = -1; // if -1 value is returned, it means none of the channel number from the specified track had any program change

		Track[] track = sequence.getTracks();
		for (int i = 0; i < track[trackNum].size(); i++)
		{
			MidiEvent event = track[trackNum].get(i);
			MidiMessage message = event.getMessage();
			if (message instanceof ShortMessage)
			{
				ShortMessage sm = (ShortMessage) message;
				if (sm.getCommand() == ShortMessage.PROGRAM_CHANGE)
				{
					channelNum = sm.getChannel();
					break;
				}
			}
		}

		return channelNum;
	}

	public ArrayList<Long> getEventTriggerTime()
	{
		return eventTriggerTime;
	}
	
	public long getNextTriggerTime(int elementValue)
	{
		return eventTriggerTime.get(elementValue);
	}
	
	public String getNextTriggerNote(int elementValue)
	{
		return eventTriggerNote.get(elementValue);
	}
	
	
	//return the element from tick
	public int getNextTriggerTimeElement(long tick)
	{
		int returnValue = 0;
		
		for(long value : eventTriggerTime)
		{
			if(value > tick)
			{
				returnValue = eventTriggerTime.indexOf(value);
				break;
			}
		}
		
		// Prevent index out of bound problem
		if(returnValue != 0)
		returnValue -= 1;
		
		
		return returnValue;
	}

	public long getTriggerTimeSize()
	{
		return eventTriggerTime.size();
	}

	public long getTickLength()
	{
		return sequence.getTickLength();
	}

	public void setMeasurePerLine(int measureMultiplier)
	{
		// TODO Auto-generated method stub
		this.measureMultiplier = measureMultiplier;
		getTimeSignature();
		setTimeSignature();
	}

	public int getTracks()
	{
		return sequence.getTracks().length;
	}

	public int getQuarterNote()
	{
		return sequence.getResolution();
	}
}