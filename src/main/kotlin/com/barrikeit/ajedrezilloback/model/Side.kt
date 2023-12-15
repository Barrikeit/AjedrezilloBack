package com.barrikeit.ajedrezilloback.model

enum class Side {
    WHITE, BLACK;

    fun opposite() = when (this) {
        WHITE -> BLACK
        BLACK -> WHITE
    }
}