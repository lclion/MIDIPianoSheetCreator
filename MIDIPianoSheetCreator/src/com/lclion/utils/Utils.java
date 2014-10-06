package com.lclion.utils;

import java.io.File;

// General Utility for Files
public class Utils
{
	public final static String midi = "midi";
	public final static String mid = "mid";
	public final static String txt = "txt";

	/*
	 * Get the extension of a file.
	 */
	public static String getExtension(File f)
	{
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1)
		{
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}
	
	

}