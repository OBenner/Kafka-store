package com.obenner.kafkastore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obenner.kafkastore.config.StoreName;
import com.obenner.kafkastore.model.Ac;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.connect.errors.NotFoundException;
import org.apache.kafka.streams.state.HostInfo;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;


@Service
@Slf4j
@AllArgsConstructor
public class StoreService {


    @Autowired
private StoreName storeName;

    private InteractiveQueryService interactiveQueryService;



    public Mono<Ac> getAccInsideKafka(long id) {
        log.info("request for id [{}]", id);
        final ReadOnlyKeyValueStore<Long, String> store =
                interactiveQueryService.getQueryableStore(storeName.getStoreName(), QueryableStoreTypes.keyValueStore());
        Ac ob = null;
        try {
            ob = new ObjectMapper().readValue(store.get(id), Ac.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ob == null) {
            throw new NotFoundException("not found");
        }
        log.info("response for id [{}]", ob);
        return Mono.just(ob);
    }


    public Ac getObjClient(long request) {

        HostInfo hostInfo = interactiveQueryService.getHostInfo(storeName.getStoreName(), request, new LongSerializer());


        log.info("hostInfo for id [{}]", hostInfo);
        if (hostInfo == null) {
            throw new RuntimeException("Not in Kafka");
        }

        if (interactiveQueryService.getCurrentHostInfo().equals(hostInfo)) {
            log.info("obj info request served from same host: " + hostInfo);

            final ReadOnlyKeyValueStore<Long, String> store =
                    interactiveQueryService.getQueryableStore(storeName.getStoreName(), QueryableStoreTypes.keyValueStore());

            Ac ob = null;
            try {
                String value = store.get(request);
                if (value == null) {
                    throw new RuntimeException("Not Found in Kafka");
                }
                ob = new ObjectMapper().readValue(value, Ac.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return ob;
        } else {
            log.info("obj info request served from different host: " + hostInfo);
            WebClient webClient = WebClient.builder().baseUrl("http://" + hostInfo.host() + ":" + hostInfo.port()).build();

            ClientResponse response = webClient.post()
                    .uri("/share?id=" + request)
                    .exchange().block();

            if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
                throw new RuntimeException("Error from kafka");
            }
            Ac acc = response.bodyToMono(Ac.class).block();
            log.info("response for id [{}]", acc);
            return acc;
        }

    }


}
