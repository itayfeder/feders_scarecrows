package com.itayfeder.feders_scarecrows.client.model.outfits;

import com.google.common.collect.ImmutableList;
import com.itayfeder.feders_scarecrows.common.entities.TemplateScarecrow;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class GuardianOutfitModel  <T extends TemplateScarecrow> extends AgeableListModel<T> {
    private final ModelPart head;
    private final ModelPart spike1;
    private final ModelPart spike2;
    private final ModelPart spike3;
    private final ModelPart spike4;
    private final ModelPart spike5;
    private final ModelPart body;
    private final ModelPart leftArm;
    private final ModelPart rightArm;

    public GuardianOutfitModel(ModelPart p_170490_) {
        this.head = p_170490_.getChild("head");
        this.spike1 = p_170490_.getChild("spike1");
        this.spike2 = p_170490_.getChild("spike2");
        this.spike3 = p_170490_.getChild("spike3");
        this.spike4 = p_170490_.getChild("spike4");
        this.spike5 = p_170490_.getChild("spike5");
        this.body = p_170490_.getChild("body");
        this.leftArm = p_170490_.getChild("leftArm");
        this.rightArm = p_170490_.getChild("rightArm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("head", CubeListBuilder.create()
                        .texOffs(0, 38).addBox(-5.0F, -9.0F, -5.0F, 10.0F, 10.0F, 10.0F)
                , PartPose.offset(0.0F, 4.0F, 0.0F));

        partdefinition.addOrReplaceChild("spike1", CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1.0F, -9.0F, -1.0F, 2.0F, 5.0F, 2.0F)
                , PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));
        partdefinition.addOrReplaceChild("spike2", CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1.0F, -9.0F, -1.0F, 2.0F, 5.0F, 2.0F)
                , PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));
        partdefinition.addOrReplaceChild("spike3", CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1.0F, -9.0F, -1.0F, 2.0F, 5.0F, 2.0F)
                , PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 1.5708F));
        partdefinition.addOrReplaceChild("spike4", CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1.0F, -9.0F, -1.0F, 2.0F, 5.0F, 2.0F)
                , PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, -1.5708F));
        partdefinition.addOrReplaceChild("spike5", CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1.0F, -9.0F, -1.0F, 2.0F, 5.0F, 2.0F)
                , PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create()
                        .texOffs(8, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F)
                , PartPose.offset(0.0F, 4.0F, 0.0F));
        partdefinition.addOrReplaceChild("leftArm", CubeListBuilder.create()
                        .texOffs(32, 8).addBox(0.0F, -2.0F, -2.0F, 6.0F, 4.0F, 4.0F)
                , PartPose.offset(4.0F, 6.0F, 0.0F));
        partdefinition.addOrReplaceChild("rightArm", CubeListBuilder.create()
                        .texOffs(32, 16).addBox(-6.0F, -2.0F, -2.0F, 6.0F, 4.0F, 4.0F)
                , PartPose.offset(-4.0F, 6.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 64, 64);

    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of();
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(head, spike1, spike2, spike3, spike4, spike5, body, leftArm, rightArm);
    }

    @Override
    public void setupAnim(T p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_, float p_102623_) {

    }
}