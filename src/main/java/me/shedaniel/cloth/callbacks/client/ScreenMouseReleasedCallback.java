package me.shedaniel.cloth.callbacks.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Screen;
import net.minecraft.util.ActionResult;

public interface ScreenMouseReleasedCallback {
    
    ActionResult mouseReleased(MinecraftClient client, Screen screen, double mouseX, double mouseY, int button);
    
}
