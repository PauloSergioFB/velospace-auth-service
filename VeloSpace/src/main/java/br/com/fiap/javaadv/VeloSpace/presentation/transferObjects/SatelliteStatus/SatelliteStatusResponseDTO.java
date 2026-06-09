package br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.SatelliteStatus;

import lombok.Builder;

@Builder
public record SatelliteStatusResponseDTO(
        Long satelliteStatusId,
        String code,
        String description) {
}
