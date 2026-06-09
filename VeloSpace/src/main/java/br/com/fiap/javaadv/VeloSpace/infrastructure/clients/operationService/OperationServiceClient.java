package br.com.fiap.javaadv.VeloSpace.infrastructure.clients.operationService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.fiap.javaadv.VeloSpace.infrastructure.enums.SatelliteSortField;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.PageResponseDTO;
import br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.Satellite.SatelliteItemResponseDTO;

@FeignClient(name = "operationClient", url = "${operation.api.url}", configuration = InternalFeignConfig.class)
public interface OperationServiceClient {

    @GetMapping("/internal/v1/satellites/by-shipper/{shipperId}")
    PageResponseDTO<SatelliteItemResponseDTO> findByShipperId(
            @PathVariable Long shipperId,
            @RequestParam int page,
            @RequestParam int items,
            @RequestParam SatelliteSortField sortBy,
            @RequestParam String direction);

    @GetMapping("/internal/v1/launch-provider/by-launch-provider/{launchProviderId}")
    PageResponseDTO<SatelliteItemResponseDTO> findByLaunchProviderId(
            @PathVariable Long launchProviderId,
            @RequestParam int page,
            @RequestParam int items,
            @RequestParam SatelliteSortField sortBy,
            @RequestParam String direction);

}
