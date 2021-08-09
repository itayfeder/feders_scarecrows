package com.itayfeder.feders_scarecrows.common.entities;

import com.itayfeder.feders_scarecrows.init.EntityTypeInit;
import com.itayfeder.feders_scarecrows.init.ItemInit;
import com.itayfeder.feders_scarecrows.utils.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Predicate;

public class GuardianScarecrow extends TemplateScarecrow {
    private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and((entity) -> {
        if (entity instanceof LivingEntity) {
            return ModTags.ENDERMEN.contains(entity.getType());
        }
        return false;
    });

    public GuardianScarecrow(Level p_31556_, double p_31557_, double p_31558_, double p_31559_) {
        super(EntityTypeInit.GUARDIAN_SCARECROW, p_31556_);
        this.setPos(p_31557_, p_31558_, p_31559_);
    }

    public GuardianScarecrow(EntityType<GuardianScarecrow> scarecrowEntityType, Level level) {
        super(scarecrowEntityType, level);
    }

    public ItemStack getPickResult() {
        return new ItemStack(ItemInit.GUARDIAN_SCARECROW);
    }

    @Override
    public void tick() {
        super.tick();
        AABB range = new AABB(this.getX() - 24.0D, this.getY() - 4.0D, this.getZ() - 24.0D, this.getX() + 24.0D, this.getY() + 4.0D, this.getZ() + 24.0D);
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
}