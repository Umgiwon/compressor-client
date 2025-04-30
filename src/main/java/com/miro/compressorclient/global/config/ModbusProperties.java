package com.miro.compressorclient.global.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "modbus")
public class ModbusProperties {

    private String portName;
    private int baudRate;
    private int dataBits;
    private int stopBits;
    private int parity;
    private int slaveId;
}
