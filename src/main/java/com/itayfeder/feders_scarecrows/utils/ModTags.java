package com.itayfeder.feders_scarecrows.utils;

import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.EntityType;

public class ModTags {
    public static final Tag.Named<EntityType<?>> ZOMBIES = EntityTypeTags.bind("feders_scarecrows:zombies");
    public static final Tag.Named<EntityType<?>> CREEPERS = EntityTypeTags.bind("feders_scarecrows:creepers");
    public static final Tag.Named<EntityType<?>> ENDERMEN = EntityTypeTags.bind("feders_scarecrows:endermen");
}
