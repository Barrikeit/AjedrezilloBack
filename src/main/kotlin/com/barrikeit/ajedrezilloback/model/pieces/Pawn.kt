package com.barrikeit.ajedrezilloback.model.pieces

import com.barrikeit.ajedrezilloback.model.Side

class Pawn(
    override val side: Side,
) : Piece {
    override val value: Int = 1
    override val symbol: String =
        when (side) {
            Side.WHITE -> Symbols.WHITE_PAWN.fanSymbol
            Side.BLACK -> Symbols.BLACK_PAWN.fanSymbol
        }
    override val textSymbol: String =
        when (side) {
            Side.WHITE -> Symbols.WHITE_PAWN.fenSymbol
            Side.BLACK -> Symbols.BLACK_PAWN.fenSymbol
        }
}