package jp.humibassclef.util

import net.minecraft.server.v1_14_R1.Blocks
import net.minecraft.server.v1_14_R1.GeneratorSettingsOverworld


class OverriderSettingsOverworld(settings: ChunkOverriderSettings) : GeneratorSettingsOverworld() {
    var option: ChunkOverriderSettings = settings

    init {
        this.s = if (option.useLavaOceans) Blocks.LAVA.blockData else Blocks.WATER.blockData
    }

    // 2層目の岩盤の高さ
    // overworld では関数が未実装
    override fun t(): Int = 0   // 0
    // 1層目の岩盤の高さ
    override fun u(): Int = 0   // 0
    // バイオームの大きさ
    override fun v(): Int = option.biomeSize.toInt()   // 4
    // 川の広さ
    override fun w() = option.riverSize.toInt()    // 4
    // fixedBiome
    override fun x() = option.fixedBiome.toInt()   // -1    // Biomes.class に ID がキーのバイーム一覧がある
}
