package com.itayfeder.feders_scarecrows.common.entities.scarecrow;

import com.itayfeder.feders_scarecrows.utils.ModTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.EntityType;

public enum ScarecrowType {
    DEFAULT("default", ModTags.ZOMBIES),
    WOLF("wolf", EntityTypeTags.SKELETONS),
    OCELOT("ocelot", ModTags.CREEPERS),
    GUARDIAN("guardian", ModTags.ENDERMEN);

    public final String type;
    public final Tag.Named<EntityType<?>> tag;
    private ScarecrowType(String type, Tag.Named<EntityType<?>> tag) {
        this.type = type;
        this.tag = tag;
    }

    public static ScarecrowType byType(String p_28977_) {
        for(ScarecrowType mushroomcow$mushroomtype : values()) {
            if (mushroomcow$mushroomtype.type.equals(p_28977_)) {
                return mushroomcow$mushroomtype;
            }
        }

        return DEFAULT;
    }
}
