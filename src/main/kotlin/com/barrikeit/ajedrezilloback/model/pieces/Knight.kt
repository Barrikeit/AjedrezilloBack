package com.barrikeit.ajedrezilloback.model.pieces

import com.barrikeit.ajedrezilloback.model.Side

class Knight(
    override val side: Side,
) : Piece {
    override val value: Int = 3
    override val textSymbol: String =
        when (side) {
            Side.WHITE -> Symbols.WHITE_KNIGHT.fenSymbol
            Side.BLACK -> Symbols.BLACK_KNIGHT.fenSymbol
        }
    override val symbol: String =
        when (side) {
            Side.WHITE -> Symbols.WHITE_KNIGHT.fanSymbol
            Side.BLACK -> Symbols.BLACK_KNIGHT.fanSymbol
        }
}
