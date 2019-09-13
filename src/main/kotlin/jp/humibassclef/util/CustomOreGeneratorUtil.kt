package jp.humibassclef.util

import java.util.*

class CustomOreGeneratorUtil {
    companion object {
        fun getRandom(seed: Long, x: Int, z: Int): Random {
            val random = Random(seed)

            val long1 = random.nextLong()
            val long2 = random.nextLong()
            val newseed = x.toLong() * long1 xor z.toLong() * long2 xor seed
            random.setSeed(newseed)

            return random
        }
    }
}
