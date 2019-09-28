package me.shedaniel.cloth.impl;

import net.minecraft.client.MinecraftClient;

public class ClientUtilsImpl implements me.shedaniel.cloth.api.ClientUtils {
    
    private static ClientUtilsImpl instance;
    private MinecraftClient client;
    
    public ClientUtilsImpl() {
        ClientUtilsImpl.instance = this;
        this.client = MinecraftClient.getInstance();
    }
    
    @Deprecated
    public static ClientUtilsImpl getInstance() {
        return instance;
    }
    
    @Override
    public double getMouseX() {
        return this.client.mouse.getX() * (double) this.client.method_22683().getScaledWidth() / (double) this.client.method_22683().getWidth();
    }
    
    @Override
    public double getMouseY() {
        return this.client.mouse.getY() * (double) this.client.method_22683().getScaledWidth() / (double) this.client.method_22683().getWidth();
    }
    
}