package com.obenner.kafkastore.temp;//package ru.neoflex.vtb.bq.cppdepositscache.temp;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.common.header.Headers;
//import org.apache.kafka.common.serialization.Serializer;
//
//import java.util.Map;
//
//@Slf4j
//public class AcSerializer implements Serializer<Ac> {
//
//        private ObjectMapper objectMapper = new ObjectMapper();
//
//        @Override
//        public void configure(Map<String, ?> configs, boolean isKey) {
//        }
//
////    @Override
////    public byte[] serialize(String topic, Ac data) {
////        return new byte[0];
////    }
//
//    @Override
//    public byte[] serialize(String topic, Headers headers, Ac data) {
//        try {
//            return objectMapper.writeValueAsBytes(data);
//        } catch (JsonProcessingException e) {
//            log.error("Unable to serialize object {}", data, e);
//            return null;
//        }
//    }
//
//    @Override
//        public byte[] serialize(String topic, Ac data) {
//            try {
//                return objectMapper.writeValueAsBytes(data);
//            } catch (JsonProcessingException e) {
//                log.error("Unable to serialize object {}", data, e);
//                return null;
//            }
//        }
//
//        @Override
//        public void close() {
//        }
//    }
//
