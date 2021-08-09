package com.itayfeder.feders_scarecrows.common.items;

import com.itayfeder.feders_scarecrows.common.entities.Scarecrow;
import com.itayfeder.feders_scarecrows.common.entities.WolfScarecrow;
import com.itayfeder.feders_scarecrows.common.items.api.ParentedItem;
import com.itayfeder.feders_scarecrows.init.EntityTypeInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class WolfScarecrowItem extends ParentedItem {
    public WolfScarecrowItem(Item.Properties p_40503_, Item parentItem) {
        super(p_40503_, parentItem);
    }

    public InteractionResult useOn(UseOnContext p_40510_) {
        Direction direction = p_40510_.getClickedFace();
        if (direction == Direction.DOWN) {
            return InteractionResult.FAIL;
        } else {
            Level level = p_40510_.getLevel();
            BlockPlaceContext blockplacecontext = new BlockPlaceContext(p_40510_);
            BlockPos blockpos = blockplacecontext.getClickedPos();
            ItemStack itemstack = p_40510_.getItemInHand();
            Vec3 vec3 = Vec3.atBottomCenterOf(blockpos);
            AABB aabb = EntityTypeInit.WOLF_SCARECROW.getDimensions().makeBoundingBox(vec3.x(), vec3.y(), vec3.z());
            if (level.noCollision((Entity)null, aabb, (p_40505_) -> {
                return true;
            }) && level.getEntities((Entity)null, aabb).isEmpty()) {
                if (level instanceof ServerLevel) {
                    ServerLevel serverlevel = (ServerLevel)level;
                    WolfScarecrow armorstand = EntityTypeInit.WOLF_SCARECROW.create(serverlevel, itemstack.getTag(), (Component)null, p_40510_.getPlayer(), blockpos, MobSpawnType.SPAWN_EGG, true, true);
                    if (armorstand == null) {
                        return InteractionResult.FAIL;
                    }

                    float f = (float) Mth.floor((Mth.wrapDegrees(p_40510_.getRotation() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
                    armorstand.moveTo(armorstand.getX(), armorstand.getY(), armorstand.getZ(), f, 0.0F);
                    serverlevel.addFreshEntityWithPassengers(armorstand);
                    level.playSound((Player)null, armorstand.getX(), armorstand.getY(), armorstand.getZ(), SoundEvents.ARMOR_STAND_PLACE, SoundSource.BLOCKS, 0.75F, 0.8F);
                    level.gameEvent(p_40510_.getPlayer(), GameEvent.ENTITY_PLACE, armorstand);
                }

                itemstack.shrink(1);
                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.FAIL;
            }
        }
    }
}