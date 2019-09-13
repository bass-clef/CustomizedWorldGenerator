package jp.humibassclef.api

import org.bukkit.Material
import java.util.*

interface OreConfig {

    fun getMaterial(): Material

    fun getOreGenerator(): String

    fun getValue(setting: OreSetting): Optional<Int>

    fun setValue(setting: OreSetting, value: Int)

    fun getOreSettings(): Map<OreSetting, Int>
}
