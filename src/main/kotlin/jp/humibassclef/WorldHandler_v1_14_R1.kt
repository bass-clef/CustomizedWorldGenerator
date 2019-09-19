package jp.humibassclef

import com.google.gson.JsonParser
import jp.humibassclef.api.WorldHandler
import net.minecraft.server.v1_14_R1.*
import net.minecraft.server.v1_14_R1.DimensionManager.register
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.WorldInitEvent
import org.bukkit.event.world.WorldLoadEvent
import java.awt.Point
import java.lang.reflect.Field
import java.util.*
import java.util.function.BiFunction

class WorldHandler_v1_14_R1 : WorldHandler, Listener {
    companion object {
        // overrided DimensionManager.OVERWORLD
        val OVERWORLD = register("overworld",
                DimensionManager(1, "", "",
                        BiFunction<World, DimensionManager, WorldProvider> { world, dimensionmanager ->
                            WorldProviderOverrider(world, dimensionmanager) },
                        true,
                        null as DimensionManager?
                )
        )
    }

    init {
        Bukkit.getPluginManager().registerEvents(this, CustomizedWorldGenerator.instance)
        val factory = WorldProviderOverrider.Companion.Factory()    // making initialize customized.json
        CustomizedWorldGenerator.instance.logger.info("making initialize customized.json, overrided DimentionManager.OVERWORLD")
    }
}
