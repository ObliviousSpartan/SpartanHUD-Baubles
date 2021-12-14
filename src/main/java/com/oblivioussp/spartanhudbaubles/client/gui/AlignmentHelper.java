package com.oblivioussp.spartanhudbaubles.client.gui;

import java.util.Arrays;
import java.util.List;

import com.oblivioussp.spartanhudbaubles.client.gui.AlignmentHelper.Alignment;

public class AlignmentHelper
{
	/**
	 * Sub-alignment for use for vertical alignments
	 */
	public enum VerticalAlignment
	{
		TOP,
		CENTER,
		BOTTOM
	}

	/**
	 * Sub-alignment for use for horizontal alignments
	 */
	public enum HorizontalAlignment
	{
		LEFT,
		CENTER,
		RIGHT
	}
	
	/**
	 * Exact alignment settings that comprise of the two different sub-alignments (horizontal and vertical)
	 */
	public enum Alignment
	{
		TOP_LEFT(VerticalAlignment.TOP, HorizontalAlignment.LEFT),
		TOP_CENTER(VerticalAlignment.TOP, HorizontalAlignment.CENTER),
		TOP_RIGHT(VerticalAlignment.TOP, HorizontalAlignment.RIGHT),
		CENTER_LEFT(VerticalAlignment.CENTER, HorizontalAlignment.LEFT),
		CENTER(VerticalAlignment.CENTER, HorizontalAlignment.CENTER),
		CENTER_RIGHT(VerticalAlignment.CENTER, HorizontalAlignment.RIGHT),
		BOTTOM_LEFT(VerticalAlignment.BOTTOM, HorizontalAlignment.LEFT),
		BOTTOM_CENTER(VerticalAlignment.BOTTOM, HorizontalAlignment.CENTER),
		BOTTOM_RIGHT(VerticalAlignment.BOTTOM, HorizontalAlignment.RIGHT);
		
		private VerticalAlignment vertical;
		private HorizontalAlignment horizontal;
		
		private Alignment(VerticalAlignment vertical, HorizontalAlignment horizontal)
		{
			this.vertical = vertical;
			this.horizontal = horizontal;
		}
		
		public VerticalAlignment getVertical()
		{
			return vertical;
		}
		
		public HorizontalAlignment getHorizontal()
		{
			return horizontal;
		}
		
		public static String[] getValidConfigValues()
		{
			Alignment[] validValues = Alignment.values();
			String[] validStrings = new String[validValues.length];
			for(int i = 0; i < validValues.length; i++)
			{
				Alignment value = validValues[i];
				validStrings[i] = value.toString();
			}
			return validStrings;
		}
	}
}
