package com.itayfeder.feders_scarecrows.init;

import com.itayfeder.feders_scarecrows.FedersScarecrowsMod;
import com.itayfeder.feders_scarecrows.common.entities.Scarecrow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FedersScarecrowsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityTypeInit {
    public static final EntityType<Scarecrow> SCARECROW = EntityType.Builder.<Scarecrow>of(Scarecrow::new, MobCategory.MISC)
            .sized(0.5F, 1.75F).clientTrackingRange(10)
            .build(new ResourceLocation(FedersScarecrowsMod.MOD_ID, "scarecrow").toString());

    @SubscribeEvent
    public static void registerEntity(RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().register(SCARECROW.setRegistryName("scarecrow"));
    }

    @SubscribeEvent
    public static void registerEntityAttribute(EntityAttributeCreationEvent event) {
        event.put(EntityTypeInit.SCARECROW, LivingEntity.createLivingAttributes().build());
    }
}
