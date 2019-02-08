package com.obenner.kafkastore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StoreName {
    @Value("${store.name}")
      String storeName;

    public  String getStoreName() {
        return storeName;
    }
}
