package me.shedaniel.cloth.gui;

import net.minecraft.client.gui.widget.ButtonWidget;

public class ClothConfigTabButton extends ButtonWidget {
    private boolean privateHovered;
    private int index = -1;
    private ClothConfigScreen screen;
    
    public ClothConfigTabButton(ClothConfigScreen screen, int index, int int_1, int int_2, int int_3, int int_4, String string_1) {
        super(int_1, int_2, int_3, int_4, string_1, widget -> {
            if (index != -1)
                screen.nextTabIndex = index;
            screen.onInitialized();
        });
        this.index = index;
        this.screen = screen;
    }
    
    @Override
    public boolean isPartOfFocusCycle() {
        return false;
    }
    
    @Override
    public void render(int int_1, int int_2, float float_1) {
        active = index != screen.selectedTabIndex;
        privateHovered = isMouseOver(int_1, int_2);
        super.render(int_1, int_2, float_1);
    }
    
    @Override
    public boolean isHovered() {
        return this.privateHovered;
    }
    
    @Override
    public boolean isMouseOver(double double_1, double double_2) {
        return this.active && this.visible && double_1 >= this.x && double_2 >= this.y && double_1 < this.x + this.width && double_2 < this.y + this.height && double_1 >= 20 && double_1 < screen.screenWidth - 20;
    }
}
