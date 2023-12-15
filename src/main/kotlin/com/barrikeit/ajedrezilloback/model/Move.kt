package com.barrikeit.ajedrezilloback.model

import com.barrikeit.ajedrezilloback.model.pieces.Piece

data class Move(
    val piece: Piece,
    val from: Position,
    val to: Position,
    val effect: MoveEffect? = null
)

enum class MoveEffect {
    CHECKMATE, CHECK, DRAW, STALEMATE, EN_PASSANT, CASTLING, PROMOTION;
    companion object {
        fun from(value: String): MoveEffect? = values().find { it.name == value }
    }
}