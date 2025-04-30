package com.miro.compressorclient.api.modbus;

import com.miro.compressorclient.global.config.ModbusProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ModbusRtuReader {

    private final ModbusProperties properties;
    private final ModbusMaster master;


    @PostConstruct
    public void init() {
        SerialPortWrapper wrapper = new SerialPortWrapperImpl(
                properties.getPortName()
                , properties.getBaudRate()
                , properties.getDataBits()
                , properties.getStopBits()
                , properties.getParity()
        );

        ModbusFactory factory = new ModBusFactory();
        master = factory.createRtuMaster(wrapper);

        try {
            master.init();
            log.info("Modbus RTU 연결 성공");
        } catch (ModbusInitException e) {
            log.error("Modbus 초기화 실패", e);
        }
    }

    public int readTemperature() {
        try {
            BaseLocator<Number> locator = BaseLocator.holdingRegister(
                    properties.getSlaveId(), 0, DataType.TWO_BYTE_INT_SIGNED
            );
            Number value = master.getValue(locator);
            log.debug("읽은 온도값: {}", value);
            return value.intValue();
        } catch (Exception e) {
            log.error("Modbus 읽기 실패", e);
            return -999;
        }
    }
}
