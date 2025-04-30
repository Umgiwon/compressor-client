package com.miro.compressorclient.global.config;

import net.devh.boot.grpc.client.autoconfigure.GrpcClientAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(GrpcClientAutoConfiguration.class)
public class GrpcClientConfig {
    // gRPC 클라이언트 자동 설정 활성화
}
