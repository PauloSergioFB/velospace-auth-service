package br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.Satellite;

import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.SatelliteStatus.SatelliteStatusResponseDTO;
import lombok.Builder;

@Builder
public record SatelliteItemResponseDTO(
        Long satelliteId,
        Long launchProviderId,
        String name,
        SatelliteStatusResponseDTO status) {
}
