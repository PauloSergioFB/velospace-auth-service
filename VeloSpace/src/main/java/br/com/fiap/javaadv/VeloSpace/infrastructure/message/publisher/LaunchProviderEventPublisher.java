package br.com.fiap.javaadv.VeloSpace.infrastructure.message.publisher;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import br.com.fiap.javaadv.VeloSpace.infrastructure.message.event.LaunchProviderCreatedEvent;
import br.com.fiap.javaadv.VeloSpace.infrastructure.message.event.LaunchProviderDeletedEvent;
import br.com.fiap.javaadv.VeloSpace.model.LaunchProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class LaunchProviderEventPublisher {

    private final JmsTemplate jmsTemplate;

    public void publishCreated(LaunchProvider launchProvider) {
        log.info("Testando...");

        jmsTemplate.convertAndSend(
                "launch_provider.created",
                LaunchProviderCreatedEvent.from(launchProvider));
    }

    public void publishDeleted(Long launchProviderId) {
        jmsTemplate.convertAndSend(
                "launch_provider.deleted",
                new LaunchProviderDeletedEvent(launchProviderId));
    }

}
