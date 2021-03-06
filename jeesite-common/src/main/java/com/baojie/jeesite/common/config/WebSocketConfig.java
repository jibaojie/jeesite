package com.baojie.jeesite.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author ：冀保杰
 * @date：2018-08-23
 * @desc：
 */
@Configuration
@ConditionalOnProperty(prefix = "jeesite", name = "websocket", havingValue = "true")
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
