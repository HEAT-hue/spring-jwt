package com.jwt.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
public class DemoController {

    @GetMapping("/admin")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> sayAdminHello() {
        return ResponseEntity.status(HttpStatus.OK).body("Hello World from secured Admin endpoint");
    }

    @GetMapping("/user")
    public ResponseEntity<String> sayUserHello() {
        System.out.println("Req user received");
        return ResponseEntity.status(HttpStatus.OK).body("Hello World from secured User endpoint");
    }

    @GetMapping("/all")
    public ResponseEntity<String> sayAllHello() {
        System.out.println("Req all received");
        return ResponseEntity.status(HttpStatus.OK).body("Hello World from role free secured endpoint");
    }
}
