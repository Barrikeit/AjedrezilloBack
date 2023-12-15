package com.barrikeit.ajedrezilloback.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

data class ChessGame(
    val id: String = UUID.randomUUID().toString(),
    val event: String = "Ajedrezillo Game",
    val site: String = "Ajedrezillo App",
    val date: String = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
    var white: Player? = null,
    var black: Player? = null,
    var turn: Side = Side.WHITE,
    var gameStatus: GameStatus = GameStatus.IN_PROGRESS,
    var board: ChessBoard = ChessBoard(),
    var moves: List<Move> = emptyList()
) {
    val result: String
        get() = when (gameStatus) {
            GameStatus.IN_PROGRESS -> "*"
            GameStatus.CHECKMATE -> if (turn == Side.WHITE) "1-0" else "0-1"
            GameStatus.RESIGNATION -> if (turn == Side.WHITE) "0-1" else "1-0"
            GameStatus.STALEMATE, GameStatus.DRAW, GameStatus.DRAW_BY_REPETITION, GameStatus.INSUFFICIENT_MATERIAL -> "½ - ½"
        }
    fun addPlayer(player: Player){
        when(player.side){
            Side.WHITE -> white = player
            Side.BLACK -> black = player
        }
    }
    fun getPlayer(playerId: String): Player?{
        return when{
            white?.id == playerId -> white
            black?.id == playerId -> black
            else -> null
        }
    }
    fun applyMove(move: Move){
        moves = moves.plus(move)
        board.move(move.piece, move.from, move.to)
        move.effect?.let {
            when(it){
                MoveEffect.CHECKMATE -> {
                    gameStatus = GameStatus.CHECKMATE
                }
                else -> {
                    swapTurn()
                }
            }
        }
    }
    private fun swapTurn(){
        turn = turn.opposite()
    }
}