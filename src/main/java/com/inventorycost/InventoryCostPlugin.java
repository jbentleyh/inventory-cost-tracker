package com.inventorycost;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
	name = "Inventory Cost Tracker"
)
public class InventoryCostPlugin extends Plugin
{
	@Inject
	private InventoryCostConfig config;

	@Inject
	private InventoryCostOverlay overlay;

	@Inject
	private OverlayManager overlayManager;

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