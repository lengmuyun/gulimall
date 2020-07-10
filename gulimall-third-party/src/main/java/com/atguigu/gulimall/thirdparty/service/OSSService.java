package com.atguigu.gulimall.thirdparty.service;

import java.util.Map;

public interface OSSService {

    /**
     * OSS服务端签名
     * @return
     */
    Map<String, String> policy();

}
