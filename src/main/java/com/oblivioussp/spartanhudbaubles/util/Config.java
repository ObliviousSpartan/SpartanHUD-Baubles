package com.oblivioussp.spartanhudbaubles.util;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;

import com.oblivioussp.spartanhudbaubles.ModSpartanHUDBaubles;
import com.oblivioussp.spartanhudbaubles.client.gui.AlignmentHelper.Alignment;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Config class containing all setting for this mod
 * @author ObliviousSpartan
 *
 */
public class Config
{
	private static final String VERSION = Integer.toString(1); 
	private static Configuration config;
	
	private static final String[] VALID_ALIGNMENT_VALUES = Alignment.getValidConfigValues();
	private static final String[] VALID_SKIN_VALUES = SkinType.getValidConfigValues();
	
	public static boolean disableHUD = false;
	public static SkinType skinType;
	public static boolean showBaublesIcon = true;
	public static int tooltipBackgroundColour = Defaults.TOOLTIP_BACKGROUND_COLOUR;
	public static int tooltipFrameTopColour = Defaults.TOOLTIP_FRAME_TOP_COLOUR;
	public static int tooltipFrameBottomColour = Defaults.TOOLTIP_FRAME_BOTTOM_COLOUR;
	public static int minimalTopColour = Defaults.MINIMAL_TOP_COLOUR;
	public static int minimalBottomColour = Defaults.MINIMAL_BOTTOM_COLOUR;
	public static Alignment hudAlignment;
	public static int hudOffsetX;
	public static int hudOffsetY;
	public static boolean hudRotateVertical = false;
	
	/**
	 * Helper class to allow individual editing of each color channel in the config
	 * @author ObliviousSpartan
	 *
	 */
	public static class ColourValue
	{
		private byte r, g, b, a;
		
		public static ColourValue fromConfig(Configuration config, String name, String category, int defaultValue, String comment)
		{
			byte red, green, blue, alpha;
			ConfigCategory cfgCategory = config.getCategory(category + "." + name);
			cfgCategory.setComment(comment);
			cfgCategory.setLanguageKey("config." + ModSpartanHUDBaubles.ID + ":client." + name);
			String[] order = {"red", "green", "blue", "alpha"};
			cfgCategory.setPropertyOrder(Arrays.asList(order));
			alpha = (byte)config.getInt("alpha", cfgCategory.getQualifiedName(), (int)(defaultValue >> 24 & 0xFF), 0, 255, "");
			red = (byte)config.getInt("red", cfgCategory.getQualifiedName(), (int)(defaultValue >> 16 & 0xFF), 0, 255, "");
			green = (byte)config.getInt("green", cfgCategory.getQualifiedName(), (int)(defaultValue >> 8 & 0xFF), 0, 255, "");
			blue = (byte)config.getInt("blue", cfgCategory.getQualifiedName(), (int)(defaultValue & 0xFF), 0, 255, "");
			
			return new ColourValue(red, green, blue, alpha);
		}
		
		public ColourValue(byte r, byte g, byte b, byte a)
		{
			this.r = r;
			this.g = g;
			this.b = b;
			this.a = a;
		}
		
		/**
		 * Converts the value to an integer value in the format (in hex): 0xAARRGGBB
		 */
		public int toInteger()
		{
			int value = ((a & 0xFF) << 24) + ((r & 0xFF) << 16) + ((g & 0xFF) << 8) + (b & 0xFF) & 0xFFFFFFFF;
			return value;
		}
	}
	
	public static void init(Path configPath)
	{
		File configFile = configPath.resolve(ModSpartanHUDBaubles.ID.toLowerCase() + ".cfg").toFile();
		
		if(config == null)
		{
			config = new Configuration(configFile, VERSION);
			load();
		}
	}
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent ev)
	{
		if(ev.getModID().equalsIgnoreCase(ModSpartanHUDBaubles.ID))
		{
			// Reload config values
			load();
		}
	}
	
	private static void load()
	{
		disableHUD = config.getBoolean("disable_hud", config.CATEGORY_CLIENT, false, "Set to true to disable the Baubles HUD Element (which essentially disables the entire mod)", "config." + ModSpartanHUDBaubles.ID + ":client.disable_hud");
		showBaublesIcon = config.getBoolean("show_baubles_icon", config.CATEGORY_CLIENT, true, "Enables display of the Baubles icon next to the Baubles HUD Element. This won't show if the skin type is set to \'None\'", "config." + ModSpartanHUDBaubles.ID + ":client.show_baubles_icon");
		ColourValue tooltipBGColour = ColourValue.fromConfig(config, "tooltip_background_colour", config.CATEGORY_CLIENT, Defaults.TOOLTIP_BACKGROUND_COLOUR, "Background colour for the tooltip skin variant");
		tooltipBackgroundColour = tooltipBGColour.toInteger();
		ColourValue tooltipFrTopColour = ColourValue.fromConfig(config, "tooltip_frame_top_colour", config.CATEGORY_CLIENT, Defaults.TOOLTIP_FRAME_TOP_COLOUR, "Frame top colour for the tooltip skin variant");
		tooltipFrameTopColour = tooltipFrTopColour.toInteger();
		ColourValue tooltipFrBtmColour = ColourValue.fromConfig(config, "tooltip_frame_bottom_colour", config.CATEGORY_CLIENT, Defaults.TOOLTIP_FRAME_BOTTOM_COLOUR, "Frame bottom colour for the tooltip skin variant");
		tooltipFrameBottomColour = tooltipFrBtmColour.toInteger();

		ColourValue minTopColour = ColourValue.fromConfig(config, "minimal_top_colour", config.CATEGORY_CLIENT, Defaults.MINIMAL_TOP_COLOUR, "Top colour for the minimal skin variant");
		minimalTopColour = minTopColour.toInteger();
		ColourValue minBtmColour = ColourValue.fromConfig(config, "minimal_bottom_colour", config.CATEGORY_CLIENT, Defaults.MINIMAL_BOTTOM_COLOUR, "Bottom colour for the minimal skin variant");
		minimalBottomColour = minBtmColour.toInteger();
		
		String skinTypeString = config.getString("skin_type", config.CATEGORY_CLIENT, SkinType.HOTBAR_STYLE.toString(), "Skin variant to display behind the Baubles in the HUD Element", VALID_SKIN_VALUES, "config." + ModSpartanHUDBaubles.ID + ":client.skin_type");
		skinType = SkinType.valueOf(skinTypeString);
		
		String hudAlignmentString = config.getString("alignment", config.CATEGORY_CLIENT, Alignment.BOTTOM_RIGHT.toString(), "Alignment of the Baubles HUD Element", VALID_ALIGNMENT_VALUES, "config." + ModSpartanHUDBaubles.ID + ":client.alignment");
		hudAlignment = Alignment.valueOf(hudAlignmentString);
		hudOffsetX = config.getInt("offset_x", config.CATEGORY_CLIENT, 0, -1000, 1000, "X offset of the Baubles HUD Element. Can be used to adjust the X position", "config." + ModSpartanHUDBaubles.ID + ":client.offset_x");
		hudOffsetY = config.getInt("offset_y", config.CATEGORY_CLIENT, 0, -1000, 1000, "Y offset of the Baubles HUD Element. Can be used to adjust the Y position", "config." + ModSpartanHUDBaubles.ID + ":client.offset_y");
		hudRotateVertical = config.getBoolean("rotate_vertical", config.CATEGORY_CLIENT, false, "Set to true to rotate the Baubles HUD Element vertically or false for horizontally", "config." + ModSpartanHUDBaubles.ID + ":client.rotate_vertical");
		
		if(config.hasChanged())
			config.save();
	}
	
	public static Configuration getConfig()
	{
		return config;
	}
}
