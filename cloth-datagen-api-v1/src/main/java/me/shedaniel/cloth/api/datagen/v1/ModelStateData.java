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

import com.google.gson.JsonElement;
import net.minecraft.block.Block;
import net.minecraft.data.client.model.*;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public interface ModelStateData extends ModelData, BlockStateDefinitionData {
    default void addBlockModel(Block block, Supplier<JsonElement> element) {
        Identifier blockId = Registry.BLOCK.getId(block);
        addModel(new Identifier(blockId.getNamespace(), "block/" + blockId.getPath()), element);
    }
    
    default void addBlockModel(Block block, JsonElement element) {
        addBlockModel(block, () -> element);
    }
    
    default void addItemModel(Item item, Supplier<JsonElement> element) {
        Identifier itemId = Registry.ITEM.getId(item);
        addModel(new Identifier(itemId.getNamespace(), "item/" + itemId.getPath()), element);
    }
    
    default void addItemModel(Item item, JsonElement element) {
        addItemModel(item, () -> element);
    }
    
    default void addSimpleBlockItemModel(Block block) {
        Item item = Item.BLOCK_ITEMS.get(block);
        Identifier identifier = ModelIds.getItemModelId(item);
        addSimpleItemModel(identifier, ModelIds.getBlockModelId(block));
    }
    
    default void addSimpleBlockModel(Block block, Identifier parent) {
        addBlockModel(block, new SimpleModelSupplier(parent));
    }
    
    default void addSimpleItemModel(Item item, Identifier parent) {
        addItemModel(item, new SimpleModelSupplier(parent));
    }
    
    default void addSimpleBlockModel(Identifier block, Identifier parent) {
        addModel(block, new SimpleModelSupplier(parent));
    }
    
    default void addSimpleItemModel(Identifier item, Identifier parent) {
        addModel(item, new SimpleModelSupplier(parent));
    }
    
    default void addItemModel(Item item, Model model) {
        model.upload(ModelIds.getItemModelId(item), Texture.layer0(item), this::addModel);
    }
    
    default void addGeneratedItemModel(Item item) {
        addItemModel(item, Models.GENERATED);
    }
    
    default void addHandheldItemModel(Item item) {
        addItemModel(item, Models.HANDHELD);
    }
    
    default void addItemModel(Item item, String suffix, Model model) {
        model.upload(ModelIds.getItemSubModelId(item, suffix), Texture.layer0(item), this::addModel);
    }
    
    default void addItemModel(Item item, Item texture, Model model) {
        model.upload(ModelIds.getItemModelId(item), Texture.layer0(texture), this::addModel);
    }
    
    default void addSingletonCubeAll(Block block) {
        addSingletonBlock(block, TexturedModel.CUBE_ALL);
    }
    
    default void addSingletonBlock(Block block, TexturedModel.Factory factory) {
        addState(block, createSingletonBlockState(block, factory.upload(block, this::addModel)));
    }
    
    static VariantsBlockStateSupplier createSingletonBlockState(Block block, Identifier modelId) {
        return createBlockState(block, BlockStateVariant.create().put(VariantSettings.MODEL, modelId));
    }
    
    static VariantsBlockStateSupplier createBlockState(Block block, BlockStateVariant variant) {
        return VariantsBlockStateSupplier.create(block, variant);
    }
}
