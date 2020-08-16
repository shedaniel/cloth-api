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

package me.shedaniel.cloth.impl.datagen;

import me.shedaniel.cloth.api.datagen.v1.LootTableData;
import me.shedaniel.cloth.api.datagen.v1.RecipeData;
import me.shedaniel.cloth.api.datagen.v1.TagData;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.Lazy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class DataGeneratorHandlerImpl implements me.shedaniel.cloth.api.datagen.v1.DataGeneratorHandler {
    private static final Logger LOGGER = LogManager.getLogger();
    private final DataGenerator dataGenerator;
    private final Lazy<LootTableDataProvider> lootTableGenerator = new Lazy<>(() -> new LootTableDataProvider(this));
    private final Lazy<TagDataProvider> tagDataProvider = new Lazy<>(() -> new TagDataProvider(this));
    private final Lazy<RecipeDataProvider> recipeDataProvider = new Lazy<>(() -> new RecipeDataProvider(this));
    
    public DataGeneratorHandlerImpl(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }
    
    @Override
    public DataGenerator getDataGenerator() {
        return dataGenerator;
    }
    
    @Override
    public LootTableData getLootTables() {
        return lootTableGenerator.get();
    }
    
    @Override
    public TagData getTags() {
        return tagDataProvider.get();
    }
    
    @Override
    public RecipeData getRecipes() {
        return recipeDataProvider.get();
    }
    
    @Override
    public void run() {
        LOGGER.info("Starting datagen in: " + getOutput().toString());
        try {
            this.dataGenerator.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
