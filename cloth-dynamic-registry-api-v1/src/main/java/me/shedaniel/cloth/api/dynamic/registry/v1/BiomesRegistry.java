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
    public static void registerFeature(DynamicRegistryManager manager, Biome biome, GenerationStep.Feature generationStep, RegistryKey<ConfiguredFeature<?, ?>> configuredFeature) {
        registerFeature(manager, biome, generationStep, () -> manager.get(Registry.CONFIGURED_FEATURE_WORLDGEN).get(configuredFeature));
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
    
    public static void registerStructure(DynamicRegistryManager manager, Biome biome, RegistryKey<ConfiguredStructureFeature<?, ?>> configuredStructure) {
        registerStructure(manager, biome, () -> manager.get(Registry.CONFIGURED_STRUCTURE_FEATURE_WORLDGEN).get(configuredStructure));
    }
    
    public static void registerStructure(DynamicRegistryManager manager, Biome biome, Supplier<ConfiguredStructureFeature<?, ?>> configuredStructure) {
        GenerationSettings generationSettings = biome.getGenerationSettings();
        
        List<Supplier<ConfiguredStructureFeature<?, ?>>> structures = generationSettings.structureFeatures;
        if (structures instanceof ImmutableList) {
            structures = generationSettings.structureFeatures = Lists.newArrayList(structures);
        }
        
        structures.add(configuredStructure);
    }
    
    public static void registerCarver(DynamicRegistryManager manager, Biome biome, GenerationStep.Carver carver, RegistryKey<ConfiguredCarver<?>> configuredCarver) {
        registerCarver(manager, biome, carver, () -> manager.get(Registry.CONFIGURED_CARVER_WORLDGEN).get(configuredCarver));
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
