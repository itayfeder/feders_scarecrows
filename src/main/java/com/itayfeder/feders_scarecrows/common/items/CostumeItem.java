package com.itayfeder.feders_scarecrows.common.items;

import com.itayfeder.feders_scarecrows.common.entities.scarecrow.ScarecrowType;
import com.itayfeder.feders_scarecrows.common.items.api.ParentedItem;
import net.minecraft.world.item.Item;

public class CostumeItem extends ParentedItem {
    final ScarecrowType type;
    public CostumeItem(ScarecrowType type, Item.Properties p_40503_, Item parentItem) {
        super(p_40503_, parentItem);
        this.type = type;
    }

    public ScarecrowType getType() {
        return type;
    }
}
