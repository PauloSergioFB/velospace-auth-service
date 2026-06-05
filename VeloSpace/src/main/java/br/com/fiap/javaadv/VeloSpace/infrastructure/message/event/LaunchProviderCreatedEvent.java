package br.com.fiap.javaadv.VeloSpace.infrastructure.message.event;

import br.com.fiap.javaadv.VeloSpace.model.LaunchProvider;
import lombok.Builder;

@Builder
public record LaunchProviderCreatedEvent(
        Long launchProviderId,
        Long userAccountId) {

    public static LaunchProviderCreatedEvent from(LaunchProvider launchProvider) {
        if (launchProvider == null)
            return null;

        return LaunchProviderCreatedEvent.builder()
                .launchProviderId(launchProvider.getLaunchProviderId())
                .userAccountId(launchProvider.getUserAccount().getUserAccountId())
                .build();
    }

}
