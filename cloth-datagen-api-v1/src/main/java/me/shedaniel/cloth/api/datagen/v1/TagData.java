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
import net.minecraft.item.ItemConvertible;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public interface TagData {
    TagBuilder<Block> block(Identifier tag);
    
    TagBuilder<ItemConvertible> item(Identifier tag);
    
    TagBuilder<EntityType<?>> entity(Identifier tag);
    
    TagBuilder<Fluid> fluid(Identifier tag);
    
    default TagBuilder<Block> block(Tag.Identified<Block> tag) {
        return block(tag.getId());
    }
    
    default TagBuilder<ItemConvertible> item(Tag.Identified<Item> tag) {
        return item(tag.getId());
    }
    
    default TagBuilder<EntityType<?>> entity(Tag.Identified<EntityType<?>> tag) {
        return entity(tag.getId());
    }
    
    default TagBuilder<Fluid> fluid(Tag.Identified<Fluid> tag) {
        return fluid(tag.getId());
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
        
        default TagBuilder<T> appendTag(boolean optional, Tag.Identified tag) {
            return appendTag(optional, tag.getId());
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
        
        default TagBuilder<T> appendTag(Tag.Identified tag) {
            return appendTag(tag.getId());
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
        
        default TagBuilder<T> appendOptionalTag(Tag.Identified tag) {
            return appendOptionalTag(tag.getId());
        }
        
        default TagBuilder<T> appendOptionalTag(TagBuilder<T> tag) {
            return appendOptionalTag(tag.getId());
        }
    }
}
