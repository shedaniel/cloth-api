package me.shedaniel.cloth.config.mixin;

import me.shedaniel.cloth.hooks.TextFieldWidgetHooks;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TextFieldWidget.class)
public class MixinTextFieldWidget implements TextFieldWidgetHooks {
    
    @Shadow
    private int y;
    
    @Shadow
    @Mutable
    @Final
    private int width;
    
    @Override
    public void clothconfig_setY(int y) {
        this.y = y;
    }
    
    @Override
    public int clothconfig_getWidth() {
        return width;
    }
    
    @Override
    public void clothconfig_setWidth(int width) {
        this.width = width;
    }
    
}
