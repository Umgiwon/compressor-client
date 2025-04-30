package com.miro.compressorclient.api.grpc;

import io.grpc.stub.StreamObserver;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CompressorClientService {

    @GrpcClient("compressor-service")
    private CompressoreServiceGrpc.CompressorServiceStub asyncStub;

    private StreamObserver<DataRequest> requestStream;

    @PostConstruct
    public void initStream() {
        requestStream = asyncStub.sendSensorData(new StreamObserver<>() {

            @Override
            public void onNext(DataResponse response) {
                log.info("서버 응답: ", response.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                log.error("gRPC 오류 : ", t);
            }

            @Override
            public void onCompleted() {
                log.info("서버와의 스트림 종료");
            }
        });
    }

    public void sendTemperature(int temperature) {
        if(requestStream != null) {
            DataRequest request = DataRequest.newBuilder()
                    .setDeviceId("COMP-001")
                    .setTemperature(temperature)
                    .getTimestamp(System.currentTimeMillis())
                    .build();
            requestStream.onNext(request);
        }
    }
}
