package me.shedaniel.cloth.mixin;

import me.shedaniel.cloth.api.ClientUtils;
import me.shedaniel.cloth.events.ClientScreenMouseScrolledEvent;
import me.shedaniel.cloth.hooks.ClothHooks;
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
    
    @Inject(method = "onMouseScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Screen;mouseScrolled(DDD)Z", ordinal = 0), cancellable = true)
    public void onMouseScrolled(long long_1, double double_1, double double_2, CallbackInfo info) {
        double x = ClientUtils.getInstance().getMouseX(), y = ClientUtils.getInstance().getMouseY();
        double amount = double_2 * this.client.options.mouseWheelSensitivity;
        if (!info.isCancelled())
            for(Consumer<ClientScreenMouseScrolledEvent> sortedListener : ClothHooks.CLIENT_SCREEN_MOUSE_SCROLLED.getSortedListeners()) {
                ClientScreenMouseScrolledEvent event;
                sortedListener.accept(event = new ClientScreenMouseScrolledEvent(client, client.currentScreen, x, y, amount));
                if (event.isCancelled()) {
                    info.cancel();
                    return;
                }
            }
    }
    
}
