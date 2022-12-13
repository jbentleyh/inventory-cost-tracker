package com.inventorycost;

import net.runelite.api.*;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.*;
import net.runelite.client.ui.overlay.components.ComponentConstants;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

import java.awt.*;
import javax.inject.Inject;

public class InventoryCostOverlay extends OverlayPanel
{

    private static final int PANEL_WIDTH_OFFSET = 10;

    private int SAVED_COST;

    private final Client client;

    private final InventoryCostConfig config;

    private final ItemManager itemManager;

    @Inject
    private InventoryCostOverlay(Client client, InventoryCostConfig config, ItemManager itemManager)
    {
        this.client = client;
        this.config = config;
        this.itemManager = itemManager;

        this.SAVED_COST = 0;
    }

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
                        .right(formatCostText(calculateTotalCost()))
                        .build());

        if (config.saveCost())
        {
            if (SAVED_COST >= 0)
            {
                panelComponent.getChildren().add(
                        LineComponent.builder()
                                .left("SAVED")
                                .right(formatCostText(SAVED_COST))
                                .build());

                panelComponent.getChildren().add(
                        LineComponent.builder()
                                .left("")
                                .right("")
                                .build());

                panelComponent.getChildren().add(
                        LineComponent.builder()
                                .left("DIFF")
                                .right(setDifferenceCostText())
                                .rightColor(setDifferenceCostColor())
                                .build());

                panelComponent.setPreferredSize(new Dimension(maxWidth + PANEL_WIDTH_OFFSET, 0));
            } else {
                saveCost();
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

        if (items == null)
        {
            return 0;
        }

        int totalCost = 0;

        for (int i = 0; i < items.length; i++)
        {
            Item item = items[i];
            int itemPrice = getItemPrice(item.getId());

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

    private int calculateEquipmentSlotCost(int index)
    {
        Item[] items = client.getItemContainer(InventoryID.EQUIPMENT).getItems();

        if (items == null)
        {
            return 0;
        }

        Item slotItem = items[index];

        if (slotItem == null)
        {
            return 0;
        }

        if (slotItem.getQuantity() > 0)
        {
            return getItemPrice(slotItem.getId()) * slotItem.getQuantity();
        }

        return getItemPrice(slotItem.getId());
    }

    private int calculateSaveCostDifference()
    {
        return calculateTotalCost() - SAVED_COST;
    }

    private void saveCost()
    {
        SAVED_COST = calculateTotalCost();
    }

    private int getItemPrice(int id)
    {
        return itemManager.getItemPrice(id);
    }

    private String setDifferenceCostText()
    {
        if (SAVED_COST < calculateTotalCost()) {
            return "+" + formatCostText(calculateSaveCostDifference());
        } else  {
            return formatCostText(calculateSaveCostDifference());
        }
    }

    private Color setDifferenceCostColor()
    {
        if (SAVED_COST > calculateTotalCost()) {
            return Color.RED;
        } else if (SAVED_COST < calculateTotalCost()) {
            return Color.GREEN;
        } else  {
            return Color.WHITE;
        }
    }

    private String formatCostText(int value)
    {
        Double numParsed = Double.parseDouble(Integer.toString(value));
        return String.format("%,.0f", numParsed);
    }
}