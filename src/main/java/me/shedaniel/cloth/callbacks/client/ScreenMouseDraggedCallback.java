package me.shedaniel.cloth.callbacks.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ActionResult;

public interface ScreenMouseDraggedCallback {
    
    ActionResult mouseDragged(MinecraftClient client, Screen screen, double mouseX1, double mouseY1, int button, double mouseX2, double mouseY2);
    
}
