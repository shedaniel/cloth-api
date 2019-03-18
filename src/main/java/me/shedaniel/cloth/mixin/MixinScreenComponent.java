package me.shedaniel.cloth.mixin;

import me.shedaniel.cloth.events.ClientScreenGetFocusedEvent;
import me.shedaniel.cloth.hooks.ClothHooks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.InputListener;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.ScreenComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(ScreenComponent.class)
public class MixinScreenComponent {
    
    @Inject(method = "getFocused", at = @At("RETURN"), cancellable = true)
    public void onGetFocused(CallbackInfoReturnable<InputListener> info) {
        if (((ScreenComponent) (Object) this) instanceof Screen && !info.isCancelled())
            for(Consumer<ClientScreenGetFocusedEvent> sortedListener : ClothHooks.CLIENT_SCREEN_GET_FOCUSED.getSortedListeners()) {
                ClientScreenGetFocusedEvent event;
                sortedListener.accept(event = new ClientScreenGetFocusedEvent(MinecraftClient.getInstance(), (Screen) (Object) this, info.getReturnValue()));
                if (event.isCancelled()) {
                    info.setReturnValue(event.getReturningValue());
                    return;
                }
            }
    }
    
}
