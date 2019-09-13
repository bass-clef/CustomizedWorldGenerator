package jp.humibassclef.impl

import jp.humibassclef.api.OreConfig
import jp.humibassclef.api.OreSetting
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import java.util.*


@SerializableAs(value = "CustomOreGenerator#OreConfig")
class OreConfigYamlImpl(material: Material, oreGenerator: String) : OreConfig, ConfigurationSerializable {

    private final val oreSettings = HashMap<OreSetting, Int>()
    private final val material: Material
    private final val oreGenerator: String

    init {
        if (!material.isBlock())
            throw IllegalArgumentException("material $material must be a block!")
        this.material = material
        this.oreGenerator = oreGenerator
    }

    override fun getOreSettings(): Map<OreSetting, Int> {
        return this.oreSettings
    }
    override fun getMaterial(): Material {
        return this.material
    }
    override fun getOreGenerator(): String {
        return this.oreGenerator
    }

    override fun getValue(setting: OreSetting): Optional<Int> {
        return Optional.ofNullable(oreSettings[setting])
    }

    override fun setValue(setting: OreSetting, value: Int) {
        oreSettings[setting] = value
    }

    override fun serialize(): Map<String, Any> {
        val map = LinkedHashMap<String, Any>()

        map[MATERIAL_KEY] = material.toString()
        map[ORE_GENERATOR_KEY] = oreGenerator

        oreSettings.forEach { (key, value) -> map[key.toString()] = value }

        return map
    }

    companion object {

        private val MATERIAL_KEY = "material"
        private val ORE_GENERATOR_KEY = "ore_generator"

        @JvmStatic fun deserialize(map: Map<String, Any>): OreConfigYamlImpl {
            val oreConfig = OreConfigYamlImpl(Material.valueOf(
                    map[MATERIAL_KEY].toString()),
                    map[ORE_GENERATOR_KEY].toString()
            )

            map.entries.stream().filter{ it -> OreConfigYamlImpl.isOreSetting(it) }.forEach {
                entry -> oreConfig.setValue(OreSetting.valueOf(entry.key), entry.value as Int)
            }

            return oreConfig
        }

        @JvmStatic private fun isOreSetting(entry: Map.Entry<String, Any>): Boolean {
            try {
                OreSetting.valueOf(entry.key)
                return true
            } catch (e: IllegalArgumentException) {
                return false
            }

        }
    }
}
