package me.shedaniel.cloth.callbacks.client;

import me.shedaniel.cloth.hooks.ScreenHooks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ActionResult;

public final class ScreenInitCallback {
    
    private ScreenInitCallback() {}
    
    public interface Pre {
        ActionResult init(MinecraftClient client, Screen screen, ScreenHooks screenHooks);
    }
    
    public interface Post {
        void init(MinecraftClient client, Screen screen, ScreenHooks screenHooks);
    }
    
}
