package jp.humibassclef.impl

import jp.humibassclef.api.BiomeConfig
import jp.humibassclef.api.OreConfig
import org.bukkit.Material
import org.bukkit.block.Biome
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import java.util.*
import java.util.Optional.ofNullable


@SerializableAs(value = "CustomOreGenerator#BiomeConfig")
class BiomeConfigYamlImpl(biome: Biome) : BiomeConfig, ConfigurationSerializable {

    private var biome: Biome
    override fun getBiome(): Biome {
        return this.biome
    }

    private val oreConfigs: HashMap<Material, OreConfig> = HashMap()

    init {
        this.biome = biome
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

    @SuppressWarnings("Duplicates")
    override fun serialize(): Map<String, Any> {
        val map = LinkedHashMap<String, Any>()

        map[BIOME_KEY] = biome.toString()

        getOreConfigs().forEach { value ->
            if (value is ConfigurationSerializable) {
                map[value.getMaterial().toString()] = value
                return@forEach
            }
            val oreConfig = OreConfigYamlImpl(value.getMaterial(), value.getOreGenerator())

            value.getOreSettings().forEach(oreConfig::setValue)

            map[value.getMaterial().toString()] = value
        }

        return map
    }

    companion object {

        private val BIOME_KEY = "biome"

        @JvmStatic fun deserialize(map: Map<String, Any>): BiomeConfigYamlImpl {
            val biomeConfig = BiomeConfigYamlImpl(Biome.valueOf(map[BIOME_KEY] as String))

            map.entries.stream().filter{ value -> isOreConfig(value) }
                    .map{ entry -> entry.value as OreConfig }
                    .forEach(biomeConfig::addOreConfig)
            return biomeConfig
        }

        @JvmStatic fun isOreConfig(entry: Map.Entry<String, Any>): Boolean {
            return entry.value is OreConfig
        }
    }

}
