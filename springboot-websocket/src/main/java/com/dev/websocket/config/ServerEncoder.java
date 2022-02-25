package com.dev.websocket.config;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * websocket 编码
 */
public class ServerEncoder implements Encoder.Text<AjaxResult> {
    @Override
    public String encode(AjaxResult ajaxResult) throws EncodeException {
        JSON parse = JSONUtil.parseObj(ajaxResult);
        return parse.toString();
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
