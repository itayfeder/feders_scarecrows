package com.itayfeder.feders_scarecrows.client.renderer;

import com.itayfeder.feders_scarecrows.FedersScarecrowsMod;
import com.itayfeder.feders_scarecrows.client.ModModelLayers;
import com.itayfeder.feders_scarecrows.client.model.GuardianScarecrowModel;
import com.itayfeder.feders_scarecrows.client.model.OcelotScarecrowModel;
import com.itayfeder.feders_scarecrows.common.entities.TemplateScarecrow;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import javax.annotation.Nullable;

public class GuardianScarecrowRenderer extends LivingEntityRenderer<TemplateScarecrow, GuardianScarecrowModel<TemplateScarecrow>> {
    private static final ResourceLocation DEFAULT = new ResourceLocation(FedersScarecrowsMod.MOD_ID, "textures/entity/scarecrow/guardian_scarecrow.png");

    public GuardianScarecrowRenderer(EntityRendererProvider.Context p_174289_) {
        super(p_174289_, new GuardianScarecrowModel<>(p_174289_.bakeLayer(ModModelLayers.GUARDIAN_SCARECROW)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(TemplateScarecrow p_114482_) {
        return DEFAULT;
    }

    protected void setupRotations(TemplateScarecrow p_113800_, PoseStack p_113801_, float p_113802_, float p_113803_, float p_113804_) {
        p_113801_.mulPose(Vector3f.YP.rotationDegrees(180.0F - p_113803_));
        float f = (float)(p_113800_.level.getGameTime() - p_113800_.lastHit) + p_113804_;
        if (f < 5.0F) {
            p_113801_.mulPose(Vector3f.YP.rotationDegrees(Mth.sin(f / 1.5F * (float)Math.PI) * 3.0F));
        }

    }

    protected boolean shouldShowName(TemplateScarecrow p_113815_) {
        double d0 = this.entityRenderDispatcher.distanceToSqr(p_113815_);
        float f = p_113815_.isCrouching() ? 32.0F : 64.0F;
        return d0 >= (double)(f * f) ? false : p_113815_.isCustomNameVisible();
    }

    @Nullable
    protected RenderType getRenderType(TemplateScarecrow p_113806_, boolean p_113807_, boolean p_113808_, boolean p_113809_) {
        if (!p_113806_.isMarker()) {
            return super.getRenderType(p_113806_, p_113807_, p_113808_, p_113809_);
        } else {
            ResourceLocation resourcelocation = this.getTextureLocation(p_113806_);
            if (p_113808_) {
                return RenderType.entityTranslucent(resourcelocation, false);
            } else {
                return p_113807_ ? RenderType.entityCutoutNoCull(resourcelocation, false) : null;
            }
        }
    }

}