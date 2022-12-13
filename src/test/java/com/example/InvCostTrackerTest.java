package com.example;

import com.inventorycost.InventoryCostPlugin;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class InvCostTrackerTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(InventoryCostPlugin.class);
		RuneLite.main(args);
	}
}