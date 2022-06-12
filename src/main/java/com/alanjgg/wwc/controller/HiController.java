package com.alanjgg.wwc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "测试接口", tags = "测试相关接口")
public class HiController {

    @GetMapping("sayHi")
    //方法参数说明，name参数名；value参数说明，备注；dataType参数类型；required 是否必传；defaultValue 默认值
    @ApiImplicitParam(name = "name", value = "用户名称")
    //说明是什么方法(可以理解为方法注释)
    @ApiOperation(value = "获取用户名称", notes = "获取用户名称")
    public String sayHi(@RequestParam(value = "name") String name) {
        log.info("name = {}", name);
        return "Hi " + name;
    }

}
