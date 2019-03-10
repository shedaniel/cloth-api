package me.shedaniel.cloth.api;

import net.minecraft.client.gui.InputListener;
import net.minecraft.client.gui.widget.ButtonWidget;

import java.util.List;

public interface ScreenHooks {
    
    List<ButtonWidget> getButtonWidgets();
    
    List<InputListener> getInputs();
    
}
