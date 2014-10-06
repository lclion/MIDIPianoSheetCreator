package com.lclion.midigui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.sound.midi.Track;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JLayeredPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.JSlider;

import java.awt.Color;

import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicSliderUI;

import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JTabbedPane;
import javax.swing.JCheckBox;

import java.awt.Font;

import javax.swing.JFormattedTextField;
import javax.swing.border.CompoundBorder;

import com.lclion.midiparser.MIDIParser;
import com.lclion.midiparser.PatchConverter;
import com.lclion.midiplayer.MIDIPlayer;

//! Dialog which displays advanced track importing options. Displayed when importing MIDI files.
/**
* 
* @author LC Lion
*
*/
public class DialogTrackImport extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JScrollPane spTracks;
	private JList list = null;
	private JLayeredPane lpPreviewSelectedTracks;
	private JSlider sliderTime;
	private JButton btnPlay;
	private JButton btnStop;
	private JSlider sliderVolume;
	private JLabel lblVolume;

	MIDIPlayer midiPlayer = null;
	private boolean isPlaying = false;
	Timer timer = null;
	DefaultListModel<String> listModel = null;
	private ListSelectionListener listSelectionListener = null;
	private static final PatchConverter pc = new PatchConverter();
	private DefaultListModel<String> listModelPlayIndicator = new DefaultListModel<String>();

	public static final int OK_BUTTON = 1;
	public static final int CANCEL_BUTTON = 0;
	private int selectedButton;
	private JList<String> listTracks;
	private JLayeredPane layeredPane;
	private JRadioButton rdbtnNoMeasure;
	private JRadioButton rdbtnMeasureBar;
	private JFormattedTextField tfMeasure;
	private JLabel label;
	private JLabel label_1;
	private JSeparator separator_1;
	private JLabel lbltimeSignatureDetermines;
	private JRadioButton rdbtnAutoTimeSig;
	private JRadioButton rdbtnCustomTimeSig;
	private JTextField tfTop;
	private JTextField tfBottom;
	private JLabel label_3;
	private JLayeredPane lpTrackList;

	private MIDIParser midiParser = null;
	private JTabbedPane tabbedPane;
	private JPanel pnlImport;
	private JPanel pnlLineFormat;
	private JPanel pnlNoteValueFormat;
	private JList listNoteIndicator;
	private JPanel pnlNoteValueColour;
	private JCheckBox chckbxColourizeNoteValues;
	private JLayeredPane lpColour;
	private JCheckBox chckbxUSeAdvanced;
	private JLayeredPane layeredPane_2;
	private JLabel lblOnlySelectedTracks;
	private JLabel lblOctuplenote;
	private JLabel lblQuadruplenote;
	private JLabel lblWholenote;
	private JLabel lblHalfnote;
	private JLabel lblQuarternote;
	private JLabel lblEighthnote;
	private JLabel lblSixteenthnote;
	private JLabel lblThirtysecondnote;
	private JLabel lblSixtyfourthnote;
	private JLabel lblHundredtwentyeighthnote;
	private JLabel lblTwohundredfiftysixthnote;
	private JButton btnColorchooserTest;
	private JList listPlayIndicator;
	private JList list_1;
	private JLabel lblSelectMidiTracks;
	private JLabel lblEvents;
	private JCheckBox chckbxShowColourReference;

	/**
	 * An advanced Dialog showing options to customise which tracks in MIDI file gets imported, as well as other varying functions
	 */
	public DialogTrackImport(JFrame parent, final File file)
	{
		setTitle("Advanced Import Options");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setLocationRelativeTo(parent);
		setBounds(100, 100, 625, 545);

		

		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosed(WindowEvent e)
			{
				System.out.println("jdialog window closed event received");
			}

			public void windowClosing(WindowEvent e)
			{
				System.out.println("jdialog window closing event received");
				midiPlayer.stopMIDI();
				if (timer != null)
					timer.stop();
			}
		});

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		// Swingworker for initialising the dialogTrackImport
		SwingWorker<Void, Void> classInitialiserWorker = new SwingWorker<Void, Void>()
		{

			@Override
			protected Void doInBackground() throws Exception
			{
				midiParser = new MIDIParser(file);
				midiParser.retrieveMidiData(null);
				
				// MIDI Player setup (to preview selected tracks)
				midiPlayer = new MIDIPlayer(file);
				midiPlayer.printPatchList();
				
				
				return null;
			}

			@Override
			protected void done()
			{
				// All code here
				continueInit();
				
			}
		};
		//classInitialiserWorker.execute();
		
		midiParser = new MIDIParser(file);
		midiParser.retrieveMidiData(null);
		
		// MIDI Player setup (to preview selected tracks)
		midiPlayer = new MIDIPlayer(file);
		midiPlayer.printPatchList();

		continueInit();
	}


	private void setupPreviewPlayer()
	{
		// TODO Auto-generated method stub
		lpPreviewSelectedTracks = new JLayeredPane();
		lpPreviewSelectedTracks.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Preview Selected Tracks", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		lpPreviewSelectedTracks.setBounds(10, 335, 599, 147);
		contentPanel.add(lpPreviewSelectedTracks);

		// Slider Time for
		sliderTime = new JSlider();
		sliderTime.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				sliderTime.setValue(((BasicSliderUI) sliderTime.getUI()).valueForXPosition(arg0.getX()));

				midiPlayer.setTick((long) sliderTime.getValue());
			}
		});
		sliderTime.addMouseMotionListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseDragged(MouseEvent arg0)
			{

				midiPlayer.setTick((long) sliderTime.getValue());
				try
				{
					Thread.sleep(40);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		sliderTime.setMaximum((int) midiPlayer.getTickLength());
		sliderTime.setPaintTicks(true);
		sliderTime.setValue(0);
		sliderTime.setOpaque(true);
		sliderTime.setForeground(Color.WHITE);
		// sliderTime.setEnabled(false);
		sliderTime.setBackground(SystemColor.menu);

		btnPlay = new JButton("Play");
		btnPlay.setIcon(new ImageIcon(getClass().getResource("/resources/images/control_play_blue.png")));
		final ArrayList<Long> eventTriggerTime = midiParser.getEventTriggerTime();
		btnPlay.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{

				if (!midiPlayer.isRunning())
				{
					listPlayIndicator.setEnabled(true);

					// Media is playing now, so toggle it to Pause
					btnPlay.setText("Pause");
					btnPlay.setIcon(new ImageIcon(getClass().getResource("/resources/images/control_pause_blue.png")));

					// listTracks.setEnabled(false);
					midiPlayer.unmuteAllTracks();

					boolean isSelected = false;

					// mute unselected tracks in JList and midiPlayer
					for (int x = 0; x <= listModel.getSize(); ++x)
					{
						if (!listTracks.isSelectedIndex(x))
							midiPlayer.muteTrack(x);
						else
							isSelected = true;
					}

					// At least one track is selected, its ok to play
					if (isSelected)
					{

						isPlaying = true;
					}

					midiPlayer.playMIDI();

					final TreeMap<Integer, ArrayList<Long>> tmTracksEvents = midiParser.retrieveTracksEvents();
					for (Entry<Integer, ArrayList<Long>> entry : tmTracksEvents.entrySet())
					{
						Integer key = entry.getKey();
						ArrayList<Long> value = entry.getValue();

						System.out.println(key + " => " + value.size());
					}

					System.out.println("midiParser.getTriggerTimeSize() : " + midiParser.getTriggerTimeSize());

					ActionListener updateListener = new ActionListener()
					{
						int iterator = 0;
						long nextEventTick = 0;
						long currentTick = 0;

						long timeInBetween = 0;

						public void actionPerformed(ActionEvent arg0)
						{
							timeInBetween = ((midiParser.getNextTriggerTime(iterator + 1) - midiParser.getNextTriggerTime(iterator)) / 6);

							if (midiPlayer.getCurrentTick() <= midiPlayer.getTickLength())
							{
								sliderTime.setValue((int) midiPlayer.getCurrentTick());

								// Force the proper iterator to be retrieved incase the user changed slider manually
								iterator = (midiParser.getNextTriggerTimeElement(midiPlayer.getCurrentTick()));

								// Check for play indicator
								if (midiPlayer.getCurrentTick() >= midiParser.getNextTriggerTime(iterator))
								{
									// For the purposes of consistency, and since midiPlayer.getCurrentTick() can be volatile during the
									// length of this calculation, initialise the value
									long currentTick = midiPlayer.getCurrentTick();

									// for(every track in sequence)
									// if(track has event)
									// listPlayIndicator.addSelectionInterval(x, x);
									int trackNum = -1;
									for (Entry<Integer, ArrayList<Long>> entry : tmTracksEvents.entrySet())
									{
										++trackNum;

										// This if statement skips tracks that have 0 notes
										//if (!entry.getValue().isEmpty())
										//{
											if (entry.getValue().contains(midiParser.getNextTriggerTime(iterator)))
											{
												//listPlayIndicator.addSelectionInterval(trackNum, trackNum);
												listPlayIndicator.addSelectionInterval(entry.getKey() - 1, entry.getKey() - 1);
											}
										//}

										/*
										 * if(value.contains(midiPlayer.getCurrentTick())) { listPlayIndicator.addSelectionInterval(key, key); }
										 */
									}

									// listPlayIndicator.addSelectionInterval(0, 0);
									// listPlayIndicator.addSelectionInterval(1, 1);
									if (iterator + 1 != midiParser.getTriggerTimeSize() - 1)
									{
										iterator += 1;
									}
								}
								// list turn on and off

								// if(midiPlayer.getCurrentTick() >= (midiParser.getNextTriggerTime(iterator)-timeInBetween))
								if (midiPlayer.getCurrentTick() >= (midiParser.getNextTriggerTime(iterator) - timeInBetween))
								{
									for (int x = 0; x <= (listModelPlayIndicator.getSize() - 1); ++x)
									{
										listModelPlayIndicator.set(x, "\u266B Event");
									}
									listPlayIndicator.clearSelection();
								}

							}
							else
							{
								listPlayIndicator.setEnabled(false);
								sliderTime.setValue(0);
								isPlaying = false;

								midiPlayer.stopMIDI();
								timer.stop();
								btnPlay.setText("Play");
								btnPlay.setIcon(new ImageIcon(getClass().getResource("/resources/images/control_play_blue.png")));

							}
							if (!midiPlayer.isRunning())
							{
								listPlayIndicator.setEnabled(false);
								btnPlay.setText("Play");
								btnPlay.setIcon(new ImageIcon(getClass().getResource("/resources/images/control_play_blue.png")));
							}
							else
								listPlayIndicator.setEnabled(true);
						}
					};
					timer = new Timer(1, updateListener);
					timer.start();

				}
				else
				{
					listPlayIndicator.setEnabled(false);
					// Media is playing, toggle it to Pause
					midiPlayer.pauseMIDI();
					btnPlay.setText("Play");
					btnPlay.setIcon(new ImageIcon(getClass().getResource("/resources/images/control_play_blue.png")));
				}
			}
		});
		btnPlay.setForeground(Color.GRAY);

		btnStop = new JButton("Stop");
		btnStop.setIcon(new ImageIcon(getClass().getResource("/resources/images/control_stop_blue.png")));
		btnStop.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				listTracks.setEnabled(true);

				// Unmute all
				sliderTime.setValue(0);
				isPlaying = false;
				if (timer != null)
					timer.stop();
				btnPlay.setText("Play");
				btnPlay.setIcon(new ImageIcon(getClass().getResource("/resources/images/control_play_blue.png")));
				midiPlayer.stopMIDI();
				listPlayIndicator.setEnabled(false);
			}
		});
		btnStop.setForeground(Color.GRAY);
		// btnStop.setEnabled(false);
		btnStop.setBackground(Color.GRAY);

		sliderVolume = new JSlider();
		sliderVolume.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				midiPlayer.setVolume(sliderVolume.getValue());
			}
		});
		sliderVolume.setValue(100);
		sliderVolume.setPaintTicks(true);
		sliderVolume.setPaintLabels(true);
		sliderVolume.setBackground(SystemColor.menu);
		sliderVolume.putClientProperty("JSlider.isFilled", Boolean.TRUE);

		sliderVolume.setValue(100);

		lblVolume = new JLabel("Volume:");
	}

	private void setupImportListing(Track[] tracks)
	{
		// TODO Setup GUI, Organise tracks, and make it play using midiPlayer

		// --------------------------------------Setup GUI
		pnlImport = new JPanel();
		tabbedPane.addTab("Import", null, pnlImport, null);
		pnlImport.setLayout(null);

		lpTrackList = new JLayeredPane();
		lpTrackList.setBounds(10, 11, 554, 263);
		pnlImport.add(lpTrackList);
		lpTrackList.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Instrument Import", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		spTracks = new JScrollPane();
		spTracks.setBounds(10, 39, 534, 213);
		lpTrackList.add(spTracks);
		spTracks.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		spTracks.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		// --------------------------------------Organise tracks, channels, patches, and instrument name
		// Make listModel for it to be inserted into a JList
		listModel = new DefaultListModel<String>();
		listModelPlayIndicator = new DefaultListModel<String>();
		ArrayList<Integer> trackEvents = midiParser.getTotalTrackEventsNumber();
		
		int trackNum = -1;
		for (Track track : tracks)
		{
			++trackNum;
			System.out.println("Track : " + trackNum);
			// User doesnt really need to know what tracks the midi has
			// + " (Track " + (listModel.getSize() + 1) + ")"

			// This commented if statement gets rid of tracks that have 0 notes
			/*if (trackEvents.get(trackNum + 1) != 0)
			{*/
				listModel.addElement(pc.getPatchName(midiParser.getPatchNumber(trackNum)) + " (" + trackEvents.get(trackNum + 1) + " notes)");
				listModelPlayIndicator.addElement("\u266B Event"); // We add this spacing to create artificial spacing in ScrollPane Row Header
			//}

		}
		// listModelPlayIndicator.addElement("           "); // Add an extra dummy element so the List doesnt resize itself

		// -------------------------------------- GUI
		listTracks = new JList<String>(listModel);
		listTracks.setBackground(Color.LIGHT_GRAY);
		listTracks.setFont(new Font("Arial", Font.PLAIN, 12));
		spTracks.setViewportView(listTracks);
		listTracks.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listTracks.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listTracks.setLayoutOrientation(JList.VERTICAL);
		listTracks.setVisibleRowCount(-1);

		// Make each list to be toggle based
		listTracks.setSelectionModel(new DefaultListSelectionModel()
		{
			@Override
			public void setSelectionInterval(int index0, int index1)
			{
				if (super.isSelectedIndex(index0))
				{
					super.removeSelectionInterval(index0, index1);
				}
				else
				{
					super.addSelectionInterval(index0, index1);
				}
			}
		});


		listPlayIndicator = new JList<String>(listModelPlayIndicator);
		listPlayIndicator.setEnabled(false);
		listPlayIndicator.setFont(new Font("Arial", Font.PLAIN, 12));
		listPlayIndicator.setOpaque(true);
		listPlayIndicator.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent arg0)
			{

				for (int value : listPlayIndicator.getSelectedIndices())
				{
					listModelPlayIndicator.set(value, "\u266B Event");
				}

			}
		});
		spTracks.setRowHeaderView(listPlayIndicator);
		listPlayIndicator.setBackground(Color.LIGHT_GRAY);
		listPlayIndicator.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listPlayIndicator.setSelectionModel(new DefaultListSelectionModel()
		{
			@Override
			public void setSelectionInterval(int index0, int index1)
			{
				if (super.isSelectedIndex(index0))
				{
					super.removeSelectionInterval(index0, index1);
				}
				else
				{
					super.addSelectionInterval(index0, index1);
				}
			}
		});

		lblSelectMidiTracks = new JLabel("Select Instruments to import:");
		lblSelectMidiTracks.setBounds(69, 24, 179, 14);
		lpTrackList.add(lblSelectMidiTracks);

		lblEvents = new JLabel("Events:");
		lblEvents.setBounds(10, 24, 59, 14);
		lpTrackList.add(lblEvents);

		// Let's make a listener for the JList's Selection model, so when toggling through
		// list, it will mute or unmute the track
		listSelectionListener = new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent listSelectionEvent)
			{
				/*
				 * System.out.print("First index: " + listSelectionEvent.getFirstIndex()); System.out.print(", Last index: " + listSelectionEvent.getLastIndex()); boolean adjust = listSelectionEvent.getValueIsAdjusting(); System.out.println(", Adjusting? " + adjust);
				 */

				// int selections[] = listTracks.getSelectedIndices();

				if (listTracks.isSelectedIndex(listSelectionEvent.getFirstIndex()))
				{
					// unmute
					midiPlayer.unmuteTrack((listSelectionEvent.getFirstIndex()));
					System.out.println("Unmuted Track " + (listSelectionEvent.getFirstIndex() + 1));
				}
				else
				{
					// mute
					midiPlayer.muteTrack((listSelectionEvent.getFirstIndex()));
					System.out.println("Muted Track " + (listSelectionEvent.getFirstIndex() + 1));
				}

				if (listTracks.isSelectedIndex(listSelectionEvent.getLastIndex()))
				{
					// unmute
					midiPlayer.unmuteTrack((listSelectionEvent.getLastIndex()));
					System.out.println("Unmuted Track " + (listSelectionEvent.getLastIndex() + 1));
				}
				else
				{
					// mute
					midiPlayer.muteTrack((listSelectionEvent.getLastIndex()));
					System.out.println("Muted Track " + (listSelectionEvent.getLastIndex() + 1));
				}

				int result[] = listTracks.getSelectedIndices();
				for (int x = 0; x <= result.length - 1; ++x)
				{
					result[x] = result[x] + 1;
				}
				System.out.println("Selected Track Indexes : " + Arrays.toString(result));
				// int ar[] = listTracks.getSelectedIndices();
				// System.out.println("Selected Indexs : " + ar[0]);

			}
		};
		listTracks.addListSelectionListener(listSelectionListener);
		// Default select all in JList
		for (int x = 0; x < listModel.getSize(); x++)
		{
			listTracks.setSelectedIndex(x);
			// listPlayIndicator.setSelectedIndex(x);
		}
	}
	
	private void continueInit()
	{
		//
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 599, 313);
		contentPanel.add(tabbedPane);

		// nothing to notify yet
		// Setup Track Listing from MIDI file and display to JList
		setupImportListing(midiPlayer.getTracks());

		setupPreviewPlayer();
		
		lblOnlySelectedTracks = new JLabel("<html>Only highlighted tracks above (on Import tab) will be played. This is useful to exclude any drums or percussions in the MIDI file, or to exclude unnecessary tracks. Toggle the tracks while playing to hear what will be imported.</html>");

		GroupLayout gl_lpPreviewSelectedTracks = new GroupLayout(lpPreviewSelectedTracks);
		gl_lpPreviewSelectedTracks.setHorizontalGroup(gl_lpPreviewSelectedTracks.createParallelGroup(Alignment.LEADING).addGroup(gl_lpPreviewSelectedTracks.createSequentialGroup().addGap(2).addGroup(gl_lpPreviewSelectedTracks.createParallelGroup(Alignment.LEADING).addComponent(lblOnlySelectedTracks, GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE).addComponent(sliderTime, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE).addGroup(Alignment.TRAILING, gl_lpPreviewSelectedTracks.createSequentialGroup().addComponent(btnPlay, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE).addGap(6).addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, 271, Short.MAX_VALUE).addComponent(lblVolume, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(sliderVolume, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))).addContainerGap()));
		gl_lpPreviewSelectedTracks.setVerticalGroup(gl_lpPreviewSelectedTracks.createParallelGroup(Alignment.TRAILING).addGroup(gl_lpPreviewSelectedTracks.createSequentialGroup().addComponent(lblOnlySelectedTracks, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE).addGap(8).addComponent(sliderTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_lpPreviewSelectedTracks.createParallelGroup(Alignment.LEADING).addComponent(btnPlay, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE).addGroup(gl_lpPreviewSelectedTracks.createParallelGroup(Alignment.BASELINE).addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE).addComponent(lblVolume)).addGroup(gl_lpPreviewSelectedTracks.createSequentialGroup().addGap(3).addComponent(sliderVolume, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))).addGap(12)));
		lpPreviewSelectedTracks.setLayout(gl_lpPreviewSelectedTracks);

		

		pnlLineFormat = new JPanel();
		tabbedPane.addTab("Line Format", null, pnlLineFormat, null);
		pnlLineFormat.setLayout(null);

		layeredPane = new JLayeredPane();
		layeredPane.setBounds(10, 11, 574, 176);
		pnlLineFormat.add(layeredPane);
		layeredPane.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "New Line Placement", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		rdbtnNoMeasure = new JRadioButton("Do not add new lines");
		rdbtnNoMeasure.setBounds(6, 67, 143, 23);
		layeredPane.add(rdbtnNoMeasure);

		rdbtnMeasureBar = new JRadioButton("Place a new line every: ");
		rdbtnMeasureBar.setSelected(true);
		rdbtnMeasureBar.setBounds(6, 41, 143, 23);
		layeredPane.add(rdbtnMeasureBar);

		ButtonGroup btngrpMeasure = new ButtonGroup();
		btngrpMeasure.add(rdbtnMeasureBar);
		btngrpMeasure.add(rdbtnNoMeasure);

		tfMeasure = new JFormattedTextField();
		tfMeasure.setText("1");
		tfMeasure.setColumns(10);
		tfMeasure.setBounds(149, 42, 21, 20);
		layeredPane.add(tfMeasure);

		rdbtnMeasureBar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				tfMeasure.setEnabled(true);
			}
		});

		rdbtnNoMeasure.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				tfMeasure.setEnabled(false);
			}
		});

		label = new JLabel("<html><i>A new line is calculated with relation to the time signature's measure / bar</i><html>");
		label.setBounds(10, 11, 463, 34);
		layeredPane.add(label);

		label_1 = new JLabel("measures / bars");
		label_1.setBounds(180, 45, 89, 14);
		layeredPane.add(label_1);

		separator_1 = new JSeparator();
		separator_1.setBounds(6, 91, 558, 8);
		layeredPane.add(separator_1);

		lbltimeSignatureDetermines = new JLabel("<html><i>Time Signature determines how many beats and types of beats there are in one measure/bar</i><html>");
		lbltimeSignatureDetermines.setEnabled(false);
		lbltimeSignatureDetermines.setBounds(10, 91, 554, 28);
		layeredPane.add(lbltimeSignatureDetermines);

		rdbtnAutoTimeSig = new JRadioButton("Auto detect Time Signature");
		rdbtnAutoTimeSig.setEnabled(false);
		rdbtnAutoTimeSig.setSelected(true);
		rdbtnAutoTimeSig.setBounds(6, 115, 178, 23);
		layeredPane.add(rdbtnAutoTimeSig);

		rdbtnCustomTimeSig = new JRadioButton("Set up a custom Time Signature :");
		rdbtnCustomTimeSig.setEnabled(false);
		rdbtnCustomTimeSig.setBounds(6, 141, 190, 23);
		layeredPane.add(rdbtnCustomTimeSig);

		ButtonGroup btngrpTimeSig = new ButtonGroup();
		btngrpTimeSig.add(rdbtnAutoTimeSig);
		btngrpTimeSig.add(rdbtnCustomTimeSig);

		tfTop = new JTextField();
		tfTop.setText("4");
		tfTop.setColumns(10);
		tfTop.setBounds(202, 142, 28, 20);
		tfTop.setEnabled(false);
		layeredPane.add(tfTop);

		tfBottom = new JTextField();
		tfBottom.setText("4");
		tfBottom.setColumns(10);
		tfBottom.setBounds(250, 142, 28, 20);
		tfBottom.setEnabled(false);
		layeredPane.add(tfBottom);

		label_3 = new JLabel("/");
		label_3.setEnabled(false);
		label_3.setBounds(240, 145, 11, 14);
		layeredPane.add(label_3);

		rdbtnCustomTimeSig.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				tfTop.setEnabled(true);
				tfBottom.setEnabled(true);
			}
		});

		rdbtnAutoTimeSig.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				tfTop.setEnabled(false);
				tfBottom.setEnabled(false);
			}
		});

		pnlNoteValueColour = new JPanel();
		tabbedPane.addTab("Note Value Colour", null, pnlNoteValueColour, null);
		tabbedPane.setEnabledAt(2, true);
		pnlNoteValueColour.setLayout(null);

		chckbxColourizeNoteValues = new JCheckBox("Indicate Note Values by Colour?");
		chckbxColourizeNoteValues.setSelected(true);
		chckbxColourizeNoteValues.setBounds(6, 10, 578, 23);
		pnlNoteValueColour.add(chckbxColourizeNoteValues);

		lpColour = new JLayeredPane();
		lpColour.setBorder(new TitledBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Colour Options", TitledBorder.LEADING, TitledBorder.TOP, null, null), "Colour Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		lpColour.setBounds(6, 40, 578, 234);
		pnlNoteValueColour.add(lpColour);

		lblOctuplenote = new JLabel("octupleNote");
		lblOctuplenote.setBounds(10, 23, 95, 14);
		lblOctuplenote.setForeground(new Color(0, 100, 0));
		lpColour.add(lblOctuplenote);

		lblQuadruplenote = new JLabel("quadrupleNote");
		lblQuadruplenote.setBounds(10, 48, 95, 14);
		lblQuadruplenote.setForeground(new Color(0, 204, 0));
		lpColour.add(lblQuadruplenote);

		lblWholenote = new JLabel("wholeNote");
		lblWholenote.setBounds(10, 73, 95, 14);
		lblWholenote.setForeground(new Color(51, 255, 51));
		lpColour.add(lblWholenote);

		lblHalfnote = new JLabel("halfNote");
		lblHalfnote.setBounds(10, 98, 100, 14);
		lblHalfnote.setForeground(new Color(128, 255, 0));
		lpColour.add(lblHalfnote);

		lblQuarternote = new JLabel("quarterNote");
		lblQuarternote.setBounds(10, 123, 95, 14);
		lblQuarternote.setForeground(new Color(204, 204, 0));
		lpColour.add(lblQuarternote);

		lblEighthnote = new JLabel("eighthNote");
		lblEighthnote.setBounds(10, 148, 95, 14);
		lblEighthnote.setForeground(new Color(255, 128, 0));
		lpColour.add(lblEighthnote);

		lblSixteenthnote = new JLabel("sixteenthNote");
		lblSixteenthnote.setBounds(10, 173, 95, 14);
		lblSixteenthnote.setForeground(new Color(255, 151, 151));
		lpColour.add(lblSixteenthnote);

		lblThirtysecondnote = new JLabel("thirtySecondNote");
		lblThirtysecondnote.setBounds(10, 198, 95, 14);
		lblThirtysecondnote.setForeground(new Color(255, 0, 0));
		lpColour.add(lblThirtysecondnote);

		lblSixtyfourthnote = new JLabel("sixtyFourthNote");
		lblSixtyfourthnote.setBounds(153, 23, 158, 14);
		lblSixtyfourthnote.setForeground(new Color(153, 0, 0));
		lpColour.add(lblSixtyfourthnote);

		lblHundredtwentyeighthnote = new JLabel("hundredTwentyEighthNote");
		lblHundredtwentyeighthnote.setBounds(153, 48, 158, 14);
		lblHundredtwentyeighthnote.setForeground(new Color(153, 0, 0));
		lpColour.add(lblHundredtwentyeighthnote);

		lblTwohundredfiftysixthnote = new JLabel("twoHundredFiftySixthNote");
		lblTwohundredfiftysixthnote.setBounds(153, 73, 158, 14);
		lblTwohundredfiftysixthnote.setForeground(new Color(153, 0, 0));
		lpColour.add(lblTwohundredfiftysixthnote);

		btnColorchooserTest = new JButton("ColorChooser Test");
		btnColorchooserTest.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				JColorChooser tcc = new JColorChooser(new Color(0, 0, 0));
				tcc.showDialog(DialogTrackImport.this, "Colour Chooser", new Color(255, 255, 255));
			}
		});
		btnColorchooserTest.setBounds(153, 144, 158, 23);
		lpColour.add(btnColorchooserTest);
		
		chckbxShowColourReference = new JCheckBox("Show Colour Reference Window after import");
		chckbxShowColourReference.setBounds(153, 114, 331, 23);
		lpColour.add(chckbxShowColourReference);

		pnlNoteValueFormat = new JPanel();
		tabbedPane.addTab("Note Value Format", null, pnlNoteValueFormat, null);

		pnlNoteValueFormat.setLayout(null);

		chckbxUSeAdvanced = new JCheckBox("Use Advanced Formatting");
		chckbxUSeAdvanced.setBounds(6, 8, 578, 23);
		pnlNoteValueFormat.add(chckbxUSeAdvanced);

		layeredPane_2 = new JLayeredPane();
		layeredPane_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Advanced Note Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		layeredPane_2.setBounds(6, 38, 578, 236);
		pnlNoteValueFormat.add(layeredPane_2);

		tabbedPane.setEnabledAt(3, false);

		// Dialog OK / Cancel
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent arg0)
					{
						midiPlayer.stopMIDI();
						if (timer != null)
							timer.stop();

						if (listTracks.getSelectedIndices() != null)
						{

						}
						// if listTracks.getIndex is null
						// JDialog no! must contain at least one track to import!
						// else
						selectedButton = OK_BUTTON;
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent arg0)
					{
						selectedButton = CANCEL_BUTTON;
						setVisible(false);
						midiPlayer.stopMIDI();
						if (timer != null)
							timer.stop();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		//setVisible(true);
	}

	public int getSelectedButton()
	{
		return selectedButton;
	}

	public int getMeasures()
	{
		int returnValue = 0;

		// rdbtnMeasureBar is not selected, meaning do not add new lines for x amount of measure
		if (!rdbtnMeasureBar.isSelected())
		{
			returnValue = 0;
		}
		else
			returnValue = Integer.valueOf(tfMeasure.getText());
		return returnValue;
	}

	public int[] getSelectedTracks()
	{	// TODO: Fix so the really correctly selected index gets sent
		int result[] = listTracks.getSelectedIndices();
		for (int x = 0; x <= result.length - 1; ++x)
		{
			result[x] = result[x] + 1;
		}
		System.out.println("Selected Track Indexes : " + Arrays.toString(result));
		return result;

	}

	public boolean isCustomMeasure()
	{
		return rdbtnMeasureBar.isSelected();
	}

	public boolean isColourise()
	{
		return chckbxColourizeNoteValues.isSelected();
	}
	
	public boolean showColourRefAfterImport()
	{
		return chckbxShowColourReference.isSelected();
	}
}
