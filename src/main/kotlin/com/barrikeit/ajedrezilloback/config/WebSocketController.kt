package com.barrikeit.ajedrezilloback.config

import com.barrikeit.ajedrezilloback.model.MoveMessage
import com.barrikeit.ajedrezilloback.model.toMove
import com.google.gson.Gson
import mu.KotlinLogging
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageExceptionHandler
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller

@Controller
class WebSocketController(
    private val gson: Gson,
    private val messagingTemplate: SimpMessagingTemplate,
    private val chessGameService: ChessGameService
) {
    private val logger = KotlinLogging.logger {}

    /**
     * Jugador con una sesion hace peticion para unirse a una partida
     * Le suscribe al topic de esa partida
     * Le devuelve el id de la partida para que pueda seguir haciendo peticiones
     * Y un loggger
     */
    @MessageMapping("/game/join")
    fun requestJoinGame(
        headerAccessor: SimpMessageHeaderAccessor
    ) {
        val playerId = headerAccessor.sessionId!!
        val game = chessGameService.findOrCreateGame(playerId)
        // Subscribe to the game topic
        headerAccessor.subscriptionId = "/topic/game/${game.id}"
        // Send the game ID back to the player
        messagingTemplate.convertAndSendToUser(playerId, "/queue/gameId", game.id)
        // Log for the server
        logger.info { "Subscribed to game with id: ${game.id}" }
    }

    /**
     * Jugador hace peticion para realizar un movimiento
     * La comprobacion del movimiento se hace en la ui, no en el servidor
     * El servidor solo comprueba que el turno sea del jugador que hace la peticion
     * Y que la pieza que se mueve sea del mismo color que el jugador que hace la peticion
     */
    @MessageMapping("/game/{gameId}/move")
    @SendTo("/topic/game/{gameId}")
    fun handleMove(
        headerAccessor: SimpMessageHeaderAccessor,
        @DestinationVariable("gameId") gameId: String,
        message: String
    ): String {
        val playerId = headerAccessor.sessionId!!
        var game = chessGameService.getGame(gameId)

        chessGameService.applyMove(game, playerId, gson.fromJson(message, MoveMessage::class.java).toMove())
        game = chessGameService.getGame(gameId)
        logger.info { "Applied move: $message" }
        logger.info { "Game: ${game.moves}" }
        logger.info { "Game: ${game.board.pieces}" }
        return message
    }

    @MessageMapping("/game/{gameId}/gameStatus")
    @SendTo("/topic/game/{gameId}")
    fun handleChangeGameStatus(
        headerAccessor: SimpMessageHeaderAccessor,
        @DestinationVariable("gameId") gameId: String,
        message: String
    ): String {
        val playerId = headerAccessor.sessionId!!
        val game = chessGameService.getGame(gameId)
        chessGameService.changeGameStatus(game, playerId, message)
        logger.info { "GameStatus ${game.gameStatus}" }
        logger.info { "GameResult ${game.result}" }
        return message
    }

    /**
     * Cualquiero error que se produzca en el servidor se envia a este queue
     */
    @MessageExceptionHandler
    @SendTo("/queue/errors")
    fun handleException(exception: Throwable): String {
        logger.error { "Server exception: \n$exception" }
        return "server exception: " + exception.message
    }
}