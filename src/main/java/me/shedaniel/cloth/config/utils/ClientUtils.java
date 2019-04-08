package me.shedaniel.cloth.config.utils;

import net.minecraft.client.MinecraftClient;

public class ClientUtils implements me.shedaniel.cloth.config.api.ClientUtils {
    
    private static ClientUtils instance;
    private MinecraftClient client;
    
    public ClientUtils() {
        ClientUtils.instance = this;
        this.client = MinecraftClient.getInstance();
    }
    
    @Deprecated
    public static ClientUtils getInstance() {
        return instance;
    }
    
    @Override
    public double getMouseX() {
        return this.client.mouse.getX() * (double) this.client.window.getScaledWidth() / (double) this.client.window.getWidth();
    }
    
    @Override
    public double getMouseY() {
        return this.client.mouse.getY() * (double) this.client.window.getScaledWidth() / (double) this.client.window.getWidth();
    }
    
}
