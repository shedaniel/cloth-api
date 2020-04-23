package me.shedaniel.cloth.callbacks.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.ActionResult;

public final class ScreenRenderCallback {
    
    private ScreenRenderCallback() {}
    
    public interface Pre {
        ActionResult render(MatrixStack matrices, MinecraftClient client, Screen screen, int mouseX, int mouseY, float delta);
    }
    
    public interface Post {
        void render(MatrixStack matrices, MinecraftClient client, Screen screen, int mouseX, int mouseY, float delta);
    }
    
}
