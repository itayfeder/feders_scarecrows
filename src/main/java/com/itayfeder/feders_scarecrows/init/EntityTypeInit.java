package com.itayfeder.feders_scarecrows.init;

import com.itayfeder.feders_scarecrows.FedersScarecrowsMod;
import com.itayfeder.feders_scarecrows.common.entities.GuardianScarecrow;
import com.itayfeder.feders_scarecrows.common.entities.OcelotScarecrow;
import com.itayfeder.feders_scarecrows.common.entities.Scarecrow;
import com.itayfeder.feders_scarecrows.common.entities.WolfScarecrow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FedersScarecrowsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityTypeInit {
    public static final EntityType<Scarecrow> SCARECROW = EntityType.Builder.<Scarecrow>of(Scarecrow::new, MobCategory.MISC)
            .sized(0.5F, 1.75F).clientTrackingRange(10)
            .build(new ResourceLocation(FedersScarecrowsMod.MOD_ID, "scarecrow").toString());

    public static final EntityType<WolfScarecrow> WOLF_SCARECROW = EntityType.Builder.<WolfScarecrow>of(WolfScarecrow::new, MobCategory.MISC)
            .sized(0.5F, 1.75F).clientTrackingRange(10)
            .build(new ResourceLocation(FedersScarecrowsMod.MOD_ID, "wolf_scarecrow").toString());

    public static final EntityType<OcelotScarecrow> OCELOT_SCARECROW = EntityType.Builder.<OcelotScarecrow>of(OcelotScarecrow::new, MobCategory.MISC)
            .sized(0.5F, 1.75F).clientTrackingRange(10)
            .build(new ResourceLocation(FedersScarecrowsMod.MOD_ID, "ocelot_scarecrow").toString());

    public static final EntityType<GuardianScarecrow> GUARDIAN_SCARECROW = EntityType.Builder.<GuardianScarecrow>of(GuardianScarecrow::new, MobCategory.MISC)
            .sized(0.5F, 1.75F).clientTrackingRange(10)
            .build(new ResourceLocation(FedersScarecrowsMod.MOD_ID, "guardian_scarecrow").toString());

    @SubscribeEvent
    public static void registerEntity(RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().register(SCARECROW.setRegistryName("scarecrow"));
        event.getRegistry().register(WOLF_SCARECROW.setRegistryName("wolf_scarecrow"));
        event.getRegistry().register(OCELOT_SCARECROW.setRegistryName("ocelot_scarecrow"));
        event.getRegistry().register(GUARDIAN_SCARECROW.setRegistryName("guardian_scarecrow"));
    }

    @SubscribeEvent
    public static void registerEntityAttribute(EntityAttributeCreationEvent event) {
        event.put(EntityTypeInit.SCARECROW, LivingEntity.createLivingAttributes().build());
        event.put(EntityTypeInit.WOLF_SCARECROW, LivingEntity.createLivingAttributes().build());
        event.put(EntityTypeInit.OCELOT_SCARECROW, LivingEntity.createLivingAttributes().build());
        event.put(EntityTypeInit.GUARDIAN_SCARECROW, LivingEntity.createLivingAttributes().build());
    }
}
