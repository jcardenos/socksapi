package com.task.cibinternstesttask.repositories;

import com.task.cibinternstesttask.entity.SocksEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

@Transactional
public interface SocksRepo extends CrudRepository<SocksEntity, Long> {
    @Query("SELECT SUM(s.quantity) FROM SocksEntity s WHERE s.color = :color and s.cottonPart < :cottonPart")
    Integer findQuantityOfSocksByColorAndCottonPartLessThanCurrentNumber(
            @Param("color") String color,
            @Param("cottonPart") Integer cottonPart
    );

    @Query("SELECT SUM(s.quantity) FROM SocksEntity s WHERE s.color = :color and s.cottonPart > :cottonPart")
    Integer findQuantityOfSocksByColorAndCottonPartGreaterThanCurrentNumber(
            @Param("color") String color,
            @Param("cottonPart") Integer cottonPart
    );

    @Query("SELECT SUM(s.quantity) FROM SocksEntity s WHERE s.color = :color and s.cottonPart = :cottonPart")
    Integer findQuantityOfSocksByColorAndCottonPartEqualsToCurrentNumber(
            @Param("color") String color,
            @Param("cottonPart") Integer cottonPart
    );

    @Transactional
    @Modifying
    @Query("UPDATE SocksEntity s SET s.quantity = s.quantity + :quantity WHERE " +
            "s.color = :color and s.cottonPart = :cottonPart")
    void incomeSocksThatAlreadyExist(
            @Param("color") String color,
            @Param("cottonPart") Integer cottonPart,
            @Param("quantity") Integer quantity
    );

    @Transactional
    @Modifying
    @Query("UPDATE SocksEntity s SET s.quantity = s.quantity - :quantity WHERE " +
            "s.color = :color and s.cottonPart = :cottonPart")
    void outcomeSocks(
            @Param("color") String color,
            @Param("cottonPart") Integer cottonPart,
            @Param("quantity") Integer quantity
    );
}