package com.barrikeit.ajedrezilloback.model.pieces

import com.barrikeit.ajedrezilloback.model.Side

class King(
    override val side: Side,
) : Piece {
    override val value: Int = Int.MAX_VALUE
    override val textSymbol: String =
        when (side) {
            Side.WHITE -> Symbols.WHITE_KING.fenSymbol
            Side.BLACK -> Symbols.BLACK_KING.fenSymbol
        }
    override val symbol: String =
        when (side) {
            Side.WHITE -> Symbols.WHITE_KING.fanSymbol
            Side.BLACK -> Symbols.BLACK_KING.fanSymbol
        }
}
