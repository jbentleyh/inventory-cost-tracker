package com.inventorycost;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup(InventoryCostConfig.GROUP)
public interface InventoryCostConfig extends Config
{
	String GROUP = "inventorycost";

	@ConfigItem(
			keyName = "saveCost",
			name = "Save Current Total Cost",
			description = "Save the current cost of your inventory and/or equipment",
			position = 1
	)
	default boolean saveCost()
	{
		return false;
	}

	@ConfigItem(
			keyName = "excludeHeadEquipmentCost",
			name = "Exclude Head Slot Cost",
			description = "Exclude player head equipment cost from total cost calculation",
			position = 2
	)
	default boolean excludeHeadEquipmentCost()
	{
		return false;
	}

	@ConfigItem(
			keyName = "excludeBodyEquipmentCost",
			name = "Exclude Body Slot Cost",
			description = "Exclude player body equipment cost from total cost calculation",
			position = 3
	)
	default boolean excludeBodyEquipmentCost()
	{
		return false;
	}

	@ConfigItem(
			keyName = "excludeGlovesEquipmentCost",
			name = "Exclude Gloves Slot Cost",
			description = "Exclude player gloves equipment cost from total cost calculation",
			position = 4
	)
	default boolean excludeGlovesEquipmentCost()
	{
		return false;
	}

	@ConfigItem(
			keyName = "excludeCapeEquipmentCost",
			name = "Exclude Cape Slot Cost",
			description = "Exclude player cape equipment cost from total cost calculation",
			position = 5
	)
	default boolean excludeCapeEquipmentCost()
	{
		return false;
	}

	@ConfigItem(
			keyName = "excludeAmuletEquipmentCost",
			name = "Exclude Amulet Slot Cost",
			description = "Exclude player amulet equipment cost from total cost calculation",
			position = 6
	)
	default boolean excludeAmuletEquipmentCost()
	{
		return false;
	}

	@ConfigItem(
			keyName = "excludeWeaponEquipmentCost",
			name = "Exclude Weapon Slot Cost",
			description = "Exclude player weapon equipment cost from total cost calculation",
			position = 7
	)
	default boolean excludeWeaponEquipmentCost()
	{
		return false;
	}

	@ConfigItem(
			keyName = "excludeShieldEquipmentCost",
			name = "Exclude Shield Slot Cost",
			description = "Exclude player shield equipment cost from total cost calculation",
			position = 8
	)
	default boolean excludeShieldEquipmentCost()
	{
		return false;
	}

	@ConfigItem(
			keyName = "excludeRingEquipmentCost",
			name = "Exclude Ring Slot Cost",
			description = "Exclude player ring equipment cost from total cost calculation",
			position = 9
	)
	default boolean excludeRingEquipmentCost()
	{
		return false;
	}

	@ConfigItem(
			keyName = "excludeAmmoEquipmentCost",
			name = "Exclude Ammo Slot Cost",
			description = "Exclude player ammo equipment cost from total cost calculation",
			position = 10
	)
	default boolean excludeAmmoEquipmentCost()
	{
		return false;
	}

	@ConfigItem(
			keyName = "excludeLegsEquipmentCost",
			name = "Exclude Legs Slot Cost",
			description = "Exclude player legs equipment cost from total cost calculation",
			position = 11
	)
	default boolean excludeLegsEquipmentCost()
	{
		return false;
	}

	@ConfigItem(
			keyName = "excludeBootsEquipmentCost",
			name = "Exclude Boots Slot Cost",
			description = "Exclude player boots equipment cost from total cost calculation",
			position = 12
	)
	default boolean excludeBootsEquipmentCost()
	{
		return false;
	}
}