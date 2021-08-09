package com.itayfeder.feders_scarecrows.common.items.api;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

public class ParentedItem extends Item {
    private final Item parentItem;

    public ParentedItem(Item.Properties p_41383_, Item parentItem) {
        super(p_41383_);
        this.parentItem = parentItem;
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (this.parentItem == null)
        {
            super.fillItemCategory(group, items);
            return;
        }

        List<Item> groupItems = items.stream().map(ItemStack::getItem).collect(Collectors.toList());

        if (this.getCreativeTabs().contains(group) && groupItems.contains(this.parentItem))
            items.add(groupItems.indexOf(this.parentItem) + 1, new ItemStack(this));
    }
}