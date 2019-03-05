package com.transaction.controller;

import com.transaction.dto.TransactionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class HealthController {


    @GetMapping(path="/health")
    public ResponseEntity<String>  get() {
        return new ResponseEntity<>("healthy",HttpStatus.OK);
    }
}
