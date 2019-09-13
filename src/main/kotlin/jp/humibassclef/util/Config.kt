package jp.humibassclef.util


import com.google.common.base.Charsets
import com.google.common.io.Files
import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.*

import java.lang.String.format

/**
 * This is the Config class<br></br>
 * <br></br>
 *
 *
 * [notice me if you find Bugs or spelling mistake] <br></br>
 * [or if you have idea for more functions]
 *
 * @author ? <br></br>
 * optimized by DerFrZocker
 */
class Config : YamlConfiguration {

    constructor(file: File) {
        if (!file.exists()) {
            try {
                Files.createParentDirs(file)
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        // try to load the Config file
        try {
            this.load(file)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    constructor(input: InputStream) {
        // try to load the Config file
        try {
            this.load(input)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    constructor(input: String) {
        try {
            this.loadFromString(input)
        } catch (e: InvalidConfigurationException) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    override fun save(file: File) {

        Files.createParentDirs(file)

        try {
            val writer = OutputStreamWriter( FileOutputStream(file), Charsets.UTF_8 )

            writer.write(this.saveToString())
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @Throws(IOException::class, InvalidConfigurationException::class)
    override fun load(file: File) {

        // load the config file
        this.load(InputStreamReader(FileInputStream(file), Charsets.UTF_8))
    }

    @Throws(IOException::class, InvalidConfigurationException::class)
    fun load(input: InputStream) {

        // load the config file
        this.load(InputStreamReader(input))
    }

    companion object {
        @JvmStatic fun getConfig(plugin: JavaPlugin, name: String): Config {
            var name = name
            if (!name.endsWith(".yml"))
                name = format("%s.yml", name)

            val file = File(plugin.dataFolder.path, name)

            var defaults: Config? = null

            if (file.exists())
                defaults = Config(plugin.getResource(name)!!)
            else
                plugin.saveResource(name, true)

            val config = Config(file)

            if (defaults != null) {
                config.setDefaults(defaults)
                config.options().copyDefaults(true)
                try {
                    config.save(file)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            return config
        }
    }

}
