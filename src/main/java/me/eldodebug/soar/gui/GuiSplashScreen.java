package me.eldodebug.soar.gui;

import me.eldodebug.soar.Soar;
import me.eldodebug.soar.management.nanovg.NanoVGManager;
import me.eldodebug.soar.management.nanovg.font.Fonts;
import me.eldodebug.soar.management.nanovg.font.Icon;
import me.eldodebug.soar.utils.GlUtils;
import me.eldodebug.soar.utils.animation.normal.Animation;
import me.eldodebug.soar.utils.animation.normal.Direction;
import me.eldodebug.soar.utils.animation.normal.other.DecelerateAnimation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GuiSplashScreen {

    private final Minecraft mc = Minecraft.getMinecraft();

    private Framebuffer framebuffer;

    private Animation fadeAnimation;

    public void draw() {

        framebuffer = GlUtils.createFrameBuffer(framebuffer);

        ScaledResolution sr = new ScaledResolution(mc);
        int scaleFactor = sr.getScaleFactor();

        if (fadeAnimation == null) {
            fadeAnimation = new DecelerateAnimation(1000, 1);
            fadeAnimation.setDirection(Direction.FORWARDS);
            fadeAnimation.reset();
        }

        mc.updateDisplay();

        while (!fadeAnimation.isDone(Direction.FORWARDS)) {

            framebuffer.framebufferClear();
            framebuffer.bindFramebuffer(true);

            GlStateManager.matrixMode(GL11.GL_PROJECTION);
            GlStateManager.loadIdentity();
            GlStateManager.ortho(0.0D, sr.getScaledWidth(), sr.getScaledHeight(), 0.0D, 1000.0D, 3000.0D);
            GlStateManager.matrixMode(GL11.GL_MODELVIEW);
            GlStateManager.loadIdentity();
            GlStateManager.translate(0.0F, 0.0F, -2000.0F);
            GlStateManager.disableLighting();
            GlStateManager.disableFog();
            GlStateManager.disableDepth();
            GlStateManager.enableTexture2D();

            GlStateManager.color(0, 0, 0, 0);
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            NanoVGManager nvg = Soar.getInstance().getNanoVGManager();
            nvg.setupAndDraw(() -> {
                nvg.drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), Color.BLACK);
                nvg.drawCenteredText(Icon.SOAR, sr.getScaledWidth() / 2, (sr.getScaledHeight() / 2) - (nvg.getTextHeight(Icon.SOAR, 130, Fonts.ICON) / 2) - 1, new Color(255, 255, 255, (int) (fadeAnimation.getValue() * 255)), 130, Fonts.ICON);
            });

            framebuffer.unbindFramebuffer();
            framebuffer.framebufferRender(sr.getScaledWidth() * scaleFactor, sr.getScaledHeight() * scaleFactor);

            GlUtils.setAlphaLimit(1);

            mc.updateDisplay();
        }
    }
}
