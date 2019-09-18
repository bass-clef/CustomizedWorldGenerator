package jp.humibassclef

import jp.humibassclef.util.OverriderSettingsOverworld
import net.minecraft.server.v1_14_R1.*
import net.minecraft.server.v1_14_R1.BlockPosition
import net.minecraft.server.v1_14_R1.HeightMap.Type
import net.minecraft.server.v1_14_R1.WorldGenStage.Decoration
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.objects.ObjectArrayList
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.objects.ObjectList
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

class ChunkOverriderOverworld(generatoraccess: GeneratorAccess, worldchunkmanager: WorldChunkManager, overriderSettingsOverworld: OverriderSettingsOverworld):
    ChunkGenerator<OverriderSettingsOverworld>(generatoraccess, worldchunkmanager, overriderSettingsOverworld) {
    // from ChunkGenerateAbstract.class
    protected val e: SeededRandom
    val cga_j: Int
    val cga_k: Int
    val cga_l: Int
    val cga_m: Int
    val cga_n: Int
    val cga_o: NoiseGeneratorOctaves
    val cga_p: NoiseGeneratorOctaves
    val cga_q: NoiseGeneratorOctaves
    val cga_r: NoiseGenerator
    protected val f: IBlockData
    protected val g: IBlockData
    // from ChunkProviderGenerate
    val k = MobSpawnerPhantom()
    val l = MobSpawnerPatrol()
    val m = MobSpawnerCat()
    val cpg_i: NoiseGeneratorOctaves
    val cpg_j: Boolean


    companion object {
        // from ChunkProviderGenerate.class
        val cpg_h = SystemUtils.a(FloatArray(25), { afloat ->
            for (i in -2..2) {
                for (j in -2..2) {
                    val f = 10.0f / MathHelper.c((i * i + j * j).toFloat() + 0.2f)
                    afloat[i + 2 + (j + 2) * 5] = f
                }
            }

        }) as FloatArray
        // from ChunkGeneratorAbstract.class
        val cga_i: IBlockData = Blocks.AIR.getBlockData()
        val cga_h = SystemUtils.a(FloatArray(13824), { afloat ->
            for (i in 0..23) {
                for (j in 0..23) {
                    for (k in 0..23) {
                        afloat[i * 24 * 24 + j * 24 + k] = cga_b(j - 12, k - 12, i - 12).toFloat()
                    }
                }
            }

        }) as FloatArray
        // from ChunkGeneratorAbstract.class
        private fun cga_a(i: Int, j: Int, k: Int): Double {
            val l = i + 12
            val i1 = j + 12
            val j1 = k + 12

            return if (l >= 0 && l < 24)
                if (i1 >= 0 && i1 < 24)
                    if (j1 >= 0 && j1 < 24) cga_h[j1 * 24 * 24 + l * 24 + i1].toDouble()
                    else 0.0
                else 0.0
            else 0.0
        }
        private fun cga_b(i: Int, j: Int, k: Int): Double {
            val d0 = (i * i + k * k).toDouble()
            val d1 = j.toDouble() + 0.5
            val d2 = d1 * d1
            val d3 = Math.pow(2.718281828459045, -(d2 / 16.0 + d0 / 16.0))
            val d4 = -d1 * MathHelper.i(d2 / 2.0 + d0 / 2.0) / 2.0
            return d4 * d3
        }
    }

    init {
        // from ChunkGenerateAbstract.class
        val i = 4
        val j = 8
        val k = 256
        val flag = true

        this.cga_j = j
        this.cga_k = i
        this.f = overriderSettingsOverworld.r()
        this.g = overriderSettingsOverworld.s()
        this.cga_l = 16 / this.cga_k
        this.cga_m = k / this.cga_j
        this.cga_n = 16 / this.cga_k
        this.e = SeededRandom(this.seed)
        this.cga_o = NoiseGeneratorOctaves(this.e, 16)
        this.cga_p = NoiseGeneratorOctaves(this.e, 16)
        this.cga_q = NoiseGeneratorOctaves(this.e, 8)
        this.cga_r = if (flag) NoiseGenerator3(this.e, 4) else NoiseGeneratorOctaves(this.e, 4)
        // ChunkProviderGenerate.class
        this.e.a(2620)
        this.cpg_i = NoiseGeneratorOctaves(this.e, 16)
        this.cpg_j = generatoraccess.getWorldData().getType() === WorldType.AMPLIFIED
    }

    // ここでの seaLevel は洞窟の生成をどこの高さまでするかの値 らしい
    // すくなくとも洞窟につながってるスポナーはここでない
    override fun doCarving(ichunkaccess: IChunkAccess, worldgenstage_features: WorldGenStage.Features) {
        val seededrandom = SeededRandom()
        val flag = true
        val chunkcoordintpair = ichunkaccess.pos
        val i = chunkcoordintpair.x
        val j = chunkcoordintpair.z
        val bitset = ichunkaccess.a(worldgenstage_features)

        for (k in i - 8..i + 8) {
            for (l in j - 8..j + 8) {
                val list = this.getCarvingBiome(ichunkaccess).a(worldgenstage_features)
                val listiterator = list.listIterator()

                doCarvingLoop@ while (listiterator.hasNext()) {
                    val i1 = listiterator.nextIndex()
                    seededrandom.c(this.seed + i1.toLong(), k, l)
                    val worldgencarverwrapper = listiterator.next() as WorldGenCarverWrapper<*>
                    val b: Boolean = worldgencarverwrapper.a(seededrandom, k, l)

                    // TODO:CHECKPOINT!
                    when(worldgencarverwrapper.a) {
                        is WorldGenCaves, is WorldGenCavesHell, is WorldGenCavesOcean ->
                            if (!this.settings.option.useCaves) continue@doCarvingLoop
                        is WorldGenCanyon, is WorldGenCanyonOcean ->
                            if (!this.settings.option.useCanyons) continue@doCarvingLoop
                    }

                    if (b) {
                        worldgencarverwrapper.a(ichunkaccess, seededrandom, this.seaLevel, k, l, i, j, bitset)
                    }
                }
            }
        }
    }

    // 構造物(村,廃坑,etc...),地中湖,溶岩池,草木,鉱石 などの生成
    // 渓谷,洞窟の生成は関わってない...
    override fun addDecorations(regionLimitedWorldAccess: RegionLimitedWorldAccess) {
//        parent.addDecorations(regionLimitedWorldAccess)
        // from ChunkGenerator.class
        val i = regionLimitedWorldAccess.a()
        val j = regionLimitedWorldAccess.b()
        val k = i * 16
        val l = j * 16
        val blockposition = BlockPosition(k, 0, l)
        val biomebase = this.getDecoratingBiome(regionLimitedWorldAccess, blockposition.b(8, 8, 8))
        val seededrandom = SeededRandom()
        val i1 = seededrandom.a(regionLimitedWorldAccess.getSeed(), k, l)
        val aworldgenstage_decoration = Decoration.values()
        val j1 = aworldgenstage_decoration.size

        for (k1 in 0 until j1) {
            val worldgenstage_decoration = aworldgenstage_decoration[k1]
//            biomebase.a(worldgenstage_decoration, this as ChunkGenerator<*>, regionLimitedWorldAccess, i1, seededrandom, blockposition)
            this.a(biomebase, worldgenstage_decoration, this as ChunkGenerator<*>, regionLimitedWorldAccess, i1, seededrandom, blockposition)
        }
    }
    // from BiomeBase.class
    fun a(biomebase: BiomeBase, var0: Decoration, var1: ChunkGenerator<*>, var2: GeneratorAccess, var3: Long, var5: SeededRandom, var6: BlockPosition) {
        var var7 = 0
        val var9 = biomebase.a(var0).toList().iterator()

        biomeGenerateLoop@ while(var9.hasNext()) {
            val var9 = var9.next() as WorldGenFeatureConfigured<*>

            var5.b(var3, var7, var0.ordinal)
            if ( !generateDecoration(var2, var1, var5, var6, var9) )
                // default call
                var9.a(var2, var1, var5, var6)
            ++var7
        }
    }
    // original
    fun generateDecoration(
            access: GeneratorAccess, generator: ChunkGenerator<*>, seededRandom: SeededRandom, pos: BlockPosition,
            worldGenFeatureConfigured: WorldGenFeatureConfigured<*>
    ): Boolean {
        val genFeatureConfigured = worldGenFeatureConfigured.b as WorldGenFeatureCompositeConfiguration
        val featureConfigured = genFeatureConfigured.a as WorldGenFeatureConfigured
        val decoratorConfigured = genFeatureConfigured.b as WorldGenDecoratorConfigured<*>
        val feature = IRegistry.FEATURE[IRegistry.FEATURE.getKey(featureConfigured.a)]
        val decorator = IRegistry.DECORATOR[IRegistry.DECORATOR.getKey(decoratorConfigured.a)]
//        TestWorldGenerator.instance.logger.info("gen:[${feature}] dec:[${decorator}]")

        // TODO:CHECKPOINT!
        when(feature) {
            is WorldGenFeaturePillagerOutpost -> if (!this.settings.option.usePillagerOutpost) return true
            is WorldGenMineshaft -> if (!this.settings.option.useMineShafts) return true
            is WorldGenWoodlandMansion -> if (!this.settings.option.useWoodlandMansions) return true
            is WorldGenFeatureJunglePyramid -> if (!this.settings.option.useJunglePyramid) return true
            is WorldGenFeatureDesertPyramid -> if (!this.settings.option.useDesertPyramid) return true
            is WorldGenFeatureIgloo -> if (!this.settings.option.useIgloo) return true
            is WorldGenFeatureShipwreck -> if (!this.settings.option.useShipwreck) return true
            is WorldGenFeatureSwampHut -> if (!this.settings.option.useSwampHut) return true
            is WorldGenStronghold -> if (!this.settings.option.useStrongholds) return true
            is WorldGenMonument -> if (!this.settings.option.useMonuments) return true
//                is WorldGenNether ->       if (!this.settings.option.useNetherBridge) return true
//                is WorldGenEndCity ->       if (!this.settings.option.useEndCity) return true
            is WorldGenBuriedTreasure -> if (!this.settings.option.useBuriedTreasure) return true
            is WorldGenVillage -> if (!this.settings.option.useVillages) return true
        }

        // TODO:CHECKPOINT!
        when(decorator) {
            // pos + vec(12, 0, 12) の位置に生成される
            is WorldGenDecoratorHeightAverage -> {
                // ラピスラズリ
                decoratorGenerate(access, generator, seededRandom, pos,
                        worldGenFeatureConfigured, decorator,
                        getDecoratorOreConfiguration(featureConfigured, decoratorConfigured) as WorldGenDecoratorHeightAverageConfiguration
                        )
                return true
            }
            is WorldGenDecoratorNetherHeight -> {
                // その他の鉱石
                decoratorGenerate(access, generator, seededRandom, pos,
                        worldGenFeatureConfigured, decorator,
                        getDecoratorOreConfiguration(featureConfigured, decoratorConfigured) as WorldGenFeatureChanceDecoratorCountConfiguration
                        )
                return true
            }
            is WorldGenDecoratorLakeWater -> {
                if (!this.settings.option.useWaterLakes) return true
                decoratorGenerate(access, generator, seededRandom, pos,
                        worldGenFeatureConfigured, decorator, WorldGenDecoratorLakeChanceConfiguration(this.settings.option.waterLakeChance.toInt()))
                return true
            }
            is WorldGenDecoratorLakeLava -> {
                if (!this.settings.option.useLavaLakes) return true
                decoratorGenerate(access, generator, seededRandom, pos,
                        worldGenFeatureConfigured, decorator, WorldGenDecoratorLakeChanceConfiguration(this.settings.option.lavaLakeChance.toInt()))
                return true
            }
            is WorldGenDecoratorDungeon -> {
                if (!this.settings.option.useDungeons) return true
                decoratorGenerate(access, generator, seededRandom, pos,
                        worldGenFeatureConfigured, decorator, WorldGenDecoratorDungeonConfiguration(this.settings.option.dungeonChance.toInt()))
                return true
            }
            is WorldGenDecoratorIceburg -> {
                if (!this.settings.option.useIceburg) return true
//                decoratorGenerate(access, generator, seededRandom, pos,
//                        worldGenFeatureConfigured, decorator, decoratorConfigured.b as WorldGenDecoratorChanceConfiguration)
//                return true
            }
//                    is WorldGenDecoratorEndIsland ->
//                        if (!this.settings.option.useEndIsland) return true
        }
        return false
    }
    // original
    fun getDecoratorOreConfiguration(featureConfigured: WorldGenFeatureConfigured<*>, decoratorConfigured: WorldGenDecoratorConfigured<*>)
            : WorldGenFeatureDecoratorConfiguration {
        val feaOreConfiguration = featureConfigured.b as WorldGenFeatureOreConfiguration
        val decOreConfiguration: WorldGenFeatureDecoratorConfiguration
        // TODO:CHECKPOINT!
        when(feaOreConfiguration.c.block) {
            Blocks.DIRT ->
                decOreConfiguration = WorldGenFeatureChanceDecoratorCountConfiguration(
                        this.settings.option.dirtCount.toInt(), this.settings.option.dirtMinHeight.toInt(), 0, this.settings.option.dirtMaxHeight.toInt())
            Blocks.GRAVEL ->
                decOreConfiguration = WorldGenFeatureChanceDecoratorCountConfiguration(
                        this.settings.option.gravelCount.toInt(), this.settings.option.gravelMinHeight.toInt(), 0, this.settings.option.gravelMaxHeight.toInt())
            Blocks.GRANITE ->
                decOreConfiguration = WorldGenFeatureChanceDecoratorCountConfiguration(
                        this.settings.option.graniteCount.toInt(), this.settings.option.graniteMinHeight.toInt(), 0, this.settings.option.graniteMaxHeight.toInt())
            Blocks.DIORITE ->
                decOreConfiguration = WorldGenFeatureChanceDecoratorCountConfiguration(
                        this.settings.option.dioriteCount.toInt(), this.settings.option.dioriteMinHeight.toInt(), 0, this.settings.option.dioriteMaxHeight.toInt())
            Blocks.ANDESITE ->
                decOreConfiguration = WorldGenFeatureChanceDecoratorCountConfiguration(
                        this.settings.option.andesiteCount.toInt(), this.settings.option.andesiteMinHeight.toInt(), 0, this.settings.option.andesiteMaxHeight.toInt())
            Blocks.COAL_ORE ->
                decOreConfiguration = WorldGenFeatureChanceDecoratorCountConfiguration(
                        this.settings.option.coalCount.toInt(), this.settings.option.coalMinHeight.toInt(), 0, this.settings.option.coalMaxHeight.toInt())
            Blocks.IRON_ORE ->
                decOreConfiguration = WorldGenFeatureChanceDecoratorCountConfiguration(
                        this.settings.option.ironCount.toInt(), this.settings.option.ironMinHeight.toInt(), 0, this.settings.option.ironMaxHeight.toInt())
            Blocks.GOLD_ORE ->
                decOreConfiguration = WorldGenFeatureChanceDecoratorCountConfiguration(
                        this.settings.option.goldCount.toInt(), this.settings.option.goldMinHeight.toInt(), 0, this.settings.option.goldMaxHeight.toInt())
            Blocks.REDSTONE_ORE ->
                decOreConfiguration = WorldGenFeatureChanceDecoratorCountConfiguration(
                        this.settings.option.redstoneCount.toInt(), this.settings.option.redstoneMinHeight.toInt(), 0, this.settings.option.redstoneMaxHeight.toInt())
            Blocks.DIAMOND_ORE ->
                decOreConfiguration = WorldGenFeatureChanceDecoratorCountConfiguration(
                        this.settings.option.diamondCount.toInt(), this.settings.option.diamondMinHeight.toInt(), 0, this.settings.option.diamondMaxHeight.toInt())
            Blocks.LAPIS_ORE ->
                decOreConfiguration = WorldGenDecoratorHeightAverageConfiguration(
                        this.settings.option.lapisCount.toInt(), this.settings.option.lapisCenterHeight.toInt(), this.settings.option.lapisSpread.toInt())
//            Blocks.DIRT, Blocks.GRAVEL, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.COAL_ORE, Blocks.IRON_ORE, Blocks.GOLD_ORE, Blocks.REDSTONE_ORE, Blocks.DIAMOND_ORE -> {
//                decOreConfiguration = decoratorConfigured.b
//                val cfg = decoratorConfigured.b as WorldGenFeatureChanceDecoratorCountConfiguration
//                TestWorldGenerator.instance.logger.info("ore[${feaOreConfiguration.c}] count:[${cfg.a}}] btm:[${cfg.b}] top:[${cfg.c}] miximum:[${cfg.d}]")
//            }
            else -> decOreConfiguration = decoratorConfigured.b
        }
        return decOreConfiguration
    }
    // from WorldGenDecorator.class changed
    fun <DCCFG:WorldGenFeatureDecoratorConfiguration, DC:WorldGenDecorator<DCCFG> > decoratorGenerate(
            access: GeneratorAccess, generator: ChunkGenerator<*>, seededRandom: SeededRandom, pos: BlockPosition,
            worldGenFeatureConfigured: WorldGenFeatureConfigured<*>, decorator: DC, configuration: DCCFG
    ) {
        if (worldGenFeatureConfigured is WorldGenFeatureDecoratorEmptyConfiguration) return
        val _var5 = seededRandom as Random
        val _var6 = AtomicBoolean(false)
        // ここで値によって座標の配列を生成されて返される
//        TestWorldGenerator.instance.logger.info("for [${pos}] a:[${worldGenFeatureConfigured.a}] b:[${worldGenFeatureConfigured.b}]")
        forLoop@ for(it in decorator.a(access, generator, _var5, configuration, pos)) {
            // ここで生成,生成できたら　true を返して保存
            var var6x = false
            when((worldGenFeatureConfigured.b as WorldGenFeatureCompositeConfiguration).a.a) {
                is WorldGenMinable -> {
                    if (_var6.get()) break@forLoop
//                    var6x = worldGenFeatureConfigured.a(access, generator, _var5, it)
                    val wgfoc = (worldGenFeatureConfigured.b as WorldGenFeatureCompositeConfiguration).a.b as WorldGenFeatureOreConfiguration
                    var6x = this.wgm_a(
                            access, generator, _var5, it,
                            wgfoc, getOreSize(wgfoc)
                    )
                }
                is WorldGenLakes -> {
                    // 地中の池の場合だけ else{} の呼び方ではエラーがでるので持ってきたらエラーがなくなった
                    // キャストの問題か下記のメソッドにたどり着くまでに何かが消失してるため ぬるぽ がでる
                    // TODO:CHECKPOINT!
                    var6x = this.wgl_a(
                            access, generator, _var5, it,
                            (worldGenFeatureConfigured.b as WorldGenFeatureCompositeConfiguration).a.b as WorldGenFeatureLakeConfiguration
                    )
                }
                else -> {
                    var6x = worldGenFeatureConfigured.a(access, generator, _var5, it)
                }
            }

//            TestWorldGenerator.instance.logger.info("pos:[${it}] gened?:[${_var6}] |= [${var6x}]")
            _var6.set(_var6.get() || var6x)
        }

        _var6.get()
    }
    // original
    fun getOreSize(oreConfiguration: WorldGenFeatureOreConfiguration): Int {
        return when(oreConfiguration.c.block) {
            Blocks.DIRT -> this.settings.option.dirtSize.toInt()
            Blocks.GRAVEL -> this.settings.option.gravelSize.toInt()
            Blocks.GRANITE -> this.settings.option.graniteSize.toInt()
            Blocks.DIORITE -> this.settings.option.dioriteSize.toInt()
            Blocks.ANDESITE -> this.settings.option.andesiteSize.toInt()
            Blocks.COAL_ORE -> this.settings.option.coalSize.toInt()
            Blocks.IRON_ORE -> this.settings.option.ironSize.toInt()
            Blocks.GOLD_ORE -> this.settings.option.goldSize.toInt()
            Blocks.REDSTONE_ORE -> this.settings.option.redstoneSize.toInt()
            Blocks.DIAMOND_ORE -> this.settings.option.diamondSize.toInt()
            Blocks.LAPIS_ORE -> this.settings.option.lapisSize.toInt()
            else -> oreConfiguration.b
        }
    }
    // WorldGenLakes.class
    fun wgl_a(var0: GeneratorAccess, var1: ChunkGenerator<out GeneratorSettingsDefault>, var2: Random, var3: BlockPosition, var4: WorldGenFeatureLakeConfiguration): Boolean {
        var var3 = var3
        while (var3.y > 5 && var0.isEmpty(var3)) {
            var3 = var3.down()
        }

        if (var3.y <= 4) {
            return false
        } else {
            var3 = var3.down(4)
            val var5 = ChunkCoordIntPair(var3)
            if (!var0.getChunkAt(var5.x, var5.z, ChunkStatus.STRUCTURE_REFERENCES).b(WorldGenerator.VILLAGE.b()).isEmpty()) {
                return false
            } else {
                val var6 = BooleanArray(2048)
                val var7 = var2.nextInt(4) + 4
                var var8: Int
                var8 = 0
                while (var8 < var7) {
                    val var9 = var2.nextDouble() * 6.0 + 3.0
                    val var11 = var2.nextDouble() * 4.0 + 2.0
                    val var13 = var2.nextDouble() * 6.0 + 3.0
                    val var15 = var2.nextDouble() * (16.0 - var9 - 2.0) + 1.0 + var9 / 2.0
                    val var17 = var2.nextDouble() * (8.0 - var11 - 4.0) + 2.0 + var11 / 2.0
                    val var19 = var2.nextDouble() * (16.0 - var13 - 2.0) + 1.0 + var13 / 2.0

                    for (var21 in 1..14) {
                        for (var22 in 1..14) {
                            for (var23 in 1..6) {
                                val var24 = (var21.toDouble() - var15) / (var9 / 2.0)
                                val var26 = (var23.toDouble() - var17) / (var11 / 2.0)
                                val var28 = (var22.toDouble() - var19) / (var13 / 2.0)
                                val var30 = var24 * var24 + var26 * var26 + var28 * var28
                                if (var30 < 1.0) {
                                    var6[(var21 * 16 + var22) * 8 + var23] = true
                                }
                            }
                        }
                    }
                    ++var8
                }

                var var11: Boolean
                for(var8 in 0..15) {
                    for(var9 in 0..15) {
                        for(var10 in 0..7) {
                            var11 = !var6[(var8 * 16 + var9) * 8 + var10] && (var8 < 15 && var6[((var8 + 1) * 16 + var9) * 8 + var10] || var8 > 0 && var6[((var8 - 1) * 16 + var9) * 8 + var10] || var9 < 15 && var6[(var8 * 16 + var9 + 1) * 8 + var10] || var9 > 0 && var6[(var8 * 16 + (var9 - 1)) * 8 + var10] || var10 < 7 && var6[(var8 * 16 + var9) * 8 + var10 + 1] || var10 > 0 && var6[(var8 * 16 + var9) * 8 + (var10 - 1)])
                            if (var11) {
                                val var12 = var0.getType(var3.b(var8, var10, var9)).material
                                if (var10 >= 4 && var12.isLiquid) {
                                    return false
                                }

                                if (var10 < 4 && !var12.isBuildable && var0.getType(var3.b(var8, var10, var9)) !== var4.a) {
                                    return false
                                }
                            }
                        }
                    }
                }

                for(var8 in 0..15) {
                    for(var9 in 0..15) {
                        for(var10 in 0..7) {
                            if (var6[(var8 * 16 + var9) * 8 + var10]) {
                                var0.setTypeAndData(var3.b(var8, var10, var9), if (var10 >= 4) Blocks.CAVE_AIR.getBlockData() else var4.a, 2)
                            }
                        }
                    }
                }

                var bp_var11: BlockPosition
                for(var8 in 0..15) {
                    for(var9 in 0..15) {
                        for(var10 in 0..7) {
                            if (var6[(var8 * 16 + var9) * 8 + var10]) {
                                bp_var11 = var3.b(var8, var10 - 1, var9)
                                if (Block.c(var0.getType(bp_var11).block) && var0.getBrightness(EnumSkyBlock.SKY, var3.b(var8, var10, var9)) > 0) {
                                    val var12 = var0.getBiome(bp_var11)
                                    if (var12.q().a().block === Blocks.MYCELIUM) {
                                        var0.setTypeAndData(bp_var11, Blocks.MYCELIUM.blockData, 2)
                                    } else {
                                        var0.setTypeAndData(bp_var11, Blocks.GRASS_BLOCK.blockData, 2)
                                    }
                                }
                            }
                        }
                    }
                }

                if (var4.a.material == Material.LAVA) {
                    for(var8 in 0..15) {
                        for(var9 in 0..15) {
                            for(var10 in 0..7) {
                                var11 = !var6[(var8 * 16 + var9) * 8 + var10] && (var8 < 15 && var6[((var8 + 1) * 16 + var9) * 8 + var10] || var8 > 0 && var6[((var8 - 1) * 16 + var9) * 8 + var10] || var9 < 15 && var6[(var8 * 16 + var9 + 1) * 8 + var10] || var9 > 0 && var6[(var8 * 16 + (var9 - 1)) * 8 + var10] || var10 < 7 && var6[(var8 * 16 + var9) * 8 + var10 + 1] || var10 > 0 && var6[(var8 * 16 + var9) * 8 + (var10 - 1)])
                                if (var11 && (var10 < 4 || var2.nextInt(2) != 0) && var0.getType(var3.b(var8, var10, var9)).material.isBuildable) {
                                    var0.setTypeAndData(var3.b(var8, var10, var9), Blocks.STONE.blockData, 2)
                                }
                            }
                        }
                    }
                }

                if (var4.a.material == Material.WATER) {
                    for(var8 in 0..15) {
                        for(var9 in 0..15) {
                            bp_var11 = var3.b(var8, 4, var9)
                            if (var0.getBiome(bp_var11).a(var0, bp_var11, false)) {
                                var0.setTypeAndData(bp_var11, Blocks.ICE.blockData, 2)
                            }
                        }
                    }
                }

                return true
            }
        }
    }
    // WorldGenMinable.class changed
    fun wgm_a(var0: GeneratorAccess, var1: ChunkGenerator<out GeneratorSettingsDefault>, var2: Random, var3: BlockPosition, var4:  WorldGenFeatureOreConfiguration, oreSize: Int): Boolean {
        val var5 = var2.nextFloat() * 3.1415927F;
        val var6 = oreSize.toDouble() / 8.0F
        val var7 = MathHelper.f((oreSize.toFloat() / 16.0F * 2.0F + 1.0F) / 2.0F)
        val var8 = (var3.getX().toFloat() + MathHelper.sin(var5) * var6).toDouble()
        val var10 = (var3.getX().toFloat() - MathHelper.sin(var5) * var6).toDouble()
        val var12 = (var3.getZ().toFloat() + MathHelper.cos(var5) * var6).toDouble()
        val var14 = (var3.getZ().toFloat() - MathHelper.cos(var5) * var6).toDouble()
//        val var16 = true
        val var17 = (var3.getY() + var2.nextInt(3) - 2).toDouble()
        val var19 = (var3.getY() + var2.nextInt(3) - 2).toDouble()
        val var21 = var3.getX() - MathHelper.f(var6) - var7;
        val var22 = var3.getY() - 2 - var7;
        val var23 = var3.getZ() - MathHelper.f(var6) - var7;
        val var24 = 2 * (MathHelper.f(var6) + var7);
        val var25 = 2 * (2 + var7);

        var var26 = var21
        while(var26 <= var21 + var24) {
            var var27 = var23
            while(var27 <= var23 + var24) {
                if (var22 <= var0.a(Type.OCEAN_FLOOR_WG, var26, var27)) {
                    return this.wgm_a(var0, var2, var4, var8, var10, var12, var14, var17, var19, var21, var22, var23, var24, var25, oreSize)
                }
                ++var27
            }
            ++var26
        }

        return false
    }
    // from WorldGenMinable.class changed
    fun wgm_a(var0: GeneratorAccess, var1: Random, var2: WorldGenFeatureOreConfiguration, var3: Double, var5: Double, var7: Double, var9: Double, var11: Double, var13: Double, var15: Int, var16: Int, var17: Int, var18: Int, var19: Int, oreSize: Int): Boolean {
        var var20 = 0
        val var21 = BitSet(var18 * var19 * var18)
        val var22 = BlockPosition.MutableBlockPosition()
        val var23 = DoubleArray(oreSize * 4)

        var var24: Int
        var var26: Double
        var var28: Double
        var var30: Double
        var var32: Double
        var24 = 0
        while (var24 < oreSize) {
            val var25 = var24.toFloat() / oreSize.toFloat()
            var26 = MathHelper.d(var25.toDouble(), var3, var5)
            var28 = MathHelper.d(var25.toDouble(), var11, var13)
            var30 = MathHelper.d(var25.toDouble(), var7, var9)
            var32 = var1.nextDouble() * oreSize.toDouble() / 16.0
            val var34 = ((MathHelper.sin(3.1415927f * var25) + 1.0f).toDouble() * var32 + 1.0) / 2.0
            var23[var24 * 4 + 0] = var26
            var23[var24 * 4 + 1] = var28
            var23[var24 * 4 + 2] = var30
            var23[var24 * 4 + 3] = var34
            ++var24
        }

        var24 = 0
        while (var24 < oreSize - 1) {
            if (var23[var24 * 4 + 3] > 0.0) {
                for (var25 in var24 + 1 until oreSize) {
                    if (var23[var25 * 4 + 3] > 0.0) {
                        var26 = var23[var24 * 4 + 0] - var23[var25 * 4 + 0]
                        var28 = var23[var24 * 4 + 1] - var23[var25 * 4 + 1]
                        var30 = var23[var24 * 4 + 2] - var23[var25 * 4 + 2]
                        var32 = var23[var24 * 4 + 3] - var23[var25 * 4 + 3]
                        if (var32 * var32 > var26 * var26 + var28 * var28 + var30 * var30) {
                            if (var32 > 0.0) {
                                var23[var25 * 4 + 3] = -1.0
                            } else {
                                var23[var24 * 4 + 3] = -1.0
                            }
                        }
                    }
                }
            }
            ++var24
        }

        var24 = 0
        while (var24 < oreSize) {
            val var25 = var23[var24 * 4 + 3]
            if (var25 >= 0.0) {
                val var27 = var23[var24 * 4 + 0]
                val var29 = var23[var24 * 4 + 1]
                val var31 = var23[var24 * 4 + 2]
                val var33 = Math.max(MathHelper.floor(var27 - var25), var15)
                val var34 = Math.max(MathHelper.floor(var29 - var25), var16)
                val var35 = Math.max(MathHelper.floor(var31 - var25), var17)
                val var36 = Math.max(MathHelper.floor(var27 + var25), var33)
                val var37 = Math.max(MathHelper.floor(var29 + var25), var34)
                val var38 = Math.max(MathHelper.floor(var31 + var25), var35)

                for (var39 in var33..var36) {
                    val var40 = (var39.toDouble() + 0.5 - var27) / var25
                    if (var40 * var40 < 1.0) {
                        for (var42 in var34..var37) {
                            val var43 = (var42.toDouble() + 0.5 - var29) / var25
                            if (var40 * var40 + var43 * var43 < 1.0) {
                                for (var45 in var35..var38) {
                                    val var46 = (var45.toDouble() + 0.5 - var31) / var25
                                    if (var40 * var40 + var43 * var43 + var46 * var46 < 1.0) {
                                        val var48 = var39 - var15 + (var42 - var16) * var18 + (var45 - var17) * var18 * var19
                                        if (!var21.get(var48)) {
                                            var21.set(var48)
                                            var22.d(var39, var42, var45)
                                            if (var2.a.b().test(var0.getType(var22))) {
                                                var0.setTypeAndData(var22, var2.c, 2)
                                                ++var20
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            ++var24
        }

        return var20 > 0
    }

    // from ChunkGeneratorAbstract.class
    protected fun g(): Double = (this.i() - 4).toDouble()
    protected fun h(): Double = 0.0
    fun i(): Int = this.cga_m + 1

    // from ChunkGeneratorAbstract.class
    override fun getBaseHeight(i: Int, j: Int, heightmap_type: Type): Int {
        val k = Math.floorDiv(i, this.cga_k)
        val l = Math.floorDiv(j, this.cga_k)
        val i1 = Math.floorMod(i, this.cga_k)
        val j1 = Math.floorMod(j, this.cga_k)
        val d0 = i1.toDouble() / this.cga_k.toDouble()
        val d1 = j1.toDouble() / this.cga_k.toDouble()
        val adouble = arrayOf(this.b(k, l), this.b(k, l + 1), this.b(k + 1, l), this.b(k + 1, l + 1))
        val k1 = this.seaLevel

        for (l1 in this.cga_m - 1 downTo 0) {
            val d2 = adouble[0][l1]
            val d3 = adouble[1][l1]
            val d4 = adouble[2][l1]
            val d5 = adouble[3][l1]
            val d6 = adouble[0][l1 + 1]
            val d7 = adouble[1][l1 + 1]
            val d8 = adouble[2][l1 + 1]
            val d9 = adouble[3][l1 + 1]

            for (i2 in this.cga_j - 1 downTo 0) {
                val d10 = i2.toDouble() / this.cga_j.toDouble()
                val d11 = MathHelper.a(d10, d0, d1, d2, d6, d4, d8, d3, d7, d5, d9)
                val j2 = l1 * this.cga_j + i2
                if (d11 > 0.0 || j2 < k1) {
                    val iblockdata: IBlockData
                    if (d11 > 0.0) {
                        iblockdata = this.f
                    } else {
                        iblockdata = this.g
                    }

                    if (heightmap_type.d().test(iblockdata)) {
                        return j2 + 1
                    }
                }
            }
        }

        return 0
    }
    protected fun b(i: Int, j: Int): DoubleArray {
        val adouble = DoubleArray(this.cga_m + 1)
        this.a(adouble, i, j)
        return adouble
    }

    // 草ブロとか境界の継ぎ目 などの生成,バイオーム特有のブロックの設置など
    override fun buildBase(ichunkaccess: IChunkAccess) {
        val chunkcoordintpair = ichunkaccess.pos
        val i = chunkcoordintpair.x
        val j = chunkcoordintpair.z
        val seededrandom = SeededRandom()
        seededrandom.a(i, j)
        val chunkcoordintpair1 = ichunkaccess.pos
        val k = chunkcoordintpair1.d()
        val l = chunkcoordintpair1.e()
        val d0 = 0.0625
        val abiomebase = ichunkaccess.biomeIndex

        for (i1 in 0..15) {
            for (j1 in 0..15) {
                val k1 = k + i1
                val l1 = l + j1
                val i2 = ichunkaccess.a(Type.WORLD_SURFACE_WG, i1, j1) + 1
                val d1 = this.cga_r.a(k1.toDouble() * 0.0625, l1.toDouble() * 0.0625, 0.0625, i1.toDouble() * 0.0625)
                abiomebase[j1 * 16 + i1].a(seededrandom, ichunkaccess, k1, l1, i2, d1, this.getSettings().r(), this.getSettings().s(), this.seaLevel, this.a.seed)
            }
        }

        this.a(ichunkaccess, seededrandom)
    }
    fun a(ichunkaccess: IChunkAccess, random: Random) {
        // from ChunkGeneratorAbstract.class
        val blockposition_mutableblockposition = BlockPosition.MutableBlockPosition()
        val i = ichunkaccess.pos.d()
        val j = ichunkaccess.pos.e()
        val t0 = this.getSettings()
        val k = t0.u()  // 岩盤1層目
        val l = t0.t()  // 2層目
        val iterator = BlockPosition.b(i, 0, j, i + 15, 0, j + 15).iterator()

        while (true) {
            var blockposition: BlockPosition
            var i1: Int
            do {
                if (!iterator.hasNext()) {
                    return
                }

                blockposition = iterator.next() as BlockPosition
                if (l > 0) {
                    i1 = l
                    while (i1 >= l - 4) {
                        if (i1 >= l - random.nextInt(5)) {
                            ichunkaccess.setType(blockposition_mutableblockposition.d(blockposition.x, i1, blockposition.z), Blocks.BEDROCK.blockData, false)
                        }
                        --i1
                    }
                }
            } while (k >= 256)

            i1 = k + 4
            while (i1 >= k) {
                if (i1 <= k + random.nextInt(5)) {
                    ichunkaccess.setType(blockposition_mutableblockposition.d(blockposition.x, i1, blockposition.z), Blocks.BEDROCK.blockData, false)
                }
                --i1
            }
        }
    }

    // バイオームによって土地の生成をする,バイオームの決定がここではない,パーリンノイズもここ
    // from ChunkGeneratorAbstract.class
    override fun buildNoise(generatorAccess: GeneratorAccess, iChunkAccess: IChunkAccess) {
        val i = this.seaLevel
        val objectlist: ObjectList<WorldGenFeaturePillagerOutpostPoolPiece> = ObjectArrayList(10)
        val objectlist1: ObjectList<WorldGenFeatureDefinedStructureJigsawJunction> = ObjectArrayList(32)
        val chunkcoordintpair = iChunkAccess.getPos()
        val j = chunkcoordintpair.x // 地形の生成x (ワールド)
        val k = chunkcoordintpair.z // .y
        val l = j shl 4     // チャンクの x
        val i1 = k shl 4    // .y
        val iterator = WorldGenerator.aQ.iterator()

        label171@ while (iterator.hasNext()) {
            val structuregenerator = iterator.next() as StructureGenerator<*>
            val s = structuregenerator.b()
            val longiterator = iChunkAccess.b(s).iterator()

            label169@ while (true) {
                var structurestart: StructureStart?
                do {
                    do {
                        if (!longiterator.hasNext()) {
                            continue@label171
                        }

                        val j1 = longiterator.nextLong()
                        val chunkcoordintpair1 = ChunkCoordIntPair(j1)
                        val ichunkaccess1 = generatorAccess.getChunkAt(chunkcoordintpair1.x, chunkcoordintpair1.z)
                        structurestart = ichunkaccess1.a(s)
                    } while (structurestart == null)
                } while (!structurestart!!.e())

                val iterator1 = structurestart!!.d().iterator()

                while (true) {
                    var structurepiece: StructurePiece
                    do {
                        do {
                            if (!iterator1.hasNext()) {
                                continue@label169
                            }

                            structurepiece = iterator1.next() as StructurePiece
                        } while (!structurepiece.a(chunkcoordintpair, 12))
                    } while (structurepiece !is WorldGenFeaturePillagerOutpostPoolPiece)

                    val worldgenfeaturepillageroutpostpoolpiece = structurepiece as WorldGenFeaturePillagerOutpostPoolPiece
                    val worldgenfeaturedefinedstructurepooltemplate_matching = worldgenfeaturepillageroutpostpoolpiece.b().c()
                    if (worldgenfeaturedefinedstructurepooltemplate_matching == WorldGenFeatureDefinedStructurePoolTemplate.Matching.RIGID) {
                        objectlist.add(worldgenfeaturepillageroutpostpoolpiece)
                    }

                    val iterator2 = worldgenfeaturepillageroutpostpoolpiece.e().iterator()

                    while (iterator2.hasNext()) {
                        val worldgenfeaturedefinedstructurejigsawjunction = iterator2.next() as WorldGenFeatureDefinedStructureJigsawJunction
                        val k1 = worldgenfeaturedefinedstructurejigsawjunction.a()
                        val l1 = worldgenfeaturedefinedstructurejigsawjunction.c()
                        if (k1 > l - 12 && l1 > i1 - 12 && k1 < l + 15 + 12 && l1 < i1 + 15 + 12) {
                            objectlist1.add(worldgenfeaturedefinedstructurejigsawjunction)
                        }
                    }
                }
            }
        }

        val adouble = Array(2) { Array(this.cga_n + 1) { DoubleArray(this.cga_m + 1) } }

        for (i2 in 0 until this.cga_n + 1) {
            adouble[0][i2] = DoubleArray(this.cga_m + 1)
            this.a(adouble[0][i2], j * this.cga_l, k * this.cga_n + i2)
            adouble[1][i2] = DoubleArray(this.cga_m + 1)
        }

        val protochunk = iChunkAccess as ProtoChunk
        val heightmap = protochunk.b(Type.OCEAN_FLOOR_WG)       // 海洋ワールド生成器
        val heightmap1 = protochunk.b(Type.WORLD_SURFACE_WG)    // 表層
        val blockposition_mutableblockposition = BlockPosition.MutableBlockPosition()
        val objectlistiterator = objectlist.iterator()
        val objectlistiterator1 = objectlist1.iterator()

        for (j2 in 0 until this.cga_l) {
            var k2: Int
            k2 = 0
            while (k2 < this.cga_n + 1) {
                this.a(adouble[1][k2], j * this.cga_l + j2 + 1, k * this.cga_n + k2)
                ++k2
            }

            k2 = 0
            while (k2 < this.cga_n) {
                var chunksection = protochunk.a(15)
                chunksection.a()

                for (l2 in this.cga_m - 1 downTo 0) {
                    val d0 = adouble[0][k2][l2]
                    val d1 = adouble[0][k2 + 1][l2]
                    val d2 = adouble[1][k2][l2]
                    val d3 = adouble[1][k2 + 1][l2]
                    val d4 = adouble[0][k2][l2 + 1]
                    val d5 = adouble[0][k2 + 1][l2 + 1]
                    val d6 = adouble[1][k2][l2 + 1]
                    val d7 = adouble[1][k2 + 1][l2 + 1]

                    for (i3 in this.cga_j - 1 downTo 0) {
                        val j3 = l2 * this.cga_j + i3
                        val k3 = j3 and 15
                        val l3 = j3 shr 4
                        if (chunksection.yPosition shr 4 != l3) {
                            chunksection.b()    // unlock?
                            chunksection = protochunk.a(l3)
                            chunksection.a()    // lock?
                        }

                        val d8 = i3.toDouble() / this.cga_j.toDouble()
                        val d9 = MathHelper.d(d8, d0, d4)
                        val d10 = MathHelper.d(d8, d2, d6)
                        val d11 = MathHelper.d(d8, d1, d5)
                        val d12 = MathHelper.d(d8, d3, d7)

                        for (i4 in 0 until this.cga_k) {
                            val j4 = l + j2 * this.cga_k + i4
                            val k4 = j4 and 15
                            val d13 = i4.toDouble() / this.cga_k.toDouble()
                            val d14 = MathHelper.d(d13, d9, d10)
                            val d15 = MathHelper.d(d13, d11, d12)

                            for (l4 in 0 until this.cga_k) {
                                val i5 = i1 + k2 * this.cga_k + l4
                                val j5 = i5 and 15
                                val d16 = l4.toDouble() / this.cga_k.toDouble()
                                val d17 = MathHelper.d(d16, d14, d15)
                                var d18 = MathHelper.a(d17 / 200.0, -1.0, 1.0)

                                var k5: Int
                                var l5: Int
                                var i6: Int
                                d18 = d18 / 2.0 - d18 * d18 * d18 / 24.0
                                while (objectlistiterator.hasNext()) {
                                    val worldgenfeaturepillageroutpostpoolpiece1 = objectlistiterator.next() as WorldGenFeaturePillagerOutpostPoolPiece
                                    val structureboundingbox = worldgenfeaturepillageroutpostpoolpiece1.g()
                                    k5 = Math.max(0, Math.max(structureboundingbox.a - j4, j4 - structureboundingbox.d))
                                    l5 = j3 - (structureboundingbox.b + worldgenfeaturepillageroutpostpoolpiece1.d())
                                    i6 = Math.max(0, Math.max(structureboundingbox.c - i5, i5 - structureboundingbox.f))
                                    d18 += cga_a(k5, l5, i6) * 0.8
                                }

                                objectlistiterator.back(objectlist.size)

                                while (objectlistiterator1.hasNext()) {
                                    val worldgenfeaturedefinedstructurejigsawjunction1 = objectlistiterator1.next() as WorldGenFeatureDefinedStructureJigsawJunction
                                    val j6 = j4 - worldgenfeaturedefinedstructurejigsawjunction1.a()
                                    k5 = j3 - worldgenfeaturedefinedstructurejigsawjunction1.b()
                                    l5 = i5 - worldgenfeaturedefinedstructurejigsawjunction1.c()
                                    d18 += cga_a(j6, k5, l5) * 0.4
                                }

                                objectlistiterator1.back(objectlist1.size)
                                val iblockdata: IBlockData
                                if (d18 > 0.0) {
                                    iblockdata = this.f     // STONE
                                } else if (j3 < i) {
                                    iblockdata = this.g     // WATER
                                } else {
                                    iblockdata = cga_i
                                }

                                if (iblockdata != cga_i) {
                                    if (iblockdata.h() != 0) {
                                        blockposition_mutableblockposition.d(j4, j3, i5)
                                        protochunk.k(blockposition_mutableblockposition)
                                    }

                                    // 設置? :ワールド座標をチャンク座標にして long 配列で DataBits クラスにて管理されてる
                                    chunksection.setType(k4, k3, j5, iblockdata, false)
                                    heightmap.a(k4, j3, j5, iblockdata)
                                    heightmap1.a(k4, j3, j5, iblockdata)
                                }
                            }
                        }
                    }
                }

                chunksection.b()
                ++k2
            }

            val adouble1 = adouble[0]
            adouble[0] = adouble[1]
            adouble[1] = adouble1
        }
    }
    // from ChunkProviderGenerator.class
    fun a(adouble: DoubleArray, i: Int, j: Int) {
        // TODO:CHECKPOINT!
        val d0 = this.settings.option.coordinateScale  //684.4119873046875  // 地形の鋭さ
        val d1 = this.settings.option.heightScale      //684.4119873046875  // 高さの幅
        val d2 = 8.555149841308594      // d0 / 80.0
        val d3 = 4.277574920654297      // d1 / 160.0
        val flag = true
        val flag1 = true
        this.a(adouble, i, j, d0, d1, d2, d3, 3, -10)
    }
    // from ChunkGeneratorAbstract.class
    fun a(adouble: DoubleArray, i: Int, j: Int, d0: Double, d1: Double, d2: Double, d3: Double, k: Int, l: Int) {
        val adouble1 = this.a(i, j)
        val d4 = adouble1[0]
        val d5 = adouble1[1]
        val d6 = this.g()
        val d7 = this.h()

        for (i1 in 0 until this.i()) {
            var d8 = this.a(i, i1, j, d0, d1, d2, d3)
            d8 -= this.a(d4, d5, i1)
            if (i1.toDouble() > d6) {
                d8 = MathHelper.b(d8, l.toDouble(), (i1.toDouble() - d6) / k.toDouble())
            } else if (i1.toDouble() < d7) {
                d8 = MathHelper.b(d8, -30.0, (d7 - i1.toDouble()) / (d7 - 1.0))
            }

            adouble[i1] = d8
        }
    }
    // from ChunkProviderGenerator.class
    fun a(d0: Double, d1: Double, i: Int): Double {
        // TODO:CHECKPOINT!
        val d2 = this.settings.option.baseSize  // 8.5D
        var d3 = (i.toDouble() - (d2 + d0 * d2 / 8.0 * 4.0)) * this.settings.option.stretchY * 128.0 / 256.0 / d1
        if (d3 < 0.0) {
            d3 *= 4.0
        }

        return d3
    }
    // from ChunkGeneratorAbstract.class
    private fun a(i: Int, j: Int, k: Int, d0: Double, d1: Double, d2: Double, d3: Double): Double {
        // d0 : coordinateScale
        // d1 : heightScale
        var d4 = 0.0    // l:16
        var d5 = 0.0    // l:16
        var d6 = 0.0    // l: 8     // this.mainNoiseRegion
        var d7 = 1.0
        val mainNoiseScaleX = d0 / this.settings.option.mainNoiseScaleX
        val mainNoiseScaleY = d1 / this.settings.option.mainNoiseScaleY
        val mainNoiseScaleZ = d0 / this.settings.option.mainNoiseScaleZ

        // version diff
        // 処理の方法が [X,Z] のみから Y.[X,Z] に変更されたっぽい
        for (l in 0..15) {
            val d8 = NoiseGeneratorOctaves.a(i.toDouble() * d0 * d7)
            val d9 = NoiseGeneratorOctaves.a(j.toDouble() * d1 * d7)
            val d10 = NoiseGeneratorOctaves.a(k.toDouble() * d0 * d7)
            val d11 = d1 * d7
            d4 += this.cga_o.a(l).a(d8, d9, d10, d11, j.toDouble() * d11) / d7  // minLimitRegion
            d5 += this.cga_p.a(l).a(d8, d9, d10, d11, j.toDouble() * d11) / d7  // maxLimitRegion
            if (l < 8) {
                // TODO:CHECKPOINT!
                d6 += this.cga_q.a(l).a(
//                        NoiseGeneratorOctaves.a(i.toDouble() * d2 * d7),
//                        NoiseGeneratorOctaves.a(j.toDouble() * d3 * d7),
//                        NoiseGeneratorOctaves.a(k.toDouble() * d2 * d7),
//                        d1 * d7,
//                        j.toDouble() * d1 * d7
                        NoiseGeneratorOctaves.a(i.toDouble() * mainNoiseScaleX * d7),
                        NoiseGeneratorOctaves.a(j.toDouble() * mainNoiseScaleY * d7),
                        NoiseGeneratorOctaves.a(k.toDouble() * mainNoiseScaleZ * d7),
                        mainNoiseScaleY * d7,
                        j.toDouble() * mainNoiseScaleY * d7
                ) / d7  // mainNoiseRegion
            }

            d7 /= 2.0
        }

        // TODO:CHECKPOINT!
//        return MathHelper.b(d4 / 512.0, d5 / 512.0, (d6 / 10.0 + 1.0) / 2.0)
        return MathHelper.b(d4 / this.settings.option.lowerLimitScale, d5 / this.settings.option.upperLimitScale, (d6 / 10.0 + 1.0) / 2.0)
    }
    // from ChunkProviderGenerator.class
    fun a(i: Int, j: Int): DoubleArray {
        val adouble = DoubleArray(2)
        var f = 0.0f
        var f1 = 0.0f
        var f2 = 0.0f
        val flag = true
        val f3 = this.c.b(i, j).g()

        for (k in -2..2) {
            for (l in -2..2) {
                val biomebase = this.c.b(i + k, j + l)
//                var f4 = biomebase.g()
//                var f5 = biomebase.k()
                // TODO:CHECKPOINT!
                var f4 = this.settings.option.biomeDepthOffset.toFloat() + biomebase.g() * this.settings.option.biomeDepthWeight.toFloat()
                var f5 = this.settings.option.biomeScaleOffset.toFloat() + biomebase.k() * this.settings.option.biomeScaleWeight.toFloat()
                if (this.cpg_j && f4 > 0.0f) {
                    // アンプリファイド設定
                    f4 = 1.0f + f4 * 2.0f
                    f5 = 1.0f + f5 * 4.0f
                }

                // changed for biomeDepthWeight
                // なぜか 1.12 にはなかったリミッター的なバランサーが設置されてるためコメントアウト
//                if (f4 < -1.8f) {
//                    f4 = -1.8f
//                }

                var f6 = cpg_h[k + 2 + (l + 2) * 5] / (f4 + 2.0f)
                if (biomebase.g() > f3) {
                    f6 /= 2.0f
                }

                f += f5 * f6
                f1 += f4 * f6
                f2 += f6
            }
        }

        f /= f2
        f1 /= f2
        f = f * 0.9f + 0.1f
        f1 = (f1 * 4.0f - 1.0f) / 8.0f
        adouble[0] = f1.toDouble() + this.c(i, j)
        adouble[1] = f.toDouble()
        return adouble
    }
    // from ChunkProviderGenerate.class
    private fun c(i: Int, j: Int): Double {
        // 1.12:ChunkGeneratorOverworld.java Line:294
        // d0 == this.depthRegion
        // TODO:CHECKPOINT!
//        var d0 = this.cpg_i.a((i * 200).toDouble(), 10.0, (j * 200).toDouble(), 1.0, 0.0, true) / 8000.0
        // depthNoiseScaleExponent なんていらなかったんや
        var d0 = this.cpg_i.a(
                (i * this.settings.option.depthNoiseScaleX),
                10.0,
                (j * this.settings.option.depthNoiseScaleZ),
                1.0, 0.0, true) / 8000.0
        if (d0 < 0.0) {
            d0 = -d0 * 0.3
        }

        d0 = d0 * 3.0 - 2.0

        if (d0 < 0.0) {
            d0 /= 28.0
        } else {
            if (d0 > 1.0) {
                d0 = 1.0
            }

            d0 /= 40.0
        }

        return d0
    }

    // from ChunkProviderGenerate.class
    override fun addMobs(regionLimitedWorldAccess: RegionLimitedWorldAccess) {
        val i = regionLimitedWorldAccess.a()
        val j = regionLimitedWorldAccess.b()
        val biomebase = regionLimitedWorldAccess.getChunkAt(i, j).getBiomeIndex()[0]
        val seededrandom = SeededRandom()
        seededrandom.a(regionLimitedWorldAccess.getSeed(), i shl 4, j shl 4)
        SpawnerCreature.a(regionLimitedWorldAccess, biomebase, i, j, seededrandom)
    }
    override fun doMobSpawning(worldserver: WorldServer?, flag: Boolean, flag1: Boolean) {
        this.k.a(worldserver, flag, flag1)
        this.l.a(worldserver, flag, flag1)
        this.m.a(worldserver, flag, flag1)
    }
    override fun getMobsFor(enumcreaturetype: EnumCreatureType?, blockposition: BlockPosition?): MutableList<BiomeBase.BiomeMeta> {
        if (WorldGenerator.SWAMP_HUT.c(this.a, blockposition)) {
            if (enumcreaturetype == EnumCreatureType.MONSTER) {
                return WorldGenerator.SWAMP_HUT.e()
            }

            if (enumcreaturetype == EnumCreatureType.CREATURE) {
                return WorldGenerator.SWAMP_HUT.f()
            }
        } else if (enumcreaturetype == EnumCreatureType.MONSTER) {
            if (WorldGenerator.PILLAGER_OUTPOST.a(this.a, blockposition)) {
                return WorldGenerator.PILLAGER_OUTPOST.e()
            }

            if (WorldGenerator.OCEAN_MONUMENT.a(this.a, blockposition)) {
                return WorldGenerator.OCEAN_MONUMENT.e()
            }
        }

        return super.getMobsFor(enumcreaturetype, blockposition)
    }

    override fun getSpawnHeight(): Int = this.a.seaLevel + 1
    // TODO:CHECKPOINT!
    override fun getSeaLevel(): Int = this.settings.option.seaLevel.toInt()
    override fun getSettings(): OverriderSettingsOverworld = this.settings
}
