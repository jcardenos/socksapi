package com.task.cibinternstesttask.controllers;

import com.task.cibinternstesttask.entity.SocksEntity;
import com.task.cibinternstesttask.services.SocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/socks")
public class SocksController {
    @Autowired
    private SocksService socksService;

    @PostMapping("/income")
    public ResponseEntity<String> incomeSocks(@RequestBody SocksEntity socks) {
            socksService.incomeSocks(socks);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/outcome")
    public ResponseEntity<String> outcomeSocks(@RequestBody SocksEntity socks) {
            socksService.outcomeSocks(socks);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<String> getAllSocks(@RequestParam("color") String color,
                                      @RequestParam("operation") String operation,
                                      @RequestParam("cottonPart") int cottonPart) {
        Integer socks = socksService.getAllSocksByOperationAndCurrentParameters(color, cottonPart, operation);
        return new ResponseEntity<>(socks.toString(), HttpStatus.OK);
    }
}