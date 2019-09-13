package jp.humibassclef.impl.dao

import com.google.common.collect.Sets
import jp.humibassclef.api.WorldConfig
import jp.humibassclef.api.dao.WorldConfigDao
import jp.humibassclef.impl.WorldConfigYamlImpl
import jp.humibassclef.util.Config
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.configuration.serialization.ConfigurationSerializable
import java.io.File
import java.io.IOException
import java.util.*

class WorldConfigYamlDao(file: File) : WorldConfigDao {

    private val file: File
    private val yaml: YamlConfiguration //TODO Check Thread safety

    init {
        this.file = file
        yaml = Config(file)
        try {
            yaml.save(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun get(world: String): Optional<WorldConfig> {
        val obj = yaml.get(world) ?: return Optional.empty()
        return Optional.of(obj as WorldConfig)
    }

    override fun remove(config: WorldConfig) {
        yaml.set(config.getWorld(), null)

        try {
            yaml.save(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    // TODO kotlin では引数に対して代入できないためこの関数をkotlinに移植することができないため代価の関数を作成
    override fun save(config: WorldConfig) {
        var config = config
        if (config !is ConfigurationSerializable) {
            val config2: WorldConfig = WorldConfigYamlImpl(config.getWorld())
            config.getBiomeConfigs().forEach(config2::addBiomeConfig)
            config.getOreConfigs().forEach(config2::addOreConfig)
            config = config2
        }

        yaml.set(config.getWorld(), config)

        try {
            yaml.save(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun getAll(): Set<WorldConfig> {
        return Sets.newHashSet(yaml.getKeys(false).stream().map(yaml::get)
                .filter(Objects::nonNull).filter { value -> value is WorldConfig }
                .map { value -> value as WorldConfig }
                .toArray{ size -> arrayOfNulls<WorldConfig>(size) }
        ) as Set<WorldConfig>
    }
}
