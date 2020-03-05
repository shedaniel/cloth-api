package me.shedaniel.cloth.callbacks.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ActionResult;

public final class ScreenRenderCallback {
    
    private ScreenRenderCallback() {}
    
    public interface Pre {
        ActionResult render(MinecraftClient client, Screen screen, int mouseX, int mouseY, float delta);
    }
    
    public interface Post {
        void render(MinecraftClient client, Screen screen, int mouseX, int mouseY, float delta);
    }
    
}
