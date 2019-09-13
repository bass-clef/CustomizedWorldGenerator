package jp.humibassclef.api

import org.bukkit.Material
import org.bukkit.block.Biome
import java.util.*

interface BiomeConfig {

    fun getBiome(): Biome

    fun getOreConfig(material: Material): Optional<OreConfig>
    fun getOreConfigs(): Set<OreConfig>

    fun addOreConfig(oreConfig: OreConfig)

}
