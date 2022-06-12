package com.task.cibinternstesttask.services;

import com.task.cibinternstesttask.entity.SocksEntity;
import com.task.cibinternstesttask.exceptions.SocksErrorCode;
import com.task.cibinternstesttask.exceptions.SocksException;
import com.task.cibinternstesttask.repositories.SocksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.geom.Arc2D;
import java.util.Optional;

@Service
public class SocksService {
    @Autowired
    private SocksRepo socksRepo;

    public SocksEntity incomeSocks(SocksEntity socks) {
        if (socks.getQuantity() <= 0 | socks.getCottonPart() < 0 | socks.getCottonPart() > 100 |
                socks.getColor().isEmpty()) {
            throw new SocksException(SocksErrorCode.INCORRECT_PARAMS);
        }
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
        if (socks.getQuantity() <= 0 | socks.getCottonPart() < 0 | socks.getCottonPart() > 100 |
                socks.getColor().isEmpty()) {
            throw new SocksException(SocksErrorCode.INCORRECT_PARAMS);
        }

        Optional<Integer> quantity = Optional.ofNullable(socksRepo.
                findQuantityOfSocksByColorAndCottonPartEqualsToCurrentNumber(socks.getColor(), socks.getCottonPart()));
        if (quantity.isPresent() && quantity.get() >= socks.getQuantity()) {
            socksRepo.outcomeSocks(socks.getColor(), socks.getCottonPart(), socks.getQuantity());
        } else {
            throw new SocksException(SocksErrorCode.SERVER_ERROR);
        }
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
        if (color.isEmpty() | cottonPart < 0 | cottonPart > 100) {
            throw new SocksException(SocksErrorCode.INCORRECT_PARAMS);
        }
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