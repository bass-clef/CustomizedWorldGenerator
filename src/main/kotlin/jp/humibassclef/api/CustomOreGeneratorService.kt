package jp.humibassclef.api

import org.bukkit.World
import java.util.*

interface CustomOreGeneratorService {

    fun getOreGenerator(name: String): Optional<OreGenerator>

    fun registerOreGenerator(oreGenerator: OreGenerator)

    fun getWorldConfig(world: String): Optional<WorldConfig>

    fun createWorldConfig(world: World): WorldConfig

    fun saveWorldConfig(config: WorldConfig)

    fun getWorldConfigs(): Set<WorldConfig>

    fun setDefaultOreGenerator(oreGenerator: OreGenerator)

    fun getDefaultOreGenerator(): OreGenerator
}
