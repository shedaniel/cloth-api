package me.shedaniel.cloth.mixin;

import me.shedaniel.cloth.events.ClientHandleInputEvent;
import me.shedaniel.cloth.hooks.ClothHooks;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    
    @Inject(method = "handleInputEvents", at = @At("HEAD"))
    private void onHandleInputEvents(CallbackInfo ci) {
        ClothHooks.CLIENT_HANDLE_INPUT.invoke(new ClientHandleInputEvent(MinecraftClient.getInstance()));
    }
    
}
