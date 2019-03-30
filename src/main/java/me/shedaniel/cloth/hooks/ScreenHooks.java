package me.shedaniel.cloth.hooks;

import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.AbstractButtonWidget;

import java.util.List;

public interface ScreenHooks {
    
    List<AbstractButtonWidget> cloth_getButtonWidgets();
    
    List<Element> cloth_getInputListeners();
    
    AbstractButtonWidget cloth_addButton(AbstractButtonWidget buttonWidget);
    
}
