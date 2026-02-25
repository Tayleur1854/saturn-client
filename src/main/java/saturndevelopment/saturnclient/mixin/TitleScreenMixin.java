/*
 * This file is part of the Saturn Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Saturn Development.
 */

package saturndevelopment.saturnclient.mixin;

import saturndevelopment.saturnclient.systems.config.Config;
import saturndevelopment.saturnclient.utils.player.TitleScreenCredits;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    public TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        float centerX = this.width / 2f;
        float centerY = this.height / 2f;
        float planetRadius = 80f;

        // Black background
        context.fill(0, 0, this.width, this.height, 0xFF000000);

        // Draw some stars randomly in the background
        java.util.Random rand = new java.util.Random(12345L);
        for (int i = 0; i < 200; i++) {
            int sx = rand.nextInt(this.width);
            int sy = rand.nextInt(this.height);
            int brightness = 150 + rand.nextInt(105);
            int starColor = (0xFF << 24) | (brightness << 16) | (brightness << 8) | brightness;
            context.fill(sx, sy, sx + 1, sy + 1, starColor);
        }

        // Ring colors (multiple passes for layered rings)
        int[] ringColors = {
            0xFF8B6914,  // dark gold
            0xFFA0784B,  // brown
            0xFFBDA66A,  // light gold
            0xFF8B6914,  // dark gold again
        };
        float[] ringRadiiX = { 130f, 150f, 170f, 190f };
        float[] ringRadiiY = { 24f,  28f,  32f,  22f  };

        // Draw back half of all rings (behind planet)
        for (int r = 0; r < ringColors.length; r++) {
            for (float t = (float) Math.PI; t < 2 * Math.PI; t += 0.005f) {
                float x = centerX + (float) Math.cos(t) * ringRadiiX[r];
                float y = centerY + 20 + (float) Math.sin(t) * ringRadiiY[r];
                context.fill((int) x, (int) y, (int) x + 3, (int) y + 3, ringColors[r]);
            }
        }

        // Draw planet body (filled circle)
        // Outer glow
        int glowColor = 0x336A5ACD;
        for (float dy = -(planetRadius + 10); dy <= (planetRadius + 10); dy++) {
            float maxDx = (float) Math.sqrt((planetRadius + 10) * (planetRadius + 10) - dy * dy);
            context.fill(
                (int)(centerX - maxDx), (int)(centerY + dy),
                (int)(centerX + maxDx), (int)(centerY + dy + 1),
                glowColor
            );
        }

        // Planet base color (purple)
        for (float dy = -planetRadius; dy <= planetRadius; dy++) {
            float dx = (float) Math.sqrt(planetRadius * planetRadius - dy * dy);
            // Gradient from lighter at top to darker at bottom
            float gradientFactor = (dy + planetRadius) / (2 * planetRadius);
            int r = (int)(100 + 40 * (1 - gradientFactor));
            int g = (int)(80 * (1 - gradientFactor));
            int b = (int)(200 + 55 * (1 - gradientFactor));
            int planetColor = (0xFF << 24) | (r << 16) | (g << 8) | b;
            context.fill(
                (int)(centerX - dx), (int)(centerY + dy),
                (int)(centerX + dx), (int)(centerY + dy + 1),
                planetColor
            );
        }

        // Planet highlight (top-left light reflection)
        int highlightColor = 0x55FFFFFF;
        float highlightRadius = planetRadius * 0.6f;
        for (float dy = -highlightRadius; dy <= 0; dy++) {
            float dx = (float) Math.sqrt(highlightRadius * highlightRadius - dy * dy);
            context.fill(
                (int)(centerX - dx * 0.8f), (int)(centerY + dy),
                (int)(centerX + dx * 0.1f), (int)(centerY + dy + 1),
                highlightColor
            );
        }

        // Draw front half of all rings (in front of planet)
        for (int r = 0; r < ringColors.length; r++) {
            for (float t = 0; t < Math.PI; t += 0.005f) {
                float x = centerX + (float) Math.cos(t) * ringRadiiX[r];
                float y = centerY + 20 + (float) Math.sin(t) * ringRadiiY[r];
                context.fill((int) x, (int) y, (int) x + 3, (int) y + 3, ringColors[r]);
            }
        }

        // Credits
        if (Config.get().titleScreenCredits.get()) TitleScreenCredits.render(context);
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void onMouseClicked(Click click, boolean doubled, CallbackInfoReturnable<Boolean> cir) {
        if (Config.get().titleScreenCredits.get() && click.button() == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            if (TitleScreenCredits.onClicked(click.x(), click.y())) cir.setReturnValue(true);
        }
    }
}
