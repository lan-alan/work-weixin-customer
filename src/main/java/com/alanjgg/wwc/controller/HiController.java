package com.alanjgg.wwc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alan
 * @Description
 * @date 2022/3/11
 */
@Slf4j
@RestController
@RequestMapping("hi")
public class HiController {

    @GetMapping("sayHi")
    public String sayHi(@RequestParam(value = "name") String name) {
        log.info("name = {}", name);
        return "Hi " + name;
    }

}
