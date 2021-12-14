package com.oblivioussp.spartanhudbaubles;

import com.oblivioussp.spartanhudbaubles.util.Config;
import com.oblivioussp.spartanhudbaubles.util.Log;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModSpartanHUDBaubles.ID, name = ModSpartanHUDBaubles.NAME, version = ModSpartanHUDBaubles.VERSION, 
	dependencies = ModSpartanHUDBaubles.DEPENDENCIES, acceptedMinecraftVersions = ModSpartanHUDBaubles.MC_VERSION,
	clientSideOnly = true, guiFactory = ModSpartanHUDBaubles.GUI_FACTORY_CLASS)
public class ModSpartanHUDBaubles 
{
	public static final String ID = "spartanhudbaubles";
	public static final String NAME = "Spartan HUD: Baubles";
	public static final String VERSION = "1.0.0 Indev 1";
	public static final String DEPENDENCIES = "required-after:baubles";
	public static final String MC_VERSION = "[1.12.2]";
	public static final String GUI_FACTORY_CLASS = "com.oblivioussp.spartanhudbaubles.client.gui.GuiFactorySHB";
	
	@Instance(ID)
	public static ModSpartanHUDBaubles INSTANCE;
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent ev)
	{
		Config.init(ev.getModConfigurationDirectory().toPath());
		MinecraftForge.EVENT_BUS.register(new Config());
		Log.info("Finished preInit phase!");
	}

	@EventHandler
	public static void init(FMLInitializationEvent ev)
	{
		Log.info("Finished init phase!");
	}

	@EventHandler
	public static void postInit(FMLPostInitializationEvent ev)
	{
		Log.info("Finished postInit phase!");
	}
}
