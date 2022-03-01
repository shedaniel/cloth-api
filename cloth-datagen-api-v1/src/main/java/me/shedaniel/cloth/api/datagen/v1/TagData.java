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

package me.shedaniel.cloth.api.datagen.v1;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public interface TagData {
    <T> TagBuilder<T> get(RegistryKey<? extends Registry<T>> registryKey, Identifier tag);
    
    default <T> TagBuilder<T> get(TagKey<T> tag) {
        return get(tag.registry(), tag.id());
    }
    
    default TagBuilder<Block> block(Identifier tag) {
        return get(Registry.BLOCK_KEY, tag);
    }
    
    default TagBuilder<Item> item(Identifier tag) {
        return get(Registry.ITEM_KEY, tag);
    }
    
    default TagBuilder<EntityType<?>> entity(Identifier tag) {
        return get(Registry.ENTITY_TYPE_KEY, tag);
    }
    
    default TagBuilder<Fluid> fluid(Identifier tag) {
        return get(Registry.FLUID_KEY, tag);
    }
    
    default TagBuilder<Block> block(TagKey<Block> tag) {
        return get(tag);
    }
    
    default TagBuilder<Item> item(TagKey<Item> tag) {
        return get(tag);
    }
    
    default TagBuilder<EntityType<?>> entity(TagKey<EntityType<?>> tag) {
        return get(tag);
    }
    
    default TagBuilder<Fluid> fluid(TagKey<Fluid> tag) {
        return get(tag);
    }
    
    interface TagBuilder<T> {
        Identifier getId();
        
        TagBuilder<T> append(boolean optional, Identifier value);
        
        TagBuilder<T> append(boolean optional, T value);
        
        TagBuilder<T> appendTag(boolean optional, Identifier tag);
        
        default TagBuilder<T> append(boolean optional, T... values) {
            for (T value : values) {
                append(optional, value);
            }
            
            return this;
        }
        
        default TagBuilder<T> appendTag(boolean optional, TagKey<T> tag) {
            return appendTag(optional, tag.id());
        }
        
        default TagBuilder<T> appendTag(boolean optional, TagBuilder<T> tag) {
            return appendTag(optional, tag.getId());
        }
        
        default TagBuilder<T> append(Identifier value) {
            return append(false, value);
        }
        
        default TagBuilder<T> append(T value) {
            return append(false, value);
        }
        
        default TagBuilder<T> append(T... values) {
            return append(false, values);
        }
        
        default TagBuilder<T> appendTag(Identifier tag) {
            return appendTag(false, tag);
        }
        
        default TagBuilder<T> appendTag(TagKey<T> tag) {
            return appendTag(tag.id());
        }
        
        default TagBuilder<T> appendTag(TagBuilder<T> tag) {
            return appendTag(tag.getId());
        }
        
        default TagBuilder<T> appendOptional(Identifier value) {
            return append(true, value);
        }
        
        default TagBuilder<T> appendOptional(T value) {
            return append(true, value);
        }
        
        default TagBuilder<T> appendOptional(T... values) {
            return append(true, values);
        }
        
        default TagBuilder<T> appendOptionalTag(Identifier tag) {
            return appendTag(true, tag);
        }
        
        default TagBuilder<T> appendOptionalTag(TagKey<T> tag) {
            return appendOptionalTag(tag.id());
        }
        
        default TagBuilder<T> appendOptionalTag(TagBuilder<T> tag) {
            return appendOptionalTag(tag.getId());
        }
    }
}
