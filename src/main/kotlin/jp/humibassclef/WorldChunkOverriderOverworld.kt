package jp.humibassclef

import com.google.common.collect.ImmutableList
import com.google.common.collect.Sets
import jp.humibassclef.util.OverriderSettingsOverworld
import net.minecraft.server.v1_14_R1.*
import java.util.*
import java.util.function.LongFunction

class WorldChunkOverriderOverworld(parent: WorldChunkManager, chunkGenerator: ChunkOverriderOverworld<*>):
        WorldChunkManager() {
    val c: GenLayer
    val d: GenLayer
    val e: Array<BiomeBase>
    var parent: WorldChunkManager

    init {
        this.parent = parent

        this.e = arrayOf(Biomes.OCEAN, Biomes.PLAINS, Biomes.DESERT, Biomes.MOUNTAINS, Biomes.FOREST, Biomes.TAIGA, Biomes.SWAMP, Biomes.RIVER, Biomes.FROZEN_OCEAN, Biomes.FROZEN_RIVER, Biomes.SNOWY_TUNDRA, Biomes.SNOWY_MOUNTAINS, Biomes.MUSHROOM_FIELDS, Biomes.MUSHROOM_FIELD_SHORE, Biomes.BEACH, Biomes.DESERT_HILLS, Biomes.WOODED_HILLS, Biomes.TAIGA_HILLS, Biomes.MOUNTAIN_EDGE, Biomes.JUNGLE, Biomes.JUNGLE_HILLS, Biomes.JUNGLE_EDGE, Biomes.DEEP_OCEAN, Biomes.STONE_SHORE, Biomes.SNOWY_BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DARK_FOREST, Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS, Biomes.GIANT_TREE_TAIGA, Biomes.GIANT_TREE_TAIGA_HILLS, Biomes.WOODED_MOUNTAINS, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.BADLANDS, Biomes.WOODED_BADLANDS_PLATEAU, Biomes.BADLANDS_PLATEAU, Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.COLD_OCEAN, Biomes.DEEP_WARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.SUNFLOWER_PLAINS, Biomes.DESERT_LAKES, Biomes.GRAVELLY_MOUNTAINS, Biomes.FLOWER_FOREST, Biomes.TAIGA_MOUNTAINS, Biomes.SWAMP_HILLS, Biomes.ICE_SPIKES, Biomes.MODIFIED_JUNGLE, Biomes.MODIFIED_JUNGLE_EDGE, Biomes.TALL_BIRCH_FOREST, Biomes.TALL_BIRCH_HILLS, Biomes.DARK_FOREST_HILLS, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.GIANT_SPRUCE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA_HILLS, Biomes.MODIFIED_GRAVELLY_MOUNTAINS, Biomes.SHATTERED_SAVANNA, Biomes.SHATTERED_SAVANNA_PLATEAU, Biomes.ERODED_BADLANDS, Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, Biomes.MODIFIED_BADLANDS_PLATEAU)
        val var1: WorldData = chunkGenerator.world.worldData                // biomeLayoutOverworldConfiguration.a()
        val var2 = chunkGenerator.settings as OverriderSettingsOverworld    // biomeLayoutOverworldConfiguration.b()
        val var3: Array<GenLayer> = GenLayers.a(var1.seed, var1.type, var2)
        this.c = var3[0]
        this.d = var3[1]
    }

    override fun a(): MutableList<BiomeBase> {
        return this.e.toMutableList()
    }

    override fun getBiome(var0: BlockPosition): BiomeBase {
        return this.getBiome(var0.x, var0.z)
    }

    override fun getBiome(var0: Int, var1: Int): BiomeBase {
        return this.d.a(var0, var1)
    }

    override fun getBiomeBlock(var0: Int, var1: Int, var2: Int, var3: Int): Array<BiomeBase> {
        return this.a(var0, var1, var2, var3, true)
    }

    override fun b(var0: Int, var1: Int): BiomeBase {
        return this.c.a(var0, var1)
    }

    override fun a(var0: Int, var1: Int, var2: Int, var3: Int, var4: Boolean): Array<BiomeBase> {
        return this.d.a(var0, var1, var2, var3)
    }

    override fun a(var0: Int, var1: Int, var2: Int): Set<BiomeBase> {
        val var3 = var0 - var2 shr 2
        val var4 = var1 - var2 shr 2
        val var5 = var0 + var2 shr 2
        val var6 = var1 + var2 shr 2
        val var7 = var5 - var3 + 1
        val var8 = var6 - var4 + 1
        val var9 = Sets.newHashSet<BiomeBase>()
        Collections.addAll(var9, *this.c.a(var3, var4, var7, var8))
        return var9
    }

    override fun a(var0: Int, var1: Int, var2: Int, var3: List<BiomeBase>, var4: Random): BlockPosition? {
        val var5 = var0 - var2 shr 2
        val var6 = var1 - var2 shr 2
        val var7 = var0 + var2 shr 2
        val var8 = var1 + var2 shr 2
        val var9 = var7 - var5 + 1
        val var10 = var8 - var6 + 1
        val var11 = this.c.a(var5, var6, var9, var10)
        var var12: BlockPosition? = null
        var var13 = 0

        for (var14 in 0 until var9 * var10) {
            val var15 = var5 + var14 % var9 shl 2
            val var16 = var6 + var14 / var9 shl 2
            if (var3.contains(var11[var14])) {
                if (var12 == null || var4.nextInt(var13 + 1) == 0) {
                    var12 = BlockPosition(var15, 0, var16)
                }

                ++var13
            }
        }

        return var12
    }

    override fun a(var0: StructureGenerator<*>): Boolean {
        return this.a.computeIfAbsent(var0) {
            val var2 = this.e
            val var3 = var2.size

            for (var4 in 0 until var3) {
                val var4x = var2[var4]
                if (var4x.a(it)) {
                    return@computeIfAbsent true
                }
            }

            return@computeIfAbsent false
        }
    }

    override fun b(): Set<IBlockData> {
        if (this.b.isEmpty()) {
            val var1: Array<BiomeBase> = this.e
            val var2 = var1.size

            var var3 = 0
            while(var3 < var2) {
                val bb: BiomeBase = var1[var3]
                this.b.add(bb.q().a())
                ++var3
            }
        }

        return this.b
    }

}
