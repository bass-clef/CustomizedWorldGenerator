package jp.humibassclef.api

import org.bukkit.World
import org.bukkit.block.Biome
import java.util.*

interface OreGenerator {

    fun generate(config: OreConfig, world: World, x: Int, z: Int, random: Random, biome: Biome)

    fun getNeededOreSettings(): Set<OreSetting>

    fun getName(): String

}
