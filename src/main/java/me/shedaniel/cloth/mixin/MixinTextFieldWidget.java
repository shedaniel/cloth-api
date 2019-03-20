package me.shedaniel.cloth.mixin;

import me.shedaniel.cloth.hooks.TextFieldWidgetHooks;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TextFieldWidget.class)
public class MixinTextFieldWidget implements TextFieldWidgetHooks {
    
    @Shadow
    private int y;
    
    @Shadow
    @Final
    private int width;
    
    @Override
    public void setY(int y) {
        this.y = y;
    }
    
    @Override
    public int getWidth() {
        return width;
    }
    
}
