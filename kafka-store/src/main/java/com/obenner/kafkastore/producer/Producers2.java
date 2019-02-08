/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.obenner.kafkastore.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obenner.kafkastore.model.Ac;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.Serde;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Soby Chacko
 */
public class Producers2 {

    public static void main(String... args) {

        ObjectMapper mapper = new ObjectMapper();
        Serde<Ac> acSerde = new JsonSerde<>(Ac.class, mapper);

        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
      //  props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS,  false);

        List<Ac> acs = new ArrayList<>();
        Ac ac = new Ac(8,"Oleg", "M", 917, 1);
        acs.add(ac);
        Ac ac2 = new Ac(7, "Jsong", "Jobs", 0, 0);
        acs.add(ac2);
        Ac ac3 = new Ac(6,"SSS111111111SSSSSS", "!!!!!!!!!!!!!!!!!!!!",0,0);
        acs.add(ac3);
        Ac ac4 = new Ac(5,"------", "parker",121222,10000440);
        acs.add(ac4);

        Ac ac5 = new Ac(4,"111", "parker",121222,10000440);
        acs.add(ac5);
        Ac ac6= new Ac(3,"AAAAAAAAAAAAAA", "parker",121222,10000440);
        acs.add(ac6);
        Ac ac7 = new Ac(2,"5555555555", "parker",121222,10000440);
        acs.add(ac7);
        DefaultKafkaProducerFactory<Long, Ac> pf = new DefaultKafkaProducerFactory<>(props);
        KafkaTemplate<Long, Ac> template = new KafkaTemplate(pf, true);
        template.setDefaultTopic("onetopic");
        for (Ac a:acs) {
            System.out.println("Send to kafka "+a);
            template.sendDefault(Long.valueOf(a.getId()), a);
        }


        System.out.println("Complite");
    }
}
