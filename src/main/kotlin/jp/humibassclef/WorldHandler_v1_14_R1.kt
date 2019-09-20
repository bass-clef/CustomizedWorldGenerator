package jp.humibassclef

import jp.humibassclef.api.WorldHandler
import net.minecraft.server.v1_14_R1.DimensionManager
import net.minecraft.server.v1_14_R1.World
import net.minecraft.server.v1_14_R1.WorldProvider
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import java.util.function.BiFunction

class WorldHandler_v1_14_R1 : WorldHandler, Listener {
    init {
        Bukkit.getPluginManager().registerEvents(this, CustomizedWorldGenerator.instance)
        WorldProviderOverrider.Companion.Factory()    // making initialize customized.json

        // overrided DimensionManager.OVERWORLD
        try {
            val bf = BiFunction<World, DimensionManager, WorldProvider> { world, dimensionmanager ->
                WorldProviderOverrider(world, dimensionmanager)
            }
            val field = DimensionManager.OVERWORLD.javaClass.getDeclaredField("providerFactory")
            field.isAccessible = true
            field.set(DimensionManager.OVERWORLD, bf)
            CustomizedWorldGenerator.instance.logger.info("overrided DimentionManager.OVERWORLD")
        } catch(e: Exception) {
            throw Exception("Unexpected error while hook into DimensionManager.OVERWORLD")
        }
    }
}
