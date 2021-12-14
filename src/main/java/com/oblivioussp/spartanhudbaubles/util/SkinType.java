package com.oblivioussp.spartanhudbaubles.util;

import com.oblivioussp.spartanhudbaubles.client.gui.AlignmentHelper.Alignment;

/**
 * Contains all the Skin types currently available in this mod
 * @author ObliviousSpartan
 *
 */
public enum SkinType 
{
	NONE,
	HOTBAR_STYLE,
	TOOLTIP_STYLE,
	MINIMAL;
	
	public static String[] getValidConfigValues()
	{
		SkinType[] validValues = SkinType.values();
		String[] validStrings = new String[validValues.length];
		for(int i = 0; i < validValues.length; i++)
		{
			SkinType value = validValues[i];
			validStrings[i] = value.toString();
		}
		return validStrings;
	}
}
