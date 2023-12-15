package com.barrikeit.ajedrezilloback.config

import org.springframework.messaging.core.MessageSendingOperations
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalTime

@Component
class ScheduledController(
    private val messageSendingOperations: MessageSendingOperations<String>
) {
    @Scheduled(fixedDelay = 60000)
    fun sendPeriodicMessages() {
        val broadcast = String.format("server periodic message %s via the broker", LocalTime.now())
        messageSendingOperations.convertAndSend("/topic/periodic", broadcast)
    }
}