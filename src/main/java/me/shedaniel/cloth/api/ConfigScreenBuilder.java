package me.shedaniel.cloth.api;

import javafx.util.Pair;
import me.shedaniel.cloth.gui.ClothConfigScreen;
import net.minecraft.client.gui.Screen;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface ConfigScreenBuilder {
    
    String getTitle();
    
    void setTitle(String title);
    
    Screen getParentScreen();
    
    void setParentScreen(Screen parent);
    
    Consumer<Map<String, List<Pair<String, Object>>>> getOnSave();
    
    void setOnSave(Consumer<Map<String, List<Pair<String, Object>>>> onSave);
    
    ClothConfigScreen build();
    
    List<String> getCategories();
    
    void addCategory(String category);
    
    default void addCategories(String... categories) {
        for(String category : categories)
            addCategory(category);
    }
    
    void removeCategory(String category);
    
    default void removeCategories(String... categories) {
        for(String category : categories)
            removeCategory(category);
    }
    
    boolean hasCategory(String category);
    
    void addOption(String category, String key, Object object);
    
    void addOption(String category, ClothConfigScreen.AbstractListEntry entry);
    
    List<Pair<String, Object>> getOptions(String category);
    
    void setDoesConfirmSave(boolean confirmSave);
    
    boolean doesConfirmSave();
    
    void setShouldProcessErrors(boolean processErrors);
    
    boolean shouldProcessErrors();
    
    @Deprecated
    public Map<String, List<Pair<String, Object>>> getDataMap();
    
}
