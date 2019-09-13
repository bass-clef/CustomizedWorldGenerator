package jp.humibassclef.impl

import jp.humibassclef.api.BiomeConfig
import jp.humibassclef.api.OreConfig
import jp.humibassclef.api.WorldConfig
import org.bukkit.Material
import org.bukkit.block.Biome
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import java.util.*
import java.util.Optional.ofNullable

@SerializableAs("TestWorldGenerator#WorldConfig")
class WorldConfigYamlImpl(world: String) : WorldConfig, ConfigurationSerializable {

    private var world: String
    init {
        this.world = world
    }
    private val oreConfigs = HashMap<Material, OreConfig>()
    private val biomeConfigs = HashMap<Biome, BiomeConfig>()

    companion object {
        private val WORLD_KEY = "world"

        @JvmStatic fun deserialize(map: Map<String, Any>): WorldConfigYamlImpl {
            val oreConfig = WorldConfigYamlImpl(map[WORLD_KEY] as String)

            map.entries.stream().filter{ value -> isOreConfig(value) }.map{ entry -> entry.value as OreConfig }.forEach(oreConfig::addOreConfig)
            map.entries.stream().filter{ value -> isBiomeConfig(value) }.map{ entry -> entry.value as BiomeConfig }.forEach(oreConfig::addBiomeConfig)

            return oreConfig
        }

        @JvmStatic private fun isOreConfig(entry: Map.Entry<String, Any>): Boolean {
            return entry.value is OreConfig
        }

        @JvmStatic private fun isBiomeConfig(entry: Map.Entry<String, Any>): Boolean {
            return entry.value is BiomeConfig
        }
    }

    override fun getWorld(): String {
        return this.world
    }

    override fun getOreConfig(material: Material): Optional<OreConfig> {
        return ofNullable(oreConfigs[material])
    }

    override fun getOreConfigs(): Set<OreConfig> {
        return HashSet(oreConfigs.values)
    }

    override fun addOreConfig(oreConfig: OreConfig) {
        oreConfigs[oreConfig.getMaterial()] = oreConfig
    }

    override fun getBiomeConfig(biome: Biome): Optional<BiomeConfig> {
        return Optional.ofNullable(biomeConfigs[biome])
    }

    override fun getBiomeConfigs(): Set<BiomeConfig> {
        return HashSet(biomeConfigs.values)
    }

    override fun addBiomeConfig(biomeConfig: BiomeConfig) {
        biomeConfigs[biomeConfig.getBiome()] = biomeConfig
    }

    @SuppressWarnings("Duplicates")
    override fun serialize(): Map<String, Any> {
        val map = LinkedHashMap<String, Any>()

        map[WORLD_KEY] = world

        getOreConfigs().forEach { value ->
            if (value is ConfigurationSerializable) {
                map[value.getMaterial().toString()] = value
                return@forEach
            }
            val oreConfig = OreConfigYamlImpl(value.getMaterial(), value.getOreGenerator())

            value.getOreSettings().forEach(oreConfig::setValue)

            map[value.getMaterial().toString()] = value
        }

        getBiomeConfigs().forEach { value ->
            if (value is ConfigurationSerializable) {
                map[value.getBiome().toString()] = value
                return@forEach
            }
            val biomeConfigYaml = BiomeConfigYamlImpl(value.getBiome())

            value.getOreConfigs().forEach(biomeConfigYaml::addOreConfig)

            map[value.getBiome().toString()] = value
        }

        return map
    }
}
