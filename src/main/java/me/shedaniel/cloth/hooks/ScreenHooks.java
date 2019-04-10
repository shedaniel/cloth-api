package me.shedaniel.cloth.hooks;

import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.text.TextComponent;

import java.util.List;

public interface ScreenHooks {
    
    List<AbstractButtonWidget> cloth_getButtonWidgets();
    
    List<Element> cloth_getInputListeners();
    
    List<Element> cloth_getChildren();
    
    AbstractButtonWidget cloth_addButton(AbstractButtonWidget buttonWidget);
    
    void cloth_setTitle(TextComponent component);
    
}
