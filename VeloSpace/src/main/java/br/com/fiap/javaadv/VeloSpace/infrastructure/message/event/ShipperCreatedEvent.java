package br.com.fiap.javaadv.VeloSpace.infrastructure.message.event;

import br.com.fiap.javaadv.VeloSpace.model.Shipper;
import lombok.Builder;

@Builder
public record ShipperCreatedEvent(
        Long shipperId,
        Long userAccountId,
        String name) {

    public static ShipperCreatedEvent from(Shipper shipper) {
        if (shipper == null)
            return null;

        return ShipperCreatedEvent.builder()
                .shipperId(shipper.getShipperId())
                .userAccountId(shipper.getUserAccount().getUserAccountId())
                .name(shipper.getName())
                .build();
    }

}
