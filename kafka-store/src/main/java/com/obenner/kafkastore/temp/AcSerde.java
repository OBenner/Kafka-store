package com.obenner.kafkastore.temp;//package ru.neoflex.vtb.bq.cppdepositscache.temp;
//
//import org.apache.kafka.common.serialization.Deserializer;
//import org.apache.kafka.common.serialization.Serde;
//import org.apache.kafka.common.serialization.Serializer;
//import ru.neoflex.vtb.bq.cppdepositscache.temp.Ac;
//
//import java.util.Map;
//
//public class AcSerde implements Serde<Ac> {
//private AcSerializer serializer;
//private AcDeserializer deserializer;
//
//    @Override
//    public void configure(Map<String, ?> configs, boolean isKey) {
//
//    }
//
//    @Override
//    public void close() {
//
//    }
//
//    @Override
//    public Serializer<Ac> serializer() {
//        return serializer;
//    }
//
//    @Override
//    public Deserializer<Ac> deserializer() {
//        return deserializer;
//    }
//}
