package com.lclion.midigui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;
import javax.swing.JLayeredPane;
import javax.swing.JTextPane;
import javax.swing.JProgressBar;
import javax.swing.border.EtchedBorder;
import javax.swing.UIManager;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSlider;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

//import midiparser.StringNotesParser;

/*import org.pushingpixels.substance.api.skin.GraphiteGlassSkin;
 import org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel;
 import org.pushingpixels.substance.api.skin.SubstanceBusinessBlueSteelLookAndFeel;
 import org.pushingpixels.substance.api.skin.SubstanceBusinessLookAndFeel;
 import org.pushingpixels.substance.api.skin.SubstanceCremeCoffeeLookAndFeel;
 import org.pushingpixels.substance.api.skin.SubstanceDustCoffeeLookAndFeel;
 import org.pushingpixels.substance.api.skin.SubstanceDustLookAndFeel;
 import org.pushingpixels.substance.api.skin.SubstanceGraphiteAquaLookAndFeel;
 import org.pushingpixels.substance.api.skin.SubstanceGraphiteGlassLookAndFeel;
 import org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel;
 import org.pushingpixels.substance.api.skin.SubstanceMagellanLookAndFeel;
 import org.pushingpixels.substance.api.skin.SubstanceMarinerLookAndFeel;
 import org.pushingpixels.substance.api.skin.SubstanceMistSilverLookAndFeel;
 import org.pushingpixels.substance.api.skin.SubstanceModerateLookAndFeel;
 import org.pushingpixels.substance.api.skin.SubstanceNebulaLookAndFeel;
 import org.pushingpixels.substance.api.skin.SubstanceOfficeBlack2007LookAndFeel;
 import org.pushingpixels.substance.api.skin.SubstanceRavenLookAndFeel;
 import org.pushingpixels.substance.api.skin.SubstanceSaharaLookAndFeel;
 import org.pushingpixels.substance.api.skin.SubstanceTwilightLookAndFeel;*/

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JCheckBoxMenuItem;

import org.pushingpixels.substance.api.skin.SubstanceAutumnLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceBusinessBlueSteelLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceBusinessLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceCremeLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceEmeraldDuskLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceGeminiLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceGraphiteAquaLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceGraphiteGlassLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceMarinerLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceNebulaLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceOfficeBlack2007LookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceOfficeSilver2007LookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceRavenLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceTwilightLookAndFeel;

import com.lclion.midiparser.MIDIParser;
import com.lclion.midiparser.NoteColourConverter;
import com.lclion.midiplayer.MIDIPlayer;
import com.lclion.utils.FileExtensionFilter;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

//! The main window of the software
/**
 * Initialises all of GUI Java Swing components as well as other Dialog Classes.
 * 
 * Some components have Event Listeners which includes the code logic in them.
 * 
 * @author LC Lion
 */
public class JFrameMIDIPianoSheetCreator extends JFrame
{
	// ! Holds frame object to pass to dialogs so they appear relative.
	private static JFrame frameHolder = null; /* !< Holds frame object to pass to dialogs so they appear relative. */
	// ! The main content pane .
	private JPanel contentPane = null;
	// ! The main text editor which displays parsed notes.
	private JTextPane tpKeyEditor;
	// ! The scroll pane which make JTextPane scrollable.
	private JScrollPane spKeyEditor;
	// ! Opens MIDI Files
	private static JMenuItem mntmOpen; /* !< Menu that opens MIDI Files. */
	// ! Saves to text file
	private JMenuItem mntmSave; /* !< Menu that saves parsed text into text file. */
	// ! Progress bar to indicate the softwares state.
	private JProgressBar progressBar;
	// ! A slider that keeps track of MIDI play time.
	private JSlider sliderTime;
	// ! Speed spinner label
	private JLabel lblSpeed;
	// ! Spinner to adjust MIDI Playback speed
	private JSpinner spinnerSpeed;
	// ! Volume slider label
	private JLabel lblVolume;
	// ! A slider that keeps track of MIDI volume.
	private JSlider sliderVolume;
	// ! A button to play the MIDI File.
	private JButton btnPlay;
	// ! A button to stop the MIDI File.
	private JButton btnStop;

	// ! The Software "About" dialog.
	private static DialogAbout dialogAbout = null;
	// ! A Dialog that displays preferences.
	private static DialogPreferences dialogPreferences = null;
	// ! A Dialog that displays advanced track options when importing MIDI Files.
	private static DialogTrackImport dialogTrackImport = null;
	// ! A Dialog that displays the colour note values as reference.
	private static DialogColourReference dialogColourReference = null;
	// ! A Dialog that displays an On Screen Keyboard for preview purposes.
	private static DialogOnScreenKeyboard dialogOnScreenKeyboard = null;
	// ! A Dialog that displays an On Screen Piano for preview purposes.
	private static DialogOnScreenPiano dialogOnScreenPiano = null;

	// ! The MIDIParser class that parses the MIDI file and converts into notes
	private MIDIParser midiParser = null;
	// ! The MIDIPlayer class that manages MIDI playback.
	private MIDIPlayer midiPlayer = null;
	// ! The NoteColourConverter class that colourises text
	private NoteColourConverter noteColourConverter = null;

	// ! The current file name.
	private String openFileName = null;
	// ! The current directory of the MIDI file chosen.
	private String dirFileName = null;
	// ! The last directory the user chose their MIDI file.
	private String lastDirectory = null;

	// ! The current font used in JTextPane.
	private String currentFont = "Verdana";
	// ! The current font size used in JTextPane.
	private int currentFontSize = 12;
	// ! A timer to keep track of time of MIDI playback.
	private Timer autoplayTimer = null;
	// ! The current patch number, used to select instrument in a MIDI.
	private int currentPatchNum = -1;

	// ! The main menu bar
	private JMenuBar menuBar;
	private JMenu mnFont;

	// Initialise all menu instruments, font size, font family
	// Perhaps use WindowBuilder Factory
	private JCheckBoxMenuItem chckbxmntmAcousticGrandPiano;
	private JCheckBoxMenuItem chckbxmntmBrightAcousticPiano;
	private JCheckBoxMenuItem chckbxmntmElectricGrandPiano;
	private JCheckBoxMenuItem chckbxmntmUseMidiDefault;
	private JCheckBoxMenuItem chckbxmntmHonkyTonkPiano;
	private JCheckBoxMenuItem chckbxmntmElectricPiano1;
	private JCheckBoxMenuItem chckbxmntmElectricPiano2;
	private JCheckBoxMenuItem chckbxmntmHarpsichord;
	private JCheckBoxMenuItem chckbxmntmClavinet;
	private JCheckBoxMenuItem mntmSize_8;
	private JCheckBoxMenuItem mntmSize_10;
	private JCheckBoxMenuItem mntmSize_12;
	private JCheckBoxMenuItem mntmSize_14;
	private JCheckBoxMenuItem mntmSize_16;
	private JCheckBoxMenuItem mntmSize_18;
	private JCheckBoxMenuItem mntmSize_24;
	private JCheckBoxMenuItem mntmSize_36;
	private JCheckBoxMenuItem mntmSize_48;
	private JCheckBoxMenuItem mntmLucidaConsole;
	private JCheckBoxMenuItem mntmTahoma;
	private JCheckBoxMenuItem mntmVerdana;
	private JCheckBoxMenuItem mntmSegoeUi;

	// ! Optional view menus
	private JMenu mnView;
	// ! Menu that opens the Colour note value reference dialog.
	private JMenuItem mntmColourNoteValues;
	// ! Menu that opens the OnScreen Keyboard dialog.
	private JMenuItem mntmOnscreenKeyboard;
	// ! Menu that opens the OnScreen Piano dialog.
	private JMenuItem mntmOnscreenPiano;
	private JMenu mnFile;

	/**
	 * Constructor that initialises all GUI components and it's Event Listeners.
	 * 
	 * This class is run under Java Swing's Event Dispatch Thread, and is not thread safe.
	 * 
	 * Use SwingWorker class to do lengthy operations, else the GUI will freeze.
	 */
	JFrameMIDIPianoSheetCreator()
	{
		// Basic frame setup
		setMinimumSize(new Dimension(505, 585));
		setLocationRelativeTo(null);
		setVisible(true);
		setIconImage(new ImageIcon(getClass().getResource("/resources/images/icongrey.png")).getImage());
		setResizable(true);
		setTitle("MIDI to Computer Keys Converter - v0.8 alpha");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Setup the Main Content Pane which will include all Java Swing Components used in this window
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0x585858));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// Use a branching pane (layered) to categorise components
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Key Editor", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		// Initiate Java Swing Components
		// Components must be declared and initialized in order of GUI hierarchy as below, do not change order unless you know what you're doing
		// Init all Main Window Components - it's important not to change this order as some GUI's rely on others to initialize first.
		initDialogs();
		initProgressSliders();
		initTextPane();
		initMenuBar();
		initButtons();

		// Initiate Speed Spinner for MIDI Playback Speed
		lblSpeed = new JLabel("Speed:");
		spinnerSpeed = new JSpinner();
		spinnerSpeed.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent evt)
			{
				midiPlayer.setSpeed((float) spinnerSpeed.getValue());
			}
		});
		spinnerSpeed.setModel(new SpinnerNumberModel(new Float(1), new Float(0.1), null, new Float(0.1)));
		spinnerSpeed.setEnabled(false);

		// GroupLayout for ContentPane
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addComponent(layeredPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE).addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)).addGap(0)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup().addComponent(layeredPane, GroupLayout.PREFERRED_SIZE, 492, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE).addGap(9)));

		GroupLayout gl_layeredPane = new GroupLayout(layeredPane);
		gl_layeredPane.setHorizontalGroup(gl_layeredPane.createParallelGroup(Alignment.LEADING).addGroup(gl_layeredPane.createSequentialGroup().addGap(4).addGroup(gl_layeredPane.createParallelGroup(Alignment.LEADING).addComponent(spKeyEditor, GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE).addGroup(gl_layeredPane.createSequentialGroup().addComponent(btnPlay, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, 46, Short.MAX_VALUE).addComponent(lblSpeed, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(spinnerSpeed, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblVolume).addPreferredGap(ComponentPlacement.RELATED).addComponent(sliderVolume, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)).addComponent(sliderTime, GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)).addContainerGap()));
		gl_layeredPane.setVerticalGroup(gl_layeredPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_layeredPane.createSequentialGroup().addGap(5).addComponent(spKeyEditor, GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addComponent(sliderTime, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE).addGroup(gl_layeredPane.createParallelGroup(Alignment.LEADING, false).addGroup(gl_layeredPane.createSequentialGroup().addGap(8).addGroup(gl_layeredPane.createParallelGroup(Alignment.BASELINE).addComponent(btnPlay, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE).addComponent(lblVolume).addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE).addComponent(spinnerSpeed, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblSpeed))).addGroup(gl_layeredPane.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE).addComponent(sliderVolume, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))).addGap(8)));
		layeredPane.setLayout(gl_layeredPane);

		contentPane.setLayout(gl_contentPane);
	}

	/**
	 * \brief Initialise dialogs related to this window.
	 * 
	 * Also sets GUI location relative to the main window
	 */
	private void initDialogs()
	{
		// Init Dialogs that are used in this window here

		dialogAbout = new DialogAbout(this);
		dialogAbout.setLocationRelativeTo(this);

		dialogPreferences = new DialogPreferences(this);
		dialogPreferences.setLocationRelativeTo(this);

		dialogColourReference = new DialogColourReference(this);
		dialogColourReference.setLocationRelativeTo(this);

		dialogOnScreenKeyboard = new DialogOnScreenKeyboard(this);
		dialogOnScreenKeyboard.setLocationRelativeTo(this);

		dialogOnScreenPiano = new DialogOnScreenPiano(this);
		dialogOnScreenPiano.setLocationRelativeTo(this);

		frameHolder = this;
	}

	/**
	 * \brief Initialise buttons related to this window here.
	 */
	private void initButtons()
	{
		initButtonPlay();
		initButtonStop();
	}

	/**
	 * \brief Initialise the Play Button and it's event listener
	 * 
	 * Plays the current MIDI File loaded.
	 * 
	 * The action listener for this button also triggers the auto highlighting feature on the textpane.
	 */
	private void initButtonPlay()
	{
		// Init play button
		btnPlay = new JButton("Play");
		btnPlay.setForeground(Color.GRAY);
		btnPlay.setBackground(Color.GRAY);
		btnPlay.setIcon(new ImageIcon(getClass().getResource("/resources/images/control_play_blue.png")));
		btnPlay.setEnabled(false);

		// Event listener to set program and MIDI Playback to start
		// btnPlay is a toggle button, so when it is pressed, it switches between pause and play
		btnPlay.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (!midiPlayer.isRunning())
				{ // The button is now toggled to Play.

					// Indicate that the MIDI Playback can now be paused
					btnPlay.setText("Pause");
					btnPlay.setIcon(new ImageIcon(getClass().getResource("/resources/images/control_pause_blue.png")));

					// Users must not be able to edit the text while the program is playing a MIDI file
					tpKeyEditor.setEnabled(true);
					tpKeyEditor.setEditable(false);
					tpKeyEditor.setCaretPosition(0);

					// Set the time slider
					sliderTime.setMaximum((int) midiPlayer.getTickLength());

					// Play the MIDI file
					midiPlayer.playMIDI();
					midiPlayer.setInstrument(currentPatchNum);

					System.out.println("spKeyEditor Vertical Visible : " + spKeyEditor.getVerticalScrollBar().getVisibleAmount());
					System.out.println("spKeyEditor Vertical Current Value : " + spKeyEditor.getVerticalScrollBar().getValue());
					System.out.println("spKeyEditor Vertical Total : " + spKeyEditor.getVerticalScrollBar().getMaximum());

					// Setup an update listener, maybe move this to a new function
					ActionListener updateListener = new ActionListener()
					{
						private String space = " ";
						private String fullText = null;

						private int iterator = 0; // The current iteration of the note played
						private long nextEventTick = 0L;
						private long currentTick = 0L;
						private int length = 0; // length of the current note
						private int currentPosition = 0; // The current position of note
						private int endPosition = 0; // end position of current note
						private int lastCurrentPosition = 0;
						private int lastLength = 0;

						// int previousScrollBarPos = spKeyEditor.getVerticalScrollBar().getValue();

						public void actionPerformed(ActionEvent arg0)
						{
							// Update slider bar
							sliderTime.setValue((int) midiPlayer.getCurrentTick());

							// Set Special Colour to Highlight the currently playing notes
							// StyleConstants.setBold(sas, true);
							final StyleContext cont = StyleContext.getDefaultStyleContext();
							final AttributeSet grey = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(122, 122, 122));
							final AttributeSet orange = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.ORANGE);
							SimpleAttributeSet sas = new SimpleAttributeSet();
							SimpleAttributeSet sasNothing = new SimpleAttributeSet();
							StyleConstants.setUnderline(sas, true);

							if (iterator != midiParser.getTriggerTimeSize())
							{

								nextEventTick = midiParser.getNextTriggerTime(iterator);

								currentTick = midiPlayer.getCurrentTick();
								// System.out.println("nextEventTick : " + nextEventTick + "CurrentTick : " + currentTick);

								if (currentTick > nextEventTick)
								{
									iterator += 1;

									// Update the On-Screen Keyboard if that window is visible
									if (dialogOnScreenKeyboard.isVisible())
									{
										dialogOnScreenKeyboard.highlightKeys(midiParser.getNextTriggerNote(iterator - 1));
									}

									try
									{
										// Get the full text of the current song
										fullText = tpKeyEditor.getDocument().getText(0, tpKeyEditor.getDocument().getLength());
									} catch (BadLocationException e2)
									{
										// TODO Auto-generated catch block
										e2.printStackTrace();
									}

									endPosition = fullText.indexOf(space, currentPosition);

									// Autoscroll
									/*
									 * System.out.println("previousScrollBarPos: " + previousScrollBarPos); tpKeyEditor.setCaretPosition(endPosition);
									 * 
									 * AdjustmentListener autoScrollListener = new AdjustmentListener() {
									 * 
									 * @Override public void adjustmentValueChanged(AdjustmentEvent evt) { // TODO Auto-generated method stub int type = evt.getAdjustmentType(); switch (type) { case AdjustmentEvent.UNIT_INCREMENT: System.out.println("Scrollbar was increased by one unit");
									 * 
									 * break; case AdjustmentEvent.UNIT_DECREMENT: System.out.println("Scrollbar was decreased by one unit"); break; case AdjustmentEvent.BLOCK_INCREMENT: System.out.println("Scrollbar was increased by one block"); break; case AdjustmentEvent.BLOCK_DECREMENT: System.out.println("Scrollbar was decreased by one block"); break; case AdjustmentEvent.TRACK: //System.out.println("The knob on the scrollbar was dragged"); //if(spKeyEditor.getVerticalScrollBar().getValue() != spKeyEditor.getVerticalScrollBar().getValue()) // break; } }
									 * 
									 * };
									 * 
									 * spKeyEditor.getVerticalScrollBar().addAdjustmentListener(autoScrollListener);
									 * 
									 * 
									 * 
									 * if(previousScrollBarPos != spKeyEditor.getVerticalScrollBar().getValue()) { System.out.println("Yes"); spKeyEditor.getVerticalScrollBar().setValue((spKeyEditor.getVerticalScrollBar().getValue() + spKeyEditor.getVerticalScrollBar().getVisibleAmount())); //tpKeyEditor.setCaretPosition(fullText.indexOf(ch, fromIndex)); //tpKeyEditor.setCaretPosition(currentPosition); previousScrollBarPos = spKeyEditor.getVerticalScrollBar().getValue() + spKeyEditor.getVerticalScrollBar().getVisibleAmount(); }
									 * 
									 * 
									 * System.out.println("CurrentPos: " + spKeyEditor.getVerticalScrollBar().getValue());
									 * 
									 * 
									 * 
									 * System.out.println("spKeyEditor Vertical Current Value : " + spKeyEditor.getVerticalScrollBar().getValue()); System.out.println("spKeyEditor Vertical Visible : " + spKeyEditor.getVerticalScrollBar().getVisibleAmount()); System.out.println("spKeyEditor Vertical Total  : " + (spKeyEditor.getVerticalScrollBar().getValue() + spKeyEditor.getVerticalScrollBar().getVisibleAmount())); System.out.println("previousScrollBarPos  : " + (previousScrollBarPos));
									 */

									length = endPosition - currentPosition;

									// Set orange to the currently located notes
									if (dialogTrackImport.isColourise())
									{
										tpKeyEditor.getStyledDocument().setCharacterAttributes(currentPosition, length, sas, false);
									}
									else
										tpKeyEditor.getStyledDocument().setCharacterAttributes(currentPosition, length, orange, false);

									/*
									 * if(length >= 2) // Don't highlight the [ and ] tpKeyEditor.getStyledDocument().setCharacterAttributes(currentPosition+1, length-1, orange, false); else tpKeyEditor.getStyledDocument().setCharacterAttributes(currentPosition, length, orange, false);
									 */

									if (iterator != 1)
									{
										tpKeyEditor.getStyledDocument().setCharacterAttributes(lastCurrentPosition, lastLength, sasNothing, true);
										tpKeyEditor.getStyledDocument().setCharacterAttributes(lastCurrentPosition, lastLength, grey, false);

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

								}
							}
							else
							{
								// Reset everything
								iterator = 0;
								nextEventTick = 0L;
								currentTick = 0L;
								length = 0;
								currentPosition = 0; // The current position that will be inserted
								endPosition = 0; // A temp
								lastCurrentPosition = 0;
								lastLength = 0;

								// Stop playing
								midiPlayer.stopMIDI();
								midiPlayer.setInstrument(currentPatchNum);

								autoplayTimer.stop();
								// final AttributeSet color = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(192, 192, 192));
								// tpKeyEditor.getStyledDocument().setCharacterAttributes(0, tpKeyEditor.getDocument().getLength(), color, false);

								sliderTime.setValue(0);

								btnPlay.setText("Play");
								btnPlay.setIcon(new ImageIcon(getClass().getResource("/resources/images/control_play_blue.png")));

								tpKeyEditor.setEnabled(true);

								final AttributeSet lightgrey = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(192, 192, 192));
								tpKeyEditor.getStyledDocument().setCharacterAttributes(0, tpKeyEditor.getDocument().getLength(), lightgrey, false);
								tpKeyEditor.setText(tpKeyEditor.getText());

								if (dialogTrackImport.isColourise())
									tpKeyEditor.setStyledDocument(noteColourConverter.ColouriseNotes());
								btnPlay.setEnabled(true);
								autoplayTimer.stop();
								// tpKeyEditor.setEditable(true);

							}

						}
					};

					// Start a timer which will automatically run the updateListener every 1ms
					autoplayTimer = new Timer(1, updateListener);
					autoplayTimer.start();
				}
				else
				{
					// Configure it to Pause, remember Play button is a toggle button between Play and Pause
					btnPlay.setText("Play");
					btnPlay.setIcon(new ImageIcon(getClass().getResource("/resources/images/control_play_blue.png")));

					tpKeyEditor.setEnabled(true);
					midiPlayer.pauseMIDI();
					btnPlay.setEnabled(true);
					autoplayTimer.stop();
				}
			}

		});
	}

	/**
	 * \brief Initialise the Stop Button and it's event listener
	 * 
	 * Stops the MIDI playback, the autoplayTimer and sets GUI components to the stop state.
	 * 
	 * @see autoplayTimers
	 */
	private void initButtonStop()
	{
		// Init stop button
		btnStop = new JButton("Stop");
		btnStop.setBackground(Color.GRAY);
		btnStop.setForeground(Color.GRAY);
		btnStop.setIcon(new ImageIcon(getClass().getResource("/resources/images/control_stop_blue.png")));
		btnStop.setEnabled(false);

		// Event listener to set program and MIDI Playback to stop
		btnStop.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// Stop button has been pressed so re-enable play button
				btnPlay.setEnabled(true);
				btnPlay.setText("Play");
				btnPlay.setIcon(new ImageIcon(getClass().getResource("/resources/images/control_play_blue.png")));

				// Stop button has been pressed so re-enable KeyEditor and set sliderTime to 0
				tpKeyEditor.setEnabled(true);
				tpKeyEditor.setCaretPosition(0);
				sliderTime.setValue(0);

				// Stop the actual playback of MIDI
				if (midiPlayer != null)
				{
					midiPlayer.stopMIDI();
					midiPlayer.setInstrument(currentPatchNum);
				}
				// Stop the timer that keeps track of notes being played
				if (autoplayTimer != null)
					autoplayTimer.stop();

				// Reset the text
				tpKeyEditor.setText(tpKeyEditor.getText());

				// Determine if the text was coloured or not, and apply accordingly back to its original state
				if (dialogTrackImport.isColourise())
				{
					tpKeyEditor.setStyledDocument(noteColourConverter.ColouriseNotes());
				}
				else
				{
					// Apply default text colour of grey
					final StyleContext cont = StyleContext.getDefaultStyleContext();
					final AttributeSet grey = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(192, 192, 192));
					tpKeyEditor.getStyledDocument().setCharacterAttributes(0, tpKeyEditor.getDocument().getLength(), grey, false);
				}

			}
		});
	}

	/**
	 * \brief Initialise sliders related to this window.
	 */
	private void initProgressSliders()
	{
		// Initialise program's status bar
		this.progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setString("Load a MIDI File to Parse");
		progressBar.setStringPainted(true);

		// Initialise the time slider.
		this.sliderTime = new JSlider();
		sliderTime.setForeground(Color.WHITE);
		sliderTime.setBackground(Color.GRAY);
		sliderTime.setPaintTicks(true);
		sliderTime.setPaintTrack(true);
		sliderTime.setOpaque(true);
		sliderTime.setValue(0);
		sliderTime.setEnabled(false);

		// Initialise Volume slider
		lblVolume = new JLabel("Volume:");
		this.sliderVolume = new JSlider();
		sliderVolume.setPaintLabels(true);
		sliderVolume.setPaintTicks(true);
		sliderVolume.setBackground(Color.GRAY);
		sliderVolume.putClientProperty("JSlider.isFilled", Boolean.TRUE);
		sliderVolume.setValue(100);
		sliderVolume.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent arg0)
			{
				midiPlayer.setVolume(sliderVolume.getValue());
			}
		});

	}

	/**
	 * \brief Initialise text panes related to this window.
	 */
	private void initTextPane()
	{
		// scroll bar on top of text pane
		tpKeyEditor = new JTextPane()
		{
			// Override so when setting String to the JTextPane, horizontal (width) works as intended
			@Override
			public boolean getScrollableTracksViewportWidth()
			{
				return getUI().getPreferredSize(this).width <= getParent().getSize().width;
			}
		};

		tpKeyEditor.setBackground(Color.DARK_GRAY);
		tpKeyEditor.setEditorKit(JEditorPane.createEditorKitForContentType("text/html"));
		tpKeyEditor.setFont(new Font(currentFont, Font.PLAIN, 12));
		tpKeyEditor.setEnabled(false);

		// Add a scrollbar pane to the textpane
		spKeyEditor = new JScrollPane();
		spKeyEditor.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		spKeyEditor.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		spKeyEditor.setViewportView(tpKeyEditor);

		wordWrapOn();
		tpKeyEditor.setFont(new Font(currentFont, Font.PLAIN, currentFontSize));
		tpKeyEditor.setText("Welcome to MIDI to Computer Keys Converter! (0.8 Alpha)\r\n\u266B\r\nThis program creates music sheets for Garry's Mod's \"Playable Piano\" Addon (by MacDGuy) from MIDI files:\r\nhttp://steamcommunity.com/sharedfiles/filedetails/?id=104548572\r\nand Virtual Piano ( virtualpiano.net )\r\n\r\n------------------------------------------------------\r\nProgrammed in Java using Eclipse Luna IDE, with WindowBuilder.\r\nSource code and this software is free and open source, and is under GNU GPL v3 license.\r\nOpening of Jar file permitted (Unencrypted and Unobfuscated). Written by Little Cute Lion, 2014\r\n\r\nPlay and Stop button Silk Icons are created by Mark James under Creative Commons Attribution 2.5 License. \r\n( http://www.famfamfam.com/lab/icons/silk/ )\r\n\r\nNote! This software is still in development and has incomplete features. If you have found cute looking bugs in the program, or for the latest updates of this software, please visit the forums of GMTower ( http://www.gmtower.org/ )\r\n\r\nEnjoy!");

	}

	/**
	 * \brief Wordwrap On for text panes related to this window.
	 */
	private void wordWrapOn()
	{
		String previousText = tpKeyEditor.getText();
		tpKeyEditor = new JTextPane();
		tpKeyEditor.setEditable(false);
		tpKeyEditor.setForeground(Color.LIGHT_GRAY);
		tpKeyEditor.setBackground(Color.DARK_GRAY);
		tpKeyEditor.setFont(new Font(currentFont, Font.PLAIN, currentFontSize));
		spKeyEditor.setViewportView(tpKeyEditor);
		tpKeyEditor.setText(previousText);
	}

	/**
	 * \brief Wordwrap Off for text panes related to this window.
	 */
	private void wordWrapOff()
	{
		String previousText = tpKeyEditor.getText();
		tpKeyEditor = new JTextPane()
		{
			// Override so when setting String to the JTextPane, horizontal (width) works as intended
			@Override
			public boolean getScrollableTracksViewportWidth()
			{
				return getUI().getPreferredSize(this).width <= getParent().getSize().width;
			}
		};
		tpKeyEditor.setBackground(Color.DARK_GRAY);
		tpKeyEditor.setFont(new Font(currentFont, Font.PLAIN, currentFontSize));
		tpKeyEditor.setEditable(false);
		tpKeyEditor.setEnabled(true);
		spKeyEditor.setViewportView(tpKeyEditor);
		tpKeyEditor.setText(previousText);
	}

	/**
	 * \brief Initialise Menu Bars related to this window.
	 */
	private void initMenuBar()
	{
		// Create the Main menu bar
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// Init all menu File GUI and logic
		initMenuFile();

		// Init Instrument Menu
		initMenuInstruments();

		// Font Menu Init
		mnFont = new JMenu("Font");
		menuBar.add(mnFont);
		initMenuFontSize();
		initMenuFontFamily();

		// Init Help Menu
		initMenuHelp();
	}

	/**
	 * \brief Initialise Menu Help related to this window.
	 */
	private void initMenuHelp()
	{

		mnView = new JMenu("View");
		menuBar.add(mnView);

		mntmColourNoteValues = new JMenuItem("Colour Reference");
		mntmColourNoteValues.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (!dialogColourReference.isVisible())
				{
					dialogColourReference.setLocationRelativeTo(frameHolder);
					dialogColourReference.setVisible(true);
				}
				else
				{
					dialogColourReference.toFront();
				}
			}
		});

		mntmOnscreenKeyboard = new JMenuItem("On-Screen Keyboard");
		mntmOnscreenKeyboard.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!dialogOnScreenKeyboard.isVisible())
				{
					dialogOnScreenKeyboard.setLocationRelativeTo(frameHolder);
					dialogOnScreenKeyboard.setVisible(true);
				}
				else
				{
					dialogOnScreenKeyboard.toFront();
				}
			}
		});

		mntmOnscreenPiano = new JMenuItem("On-Screen Piano");
		mntmOnscreenPiano.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (!dialogOnScreenPiano.isVisible())
				{
					dialogOnScreenPiano.setLocationRelativeTo(frameHolder);
					dialogOnScreenPiano.setVisible(true);
				}
				else
				{
					dialogOnScreenPiano.toFront();
				}
			}
		});
		mnView.add(mntmOnscreenPiano);
		mnView.add(mntmOnscreenKeyboard);
		mnView.add(mntmColourNoteValues);
		// TODO Auto-generated method stub
		JMenu mnHelp = new JMenu("Help");
		// mnHelp.setBackground(Color.LIGHT_GRAY);
		menuBar.add(mnHelp);

		JMenuItem mntmViewHelp = new JMenuItem("View Help");
		mntmViewHelp.setEnabled(false);
		mntmViewHelp.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				// JOptionPane.showMessageDialog(new JFrame(), "MIDI to Computer Keys Converter is a software that creates piano sheet music from MIDI files that can be played on your computer keyboard.\n\nIt is specifically created for accompanied use with the game, \"Garry's Mod\", specifically the \"Playable Piano\" workshop file that is used in GMTower servers ( http://www.gmtower.org/ ) and the Virtual Piano website ( virtualpiano.net )\n\nTo get started, download your favourite MIDI files by searching www.google.com\n\nThen simply go to File > Open MIDI and select your downloaded MIDI file to begin.", "Help", JOptionPane.PLAIN_MESSAGE);
				JOptionPane.showMessageDialog(new JFrame(), "nope.", "Help", JOptionPane.PLAIN_MESSAGE);
			}
		});
		mnHelp.add(mntmViewHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				dialogAbout.setLocationRelativeTo(frameHolder);
				dialogAbout.setVisible(true);
				// dialogAbout.setLocation(x, y);
			}
		});
		mnHelp.add(mntmAbout);
	}

	/**
	 * \brief Disables the "New Folder" button when opening files (as it is not necessary) Source: java2s.com
	 * 
	 * @param c
	 *            The container which has the component needed to search and disable the "New Folder" button
	 */
	private static void disableNewFolderButton(Container c)
	{
		int len = c.getComponentCount();
		for (int i = 0; i < len; i++)
		{
			Component comp = c.getComponent(i);
			if (comp instanceof JButton)
			{
				JButton b = (JButton) comp;
				Icon icon = b.getIcon();
				if (icon != null && icon == UIManager.getIcon("FileChooser.newFolderIcon"))
					b.setEnabled(false);
			}
			else if (comp instanceof Container)
			{
				disableNewFolderButton((Container) comp);
			}
		}
	}

	/**
	 * \brief Initialise the menus in the main window.
	 */
	private void initMenuFile()
	{
		mnFile = new JMenu("File");
		menuBar.add(mnFile);

		initMntmOpen();
		initMntmSave();
		mnFile.addSeparator();

		JMenuItem mntmPreferences = new JMenuItem("Preferences");
		mnFile.add(mntmPreferences);
		mntmPreferences.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{

				dialogPreferences.setLocationRelativeTo(frameHolder);
				dialogPreferences.setVisible(true);

			}
		});
		mnFile.addSeparator();

		// Menu Bar Exit
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
	}

	/**
	 * \brief Menu to save the parsed piano sheet to text file (notepad's .txt file format)
	 * 
	 * 
	 */
	private void initMntmSave()
	{
		mntmSave = new JMenuItem("Save To Text");
		mntmSave.setEnabled(false);
		mntmSave.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{

				tpKeyEditor.setEnabled(true);

				JFileChooser fileChooserDialogueSave = new JFileChooser(lastDirectory)
				{ // Copy paste from stack overflow, thank you kmindi
					public void approveSelection()
					{
						File f = getSelectedFile();
						if (f.exists() && getDialogType() == SAVE_DIALOG)
						{
							Toolkit.getDefaultToolkit().beep();
							int result = JOptionPane.showConfirmDialog(this, "The file exists, overwrite?", "Existing file", JOptionPane.YES_NO_OPTION);

							switch (result)
							{
							case JOptionPane.YES_OPTION:
								super.approveSelection();
								return;
							case JOptionPane.CANCEL_OPTION:
								cancelSelection();
								return;
							default:
								return;
							}
						}
						super.approveSelection();
					}
				};

				// Apply file format filter for midi so only midi files can be chosen
				FileExtensionFilter fileFilter = new FileExtensionFilter(false);
				fileChooserDialogueSave.addChoosableFileFilter(fileFilter);
				fileChooserDialogueSave.setFileFilter(fileFilter);
				fileChooserDialogueSave.setAcceptAllFileFilterUsed(true);
				fileChooserDialogueSave.setPreferredSize(new Dimension(575, 495));

				// openFileName
				String noExtensionName = openFileName.substring(0, openFileName.length() - 4);
				fileChooserDialogueSave.setSelectedFile(new File(noExtensionName + ".txt"));
				// File loaded, pass file to midi converter
				int returnVal = fileChooserDialogueSave.showSaveDialog(JFrameMIDIPianoSheetCreator.this);
				// load from file
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					File file = fileChooserDialogueSave.getSelectedFile();

					// if user didnt type an extension, make one
					String file_name = file.toString();
					if (!file_name.endsWith(".txt"))
					{
						file_name += ".txt";
						file.renameTo(new File(file_name));
					}

					// We got the file, write the text to file
					try
					{
						Writer newTextFile = new BufferedWriter(new FileWriter(file));
						BufferedWriter fw = new BufferedWriter(newTextFile);
						fw.write(tpKeyEditor.getText());
						fw.close();
					} catch (IOException iox)
					{
						// do stuff with exception
						iox.printStackTrace();
					}
				}

			}
		});
		mnFile.add(mntmSave);
	}

	/**
	 * \brief Menu to open a MIDI File
	 * 
	 * Opens MIDI File by calling a file chooser, and setting up necessary options for it using DialogTrackImport class
	 * 
	 * Also sets up an Event listener and a SwingWorker when parsing MIDI File
	 * 
	 * @see DialogTrackImport
	 */
	private void initMntmOpen()
	{
		// Menu Bar Open (Open Dialogue for MIDI
		mntmOpen = new JMenuItem("Open MIDI");
		mnFile.add(mntmOpen);

		mntmOpen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (lastDirectory == null)
					lastDirectory = (System.getProperty("user.home") + "\\Desktop"); // Windows Vista/7/8
				final JFileChooser fileChooserOpenDialogue = new JFileChooser(lastDirectory); // If directory is not found, it results to default
				fileChooserOpenDialogue.setPreferredSize(new Dimension(575, 495));
				disableNewFolderButton(fileChooserOpenDialogue);
				// Apply file format filter for midi so only midi files can be chosen
				FileExtensionFilter fileFilter = new FileExtensionFilter(true);
				fileChooserOpenDialogue.addChoosableFileFilter(fileFilter);
				fileChooserOpenDialogue.setFileFilter(fileFilter);
				fileChooserOpenDialogue.setAcceptAllFileFilterUsed(false);

				// File loaded, pass file to midi converter
				int returnVal = fileChooserOpenDialogue.showOpenDialog(JFrameMIDIPianoSheetCreator.this);

				// Get the actual file to be used
				final File file = fileChooserOpenDialogue.getSelectedFile();
				if (file != null)
				{
					openFileName = file.getName();
					dirFileName = file.getAbsolutePath();
					lastDirectory = file.getParent();
				}

				// load from file
				if (returnVal == JFileChooser.CANCEL_OPTION)
				{
					progressBar.setIndeterminate(false);
				}

				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					progressBar.setIndeterminate(true);
					System.out.println("----------------------------------");
					// if midiPlayer is still playing, stop
					if (midiPlayer != null)
						midiPlayer.stopMIDI();

					// if timer for checking updates is still running, stop
					if (autoplayTimer != null)
						autoplayTimer.stop();

					// Set appropriate settings to apply to the Note sheet according to dialog options and settings
					if (dialogPreferences.isAdvancedTrackImport())
					{
						// Setup the required operations in a SwingWorker
						final SwingWorker<String, File> midiParserWorker = setupSwingWorkerMIDIParse(file);

						dialogTrackImport = new DialogTrackImport(frameHolder, file);

						dialogTrackImport.setLocationRelativeTo(frameHolder);
						dialogTrackImport.setVisible(true);

						if (dialogTrackImport.getSelectedButton() == DialogTrackImport.OK_BUTTON)
						{
							progressBar.setIndeterminate(false);
							System.out.println();
							System.out.println("Loading " + openFileName);

							// MIDI Parser Preparation
							if (!dialogTrackImport.isCustomMeasure())
							{ // Word Wrap on
								wordWrapOn();
							}
							else
							{ // Word wrap off
								wordWrapOff();
							}

							// GUI Loading Setup
							progressBar.setIndeterminate(true);
							progressBar.setString("Please wait, Parsing: " + openFileName);
							tpKeyEditor.setText("Please Wait...");

							System.out.println("dialogImportOptions.getMeasures() : " + dialogPreferences.getMeasures());

							// We got all the setting required to parse the MIDI Data, now execute the SwingWorker to parse it.
							midiParserWorker.execute();
						}
						else if (dialogTrackImport.getSelectedButton() == DialogTrackImport.CANCEL_BUTTON)
						{
							progressBar.setIndeterminate(false);
						}

					}
					else
					{
						// Setup the required operations in a SwingWorker
						SwingWorker<String, File> midiParserWorker = setupSwingWorkerMIDIParse(file);

						// MIDI Parser Preparation
						if (!dialogPreferences.isCustomMeasure())
						{ // Word Wrap on
							wordWrapOn();
						}
						else
						{ // Word wrap off
							wordWrapOff();
						}
						// midiParser.setMeasurePerLine(dialogPreferences.getMeasures());
						midiParserWorker.execute();
					}

				}

			}

		});
	}

	/**
	 * \brief Returns the SwingWorker required to handle MIDI Parsing (SwingWorker prevents GUI freezing)
	 * 
	 * @param file
	 *            The MIDI file to parse
	 * @return SwingWorker The SwingWorker that handles the actual midiParsing operations
	 */
	private SwingWorker<String, File> setupSwingWorkerMIDIParse(File file)
	{
		final File tmpFile = file;
		// SwingWorker Class (Prevents GUI freezing when calculating time consuming functions from an EDT Thread)
		SwingWorker<String, File> midiParserWorker = new SwingWorker<String, File>()
		{
			@Override
			public String doInBackground()
			{
				// Initiate the parser
				midiParser = new MIDIParser(tmpFile);

				// Setup midiParser depending on what options are chosen in preferences
				if (dialogPreferences.isAdvancedTrackImport())
				{
					midiParser.setMeasurePerLine(dialogTrackImport.getMeasures());
					// Start parsing data - this is the main task that freezes the GUI (Hence fixed by use of SwingWorker Class)
					midiParser.retrieveMidiData(dialogTrackImport.getSelectedTracks());
				}
				else
				{
					midiParser.setMeasurePerLine(dialogPreferences.getMeasures());
					midiParser.retrieveMidiData(null); // param: null = just import all tracks
				}
				return null;
			}

			@Override
			public void done()
			{
				// Get parsed data
				tpKeyEditor.setText(midiParser.getParsedData());
				dialogOnScreenKeyboard.setText(midiParser.getParsedData());

				noteColourConverter = new NoteColourConverter(tpKeyEditor.getStyledDocument(), midiParser.getEventTriggerTime(), midiParser.getQuarterNote());

				if (dialogTrackImport.isColourise())
					tpKeyEditor.setStyledDocument(noteColourConverter.ColouriseNotes());

				tpKeyEditor.setCaretPosition(0);

				tpKeyEditor.setEnabled(true);

				progressBar.setIndeterminate(false);
				progressBar.setValue(100);
				progressBar.setString("Loaded File - " + openFileName);

				midiPlayer = new MIDIPlayer(new File(dirFileName));
				// Set user volume and instrument
				// TODO: Add speed so initial speed is applied
				midiPlayer.setVolume(sliderVolume.getValue());
				midiPlayer.setInstrument(currentPatchNum);

				btnPlay.setEnabled(true);
				btnPlay.setText("Play");
				btnPlay.setIcon(new ImageIcon(getClass().getResource("/resources/images/control_play_blue.png")));

				btnStop.setEnabled(true);
				mntmSave.setEnabled(true);
				sliderTime.setEnabled(true);
				sliderTime.setMaximum((int) midiPlayer.getTickLength());
				sliderTime.setMinimum(0);
				sliderTime.setValue(0);

				spinnerSpeed.setEnabled(true);

			}
		};

		return midiParserWorker;
	}

	private void initMenuFontFamily()
	{
	}

	private void initMenuFontSize()
	{
		// TODO Auto-generated method stub
		JMenu mnFamily = new JMenu("Family");
		mnFont.add(mnFamily);

		mntmLucidaConsole = new JCheckBoxMenuItem("Lucida Console");
		mntmLucidaConsole.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				deselectFontFamilyMenu();
				mntmLucidaConsole.setSelected(true);
				currentFont = "Lucida Console";
				Font font = new Font("Lucida Console", Font.PLAIN, tpKeyEditor.getFont().getSize());
				tpKeyEditor.setFont(font);
			}
		});
		mnFamily.add(mntmLucidaConsole);

		mntmTahoma = new JCheckBoxMenuItem("Tahoma");
		mntmTahoma.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				deselectFontFamilyMenu();
				mntmTahoma.setSelected(true);
				currentFont = "Tahoma";
				Font font = new Font("Tahoma", Font.PLAIN, tpKeyEditor.getFont().getSize());
				tpKeyEditor.setFont(font);
			}
		});
		mnFamily.add(mntmTahoma);

		mntmVerdana = new JCheckBoxMenuItem("Verdana");
		mntmVerdana.setSelected(true);
		mntmVerdana.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				deselectFontFamilyMenu();
				mntmVerdana.setSelected(true);
				currentFont = "Verdana";
				Font font = new Font("Verdana", Font.PLAIN, tpKeyEditor.getFont().getSize());
				tpKeyEditor.setFont(font);
			}
		});
		mnFamily.add(mntmVerdana);

		mntmSegoeUi = new JCheckBoxMenuItem("Segoe UI");
		mntmSegoeUi.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				deselectFontFamilyMenu();
				mntmSegoeUi.setSelected(true);
				currentFont = "Segoe UI";
				Font font = new Font("Segoe UI", Font.PLAIN, tpKeyEditor.getFont().getSize());
				tpKeyEditor.setFont(font);
			}
		});
		mnFamily.add(mntmSegoeUi);
		// TODO Auto-generated method stub
		JMenu mnSize = new JMenu("Size");
		mnFont.add(mnSize);

		mntmSize_8 = new JCheckBoxMenuItem("Size 8");
		mntmSize_8.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				deselectFontSizeMenu();
				mntmSize_8.setSelected(true);
				Font font = new Font(currentFont, Font.PLAIN, 8);
				tpKeyEditor.setFont(font);
				currentFontSize = 8;
			}
		});
		mnSize.add(mntmSize_8);

		mntmSize_10 = new JCheckBoxMenuItem("Size 10");
		mntmSize_10.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				deselectFontSizeMenu();
				mntmSize_10.setSelected(true);
				Font font = new Font(currentFont, Font.PLAIN, 10);
				tpKeyEditor.setFont(font);
				currentFontSize = 10;
			}
		});
		mnSize.add(mntmSize_10);

		mntmSize_12 = new JCheckBoxMenuItem("Size 12");
		mntmSize_12.setSelected(true);
		mntmSize_12.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				deselectFontSizeMenu();
				mntmSize_12.setSelected(true);
				Font font = new Font(currentFont, Font.PLAIN, 12);
				tpKeyEditor.setFont(font);
				currentFontSize = 12;
			}
		});
		mnSize.add(mntmSize_12);

		mntmSize_14 = new JCheckBoxMenuItem("Size 14");
		mntmSize_14.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				deselectFontSizeMenu();
				mntmSize_14.setSelected(true);
				Font font = new Font(currentFont, Font.PLAIN, 14);
				tpKeyEditor.setFont(font);
				currentFontSize = 14;
			}
		});
		mnSize.add(mntmSize_14);

		mntmSize_16 = new JCheckBoxMenuItem("Size 16");
		mntmSize_16.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				deselectFontSizeMenu();
				mntmSize_16.setSelected(true);
				Font font = new Font(currentFont, Font.PLAIN, 16);
				tpKeyEditor.setFont(font);
				currentFontSize = 16;
			}
		});
		mnSize.add(mntmSize_16);

		mntmSize_18 = new JCheckBoxMenuItem("Size 18");
		mntmSize_18.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				deselectFontSizeMenu();
				mntmSize_18.setSelected(true);
				Font font = new Font(currentFont, Font.PLAIN, 18);
				tpKeyEditor.setFont(font);
				currentFontSize = 18;
			}
		});
		mnSize.add(mntmSize_18);

		mntmSize_24 = new JCheckBoxMenuItem("Size 24");
		mntmSize_24.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				deselectFontSizeMenu();
				mntmSize_24.setSelected(true);
				Font font = new Font(currentFont, Font.PLAIN, 24);
				tpKeyEditor.setFont(font);
				currentFontSize = 24;
			}
		});
		mnSize.add(mntmSize_24);

		mntmSize_36 = new JCheckBoxMenuItem("Size 36");
		mntmSize_36.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				deselectFontSizeMenu();
				mntmSize_36.setSelected(true);
				Font font = new Font(currentFont, Font.PLAIN, 36);
				tpKeyEditor.setFont(font);
				currentFontSize = 36;
			}
		});
		mnSize.add(mntmSize_36);

		mntmSize_48 = new JCheckBoxMenuItem("Size 48");
		mntmSize_48.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				deselectFontSizeMenu();
				mntmSize_48.setSelected(true);
				Font font = new Font(currentFont, Font.PLAIN, 48);
				tpKeyEditor.setFont(font);
				currentFontSize = 48;
			}
		});
		mnSize.add(mntmSize_48);
	}

	private void initMenuInstruments()
	{
		// TODO Auto-generated method stub
		JMenu mnInstrument = new JMenu("Instrument");
		menuBar.add(mnInstrument);

		chckbxmntmAcousticGrandPiano = new JCheckBoxMenuItem("Acoustic Grand Piano");
		chckbxmntmAcousticGrandPiano.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				changeInstrument(0);
				chckbxmntmAcousticGrandPiano.setSelected(true);
			}
		});
		mnInstrument.add(chckbxmntmAcousticGrandPiano);

		chckbxmntmBrightAcousticPiano = new JCheckBoxMenuItem("Bright Acoustic Piano");
		chckbxmntmBrightAcousticPiano.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				changeInstrument(1);

				chckbxmntmBrightAcousticPiano.setSelected(true);
			}
		});
		mnInstrument.add(chckbxmntmBrightAcousticPiano);

		chckbxmntmElectricGrandPiano = new JCheckBoxMenuItem("Electric Grand Piano");
		chckbxmntmElectricGrandPiano.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				changeInstrument(2);
				chckbxmntmElectricGrandPiano.setSelected(true);
			}
		});
		mnInstrument.add(chckbxmntmElectricGrandPiano);

		chckbxmntmHonkyTonkPiano = new JCheckBoxMenuItem("Honky-tonk Piano");
		chckbxmntmHonkyTonkPiano.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				changeInstrument(3);
				chckbxmntmHonkyTonkPiano.setSelected(true);
			}
		});
		mnInstrument.add(chckbxmntmHonkyTonkPiano);

		chckbxmntmElectricPiano1 = new JCheckBoxMenuItem("Electric Piano 1");
		chckbxmntmElectricPiano1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				changeInstrument(4);
				chckbxmntmElectricPiano1.setSelected(true);
			}
		});
		mnInstrument.add(chckbxmntmElectricPiano1);

		chckbxmntmElectricPiano2 = new JCheckBoxMenuItem("Electric Piano 2");
		chckbxmntmElectricPiano2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				changeInstrument(5);
				chckbxmntmElectricPiano2.setSelected(true);
			}
		});
		mnInstrument.add(chckbxmntmElectricPiano2);

		chckbxmntmHarpsichord = new JCheckBoxMenuItem("Harpsichord");
		chckbxmntmHarpsichord.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				changeInstrument(6);
				chckbxmntmHarpsichord.setSelected(true);
			}
		});
		mnInstrument.add(chckbxmntmHarpsichord);

		chckbxmntmClavinet = new JCheckBoxMenuItem("Clavinet");
		chckbxmntmClavinet.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				changeInstrument(7);
				chckbxmntmClavinet.setSelected(true);
			}
		});
		mnInstrument.add(chckbxmntmClavinet);

		chckbxmntmUseMidiDefault = new JCheckBoxMenuItem("Use MIDI Default Instruments");
		chckbxmntmUseMidiDefault.setSelected(true);
		chckbxmntmUseMidiDefault.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				changeInstrument(-1);
				chckbxmntmUseMidiDefault.setSelected(true);
			}
		});
		mnInstrument.add(chckbxmntmUseMidiDefault);
	}

	/**
	 * \brief Change instrument of all MIDI tracks through a patch number
	 * 
	 * @param patchNum
	 */
	private void changeInstrument(int patchNum)
	{
		// Untick all instrument menu first
		deselectInstrumentMenu();

		if (midiPlayer != null)
		{
			midiPlayer.setInstrument(patchNum);
			currentPatchNum = patchNum;
		}

	}

	/**
	 * \brief Untick (deselect) all Instrument menu
	 */
	private void deselectInstrumentMenu()
	{
		chckbxmntmAcousticGrandPiano.setSelected(false);
		chckbxmntmBrightAcousticPiano.setSelected(false);
		chckbxmntmElectricGrandPiano.setSelected(false);
		chckbxmntmUseMidiDefault.setSelected(false);
		chckbxmntmHonkyTonkPiano.setSelected(false);
		chckbxmntmElectricPiano1.setSelected(false);
		chckbxmntmElectricPiano2.setSelected(false);
		chckbxmntmHarpsichord.setSelected(false);
		chckbxmntmClavinet.setSelected(false);
	}

	/**
	 * \brief Untick (deselect) all Font menu
	 */
	private void deselectFontSizeMenu()
	{
		mntmSize_8.setSelected(false);
		mntmSize_10.setSelected(false);
		mntmSize_12.setSelected(false);
		mntmSize_14.setSelected(false);
		mntmSize_16.setSelected(false);
		mntmSize_18.setSelected(false);
		mntmSize_24.setSelected(false);
		mntmSize_36.setSelected(false);
		mntmSize_48.setSelected(false);
	}

	/**
	 * \brief Untick (deselect) all Font Family menu
	 */
	private void deselectFontFamilyMenu()
	{
		mntmLucidaConsole.setSelected(false);
		mntmTahoma.setSelected(false);
		mntmVerdana.setSelected(false);
		mntmSegoeUi.setSelected(false);
	}
}
