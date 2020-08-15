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

package me.shedaniel.cloth.impl.dynamic.registry;

import com.google.common.collect.Maps;
import me.shedaniel.cloth.api.dynamic.registry.v1.DynamicRegistryCallback;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import java.util.Map;

public final class DynamicRegistryCallbackManager {
    private static final Map<RegistryKey<? extends Registry<?>>, Event<? extends DynamicRegistryCallback<?>>> EVENT_MAP = Maps.newHashMap();
    
    public static <T> Event<DynamicRegistryCallback<T>> event(RegistryKey<Registry<T>> registryKey) {
        return (Event<DynamicRegistryCallback<T>>) EVENT_MAP.computeIfAbsent(registryKey, key -> EventFactory.createArrayBacked(DynamicRegistryCallback.class, callbacks -> (manager, id, value) -> {
            for (DynamicRegistryCallback<?> callback : callbacks) {
                ((DynamicRegistryCallback<Object>) callback).accept(manager, id, value);
            }
        }));
    }
    
    public static <T> Event<DynamicRegistryCallback<T>> eventNullable(RegistryKey<Registry<T>> registryKey) {
        return (Event<DynamicRegistryCallback<T>>) EVENT_MAP.get(registryKey);
    }
}
