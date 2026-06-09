package br.com.fiap.javaadv.VeloSpace.infrastructure.message.publisher;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import br.com.fiap.javaadv.VeloSpace.infrastructure.message.event.ShipperCreatedEvent;
import br.com.fiap.javaadv.VeloSpace.infrastructure.message.event.ShipperDeletedEvent;
import br.com.fiap.javaadv.VeloSpace.model.Shipper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShipperEventPublisher {

    private final JmsTemplate jmsTemplate;

    public void publishCreated(Shipper shipper) {
        log.info("Enviando mensagem shipper criado id: {}", shipper.getShipperId());

        jmsTemplate.convertAndSend(
                "shipper.created",
                ShipperCreatedEvent.from(shipper));
    }

    public void publishDeleted(Long shipperId) {
        log.info("Enviando mensagem shipper deletado id: {}", shipperId);

        jmsTemplate.convertAndSend(
                "shipper.deleted",
                new ShipperDeletedEvent(shipperId));
    }

}
