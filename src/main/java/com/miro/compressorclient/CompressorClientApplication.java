package com.miro.compressorclient;

import com.miro.compressorclient.api.grpc.CompressorClientService;
import com.miro.compressorclient.api.modbus.ModbusRtuReader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CompressorClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompressorClientApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(ModbusRtuReader reader, CompressorClientService clientService) {
        return args -> {
            while (true) {
                int temp = reader.readTemperature();
                clientService.sendTemperature(temp);
                Thread.sleep(5000); // 5초마다 데이터 전송
            }
        };
    }
}
