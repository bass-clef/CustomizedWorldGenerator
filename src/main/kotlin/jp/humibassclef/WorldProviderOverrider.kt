package jp.humibassclef

import com.google.gson.*
import com.google.gson.stream.JsonReader
import jp.humibassclef.util.ChunkProviderSettings
import jp.humibassclef.util.OverriderSettingsOverworld
import net.minecraft.server.v1_14_R1.*
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.StringUtils.abbreviateMiddle
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.io.Reader
import java.io.StringReader
import java.lang.reflect.Type
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors


class JsonUtils {
    companion object {
        fun <T> gsonDeserialize(gsonIn: Gson, readerIn: Reader, adapter: Class<T>, lenient: Boolean): T {
            try {
                val jsonReader = JsonReader(readerIn)
                jsonReader.isLenient = lenient
                return gsonIn.getAdapter(adapter).read(jsonReader)
            } catch(e: Exception) {
                throw JsonParseException(e)
            }
        }
        fun <T> gsonDeserialize(gsonIn: Gson, json: String, adapter: Class<T>, lenient: Boolean): T {
            return gsonDeserialize(gsonIn, StringReader(json), adapter, lenient)
        }
        fun <T> gsonDeserialize(gsonIn: Gson, json: String, adapter: Class<T>): T {
            return gsonDeserialize(gsonIn, json, adapter, false)
        }

        fun toString(jsonElement: JsonElement): String {
            val s = abbreviateMiddle((jsonElement as Any?).toString(), "...", 10)
            if (jsonElement == null) {
                return "null (missing)"
            } else if (jsonElement.isJsonNull) {
                return "null (json)"
            } else if (jsonElement.isJsonArray) {
                return "an array ($s)"
            } else if (jsonElement.isJsonObject) {
                return "an object ($s)"
            } else {
                if (jsonElement.isJsonPrimitive) {
                    val jsonprimitive = jsonElement.asJsonPrimitive
                    if (jsonprimitive.isNumber) {
                        return "a number ($s)"
                    }

                    if (jsonprimitive.isBoolean) {
                        return "a boolean ($s)"
                    }
                }

                return s
            }
        }

        fun getBoolean(jsonElement: JsonElement, key: String): Boolean {
            if (jsonElement.isJsonPrimitive) {
                return jsonElement.asBoolean
            } else {
                throw JsonSyntaxException("Expected $key to be a Boolean, was ${toString(jsonElement)}")
            }
        }
        fun getBoolean(jsonObject: JsonObject, key: String): Boolean {
            if (jsonObject.has(key)) {
                return getBoolean(jsonObject.get(key), key)
            } else {
                throw JsonSyntaxException("Missing $key, expected to find a Boolean")
            }
        }
        fun getBoolean(jsonObject: JsonObject, key: String, defaultValue: Boolean): Boolean {
            return if (jsonObject.has(key)) getBoolean(jsonObject.get(key), key) else defaultValue
        }
        fun getDouble(jsonElement: JsonElement, key: String): Double {
            if (jsonElement.isJsonPrimitive) {
                return jsonElement.asDouble
            } else {
                throw JsonSyntaxException("Expected $key to be a Double, was ${toString(jsonElement)}")
            }
        }
        fun getDouble(jsonObject: JsonObject, key: String): Double {
            if (jsonObject.has(key)) {
                return getDouble(jsonObject.get(key), key)
            } else {
                throw JsonSyntaxException("Missing $key, expected to find a Double")
            }
        }
        fun getDouble(jsonObject: JsonObject, key: String, defaultValue: Double): Double {
            return if (jsonObject.has(key)) getDouble(jsonObject.get(key), key) else defaultValue
        }
    }
}

class WorldProviderOverrider(world: World, dimensionmanager: DimensionManager) : WorldProviderNormal(world, dimensionmanager) {
    companion object {
        class Factory(settingsDefault: ChunkProviderSettings = DEFAULT_SETTINGS) {
            companion object {
                val JSON_ADAPTER = GsonBuilder().registerTypeAdapter(Factory::class.java, Serializer()).create()
                val SETTINGS_FILE = "customized.json"
                var DEFAULT_SETTINGS = ChunkProviderSettings()

                init {
                    setDefaults()
                    CustomizedWorldGenerator.instance.logger.info("initilized. make default customized.json")
                }
                fun jsonToFactory(source: String): Factory {
                    if (source.isEmpty()) return Factory()
                    try {
                        return JsonUtils.gsonDeserialize(JSON_ADAPTER, source, Factory::class.java)
                    } catch(e: Exception) {
                        return Factory()
                    }
                }

                private fun getWorldFileName(worldName: String): String = "${CustomizedWorldGenerator.instance.dataFolder.path}/../../$worldName/$SETTINGS_FILE"
                private fun getServerFileName(): String = "${CustomizedWorldGenerator.instance.dataFolder.path}/../../$SETTINGS_FILE"
                private fun setDefaults() {
                    val factory = Factory()
                    try {
                        factory.loadSettings(getServerFileName())
                    } catch(e: Exception) {
                        factory.saveSettings(getServerFileName())
                    }
                    DEFAULT_SETTINGS = ChunkProviderSettings( factory.settings.map )
                }
            }

            var settings: ChunkProviderSettings = ChunkProviderSettings(settingsDefault.map)
            var isFromFile: Boolean = false
            init {
                settings = DEFAULT_SETTINGS
                isFromFile = false
            }
            constructor(worldName: String): this() {
                try {
                    loadWorldSettings(worldName)
                } catch(e: Exception) {
                    saveWorldSettings(worldName)
                }
            }

            fun loadWorldSettings(worldName: String) = loadSettings(getWorldFileName(worldName))
            fun saveWorldSettings(worldName: String) = saveSettings(getWorldFileName(worldName))

            private fun loadSettings(path: String) {
                val json = Files.lines( Paths.get( path ), Charsets.UTF_8 )
                        .collect(Collectors.joining(System.getProperty("line.separator")))
                settings = ChunkProviderSettings( jsonToFactory(json).settings.map )
                isFromFile = !isDefault()
            }
            private fun saveSettings(path: String) {
                val writer = OutputStreamWriter( FileOutputStream( path ), Charsets.UTF_8 )
                writer.write( this.toString() )
                writer.close()
            }

            fun isDefault(): Boolean = settings == DEFAULT_SETTINGS

            override fun toString(): String = JSON_ADAPTER.toJson(this)

            override fun equals(obj: Any?): Boolean {
                if (this === obj) return true
                if (!(obj != null && obj is Factory)) return false
                var ret = true
                for(it in this.settings.map) {
                    if (it.value != obj.settings.map[it.key]) {
                        ret = false
                        break
                    }
                }
                return ret
            }

            override fun hashCode(): Int {
                var hash = 1
                this.settings.map.map {
                    hash = 31 * hash + when(it.value) {
                        is Double -> Double.fromBits(it.value as Long)
                        is Boolean -> if (it.value as Boolean) 1 else 0
                        else -> 0
                    } as Int
                }
                return hash
            }
        }
        class Serializer : JsonDeserializer<Factory>, JsonSerializer<Factory> {
            override fun deserialize(jsonElement: JsonElement, type: Type, context: JsonDeserializationContext): Factory {
                val jsonObject: JsonObject = jsonElement.asJsonObject
                val worldProviderOverrider_Factory = Factory()
                try {
                    for(it in worldProviderOverrider_Factory.settings.map) {
                        worldProviderOverrider_Factory.settings.map[it.key] = when(it.value) {
                            is Boolean -> JsonUtils.getBoolean(jsonObject, it.key, it.value as Boolean)
                            is Double -> JsonUtils.getDouble(jsonObject, it.key, it.value as Double)
                            else -> { throw Exception("unknown parameter type ${it.key.javaClass}") }
                        }
                    }
                } catch(e: Exception) {
                    CustomizedWorldGenerator.instance.logger.info("exception inner is null. $e")
                }
                return Factory(worldProviderOverrider_Factory.settings)
            }

            override fun serialize(factory: Factory, type: Type?, context: JsonSerializationContext?): JsonElement {
                val jsonObject = JsonObject()
                for (it in factory.settings.map) {
                    when(it.value) {
                        is Boolean -> jsonObject.addProperty(it.key, it.value as Boolean)
                        is Double -> jsonObject.addProperty(it.key, it.value as Double)
                        else -> { throw Exception("unknown parameter type ${it.key.javaClass}") }
                    }
                }
                return jsonObject
            }
        }
    }

    override fun getChunkGenerator(): ChunkGenerator<*> {
        val worldType = this.b.getWorldData().type
        if (worldType != WorldType.CUSTOMIZED) {
            return super.getChunkGenerator()
        }

        val opt = this.b.getWorldData().a( NBTTagCompound() )
        var factory = Factory(this.b.world.name)

        if (factory.isDefault() && !factory.isFromFile) {
            val jsonOption = opt.get("legacy_custom_options").toString().replace("'", "")
            factory = Factory.jsonToFactory( jsonOption ) ?: factory
            factory.saveWorldSettings(this.b.world.name)

            CustomizedWorldGenerator.instance.logger.info("""[${this.b.world.name}] loaded (show only first).
                [${jsonOption}]
                --- readed options >>>
                [${factory}]""".trimIndent())
        } else {
            CustomizedWorldGenerator.instance.logger.info("readed settings from customized.json")
        }

        val overriderSettingsOverworld = OverriderSettingsOverworld( factory.settings )
        val biomelayout1 = BiomeLayout.c
        val biomelayoutoverworldconfiguration = (biomelayout1.a() as BiomeLayoutOverworldConfiguration).a(this.b.getWorldData()).a(overriderSettingsOverworld)

        return ChunkOverriderOverworld(this.b, biomelayout1.a(biomelayoutoverworldconfiguration), overriderSettingsOverworld)
    }
}