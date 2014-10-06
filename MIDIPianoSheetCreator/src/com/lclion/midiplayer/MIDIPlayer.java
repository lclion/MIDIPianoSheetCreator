package com.lclion.midiplayer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Patch;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;

import com.lclion.midiparser.KeyConverter;

//! A MIDIPlayer that manages the playback of MIDI files
/**
* 
* @author LC Lion
*
*/
public class MIDIPlayer
{
	private int channel = 0; // 0 is a piano, 9 is percussion, other channels are for other instruments, see http://www.midi.org/techspecs/gm1sound.php
	private int volume = 80; // between 0 et 127
	private int duration = 200; // in milliseconds
	private int currentInstrumentPatchNum = -1; // -1 for default midi instrument

	private Sequence sequence = null;
	private Sequencer sequencer = null;
	private Synthesizer synth;
	private KeyConverter keyConverter = new KeyConverter();
	private String textToPlay = null;
	private ControllerEventListener controllerEventListener = null;
	private MetaEventListener mel = null;
	private Receiver receiver = null;

	private static final int NOTE_ON = 0x90;

	// Add constructor for - channel, arraylist of keys that need to be played at the same tick,
	public MIDIPlayer(File file)
	{
		try
		{
			this.sequence = MidiSystem.getSequence(file);
			Sequencer sequencer = MidiSystem.getSequencer(false);
			this.sequencer = sequencer;
			this.sequencer.open();
			this.receiver = MidiSystem.getReceiver();

			// Try setting the receiver to a synthesizer, and
			// using getChannel from Synthesizer class - http://docs.oracle.com/javase/7/docs/api/javax/sound/midi/Synthesizer.html#getChannels%28%29
			// and retrieve program number via http://docs.oracle.com/javase/7/docs/api/javax/sound/midi/MidiChannel.html#getProgram%28%29
			// Using this way, you may be able get the program number used, and thus setting it in track import dialog
			this.sequencer.getTransmitter().setReceiver(this.receiver);
			this.sequencer.setSequence(sequence);

			// this.sequencer.getTransmitter().setReceiver((Receiver) MidiSystem.getSynthesizer());
			// this.sequencer.setSequence(sequence);

			/*
			 * Synthesizer synthesizer = MidiSystem.getSynthesizer(); synthesizer.open();
			 * 
			 * synthesizer.getTransmitter().setReceiver(this.receiver); Instrument intr[] = synthesizer.getLoadedInstruments(); Patch patch = intr[0].getPatch(); int a = patch.getProgram(); System.out.println("patch num: " + a);
			 * 
			 * MidiChannel channel[] = synthesizer.getChannels(); System.out.println("channel length: " + channel.length);
			 */
			// channel[0].getProgram();

			/*
			 * Soundbank sb = MidiSystem.getSoundbank(file); Instrument intr[] = sb.getInstruments();
			 * 
			 * System.out.println("Soundbank name: " + sb.getName()); //Instrument intr[] = MidiSystem.getSynthesizer().getLoadedInstruments(); System.out.println("instru length: " + intr.length); Patch patch = intr[1].getPatch(); int a = patch.getProgram(); System.out.println("patch num: " + a);
			 */

			System.out.println(MidiSystem.getMidiDeviceInfo());

			MidiDevice.Info[] info = MidiSystem.getMidiDeviceInfo();
			for (int i = 0; i < info.length; i++)
				System.out.println(info[i].getName());

		} catch (InvalidMidiDataException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException | MidiUnavailableException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		/*
		 * this.textToPlay = textToPlay;
		 * 
		 * Scanner sc; sc = new Scanner(textToPlay).useDelimiter("\\s"); while (sc.hasNextLine()) { // Prints out strings. String keyChar = sc.next();
		 * 
		 * if (keyChar.isEmpty()) { System.out.println("Im empty!"); } else if (keyChar.length() > 1) { // Get rid of [ and ] if( keyChar.startsWith("[")) keyChar = keyChar.substring(1); if( keyChar.endsWith("]") ) keyChar = keyChar.substring(0, keyChar.length() - 1);
		 * 
		 * int var; System.out.print(keyChar + " = "); for (int x = 0; x <= keyChar.length() - 1; ++x) { var = keyConverter.convertKeyboardToMidi(Character.toString(keyChar.charAt(x))); System.out.print(var + " "); } System.out.print(",length is: " + keyChar.length()); System.out.println();
		 * 
		 * } else { int var = keyConverter.convertKeyboardToMidi(keyChar); System.out.println(keyChar + " = " + var + " ,length is: " + keyChar.length()); }
		 * 
		 * // TODO continue from here, midiplayer // convert keys to midi num here // find sleep value here // send sleep value + midinum to MIDIPlayer to play // find a way to see if you can keep track of string
		 * 
		 * } System.out.println("Complete"); sc.close();
		 * 
		 * // this.channel = channel... // etc
		 */

	}

	// add channel, arraylist of keys that need to be played at the same tick
	public void playKeys(int[] keys)
	{
		try
		{
			synth = MidiSystem.getSynthesizer();
			synth.open();
			MidiChannel[] channels = synth.getChannels();
			int channel = 0; // 0 is a piano, 9 is percussion, other channels are for other instruments

			channels[0].programChange(0);

			// TODO: continue from here
			// for(int i; channel.length(); ++i)
			// {
			// insert for loop here, note on for all concurrent keys on same tick
			channels[channel].noteOn(60, volume); // C note

			Thread.sleep(duration);

			// insert for loop here, note on for all concurrent keys on same tick
			channels[channel].noteOff(60);
			// }

		} catch (MidiUnavailableException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			synth.close();
		}
	}

	public void playMIDI()
	{

		if (!sequencer.isRunning())
		{
			sequencer.stop();

			// sequencer.setTempoFactor(2.0f); //it works

			// Consider using sequencer.setTrackMute(int, boolean) for a more customised control
			// sequencer.setTrackSolo(3, true);
			
			sequencer.start();
			
			setInstrument(currentInstrumentPatchNum);

			System.out.println("currentInstrumentPatchNum : " + currentInstrumentPatchNum);

			// System.out.println("playMIDI();");
		}
		else
		{
			setInstrument(currentInstrumentPatchNum);
		}

		ControllerEventListener controllerEventListener = new ControllerEventListener()
		{
			public void controlChange(ShortMessage event)
			{ // TODO convert the event into a readable/desired output
				System.out.println(event);
			}
		};

		/*
		 * int[] controllersOfInterest = { 0, 1,7,8,10 }; sequencer.addControllerEventListener(controllerEventListener, controllersOfInterest);
		 */

		sequencer.addMetaEventListener(new MetaEventListener()
		{
			@Override
			public void meta(MetaMessage event)
			{
				if (event.getType() == 88)
				{
					// System.out.println("event 88");
				}
				if (event.getType() == 47)
				{
					// Sequencer is done playing
					System.out.println("event 47");
					sequencer.stop();
					sequencer.setTickPosition(0);

				}
				if (event.getType() == 7)
				{
					// Sequencer is done playing
					// System.out.println("event 7");
				}
				if (event.getType() == 4)
				{
					System.out.println("MetaEvent: Instrument Name Triggered");
					System.out.println("Msg : " + event.toString());
				}
			}
		});
		// sequencer.setTempoFactor(5.0f);

		/*
		 * int trackNumber = 0; MidiEvent event; MidiMessage message; ShortMessage sm; while (sequencer.isRunning()) { sequencer.getTickPosition(); for (Track track : sequence.getTracks()) { trackNumber++; for (int i = 0; i < track.size(); i++) { event = track.get(i); message = event.getMessage(); if (message instanceof ShortMessage) { sm = (ShortMessage) message; if (sm.getCommand() == NOTE_ON) { int key = sm.getData1(); } } } } }
		 */

	}

	public void stopMIDI()
	{
		sequencer.stop();
		sequencer.setTickPosition(0);
		// System.out.println("stopMIDI();");
	}

	public void pauseMIDI()
	{
		sequencer.stop();
		// System.out.println("pauseMIDI();");
	}

	public void setVolume(double volume)
	{
		// Basically intercept and modify the midi message before sending to the receiver
		ShortMessage volMessage = new ShortMessage();
		for (int i = 0; i < 16; i++)
		{
			try
			{
				volMessage.setMessage(ShortMessage.CONTROL_CHANGE, i, 7, (int) volume);
				receiver.send(volMessage, -1);
			} catch (InvalidMidiDataException e)
			{
			}

		}
	}

	public void setInstrument(int progNum)
	{
		currentInstrumentPatchNum = progNum;

		if (progNum == -1)
		{
			// Invalid patch number, set it to default midi file
			try
			{
				long tmp = sequencer.getTickPosition();
				sequencer.setSequence(sequence);
				sequencer.setTickPosition(tmp);
				currentInstrumentPatchNum = progNum;
			} catch (InvalidMidiDataException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}

		if (sequencer.isRunning())
		{
			// sequencer.stop();
			// Basically intercept and modify the midi message before sending to the receiver
			ShortMessage progMsg = new ShortMessage();

			// for all 16 channels in the sequence per track
			for (int i = 0; i < 16; i++)
			{
				try
				{
					for (Track track : sequence.getTracks())
					{
						progMsg.setMessage(ShortMessage.PROGRAM_CHANGE, i, progNum, -1); // Data 2 is not used in a program change. ( -1 )
						track.add(new MidiEvent(progMsg, (long) 0)); // doubt this makes any changes at all
						receiver.send(progMsg, ((sequencer.getTickPosition() + 1) * 1000L + 100000L));
						track.get(0);
					}

					// Change Instrument - (msg, patchNumber, (not used - or maybe track?))
					/*
					 * progMsg.setMessage(ShortMessage.PROGRAM_CHANGE, progNum, progNum, progNum); receiver.send(progMsg, -1);
					 */
				} catch (InvalidMidiDataException e)
				{
				}

			}
			currentInstrumentPatchNum = progNum;
			// sequencer.start();
		}
		else
		{
			currentInstrumentPatchNum = progNum;
		}

	}
	
	public void setSpeed(float speed)
	{
		sequencer.setTempoFactor(speed);
	}

	public void play()
	{
		// TODO Auto-generated method stub
	}

	public void printTickPos()
	{
		System.out.println("TickPosition is : " + sequencer.getTickPosition());
	}

	public boolean isRunning()
	{
		// TODO Auto-generated method stub
		return sequencer.isRunning();
	}

	public void setTick(long tick)
	{
		sequencer.setTickPosition(tick);
	}

	public long getCurrentTick()
	{
		// TODO Auto-generated method stub
		return sequencer.getTickPosition();
	}

	public long getTickLength()
	{
		// TODO Auto-generated method stub
		return sequencer.getTickLength();
	}

	public Track[] getTracks()
	{
		return sequence.getTracks();
	}

	public void muteTrack(int trackNum)
	{
		sequencer.setTrackMute(trackNum, true);
	}

	public void unmuteTrack(int trackNum)
	{
		sequencer.setTrackMute(trackNum, false);
	}

	public void unmuteAllTracks()
	{
		for (int x = 0; x < sequence.getTracks().length; ++x)
			sequencer.setTrackMute(x, false);
	}

	public void printPatchList()
	{
		// Patch patch[] = sequence.getPatchList();
		// System.out.println("patchlist = " + sequence.getPatchList().length);
		// System.out.println("patch = " + patch[0].getProgram());

		// for(int x = 0; x <= patch.length; ++x)
		// System.out.println("Patch Number: " + patch[0].getProgram());
	}


}
