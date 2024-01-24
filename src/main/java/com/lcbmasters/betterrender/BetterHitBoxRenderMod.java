package com.lcbmasters.betterrender;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class BetterHitBoxRenderMod {

    public static boolean isHitBoxEnabled;

    private Minecraft mc;
    private RenderManager renderManager;

    private double renderPosX;
    private double renderPosY;
    private double renderPosZ;

    public BetterHitBoxRenderMod(Minecraft mc, RenderManager renderManager) {
        this.mc = mc;
        this.renderManager = renderManager;
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (isHitBoxEnabled) {
            float partialTicks = event.partialTicks;
            List<Entity> loadedEntityList = Minecraft.getMinecraft().theWorld.loadedEntityList;

            Entity entity = this.mc.getRenderViewEntity();
            double d3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks;
            double d4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks;
            double d5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks;
            this.setRenderPosition(d3, d4, d5);

            for (Entity entityLoaded : loadedEntityList) {
                if (!(entityLoaded instanceof EntityPlayerSP)) {
                    try {
                        renderEntityBoundingBox(entityLoaded, partialTicks);
                    } catch (Exception ignored) {
                        System.out.println("failed");
                    }
                }
            }
        }
    }

    private void setRenderPosition(double renderPosXIn, double renderPosYIn, double renderPosZIn) {
        this.renderPosX = renderPosXIn;
        this.renderPosY = renderPosYIn;
        this.renderPosZ = renderPosZIn;
    }

    private void renderEntityBoundingBox(Entity entity, float partialTicks) {
        //调用原版渲染方法
        if (entity.ticksExisted == 0) {
            entity.lastTickPosX = entity.posX;
            entity.lastTickPosY = entity.posY;
            entity.lastTickPosZ = entity.posZ;
        }

        double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks;
        double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks;
        double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks;
        int i = entity.getBrightnessForRender(partialTicks);

        if (entity.isBurning()) {
            i = 15728880;
        }

        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j / 1.0F, (float) k / 1.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        // 在这里实现你的碰撞箱渲染逻辑
        renderCustomBoundingBox(entity, d0 - this.renderPosX, d1 - this.renderPosY, d2 - this.renderPosZ, partialTicks);
    }

    private void renderCustomBoundingBox(Entity entityIn, double x, double y, double z, float partialTicks) {
        GlStateManager.depthMask(false);
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        float f = entityIn.width / 2.0F;
        AxisAlignedBB axisalignedbb = entityIn.getEntityBoundingBox();
        AxisAlignedBB axisalignedbb1 = new AxisAlignedBB(axisalignedbb.minX - entityIn.posX + x, axisalignedbb.minY - entityIn.posY + y, axisalignedbb.minZ - entityIn.posZ + z, axisalignedbb.maxX - entityIn.posX + x, axisalignedbb.maxY - entityIn.posY + y, axisalignedbb.maxZ - entityIn.posZ + z);

        if (entityIn instanceof EntityLivingBase || entityIn instanceof EntityArrow) {

            RenderGlobal.drawOutlinedBoundingBox(axisalignedbb1, 255, 255, 255, 255);
            try {
                Entity entityHit = Minecraft.getMinecraft().objectMouseOver.entityHit;

                if (entityIn.getUniqueID().toString().equals(entityHit.getUniqueID().toString())) {
                    RenderGlobal.drawOutlinedBoundingBox(axisalignedbb1, 255, 0, 0, 255);
                }
            } catch (Exception e) {
            }
        }
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
    }
}