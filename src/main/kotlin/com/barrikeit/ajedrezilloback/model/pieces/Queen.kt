package com.barrikeit.ajedrezilloback.model.pieces

import com.barrikeit.ajedrezilloback.model.Side

class Queen(
    override val side: Side,
) : Piece {
    override val value: Int = 9
    override val textSymbol: String =
        when (side) {
            Side.WHITE -> Symbols.WHITE_QUEEN.fenSymbol
            Side.BLACK -> Symbols.BLACK_QUEEN.fenSymbol
        }
    override val symbol: String =
        when (side) {
            Side.WHITE -> Symbols.WHITE_QUEEN.fanSymbol
            Side.BLACK -> Symbols.BLACK_QUEEN.fanSymbol
        }
}
