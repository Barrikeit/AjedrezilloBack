package com.barrikeit.ajedrezilloback.model

import com.barrikeit.ajedrezilloback.model.pieces.Piece
import com.google.gson.Gson
import lombok.Data

@Data
data class MoveMessage(
    val piece: Piece,
    val from: String,
    val to: String,
    val effect: String? = null
)

fun Move.toMoveMessage(): MoveMessage {
    return MoveMessage(
        piece = piece,
        from = from.toString(),
        to = to.toString(),
        effect = effect?.toString()
    )
}
fun MoveMessage.toMove(): Move {
    return Move(
        piece = piece,
        from = Position.from(from),
        to = Position.from(to),
        effect = effect?.let { MoveEffect.from(it) }
    )
}
//{"piece": {"side": "WHITE","value": 1,"symbol": "♟","textSymbol": "P"},"from": "E2","to": "E4","effect": null}