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
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemConvertible;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableRange;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionConsumingBuilder;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.LootFunctionConsumingBuilder;
import net.minecraft.util.Identifier;

public interface LootTableData {
    void register(LootContextType type, Identifier identifier, LootTable.Builder lootTable);
    
    default void register(Block block, LootTable.Builder lootTable) {
        register(LootContextTypes.BLOCK, block.getLootTableId(), lootTable);
    }
    
    default void register(EntityType<?> entityType, LootTable.Builder lootTable) {
        register(LootContextTypes.ENTITY, entityType.getLootTableId(), lootTable);
    }
    
    default void registerBlockDropSelf(Block block) {
        this.registerBlockDrop(block, block);
    }
    
    default void registerBlockDropSelfRequiresSilkTouch(Block block) {
        this.registerBlockDropRequiresSilkTouch(block, block);
    }
    
    default void registerBlockDrop(Block block, ItemConvertible drop) {
        this.register(block, dropsBlock(drop));
    }
    
    default void registerBlockDropRequiresSilkTouch(Block block, ItemConvertible drop) {
        this.register(block, dropsBlockWithSilkTouch(drop));
    }
    
    static LootTable.Builder dropsBlock(ItemConvertible drop) {
        return BlockLootTableGenerator.drops(drop);
    }
    
    static LootTable.Builder dropsBlock(Block drop, LootCondition.Builder conditionBuilder, LootPoolEntry.Builder<?> child) {
        return BlockLootTableGenerator.drops(drop, conditionBuilder, child);
    }
    
    static LootTable.Builder dropsBlockWithSilkTouch(ItemConvertible drop) {
        return BlockLootTableGenerator.dropsWithSilkTouch(drop);
    }
    
    static LootTable.Builder dropsBlockWithShears(ItemConvertible drop) {
        return BlockLootTableGenerator.createForBlockNeedingShears(drop);
    }
    
    static LootTable.Builder dropsSlabs(Block drop) {
        return BlockLootTableGenerator.createForSlabs(drop);
    }
    
    static LootTable.Builder dropsSilkBlockAndNormalItem(Block block, ItemConvertible drop, LootTableRange count) {
        return BlockLootTableGenerator.createForBlockWithItemDrops(block, drop, count);
    }
    
    static LootTable.Builder dropsBlockWithSilkTouch(Block block, LootPoolEntry.Builder<?> child) {
        return BlockLootTableGenerator.createForNeedingSilkTouch(block, child);
    }
    
    static LootTable.Builder dropsSingleOreGem(Block block, ItemConvertible gem) {
        return dropsBlockWithSilkTouch(block, addExplosionDecayLootFunction(block, ItemEntry.builder(gem).withFunction(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))));
    }
    
    static <T> T addExplosionDecayLootFunction(ItemConvertible drop, LootFunctionConsumingBuilder<T> builder) {
        return BlockLootTableGenerator.addExplosionDecayLootFunction(drop, builder);
    }
    
    static <T> T addSurvivesExplosionLootCondition(ItemConvertible drop, LootConditionConsumingBuilder<T> builder) {
        return BlockLootTableGenerator.addSurvivesExplosionLootCondition(drop, builder);
    }
}
