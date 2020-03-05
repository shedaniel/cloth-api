package me.shedaniel.cloth.mixin;

import me.shedaniel.cloth.hooks.ClothClientHooks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.SynchronizeRecipesS2CPacket;
import net.minecraft.recipe.RecipeManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {
    
    @Shadow private MinecraftClient client;
    
    @Shadow @Final private RecipeManager recipeManager;
    
    @Inject(method = "onSynchronizeRecipes", at = @At("RETURN"))
    private void onUpdateRecipes(SynchronizeRecipesS2CPacket packet, CallbackInfo ci) {
        ClothClientHooks.SYNC_RECIPES.invoker().syncRecipes(client, recipeManager, packet);
    }
    
}
