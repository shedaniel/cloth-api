package me.shedaniel.cloth.api;

import me.shedaniel.cloth.impl.ClientUtilsImpl;

@Deprecated
public interface ClientUtils {
    
    static ClientUtils getInstance() {
        return ClientUtilsImpl.getInstance();
    }
    
    double getMouseX();
    
    double getMouseY();
    
}
