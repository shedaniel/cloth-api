package me.shedaniel.cloth.mixin;

import me.shedaniel.cloth.api.ClientUtils;
import me.shedaniel.cloth.events.client.ScreenMouseClickedEvent;
import me.shedaniel.cloth.events.client.ScreenMouseReleasedEvent;
import me.shedaniel.cloth.events.client.ScreenMouseScrolledEvent;
import me.shedaniel.cloth.hooks.ClothClientHooks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(Mouse.class)
public class MixinMouse {
    
    @Shadow
    @Final
    private MinecraftClient client;
    
    @Shadow
    private int activeButton;
    
    @Inject(method = "onMouseScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Screen;mouseScrolled(DDD)Z", ordinal = 0), cancellable = true)
    public void onMouseScrolled(long long_1, double double_1, double double_2, CallbackInfo info) {
        double x = ClientUtils.getInstance().getMouseX(), y = ClientUtils.getInstance().getMouseY();
        double amount = double_2 * this.client.options.mouseWheelSensitivity;
        if (!info.isCancelled())
            for(Consumer<ScreenMouseScrolledEvent> sortedListener : ClothClientHooks.SCREEN_MOUSE_SCROLLED.getSortedListeners()) {
                ScreenMouseScrolledEvent event;
                sortedListener.accept(event = new ScreenMouseScrolledEvent(client, client.currentScreen, x, y, amount));
                if (event.isCancelled()) {
                    info.cancel();
                    return;
                }
            }
    }
    
    @Inject(method = "onMouseButton", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Screen;method_2217(Ljava/lang/Runnable;Ljava/lang/String;Ljava/lang/String;)V", ordinal = 0), cancellable = true)
    public void onMouseClicked(long long_1, int int_1, int int_2, int int_3, CallbackInfo info) {
        double x = ClientUtils.getInstance().getMouseX(), y = ClientUtils.getInstance().getMouseY();
        if (!info.isCancelled())
            for(Consumer<ScreenMouseClickedEvent> sortedListener : ClothClientHooks.SCREEN_MOUSE_CLICKED.getSortedListeners()) {
                ScreenMouseClickedEvent event;
                sortedListener.accept(event = new ScreenMouseClickedEvent(client, client.currentScreen, x, y, activeButton));
                if (event.isCancelled()) {
                    info.cancel();
                    return;
                }
            }
    }
    
    @Inject(method = "onMouseButton", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Screen;method_2217(Ljava/lang/Runnable;Ljava/lang/String;Ljava/lang/String;)V", ordinal = 1), cancellable = true)
    public void onMouseReleased(long long_1, int int_1, int int_2, int int_3, CallbackInfo info) {
        double x = ClientUtils.getInstance().getMouseX(), y = ClientUtils.getInstance().getMouseY();
        if (!info.isCancelled())
            for(Consumer<ScreenMouseReleasedEvent> sortedListener : ClothClientHooks.SCREEN_MOUSE_RELEASED.getSortedListeners()) {
                ScreenMouseReleasedEvent event;
                sortedListener.accept(event = new ScreenMouseReleasedEvent(client, client.currentScreen, x, y, activeButton));
                if (event.isCancelled()) {
                    info.cancel();
                    return;
                }
            }
    }
    
}
