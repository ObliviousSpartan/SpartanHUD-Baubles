package com.oblivioussp.spartanhudbaubles.client.gui;


import com.oblivioussp.spartanhudbaubles.client.gui.AlignmentHelper.Alignment;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class HudElement 
{
	protected boolean isEnabled = true;
	
	protected int width, height;
	
	public HudElement(int elementWidth, int elementHeight)
	{
		width = elementWidth;
		height = elementHeight;
	}
	
	public boolean isEnabled()
	{
		return isEnabled;
	}
	
	public void setEnabled(boolean isEnabled) 
	{
		this.isEnabled = isEnabled;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getAlignedX(Alignment align, int offset, ScaledResolution res)
	{
		switch(align.getHorizontal())
		{
			case LEFT:
				return /*5 +*/ offset;
			case CENTER:
				return (res.getScaledWidth() / 2) - (width / 2) + offset;
			case RIGHT:
				return res.getScaledWidth() - width /*- 5*/ + offset;
			default:
				return 0;
		}
	}
	
	public int getAlignedY(Alignment align, int offset, ScaledResolution res)
	{
		switch(align.getVertical())
		{
			case TOP:
				return /*5 +*/ offset;
			case CENTER:
			{
				switch(align.getHorizontal())
				{
					// Check to see if it encroaches the crosshairs. If so, move it down a bit
					case CENTER:
						return (res.getScaledHeight() / 2) + 26 + offset;
					default:
						return (res.getScaledHeight() / 2) - (height / 2) + offset;
				}
			}
			case BOTTOM:
			{
				switch(align.getHorizontal())
				{
					// Check to see if it encroaches the hotbar. If so, move it up a bit
					case CENTER:
						return res.getScaledHeight() - height - 65 + offset;
					default:
						return res.getScaledHeight() - height + offset;
				}
			}
			default:
				return 0;
		}
	}
	
	public abstract void render();
}
