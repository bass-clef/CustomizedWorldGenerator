package jp.humibassclef.util


// 変更場所を見つけたやつはチェックしていく
data class ChunkProviderSettings (
        // minecraft customize preset between 1.8 to 1.12
/*@*/        var useCaves:Boolean = true,                       // 洞窟
/*@add*/     var useIceburg:Boolean = true,                     // 氷山
/*@*/        var useDungeons:Boolean = true,                    // スポナー部屋
/*@rn*/      var useCanyons:Boolean = true,                     // 渓谷
/*@*/        var useWaterLakes:Boolean = true,                  // 水の池
/*@*/        var useLavaLakes:Boolean = true,                   // 溶岩池
/*@*/        var useLavaOceans:Boolean = true,                 // 溶岩の海

/*@add*/     var usePillagerOutpost:Boolean = true,             // 略奪隊の前哨基地
/*@*/        var useMineShafts:Boolean = true,                  // 廃坑
/*@rn*/      var useWoodlandMansions:Boolean = true,            // 森の館
/*@rn*/      var useJunglePyramid:Boolean = true,               // ジャングル寺院
/*@rn*/      var useDesertPyramid:Boolean = true,               // 砂漠ピラミッド
/*@add*/     var useIgloo:Boolean = true,                       // かまくら
/*@add*/     var useShipwreck:Boolean = true,                   // 沈没船
/*@add*/     var useSwampHut:Boolean = true,                    // 魔女
/*@*/        var useStrongholds:Boolean = true,                 // エンド要塞
/*@*/        var useMonuments:Boolean = true,                   // 海底神殿
/*add*/      var useNetherBridge:Boolean = true,                // ネザー要塞
/*add*/      var useEndCity:Boolean = true,                     // エンド街
/*add*/      var useEndIsland:Boolean = true,                   // エンド島
/*@add*/     var useBuriedTreasure:Boolean = true,              // 埋蔵金
/*@*/        var useVillages:Boolean = true,                    // 村
                
/*@*/        var coordinateScale:Double = 684.412,           // 1.0  <=6000 地形の鋭さ
/*@*/        var heightScale:Double = 684.412,               // <=6000 高さの幅
/*@*/        var lowerLimitScale:Double = 512.0,             // <=5000 スケール下限
/*@*/        var upperLimitScale:Double = 512.0,             // <=5000 スケール上限

/**/        var depthNoiseScaleX:Double = 200.0,            // <=2000 x方向の起伏の激しさ
/**/        var depthNoiseScaleZ:Double = 200.0,            // <=2000 z方向の起伏の激しさ
/**/        var depthNoiseScaleExponent:Double = 0.5,       // 0.01 <=  20 地形のでたらめさ

/*@*/        var mainNoiseScaleX:Double = 80.0,              // 1.0  <=5000 地形の伸び x
/*@*/        var mainNoiseScaleY:Double = 160.0,             // <=5000 y
/*@*/        var mainNoiseScaleZ:Double = 80.0,              // <=5000 z

/*@*/        var baseSize:Double = 8.5,                      // <=  25 基本震度
/*@*/        var stretchY:Double = 12.0,                     // 0.01 <=  50 高さ伸び
/*@*/        var biomeDepthWeight:Double = 1.0,              // 1.0  <=  20 バイオームの地形変化の度合い
/*@*/        var biomeDepthOffset:Double = 0.0,              // 0.0  <=  20 奥行きの偏り
/*@*/        var biomeScaleWeight:Double = 1.0,              // 1.0  <=  20 大きさの度合い
/*@*/        var biomeScaleOffset:Double = 0.0,              // 0.0  <=  20 スケールの偏り

//add        var netherRoof:Boolean = true,                // ネザーに屋根を作るかどうか
//add        var netherRoofLevel:Double = 127.0,                  // 屋根の高さ

/*@*/        var fixedBiome:Double = -1.0,                        //  バイオームの固定化
/*@*/        var biomeSize:Double = 4.0,                          //   1 <=   8
/*@*/        var riverSize:Double = 4.0,                          //   1 <=   5

/*@*/        var seaLevel:Double = 63.0,                     //   1 <= 255
/*@*/        var waterLakeChance:Double = 4.0,                    //   1 <= 100 地中の水の池の数
/*@*/        var lavaLakeChance:Double = 80.0,                    //  10 <= 100 地中の溶岩池の数 [10未満だと例外はいて落ちる]
/*@*/        var dungeonChance:Double = 8.0,                      //   1 <= 100 ダンジョンの生成数
/*@add*/      var iceburgChance:Double = 16.0,                    // 16 | 200 氷山の生成数

/*@*/        var dirtSize:Double = 33.0,             // 1 <= 50      生成数
/*@*/        var dirtCount:Double = 10.0,            // 0 <= 40      頻度
/*@*/        var dirtMinHeight:Double = 0.0,         // 0 <= 255     下限
/*@*/        var dirtMaxHeight:Double = 256.0,       // 0 <= 255     上限

/*@*/        var gravelSize:Double = 33.0,
/*@*/        var gravelCount:Double = 8.0,
/*@*/        var gravelMinHeight:Double = 0.0,
/*@*/        var gravelMaxHeight:Double = 256.0,

/*@*/        var graniteSize:Double = 33.0,
/*@*/        var graniteCount:Double = 10.0,
/*@*/        var graniteMinHeight:Double = 0.0,
/*@*/        var graniteMaxHeight:Double = 80.0,

/*@*/        var dioriteSize:Double = 33.0,
/*@*/        var dioriteCount:Double = 10.0,
/*@*/        var dioriteMinHeight:Double = 0.0,
/*@*/        var dioriteMaxHeight:Double = 80.0,

/*@*/        var andesiteSize:Double = 33.0,
/*@*/        var andesiteCount:Double = 10.0,
/*@*/        var andesiteMinHeight:Double = 0.0,
/*@*/        var andesiteMaxHeight:Double = 80.0,

/*@*/        var coalSize:Double = 17.0,
/*@*/        var coalCount:Double = 20.0,
/*@*/        var coalMinHeight:Double = 0.0,
/*@*/        var coalMaxHeight:Double = 128.0,

/*@*/        var ironSize:Double = 9.0,
/*@*/        var ironCount:Double = 20.0,
/*@*/        var ironMinHeight:Double = 0.0,
/*@*/        var ironMaxHeight:Double = 64.0,

/*@*/        var goldSize:Double = 9.0,
/*@*/        var goldCount:Double = 2.0,
/*@*/        var goldMinHeight:Double = 0.0,
/*@*/        var goldMaxHeight:Double = 32.0,

/*@*/        var redstoneSize:Double = 8.0,
/*@*/        var redstoneCount:Double = 8.0,
/*@*/        var redstoneMinHeight:Double = 0.0,
/*@*/        var redstoneMaxHeight:Double = 16.0,

/*@*/        var diamondSize:Double = 8.0,
/*@*/        var diamondCount:Double = 1.0,
/*@*/        var diamondMinHeight:Double = 0.0, 
/*@*/        var diamondMaxHeight:Double = 16.0,

/*@*/        var lapisSize:Double = 7.0,
/*@*/        var lapisCount:Double = 1.0,
/*@*/        var lapisCenterHeight:Double = 16.0,
/*@*/        var lapisSpread:Double = 16.0
)


// 変更場所を見つけたやつはチェックしていく(テスト用)
data class ChunkProviderSettingsLast (
        // minecraft customize preset between 1.8 to 1.12
/**/        var useCaves:Boolean = true,                    // 洞窟
/**/        var useCavesOcean:Boolean = true,                    // 海の洞窟
        var useDungeons:Boolean = true,                 // スポナー部屋
/**/        var useRavines:Boolean = true,                                      // 渓谷
/**/        var useRavinesOcean:Boolean = true,                                 // 海の渓谷
        var useWaterLakes:Boolean = true,                               // 水の池
        var useLavaLakes:Boolean = true,                                // 溶岩池
        var useLavaOceans:Boolean = false,                              // 溶岩の海

/*TODO*/        var useStrongholds:Boolean = true,                      // 魔女
/**/        var useVillages:Boolean = true,
/**/        var useMineShafts:Boolean = true,                           // 廃坑
/**/        var useTemples:Boolean = true,                                      // to Jungle_pyramid
/**/        var useMonuments:Boolean = true,                            // 海底神殿
/**/        var useMansions:Boolean = true,
                
/**/        var coordinateScale:Double = 342.206,           // 1.0  <=6000 地形の鋭さ
/**/        var heightScale:Double = 5475.296,               // <=6000 高さの幅
/**/        var lowerLimitScale:Double = 476.0,             // <=5000 スケール下限
/**/        var upperLimitScale:Double = 548.0,             // <=5000 スケール上限

/**/        var depthNoiseScaleX:Double = 200.0,            // <=2000 x方向の起伏の激しさ
/**/        var depthNoiseScaleZ:Double = 200.0,            // <=2000 z方向の起伏の激しさ
/**/        var depthNoiseScaleExponent:Double = 0.5,       // 0.01 <=  20 地形のでたらめさ

/**/        var mainNoiseScaleX:Double = 40.0,              // 1.0  <=5000 地形の伸び x
/**/        var mainNoiseScaleY:Double = 120.0,             // <=5000 y
/**/        var mainNoiseScaleZ:Double = 40.0,              // <=5000 z

/**/        var baseSize:Double = 7.5,                      // <=  25 基本震度
/**/        var stretchY:Double = 10.0,                     // 0.01 <=  50 高さ伸び
/**/        var biomeDepthWeight:Double = 2.236,              // 1.0  <=  20 バイオームの地形変化の度合い
/**/        var biomeDepthOffset:Double = 1.0,              // 0.0  <=  20 奥行きの偏り
/**/        var biomeScaleWeight:Double = 1.414,              // 1.0  <=  20 大きさの度合い
/**/        var biomeScaleOffset:Double = 0.5,              // 0.0  <=  20 スケールの偏り

/**/        var seaLevel:Double = 63.0,
        var waterLakeChance:Double = 4.0,                               // 地中の水の池の数
        var lavaLakeChance:Double = 80.0,                               // 地中の溶岩池の数
        var dungeonChance:Double = 8.0,                                 // ダンジョンの生成数

        var fixedBiome:Double = -1.0,
        var biomeSize:Double = 4.0,
        var riverSize:Double = 4.0,

        var dirtSize:Double = 33.0,
        var dirtCount:Double = 10.0,
        var dirtMinHeight:Double = 0.0,
        var dirtMaxHeight:Double = 256.0,

        var gravelSize:Double = 33.0,
        var gravelCount:Double = 8.0,
        var gravelMinHeight:Double = 0.0,
        var gravelMaxHeight:Double = 256.0,

        var graniteSize:Double = 33.0,
        var graniteCount:Double = 10.0,
        var graniteMinHeight:Double = 0.0,
        var graniteMaxHeight:Double = 80.0,

        var dioriteSize:Double = 33.0,
        var dioriteCount:Double = 10.0,
        var dioriteMinHeight:Double = 0.0,
        var dioriteMaxHeight:Double = 80.0,

        var andesiteSize:Double = 33.0,
        var andesiteCount:Double = 10.0,
        var andesiteMinHeight:Double = 0.0,
        var andesiteMaxHeight:Double = 80.0,

        var coalSize:Double = 17.0,
        var coalCount:Double = 20.0,
        var coalMinHeight:Double = 0.0,
        var coalMaxHeight:Double = 128.0,

        var ironSize:Double = 9.0,
        var ironCount:Double = 20.0,
        var ironMinHeight:Double = 0.0,
        var ironMaxHeight:Double = 64.0,

        var goldSize:Double = 9.0,
        var goldCount:Double = 2.0,
        var goldMinHeight:Double = 0.0,
        var goldMaxHeight:Double = 32.0,

        var redstoneSize:Double = 8.0,
        var redstoneCount:Double = 8.0,
        var redstoneMinHeight:Double = 0.0,
        var redstoneMaxHeight:Double = 16.0,

        var diamondSize:Double = 8.0,
        var diamondCount:Double = 1.0,
        var diamondMinHeight:Double = 0.0,
        var diamondMaxHeight:Double = 16.0,

        var lapisSize:Double = 7.0,
        var lapisCount:Double = 1.0,
        var lapisCenterHeight:Double = 16.0,
        var lapisSpread:Double = 16.0,

        // add original customize presets
        var netherRoof:Boolean = true   // ネザーに屋根を作るかどうか？
)



// 変更場所を見つけたやつはチェックしていく(バックアップ)
data class ChunkProviderSettings_ (
        // minecraft customize preset between 1.8 to 1.12
/**/        var useCaves:Boolean = true,                    // 洞窟
/**/        var useCavesOcean:Boolean = true,                    // 海の洞窟
        var useDungeons:Boolean = true,                 // スポナー部屋
/**/        var useRavines:Boolean = true,                                      // 渓谷
/**/        var useRavinesOcean:Boolean = true,                                 // 海の渓谷
        var useWaterLakes:Boolean = true,                               // 水の池
        var useLavaLakes:Boolean = true,                                // 溶岩池
        var useLavaOceans:Boolean = false,                              // 溶岩の海

/*TODO*/        var useStrongholds:Boolean = true,                      // 魔女
/**/        var useVillages:Boolean = true,
/**/        var useMineShafts:Boolean = true,                           // 廃坑
/**/        var useTemples:Boolean = true,                                      // to Jungle_pyramid
/**/        var useMonuments:Boolean = true,                            // 海底神殿
/**/        var useMansions:Boolean = true,
                
/**/        var coordinateScale:Double = 684.412,           // 1.0  <=6000 地形の鋭さ
/**/        var heightScale:Double = 684.412,               // <=6000 高さの幅
/**/        var lowerLimitScale:Double = 512.0,             // <=5000 スケール下限
/**/        var upperLimitScale:Double = 512.0,             // <=5000 スケール上限

/**/        var depthNoiseScaleX:Double = 200.0,            // <=2000 x方向の起伏の激しさ
/**/        var depthNoiseScaleZ:Double = 200.0,            // <=2000 z方向の起伏の激しさ
/**/        var depthNoiseScaleExponent:Double = 0.5,       // 0.01 <=  20 地形のでたらめさ

/**/        var mainNoiseScaleX:Double = 80.0,              // 1.0  <=5000 地形の伸び x
/**/        var mainNoiseScaleY:Double = 160.0,             // <=5000 y
/**/        var mainNoiseScaleZ:Double = 80.0,              // <=5000 z

/**/        var baseSize:Double = 8.5,                      // <=  25 基本震度
/**/        var stretchY:Double = 12.0,                     // 0.01 <=  50 高さ伸び
/**/        var biomeDepthWeight:Double = 1.0,              // 1.0  <=  20 バイオームの地形変化の度合い
/**/        var biomeDepthOffset:Double = 0.0,              // 0.0  <=  20 奥行きの偏り
/**/        var biomeScaleWeight:Double = 1.0,              // 1.0  <=  20 大きさの度合い
/**/        var biomeScaleOffset:Double = 0.0,              // 0.0  <=  20 スケールの偏り

/**/        var seaLevel:Double = 63.0,
        var waterLakeChance:Double = 4.0,                               // 地中の水の池の数
        var lavaLakeChance:Double = 80.0,                               // 地中の溶岩池の数
        var dungeonChance:Double = 8.0,                                 // ダンジョンの生成数

        var fixedBiome:Double = -1.0,
        var biomeSize:Double = 4.0,
        var riverSize:Double = 4.0,

        var dirtSize:Double = 33.0,
        var dirtCount:Double = 10.0,
        var dirtMinHeight:Double = 0.0,
        var dirtMaxHeight:Double = 256.0,

        var gravelSize:Double = 33.0,
        var gravelCount:Double = 8.0,
        var gravelMinHeight:Double = 0.0,
        var gravelMaxHeight:Double = 256.0,

        var graniteSize:Double = 33.0,
        var graniteCount:Double = 10.0,
        var graniteMinHeight:Double = 0.0,
        var graniteMaxHeight:Double = 80.0,

        var dioriteSize:Double = 33.0,
        var dioriteCount:Double = 10.0,
        var dioriteMinHeight:Double = 0.0,
        var dioriteMaxHeight:Double = 80.0,

        var andesiteSize:Double = 33.0,
        var andesiteCount:Double = 10.0,
        var andesiteMinHeight:Double = 0.0,
        var andesiteMaxHeight:Double = 80.0,

        var coalSize:Double = 17.0,
        var coalCount:Double = 20.0,
        var coalMinHeight:Double = 0.0,
        var coalMaxHeight:Double = 128.0,

        var ironSize:Double = 9.0,
        var ironCount:Double = 20.0,
        var ironMinHeight:Double = 0.0,
        var ironMaxHeight:Double = 64.0,

        var goldSize:Double = 9.0,
        var goldCount:Double = 2.0,
        var goldMinHeight:Double = 0.0,
        var goldMaxHeight:Double = 32.0,

        var redstoneSize:Double = 8.0,
        var redstoneCount:Double = 8.0,
        var redstoneMinHeight:Double = 0.0,
        var redstoneMaxHeight:Double = 16.0,

        var diamondSize:Double = 8.0,
        var diamondCount:Double = 1.0,
        var diamondMinHeight:Double = 0.0,
        var diamondMaxHeight:Double = 16.0,

        var lapisSize:Double = 7.0,
        var lapisCount:Double = 1.0,
        var lapisCenterHeight:Double = 16.0,
        var lapisSpread:Double = 16.0,

        // add original customize presets
        var netherRoof:Boolean = true   // ネザーに屋根を作るかどうか？
)
