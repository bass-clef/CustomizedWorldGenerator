package jp.humibassclef.api

import org.bukkit.Material
import org.bukkit.block.Biome
import java.util.*

interface WorldConfig {

    fun getWorld(): String

    fun getOreConfig(material: Material): Optional<OreConfig>

    fun getOreConfigs(): Set<OreConfig>

    fun addOreConfig(oreConfig: OreConfig)

    fun getBiomeConfig(biome: Biome): Optional<BiomeConfig>

    fun getBiomeConfigs(): Set<BiomeConfig>

    fun addBiomeConfig(biomeConfig: BiomeConfig)

}
