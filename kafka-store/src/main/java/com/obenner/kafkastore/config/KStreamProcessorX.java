package com.obenner.kafkastore.config;


import org.apache.kafka.streams.kstream.KTable;
import org.springframework.cloud.stream.annotation.Input;

public interface KStreamProcessorX {

    @Input("one")
    KTable<Long, String> one();

    @Input("two")
    KTable<Long, String> two();



}
