package com.barrikeit.ajedrezilloback.config

import com.barrikeit.ajedrezilloback.model.*
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class ChessGameService {
    private val games = ConcurrentHashMap<String, ChessGame>()

    /**
     * Returns the game with the given id if it exists, otherwise throws an exception
     */
    fun getGame(
        gameId: String,
    ): ChessGame {
        return games[gameId] ?: throw RuntimeException("This game does not exist: $gameId")
    }

    /**
     * Returns TRUE if the player is already playing a game and FALSE otherwise
     */
    fun isPlayerInGame(
        playerId: String
    ): Boolean {
        return games.values.any { it.white?.id == playerId || it.black?.id == playerId }
    }

    /**
     * Returns the game with the given id if it exists, otherwise creates a new game and returns it
     */
    fun getGameWithWaitingPlayerOrCreateGame(): ChessGame {
        return games.values.find { it.black == null } ?: createGame(ChessGame())
    }

    /**
     * Returns the game with the new player added to the game if no game is being played by the player of the given id, otherwise throws an exception
     */
    fun findOrCreateGame(
        playerId: String,
    ): ChessGame {
        return if (!isPlayerInGame(playerId)) {
            val game = getGameWithWaitingPlayerOrCreateGame()
            addPlayer(game, playerId)
            getGame(game.id)
        } else {
            getGame(games.values.find { it.white?.id == playerId || it.black?.id == playerId }!!.id)
        }
    }

    /**
     * Returns the game with the move applied if the game is full, the player is playing the game, it is their turn and the piece they are moving is of their side, otherwise throws an exception
     */
    fun applyMove(
        game: ChessGame,
        playerId: String,
        move: Move
    ): ChessGame {
        if (game.white == null || game.black == null) throw RuntimeException("Game with id ${game.id} is not full")
        val player = game.getPlayer(playerId) ?: throw RuntimeException("Player with id $playerId does not exist in game with id $game")
        if (game.turn != player.side) {
            throw RuntimeException("It is not $playerId's turn")
        } else if (player.side != move.piece.side) {
            throw RuntimeException("It is not $playerId's piece")
        } else {
            game.applyMove(move)
            return getGame(game.id)
        }
    }

    /**
     * hanges the gameStatus, otherwise throws an exception
     */
    fun changeGameStatus(
        game: ChessGame,
        playerId: String,
        gameStatus: String
    ){
        if (game.white == null || game.black == null) throw RuntimeException("Game with id ${game.id} is not full")
        val player = game.getPlayer(playerId) ?: throw RuntimeException("Player with id $playerId does not exist in game with id $game")
        if (game.turn != player.side) {
            throw RuntimeException("It is not $playerId's turn")
        } else {
            game.gameStatus = GameStatus.valueOf(gameStatus)
        }
    }

    /**
     * Adds the game to the list of games and returns it
     */
    private fun createGame(chessGame: ChessGame): ChessGame {
        games[chessGame.id] = chessGame
        return chessGame
    }

    /**
     * Adds the player to the game if there is a free spot, otherwise throws an exception
     */
    private fun addPlayer(
        game: ChessGame,
        playerId: String,
    ) {
        if (game.white == null) {
            game.addPlayer(Player(id = playerId, name = "Player 1", Side.WHITE))
        } else if (game.black == null) {
            game.addPlayer(Player(id = playerId, name = "Player 2", Side.BLACK))
        } else {
            throw RuntimeException("Game with id $game is full")
        }
    }
}