package jp.humibassclef.impl

import jp.humibassclef.api.CustomOreGeneratorService
import jp.humibassclef.api.OreGenerator
import jp.humibassclef.api.WorldConfig
import jp.humibassclef.api.dao.WorldConfigDao
import jp.humibassclef.impl.dao.WorldConfigYamlDao
import org.bukkit.World
import java.util.*

class CustomOreGeneratorServiceImpl(ydao: WorldConfigYamlDao) : CustomOreGeneratorService {

    private var dao: WorldConfigDao
    private val oreGenerator = HashMap<String, OreGenerator>()

    init {
        dao = ydao
    }

    private lateinit var defaultOreGenerator: OreGenerator
    override fun getDefaultOreGenerator() : OreGenerator {
        return this.defaultOreGenerator
    }

    override fun getOreGenerator(name: String): Optional<OreGenerator> {
        return Optional.ofNullable(oreGenerator[name])
    }

    override fun registerOreGenerator(oreGenerator: OreGenerator) {
        this.oreGenerator[oreGenerator.getName()] = oreGenerator
    }

    override fun getWorldConfig(world: String): Optional<WorldConfig> {
        return dao.get(world)
    }

    override fun createWorldConfig(world: World): WorldConfig {
        val worldConfig = WorldConfigYamlImpl(world.getName())

        saveWorldConfig(worldConfig)

        return worldConfig
    }

    override fun saveWorldConfig(config: WorldConfig) {
        dao.save(config)
    }

    override fun getWorldConfigs(): Set<WorldConfig> {
        return dao.getAll()
    }

    override fun setDefaultOreGenerator(oreGenerator: OreGenerator) {
        registerOreGenerator(oreGenerator)
        defaultOreGenerator = oreGenerator
    }


}
