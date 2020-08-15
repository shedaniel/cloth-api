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

package me.shedaniel.cloth.mixin.dynamic.registry;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import me.shedaniel.cloth.api.dynamic.registry.v1.DynamicRegistryCallback;
import me.shedaniel.cloth.api.dynamic.registry.v1.EarlyInitializer;
import me.shedaniel.cloth.impl.dynamic.registry.DynamicRegistryCallbackManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.RegistryOps;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

@Mixin(DynamicRegistryManager.class)
public abstract class MixinDynamicRegistryManager {
    static {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
            FabricLoader.getInstance().getEntrypoints("cloth-early-init", EarlyInitializer.class).forEach(EarlyInitializer::onEarlyInitialization);
        }
    }
    
    @Shadow @Final private static Map<RegistryKey<? extends Registry<?>>, DynamicRegistryManager.Info<?>> INFOS;
    
    @Shadow
    public abstract <E> MutableRegistry<E> get(RegistryKey<? extends Registry<E>> key);
    
    @Inject(method = "create", at = @At("RETURN"))
    private static void create(CallbackInfoReturnable<DynamicRegistryManager.Impl> cir) {
        for (RegistryKey<? extends Registry<?>> registryKey : INFOS.keySet()) {
            acceptEvents((RegistryKey) registryKey, cir.getReturnValue(), key -> true);
        }
    }
    
    @Unique
    private static <E> void acceptEvents(RegistryKey<Registry<E>> registryKey, DynamicRegistryManager manager, Predicate<RegistryKey<E>> predicate) {
        manager.getOptional(registryKey).ifPresent(registry -> {
            Event<DynamicRegistryCallback<E>> event = DynamicRegistryCallbackManager.eventNullable(registryKey);
            if (event != null) {
                DynamicRegistryCallback<E> invoker = event.invoker();
                for (Map.Entry<RegistryKey<E>, E> entry : registry.getEntries()) {
                    if (predicate.test(entry.getKey()))
                        invoker.accept(manager, entry.getKey(), entry.getValue());
                }
            }
        });
    }
    
    @Unique Map<Identifier, Set<Identifier>> storedLoaded;
    
    @Inject(method = "load(Lnet/minecraft/util/registry/DynamicRegistryManager$Impl;Lnet/minecraft/util/dynamic/RegistryOps;)V", at = @At("HEAD"))
    private static void beforeLoad(DynamicRegistryManager.Impl manager, RegistryOps<?> registryOps, CallbackInfo ci) {
        Map<Identifier, Set<Identifier>> map = Maps.newHashMap();
        ((MixinDynamicRegistryManager) (Object) manager).storedLoaded = map;
        for (RegistryKey<? extends Registry<?>> registryKey : INFOS.keySet()) {
            store((RegistryKey) registryKey, manager, map);
        }
    }
    
    @Unique
    private static <E> void store(RegistryKey<Registry<E>> registryKey, DynamicRegistryManager manager, Map<Identifier, Set<Identifier>> map) {
        manager.getOptional(registryKey).ifPresent(registry -> {
            map.put(registryKey.getValue(), Sets.newHashSet(registry.getIds()));
        });
    }
    
    @Inject(method = "load(Lnet/minecraft/util/registry/DynamicRegistryManager$Impl;Lnet/minecraft/util/dynamic/RegistryOps;)V", at = @At("RETURN"))
    private static void afterLoad(DynamicRegistryManager.Impl manager, RegistryOps<?> registryOps, CallbackInfo ci) {
        Map<Identifier, Set<Identifier>> storedLoaded = ((MixinDynamicRegistryManager) (Object) manager).storedLoaded;
        for (RegistryKey<? extends Registry<?>> registryKey : INFOS.keySet()) {
            afterLoad((RegistryKey) registryKey, manager, storedLoaded);
        }
        
        ((MixinDynamicRegistryManager) (Object) manager).storedLoaded = null;
    }
    
    @Unique
    private static <E> void afterLoad(RegistryKey<Registry<E>> registryKey, DynamicRegistryManager manager, Map<Identifier, Set<Identifier>> map) {
        Set<Identifier> identifiers = map.getOrDefault(registryKey.getValue(), Collections.emptySet());
        acceptEvents(registryKey, manager, valueKey -> !identifiers.contains(valueKey.getValue()));
    }
}
