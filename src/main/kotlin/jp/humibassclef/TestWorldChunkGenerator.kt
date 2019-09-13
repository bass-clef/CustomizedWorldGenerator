package jp.humibassclef

import org.bukkit.generator.ChunkGenerator


class TestWorldChunkGenerator(worldName: String = "world", config: String?) : ChunkGenerator() {//ChunkGenerator() {

     val worldName: String
    var config: String?

    init {
        this.worldName = worldName
        this.config = config ?: ""

        TestWorldGenerator.instance.logger.info("generator presets:$config\n")

//        val worldLoadEvent: WorldLoadEvent = WorldLoadEvent(world)
//        WorldHandler_v1_14_R1().onWorldLoad(wle)

        /*
         * 1.8 - 1.12 までのカスタマイズワールドのプリセットの書き方をそのまま記述できるように
         *
         * un-serializer は ChunkProviderSettings に記述
         **/
        try {

        } catch (e: Exception) {
            TestWorldGenerator.instance.logger.severe("${TestWorldGenerator.name} Error Parsing.\nConfigName:\n" + e.toString())
            e.printStackTrace()
        }
    }

    /*
    override fun generateChunkData(world: World, random: Random, x: Int, z: Int, biome: BiomeGrid): ChunkData {
        return super.generateChunkData(world, random, x, z, biome)
    }
    */
}
