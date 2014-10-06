package com.lclion.utils;

import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.*;

// Used in FileOpen Dialogue
public class FileExtensionFilter extends FileFilter
{
	private static boolean isMIDI = false;

	public FileExtensionFilter(boolean isMIDI)
	{
		this.isMIDI = isMIDI;
	}

	@Override
	public boolean accept(File f)
	{
		// TODO Auto-generated method stub
		if (f.isDirectory())
			return true;

		String extension = Utils.getExtension(f);
		if (extension != null)
		{
			if (isMIDI)
			{
				if (extension.equals(Utils.mid) || extension.equals(Utils.midi))
					return true;
				else
					return false;
			}
			else
			{
				if (extension.equals(Utils.txt))
					return true;
				else
					return false;
			}
		}

		return false;
	}

	@Override
	public String getDescription()
	{
		// TODO Auto-generated method stub
		String x = null;
		if (isMIDI)
			x = "MIDI File";
		else
			x = "Text File";

		return x;
	}
}
