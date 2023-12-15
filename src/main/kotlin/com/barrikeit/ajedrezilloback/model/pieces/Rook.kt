package com.barrikeit.ajedrezilloback.model.pieces

import com.barrikeit.ajedrezilloback.model.Side

class Rook(
    override val side: Side,
) : Piece {
    override val value: Int = 5
    override val textSymbol: String =
        when (side) {
            Side.WHITE -> Symbols.WHITE_ROOK.fenSymbol
            Side.BLACK -> Symbols.BLACK_ROOK.fenSymbol
        }
    override val symbol: String =
        when (side) {
            Side.WHITE -> Symbols.WHITE_ROOK.fanSymbol
            Side.BLACK -> Symbols.BLACK_ROOK.fanSymbol
        }
}