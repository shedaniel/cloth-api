package me.shedaniel.cloth.callbacks.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Screen;
import net.minecraft.util.ActionResult;

public interface ScreenKeyReleasedCallback {
    
    ActionResult keyReleased(MinecraftClient client, Screen screen, int keyCode, int scanCode, int modifiers);
    
}
