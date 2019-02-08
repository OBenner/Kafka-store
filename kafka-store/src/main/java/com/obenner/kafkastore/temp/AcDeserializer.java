package com.obenner.kafkastore.temp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obenner.kafkastore.model.Ac;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

@Slf4j
public class AcDeserializer implements Deserializer<Ac> {

        private ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void configure(Map<String, ?> configs, boolean isKey) {
        }

        @Override
        public Ac deserialize(String topic, byte[] data) {
            try {
                return objectMapper.readValue(new String(data, "UTF-8"), Ac.class);
            } catch (Exception e) {
                log.error("Unable to deserialize message {}", data, e);
                return null;
            }
        }

        @Override
        public void close() {
        }
    }

