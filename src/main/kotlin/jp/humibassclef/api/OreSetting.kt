package jp.humibassclef.api

enum class OreSetting() {

    // TODO add other save values
    VEIN_SIZE(0),
    VEINS_PER_CHUNK(0),
    HEIGHT_RANGE(1),
    HEIGHT_CENTER(-1),
    MINIMUM_ORES_PER_CHUNK(-1),
    ORES_PER_CHUNK_RANGE(-1),
    MINIMUM_HEIGHT(0),
    HEIGHT_SUBTRACT_VALUE(-1);

    constructor(saveValue: Int) {
        this.saveValue = saveValue
    }

    private var saveValue: Int = 0
    fun getSaveValue(): Int {
        return saveValue
    }
}
