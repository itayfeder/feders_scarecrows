package com.itayfeder.feders_scarecrows.utils;

import com.itayfeder.feders_scarecrows.FedersScarecrowsMod;
import com.itayfeder.feders_scarecrows.client.ModModelLayers;
import com.itayfeder.feders_scarecrows.client.model.ScarecrowModel;
import com.itayfeder.feders_scarecrows.client.model.outfits.DefaultOutfitModel;
import com.itayfeder.feders_scarecrows.client.model.outfits.GuardianOutfitModel;
import com.itayfeder.feders_scarecrows.client.model.outfits.OcelotOutfitModel;
import com.itayfeder.feders_scarecrows.client.model.outfits.WolfOutfitModel;
import com.itayfeder.feders_scarecrows.client.renderer.ScarecrowRenderer;
import com.itayfeder.feders_scarecrows.init.EntityTypeInit;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = FedersScarecrowsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventBusSubscriber {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(EntityTypeInit.SCARECROW, ScarecrowRenderer::new);
    }

    @SubscribeEvent
    public static void onLayerRenderer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.SCARECROW, ScarecrowModel::createBodyLayer);

        event.registerLayerDefinition(ModModelLayers.DEFAULT_OUTFIT, DefaultOutfitModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.WOLF_OUTFIT, WolfOutfitModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.OCELOT_OUTFIT, OcelotOutfitModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.GUARDIAN_OUTFIT, GuardianOutfitModel::createBodyLayer);
    }
}
