package jp.humibassclef

import jp.humibassclef.api.CustomOreGeneratorService
import jp.humibassclef.api.Version
import jp.humibassclef.impl.BiomeConfigYamlImpl
import jp.humibassclef.impl.CustomOreGeneratorServiceImpl
import jp.humibassclef.impl.OreConfigYamlImpl
import jp.humibassclef.impl.WorldConfigYamlImpl
import jp.humibassclef.impl.dao.WorldConfigYamlDao
import org.bukkit.Bukkit
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.event.Listener
import org.bukkit.generator.ChunkGenerator
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class TestWorldGenerator : JavaPlugin(), Listener {
    companion object {
        val name = "TestWorldGenerator"
        lateinit var instance: TestWorldGenerator

        init {
            ConfigurationSerialization.registerClass(WorldConfigYamlImpl::class.java)

            Version.v1_14_R1.add( object: Runnable {
                override fun run() {
                    WorldHandler_v1_14_R1()
                }
            } )
        }

        @JvmStatic fun getService(): CustomOreGeneratorService {
            val service = Bukkit.getServicesManager().load(CustomOreGeneratorService::class.java)

            if (null == service) {
                throw IllegalStateException(
                        "The Bukkit service have to ${CustomOreGeneratorService::class.java.getName()} registered",
                        NullPointerException("service can't be null")
                )
            }
            return service
        }
    }

    override fun onLoad() {
        instance = this
        Bukkit.getServicesManager().register(
                CustomOreGeneratorService::class.java,
                CustomOreGeneratorServiceImpl(
                        WorldConfigYamlDao(
                                File(
                                        getDataFolder(), "data/world_configs.yml"))
                ),
                this,
                ServicePriority.Normal
        )
    }

    override fun onEnable() {
        // Plugin startup logic
        Version.getCurrent().run()
        Version.clear()
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    override fun getDefaultWorldGenerator(worldName: String, id: String?): ChunkGenerator {
        return TestWorldChunkGenerator(worldName, id)
    }
}
