package com.itayfeder.feders_scarecrows.client;

import com.itayfeder.feders_scarecrows.FedersScarecrowsMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static ModelLayerLocation SCARECROW = new ModelLayerLocation(new ResourceLocation(FedersScarecrowsMod.MOD_ID, "scarecrow"), "scarecrow");

    public static ModelLayerLocation DEFAULT_OUTFIT = new ModelLayerLocation(new ResourceLocation(FedersScarecrowsMod.MOD_ID, "scarecrow"), "default_outfit");
    public static ModelLayerLocation WOLF_OUTFIT = new ModelLayerLocation(new ResourceLocation(FedersScarecrowsMod.MOD_ID, "scarecrow"), "wolf_outfit");
    public static ModelLayerLocation OCELOT_OUTFIT = new ModelLayerLocation(new ResourceLocation(FedersScarecrowsMod.MOD_ID, "scarecrow"), "ocelot_outfit");
    public static ModelLayerLocation GUARDIAN_OUTFIT = new ModelLayerLocation(new ResourceLocation(FedersScarecrowsMod.MOD_ID, "scarecrow"), "guardian_outfit");

}
