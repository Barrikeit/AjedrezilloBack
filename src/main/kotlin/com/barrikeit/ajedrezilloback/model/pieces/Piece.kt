package com.barrikeit.ajedrezilloback.model.pieces

import com.barrikeit.ajedrezilloback.model.Side

interface Piece {
    val side: Side
    val value: Int
    val symbol: String
    val textSymbol: String
}