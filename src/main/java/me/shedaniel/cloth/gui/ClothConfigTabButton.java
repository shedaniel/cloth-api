package me.shedaniel.cloth.gui;

import net.minecraft.client.gui.widget.ButtonWidget;

public class ClothConfigTabButton extends ButtonWidget {
    private boolean privateHovered;
    private int index = -1;
    private ClothConfigScreen screen;
    
    public ClothConfigTabButton(ClothConfigScreen screen, int index, int int_1, int int_2, int int_3, int int_4, String string_1) {
        super(int_1, int_2, int_3, int_4, string_1);
        this.index = index;
        this.screen = screen;
    }
    
    @Override
    public void onPressed() {
        if (index != -1)
            screen.nextTabIndex = index;
        screen.onInitialized();
    }
    
    @Override
    public boolean hasFocus() {
        return false;
    }
    
    @Override
    public void draw(int int_1, int int_2, float float_1) {
        enabled = index != screen.selectedTabIndex;
        privateHovered = isSelected(int_1, int_2);
        super.draw(int_1, int_2, float_1);
    }
    
    @Override
    public boolean isHovered() {
        return this.privateHovered;
    }
    
    @Override
    protected boolean isSelected(double double_1, double double_2) {
        return this.enabled && this.visible && double_1 >= this.x && double_2 >= this.y && double_1 < this.x + this.width && double_2 < this.y + this.height && double_1 >= 20 && double_1 < screen.screenWidth - 20;
    }
}
