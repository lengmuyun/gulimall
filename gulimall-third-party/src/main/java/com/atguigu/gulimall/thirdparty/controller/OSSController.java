package com.atguigu.gulimall.thirdparty.controller;

import com.atguigu.gulimall.thirdparty.service.OSSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OSSController {

    @Autowired
    private OSSService ossService;

    @RequestMapping("/oss/policy")
    public Map<String, String> policy() {
        return ossService.policy();
    }

}
