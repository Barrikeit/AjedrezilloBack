package com.barrikeit.ajedrezilloback.model

import com.barrikeit.ajedrezilloback.model.pieces.*

data class ChessBoard(
    val pieces: Map<Position, Piece> = initialPieces,
) {
    fun move(piece: Piece, from: Position, to: Position): ChessBoard {
        val newPieces = pieces.toMutableMap()
        newPieces.remove(from)
        newPieces[to] = piece
        return copy(pieces = newPieces)
    }
}

private val initialPieces = mapOf(
    Position.A8 to Rook(Side.BLACK),
    Position.B8 to Knight(Side.BLACK),
    Position.C8 to Bishop(Side.BLACK),
    Position.D8 to Queen(Side.BLACK),
    Position.E8 to King(Side.BLACK),
    Position.F8 to Bishop(Side.BLACK),
    Position.G8 to Knight(Side.BLACK),
    Position.H8 to Rook(Side.BLACK),

    Position.A7 to Pawn(Side.BLACK),
    Position.B7 to Pawn(Side.BLACK),
    Position.C7 to Pawn(Side.BLACK),
    Position.D7 to Pawn(Side.BLACK),
    Position.E7 to Pawn(Side.BLACK),
    Position.F7 to Pawn(Side.BLACK),
    Position.G7 to Pawn(Side.BLACK),
    Position.H7 to Pawn(Side.BLACK),

    Position.A2 to Pawn(Side.WHITE),
    Position.B2 to Pawn(Side.WHITE),
    Position.C2 to Pawn(Side.WHITE),
    Position.D2 to Pawn(Side.WHITE),
    Position.E2 to Pawn(Side.WHITE),
    Position.F2 to Pawn(Side.WHITE),
    Position.G2 to Pawn(Side.WHITE),
    Position.H2 to Pawn(Side.WHITE),

    Position.A1 to Rook(Side.WHITE),
    Position.B1 to Knight(Side.WHITE),
    Position.C1 to Bishop(Side.WHITE),
    Position.D1 to Queen(Side.WHITE),
    Position.E1 to King(Side.WHITE),
    Position.F1 to Bishop(Side.WHITE),
    Position.G1 to Knight(Side.WHITE),
    Position.H1 to Rook(Side.WHITE),
)