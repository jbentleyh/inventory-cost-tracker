package net.runelite.client.plugins.inventorycosttracker;

import com.google.inject.Inject;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
	name = "Inventory Cost Tracker",
	description = "Tracks the real-time cost of the players inventory/equipment",
	tags = {"overlay", "tracker", "calculator", "gp", "value", "check", "worth"}
)
public class InventoryCostPlugin extends Plugin
{
	@Inject
	private OverlayManager overlayManager;

	@Inject
	private InventoryCostOverlay overlay;

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(overlay);
		log.info("Inventory Cost Tracker started");
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
		log.info("Inventory Cost Tracker stopped");
	}

	@Provides
	InventoryCostConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(InventoryCostConfig.class);
	}
}