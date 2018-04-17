package com.smart.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by fc.w on 2018/3/15.
 */
public class KafkaDataByOffset {

    public static void main(String[] args) throws Exception {


        //Kafka consumer configuration settings
        String topicName = "click";
        Properties props = new Properties();

        props.put("bootstrap.servers", "hadoop3:9092,hadoop4:9092,hadoop5:9092,hadoop6:9092");
        props.put("group.id", "aa");
        props.put("enable.auto.commit","false");
        props.put("auto.commit.interval.ms","1000");
        props.put("session.timeout.ms","30000");
        props.put("auto.offset.reset","earliest");
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, Object> consumer = new KafkaConsumer<String, Object>(props);
        // consumer.subscribe(Arrays.asList(topicName));
        consumer.assign(Arrays.asList(new TopicPartition(topicName, 0)));
//        consumer.seekToBeginning(Arrays.asList(new TopicPartition(topicName, 0)));//不改变当前offset
        consumer.seek(new TopicPartition(topicName, 0), 1009845);//不改变当前offset

        while (true) {
            ConsumerRecords<String, Object> records = consumer.poll(100);
            for (ConsumerRecord<String, Object> record : records){
                System.out.println(record.toString());
            }
        }

    }

}
