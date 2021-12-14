package com.oblivioussp.spartanhudbaubles.client.event;

import com.oblivioussp.spartanhudbaubles.client.gui.AlignmentHelper.Alignment;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * An event that fires when rendering a specific bauble to allow rendering extra things under or over the specific bauble.<br>
 * This event is not {@link Cancelable}<br>
 * This event does not have a {@link Result}<br>
 * Use the {@link Pre} or {@link Post} events to subscribe to the event bus<br>
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}
 * @author ObliviousSpartan
 *
 */
public class RenderBaubleOnHUDEvent extends Event 
{
	protected final ItemStack itemStack;
	protected final int positionX, positionY;
	protected final Alignment alignment;
	protected final boolean isVertical;
	protected final ScaledResolution resolution;
	protected final GuiIngame guiIngame;
	protected final FontRenderer fontRenderer;
	
	private RenderBaubleOnHUDEvent(ItemStack stack, int posX, int posY, Alignment align, boolean vertical, ScaledResolution res, GuiIngame gui, FontRenderer font)
	{
		itemStack = stack;
		positionX = posX;
		positionY = posY;
		alignment = align;
		isVertical = vertical;
		resolution = res;
		guiIngame = gui;
		fontRenderer = font;
	}
	
	public ItemStack getItemStack()
	{
		return itemStack;
	}
	
	public int getPositionX() 
	{
		return positionX;
	}
	
	public int getPositionY()
	{
		return positionY;
	}
	
	public Alignment getAlignment() 
	{
		return alignment;
	}
	
	public boolean isVertical() 
	{
		return isVertical;
	}
	
	public ScaledResolution getScaledResolution() 
	{
		return resolution;
	}
	
	public GuiIngame getGuiIngame() 
	{
		return guiIngame;
	}
	
	public FontRenderer getFontRenderer() 
	{
		return fontRenderer;
	}
	
	public static class Pre extends RenderBaubleOnHUDEvent
	{
		public Pre(ItemStack stack, int posX, int posY, Alignment align, boolean vertical, ScaledResolution res, GuiIngame gui, FontRenderer font)
		{
			super(stack, posX, posY, align, vertical, res, gui, font);
		}
	}
	
	public static class Post extends RenderBaubleOnHUDEvent
	{
		public Post(ItemStack stack, int posX, int posY, Alignment align, boolean vertical, ScaledResolution res, GuiIngame gui, FontRenderer font)
		{
			super(stack, posX, posY, align, vertical, res, gui, font);
		}
	}
}
