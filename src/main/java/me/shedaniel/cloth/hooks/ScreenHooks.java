package me.shedaniel.cloth.hooks;

import net.minecraft.client.gui.InputListener;
import net.minecraft.client.gui.widget.ButtonWidget;

import java.util.List;

public interface ScreenHooks {
    
    List<ButtonWidget> cloth_getButtonWidgets();
    
    List<InputListener> cloth_getInputListeners();
    
}
