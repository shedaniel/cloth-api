package me.shedaniel.cloth.hooks;

import net.minecraft.client.gui.InputListener;
import net.minecraft.client.gui.widget.AbstractButtonWidget;

import java.util.List;

public interface ScreenHooks {
    
    List<AbstractButtonWidget> cloth_getButtonWidgets();
    
    List<InputListener> cloth_getInputListeners();
    
}
