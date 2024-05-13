package com.uade.tpo.demo.controller;

import com.uade.tpo.demo.entity.dto.TestDTO;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Controller
public class ExampleController {

    @GetMapping("/ping")
    public ResponseEntity ping() {
        return ResponseEntity.ok("pong");
    }

    @PostMapping("/example/post-con-body")
    public ResponseEntity postConBody(@RequestBody TestDTO body) {

        return ResponseEntity.ok(body);

    }

    @PostMapping("/example/post-con-body/{variable}")
    public ResponseEntity postConBody(@RequestBody TestDTO body, @PathVariable String variable) {

        log.info(variable);
        log.info(body.toString());
        return ResponseEntity.ok(body);

    }

    @PostMapping("/example/post-con-body-y-query-param")
    public ResponseEntity postConBodyYQueryParam(@RequestBody TestDTO body, @RequestParam String variable) {

        log.info(variable);
        log.info(body.toString());

        return ResponseEntity.ok(body);
    }

}
