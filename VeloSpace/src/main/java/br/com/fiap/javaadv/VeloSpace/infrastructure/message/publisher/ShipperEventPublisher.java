package br.com.fiap.javaadv.VeloSpace.infrastructure.message.publisher;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import br.com.fiap.javaadv.VeloSpace.infrastructure.message.event.ShipperCreatedEvent;
import br.com.fiap.javaadv.VeloSpace.infrastructure.message.event.ShipperDeletedEvent;
import br.com.fiap.javaadv.VeloSpace.model.Shipper;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ShipperEventPublisher {

    private final JmsTemplate jmsTemplate;

    public void publishCreated(Shipper shipper) {
        jmsTemplate.convertAndSend(
                "shipper.created",
                ShipperCreatedEvent.from(shipper));
    }

    public void publishDeleted(Long shipperId) {
        jmsTemplate.convertAndSend(
                "shipper.deleted",
                new ShipperDeletedEvent(shipperId));
    }

}
