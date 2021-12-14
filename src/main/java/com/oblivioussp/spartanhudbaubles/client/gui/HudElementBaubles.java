package com.oblivioussp.spartanhudbaubles.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.oblivioussp.spartanhudbaubles.ModSpartanHUDBaubles;
import com.oblivioussp.spartanhudbaubles.client.event.RenderBaubleOnHUDEvent;
import com.oblivioussp.spartanhudbaubles.client.gui.AlignmentHelper.HorizontalAlignment;
import com.oblivioussp.spartanhudbaubles.client.gui.AlignmentHelper.VerticalAlignment;
import com.oblivioussp.spartanhudbaubles.util.Config;
import com.oblivioussp.spartanhudbaubles.util.SkinType;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import baubles.common.Baubles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(modid = ModSpartanHUDBaubles.ID, value = Side.CLIENT)
public class HudElementBaubles extends HudElement 
{
	private static HudElementBaubles ACTIVE = null;
	
	private static final ResourceLocation HUD = new ResourceLocation(ModSpartanHUDBaubles.ID, "textures/gui/hud.png");
	private static final ResourceLocation BAUBLES_EXPANDED_INVENTORY = new ResourceLocation(Baubles.MODID, "textures/gui/expanded_inventory.png");
	private static final int DEFAULT_WIDTH = 130;
	private static final int DEFAULT_HEIGHT = 22;
	
	private boolean isPreview = false;
	
	public HudElementBaubles()
	{
		super(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public HudElementBaubles(boolean enablePreview)
	{
		this();
		isPreview = enablePreview;
	}

	@Override
	public void render()
	{
		List<ItemStack> stacksToRender = new ArrayList<ItemStack>();
		
		// Fetch the item stacks to render if preview mode is not enabled 
		if(!isPreview)
		{
			IBaublesItemHandler baublesHandler = BaublesApi.getBaublesHandler(Minecraft.getMinecraft().player);
	
			for(int i = 0; i < baublesHandler.getSlots(); i++)
			{
				ItemStack checkStack = baublesHandler.getStackInSlot(i);
				if(!checkStack.isEmpty())
				{
					stacksToRender.add(checkStack);
				}
			}
		}
		
		// Default to 7 slots for preview mode
		int slotsToRender = isPreview ? 7 : stacksToRender.size();
		
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution res = new ScaledResolution(mc);
		
		// Don't render if there is no items to render or if the player is a spectator unless in preview mode
		if(!isPreview && (stacksToRender.isEmpty() || mc.player.isSpectator()))	return;
		
		GlStateManager.enableBlend();
		
		int x = this.getAlignedX(Config.hudAlignment, Config.hudOffsetX, res);
		int y = this.getAlignedY(Config.hudAlignment, Config.hudOffsetY, res);
		
		HorizontalAlignment horizontal = Config.hudAlignment.getHorizontal();
		VerticalAlignment vertical = Config.hudAlignment.getVertical();
		
		// Get the GUI class to draw to
		GuiIngame gui = Minecraft.getMinecraft().ingameGUI;
		
		int startX = 3, startY = 3;
		int spacing = 2;
		int length = (Config.hudRotateVertical ? startY : startX) * 2 + 16 * slotsToRender + spacing * (slotsToRender - 1);
		if(!Config.hudRotateVertical && length != width)
		{
			width = length;
			height = DEFAULT_HEIGHT;
		}
		if(Config.hudRotateVertical && length != height)
		{
			height = length;
			width = DEFAULT_HEIGHT;
		}
		
		// Draw the Baubles icon if enabled unless set to no skin type
		if(Config.showBaublesIcon && Config.skinType != SkinType.NONE)
		{
			mc.getTextureManager().bindTexture(BAUBLES_EXPANDED_INVENTORY);
			int iconX = Config.hudRotateVertical ? x + 6 : horizontal == HorizontalAlignment.LEFT ? x + width + 1 : x - 11;
			int iconY = Config.hudRotateVertical ? vertical == VerticalAlignment.BOTTOM ? y - 11 : y + height + 1 : y + 6;
			gui.drawTexturedModalRect(iconX, iconY, 210, 48, 10, 10);
		}
		
		// Draw the appropriate skin according to the skin type
		switch(Config.skinType)
		{
			case HOTBAR_STYLE:
				mc.getTextureManager().bindTexture(HUD);
				switch(slotsToRender)
				{
					case 1:
						gui.drawTexturedModalRect(x, y, 0, 0, 22, 22);
						break;
					case 2:
						drawTextureRectFromRotation(x, y, 0, 24, 0, 21, 22, Config.hudRotateVertical);
						drawTextureRectFromRotation(x, y, 21, 135, 0, 19, 22, Config.hudRotateVertical);
						break;
					case 3:
						drawTextureRectFromRotation(x, y, 0, 24, 0, 39, 22, Config.hudRotateVertical);
						drawTextureRectFromRotation(x, y, 39, 135, 0, 19, 22, Config.hudRotateVertical);
						break;
					case 4:
						drawTextureRectFromRotation(x, y, 0, 24, 0, 39, 22, Config.hudRotateVertical);
						drawTextureRectFromRotation(x, y, 39, 117, 0, 37, 22, Config.hudRotateVertical);
						break;
					case 5:
						drawTextureRectFromRotation(x, y, 0, 24, 0, 57, 22, Config.hudRotateVertical);
						drawTextureRectFromRotation(x, y, 57, 117, 0, 37, 22, Config.hudRotateVertical);
						break;
					case 6:
						drawTextureRectFromRotation(x, y, 0, 24, 0, 57, 22, Config.hudRotateVertical);
						drawTextureRectFromRotation(x, y, 57, 99, 0, 55, 22, Config.hudRotateVertical);
						break;
					case 7:
						drawTextureRectFromRotation(x, y, 0, 24, 0, 130, 22, Config.hudRotateVertical);
						break;
					default:
						break;
				}
				break;
			case TOOLTIP_STYLE:
				gui.drawGradientRect(x + 1, y, x + width - 1, y + 1, Config.tooltipBackgroundColour, Config.tooltipBackgroundColour);
				gui.drawGradientRect(x + 1, y + height - 1, x + width - 1, y + height, Config.tooltipBackgroundColour, Config.tooltipBackgroundColour);
				gui.drawGradientRect(x, y + 1, x + 1, y + height - 1, Config.tooltipBackgroundColour, Config.tooltipBackgroundColour);
				gui.drawGradientRect(x + width - 1, y + 1, x + width, y + height - 1, Config.tooltipBackgroundColour, Config.tooltipBackgroundColour);
				gui.drawGradientRect(x + 1, y + 1, x + width - 1, y + height - 1, Config.tooltipBackgroundColour, Config.tooltipBackgroundColour);
				
				gui.drawGradientRect(x + 1, y + 1, x + width - 1, y + 2, Config.tooltipFrameTopColour, Config.tooltipFrameTopColour);
				gui.drawGradientRect(x + 1, y + height - 2, x + width - 1, y + height - 1, Config.tooltipFrameBottomColour, Config.tooltipFrameBottomColour);
				gui.drawGradientRect(x + 1, y + 2, x + 2, y + height - 2, Config.tooltipFrameTopColour, Config.tooltipFrameBottomColour);
				gui.drawGradientRect(x + width - 2, y + 2, x + width - 1, y + height - 2, Config.tooltipFrameTopColour, Config.tooltipFrameBottomColour);
				break;
			case MINIMAL:
				gui.drawGradientRect(x, y, x + width, y + height, Config.minimalTopColour, Config.minimalBottomColour);
				break;
			default:
				break;
		}
		
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        RenderHelper.enableGUIStandardItemLighting();
        
        if(!isPreview)
        {
            // Draw the equipped Bauble items unless in preview mode
			for(int i = 0; i < stacksToRender.size(); i++)
			{
				ItemStack stack = stacksToRender.get(i);
				int offsetX = Config.hudRotateVertical ? x + startX : x + startX + (16 + spacing) * i;
				int offsetY = Config.hudRotateVertical ? y + startY + (16 + spacing) * i : y + startY;
				MinecraftForge.EVENT_BUS.post(new RenderBaubleOnHUDEvent.Pre(stack, offsetX, offsetY, Config.hudAlignment, Config.hudRotateVertical, res, gui, mc.fontRenderer));
				mc.getRenderItem().renderItemAndEffectIntoGUI(stack, offsetX, offsetY);
				mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, stack, offsetX, offsetY, null);
				MinecraftForge.EVENT_BUS.post(new RenderBaubleOnHUDEvent.Post(stack, offsetX, offsetY, Config.hudAlignment, Config.hudRotateVertical, res, gui, mc.fontRenderer));
			}
        }
        else
        {
        	// Draw "Dummy" baubles in the HUD if in preview mode
			mc.getTextureManager().bindTexture(BAUBLES_EXPANDED_INVENTORY);
        	ItemStack stack = new ItemStack(Blocks.BARRIER);
        	for(int i = 0; i < slotsToRender; i++)
        	{
				int offsetX = Config.hudRotateVertical ? x + startX : x + startX + (16 + spacing) * i;
				int offsetY = Config.hudRotateVertical ? y + startY + (16 + spacing) * i : y + startY;
				gui.drawTexturedModalRect(offsetX + 3, offsetY + 3, 200, 48, 10, 10);
        	}
        }

		RenderHelper.disableStandardItemLighting();
	}
	
	/**
	 * Helper method to automatically move positions and locate texture UVs depending on rotations
	 */
	private void drawTextureRectFromRotation(int x, int y, int offset, int textureU, int textureV, int width, int height, boolean rotate)
	{
		int texX = rotate ? x : x + offset;
		int texY = rotate ? y + offset : y;
		int texU = rotate ? textureV : textureU;
		int texV = rotate ? textureU : textureV;
		int texWidth = rotate ? height : width;
		int texHeight = rotate ? width : height;
		Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(texX, texY, texU, texV, texWidth, texHeight);
	}

	/**
	 * Event that allows drawing of the HUD over the screen
	 * @param ev
	 */
	@SubscribeEvent
	public static void onHUDOverlay(RenderGameOverlayEvent.Post ev)
	{
		Minecraft mc = Minecraft.getMinecraft();
		if(!Config.disableHUD && ev.getType() == ElementType.EXPERIENCE)
		{
			// Don't render the HUD if there is no current screen or if the Preview HUD GUI is being displayed
			if(mc == null || (mc.currentScreen != null && mc.currentScreen.getClass() == GuiPreviewHUD.class))
				return;
			if(ACTIVE == null)
				ACTIVE = new HudElementBaubles();
			
			ACTIVE.render();
		}
	}
}
