package me.shedaniel.cloth.config.api;

import java.awt.*;

public interface ClientUtils {
    
    @SuppressWarnings("deprecated")
    static ClientUtils getInstance() {
        return me.shedaniel.cloth.config.utils.ClientUtils.getInstance();
    }
    
    static Point getMouseLocation() {
        return new Point((int) getInstance().getMouseX(), (int) getInstance().getMouseY());
    }
    
    double getMouseX();
    
    double getMouseY();
    
}
