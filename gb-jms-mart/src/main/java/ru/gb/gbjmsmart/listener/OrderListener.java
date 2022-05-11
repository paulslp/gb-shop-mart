package ru.gb.gbjmsmart.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.gb.gbapi.events.OrderEvent;
import ru.gb.gbjmsmart.sender.MailService;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderListener {

    private final MailService mailService;

    @JmsListener(destination = "${spring.artemis.embedded.queues}")
    public void listen(@Payload OrderEvent orderEvent) {
        log.info(orderEvent.toString());
        mailService.SendMessage(orderEvent.getOrderDto().getMail(), "Change order",
                orderEvent.getOrderDto().toString());
    }
}
