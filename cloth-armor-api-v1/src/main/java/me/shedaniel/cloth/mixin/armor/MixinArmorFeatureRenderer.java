/*
 * This is free and unencumbered software released into the public domain.
 *
 * Anyone is free to copy, modify, publish, use, compile, sell, or
 * distribute this software, either in source code form or as a compiled
 * binary, for any purpose, commercial or non-commercial, and by any
 * means.
 *
 * In jurisdictions that recognize copyright laws, the author or authors
 * of this software dedicate any and all copyright interest in the
 * software to the public domain. We make this dedication for the benefit
 * of the public at large and to the detriment of our heirs and
 * successors. We intend this dedication to be an overt act of
 * relinquishment in perpetuity of all present and future rights to this
 * software under copyright law.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information, please refer to <http://unlicense.org>
 */

package me.shedaniel.cloth.mixin.armor;

import me.shedaniel.cloth.api.armor.v1.CustomModeledArmor;
import me.shedaniel.cloth.api.armor.v1.CustomTexturedArmor;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.Map;

@Mixin(ArmorFeatureRenderer.class)
public abstract class MixinArmorFeatureRenderer extends FeatureRenderer {
    @Shadow
    @Final
    private static Map<String, Identifier> ARMOR_TEXTURE_CACHE;
    
    public MixinArmorFeatureRenderer(FeatureRendererContext context) {
        super(context);
    }
    
    @Unique
    private LivingEntity storedEntity;
    
    @Inject(method = "render", at = @At("HEAD"))
    private void storeEntity(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, LivingEntity livingEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
        this.storedEntity = livingEntity;
    }
    
    @Inject(method = "getArmor", at = @At("RETURN"), cancellable = true)
    private void getArmor(EquipmentSlot slot, CallbackInfoReturnable<BipedEntityModel<LivingEntity>> cir) {
        ItemStack stack = storedEntity.getEquippedStack(slot);
        if (stack.getItem() instanceof CustomModeledArmor) {
            BipedEntityModel<LivingEntity> model = ((CustomModeledArmor) stack.getItem()).getArmorModel(storedEntity, stack, slot, cir.getReturnValue());
            if (model != cir.getReturnValue())
                cir.setReturnValue(model);
        }
    }
    
    @Inject(method = "getArmorTexture", at = @At("HEAD"), cancellable = true)
    private void getArmorTexture(ArmorItem armorItem, boolean secondLayer, @Nullable String suffix, CallbackInfoReturnable<Identifier> cir) {
        if (armorItem instanceof CustomTexturedArmor) {
            @SuppressWarnings("deprecation")
            String model = ((CustomTexturedArmor) armorItem).getArmorTexture(armorItem.getSlotType(), armorItem, secondLayer, suffix);
            if (model != null) {
                cir.setReturnValue(ARMOR_TEXTURE_CACHE.computeIfAbsent(model, Identifier::new));
            }
        }
    }
}