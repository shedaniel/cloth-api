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

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.carver.CarverConfig;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.*;

public interface WorldGenData extends SimpleData {
    default <FC extends FeatureConfig, F extends Feature<FC>> void addFeature(Identifier key, ConfiguredFeature<FC, F> feature) {
        addCodec("data/" + key.getNamespace() + "/" + Registry.CONFIGURED_FEATURE_KEY.getValue().getPath() + "/" + key.getPath() + ".json", ConfiguredFeature.CODEC, feature);
    }
    
    default <FC extends FeatureConfig, F extends StructureFeature<FC>> void addStructureFeature(Identifier key, ConfiguredStructureFeature<FC, F> feature) {
        addCodec("data/" + key.getNamespace() + "/" + Registry.CONFIGURED_STRUCTURE_FEATURE_KEY.getValue().getPath() + "/" + key.getPath() + ".json", ConfiguredStructureFeature.CODEC, feature);
    }
    
    default <WC extends CarverConfig> void addCarver(Identifier key, ConfiguredCarver<WC> carver) {
        addCodec("data/" + key.getNamespace() + "/" + Registry.CONFIGURED_CARVER_KEY.getValue().getPath() + "/" + key.getPath() + ".json", ConfiguredCarver.CODEC, carver);
    }
    
    default void addBiome(Identifier key, Biome biome) {
        addCodec("data/" + key.getNamespace() + "/" + Registry.BIOME_KEY.getValue().getPath() + "/" + key.getPath() + ".json", Biome.CODEC, biome);
    }
    
    default void addDimension(Identifier key, DimensionType dimensionType) {
        addCodec("data/" + key.getNamespace() + "/" + Registry.DIMENSION_TYPE_KEY.getValue().getPath() + "/" + key.getPath() + ".json", DimensionType.CODEC, dimensionType);
    }
}
