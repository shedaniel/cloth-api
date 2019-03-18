package me.shedaniel.cloth.api;

import me.shedaniel.cloth.ClothInitializer;

import java.awt.*;

public interface ClientUtils {
    
    static ClientUtils getInstance() {
        return ClothInitializer.clientUtils;
    }
    
    double getMouseX();
    
    double getMouseY();
    
    Point getMouseLocation();
    
}
