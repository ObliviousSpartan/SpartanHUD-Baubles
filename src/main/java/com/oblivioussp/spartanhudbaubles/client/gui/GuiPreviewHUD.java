package com.oblivioussp.spartanhudbaubles.client.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import com.oblivioussp.spartanhudbaubles.ModSpartanHUDBaubles;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiConfig;

/**
 * Preview HUD GUI class which shows config changes recently made using the Config GUI
 * @author ObliviousSpartan
 *
 */
public class GuiPreviewHUD extends GuiScreen 
{
	private static final int BUTTON_ID_DONE = 100;
	private static final ResourceLocation WIDGETS_TEXTURE = new ResourceLocation("textures/gui/widgets.png");
	
	private final GuiScreen parentScreen;
	private HudElementBaubles hud;
	
	public GuiPreviewHUD(GuiScreen parent)
	{
		parentScreen = parent;
		hud = new HudElementBaubles(true);
	}
	
	@Override
	public void initGui() 
	{
		ScaledResolution res = new ScaledResolution(mc);
		String doneTitle = I18n.format("gui.done");
		int doneWidth = fontRenderer.getStringWidth(doneTitle) + 20;
		buttonList.add(new GuiButtonExt(BUTTON_ID_DONE, res.getScaledWidth() / 2 - doneWidth / 2, res.getScaledHeight() / 2 - 50, doneWidth, 20, doneTitle));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		if(button.id == BUTTON_ID_DONE)
		{
			mc.displayGuiScreen(parentScreen);
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException 
	{
		if(keyCode == Keyboard.KEY_ESCAPE)
			mc.displayGuiScreen(parentScreen);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		if(mc.world == null)
			drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		ScaledResolution res = new ScaledResolution(mc);
		
		EnumHandSide offhandSide = mc.gameSettings.mainHand.opposite();
		
		// Draw the mock default Hud (note that any Hud changing mods will not change this...)
		mc.getTextureManager().bindTexture(WIDGETS_TEXTURE);
		GlStateManager.color(1.0f, 1.0f, 1.0f);
		GlStateManager.enableBlend();
		
		if(mc.world == null)
		{
			// Draw the Hotbar
			int halfWidth = res.getScaledWidth() / 2;
			int halfHeight = res.getScaledHeight() / 2;
			drawTexturedModalRect(halfWidth - 91, res.getScaledHeight() - 22, 0, 0, 182, 22);
			if(offhandSide == EnumHandSide.LEFT)
				drawTexturedModalRect(halfWidth - 91 - 29, res.getScaledHeight() - 23, 24, 22, 29, 24);
			else
				drawTexturedModalRect(halfWidth + 91, res.getScaledHeight() - 23, 53, 22, 29, 24);
			
			// Draw the crosshair
			mc.getTextureManager().bindTexture(ICONS);
			GlStateManager.tryBlendFuncSeparate(SourceFactor.ONE_MINUS_DST_COLOR, DestFactor.ONE_MINUS_SRC_COLOR, SourceFactor.ONE, DestFactor.ZERO);
			GlStateManager.enableAlpha();
			drawTexturedModalRect(halfWidth - 7, halfHeight - 7, 0, 0, 16, 16);
			GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
			
			// Draw the XP bar
			drawTexturedModalRect(halfWidth - 91, res.getScaledHeight() - 32 + 3, 0, 64, 182, 5);

			// Draw Armor
			int armorHeight = res.getScaledHeight() - 49;
			for(int i = 0; i < 10; i++)
			{
				drawTexturedModalRect(halfWidth - 91 + i * 8, armorHeight, 43, 9, 9, 9);
			}
			
			// Draw Health
			int healthHeight = res.getScaledHeight() - 39;
			for(int i = 0; i < 10; i++)
			{
				drawTexturedModalRect(halfWidth - 91 + i * 8, healthHeight, 16, 0, 9, 9);
				drawTexturedModalRect(halfWidth - 91 + i * 8, healthHeight, 52, 0, 9, 9);
			}
			
			// Draw Hunger
			int hungerHeight = res.getScaledHeight() - 39;
			for(int i = 0; i < 10; i++)
			{
				drawTexturedModalRect(halfWidth + 82 - i * 8, hungerHeight, 16, 27, 9, 9);
				drawTexturedModalRect(halfWidth + 82 - i * 8, hungerHeight, 52, 27, 9, 9);
			}
			
			// Draw the hover text for recently equipped items
			String hoverText = I18n.format("config." + ModSpartanHUDBaubles.ID + ":hover_text_example");
			int hoverTextSize = fontRenderer.getStringWidth(hoverText);
			int hoverX = (res.getScaledWidth() - hoverTextSize) / 2;
			int hoverY = res.getScaledHeight() - 59;
			fontRenderer.drawStringWithShadow(hoverText, hoverX, hoverY, 0xFFFFFFFF);
		}

		GlStateManager.disableBlend();
		hud.render();
	}
}
