package com.fudan.xk.util;

import org.springframework.stereotype.Component;

/**
 * @author cailei.lu
 * @description
 * @date 2018/8/3
 */
@Component
public interface TokenGenerator {

    public String generate(String... strings);

}
