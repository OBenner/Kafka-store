package com.obenner.kafkastore.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.obenner.kafkastore.config.KStreamProcessorX;
import com.obenner.kafkastore.config.StoreName;
import com.obenner.kafkastore.model.Ac;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.ValueJoiner;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;


import java.io.IOException;


@EnableBinding(KStreamProcessorX.class)
@Slf4j
public class StreamService {


    private StoreName storeName;

    @Autowired
    public StreamService(StoreName storeName) {
        this.storeName=storeName;
    }

    @StreamListener
    public void process(@Input("one") KTable<Long, String> one,
                        @Input("two") KTable<Long, String> two) {


          KTable<Long, String> stringKTable =
                  one.leftJoin(two, new ValueJoiner<String, String, String>() {
                      @Override
                      public String apply(String value1, String value2) {

                          return mapper(value1, value2);
                      }
                  }, Materialized.<Long, String, KeyValueStore<Bytes, byte[]>>as(storeName.getStoreName()).withKeySerde(Serdes.Long()).withValueSerde(Serdes.String()).withCachingEnabled());


    }

    private String mapper(String value1, String value2) {
        Ac ac1 = null;
        Ac ac2 = null;
        try {
            ac1 = new ObjectMapper().readValue(value1, Ac.class);
            log.info("request object from stream [{}]", ac1);
            if (value2 != null) ac2 = new ObjectMapper().readValue(value2, Ac.class);
            log.info("object from store [{}]", ac2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Ac newac = null;
        if (ac2 == null) {
            newac = new Ac(ac1.getId(), ac1.getFirstName(), ac1.getLastName(), ac1.getPhone(), ac1.getAmount());
            log.info("add new object to store");
        } else {
            newac = new Ac(ac2.getId(), ac1.getFirstName(), ac1.getLastName(), ac1.getPhone(), ac1.getAmount());
            log.info("update object from store");
        }
        String result = null;
        try {
            result = new ObjectMapper().writeValueAsString(newac);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.info("result [{}]", result);
        return result;
    }

}
