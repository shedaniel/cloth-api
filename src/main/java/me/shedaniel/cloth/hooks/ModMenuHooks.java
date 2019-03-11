package me.shedaniel.cloth.hooks;

import io.github.prospector.modmenu.gui.ModListScreen;
import io.github.prospector.modmenu.gui.ModListWidget;
import me.shedaniel.cloth.api.EventPriority;
import me.shedaniel.cloth.api.ReflectionUtils;
import me.shedaniel.cloth.events.modmenu.ConfigButtonEvent;
import net.fabricmc.loader.api.ModContainer;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class ModMenuHooks {
    
    public static Runnable onButtonClicked = null;
    
    static void registerEvents() {
        AtomicReference<ModContainer> lastContainer = new AtomicReference(null);
        ClothHooks.CLIENT_PRE_DRAW_SCREEN.registerEvent(post -> {
            if (post.getScreen() instanceof ModListScreen) {
                ModListWidget list = ReflectionUtils.getField((ModListScreen) post.getScreen(), ModListWidget.class, 4).orElse(null);
                ModContainer selectedContainer = list != null && list.selected != null ? list.selected.container : null;
                if (lastContainer.get() != selectedContainer) {
                    if (selectedContainer != null)
                        for(Consumer<ConfigButtonEvent> listener : ClothModMenuHooks.CONFIG_BUTTON_EVENT.getSortedListeners()) {
                            ConfigButtonEvent event = new ConfigButtonEvent(selectedContainer, post.getButtonWidgets().get(1));
                            listener.accept(event);
                            if (event.isCancelled())
                                break;
                        }
                    lastContainer.set(selectedContainer);
                }
            }
        }, EventPriority.LOWEST);
    }
    
}
