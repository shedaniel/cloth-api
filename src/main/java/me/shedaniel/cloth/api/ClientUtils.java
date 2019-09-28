package me.shedaniel.cloth.api;

import me.shedaniel.cloth.impl.ClientUtilsImpl;

import java.awt.*;

@Deprecated
public interface ClientUtils {
    
    static ClientUtils getInstance() {
        return ClientUtilsImpl.getInstance();
    }
    
    static Point getMouseLocation() {
        return new Point((int) getInstance().getMouseX(), (int) getInstance().getMouseY());
    }
    
    double getMouseX();
    
    double getMouseY();
    
}
