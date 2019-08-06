package me.shedaniel.cloth.callbacks.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.util.ActionResult;

public interface ScreenAddButtonCallback {
    
    ActionResult addButton(MinecraftClient client, Screen screen, AbstractButtonWidget buttonWidget);
    
}
