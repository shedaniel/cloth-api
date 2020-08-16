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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import me.shedaniel.cloth.api.datagen.v1.DataGeneratorHandler;
import me.shedaniel.cloth.api.datagen.v1.RecipeData;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataProvider;
import net.minecraft.data.server.RecipesProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

public class RecipeDataProvider implements DataProvider, RecipeData {
    private static final Logger LOGGER = LogManager.getLogger();
    private final DataGeneratorHandler handler;
    private final List<RecipeJsonProvider> recipes = Lists.newArrayList();
    
    public RecipeDataProvider(DataGeneratorHandler handler) {
        this.handler = handler;
        this.handler.install(this);
    }
    
    @Override
    public void accept(RecipeJsonProvider recipeJsonProvider) {
        recipes.add(recipeJsonProvider);
    }
    
    @Override
    public void run(DataCache cache) throws IOException {
        Path path = this.handler.getOutput();
        Set<Identifier> set = Sets.newHashSet();
        recipes.forEach((recipeJsonProvider) -> {
            if (!set.add(recipeJsonProvider.getRecipeId())) {
                throw new IllegalStateException("Duplicate recipe " + recipeJsonProvider.getRecipeId());
            } else {
                RecipesProvider.saveRecipe(cache, recipeJsonProvider.toJson(), path.resolve("data/" + recipeJsonProvider.getRecipeId().getNamespace() + "/recipes/" + recipeJsonProvider.getRecipeId().getPath() + ".json"));
                JsonObject jsonObject = recipeJsonProvider.toAdvancementJson();
                if (jsonObject != null) {
                    RecipesProvider.saveRecipeAdvancement(cache, jsonObject, path.resolve("data/" + recipeJsonProvider.getRecipeId().getNamespace() + "/advancements/" + recipeJsonProvider.getAdvancementId().getPath() + ".json"));
                }
            }
        });
    }
    
    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
}
