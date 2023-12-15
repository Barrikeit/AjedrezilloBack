package com.barrikeit.ajedrezilloback.config

import com.barrikeit.ajedrezilloback.model.pieces.*
import com.google.gson.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import java.lang.reflect.Type

/**
 * En una conexsion con un websocket, lo primero que se hace es conectarse a un endpoint haciendo un request http de tipo GET con un header Connection: Upgrade.
 * El servidor responde con un http 101 Switching Protocols indicando que se ha cambiado el protocolo a websocket.
 * Debido a que es un servidor STOMP, las llamadas que se pueden hacer son SEND, SUBSCRIBE, UNSUBSCRIBE y BEGIN.
 */
@Configuration
@EnableScheduling
@EnableWebSocketMessageBroker
class WebSocketConfig : WebSocketMessageBrokerConfigurer {
    val url = "ws://localhost:8888/v1/chess"

    override fun configureMessageBroker(config: MessageBrokerRegistry) {
        config.enableSimpleBroker("/queue", "/topic")
        config.setApplicationDestinationPrefixes("/app")
        config.setPreservePublishOrder(true);
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/v1/chess").setAllowedOriginPatterns("*")
        registry.addEndpoint("/v1/chess").setAllowedOriginPatterns("*").withSockJS()
    }

    @Bean
    fun Gson(): Gson = GsonBuilder()
        .registerTypeAdapter(Piece::class.java, PieceDeserializer())
        .create()


    class PieceDeserializer : JsonDeserializer<Piece> {
        override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): Piece {
            val jsonObject = json.asJsonObject
            return when (val pieceType = jsonObject["textSymbol"].asString) {
                "P", "p" -> context!!.deserialize(json, Pawn::class.java)
                "N", "n" -> context!!.deserialize(json, Knight::class.java)
                "B", "b" -> context!!.deserialize(json, Bishop::class.java)
                "R", "r" -> context!!.deserialize(json, Rook::class.java)
                "Q", "q" -> context!!.deserialize(json, Queen::class.java)
                "K", "k" -> context!!.deserialize(json, King::class.java)
                else -> throw JsonParseException("Unknown piece type: $pieceType")
            }
        }
    }
}