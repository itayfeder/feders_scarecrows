package com.itayfeder.feders_scarecrows.common.entities.scarecrow;

import com.itayfeder.feders_scarecrows.init.EntityTypeInit;
import com.itayfeder.feders_scarecrows.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class TemplateScarecrow extends LivingEntity {
    public static final int WOBBLE_TIME = 5;

    private static final EntityDimensions MARKER_DIMENSIONS = new EntityDimensions(0.0F, 0.0F, true);
    private static final EntityDimensions BABY_DIMENSIONS = EntityTypeInit.SCARECROW.getDimensions().scale(0.5F);
    public static final EntityDataAccessor<Byte> DATA_CLIENT_FLAGS = SynchedEntityData.defineId(Scarecrow.class, EntityDataSerializers.BYTE);
    private static final Predicate<Entity> RIDABLE_MINECARTS = (p_31582_) -> {
        return p_31582_ instanceof AbstractMinecart && ((AbstractMinecart)p_31582_).canBeRidden();
    };
    private boolean invisible;
    public long lastHit;
    private final NonNullList<ItemStack> handItems = NonNullList.withSize(2, ItemStack.EMPTY);
    private final NonNullList<ItemStack> armorItems = NonNullList.withSize(4, ItemStack.EMPTY);

    public TemplateScarecrow(EntityType<? extends TemplateScarecrow> p_31553_, Level p_31554_) {
        super(p_31553_, p_31554_);
        this.maxUpStep = 0.0F;
    }

    public void refreshDimensions() {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        super.refreshDimensions();
        this.setPos(d0, d1, d2);
    }

    private boolean hasPhysics() {
        return !this.isMarker() && !this.isNoGravity();
    }

    public boolean isEffectiveAi() {
        return super.isEffectiveAi() && this.hasPhysics();
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_CLIENT_FLAGS, (byte)0);
    }

    public void addAdditionalSaveData(CompoundTag p_31619_) {
        super.addAdditionalSaveData(p_31619_);

        p_31619_.putBoolean("Invisible", this.isInvisible());
        p_31619_.putBoolean("Small", this.isSmall());
        if (this.isMarker()) {
            p_31619_.putBoolean("Marker", this.isMarker());
        }
    }

    public void readAdditionalSaveData(CompoundTag p_31600_) {
        super.readAdditionalSaveData(p_31600_);

        this.setInvisible(p_31600_.getBoolean("Invisible"));
        this.setSmall(p_31600_.getBoolean("Small"));
        this.setMarker(p_31600_.getBoolean("Marker"));
        this.noPhysics = !this.hasPhysics();
    }

    public boolean isPushable() {
        return false;
    }

    protected void doPush(Entity p_31564_) {
    }

    protected void pushEntities() {
        List<Entity> list = this.level.getEntities(this, this.getBoundingBox(), RIDABLE_MINECARTS);

        for(int i = 0; i < list.size(); ++i) {
            Entity entity = list.get(i);
            if (this.distanceToSqr(entity) <= 0.2D) {
                entity.push(this);
            }
        }

    }

    public InteractionResult interactAt(Player p_31594_, Vec3 p_31595_, InteractionHand p_31596_) {
        return InteractionResult.PASS;
    }

    public boolean hurt(DamageSource p_31579_, float p_31580_) {
        if (!this.level.isClientSide && !this.isRemoved()) {
            if (DamageSource.OUT_OF_WORLD.equals(p_31579_)) {
                this.kill();
                return false;
            } else if (!this.isInvulnerableTo(p_31579_) && !this.invisible && !this.isMarker()) {
                if (p_31579_.isExplosion()) {
                    this.brokenByAnything(p_31579_);
                    this.kill();
                    return false;
                } else if (DamageSource.IN_FIRE.equals(p_31579_)) {
                    if (this.isOnFire()) {
                        this.causeDamage(p_31579_, 0.15F);
                    } else {
                        this.setSecondsOnFire(5);
                    }

                    return false;
                } else if (DamageSource.ON_FIRE.equals(p_31579_) && this.getHealth() > 0.5F) {
                    this.causeDamage(p_31579_, 4.0F);
                    return false;
                } else {
                    boolean flag = p_31579_.getDirectEntity() instanceof AbstractArrow;
                    boolean flag1 = flag && ((AbstractArrow)p_31579_.getDirectEntity()).getPierceLevel() > 0;
                    boolean flag2 = "player".equals(p_31579_.getMsgId());
                    if (!flag2 && !flag) {
                        return false;
                    } else if (p_31579_.getEntity() instanceof Player && !((Player)p_31579_.getEntity()).getAbilities().mayBuild) {
                        return false;
                    } else if (p_31579_.isCreativePlayer()) {
                        this.playBrokenSound();
                        this.showBreakingParticles();
                        this.kill();
                        return flag1;
                    } else {
                        long i = this.level.getGameTime();
                        if (i - this.lastHit > 5L && !flag) {
                            this.level.broadcastEntityEvent(this, (byte)32);
                            this.gameEvent(GameEvent.ENTITY_DAMAGED, p_31579_.getEntity());
                            this.lastHit = i;
                        } else {
                            this.brokenByPlayer(p_31579_);
                            this.showBreakingParticles();
                            this.kill();
                        }

                        return true;
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void handleEntityEvent(byte p_31568_) {
        if (p_31568_ == 32) {
            if (this.level.isClientSide) {
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ARMOR_STAND_HIT, this.getSoundSource(), 0.3F, 1.0F, false);
                this.lastHit = this.level.getGameTime();
            }
        } else {
            super.handleEntityEvent(p_31568_);
        }

    }

    public Iterable<ItemStack> getHandSlots() {
        return this.handItems;
    }

    public Iterable<ItemStack> getArmorSlots() {
        return this.armorItems;
    }

    public ItemStack getItemBySlot(EquipmentSlot p_31612_) {
        switch(p_31612_.getType()) {
            case HAND:
                return this.handItems.get(p_31612_.getIndex());
            case ARMOR:
                return this.armorItems.get(p_31612_.getIndex());
            default:
                return ItemStack.EMPTY;
        }
    }

    public void setItemSlot(EquipmentSlot p_31584_, ItemStack p_31585_) {
        this.verifyEquippedItem(p_31585_);
        switch(p_31584_.getType()) {
            case HAND:
                this.equipEventAndSound(p_31585_);
                this.handItems.set(p_31584_.getIndex(), p_31585_);
                break;
            case ARMOR:
                this.equipEventAndSound(p_31585_);
                this.armorItems.set(p_31584_.getIndex(), p_31585_);
        }

    }

    public boolean shouldRenderAtSqrDistance(double p_31574_) {
        double d0 = this.getBoundingBox().getSize() * 4.0D;
        if (Double.isNaN(d0) || d0 == 0.0D) {
            d0 = 4.0D;
        }

        d0 = d0 * 64.0D;
        return p_31574_ < d0 * d0;
    }

    private void showBreakingParticles() {
        if (this.level instanceof ServerLevel) {
            ((ServerLevel)this.level).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.OAK_PLANKS.defaultBlockState()), this.getX(), this.getY(0.6666666666666666D), this.getZ(), 10, (double)(this.getBbWidth() / 4.0F), (double)(this.getBbHeight() / 4.0F), (double)(this.getBbWidth() / 4.0F), 0.05D);
        }

    }

    private void causeDamage(DamageSource p_31649_, float p_31650_) {
        float f = this.getHealth();
        f = f - p_31650_;
        if (f <= 0.5F) {
            this.brokenByAnything(p_31649_);
            this.kill();
        } else {
            this.setHealth(f);
            this.gameEvent(GameEvent.ENTITY_DAMAGED, p_31649_.getEntity());
        }

    }

    private void brokenByPlayer(DamageSource p_31647_) {
        Block.popResource(this.level, this.blockPosition(), getPickResult());
        this.brokenByAnything(p_31647_);
    }

    private void brokenByAnything(DamageSource p_31654_) {
        this.playBrokenSound();
        this.dropAllDeathLoot(p_31654_);
    }

    private void playBrokenSound() {
        this.level.playSound((Player)null, this.getX(), this.getY(), this.getZ(), SoundEvents.ARMOR_STAND_BREAK, this.getSoundSource(), 1.0F, 1.0F);
    }

    protected float tickHeadTurn(float p_31644_, float p_31645_) {
        this.yBodyRotO = this.yRotO;
        this.yBodyRot = this.getYRot();
        return 0.0F;
    }

    protected float getStandingEyeHeight(Pose p_31614_, EntityDimensions p_31615_) {
        return p_31615_.height * (this.isBaby() ? 0.5F : 0.9F);
    }

    public double getMyRidingOffset() {
        return this.isMarker() ? 0.0D : (double)0.1F;
    }

    public void travel(Vec3 p_31656_) {
        if (this.hasPhysics()) {
            super.travel(p_31656_);
        }
    }

    public void setYBodyRot(float p_31670_) {
        this.yBodyRotO = this.yRotO = p_31670_;
        this.yHeadRotO = this.yHeadRot = p_31670_;
    }

    public void setYHeadRot(float p_31668_) {
        this.yBodyRotO = this.yRotO = p_31668_;
        this.yHeadRotO = this.yHeadRot = p_31668_;
    }

    protected void updateInvisibilityStatus() {
        this.setInvisible(this.invisible);
    }

    public void setInvisible(boolean p_31663_) {
        this.invisible = p_31663_;
        super.setInvisible(p_31663_);
    }

    public boolean isBaby() {
        return this.isSmall();
    }

    public void kill() {
        this.remove(Entity.RemovalReason.KILLED);
    }

    public boolean ignoreExplosion() {
        return this.isInvisible();
    }

    public PushReaction getPistonPushReaction() {
        return this.isMarker() ? PushReaction.IGNORE : super.getPistonPushReaction();
    }

    private void setSmall(boolean p_31604_) {
        this.entityData.set(DATA_CLIENT_FLAGS, this.setBit(this.entityData.get(DATA_CLIENT_FLAGS), 1, p_31604_));
    }

    public boolean isSmall() {
        return (this.entityData.get(DATA_CLIENT_FLAGS) & 1) != 0;
    }

    private void setMarker(boolean p_31682_) {
        this.entityData.set(DATA_CLIENT_FLAGS, this.setBit(this.entityData.get(DATA_CLIENT_FLAGS), 16, p_31682_));
    }

    public boolean isMarker() {
        return (this.entityData.get(DATA_CLIENT_FLAGS) & 16) != 0;
    }

    private byte setBit(byte p_31570_, int p_31571_, boolean p_31572_) {
        if (p_31572_) {
            p_31570_ = (byte)(p_31570_ | p_31571_);
        } else {
            p_31570_ = (byte)(p_31570_ & ~p_31571_);
        }

        return p_31570_;
    }

    public boolean isPickable() {
        return super.isPickable() && !this.isMarker();
    }

    public boolean skipAttackInteraction(Entity p_31687_) {
        return p_31687_ instanceof Player && !this.level.mayInteract((Player)p_31687_, this.blockPosition());
    }

    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    protected SoundEvent getFallDamageSound(int p_31673_) {
        return SoundEvents.ARMOR_STAND_FALL;
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource p_31636_) {
        return SoundEvents.ARMOR_STAND_HIT;
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.ARMOR_STAND_BREAK;
    }

    public void thunderHit(ServerLevel p_31576_, LightningBolt p_31577_) {
    }

    public boolean isAffectedByPotions() {
        return false;
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> p_31602_) {
        if (DATA_CLIENT_FLAGS.equals(p_31602_)) {
            this.refreshDimensions();
            this.blocksBuilding = !this.isMarker();
        }

        super.onSyncedDataUpdated(p_31602_);
    }

    public boolean attackable() {
        return false;
    }

    public EntityDimensions getDimensions(Pose p_31587_) {
        return this.getDimensionsMarker(this.isMarker());
    }

    private EntityDimensions getDimensionsMarker(boolean p_31684_) {
        if (p_31684_) {
            return MARKER_DIMENSIONS;
        } else {
            return this.isBaby() ? BABY_DIMENSIONS : this.getType().getDimensions();
        }
    }

    public Vec3 getLightProbePosition(float p_31665_) {
        if (this.isMarker()) {
            AABB aabb = this.getDimensionsMarker(false).makeBoundingBox(this.position());
            BlockPos blockpos = this.blockPosition();
            int i = Integer.MIN_VALUE;

            for(BlockPos blockpos1 : BlockPos.betweenClosed(new BlockPos(aabb.minX, aabb.minY, aabb.minZ), new BlockPos(aabb.maxX, aabb.maxY, aabb.maxZ))) {
                int j = Math.max(this.level.getBrightness(LightLayer.BLOCK, blockpos1), this.level.getBrightness(LightLayer.SKY, blockpos1));
                if (j == 15) {
                    return Vec3.atCenterOf(blockpos1);
                }

                if (j > i) {
                    i = j;
                    blockpos = blockpos1.immutable();
                }
            }

            return Vec3.atCenterOf(blockpos);
        } else {
            return super.getLightProbePosition(p_31665_);
        }
    }

    public ItemStack getPickResult() {
        return new ItemStack(ItemInit.SCARECROW);
    }

    public boolean canBeSeenByAnyone() {
        return !this.isInvisible() && !this.isMarker();
    }
}