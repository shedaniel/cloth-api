package me.shedaniel.cloth.callbacks.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Screen;
import net.minecraft.util.ActionResult;

public interface ScreenRenderCallback {
    
    public static interface Pre {
        ActionResult render(MinecraftClient client, Screen screen, int mouseX, int mouseY, float delta);
    }
    
    public static interface Post {
        void render(MinecraftClient client, Screen screen, int mouseX, int mouseY, float delta);
    }
    
}
