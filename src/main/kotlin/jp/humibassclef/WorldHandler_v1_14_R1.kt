package jp.humibassclef

import jp.humibassclef.api.WorldHandler
import jp.humibassclef.util.OverriderSettingsOverworld
import net.minecraft.server.v1_14_R1.*
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.ChunkLoadEvent
import org.bukkit.event.world.WorldLoadEvent
import java.lang.reflect.Field

class WorldHandler_v1_14_R1 : WorldHandler, Listener {
    init {
        Bukkit.getPluginManager().registerEvents(this, TestWorldGenerator.instance)
    }

    @EventHandler
    fun onWorldInitEvent(event: ChunkLoadEvent) {
        // checking if the Bukkit world is an instance of CraftWorld, if not return
        if (event.world !is CraftWorld) {
            TestWorldGenerator.instance.logger.info("can't hook into world: [${event.world.name}], because World is not an instance of CraftWorld")
            return
        }

        val world = event.world as CraftWorld

        try {
            // get the playerChunkMap where the ChunkGenerator is store, that we need to override
            val playerChunkMap = world.handle.chunkProvider.playerChunkMap

            // chunkGenerator の書き換え
            val chunkGeneratorField: Field = PlayerChunkMap::class.java.getDeclaredField("chunkGenerator")
            chunkGeneratorField.isAccessible = true
            val chunkGeneratorObject = chunkGeneratorField.get(playerChunkMap)
            if (chunkGeneratorObject is ChunkOverriderOverworld<*>) return  // 複数の書き換え防止
            if (chunkGeneratorObject !is ChunkProviderGenerate) return  // オーバーワールド以外の書き換え防止
            TestWorldGenerator.instance.logger.info("try to hook in to world [${world.name}]")
            if (chunkGeneratorObject !is ChunkGenerator<*>) {
                TestWorldGenerator.instance.logger.info("can't hook into world: ${world.name}, because object is not an instance of ChunkTaskScheduler")
                return
            }
            val chunkGenerator: ChunkGenerator<*> = chunkGeneratorObject
            val overrider = ChunkOverriderOverworld(chunkGenerator)

            // WorldChunkManager の書き換え
            val chunkManagerField: Field = ChunkGenerator::class.java.getDeclaredField("c")
            chunkManagerField.isAccessible = true
            val chunkManagerObject = chunkManagerField.get(overrider)
            if (chunkManagerObject !is WorldChunkManager) {
                TestWorldGenerator.instance.logger.info("can't hook into worldChunkManage: ${world.name}")
                return
            }
            val chunkManager: WorldChunkManager = chunkManagerObject
            var chunkOverrider = WorldChunkOverriderOverworld(chunkManager, overrider)
            chunkManagerField.set(overrider, chunkOverrider)

            // PlayerChunkMap に新しい ChunkGenerator を登録
            chunkGeneratorField.set(playerChunkMap, overrider)
        } catch (e: Exception) {
            TestWorldGenerator.instance.logger.warning("Unexpected error while hook into world: [${world.name}], send the stacktrace below to the developer")
            e.printStackTrace()
        }
    }

    @EventHandler
    fun onWorldLoad(event: WorldLoadEvent) {
        val world = event.world as CraftWorld
        val playerChunkMap = world.handle.chunkProvider.playerChunkMap
        val ChunkGeneratorField: Field = PlayerChunkMap::class.java.getDeclaredField("chunkGenerator")
        ChunkGeneratorField.isAccessible = true
        val chunkGeneratorObject = ChunkGeneratorField.get(playerChunkMap)
        if (chunkGeneratorObject is ChunkOverriderOverworld<*>) {
            TestWorldGenerator.instance.logger.info("ChunkGenerator is overloaded.")
        }

        val chunkManagerField: Field = ChunkGenerator::class.java.getDeclaredField("c")
        chunkManagerField.isAccessible = true
        val chunkManagerObject = chunkManagerField.get(chunkGeneratorObject)
        if (chunkManagerObject is WorldChunkOverriderOverworld) {
            TestWorldGenerator.instance.logger.info("WorldChunkManager is overloaded.")
        }
    }

}
