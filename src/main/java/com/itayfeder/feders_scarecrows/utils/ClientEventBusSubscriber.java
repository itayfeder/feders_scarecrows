package com.itayfeder.feders_scarecrows.utils;

import com.itayfeder.feders_scarecrows.FedersScarecrowsMod;
import com.itayfeder.feders_scarecrows.client.ModModelLayers;
import com.itayfeder.feders_scarecrows.client.model.GuardianScarecrowModel;
import com.itayfeder.feders_scarecrows.client.model.OcelotScarecrowModel;
import com.itayfeder.feders_scarecrows.client.model.ScarecrowModel;
import com.itayfeder.feders_scarecrows.client.model.WolfScarecrowModel;
import com.itayfeder.feders_scarecrows.client.renderer.GuardianScarecrowRenderer;
import com.itayfeder.feders_scarecrows.client.renderer.OcelotScarecrowRenderer;
import com.itayfeder.feders_scarecrows.client.renderer.ScarecrowRenderer;
import com.itayfeder.feders_scarecrows.client.renderer.WolfScarecrowRenderer;
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
        EntityRenderers.register(EntityTypeInit.WOLF_SCARECROW, WolfScarecrowRenderer::new);
        EntityRenderers.register(EntityTypeInit.OCELOT_SCARECROW, OcelotScarecrowRenderer::new);
        EntityRenderers.register(EntityTypeInit.GUARDIAN_SCARECROW, GuardianScarecrowRenderer::new);
    }

    @SubscribeEvent
    public static void onLayerRenderer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.SCARECROW, ScarecrowModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.WOLF_SCARECROW, WolfScarecrowModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.OCELOT_SCARECROW, OcelotScarecrowModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.GUARDIAN_SCARECROW, GuardianScarecrowModel::createBodyLayer);
    }
}
