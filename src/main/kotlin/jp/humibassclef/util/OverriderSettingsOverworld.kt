package jp.humibassclef.util

import net.minecraft.server.v1_14_R1.Blocks
import net.minecraft.server.v1_14_R1.GeneratorSettingsOverworld
import net.minecraft.server.v1_14_R1.IBlockData


class OverriderSettingsOverworld(settings: ChunkProviderSettings) : GeneratorSettingsOverworld() {
    var t = 0
    var u = 0
    var v = settings.biomeSize.toInt()
    var w = settings.riverSize.toInt()
    var x = settings.fixedBiome.toInt()

    init {
        this.s = if (settings.useLavaOceans) Blocks.LAVA.blockData else Blocks.WATER.blockData
    }

    // 2層目の岩盤の高さ
    // overworld では関数が未実装
    override fun t(): Int = t   // 0
    // 1層目の岩盤の高さ
    override fun u(): Int = u   // 0
    // バイオームの大きさ
    override fun v(): Int = v   // 4
    // 川の広さ
    override fun w() = w    // 4
    // fixedBiome
    override fun x() = x   // -1    // Biomes.class に ID がキーのバイーム一覧がある
}
