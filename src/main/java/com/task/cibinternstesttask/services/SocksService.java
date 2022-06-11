package com.task.cibinternstesttask.services;

import com.task.cibinternstesttask.entity.SocksEntity;
import com.task.cibinternstesttask.exceptions.ColorIsEmptyException;
import com.task.cibinternstesttask.exceptions.QuantityIsLessThanZeroOrEqualsToZeroException;
import com.task.cibinternstesttask.exceptions.QuantityOfSocksWithCurrentParametersIsNullOrLessThanEnteredNumberException;
import com.task.cibinternstesttask.exceptions.СottonPartIsLessThanZeroOrHigherThanHundredException;
import com.task.cibinternstesttask.repositories.SocksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SocksService {
    @Autowired
    private SocksRepo socksRepo;

    public SocksEntity incomeSocks(SocksEntity socks) throws QuantityIsLessThanZeroOrEqualsToZeroException,
            СottonPartIsLessThanZeroOrHigherThanHundredException, ColorIsEmptyException {
        if (socks.getQuantity() <= 0) {
            throw new QuantityIsLessThanZeroOrEqualsToZeroException("Что-то пошло не так: Количество пар носков не " +
                    "может быть меньше одной :(");
        }
        if (socks.getCottonPart() < 0 | socks.getCottonPart() > 100) {
            throw new СottonPartIsLessThanZeroOrHigherThanHundredException("Что-то пошло не так: Процентное" +
                    " содержание хлопка не может быть меньше 0 или больше 100 :(");
        }
        if (socks.getColor().isEmpty()) {
            throw new ColorIsEmptyException("Что-то пошло не так: Поле цвета носков не может быть пустым :(");
        }

        Integer quantity = socksRepo.findQuantityOfSocksByColorAndCottonPartEqualsToCurrentNumber(socks.getColor(),
                socks.getCottonPart());
        if (quantity != null && quantity != 0) {
            socksRepo.incomeSocksThatAlreadyExist(socks.getColor(), socks.getCottonPart(), socks.getQuantity());
            return null;
        }
        return socksRepo.save(socks);
    }

    public void outcomeSocks(SocksEntity socks) throws QuantityIsLessThanZeroOrEqualsToZeroException,
            СottonPartIsLessThanZeroOrHigherThanHundredException, ColorIsEmptyException,
            QuantityOfSocksWithCurrentParametersIsNullOrLessThanEnteredNumberException {
        if (socks.getQuantity() <= 0) {
            throw new QuantityIsLessThanZeroOrEqualsToZeroException("Что-то пошло не так: Количество пар носков не " +
                    "может быть меньше одной :(");
        }
        if (socks.getCottonPart() < 0 | socks.getCottonPart() > 100) {
            throw new СottonPartIsLessThanZeroOrHigherThanHundredException("Что-то пошло не так: Процентное" +
                    " содержание хлопка не может быть меньше 0 или больше 100 :(");
        }
        if (socks.getColor().isEmpty()) {
            throw new ColorIsEmptyException("Что-то пошло не так: Поле цвета носков не может быть пустым :(");
        }

        Integer quantity = socksRepo.findQuantityOfSocksByColorAndCottonPartEqualsToCurrentNumber(socks.getColor(),
                socks.getCottonPart());
        if (quantity != null && quantity != 0 && quantity > socks.getQuantity()) {
            socksRepo.outcomeSocks(socks.getColor(), socks.getCottonPart(), socks.getQuantity());
        } else {
            throw new QuantityOfSocksWithCurrentParametersIsNullOrLessThanEnteredNumberException("Что-то пошло не " +
                    "так: Либо на складе нет носков по указанным вам параметрам, либо вы ввели значение " +
                    "пар носков больше, чем есть на складе :(");
        }
    }

    public int getQuantityOfSocksByColorAndCottonPartLessThanCurrentNumber(String color, int cottonPart) {
        return socksRepo.findQuantityOfSocksByColorAndCottonPartLessThanCurrentNumber(color, cottonPart);
    }

    public int getQuantityOfSocksByColorAndCottonPartGreaterThanCurrentNumber(String color, int cottonPart) {
        return socksRepo.findQuantityOfSocksByColorAndCottonPartGreaterThanCurrentNumber(color, cottonPart);
    }

    public int getQuantityOfSocksByColorAndCottonPartEqualsToCurrentNumber(String color, int cottonPart) {
        return socksRepo.findQuantityOfSocksByColorAndCottonPartEqualsToCurrentNumber(color, cottonPart);
    }
}