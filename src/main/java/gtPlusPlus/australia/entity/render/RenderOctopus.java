package gtPlusPlus.australia.entity.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gtPlusPlus.australia.entity.type.EntityOctopus;
import gtPlusPlus.core.lib.CORE;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderOctopus extends RenderLiving {

    private static final ResourceLocation octopusTextures =
            new ResourceLocation(CORE.MODID + ":" + "textures/entity/australia/octopus.png");

    public RenderOctopus(ModelBase p_i1268_1_, float p_i1268_2_) {
        super(p_i1268_1_, p_i1268_2_);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method,
     * always casting down its argument and then handing it off to a worker function
     * which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void
     * func_76986_a(T entity, double d, double d1, double d2, float f, float f1).
     * But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(
            EntityOctopus p_76986_1_,
            double p_76986_2_,
            double p_76986_4_,
            double p_76986_6_,
            float p_76986_8_,
            float p_76986_9_) {
        super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless
     * you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityOctopus p_110775_1_) {
        return octopusTextures;
    }

    protected void rotateCorpse(EntityOctopus p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_) {
        float f3 = p_77043_1_.prevSquidPitch + (p_77043_1_.octopusPitch - p_77043_1_.prevSquidPitch) * p_77043_4_;
        float f4 = p_77043_1_.prevSquidYaw + (p_77043_1_.octopusYaw - p_77043_1_.prevSquidYaw) * p_77043_4_;
        GL11.glTranslatef(0.0F, 0.5F, 0.0F);
        GL11.glRotatef(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(f3, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(f4, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(0.0F, -1.2F, 0.0F);
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float handleRotationFloat(EntityOctopus p_77044_1_, float p_77044_2_) {
        return p_77044_1_.lastTentacleAngle + (p_77044_1_.tentacleAngle - p_77044_1_.lastTentacleAngle) * p_77044_2_;
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method,
     * always casting down its argument and then handing it off to a worker function
     * which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void
     * func_76986_a(T entity, double d, double d1, double d2, float f, float f1).
     * But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(
            EntityLiving p_76986_1_,
            double p_76986_2_,
            double p_76986_4_,
            double p_76986_6_,
            float p_76986_8_,
            float p_76986_9_) {
        this.doRender((EntityOctopus) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float handleRotationFloat(EntityLivingBase p_77044_1_, float p_77044_2_) {
        return this.handleRotationFloat((EntityOctopus) p_77044_1_, p_77044_2_);
    }

    protected void rotateCorpse(EntityLivingBase p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_) {
        this.rotateCorpse((EntityOctopus) p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method,
     * always casting down its argument and then handing it off to a worker function
     * which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void
     * func_76986_a(T entity, double d, double d1, double d2, float f, float f1).
     * But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(
            EntityLivingBase p_76986_1_,
            double p_76986_2_,
            double p_76986_4_,
            double p_76986_6_,
            float p_76986_8_,
            float p_76986_9_) {
        this.doRender((EntityOctopus) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless
     * you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return this.getEntityTexture((EntityOctopus) p_110775_1_);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method,
     * always casting down its argument and then handing it off to a worker function
     * which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void
     * func_76986_a(T entity, double d, double d1, double d2, float f, float f1).
     * But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(
            Entity p_76986_1_,
            double p_76986_2_,
            double p_76986_4_,
            double p_76986_6_,
            float p_76986_8_,
            float p_76986_9_) {
        this.doRender((EntityOctopus) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
