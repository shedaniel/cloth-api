package me.shedaniel.cloth.utils;

import net.minecraft.client.MinecraftClient;

import java.awt.*;

public class ClientUtils implements me.shedaniel.cloth.api.ClientUtils {
    
    private MinecraftClient client;
    
    public ClientUtils() {
        this.client = MinecraftClient.getInstance();
    }
    
    @Override
    public double getMouseX() {
        return this.client.mouse.getX() * (double) this.client.window.getScaledWidth() / (double) this.client.window.getWidth();
    }
    
    @Override
    public double getMouseY() {
        return this.client.mouse.getY() * (double) this.client.window.getScaledWidth() / (double) this.client.window.getWidth();
    }
    
    @Override
    public Point getMouseLocation() {
        return new Point((int) getMouseX(), (int) getMouseY());
    }
    
}
