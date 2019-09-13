package jp.humibassclef.api

import org.bukkit.Bukkit
import java.util.*


enum class Version {

    v1_14_R1, v1_13_R2, v1_13_R1, v1_12_R1, v1_11_R1, v1_10_R1, v1_9_R2, v1_9_R1, v1_8_R3, v1_8_R2, v1_8_R1;

    private val list = ArrayList<Runnable>()

    fun run() {
        list.forEach(Runnable::run)
    }

    fun add(runnable: Runnable) {
        list.add(runnable)
    }

    companion object {
        private var current: Version
        @JvmStatic fun getCurrent(): Version { return current }

        private var paper: Boolean
        @JvmStatic fun getPaper(): Boolean { return paper }

        @JvmStatic fun clear() {
            for (value in values()) {
                value.list.clear()
            }
        }

        init {
            val version = Bukkit.getServer().javaClass.getPackage().name.substring(Bukkit.getServer().javaClass.getPackage().name.lastIndexOf('.') + 1)

            try {
                current = valueOf(version.trim { it <= ' ' })
            } catch (e: IllegalArgumentException) {
                throw IllegalStateException("unknown server version: $version")
            }

            paper = Bukkit.getBukkitVersion().toLowerCase().contains("paper")
        }
    }

}
