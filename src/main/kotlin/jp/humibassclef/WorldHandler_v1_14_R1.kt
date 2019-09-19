package jp.humibassclef

import jp.humibassclef.api.WorldHandler
import net.minecraft.server.v1_14_R1.PlayerChunkMap
import net.minecraft.server.v1_14_R1.World
import net.minecraft.server.v1_14_R1.WorldProviderNormal
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.WorldInitEvent
import java.lang.reflect.Field

class WorldHandler_v1_14_R1 : WorldHandler, Listener {
    init {
        Bukkit.getPluginManager().registerEvents(this, CustomizedWorldGenerator.instance)
        val factory = WorldProviderOverrider.Companion.Factory()
    }

    @EventHandler
    fun onWorldInitEvent(event: WorldInitEvent) {
        // checking if the Bukkit world is an instance of CraftWorld, if not return
        if (event.world !is CraftWorld) {
            CustomizedWorldGenerator.instance.logger.info("can't hook into world: [${event.world.name}], because World is not an instance of CraftWorld")
            return
        }
        val world = event.world as CraftWorld


        try {
            // worldProvider の書き換え
            val worldProviderField: Field = World::class.java.getDeclaredField("worldProvider")
            worldProviderField.isAccessible = true
            val worldProviderObject = worldProviderField.get(world.handle)

            if (worldProviderObject !is WorldProviderNormal) {
                CustomizedWorldGenerator.instance.logger.info("can't hook into WorldProviderNormal: ${world.name}")
                return
            }
            if (worldProviderObject is WorldProviderOverrider) return
            var worldProviderOverrider = WorldProviderOverrider(world.handle, worldProviderObject.dimensionManager)

            worldProviderField.set(world.handle, worldProviderOverrider)
            CustomizedWorldGenerator.instance.logger.info("overrided WorldProvider.")
        } catch (e: Exception) {
            CustomizedWorldGenerator.instance.logger.warning("Unexpected error while hook into WorldProvider: [${world.name}], send the stacktrace below to the developer")
            e.printStackTrace()
        }

        try {
            // get the playerChunkMap where the ChunkGenerator is store, that we need to override
            val playerChunkMap = world.handle.chunkProvider.playerChunkMap

            // chunkGenerator の書き換え
            val chunkGeneratorField: Field = PlayerChunkMap::class.java.getDeclaredField("chunkGenerator")
            chunkGeneratorField.isAccessible = true

            // PlayerChunkMap に新しい ChunkGenerator を登録
            chunkGeneratorField.set(playerChunkMap, world.handle.worldProvider.chunkGenerator)
            CustomizedWorldGenerator.instance.logger.info("overrided ChunkGenerator.")
        } catch (e: Exception) {
            CustomizedWorldGenerator.instance.logger.warning("Unexpected error while hook into world: [${world.name}], send the stacktrace below to the developer")
            e.printStackTrace()
        }
    }

}
