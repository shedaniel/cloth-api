package me.shedaniel.cloth.events.modmenu;

import me.shedaniel.cloth.api.CancellableEvent;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.gui.widget.AbstractButtonWidget;

// To be removed in next snapshot
public class ConfigButtonEvent implements CancellableEvent {
    
    private ModContainer modContainer;
    private boolean cancelled;
    private AbstractButtonWidget widget;
    
    public ConfigButtonEvent(ModContainer modContainer, AbstractButtonWidget widget) {
        this.modContainer = modContainer;
        this.widget = new AbstractButtonWidget(0, 0, "") {};
        this.widget.enabled = false;
        this.cancelled = false;
    }
    
    public void setClickedRunnable(Runnable r) {
    
    }
    
    public String getButtonText() {
        return widget.getText();
    }
    
    public void setButtonText(String text) {
        widget.setText(text);
    }
    
    public void setButtonOpacity(float level) {
        widget.setOpacity(level);
    }
    
    public ModContainer getModContainer() {
        return modContainer;
    }
    
    public String getModId() {
        return modContainer != null ? modContainer.getMetadata().getId() : "";
    }
    
    @Override
    public boolean isCancelled() {
        return cancelled;
    }
    
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    public boolean isEnabled() {
        return widget.enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.widget.enabled = enabled;
    }
    
}
