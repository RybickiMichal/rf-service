package com.mprybicki.rfservice.rfdata.service;


import com.mprybicki.rfservice.common.model.RFData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
public class RFDataSenderService {

    private KafkaTemplate<String, RFData> kafkaTemplate;

    @Value("${kafka.rf.data.topic}")
    private String kafkaTopic;

    public RFDataSenderService(KafkaTemplate<String, RFData> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(RFData rfData) {
        ListenableFuture<SendResult<String, RFData>> future = kafkaTemplate.send(kafkaTopic, rfData);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, RFData> result) {
                log.info("Sent message: " + rfData.toString());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message : " + rfData.toString(), ex);
            }
        });
    }
}