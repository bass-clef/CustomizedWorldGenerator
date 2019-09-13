package jp.humibassclef

import com.google.common.collect.Sets
import jp.humibassclef.api.OreConfig
import jp.humibassclef.api.OreGenerator
import jp.humibassclef.api.OreSetting
import net.minecraft.server.v1_14_R1.*
import org.bukkit.World
import org.bukkit.block.Biome
import org.bukkit.craftbukkit.v1_14_R1.CraftChunk
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld
import org.bukkit.craftbukkit.v1_14_R1.util.CraftMagicNumbers
import java.util.*


class MinableGenerator_v1_14_R1 : OreGenerator {

    private val generator = WorldGenMinable(null)
    private val neededOreSettings = Sets.newHashSet(OreSetting.VEIN_SIZE, OreSetting.VEINS_PER_CHUNK, OreSetting.HEIGHT_RANGE, OreSetting.MINIMUM_HEIGHT)
    override fun getNeededOreSettings(): Set<OreSetting> {
        return neededOreSettings
    }
    private val name = "vanilla_minable_generator"
    override fun getName(): String {
        return name
    }

    /*
     * 何故か定義できないのでラムダを関数にする
    val blocks = { value: IBlockData? ->
        if (value == null) {
            return false
        } else {
            val block = value.getBlock()
            return block === Blocks.STONE || block === Blocks.GRANITE || block === Blocks.DIORITE || block === Blocks.ANDESITE || block === Blocks.END_STONE || block === Blocks.NETHERRACK
        }
    }
    */
    fun blocks(value: IBlockData): Boolean {
        if (null == value) {
            return false
        }
        val block = value.block
        return block === Blocks.STONE || block === Blocks.GRANITE || block === Blocks.DIORITE || block === Blocks.ANDESITE || block === Blocks.END_STONE || block === Blocks.NETHERRACK
    }

    init {
        TestWorldGenerator.getService().setDefaultOreGenerator(this)
    }

    @SuppressWarnings("Duplicates")
    override fun generate(config: OreConfig, world: World, x2: Int, z2: Int, random: Random, biome: Biome) {
        TestWorldGenerator.instance.logger.info("[${config}] ---")
        val veinSize = config.getValue(OreSetting.VEIN_SIZE).orElse(OreSetting.VEIN_SIZE.getSaveValue())
        val veinsPerChunk = config.getValue(OreSetting.VEINS_PER_CHUNK).orElse(OreSetting.VEINS_PER_CHUNK.getSaveValue())
        val heightRange = config.getValue(OreSetting.HEIGHT_RANGE).orElse(OreSetting.HEIGHT_RANGE.getSaveValue())
        val minimumHeight = config.getValue(OreSetting.MINIMUM_HEIGHT).orElse(OreSetting.MINIMUM_HEIGHT.getSaveValue())

        val craftWorld = world as CraftWorld
        val craftChunk = world.getChunkAt(x2, z2) as CraftChunk

        if (!craftChunk.handle.heightMap.containsKey(HeightMap.Type.OCEAN_FLOOR_WG))
            craftChunk.handle.heightMap[HeightMap.Type.OCEAN_FLOOR_WG] = HeightMapOverrider(craftChunk.handle, HeightMap.Type.OCEAN_FLOOR_WG)

        for (trys in 0 until veinsPerChunk) {
            val x = random.nextInt(15)
            val y = random.nextInt(heightRange) + minimumHeight
            val z = random.nextInt(15)

            val position = BlockPosition(x + (x2 shl 4), y, z + (z2 shl 4))

            if (biome == null || craftChunk.getBlock(x, y, z).biome == biome) {
                generator.a(craftWorld.handle, craftWorld.handle.chunkProvider.getChunkGenerator(), random, position, WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NATURAL_STONE, CraftMagicNumbers.getBlock(config.getMaterial()).blockData, veinSize))// TODO find way for custom Blocks
            }
        }
    }

    fun generate(config: OreConfig, world: World, access: RegionLimitedWorldAccess, random: Random, biome: Biome?) {
        this.generate(config, world, access.a(), access.b(), random, biome!!)
//
//        val veinSize = config.getValue(OreSetting.VEIN_SIZE).orElse(OreSetting.VEIN_SIZE.getSaveValue())
//        val veinsPerChunk = config.getValue(OreSetting.VEINS_PER_CHUNK).orElse(OreSetting.VEINS_PER_CHUNK.getSaveValue())
//        val heightRange = config.getValue(OreSetting.HEIGHT_RANGE).orElse(OreSetting.HEIGHT_RANGE.getSaveValue())
//        val minimumHeight = config.getValue(OreSetting.MINIMUM_HEIGHT).orElse(OreSetting.MINIMUM_HEIGHT.getSaveValue())
//
//        val craftWorld = world as CraftWorld
//
//        for (trys in 0 until veinsPerChunk) {
//            val x = random.nextInt(15)
//            val y = random.nextInt(heightRange) + minimumHeight
//            val z = random.nextInt(15)
//
//            val position = BlockPosition(x + (access.a() shl 4), y, z + (access.b() shl 4))
//
//            if (biome == null || biome.toString().equals(IRegistry.BIOME.getKey(access.getBiome(position))!!.key, ignoreCase = true)) {
//                generator.a(access, craftWorld.handle.chunkProvider.getChunkGenerator(), random, position, WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NATURAL_STONE, CraftMagicNumbers.getBlock(config.getMaterial()).blockData, veinSize))
//            }
//        }
    }

    private inner class HeightMapOverrider(iChunkAccess: IChunkAccess, type: HeightMap.Type) : HeightMap(iChunkAccess, type) {

        override fun a(x: Int, z: Int): Int {
            val y = super.a(x, z)

            return if (y == 0) 128 else y
        }
    }

}
