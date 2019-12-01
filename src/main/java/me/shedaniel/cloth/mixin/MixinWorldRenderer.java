package me.shedaniel.cloth.mixin;

import me.shedaniel.cloth.hooks.ClothClientHooks;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.Matrix4f;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer {
    
    @Inject(method = "render", at = @At(value = "INVOKE",
                                        target = "Lnet/minecraft/client/render/debug/DebugRenderer;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;DDD)V",
                                        ordinal = 0))
    public void renderCenter(MatrixStack matrixStack_1, float float_1, long long_1, boolean boolean_1, Camera camera_1, GameRenderer gameRenderer_1, LightmapTextureManager lightmapTextureManager_1, Matrix4f matrix4f_1, CallbackInfo callbackInfo) {
        ClothClientHooks.DEBUG_RENDER_PRE.invoker().run();
    }
    
}
