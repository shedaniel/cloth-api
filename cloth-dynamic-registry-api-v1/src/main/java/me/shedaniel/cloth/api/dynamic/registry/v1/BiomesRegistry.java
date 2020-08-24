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

package me.shedaniel.cloth.api.dynamic.registry.v1;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class BiomesRegistry {
    public static void registerFeature(DynamicRegistryManager manager, Biome biome, GenerationStep.Feature generationStep, RegistryKey<ConfiguredFeature<?, ?>> key) {
        registerFeature(biome, generationStep, () -> {
            ConfiguredFeature<?, ?> feature = manager.get(Registry.CONFIGURED_FEATURE_WORLDGEN).get(key);
            if (feature == null) {
                System.err.println("Configured Feature doesn't exist at " + key.getValue());
                throw new NullPointerException("Configured Feature doesn't exist at " + key.getValue());
            }
            return feature;
        });
    }
    
    public static void registerFeature(Biome biome, GenerationStep.Feature generationStep, Supplier<ConfiguredFeature<?, ?>> configuredFeature) {
        registerFeature(null, biome, generationStep, configuredFeature);
    }
    
    public static void registerFeature(DynamicRegistryManager manager, Biome biome, GenerationStep.Feature generationStep, Supplier<ConfiguredFeature<?, ?>> configuredFeature) {
        GenerationSettings generationSettings = biome.getGenerationSettings();
        
        List<List<Supplier<ConfiguredFeature<?, ?>>>> features = generationSettings.features;
        if (features instanceof ImmutableList) {
            features = generationSettings.features = Lists.newArrayList(features);
        }
        
        for (int i = features.size(); i <= generationStep.ordinal(); i++) {
            features.add(Lists.newArrayList());
        }
        
        List<Supplier<ConfiguredFeature<?, ?>>> suppliers = features.get(generationStep.ordinal());
        if (suppliers instanceof ImmutableList) {
            features.set(generationStep.ordinal(), suppliers = Lists.newArrayList(suppliers));
        }
        
        suppliers.add(configuredFeature);
    }
    
    public static void registerStructure(DynamicRegistryManager manager, Biome biome, RegistryKey<ConfiguredStructureFeature<?, ?>> key) {
        registerStructure(biome, () -> {
            ConfiguredStructureFeature<?, ?> feature = manager.get(Registry.CONFIGURED_STRUCTURE_FEATURE_WORLDGEN).get(key);
            if (feature == null) {
                System.err.println("Configured Structure Feature doesn't exist at " + key.getValue());
                throw new NullPointerException("Configured Structure Feature doesn't exist at " + key.getValue());
            }
            return feature;
        });
    }
    
    public static void registerStructure(Biome biome, Supplier<ConfiguredStructureFeature<?, ?>> configuredStructure) {
        registerStructure(null, biome, configuredStructure);
    }
    
    public static void registerStructure(DynamicRegistryManager manager, Biome biome, Supplier<ConfiguredStructureFeature<?, ?>> configuredStructure) {
        GenerationSettings generationSettings = biome.getGenerationSettings();
        
        List<Supplier<ConfiguredStructureFeature<?, ?>>> structures = generationSettings.structureFeatures;
        if (structures instanceof ImmutableList) {
            structures = generationSettings.structureFeatures = Lists.newArrayList(structures);
        }
        
        structures.add(configuredStructure);
    }
    
    public static void registerCarver(DynamicRegistryManager manager, Biome biome, GenerationStep.Carver carver, RegistryKey<ConfiguredCarver<?>> key) {
        registerCarver(biome, carver, () -> {
            ConfiguredCarver<?> configuredCarver = manager.get(Registry.CONFIGURED_CARVER_WORLDGEN).get(key);
            if (configuredCarver == null) {
                System.err.println("Configured Carver doesn't exist at " + key.getValue());
                throw new NullPointerException("Configured Carver doesn't exist at " + key.getValue());
            }
            return configuredCarver;
        });
    }
    
    public static void registerCarver(Biome biome, GenerationStep.Carver carver, Supplier<ConfiguredCarver<?>> configuredCarver) {
        registerCarver(null, biome, carver, configuredCarver);
    }
    
    public static void registerCarver(DynamicRegistryManager manager, Biome biome, GenerationStep.Carver carver, Supplier<ConfiguredCarver<?>> configuredCarver) {
        GenerationSettings generationSettings = biome.getGenerationSettings();
        
        Map<GenerationStep.Carver, List<Supplier<ConfiguredCarver<?>>>> carvers = generationSettings.carvers;
        if (carvers instanceof ImmutableMap) {
            carvers = generationSettings.carvers = Maps.newLinkedHashMap(carvers);
        }
        
        List<Supplier<ConfiguredCarver<?>>> suppliers = carvers.get(carver);
        if (suppliers instanceof ImmutableList) {
            carvers.put(carver, suppliers = Lists.newArrayList(suppliers));
        }
        
        suppliers.add(configuredCarver);
    }
}
