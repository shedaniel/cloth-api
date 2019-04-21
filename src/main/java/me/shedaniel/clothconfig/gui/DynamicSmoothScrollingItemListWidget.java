package me.shedaniel.clothconfig.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public abstract class DynamicSmoothScrollingItemListWidget<E extends DynamicItemListWidget.Item<E>> extends DynamicItemListWidget<E> {
    
    protected double scrollVelocity;
    protected boolean smoothScrolling = true;
    
    public DynamicSmoothScrollingItemListWidget(MinecraftClient client, int width, int height, int top, int bottom, Identifier backgroundLocation) {
        super(client, width, height, top, bottom, backgroundLocation);
    }
    
    public double getScrollVelocity() {
        return scrollVelocity;
    }
    
    public void setScrollVelocity(double scrollVelocity) {
        this.scrollVelocity = scrollVelocity;
    }
    
    public boolean isSmoothScrolling() {
        return smoothScrolling;
    }
    
    public void setSmoothScrolling(boolean smoothScrolling) {
        this.smoothScrolling = smoothScrolling;
    }
    
    @Override
    protected void scroll(int int_1) {
        super.scroll(int_1);
        this.scrollVelocity = 0d;
    }
    
    @Override
    public boolean mouseScrolled(double double_1, double double_2, double double_3) {
        if (double_3 < 0)
            scrollVelocity += 16;
        if (double_3 > 0)
            scrollVelocity -= 16;
        return true;
    }
    
    @Override
    public void render(int mouseX, int mouseY, float delta) {
        if (smoothScrolling) {
            double change = scrollVelocity * 0.2f;
            if (change != 0) {
                if (change > 0 && change < .2)
                    change = .2;
                else if (change < 0 && change > -.2)
                    change = -.2;
                scroll += change;
                scrollVelocity -= change;
                if (change > 0 == scrollVelocity < 0)
                    scrollVelocity = 0d;
                capYPosition(scroll);
            }
        } else {
            scroll += scrollVelocity;
            scrollVelocity = 0d;
            capYPosition(scroll);
        }
        super.render(mouseX, mouseY, delta);
    }
    
}
