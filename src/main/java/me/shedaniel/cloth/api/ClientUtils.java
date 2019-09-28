package me.shedaniel.cloth.api;

import java.awt.*;

@Deprecated
public interface ClientUtils {
    
    static ClientUtils getInstance() {
        return new ClientUtils() {
            @Override
            public double getMouseX() {
                return getMouseLocation().x + 0d;
            }
            
            @Override
            public double getMouseY() {
                return getMouseLocation().y + 0d;
            }
        };
    }
    
    static Point getMouseLocation() {
        return new Point((int) getInstance().getMouseX(), (int) getInstance().getMouseY());
    }
    
    double getMouseX();
    
    double getMouseY();
    
}
