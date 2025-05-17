package com.inventorycost;

import com.google.inject.Inject;
import net.runelite.api.*;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.*;
import net.runelite.client.ui.overlay.components.ComponentConstants;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

import java.awt.*;

public class InventoryCostOverlay extends OverlayPanel
{
	@Inject
    private Client client;

	@Inject
    private ItemManager itemManager;

	@Inject
	private InventoryCostConfig config;

	private int SAVED_COST = 0;
	private final int PANEL_WIDTH_OFFSET = 10;

    @Override
    public Dimension render(Graphics2D graphics)
    {
        final String title = "Inventory Cost";
        panelComponent.getChildren().add(
                TitleComponent.builder()
                        .text(title)
                        .build());

        int maxWidth = ComponentConstants.STANDARD_WIDTH;

        panelComponent.getChildren().add(
                LineComponent.builder()
                        .left("CURRENT")
                        .right(setFormattedText(calculateTotalCost()))
                        .build());

        if (config.saveCost())
        {
            if (SAVED_COST >= 0)
            {
                panelComponent.getChildren().add(
                        LineComponent.builder()
                                .left("SAVED")
                                .right(setFormattedText(SAVED_COST))
                                .build());

                panelComponent.getChildren().add(
                        LineComponent.builder()
                                .left("")
                                .right("")
                                .build());

                panelComponent.getChildren().add(
                        LineComponent.builder()
                                .left("DIFF")
                                .right(setCostDifferenceText())
                                .rightColor(setCostDifferenceColor())
                                .build());

                panelComponent.setPreferredSize(new Dimension(maxWidth + PANEL_WIDTH_OFFSET, 0));
            } else {
                setSaveCost();
            }
        } else {
            SAVED_COST = -1;
        }

        return super.render(graphics);
    }

    private int calculateTotalCost()
    {
        int totalCost = 0;

        totalCost += calculateItemsCost(client.getItemContainer(InventoryID.INVENTORY));

        if (!config.excludeHeadEquipmentCost())
        {
            totalCost += calculateEquipmentSlotCost(EquipmentInventorySlot.HEAD.getSlotIdx());
        }

        if (!config.excludeBodyEquipmentCost())
        {
            totalCost += calculateEquipmentSlotCost(EquipmentInventorySlot.BODY.getSlotIdx());
        }

        if (!config.excludeCapeEquipmentCost())
        {
            totalCost += calculateEquipmentSlotCost(EquipmentInventorySlot.CAPE.getSlotIdx());
        }

        if (!config.excludeAmuletEquipmentCost())
        {
            totalCost += calculateEquipmentSlotCost(EquipmentInventorySlot.AMULET.getSlotIdx());
        }

        if (!config.excludeGlovesEquipmentCost())
        {
            totalCost += calculateEquipmentSlotCost(EquipmentInventorySlot.GLOVES.getSlotIdx());
        }

        if (!config.excludeWeaponEquipmentCost())
        {
            totalCost += calculateEquipmentSlotCost(EquipmentInventorySlot.WEAPON.getSlotIdx());
        }

        if (!config.excludeShieldEquipmentCost())
        {
            totalCost += calculateEquipmentSlotCost(EquipmentInventorySlot.SHIELD.getSlotIdx());
        }

        if (!config.excludeRingEquipmentCost())
        {
            totalCost += calculateEquipmentSlotCost(EquipmentInventorySlot.RING.getSlotIdx());
        }

        if (!config.excludeAmmoEquipmentCost())
        {
            totalCost += calculateEquipmentSlotCost(EquipmentInventorySlot.AMMO.getSlotIdx());
        }

        if (!config.excludeLegsEquipmentCost())
        {
            totalCost += calculateEquipmentSlotCost(EquipmentInventorySlot.LEGS.getSlotIdx());
        }

        if (!config.excludeBootsEquipmentCost())
        {
            totalCost += calculateEquipmentSlotCost(EquipmentInventorySlot.BOOTS.getSlotIdx());
        }

        return totalCost;
    }

    private int calculateItemsCost(ItemContainer itemContainer)
    {
        if (itemContainer == null)
        {
            return 0;
        }

        Item[] items = itemContainer.getItems();

        if (items == null || items.length == 0)
        {
            return 0;
        }

        int totalCost = 0;

        for (int i = 0; i < items.length; i++)
        {
            Item item = items[i];
            int itemPrice = itemManager.getItemPrice(item.getId());

            if (item.getQuantity() > 0)
            {
                totalCost += itemPrice * item.getQuantity();
            }
            else
            {
                totalCost += itemPrice;
            }
        }

        return totalCost;
    }

    private int calculateEquipmentSlotCost(int slotIndex)
    {
		ItemContainer itemContainer  = client.getItemContainer(InventoryID.EQUIPMENT);

		if (itemContainer == null)
		{
			return 0;
		}

        Item slotItem = itemContainer.getItem(slotIndex);

        if (slotItem == null)
        {
            return 0;
        }

        if (slotItem.getQuantity() > 0)
        {
            return itemManager.getItemPrice(slotItem.getId()) * slotItem.getQuantity();
        }

        return itemManager.getItemPrice(slotItem.getId());
    }

    private int calculateSaveCostDifference()
    {
        return calculateTotalCost() - SAVED_COST;
    }

    private String setCostDifferenceText()
    {
        if (SAVED_COST < calculateTotalCost()) {
            return "+" + setFormattedText(calculateSaveCostDifference());
        } else  {
            return setFormattedText(calculateSaveCostDifference());
        }
    }

    private Color setCostDifferenceColor()
    {
        if (SAVED_COST > calculateTotalCost()) {
            return Color.RED;
        } else if (SAVED_COST < calculateTotalCost()) {
            return Color.GREEN;
        } else  {
            return Color.WHITE;
        }
    }

    private String setFormattedText(int value)
    {
        Double numParsed = Double.parseDouble(Integer.toString(value));
        return String.format("%,.0f", numParsed);
    }

	private void setSaveCost()
	{
		SAVED_COST = calculateTotalCost();
	}
}