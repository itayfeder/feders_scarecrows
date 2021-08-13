package com.itayfeder.feders_scarecrows.common.entities.scarecrow;

import com.itayfeder.feders_scarecrows.common.items.CostumeItem;
import com.itayfeder.feders_scarecrows.init.EntityTypeInit;
import com.itayfeder.feders_scarecrows.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Predicate;

public class Scarecrow extends TemplateScarecrow {
    private static final EntityDataAccessor<String> DATA_TYPE = SynchedEntityData.defineId(Scarecrow.class, EntityDataSerializers.STRING);

/*    private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and((entity) -> {
        if (entity instanceof LivingEntity) {
            return ModTags.ZOMBIES.contains(entity.getType());
        }
        return false;
    });*/

    public Scarecrow(Level p_31556_, double p_31557_, double p_31558_, double p_31559_) {
        super(EntityTypeInit.SCARECROW, p_31556_);
        this.setPos(p_31557_, p_31558_, p_31559_);
    }

    public Scarecrow(EntityType<Scarecrow> scarecrowEntityType, Level level) {
        super(scarecrowEntityType, level);
    }

    public ItemStack getPickResult() {
        return new ItemStack(ItemInit.SCARECROW);
    }

    @Override
    public void tick() {
        super.tick();
        AABB range = new AABB(this.getX() - 24.0D, this.getY() - 4.0D, this.getZ() - 24.0D, this.getX() + 24.0D, this.getY() + 4.0D, this.getZ() + 24.0D);
        Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and((entity) -> {
            if (entity instanceof LivingEntity) {
                return getScarecrowType().tag.contains(entity.getType());
            }
            return false;
        });

        List<? extends PathfinderMob> entities = this.level.getEntitiesOfClass(PathfinderMob.class, range, ENTITY_PREDICATE);
        for (PathfinderMob entity : entities) {
            if (entity != null) {
                double xt = entity.xOld;
                double yt = entity.yOld;
                double zt = entity.zOld;
                int x1 = Mth.floor(this.xOld);
                int y1 = Mth.floor(this.yOld);
                int z1 = Mth.floor(this.zOld);
                double x2 = xt - (double)x1;
                double y2 = yt - (double)y1;
                double z2 = zt - (double)z1;
                if (Mth.abs((int)x2) < 24 && Mth.abs((int)z2) < 8 && Mth.abs((int)y2) < 24) {
                    entity.setTarget((LivingEntity)null);
                    //entity.reve((LivingEntity)null);
                    Vec3 vec3d = DefaultRandomPos.getPosAway(entity, 20, 7, new Vec3(entity.xOld, entity.yOld, entity.zOld));
                    WorldBorder wb = this.level.getWorldBorder();
                    if (vec3d != null && wb.isWithinBounds(new BlockPos(entity.xOld, entity.yOld, entity.zOld)) && this.distanceToSqr(vec3d.x, vec3d.y, vec3d.z) >= this.distanceToSqr(entity)) {
                        entity.getNavigation().moveTo(vec3d.x, vec3d.y, vec3d.z, 2.0D);
                    }
                }
            }
        }

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_TYPE, ScarecrowType.DEFAULT.type);
    }

    public void setScarecrowType(ScarecrowType p_28929_) {
        this.entityData.set(DATA_TYPE, p_28929_.type);
    }

    public ScarecrowType getScarecrowType() {
        return ScarecrowType.byType(this.entityData.get(DATA_TYPE));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag p_31619_) {
        super.addAdditionalSaveData(p_31619_);
        p_31619_.putString("Type", this.getScarecrowType().type);

    }

    public void readAdditionalSaveData(CompoundTag p_28936_) {
        super.readAdditionalSaveData(p_28936_);
        this.setScarecrowType(ScarecrowType.byType(p_28936_.getString("Type")));
    }

    private ItemStack getCostumeStack() {
        switch(this.getScarecrowType()) {
            case DEFAULT:
            default:
                return ItemStack.EMPTY;
            case WOLF:
                return new ItemStack(ItemInit.WOLF_COSTUME);
            case OCELOT:
                return new ItemStack(ItemInit.OCELOT_COSTUME);
            case GUARDIAN:
                return new ItemStack(ItemInit.GUARDIAN_COSTUME);
        }
    }

    protected void dropEquipment() {
        super.dropEquipment();
        this.spawnAtLocation(getCostumeStack());
    }

    @Override
    public InteractionResult interact(Player p_19978_, InteractionHand p_19979_) {
        ItemStack itemstack = p_19978_.getItemInHand(p_19979_);
        if (itemstack.getItem() instanceof CostumeItem) {
            if (!p_19978_.getAbilities().instabuild) {
                dropEquipment();
            }
            this.setScarecrowType(((CostumeItem) itemstack.getItem()).getType());
            this.level.playLocalSound(this.getX() + 0.5D, this.getY() + 0.5D, this.getZ() + 0.5D, SoundEvents.ARMOR_EQUIP_LEATHER, this.getSoundSource(), 1.0F, 1F, false);
            if (!p_19978_.getAbilities().instabuild) {
                itemstack.shrink(1);
            }

            return InteractionResult.SUCCESS;
        } else if (itemstack.isEmpty()) {
            if (!p_19978_.getAbilities().instabuild) {
                dropEquipment();
            }
            this.setScarecrowType(ScarecrowType.DEFAULT);
            this.level.playLocalSound(this.getX() + 0.5D, this.getY() + 0.5D, this.getZ() + 0.5D, SoundEvents.ARMOR_EQUIP_LEATHER, this.getSoundSource(), 1.0F, 1F, false);
            if (!p_19978_.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        return super.interact(p_19978_, p_19979_);
    }
}
