package br.com.fiap.javaadv.VeloSpace.infrastructure.message.publisher;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import br.com.fiap.javaadv.VeloSpace.infrastructure.message.event.OperatorCreatedEvent;
import br.com.fiap.javaadv.VeloSpace.infrastructure.message.event.OperatorDeletedEvent;
import br.com.fiap.javaadv.VeloSpace.infrastructure.message.event.OperatorUpdatedEvent;
import br.com.fiap.javaadv.VeloSpace.model.Operator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class OperatorEventPublisher {

    private final JmsTemplate jmsTemplate;

    public void publishCreated(Operator operator) {
        log.info("Enviando mensagem operator criado id: {}", operator.getOperatorId());

        jmsTemplate.convertAndSend(
                "operator.created",
                OperatorCreatedEvent.from(operator));
    }

    public void publishUpdated(Operator updatedOperator) {
        log.info("Enviando mensagem operator atualizado id: {}", updatedOperator.getOperatorId());

        jmsTemplate.convertAndSend(
                "operator.updated",
                OperatorUpdatedEvent.from(updatedOperator));
    }

    public void publishDeleted(Long operatorId) {
        log.info("Enviando mensagem operator deletado id: {}", operatorId);

        jmsTemplate.convertAndSend(
                "operator.deleted",
                new OperatorDeletedEvent(operatorId));
    }

}
