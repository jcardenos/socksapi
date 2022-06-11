package com.task.cibinternstesttask.controllers;

import com.task.cibinternstesttask.entity.SocksEntity;
import com.task.cibinternstesttask.exceptions.ColorIsEmptyException;
import com.task.cibinternstesttask.exceptions.QuantityIsLessThanZeroOrEqualsToZeroException;
import com.task.cibinternstesttask.exceptions.QuantityOfSocksWithCurrentParametersIsNullOrLessThanEnteredNumberException;
import com.task.cibinternstesttask.exceptions.СottonPartIsLessThanZeroOrHigherThanHundredException;
import com.task.cibinternstesttask.services.SocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/socks")
public class SocksController {
    @Autowired
    private SocksService socksService;

    @PostMapping("/income")
    public ResponseEntity incomeSocks(@RequestBody SocksEntity socks) {
        try {
            socksService.incomeSocks(socks);
            return ResponseEntity.ok("Вы успешно внесли в базу пришедшие на склад носки:\nКоличество: "
                    + socks.getQuantity() + "\nПроцентное содержание хлопка: " + socks.getCottonPart() +
                    "\nЦвет: " + socks.getColor());
        } catch (QuantityIsLessThanZeroOrEqualsToZeroException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (СottonPartIsLessThanZeroOrHigherThanHundredException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ColorIsEmptyException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Что-то пошло не так :(");
        }
    }

    @PostMapping("/outcome")
    public ResponseEntity outcomeSocks(@RequestBody SocksEntity socks) {
        try {
            socksService.outcomeSocks(socks);
            return ResponseEntity.ok("Вы зафискировали уход носков со склада:\nКоличество: " + socks.getQuantity()
                    + "\nПроцентое содержание хлопка: " + socks.getCottonPart() + "\nЦвет: " + socks.getColor());
        } catch (QuantityIsLessThanZeroOrEqualsToZeroException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (СottonPartIsLessThanZeroOrHigherThanHundredException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ColorIsEmptyException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (QuantityOfSocksWithCurrentParametersIsNullOrLessThanEnteredNumberException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Что-то пошло не так :(");
        }
    }

    @GetMapping
    public ResponseEntity getAllSocks(@RequestParam String color, @RequestParam String operation,
                                      @RequestParam int cottonPart) {
        try {
            if (operation.equals("lessThan")) {
                int quantity = socksService.getQuantityOfSocksByColorAndCottonPartLessThanCurrentNumber(color, cottonPart);
                return ResponseEntity.ok().body(quantity + " шт. носков на складе с содержанием хлопка меньше "
                        + cottonPart + " и цвета " + color);
            }
            if (operation.equals("moreThan")) {
                int quantity = socksService.getQuantityOfSocksByColorAndCottonPartGreaterThanCurrentNumber(color,
                        cottonPart);
                return ResponseEntity.ok().body(quantity + " шт. носков на складе с содержанием хлопка больше "
                        + cottonPart + " и цвета " + color);
            }
            if (operation.equals("equals")) {
                int quantity = socksService.getQuantityOfSocksByColorAndCottonPartEqualsToCurrentNumber(color,
                        cottonPart);
                return ResponseEntity.ok().body(quantity + " шт. носков на складе с содержанием хлопка, равным "
                        + cottonPart + " и цвета " + color);
            }
            return ResponseEntity.badRequest().body("Что-то пошло не так: Несуществующий operation. Список " +
                    "существующих: lessThan, moreThan или equals");
        } catch (NullPointerException e) {
            return ResponseEntity.ok().body("На складе нет носков по указанным вам параметрам.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Что-то пошло не так");
        }
    }
}