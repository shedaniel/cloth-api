package me.shedaniel.clothconfig.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.ParentElement;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public abstract class DynamicElementListWidget<E extends DynamicElementListWidget.ElementItem<E>> extends DynamicSmoothScrollingItemListWidget<E> {
    
    public DynamicElementListWidget(MinecraftClient client, int width, int height, int top, int bottom, Identifier backgroundLocation) {
        super(client, width, height, top, bottom, backgroundLocation);
    }
    
    public boolean changeFocus(boolean boolean_1) {
        boolean boolean_2 = super.changeFocus(boolean_1);
        if (boolean_2)
            this.ensureVisible(this.getFocused());
        return boolean_2;
    }
    
    protected boolean isSelected(int int_1) {
        return false;
    }
    
    @Environment(EnvType.CLIENT)
    public abstract static class ElementItem<E extends DynamicElementListWidget.ElementItem<E>> extends Item<E> implements ParentElement {
        private Element focused;
        private boolean dragging;
        
        public ElementItem() {
        }
        
        public boolean isDragging() {
            return this.dragging;
        }
        
        public void setDragging(boolean boolean_1) {
            this.dragging = boolean_1;
        }
        
        public Element getFocused() {
            return this.focused;
        }
        
        public void setFocused(Element element_1) {
            this.focused = element_1;
        }
    }
}

