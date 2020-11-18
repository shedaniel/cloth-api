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

package me.shedaniel.cloth.test.datagen;

import me.shedaniel.cloth.api.datagen.v1.*;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.minecraft.advancement.criterion.ImpossibleCriterion;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.nio.file.Paths;

public class TestDatagen implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        try {
            DataGeneratorHandler handler = DataGeneratorHandler.create(Paths.get("../cloth-datagen-api-v1/src/generated/resource"));
            
            LootTableData table = handler.getLootTables();
            table.registerBlockDropSelf(Blocks.DIAMOND_BLOCK);
            table.registerBlockDrop(Blocks.IRON_BLOCK, Items.ACACIA_FENCE);
            table.register(Blocks.COAL_BLOCK, LootTableData.dropsBlockWithShears(Items.ACACIA_DOOR));
            table.register(Blocks.BONE_BLOCK, LootTableData.dropsBlockWithSilkTouch(Items.DIAMOND));
            table.register(Blocks.ACACIA_SLAB, LootTableData.dropsSlabs(Blocks.ACACIA_SLAB));
            
            TagData tag = handler.getTags();
            tag.block(new Identifier("thing")).append(Blocks.ACACIA_FENCE);
            TagData.TagBuilder<ItemConvertible> thing = tag.item(new Identifier("thing")).append(Blocks.ACACIA_FENCE, Blocks.STONE).appendTag(ItemTags.SAPLINGS).appendTag(ItemTags.BANNERS).appendOptionalTag(ItemTags.BUTTONS);
            tag.item(new Identifier("awesome")).append(Blocks.BIRCH_SAPLING, Items.IRON_AXE).appendTag(ItemTags.ANVIL).appendTag(thing);
            
            RecipeData recipes = handler.getRecipes();
            ShapelessRecipeJsonFactory.create(Items.STONE)
                    .criterion("impossible", new ImpossibleCriterion.Conditions())
                    .input(Items.SPONGE)
                    .input(Items.SPONGE)
                    .input(Items.SPONGE)
                    .input(Items.SPONGE)
                    .input(Items.SPONGE)
                    .input(Items.SPONGE)
                    .offerTo(recipes);
            
            ModelStateData modelStates = handler.getModelStates();
            modelStates.addSingletonCubeAll(Blocks.BONE_BLOCK);
            modelStates.addSimpleBlockItemModel(Blocks.BONE_BLOCK);
            
            modelStates.addHandheldItemModel(Items.DIAMOND_AXE);
            modelStates.addGeneratedItemModel(Items.LAPIS_LAZULI);
            
            WorldGenData worldGen = handler.getWorldGen();
            worldGen.addFeature(new Identifier("sponge_ores"),
                    Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, Blocks.SPONGE.getDefaultState(), 17))
                            .rangeOf(128)
                            .spreadHorizontally()
                            .repeat(20));
            
            handler.run();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }
}
