package me.shedaniel.cloth.callbacks.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Screen;
import net.minecraft.util.ActionResult;

public interface ScreenMouseScrolledCallback {
    
    ActionResult mouseScrolled(MinecraftClient client, Screen screen, double mouseX, double mouseY, double amount);
    
}
