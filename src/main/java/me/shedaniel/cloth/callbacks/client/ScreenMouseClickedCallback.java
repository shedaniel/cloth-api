package me.shedaniel.cloth.callbacks.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ActionResult;

public interface ScreenMouseClickedCallback {
    
    ActionResult mouseClicked(MinecraftClient client, Screen screen, double mouseX, double mouseY, int button);
    
}
