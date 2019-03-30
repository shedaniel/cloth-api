package me.shedaniel.cloth.callbacks.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Screen;
import net.minecraft.util.ActionResult;

public interface ScreenKeyTypedCallback {
    
    ActionResult charTyped(MinecraftClient client, Screen screen, char character, int keyCode);
    
}
