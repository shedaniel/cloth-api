package me.shedaniel.cloth.events.modmenu;

import me.shedaniel.cloth.api.Cancelable;
import me.shedaniel.cloth.api.CancelableEvent;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.gui.widget.ButtonWidget;

public class ConfigButtonEvent implements CancelableEvent {
    
    private ModContainer modContainer;
    private Cancelable cancelable;
    private ButtonWidget widget;
    
    public ConfigButtonEvent(ModContainer modContainer, ButtonWidget widget) {
        this.modContainer = modContainer;
        this.widget = widget;
        this.widget.enabled = false;
        this.cancelable = new Cancelable();
    }
    
    public void setClickedRunnable(Runnable r) {
        try {
            Class.forName("me.shedaniel.cloth.hooks.ModMenuHooks").getDeclaredField("onButtonClicked").set(null, r);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
    
    @Override
    public boolean isCancelled() {
        return cancelable.isCancelled();
    }
    
    @Override
    public void setCancelled(boolean cancelled) {
        cancelable.setCancelled(cancelled);
    }
    
    public boolean isEnabled() {
        return widget.enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.widget.enabled = enabled;
    }
    
}
