package jp.humibassclef.util


// 変更場所を見つけたやつはチェックしていく
data class ChunkProviderSettings(
    var map: MutableMap<String, Any?> = mutableMapOf(
    // minecraft customize preset between 1.8 to 1.12
    /*@*/        "useCaves" to true,                       // 洞窟
    /*@add*/     "useIceburg" to true,                     // 氷山
    /*@*/        "useDungeons" to true,                    // スポナー部屋
    /*@rn*/      "useCanyons" to true,                     // 渓谷
    /*@*/        "useWaterLakes" to true,                  // 水の池
    /*@*/        "useLavaLakes" to true,                   // 溶岩池
    /*@*/        "useLavaOceans" to true,                 // 溶岩の海

    /*@add*/     "usePillagerOutpost" to true,             // 略奪隊の前哨基地
    /*@*/        "useMineShafts" to true,                  // 廃坑
    /*@rn*/      "useWoodlandMansions" to true,            // 森の館
    /*@rn*/      "useJunglePyramid" to true,               // ジャングル寺院
    /*@rn*/      "useDesertPyramid" to true,               // 砂漠ピラミッド
    /*@add*/     "useIgloo" to true,                       // かまくら
    /*@add*/     "useShipwreck" to true,                   // 沈没船
    /*@add*/     "useSwampHut" to true,                    // 魔女
    /*@*/        "useStrongholds" to true,                 // エンド要塞
    /*@*/        "useMonuments" to true,                   // 海底神殿
    /*add*/      "useNetherBridge" to true,                // ネザー要塞
    /*add*/      "useEndCity" to true,                     // エンド街
    /*add*/      "useEndIsland" to true,                   // エンド島
    /*@add*/     "useBuriedTreasure" to true,              // 埋蔵金
    /*@*/        "useVillages" to true,                    // 村

    /*@*/        "coordinateScale" to 684.4119873046875, // 1.0  <=6000 地形の鋭さ
    /*@*/        "heightScale" to 684.4119873046875,     // <=6000 高さの幅
    /*@*/        "lowerLimitScale" to 512.0,             // <=5000 スケール下限
    /*@*/        "upperLimitScale" to 512.0,             // <=5000 スケール上限

    /**/        "depthNoiseScaleX" to 200.0,            // <=2000 x方向の起伏の激しさ
    /**/        "depthNoiseScaleZ" to 200.0,            // <=2000 z方向の起伏の激しさ
    /**/        "depthNoiseScaleExponent" to 0.5,       // 0.01 <=  20 地形のでたらめさ

    /*@*/        "mainNoiseScaleX" to 80.0,              // 1.0  <=5000 地形の伸び x
    /*@*/        "mainNoiseScaleY" to 160.0,             // <=5000 y
    /*@*/        "mainNoiseScaleZ" to 80.0,              // <=5000 z

    /*@*/        "baseSize" to 8.5,                      // <=  25 基本震度
    /*@*/        "stretchY" to 12.0,                     // 0.01 <=  50 高さ伸び
    /*@*/        "biomeDepthWeight" to 1.0,              // 1.0  <=  20 バイオームの地形変化の度合い
    /*@*/        "biomeDepthOffset" to 0.0,              // 0.0  <=  20 奥行きの偏り
    /*@*/        "biomeScaleWeight" to 1.0,              // 1.0  <=  20 大きさの度合い
    /*@*/        "biomeScaleOffset" to 0.0,              // 0.0  <=  20 スケールの偏り

    /*add*/      "netherRoof" to true,                   // ネザーに屋根を作るかどうか
    /*add*/      "netherRoofLevel" to 127.0,             // 屋根の高さ

    /*@*/        "fixedBiome" to -1.0,                        //  バイオームの固定化
    /*@*/        "biomeSize" to 4.0,                          //   1 <=   8
    /*@*/        "riverSize" to 4.0,                          //   1 <=   5

    /*@*/        "seaLevel" to 63.0,                     //   1 <= 255
    /*@*/        "waterLakeChance" to 4.0,                    //   1 <= 100 地中の水の池の数
    /*@*/        "lavaLakeChance" to 80.0,                    //  10 <= 100 地中の溶岩池の数 [10未満だと例外はいて落ちる]
    /*@*/        "dungeonChance" to 8.0,                      //   1 <= 100 ダンジョンの生成数
    /*@add*/      "iceburgChance" to 16.0,                    // 16 | 200 氷山の生成数

    /*@*/        "dirtSize" to 33.0,             // 1 <= 50      生成数
    /*@*/        "dirtCount" to 10.0,            // 0 <= 40      頻度
    /*@*/        "dirtMinHeight" to 0.0,         // 0 <= 255     下限
    /*@*/        "dirtMaxHeight" to 256.0,       // 0 <= 255     上限

    /*@*/        "gravelSize" to 33.0,
    /*@*/        "gravelCount" to 8.0,
    /*@*/        "gravelMinHeight" to 0.0,
    /*@*/        "gravelMaxHeight" to 256.0,

    /*@*/        "graniteSize" to 33.0,
    /*@*/        "graniteCount" to 10.0,
    /*@*/        "graniteMinHeight" to 0.0,
    /*@*/        "graniteMaxHeight" to 80.0,

    /*@*/        "dioriteSize" to 33.0,
    /*@*/        "dioriteCount" to 10.0,
    /*@*/        "dioriteMinHeight" to 0.0,
    /*@*/        "dioriteMaxHeight" to 80.0,

    /*@*/        "andesiteSize" to 33.0,
    /*@*/        "andesiteCount" to 10.0,
    /*@*/        "andesiteMinHeight" to 0.0,
    /*@*/        "andesiteMaxHeight" to 80.0,

    /*@*/        "coalSize" to 17.0,
    /*@*/        "coalCount" to 20.0,
    /*@*/        "coalMinHeight" to 0.0,
    /*@*/        "coalMaxHeight" to 128.0,

    /*@*/        "ironSize" to 9.0,
    /*@*/        "ironCount" to 20.0,
    /*@*/        "ironMinHeight" to 0.0,
    /*@*/        "ironMaxHeight" to 64.0,

    /*@*/        "goldSize" to 9.0,
    /*@*/        "goldCount" to 2.0,
    /*@*/        "goldMinHeight" to 0.0,
    /*@*/        "goldMaxHeight" to 32.0,

    /*@*/        "redstoneSize" to 8.0,
    /*@*/        "redstoneCount" to 8.0,
    /*@*/        "redstoneMinHeight" to 0.0,
    /*@*/        "redstoneMaxHeight" to 16.0,

    /*@*/        "diamondSize" to 8.0,
    /*@*/        "diamondCount" to 1.0,
    /*@*/        "diamondMinHeight" to 0.0,
    /*@*/        "diamondMaxHeight" to 16.0,

    /*@*/        "lapisSize" to 7.0,
    /*@*/        "lapisCount" to 1.0,
    /*@*/        "lapisCenterHeight" to 16.0,
    /*@*/        "lapisSpread" to 16.0
    )
) {

    var useCaves: Boolean by map
    var useIceburg: Boolean by map
    var useDungeons: Boolean by map
    var useCanyons: Boolean by map
    var useWaterLakes: Boolean by map
    var useLavaLakes: Boolean by map
    var useLavaOceans: Boolean by map

    var usePillagerOutpost: Boolean by map
    var useMineShafts: Boolean by map
    var useWoodlandMansions: Boolean by map
    var useJunglePyramid: Boolean by map
    var useDesertPyramid: Boolean by map
    var useIgloo: Boolean by map
    var useShipwreck: Boolean by map
    var useSwampHut: Boolean by map
    var useStrongholds: Boolean by map
    var useMonuments: Boolean by map
    var useNetherBridge: Boolean by map
    var useEndCity: Boolean by map
    var useEndIsland: Boolean by map
    var useBuriedTreasure: Boolean by map
    var useVillages: Boolean by map

    var coordinateScale: Double by map
    var heightScale: Double by map
    var lowerLimitScale: Double by map
    var upperLimitScale: Double by map

    var depthNoiseScaleX: Double by map
    var depthNoiseScaleZ: Double by map
    var depthNoiseScaleExponent: Double by map

    var mainNoiseScaleX: Double by map
    var mainNoiseScaleY: Double by map
    var mainNoiseScaleZ: Double by map

    var baseSize: Double by map
    var stretchY: Double by map
    var biomeDepthWeight: Double by map
    var biomeDepthOffset: Double by map
    var biomeScaleWeight: Double by map
    var biomeScaleOffset: Double by map

    var netherRoof: Boolean by map
    var netherRoofLevel: Double by map

    var fixedBiome: Double by map
    var biomeSize: Double by map
    var riverSize: Double by map

    var seaLevel: Double by map
    var waterLakeChance: Double by map
    var lavaLakeChance: Double by map

    var dungeonChance: Double by map
    var iceburgChance: Double by map

    var dirtSize: Double by map
    var dirtCount: Double by map
    var dirtMinHeight: Double by map
    var dirtMaxHeight: Double by map

    var gravelSize: Double by map
    var gravelCount: Double by map
    var gravelMinHeight: Double by map
    var gravelMaxHeight: Double by map

    var graniteSize: Double by map
    var graniteCount: Double by map
    var graniteMinHeight: Double by map
    var graniteMaxHeight: Double by map

    var dioriteSize: Double by map
    var dioriteCount: Double by map
    var dioriteMinHeight: Double by map
    var dioriteMaxHeight: Double by map

    var andesiteSize: Double by map
    var andesiteCount: Double by map
    var andesiteMinHeight: Double by map
    var andesiteMaxHeight: Double by map

    var coalSize: Double by map
    var coalCount: Double by map
    var coalMinHeight: Double by map
    var coalMaxHeight: Double by map

    var ironSize: Double by map
    var ironCount: Double by map
    var ironMinHeight: Double by map
    var ironMaxHeight: Double by map

    var goldSize: Double by map
    var goldCount: Double by map
    var goldMinHeight: Double by map
    var goldMaxHeight: Double by map

    var redstoneSize: Double by map
    var redstoneCount: Double by map
    var redstoneMinHeight: Double by map
    var redstoneMaxHeight: Double by map

    var diamondSize: Double by map
    var diamondCount: Double by map
    var diamondMinHeight: Double by map
    var diamondMaxHeight: Double by map

    var lapisSize: Double by map
    var lapisCount: Double by map
    var lapisCenterHeight: Double by map
    var lapisSpread: Double by map
}


// 変更場所を見つけたやつはチェックしていく
data class ChunkProviderSettingsLast(
    var map: MutableMap<String, Any?> = mutableMapOf(
    // minecraft customize preset between 1.8 to 1.12
    /*@*/        "useCaves" to true,                       // 洞窟
    /*@add*/     "useIceburg" to true,                     // 氷山
    /*@*/        "useDungeons" to true,                    // スポナー部屋
    /*@rn*/      "useCanyons" to true,                     // 渓谷
    /*@*/        "useWaterLakes" to true,                  // 水の池
    /*@*/        "useLavaLakes" to true,                   // 溶岩池
    /*@*/        "useLavaOceans" to false,                 // 溶岩の海

    /*@add*/     "usePillagerOutpost" to true,             // 略奪隊の前哨基地
    /*@*/        "useMineShafts" to true,                  // 廃坑
    /*@rn*/      "useWoodlandMansions" to true,            // 森の館
    /*@rn*/      "useJunglePyramid" to true,               // ジャングル寺院
    /*@rn*/      "useDesertPyramid" to true,               // 砂漠ピラミッド
    /*@add*/     "useIgloo" to true,                       // かまくら
    /*@add*/     "useShipwreck" to true,                   // 沈没船
    /*@add*/     "useSwampHut" to true,                    // 魔女
    /*@*/        "useStrongholds" to true,                 // エンド要塞
    /*@*/        "useMonuments" to true,                   // 海底神殿
    /*add*/      "useNetherBridge" to true,                // ネザー要塞
    /*add*/      "useEndCity" to true,                     // エンド街
    /*add*/      "useEndIsland" to true,                   // エンド島
    /*@add*/     "useBuriedTreasure" to true,              // 埋蔵金
    /*@*/        "useVillages" to true,                    // 村

    /*@*/        "coordinateScale" to 342.206, // 1.0  <=6000 地形の鋭さ
    /*@*/        "heightScale" to 5475.296,     // <=6000 高さの幅
    /*@*/        "lowerLimitScale" to 476.0,             // <=5000 スケール下限
    /*@*/        "upperLimitScale" to 548.0,             // <=5000 スケール上限

    /**/        "depthNoiseScaleX" to 200.0,            // <=2000 x方向の起伏の激しさ
    /**/        "depthNoiseScaleZ" to 200.0,            // <=2000 z方向の起伏の激しさ
    /**/        "depthNoiseScaleExponent" to 1.0,       // 0.01 <=  20 地形のでたらめさ

    /*@*/        "mainNoiseScaleX" to 40.0,              // 1.0  <=5000 地形の伸び x
    /*@*/        "mainNoiseScaleY" to 120.0,             // <=5000 y
    /*@*/        "mainNoiseScaleZ" to 40.0,              // <=5000 z

    /*@*/        "baseSize" to 7.5,                      // <=  25 基本震度
    /*@*/        "stretchY" to 10.0,                     // 0.01 <=  50 高さ伸び
    /*@*/        "biomeDepthWeight" to 2.236,              // 1.0  <=  20 バイオームの地形変化の度合い
    /*@*/        "biomeDepthOffset" to 1.0,              // 0.0  <=  20 奥行きの偏り
    /*@*/        "biomeScaleWeight" to 1.414,              // 1.0  <=  20 大きさの度合い
    /*@*/        "biomeScaleOffset" to 0.5,              // 0.0  <=  20 スケールの偏り

    /*add*/      "netherRoof" to true,                   // ネザーに屋根を作るかどうか
    /*add*/      "netherRoofLevel" to 127.0,             // 屋根の高さ

    /*@*/        "fixedBiome" to -1.0,                        //  バイオームの固定化
    /*@*/        "biomeSize" to 2.0,                          //   1 <=   8
    /*@*/        "riverSize" to 3.0,                          //   1 <=   5

    /*@*/        "seaLevel" to 63.0,                     //   1 <= 255
    /*@*/        "waterLakeChance" to 2.0,                    //   1 <= 100 地中の水の池の数
    /*@*/        "lavaLakeChance" to 40.0,                    //  10 <= 100 地中の溶岩池の数 [10未満だと例外はいて落ちる]
    /*@*/        "dungeonChance" to 28.0,                      //   1 <= 100 ダンジョンの生成数
    /*@add*/      "iceburgChance" to 16.0,                    // 16 | 200 氷山の生成数

        "dirtSize" to 33.0,
        "dirtCount" to 20.0,
        "dirtMinHeight" to 64.0,
        "dirtMaxHeight" to 256.0,
        "gravelSize" to 33.0,
        "gravelCount" to 16.0,
        "gravelMinHeight" to 64.0,
        "gravelMaxHeight" to 256.0,
        "graniteSize" to 33.0,
        "graniteCount" to 20.0,
        "graniteMinHeight" to 0.0,
        "graniteMaxHeight" to 64.0,
        "dioriteSize" to 33.0,
        "dioriteCount" to 20.0,
        "dioriteMinHeight" to 0.0,
        "dioriteMaxHeight" to 64.0,
        "andesiteSize" to 33.0,
        "andesiteCount" to 20.0,
        "andesiteMinHeight" to 0.0,
        "andesiteMaxHeight" to 64.0,
        "coalSize" to 34.0,
        "coalCount" to 40.0,
        "coalMinHeight" to 0.0,
        "coalMaxHeight" to 64.0,
        "ironSize" to 18.0,
        "ironCount" to 40.0,
        "ironMinHeight" to 0.0,
        "ironMaxHeight" to 64.0,
        "goldSize" to 18.0,
        "goldCount" to 4.0,
        "goldMinHeight" to 0.0,
        "goldMaxHeight" to 64.0,
        "redstoneSize" to 16.0,
        "redstoneCount" to 16.0,
        "redstoneMinHeight" to 0.0,
        "redstoneMaxHeight" to 64.0,
        "diamondSize" to 16.0,
        "diamondCount" to 2.0,
        "diamondMinHeight" to 0.0,
        "diamondMaxHeight" to 64.0,
        "lapisSize" to 14.0,
        "lapisCount" to 2.0,
        "lapisCenterHeight" to 16.0,
        "lapisSpread" to 64.0
    )
)