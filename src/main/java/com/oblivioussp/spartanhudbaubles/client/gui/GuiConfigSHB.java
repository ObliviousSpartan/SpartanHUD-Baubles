package com.oblivioussp.spartanhudbaubles.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.oblivioussp.spartanhudbaubles.ModSpartanHUDBaubles;
import com.oblivioussp.spartanhudbaubles.util.Config;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.PostConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.Event.Result;

/**
 * Config GUI class that adds the HUD Preview button
 * @author ObliviousSpartan
 *
 */
public class GuiConfigSHB extends GuiConfig 
{
	private static final int BUTTON_ID_PREVIEW_HUD = 3000;
	
	public GuiConfigSHB(GuiScreen guiScreen)
	{
		super(guiScreen,
				getConfigElements(),
				ModSpartanHUDBaubles.ID,
				false,
				false,
				ModSpartanHUDBaubles.NAME + " Config",
				GuiConfig.getAbridgedConfigPath(Config.getConfig().toString()));
	}
	
	private static List<IConfigElement> getConfigElements()
	{
		List<IConfigElement> elements = new ArrayList<IConfigElement>();
		elements.addAll(new ConfigElement(Config.getConfig().getCategory(Configuration.CATEGORY_CLIENT)).getChildElements());
			
		return elements;
	}
	
	@Override
	public void initGui() 
	{
		String hudEdit = I18n.format("config." + ModSpartanHUDBaubles.ID + ":preview_hud");
		int width = fontRenderer.getStringWidth(hudEdit) + 20;
		this.buttonList.add(new GuiButtonExt(BUTTON_ID_PREVIEW_HUD, 5, 5, width, 20, hudEdit));
		super.initGui();
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		if(button.id == BUTTON_ID_PREVIEW_HUD)
		{
			// Attempt to refresh the config first before previewing the HUD
			if(entryList.hasChangedEntry(true) && Loader.isModLoaded(modID))
			{
				boolean requiresMcRestart = entryList.saveConfigElements();
				OnConfigChangedEvent ev = new OnConfigChangedEvent(modID, configID, isWorldRunning, requiresMcRestart);
				MinecraftForge.EVENT_BUS.post(ev);
				if(!ev.getResult().equals(Result.DENY))
					MinecraftForge.EVENT_BUS.post(new PostConfigChangedEvent(modID, configID, isWorldRunning, requiresMcRestart));
				needsRefresh = true;
			}
			mc.displayGuiScreen(new GuiPreviewHUD(this));
		}
		super.actionPerformed(button);
	}
}
