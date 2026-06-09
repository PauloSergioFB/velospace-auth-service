package br.com.fiap.javaadv.VeloSpace.service.OperatorStatus;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.NotFoundException;
import br.com.fiap.javaadv.VeloSpace.model.OperatorStatus;
import br.com.fiap.javaadv.VeloSpace.model.repository.OperatorStatusRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OperatorStatusServiceImpl implements OperatorStatusService<OperatorStatus, Long> {

    private final OperatorStatusRepository operatorStatusRepository;

    @Override
    @Cacheable(value = "operator-statuses", key = "#code")
    public OperatorStatus findByCode(String code) {
        return operatorStatusRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException(
                        "Status de operador não encontrado."));
    }

    @Override
    @Cacheable(value = "operator-statuses-required", key = "#code")
    public OperatorStatus getRequiredByCode(String code) {
        return operatorStatusRepository.findByCode(code)
                .orElseThrow(() -> new IllegalStateException(
                        "Status de operador obrigatório não encontrado na base de dados: " + code + "."));
    }

}
