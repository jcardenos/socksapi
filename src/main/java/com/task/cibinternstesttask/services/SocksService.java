package com.task.cibinternstesttask.services;

import com.task.cibinternstesttask.components.Validation;
import com.task.cibinternstesttask.entity.SocksEntity;
import com.task.cibinternstesttask.exceptions.SocksErrorCode;
import com.task.cibinternstesttask.exceptions.SocksException;
import com.task.cibinternstesttask.repositories.SocksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SocksService {
    private SocksRepo socksRepo;
    private Validation validation;

    @Autowired
    public SocksService(SocksRepo socksRepo, Validation validation) {
        this.socksRepo = socksRepo;
        this.validation = validation;
    }

    public SocksEntity incomeSocks(SocksEntity socks) {
        validation.incomeOutcomeValidator(socks);

        Optional<Integer> quantity = Optional.ofNullable(socksRepo.
                findQuantityOfSocksByColorAndCottonPartEqualsToCurrentNumber(socks.getColor(), socks.getCottonPart()));
        if (!quantity.isPresent()) {
            return socksRepo.save(socks);
        } else {
            socksRepo.incomeSocksThatAlreadyExist(socks.getColor(), socks.getCottonPart(), socks.getQuantity());
            return null;
        }
    }

    public void outcomeSocks(SocksEntity socks) {
        validation.incomeOutcomeValidator(socks);

        Optional<Integer> quantity = Optional.ofNullable(socksRepo.
                findQuantityOfSocksByColorAndCottonPartEqualsToCurrentNumber(socks.getColor(), socks.getCottonPart()));
        validation.outcomeValidator(quantity, socks.getQuantity());
        socksRepo.outcomeSocks(socks.getColor(), socks.getCottonPart(), socks.getQuantity());
    }

    public int getQuantityOfSocksByColorAndCottonPartLessThanCurrentNumber(String color, int cottonPart) {
        Optional<Integer> quantity = Optional.ofNullable
                (socksRepo.findQuantityOfSocksByColorAndCottonPartLessThanCurrentNumber(color, cottonPart));
        if (quantity.isPresent()) {
            return quantity.get();
        }
        return 0;
    }

    public int getQuantityOfSocksByColorAndCottonPartGreaterThanCurrentNumber(String color, int cottonPart) {
        Optional<Integer> quantity = Optional.ofNullable
                (socksRepo.findQuantityOfSocksByColorAndCottonPartGreaterThanCurrentNumber(color, cottonPart));
        if (quantity.isPresent()) {
            return quantity.get();
        }
        return 0;
    }

    public int getQuantityOfSocksByColorAndCottonPartEqualsToCurrentNumber(String color, int cottonPart) {
        Optional<Integer> quantity = Optional.ofNullable
                (socksRepo.findQuantityOfSocksByColorAndCottonPartEqualsToCurrentNumber(color, cottonPart));
        if (quantity.isPresent()) {
            return quantity.get();
        }
        return 0;
    }

    public int getAllSocksByOperationAndCurrentParameters(String color, int cottonPart, String operation) {
        validation.getSocksValidator(color, cottonPart);

        if (operation.equals("moreThan")) {
            return getQuantityOfSocksByColorAndCottonPartGreaterThanCurrentNumber(color, cottonPart);
        }
        if (operation.equals("lessThan")) {
            return getQuantityOfSocksByColorAndCottonPartLessThanCurrentNumber(color, cottonPart);
        }
        if (operation.equals("equals")) {
            return getQuantityOfSocksByColorAndCottonPartEqualsToCurrentNumber(color, cottonPart);
        }
        throw new SocksException(SocksErrorCode.INCORRECT_PARAMS);
    }
}