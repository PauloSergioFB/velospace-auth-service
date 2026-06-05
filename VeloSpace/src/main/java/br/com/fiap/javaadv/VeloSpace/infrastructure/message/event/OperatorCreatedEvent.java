package br.com.fiap.javaadv.VeloSpace.infrastructure.message.event;

import br.com.fiap.javaadv.VeloSpace.model.Operator;
import lombok.Builder;

@Builder
public record OperatorCreatedEvent(
        Long operatorId,
        Long launchProviderId,
        Long userAccountId,
        String operatorStatusCode) {

    public static OperatorCreatedEvent from(Operator operator) {
        if (operator == null)
            return null;

        return OperatorCreatedEvent.builder()
                .launchProviderId(operator.getOperatorId())
                .launchProviderId(operator.getLaunchProvider().getLaunchProviderId())
                .userAccountId(operator.getUserAccount().getUserAccountId())
                .operatorStatusCode(operator.getOperatorStatus().getCode())
                .build();
    }

}
