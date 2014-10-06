package com.lclion.midigui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Font;

import javax.swing.JLayeredPane;
import javax.swing.border.TitledBorder;
import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

//! Displays a preview of a QWERTY keyboard, indicating the current note played from the main window.
/**
 * 
 * @author LC Lion
 *
 */
public class DialogOnScreenKeyboard extends JDialog
{
	private int fontSize = 14;
	private int fontStyle = Font.BOLD;
	private String fontFamily = "Tahoma";
	private Color clrLowerCase = new Color(0, 153, 0);
	private Color clrUpperCase = new Color(255, 0, 0);
	private Color clrDefaultBackground = new Color(255, 255, 255);
	private Color clrDefaultForeground = new Color(255, 255, 255);

	private final JPanel contentPanel = new JPanel();
	private JLayeredPane layeredPane;
	private JButton btnTilda;
	private JButton btn1;
	private JButton btn2;
	private JButton btn3;
	private JButton btn4;
	private JButton btn5;
	private JButton btn6;
	private JButton btn7;
	private JButton btn8;
	private JButton btn9;
	private JButton btn0;
	private JButton btnTab;
	private JButton btnQ;
	private JButton btnW;
	private JButton btnE;
	private JButton btnR;
	private JButton btnT;
	private JButton btnU;
	private JButton btnI;
	private JButton btnO;
	private JButton btnP;
	private JButton btnY;
	private JButton btnCapsLock;
	private JButton btnA;
	private JButton btnS;
	private JButton btnD;
	private JButton btnF;
	private JButton btnG;
	private JButton btnJ;
	private JButton btnK;
	private JButton btnL;
	private JButton btnH;
	private JButton btnShiftLeft;
	private JButton btnZ;
	private JButton btnX;
	private JButton btnC;
	private JButton btnV;
	private JButton btnB;
	private JButton btnM;
	private JButton btnN;
	private JCheckBox chckbxHighlightLeftShift;
	private JCheckBox chckbxEnableFade;
	private JButton btnMinus;
	private JButton btnEqual;
	private JButton btnBackspace;
	private JButton btnSquareBracketOpen;
	private JButton btnSquareBracketClose;
	private JButton btnBackslash;
	private JButton btnSemicolon;
	private JButton btnApostraphe;
	private JButton btnEnter;
	private JButton btnComma;
	private JButton btnFullStop;
	private JButton btnForwardSlash;
	private JButton btnShiftRight;
	private JButton btnCtrlLeft;
	private JButton btnWinLeft;
	private JButton btnAltLeft;
	private JButton btnSpace;
	private JButton btnAltRight;
	private JButton btnWinRight;
	private JButton btnContext;
	private JButton btnCtrlRight;
	private JCheckBox chckbxHighlightRightShift;
	private JCheckBox chckbxEnableTextColoring;
	private JCheckBox chckbxEnableBackgroundHighlighting;
	private JLabel lblCurrentNote;
	private JCheckBox chckbxAlwaysOnTop;

	/**
	 * Create the dialog.
	 */
	public DialogOnScreenKeyboard(final JFrame parent)
	{
		setResizable(false);
		setBounds(100, 100, 543, 306);
		setTitle("On-Screen Keyboard");
		setIconImage(new ImageIcon(getClass().getResource("/resources/images/icongrey.png")).getImage());

		WindowFocusListener focusListener = new WindowFocusListener()
		{
			@Override
			public void windowGainedFocus(WindowEvent arg0)
			{
				// TODO Auto-generated method stub
				if(!chckbxAlwaysOnTop.isEnabled())
				parent.toFront();
			}

			@Override
			public void windowLostFocus(WindowEvent arg0)
			{
				// TODO Auto-generated method stub
			}

		};
		this.addWindowFocusListener(focusListener);

		// Set a modal exclusion even if other JDialogs has ModalityType.APPLICATION_MODAL
		// Example, if another JDialog has exclusive window focus, this window will still be movable
		setModalExclusionType(JDialog.ModalExclusionType.APPLICATION_EXCLUDE);

		setAlwaysOnTop(true);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		layeredPane = new JLayeredPane();
		layeredPane.setBorder(new TitledBorder(null, "On-Screen Keyboard", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		layeredPane.setBounds(8, 48, 520, 219);
		contentPanel.add(layeredPane);

		btnTilda = new JButton("~");
		btnTilda.setBounds(10, 21, 35, 35);
		layeredPane.add(btnTilda);
		btnTilda.setFont(new Font(fontFamily, fontStyle, fontSize));
		clrDefaultBackground = btnTilda.getBackground();
		clrDefaultForeground = btnTilda.getForeground();

		btn1 = new JButton("1");
		btn1.setBounds(43, 21, 35, 35);
		layeredPane.add(btn1);
		btn1.setFont(new Font(fontFamily, fontStyle, fontSize));

		btn2 = new JButton("2");
		btn2.setFont(new Font(fontFamily, fontStyle, fontSize));
		btn2.setBounds(76, 21, 35, 35);
		layeredPane.add(btn2);

		btn3 = new JButton("3");
		btn3.setFont(new Font(fontFamily, fontStyle, fontSize));
		btn3.setBounds(109, 21, 35, 35);
		layeredPane.add(btn3);

		btn4 = new JButton("4");
		btn4.setFont(new Font(fontFamily, fontStyle, fontSize));
		btn4.setBounds(142, 21, 35, 35);
		layeredPane.add(btn4);

		btn5 = new JButton("5");
		btn5.setFont(new Font(fontFamily, fontStyle, fontSize));
		btn5.setBounds(175, 21, 35, 35);
		layeredPane.add(btn5);

		btn6 = new JButton("6");
		btn6.setFont(new Font(fontFamily, fontStyle, fontSize));
		btn6.setBounds(208, 21, 35, 35);
		layeredPane.add(btn6);

		btn7 = new JButton("7");
		btn7.setFont(new Font(fontFamily, fontStyle, fontSize));
		btn7.setBounds(241, 21, 35, 35);
		layeredPane.add(btn7);

		btn8 = new JButton("8");
		btn8.setFont(new Font(fontFamily, fontStyle, fontSize));
		btn8.setBounds(274, 21, 35, 35);
		layeredPane.add(btn8);

		btn9 = new JButton("9");
		btn9.setFont(new Font(fontFamily, fontStyle, fontSize));
		btn9.setBounds(307, 21, 35, 35);
		layeredPane.add(btn9);

		btn0 = new JButton("0");
		btn0.setFont(new Font(fontFamily, fontStyle, fontSize));
		btn0.setBounds(340, 21, 35, 35);
		layeredPane.add(btn0);

		btnTab = new JButton("Tab");
		btnTab.setFont(new Font(fontFamily, fontStyle, fontSize));
		btnTab.setBounds(10, 55, 53, 35);
		layeredPane.add(btnTab);

		btnQ = new JButton("Q");
		btnQ.setFont(new Font(fontFamily, fontStyle, fontSize));
		btnQ.setBounds(62, 55, 35, 35);
		layeredPane.add(btnQ);

		btnW = new JButton("W");
		btnW.setFont(new Font(fontFamily, fontStyle, fontSize));
		btnW.setBounds(95, 55, 37, 35);
		layeredPane.add(btnW);

		btnE = new JButton("E");
		btnE.setFont(new Font(fontFamily, fontStyle, fontSize));
		btnE.setBounds(130, 55, 35, 35);
		layeredPane.add(btnE);

		btnR = new JButton("R");
		btnR.setFont(new Font(fontFamily, fontStyle, fontSize));
		btnR.setBounds(163, 55, 35, 35);
		layeredPane.add(btnR);

		btnT = new JButton("T");
		btnT.setFont(new Font(fontFamily, fontStyle, fontSize));
		btnT.setBounds(197, 55, 35, 35);
		layeredPane.add(btnT);

		btnU = new JButton("U");
		btnU.setFont(new Font(fontFamily, fontStyle, fontSize));
		btnU.setBounds(263, 55, 35, 35);
		layeredPane.add(btnU);

		btnI = new JButton("I");
		btnI.setFont(new Font(fontFamily, fontStyle, fontSize));
		btnI.setBounds(296, 55, 35, 35);
		layeredPane.add(btnI);

		btnO = new JButton("O");
		btnO.setFont(new Font(fontFamily, fontStyle, fontSize));
		btnO.setBounds(329, 55, 35, 35);
		layeredPane.add(btnO);

		btnP = new JButton("P");
		btnP.setFont(new Font(fontFamily, fontStyle, fontSize));
		btnP.setBounds(362, 55, 35, 35);
		layeredPane.add(btnP);

		btnY = new JButton("Y");
		btnY.setFont(new Font(fontFamily, fontStyle, fontSize));
		btnY.setBounds(230, 55, 35, 35);
		layeredPane.add(btnY);

		btnCapsLock = new JButton("Caps");
		btnCapsLock.setFont(new Font(fontFamily, fontStyle, fontSize));
		btnCapsLock.setBounds(10, 88, 70, 35);
		layeredPane.add(btnCapsLock);

		btnA = new JButton("A");
		btnA.setFont(new Font(fontFamily, fontStyle, fontSize));
		btnA.setBounds(78, 88, 35, 35);
		layeredPane.add(btnA);

		btnS = new JButton("S");
		btnS.setFont(new Font(fontFamily, fontStyle, fontSize));
		btnS.setBounds(111, 88, 37, 35);
		layeredPane.add(btnS);

		btnD = new JButton("D");
		btnD.setFont(new Font(fontFamily, fontStyle, fontSize));
		btnD.setBounds(146, 88, 35, 35);
		layeredPane.add(btnD);

		btnF = new JButton("F");
		btnF.setFont(new Font(fontFamily, fontStyle, fontSize));
		btnF.setBounds(179, 88, 35, 35);
		layeredPane.add(btnF);

		btnG = new JButton("G");
		btnG.setFont(new Font(fontFamily, fontStyle, fontSize));
		btnG.setBounds(212, 88, 35, 35);
		layeredPane.add(btnG);

		btnJ = new JButton("J");
		btnJ.setFont(new Font(fontFamily, fontStyle, fontSize));
		btnJ.setBounds(278, 88, 35, 35);
		layeredPane.add(btnJ);

		btnK = new JButton("K");
		btnK.setFont(new Font(fontFamily, fontStyle, fontSize));
		btnK.setBounds(311, 88, 35, 35);
		layeredPane.add(btnK);

		btnL = new JButton("L");
		btnL.setFont(new Font(fontFamily, fontStyle, fontSize));
		btnL.setBounds(344, 88, 35, 35);
		layeredPane.add(btnL);

		btnH = new JButton("H");
		btnH.setFont(new Font(fontFamily, fontStyle, fontSize));
		btnH.setBounds(245, 88, 35, 35);
		layeredPane.add(btnH);

		btnShiftLeft = new JButton("Shift");
		btnShiftLeft.setFont(new Font(fontFamily, fontStyle, fontSize));
		btnShiftLeft.setBounds(10, 122, 87, 35);
		layeredPane.add(btnShiftLeft);

		btnZ = new JButton("Z");
		btnZ.setFont(new Font(fontFamily, fontStyle, fontSize));
		btnZ.setBounds(95, 122, 35, 35);
		layeredPane.add(btnZ);

		btnX = new JButton("X");
		btnX.setFont(new Font(fontFamily, fontStyle, fontSize));
		btnX.setBounds(128, 122, 35, 35);
		layeredPane.add(btnX);

		btnC = new JButton("C");
		btnC.setFont(new Font(fontFamily, fontStyle, fontSize));
		btnC.setBounds(161, 122, 35, 35);
		layeredPane.add(btnC);

		btnV = new JButton("V");
		btnV.setFont(new Font(fontFamily, fontStyle, fontSize));
		btnV.setBounds(194, 122, 35, 35);
		layeredPane.add(btnV);

		btnB = new JButton("B");
		btnB.setFont(new Font(fontFamily, fontStyle, fontSize));
		btnB.setBounds(227, 122, 35, 35);
		layeredPane.add(btnB);

		btnM = new JButton("M");
		btnM.setFont(new Font(fontFamily, fontStyle, fontSize));
		btnM.setBounds(293, 122, 35, 35);
		layeredPane.add(btnM);

		btnN = new JButton("N");
		btnN.setFont(new Font(fontFamily, fontStyle, fontSize));
		btnN.setBounds(260, 122, 35, 35);
		layeredPane.add(btnN);

		// layeredPane.setEnabled(false);
		contentPanel.setEnabled(false);

		// toggleComponents(layeredPane, false);
		clearHighlights();

		chckbxHighlightLeftShift = new JCheckBox("Highlight Left Shift");
		chckbxHighlightLeftShift.setSelected(true);
		chckbxHighlightLeftShift.setBounds(10, 164, 135, 23);
		layeredPane.add(chckbxHighlightLeftShift);

		chckbxEnableFade = new JCheckBox("Enable Fade History");
		chckbxEnableFade.setSelected(true);
		chckbxEnableFade.setBounds(374, 164, 135, 23);
		layeredPane.add(chckbxEnableFade);

		btnMinus = new JButton("-");
		btnMinus.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnMinus.setBounds(373, 21, 35, 35);
		layeredPane.add(btnMinus);

		btnEqual = new JButton("=");
		btnEqual.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnEqual.setBounds(406, 21, 35, 35);
		layeredPane.add(btnEqual);

		btnBackspace = new JButton("Back");
		btnBackspace.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnBackspace.setBounds(439, 21, 70, 35);
		layeredPane.add(btnBackspace);

		btnSquareBracketOpen = new JButton("[");
		btnSquareBracketOpen.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSquareBracketOpen.setBounds(395, 55, 35, 35);
		layeredPane.add(btnSquareBracketOpen);

		btnSquareBracketClose = new JButton("]");
		btnSquareBracketClose.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSquareBracketClose.setBounds(428, 55, 35, 35);
		layeredPane.add(btnSquareBracketClose);

		btnBackslash = new JButton("\\");
		btnBackslash.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnBackslash.setBounds(461, 55, 48, 35);
		layeredPane.add(btnBackslash);

		btnSemicolon = new JButton(";");
		btnSemicolon.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSemicolon.setBounds(377, 88, 35, 35);
		layeredPane.add(btnSemicolon);

		btnApostraphe = new JButton("'");
		btnApostraphe.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnApostraphe.setBounds(410, 88, 35, 35);
		layeredPane.add(btnApostraphe);

		btnEnter = new JButton("Enter");
		btnEnter.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnEnter.setBounds(443, 88, 66, 35);
		layeredPane.add(btnEnter);

		btnComma = new JButton(",");
		btnComma.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnComma.setBounds(326, 122, 35, 35);
		layeredPane.add(btnComma);

		btnFullStop = new JButton(".");
		btnFullStop.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnFullStop.setBounds(359, 122, 35, 35);
		layeredPane.add(btnFullStop);

		btnForwardSlash = new JButton("/");
		btnForwardSlash.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnForwardSlash.setBounds(392, 122, 35, 35);
		layeredPane.add(btnForwardSlash);

		btnShiftRight = new JButton("Shift");
		btnShiftRight.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnShiftRight.setBounds(425, 122, 84, 35);
		layeredPane.add(btnShiftRight);

		chckbxHighlightRightShift = new JCheckBox("Highlight Right Shift");
		chckbxHighlightRightShift.setBounds(10, 189, 145, 23);
		layeredPane.add(chckbxHighlightRightShift);

		chckbxEnableTextColoring = new JCheckBox("Enable Text Coloring");
		chckbxEnableTextColoring.setSelected(true);
		chckbxEnableTextColoring.setBounds(164, 164, 167, 23);
		layeredPane.add(chckbxEnableTextColoring);

		chckbxEnableBackgroundHighlighting = new JCheckBox("Enable Background Highlighting");
		chckbxEnableBackgroundHighlighting.setSelected(true);
		chckbxEnableBackgroundHighlighting.setBounds(164, 189, 213, 23);
		layeredPane.add(chckbxEnableBackgroundHighlighting);

		chckbxAlwaysOnTop = new JCheckBox("Always on Top");
		chckbxAlwaysOnTop.setSelected(true);
		chckbxAlwaysOnTop.setBounds(374, 190, 135, 23);
		chckbxAlwaysOnTop.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (chckbxAlwaysOnTop.isSelected())
					setAlwaysOnTop(true);
				else
					setAlwaysOnTop(false);
			}
		});
		chckbxAlwaysOnTop.setEnabled(true);
		layeredPane.add(chckbxAlwaysOnTop);

		lblCurrentNote = new JLabel("Current Note");
		lblCurrentNote.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblCurrentNote.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentNote.setBounds(8, 11, 518, 26);
		contentPanel.add(lblCurrentNote);

		// Not really required
		/*
		 * { btnCtrlLeft = new JButton("Ctrl"); btnCtrlLeft.setFont(new Font("Tahoma", Font.BOLD, 14)); btnCtrlLeft.setBounds(10, 154, 48, 35); layeredPane.add(btnCtrlLeft);
		 * 
		 * btnWinLeft = new JButton("Win"); btnWinLeft.setFont(new Font("Tahoma", Font.BOLD, 14)); btnWinLeft.setBounds(56, 154, 48, 35); layeredPane.add(btnWinLeft);
		 * 
		 * btnAltLeft = new JButton("Alt"); btnAltLeft.setFont(new Font("Tahoma", Font.BOLD, 14)); btnAltLeft.setBounds(102, 154, 42, 35); layeredPane.add(btnAltLeft);
		 * 
		 * btnSpace = new JButton(""); btnSpace.setFont(new Font("Tahoma", Font.BOLD, 14)); btnSpace.setBounds(142, 154, 200, 35); layeredPane.add(btnSpace);
		 * 
		 * btnAltRight = new JButton("Alt"); btnAltRight.setFont(new Font("Tahoma", Font.BOLD, 14)); btnAltRight.setBounds(340, 154, 42, 35); layeredPane.add(btnAltRight);
		 * 
		 * btnWinRight = new JButton("Win"); btnWinRight.setFont(new Font("Tahoma", Font.BOLD, 14)); btnWinRight.setBounds(380, 154, 48, 35); layeredPane.add(btnWinRight);
		 * 
		 * btnContext = new JButton(""); btnContext.setFont(new Font("Tahoma", Font.BOLD, 14)); btnContext.setBounds(426, 154, 37, 35); layeredPane.add(btnContext);
		 * 
		 * btnCtrlRight = new JButton("Ctrl"); btnCtrlRight.setFont(new Font("Tahoma", Font.BOLD, 14)); btnCtrlRight.setBounds(461, 154, 48, 35); layeredPane.add(btnCtrlRight); }
		 */

	}

	public void highlightKeys(String keysToHighlight)
	{
		System.out.println(keysToHighlight);
		updateCurrentNoteLabel(keysToHighlight);

		clearHighlights();

		char[] charKeysToHighlight = keysToHighlight.toCharArray();
		// for each component, if char exist (in lower and uppercase), component.setForeground

		Component[] components = layeredPane.getComponents();

		for (char c : charKeysToHighlight)
		{
			if (c == '[' || c == ']')
			{ // Skip Brakets
				continue;
			}
			// System.out.println("Char : " + c);

			for (Component component : components)
			{
				if (component instanceof JButton)
				{
					String stringCompare = ((JButton) component).getText();

					String tmp = Character.toString(c);

					String tmpLower = stringCompare.toLowerCase();
					String tmpUpper = null;
					if (!Character.isDigit(c))
						tmpUpper = stringCompare.toUpperCase();
					// System.out.println("tmp: " + tmp + "tmpLower: " + tmpLower + "tmpUpper: " + tmpUpper);
					if (tmp.equals(tmpLower))
					{
						// System.out.println("Lower Yes, " + tmp);

						if (chckbxEnableTextColoring.isSelected())
							((JButton) component).setForeground(clrLowerCase);

						if (chckbxEnableBackgroundHighlighting.isSelected())
							((JButton) component).setBackground(new Color(153, 255, 153));

						if (chckbxEnableFade.isSelected())
							((JButton) component).setSelected(true);

						((JButton) component).setEnabled(true);
						((JButton) component).setText(tmpLower);

					}
					if (tmp.equals(tmpUpper))
					{
						// System.out.println("Upper Yes, " + tmp);

						if (chckbxEnableTextColoring.isSelected())
							((JButton) component).setForeground(clrUpperCase);

						if (chckbxEnableBackgroundHighlighting.isSelected())
							((JButton) component).setBackground(new Color(255, 153, 153));

						if (chckbxEnableFade.isSelected())
							((JButton) component).setSelected(true);

						((JButton) component).setEnabled(true);
						((JButton) component).setText(tmpUpper);

						if (chckbxHighlightLeftShift.isSelected())
						{
							if (chckbxEnableTextColoring.isSelected())
								btnShiftLeft.setForeground(clrUpperCase);
							if (chckbxEnableBackgroundHighlighting.isSelected())
								btnShiftLeft.setBackground(new Color(255, 153, 153));
							if (chckbxEnableFade.isSelected())
								btnShiftLeft.setEnabled(true);
							// setFade(btnShiftLeft);
						}
						if (chckbxHighlightRightShift.isSelected())
						{
							if (chckbxEnableTextColoring.isSelected())
								btnShiftRight.setForeground(clrUpperCase);
							if (chckbxEnableBackgroundHighlighting.isSelected())
								btnShiftRight.setBackground(new Color(255, 153, 153));
							if (chckbxEnableFade.isSelected())
								btnShiftRight.setEnabled(true);
							// setFade(btnShiftRight);
						}

					}
					if (Character.isDigit(c))
					{
						if (c == stringCompare.charAt(0))
						{
							if (chckbxEnableTextColoring.isSelected())
								((JButton) component).setForeground(clrLowerCase);

							if (chckbxEnableBackgroundHighlighting.isSelected())
								((JButton) component).setBackground(new Color(153, 255, 153));

							if (chckbxEnableFade.isSelected())
								((JButton) component).setSelected(true);

							((JButton) component).setEnabled(true);
							((JButton) component).setText(stringCompare);
						}
					}

				}
			}
			// System.out.println();
		}

	}

	private void updateCurrentNoteLabel(String currentNote)
	{
		// TODO Auto-generated method stub
		lblCurrentNote.setText(currentNote);
	}

	private void clearHighlights()
	{
		// TODO Auto-generated method stub
		Component[] components = layeredPane.getComponents();
		for (Component component : components)
		{
			if (component instanceof JButton)
			{
				((JButton) component).setForeground(clrDefaultForeground);
				((JButton) component).setBackground(clrDefaultBackground);
				((JButton) component).setSelected(false);
				((JButton) component).setEnabled(true);
				((JButton) component).setFocusable(false);
				if (((JButton) component).getText().length() == 1)
				{
					((JButton) component).setText(((JButton) component).getText().toLowerCase());
				}
			}
		}
	}

	private void toggleComponents(JLayeredPane container, boolean enable)
	{
		Component[] components = container.getComponents();
		for (Component component : components)
		{
			component.setEnabled(enable);
		}
	}

	public void setText(String parsedData)
	{
		// TODO Auto-generated method stub
		// lblCurrentNote.setText(parsedData);
	}
}
