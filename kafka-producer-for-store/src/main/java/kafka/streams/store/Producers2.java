

package kafka.streams.store;

import com.fasterxml.jackson.databind.ObjectMapper;
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
      // props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS,  false);

        List<Ac> acs = new ArrayList<>();
        Ac ac = new Ac(1,"Name1", "LastName1", 917, 1);
        acs.add(ac);
        Ac ac2 = new Ac(2, "Name2", "LastName2", 0, 0);
        acs.add(ac2);
        Ac ac3 = new Ac(3,"Name3", "LastName3",0,0);
        acs.add(ac3);
        Ac ac4 = new Ac(4,"Name4", "LastName4",121222,10000440);
        acs.add(ac4);
        Ac ac5 = new Ac(5,"Name5", "LastName5",121222,10000440);
        acs.add(ac5);
        Ac ac6= new Ac(6,"Name6", "LastName6",121222,10000440);
        acs.add(ac6);
        Ac ac7 = new Ac(7,"Name7", "LastName7",121222,10000440);
        acs.add(ac7);
        DefaultKafkaProducerFactory<Long, Ac> pf = new DefaultKafkaProducerFactory<>(props);
        KafkaTemplate<Long, Ac> template = new KafkaTemplate(pf, true);
        template.setDefaultTopic("onetopic"); //topic from store
        for (Ac a:acs) {
            System.out.println("Send to kafka "+a);
            template.sendDefault(Long.valueOf(a.getId()), a);
        }

    }
}
