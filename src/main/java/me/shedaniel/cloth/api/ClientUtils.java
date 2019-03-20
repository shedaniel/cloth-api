package me.shedaniel.cloth.api;

import java.awt.*;

public interface ClientUtils {
    
    static ClientUtils getInstance() {
        return me.shedaniel.cloth.utils.ClientUtils.getInstance();
    }
    
    static Point getMouseLocation() {
        return new Point((int) getInstance().getMouseX(), (int) getInstance().getMouseY());
    }
    
    double getMouseX();
    
    double getMouseY();
    
}
