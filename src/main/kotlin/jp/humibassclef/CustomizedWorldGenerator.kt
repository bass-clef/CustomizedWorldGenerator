package jp.humibassclef

import jp.humibassclef.api.Version
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class CustomizedWorldGenerator : JavaPlugin(), Listener {
    companion object {
        val name = "CustomizedWorldGenerator"
        lateinit var instance: CustomizedWorldGenerator

        init {
            Version.v1_14_R1.add( object: Runnable {
                override fun run() {
                    WorldHandler_v1_14_R1()
                }
            } )
        }
    }

    override fun onLoad() {
        instance = this
    }

    override fun onEnable() {
        // Plugin startup logic
        Version.getCurrent().run()
        Version.clear()
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
