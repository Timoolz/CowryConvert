package com.olamide.cowryconvert.model

enum class NightMode(val value: Int) {
    DARK(2),
    LIGHT(1),
    SYSTEM(0);

    companion object {
        private val map = NightMode.values().associateBy(NightMode::value)
        fun fromInt(value: Int) = map[value]
    }
}