package com.task.cibinternstesttask.components;

import com.task.cibinternstesttask.entity.SocksEntity;
import com.task.cibinternstesttask.exceptions.SocksErrorCode;
import com.task.cibinternstesttask.exceptions.SocksException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Validation {
    public void incomeOutcomeValidator(SocksEntity socks) {
        if (socks.getQuantity() <= 0 || socks.getCottonPart() < 0 || socks.getCottonPart() > 100 || socks.getColor().isEmpty()) {
            throw new SocksException(SocksErrorCode.INCORRECT_PARAMS);
        }
    }

    public void getSocksValidator(String color, int cottonPart) {
        if (color.isEmpty() || cottonPart < 0 || cottonPart > 100) {
            throw new SocksException(SocksErrorCode.INCORRECT_PARAMS);
        }
    }

    public void outcomeValidator(Optional<Integer> actualQuantity, Integer transferableQuantity) {
        if (!actualQuantity.isPresent() || transferableQuantity > actualQuantity.get()) {
            throw new SocksException(SocksErrorCode.INCORRECT_PARAMS);
        }
    }
}
