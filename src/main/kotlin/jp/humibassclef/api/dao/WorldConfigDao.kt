package jp.humibassclef.api.dao

import jp.humibassclef.api.WorldConfig
import java.util.*

interface WorldConfigDao {

    fun get(world: String): Optional<WorldConfig>

    fun remove(config: WorldConfig)

    fun save(config: WorldConfig)

    fun getAll(): Set<WorldConfig>
}
