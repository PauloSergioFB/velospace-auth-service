package br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.Operator;

import jakarta.validation.constraints.NotNull;

public record ApprovalOperatorDTO(
        @NotNull(message = "Approval é obrigatório") Boolean approval) {
}
