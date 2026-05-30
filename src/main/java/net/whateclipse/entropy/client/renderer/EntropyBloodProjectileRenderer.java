package net.whateclipse.entropy.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.whateclipse.entropy.Entropy;
import net.whateclipse.entropy.entities.EntropyBloodProjectileEntity;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import javax.annotation.Nonnull;

public class EntropyBloodProjectileRenderer extends EntityRenderer<EntropyBloodProjectileEntity> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Entropy.MODID,
            "textures/entity/blood_projectile.png");

    public EntropyBloodProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(@Nonnull EntropyBloodProjectileEntity entity, float entityYaw, float partialTicks,
                       @Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        // Rotate to match movement direction
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));

        // Apply the random Y rotation offset so each projectile looks different
        poseStack.mulPose(Axis.XP.rotationDegrees(entity.getRandomYRot()));

        // Rotate the texture by 90°
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));

        VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        PoseStack.Pose posestack$pose = poseStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();

        float x0 = -1.0F;
        float x1 = 1.0F;
        float y0 = -1.0F; // bottom
        float y1 =  1.0F; // top

// Front face
        this.vertex(matrix4f, matrix3f, vertexconsumer, x1, y1, 0, 0, 0, 0, 0, 1, packedLight);
        this.vertex(matrix4f, matrix3f, vertexconsumer, x0, y1, 0, 1, 0, 0, 0, 1, packedLight);
        this.vertex(matrix4f, matrix3f, vertexconsumer, x0, y0, 0, 1, 1, 0, 0, 1, packedLight);
        this.vertex(matrix4f, matrix3f, vertexconsumer, x1, y0, 0, 0, 1, 0, 0, 1, packedLight);

// Back face (reversed winding, normal flipped)
        this.vertex(matrix4f, matrix3f, vertexconsumer, x1, y0, 0, 0, 1, 0, 0, -1, packedLight);
        this.vertex(matrix4f, matrix3f, vertexconsumer, x0, y0, 0, 1, 1, 0, 0, -1, packedLight);
        this.vertex(matrix4f, matrix3f, vertexconsumer, x0, y1, 0, 1, 0, 0, 0, -1, packedLight);
        this.vertex(matrix4f, matrix3f, vertexconsumer, x1, y1, 0, 0, 0, 0, 0, -1, packedLight);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    private void vertex(Matrix4f matrix, Matrix3f normal, VertexConsumer consumer, float x, float y, float z, float u,
                        float v, int nx, int ny, int nz, int packedLight) {
        @SuppressWarnings("null")
        VertexConsumer vertex = consumer.addVertex(matrix, x, y, z);
        vertex.setColor(255, 255, 255, 255)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(nx, ny, nz);
    }

    @Override
    @Nonnull
    public ResourceLocation getTextureLocation(@Nonnull EntropyBloodProjectileEntity entity) {
        return TEXTURE;
    }
}