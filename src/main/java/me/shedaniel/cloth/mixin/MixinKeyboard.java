package me.shedaniel.cloth.mixin;

import me.shedaniel.cloth.events.client.ScreenKeyTypedEvent;
import me.shedaniel.cloth.hooks.ClothClientHooks;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(Keyboard.class)
public class MixinKeyboard {
    
    @Shadow
    @Final
    private MinecraftClient client;
    
    @Inject(method = "onChar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Screen;method_2217(Ljava/lang/Runnable;Ljava/lang/String;Ljava/lang/String;)V", ordinal = 0), cancellable = true)
    public void onCharFirst(long long_1, int int_1, int int_2, CallbackInfo info) {
        if (!info.isCancelled())
            for(Consumer<ScreenKeyTypedEvent> sortedListener : ClothClientHooks.SCREEN_KEY_TYPED.getSortedListeners()) {
                ScreenKeyTypedEvent event;
                sortedListener.accept(event = new ScreenKeyTypedEvent(client, client.currentScreen, (char) int_1, int_2));
                if (event.isCancelled()) {
                    info.cancel();
                    return;
                }
            }
    }
    
    @Inject(method = "onChar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Screen;method_2217(Ljava/lang/Runnable;Ljava/lang/String;Ljava/lang/String;)V", ordinal = 1), cancellable = true)
    public void onCharSecond(long long_1, int int_1, int int_2, CallbackInfo info) {
        if (!info.isCancelled())
            for(Consumer<ScreenKeyTypedEvent> sortedListener : ClothClientHooks.SCREEN_KEY_TYPED.getSortedListeners()) {
                ScreenKeyTypedEvent event;
                sortedListener.accept(event = new ScreenKeyTypedEvent(client, client.currentScreen, (char) int_1, int_2));
                if (event.isCancelled()) {
                    info.cancel();
                    return;
                }
            }
    }
    
}
