package me.shedaniel.cloth.mixin;

import me.shedaniel.cloth.events.client.ScreenGetFocusedEvent;
import me.shedaniel.cloth.hooks.ClothClientHooks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(ScreenComponent.class)
public abstract class MixinScreenComponent extends DrawableHelper implements MultiInputListener {
    
    @Inject(method = "getFocused", at = @At("RETURN"), cancellable = true)
    public void onGetFocused(CallbackInfoReturnable<InputListener> info) {
        if (((ScreenComponent) (Object) this) instanceof Screen && !info.isCancelled())
            for(Consumer<ScreenGetFocusedEvent> sortedListener : ClothClientHooks.SCREEN_GET_FOCUSED.getSortedListeners()) {
                ScreenGetFocusedEvent event;
                sortedListener.accept(event = new ScreenGetFocusedEvent(MinecraftClient.getInstance(), (Screen) (Object) this, info.getReturnValue()));
                if (event.isCancelled()) {
                    info.setReturnValue(event.getReturningValue());
                    return;
                }
            }
    }
    
}
