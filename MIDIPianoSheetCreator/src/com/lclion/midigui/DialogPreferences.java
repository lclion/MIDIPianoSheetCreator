package com.lclion.midigui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JSeparator;

//! The main preference window where users can configure how MIDI files get imported.
/**
* 
* @author LC Lion
*
*/
public class DialogPreferences extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JRadioButton rdbtnAutoTimeSig;
	private JRadioButton rdbtnCustomTimeSig;
	private JTextField tfTop;
	private JTextField tfBottom;
	private JLabel lblTimeSignatureIs;

	private JLayeredPane lpNewLinePlacement;
	private JRadioButton rdbtnMeasureBar;
	private JRadioButton rdbtnNoMeasure;
	private JTextField tfMeasure;
	private JLabel lblANewLine;

	private JLayeredPane lpAdvancedImportOptions;
	private JRadioButton rdbtnUseAdvancedImport;
	private JRadioButton rdbtnUseDefaultSetting;
	private JLabel lblMeasuresBars;
	private JLabel lblDivider;
	private JSeparator separator;

	/**
	 * Create the dialog.
	 */
	public DialogPreferences(JFrame parent)
	{
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(parent);

		setTitle("Import Preferences");
		setBounds(100, 100, 463, 348);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(contentPanel, BorderLayout.CENTER);

		contentPanel.setLayout(null);

		ButtonGroup btngrpTimeSig = new ButtonGroup();

		{
			lpNewLinePlacement = new JLayeredPane();
			lpNewLinePlacement.setBounds(10, 100, 438, 182);
			lpNewLinePlacement.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "New Line Placement", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			lpNewLinePlacement.setEnabled(false);
		}
		contentPanel.add(lpNewLinePlacement);

		rdbtnNoMeasure = new JRadioButton("Do not add new lines");
		rdbtnNoMeasure.setEnabled(false);
		rdbtnNoMeasure.setBounds(6, 67, 143, 23);
		lpNewLinePlacement.add(rdbtnNoMeasure);

		rdbtnMeasureBar = new JRadioButton("Place a new line every: ");
		rdbtnMeasureBar.setEnabled(false);
		rdbtnMeasureBar.setSelected(true);
		rdbtnMeasureBar.setBounds(6, 41, 143, 23);
		lpNewLinePlacement.add(rdbtnMeasureBar);

		ButtonGroup btngrpMeasure = new ButtonGroup();
		btngrpMeasure.add(rdbtnMeasureBar);
		btngrpMeasure.add(rdbtnNoMeasure);

		tfMeasure = new JTextField();
		tfMeasure.setEnabled(false);
		tfMeasure.setText("1");
		tfMeasure.setBounds(149, 42, 21, 20);
		lpNewLinePlacement.add(tfMeasure);
		tfMeasure.setColumns(10);
		tfMeasure.getText();

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

		{
			lblANewLine = new JLabel("<html><i>A new line is calculated with relation to the time signature's measure / bar</i><html>");
			lblANewLine.setEnabled(false);
			lblANewLine.setBounds(10, 11, 463, 34);
			lpNewLinePlacement.add(lblANewLine);
		}

		lblMeasuresBars = new JLabel("measures / bars");
		lblMeasuresBars.setEnabled(false);
		lblMeasuresBars.setBounds(180, 45, 89, 14);
		lpNewLinePlacement.add(lblMeasuresBars);

		separator = new JSeparator();
		separator.setBounds(10, 97, 422, 7);
		lpNewLinePlacement.add(separator);

		lblTimeSignatureIs = new JLabel("<html><i>Time Signature is used to provide easier reading by placing new lines per measure/bar</i><html>");
		lblTimeSignatureIs.setBounds(10, 97, 463, 28);
		lpNewLinePlacement.add(lblTimeSignatureIs);
		lblTimeSignatureIs.setEnabled(false);

		rdbtnAutoTimeSig = new JRadioButton("Auto detect Time Signature");
		rdbtnAutoTimeSig.setBounds(6, 121, 178, 23);
		lpNewLinePlacement.add(rdbtnAutoTimeSig);
		rdbtnAutoTimeSig.setSelected(true);
		btngrpTimeSig.add(rdbtnAutoTimeSig);
		rdbtnAutoTimeSig.setEnabled(false);

		rdbtnCustomTimeSig = new JRadioButton("Set up a custom Time Signature :");
		rdbtnCustomTimeSig.setBounds(6, 147, 190, 23);
		lpNewLinePlacement.add(rdbtnCustomTimeSig);
		btngrpTimeSig.add(rdbtnCustomTimeSig);
		rdbtnCustomTimeSig.setEnabled(false);

		tfTop = new JTextField();
		tfTop.setBounds(202, 148, 28, 20);
		lpNewLinePlacement.add(tfTop);
		tfTop.setText("4");
		tfTop.setColumns(10);
		tfTop.setEnabled(false);

		tfBottom = new JTextField();
		tfBottom.setBounds(250, 148, 28, 20);
		lpNewLinePlacement.add(tfBottom);
		tfBottom.setText("4");
		tfBottom.setColumns(10);
		tfBottom.setEnabled(false);

		lblDivider = new JLabel("/");
		lblDivider.setBounds(240, 151, 11, 14);
		lpNewLinePlacement.add(lblDivider);
		lblDivider.setEnabled(false);

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

		lpAdvancedImportOptions = new JLayeredPane();
		lpAdvancedImportOptions.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "MIDI Import Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		lpAdvancedImportOptions.setBounds(10, 11, 438, 78);
		contentPanel.add(lpAdvancedImportOptions);

		rdbtnUseAdvancedImport = new JRadioButton("Always Display Advanced Import Options when Opening a MIDI File");
		rdbtnUseAdvancedImport.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				toggleEnableTimeSig(false);
				toggleEnableNewLinePlacement(false);

				// Reset controls to default
				tfTop.setEnabled(false);
				tfBottom.setEnabled(false);
				rdbtnAutoTimeSig.setSelected(true);
				rdbtnMeasureBar.setSelected(true);
				tfMeasure.setEnabled(false);
			}
		});
		rdbtnUseAdvancedImport.setBounds(6, 19, 426, 23);
		lpAdvancedImportOptions.add(rdbtnUseAdvancedImport);

		rdbtnUseDefaultSetting = new JRadioButton("Use Default Settings Below when Opening a MIDI File");
		rdbtnUseDefaultSetting.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//toggleEnableTimeSig(true);
				toggleEnableNewLinePlacement(true);
			}
		});
		rdbtnUseDefaultSetting.setBounds(6, 45, 426, 23);
		lpAdvancedImportOptions.add(rdbtnUseDefaultSetting);

		ButtonGroup btngrpSettingUse = new ButtonGroup();
		btngrpSettingUse.add(rdbtnUseAdvancedImport);
		btngrpSettingUse.add(rdbtnUseDefaultSetting);

		rdbtnUseAdvancedImport.setSelected(true);

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
						setVisible(false);
						dispose();
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
					public void actionPerformed(ActionEvent e)
					{
						setVisible(false);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		// pack();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(parent);
	}

	private void toggleEnableTimeSig(boolean value)
	{
		rdbtnAutoTimeSig.setEnabled(value);
		rdbtnCustomTimeSig.setEnabled(value);
		// tfTop.setEnabled(value);
		// tfBottom.setEnabled(value);
		lblTimeSignatureIs.setEnabled(value);
		lblDivider.setEnabled(value);
	}

	private void toggleEnableNewLinePlacement(boolean value)
	{
		lpNewLinePlacement.setEnabled(value);
		rdbtnMeasureBar.setEnabled(value);
		rdbtnNoMeasure.setEnabled(value);
		tfMeasure.setEnabled(value);
		lblANewLine.setEnabled(value);
		lblMeasuresBars.setEnabled(value);
	}

	// if rdbtnMeasureBar is selected, return true or false
	public boolean isCustomMeasure()
	{
		return rdbtnMeasureBar.isSelected();
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

	public boolean isAdvancedTrackImport()
	{
		return rdbtnUseAdvancedImport.isSelected();
	}
}
