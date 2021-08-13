package com.itayfeder.feders_scarecrows.client.layer;

import com.itayfeder.feders_scarecrows.FedersScarecrowsMod;
import com.itayfeder.feders_scarecrows.client.ModModelLayers;
import com.itayfeder.feders_scarecrows.client.model.ScarecrowModel;
import com.itayfeder.feders_scarecrows.client.model.outfits.WolfOutfitModel;
import com.itayfeder.feders_scarecrows.common.entities.scarecrow.Scarecrow;
import com.itayfeder.feders_scarecrows.common.entities.scarecrow.ScarecrowType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class WolfOutfitLayer extends RenderLayer<Scarecrow, ScarecrowModel<Scarecrow>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(FedersScarecrowsMod.MOD_ID, "textures/entity/scarecrow/wolf_scarecrow.png");
    private final WolfOutfitModel<Scarecrow> model;

    public WolfOutfitLayer(RenderLayerParent<Scarecrow, ScarecrowModel<Scarecrow>> p_174533_, EntityModelSet p_174534_) {
        super(p_174533_);
        this.model = new WolfOutfitModel<>(p_174534_.bakeLayer(ModModelLayers.WOLF_OUTFIT));
    }

    @Override
    public void render(PoseStack p_117421_, MultiBufferSource p_117422_, int p_117423_, Scarecrow entity, float p_117425_, float p_117426_, float p_117427_, float p_117428_, float p_117429_, float p_117430_) {
        if (entity.getScarecrowType() == ScarecrowType.WOLF) {
            if (entity.isInvisible()) {
                Minecraft minecraft = Minecraft.getInstance();
                boolean flag = minecraft.shouldEntityAppearGlowing(entity);
                if (flag) {
                    this.getParentModel().copyPropertiesTo(this.model);
                    this.model.prepareMobModel(entity, p_117425_, p_117426_, p_117427_);
                    this.model.setupAnim(entity, p_117425_, p_117426_, p_117428_, p_117429_, p_117430_);
                    VertexConsumer vertexconsumer = p_117422_.getBuffer(RenderType.outline(LOCATION));
                    this.model.renderToBuffer(p_117421_, vertexconsumer, p_117423_, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 0.0F, 0.0F, 0.0F, 1.0F);
                }

            }
            else {
                coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, LOCATION, p_117421_, p_117422_, p_117423_, entity, p_117425_, p_117426_, p_117428_, p_117429_, p_117430_, p_117427_, 1F, 1F, 1F);
            }
        }
    }
}