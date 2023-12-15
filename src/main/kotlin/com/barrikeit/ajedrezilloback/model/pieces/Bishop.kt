package com.barrikeit.ajedrezilloback.model.pieces

import com.barrikeit.ajedrezilloback.model.Side

class Bishop(
    override val side: Side,
) : Piece {
    override val value: Int = 3
    override val symbol: String =
        when (side) {
            Side.WHITE -> Symbols.WHITE_BISHOP.fanSymbol
            Side.BLACK -> Symbols.BLACK_BISHOP.fanSymbol
        }
    override val textSymbol: String =
        when (side) {
            Side.WHITE -> Symbols.WHITE_BISHOP.fenSymbol
            Side.BLACK -> Symbols.BLACK_BISHOP.fenSymbol
        }
}