package me.shedaniel.cloth.mixin;

import me.shedaniel.cloth.api.ClientUtils;
import me.shedaniel.cloth.hooks.ClothClientHooks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.resource.SynchronousResourceReloadListener;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer implements AutoCloseable, SynchronousResourceReloadListener {
    
    @Shadow @Final private MinecraftClient client;
    
    @Inject(method = "render(FJZ)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;render(IIF)V",
                     shift = At.Shift.AFTER, ordinal = 0))
    public void renderScreen(float float_1, long long_1, boolean boolean_1, CallbackInfo ci) {
        ClothClientHooks.SCREEN_LATE_RENDER.invoker().render(client, client.currentScreen, (int) ClientUtils.getInstance().getMouseX(), (int) ClientUtils.getInstance().getMouseY(), client.getLastFrameDuration());
    }
    
    @Inject(method = "renderCenter",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;debugRenderer:Lnet/minecraft/client/render/debug/DebugRenderer;",
                     ordinal = 0))
    public void renderCenter(float delta, long long_1, CallbackInfo callbackInfo) {
        ClothClientHooks.DEBUG_RENDER_PRE.invoker().run();
    }
    
}