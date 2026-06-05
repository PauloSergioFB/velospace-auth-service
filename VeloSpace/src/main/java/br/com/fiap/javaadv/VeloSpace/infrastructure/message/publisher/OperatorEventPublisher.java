package br.com.fiap.javaadv.VeloSpace.infrastructure.message.publisher;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import br.com.fiap.javaadv.VeloSpace.infrastructure.message.event.OperatorCreatedEvent;
import br.com.fiap.javaadv.VeloSpace.infrastructure.message.event.OperatorDeletedEvent;
import br.com.fiap.javaadv.VeloSpace.model.Operator;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OperatorEventPublisher {

    private final JmsTemplate jmsTemplate;

    public void publishCreated(Operator operator) {
        jmsTemplate.convertAndSend(
                "operator.created",
                OperatorCreatedEvent.from(operator));
    }

    public void publishUpdated(Operator updatedOperator) {
        jmsTemplate.convertAndSend(
                "operator.updated",
                OperatorCreatedEvent.from(updatedOperator));
    }

    public void publishDeleted(Long operatorId) {
        jmsTemplate.convertAndSend(
                "operator.deleted",
                new OperatorDeletedEvent(operatorId));
    }

}
