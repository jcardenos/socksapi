package com.task.cibinternstesttask.services;

import com.task.cibinternstesttask.components.Validation;
import com.task.cibinternstesttask.entity.SocksEntity;
import com.task.cibinternstesttask.repositories.SocksRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SocksServiceTest {
    private SocksService socksService;

    @Mock
    private SocksRepo socksRepo;

    @Mock
    private SocksEntity socks;

    @Mock
    private Validation validation;

    @BeforeEach
    public void setUp() {
        socksRepo = mock(SocksRepo.class);
        validation = mock(Validation.class);
        socksService = new SocksService(socksRepo, validation);
    }

    @Test
    void incomeNewSocks() {
        socks = mock(SocksEntity.class);
        when(socksRepo.findQuantityOfSocksByColorAndCottonPartEqualsToCurrentNumber(socks.getColor(), socks.getCottonPart())).thenReturn(null);
        socksService.incomeSocks(socks);
        verify(socksRepo, times(1)).save(socks);
        verify(socksRepo, never()).incomeSocksThatAlreadyExist(socks.getColor(), socks.getCottonPart(), socks.getQuantity());
    }

    @Test
    void incomeSocksThatAlreadyExist() {
        socks = mock(SocksEntity.class);

        when(socksRepo.findQuantityOfSocksByColorAndCottonPartEqualsToCurrentNumber(socks.getColor(), socks.getCottonPart())).thenReturn(1);
        socksService.incomeSocks(socks);
        verify(socksRepo, times(1)).incomeSocksThatAlreadyExist(socks.getColor(), socks.getCottonPart(), socks.getQuantity());
        verify(socksRepo, never()).save(socks);
    }

    @Test
    void outcomeSocks() {
        socks = mock(SocksEntity.class);

        when(socksRepo.findQuantityOfSocksByColorAndCottonPartEqualsToCurrentNumber(socks.getColor(), socks.getCottonPart())).thenReturn(1);
        socksService.outcomeSocks(socks);
        verify(socksRepo, times(1)).outcomeSocks(socks.getColor(), socks.getCottonPart(), socks.getQuantity());
    }

    @Test
    void getQuantityOfSocksByColorAndCottonPartLessThanCurrentNumber() {
        socks = mock(SocksEntity.class);
        int actualResult = 5;
        int expectedResult = 0;
        when(socksRepo.findQuantityOfSocksByColorAndCottonPartLessThanCurrentNumber(socks.getColor(), socks.getCottonPart())).thenReturn(actualResult);
        expectedResult = socksService.getQuantityOfSocksByColorAndCottonPartLessThanCurrentNumber(socks.getColor(), socks.getCottonPart());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getQuantityOfSocksByColorAndCottonPartLessThanCurrentNumberIfSockHasNotFound() {
        socks = mock(SocksEntity.class);
        int actualResult = 0;
        int expectedResult = 0;
        when(socksRepo.findQuantityOfSocksByColorAndCottonPartLessThanCurrentNumber(socks.getColor(), socks.getCottonPart())).thenReturn(null);
        expectedResult = socksService.getQuantityOfSocksByColorAndCottonPartLessThanCurrentNumber(socks.getColor(), socks.getCottonPart());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getQuantityOfSocksByColorAndCottonPartGreaterThanCurrentNumber() {
        socks = mock(SocksEntity.class);
        int actualResult = 5;
        int expectedResult = 0;
        when(socksRepo.findQuantityOfSocksByColorAndCottonPartGreaterThanCurrentNumber(socks.getColor(), socks.getCottonPart())).thenReturn(actualResult);
        expectedResult = socksService.getQuantityOfSocksByColorAndCottonPartGreaterThanCurrentNumber(socks.getColor(), socks.getCottonPart());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getQuantityOfSocksByColorAndCottonPartGreaterThanCurrentNumberIfSockHasNotFound() {
        socks = mock(SocksEntity.class);
        int actualResult = 0;
        int expectedResult = 0;
        when(socksRepo.findQuantityOfSocksByColorAndCottonPartGreaterThanCurrentNumber(socks.getColor(), socks.getCottonPart())).thenReturn(null);
        expectedResult = socksService.getQuantityOfSocksByColorAndCottonPartGreaterThanCurrentNumber(socks.getColor(), socks.getCottonPart());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getQuantityOfSocksByColorAndCottonPartEqualsToCurrentNumber() {
        socks = mock(SocksEntity.class);
        int actualResult = 5;
        int expectedResult = 0;
        when(socksRepo.findQuantityOfSocksByColorAndCottonPartEqualsToCurrentNumber(socks.getColor(), socks.getCottonPart())).thenReturn(actualResult);
        expectedResult = socksService.getQuantityOfSocksByColorAndCottonPartEqualsToCurrentNumber(socks.getColor(), socks.getCottonPart());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getQuantityOfSocksByColorAndCottonPartEqualsToCurrentNumberIfSockHasNotFound() {
        socks = mock(SocksEntity.class);
        int actualResult = 0;
        int expectedResult = 0;
        when(socksRepo.findQuantityOfSocksByColorAndCottonPartEqualsToCurrentNumber(socks.getColor(), socks.getCottonPart())).thenReturn(null);
        expectedResult = socksService.getQuantityOfSocksByColorAndCottonPartEqualsToCurrentNumber(socks.getColor(), socks.getCottonPart());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getAllSocksByOperationAndCurrentParameters() {
    }
}