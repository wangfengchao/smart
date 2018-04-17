package com.smart.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndTimestamp;
import org.apache.kafka.common.TopicPartition;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by fc.w on 2018/3/15.
 */
public class KafkaOffsetByTimeStamp {

    public static void main(String[] args) {
        String bootstrapServers = "hadoop3:9092,hadoop4:9092,hadoop5:9092,hadoop6:9092";
        String topic = "click";
        String groupId = "aa";
        String startTime = "20180315000000";
        String endTime = "20180315030000";
        getOffsetsForTimes(bootstrapServers, topic, groupId, startTime, endTime);
    }

    public static void getOffsetsForTimes(String bootstrapServers, String topic, String groupId, String startTime, String endTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        long start = 0;
        long end = 0;
        try{
            start = sdf.parse(startTime).getTime();
            end = sdf.parse(endTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Properties props = new Properties();
        props.put("bootstrap.servers",bootstrapServers);
        props.put("group.id",groupId);
        props.put("enable.auto.commit","false");
        props.put("auto.commit.interval.ms","1000");
        props.put("session.timeout.ms","30000");
        props.put("auto.offset.reset","earliest");
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String,String>(props);
        TopicPartition topicPartition0 = new TopicPartition(topic,0);
        TopicPartition topicPartition1 = new TopicPartition(topic,1);
        TopicPartition topicPartition2 = new TopicPartition(topic,2);

        // 通过开始时间戳获取offset
        Map<TopicPartition, Long> startMap = new HashMap<TopicPartition, Long>();
        startMap.put(topicPartition0, start);
        startMap.put(topicPartition1, start);
        startMap.put(topicPartition2, start);
        Map<TopicPartition, OffsetAndTimestamp> startOffsetMap = consumer.offsetsForTimes(startMap);

        // 通过结束时间戳获取offset
        Map<TopicPartition, Long> endMap = new HashMap<TopicPartition, Long>();
        endMap.put(topicPartition0, end);
        endMap.put(topicPartition1, end);
        endMap.put(topicPartition2, end);
        Map<TopicPartition, OffsetAndTimestamp> endOffsetMap = consumer.offsetsForTimes(endMap);

        // 获取每个Partition 开始 Offset
        long partition0StartOffset = 0;
        long partition1StartOffset = 0;
        long partition2StartOffset = 0;
        if (startOffsetMap.get(topicPartition0) != null) {
            partition0StartOffset = startOffsetMap.get(topicPartition0).offset();
        }
        if(startOffsetMap.get(topicPartition1) != null){
            partition1StartOffset = startOffsetMap.get(topicPartition1).offset();
        }
        if(startOffsetMap.get(topicPartition2) != null){
            partition2StartOffset = startOffsetMap.get(topicPartition2).offset();
        }

        // 获取每个Partition 结束 Offset
        long partition0EndOffset = 0;
        long partition1EndOffset = 0;
        long partition2EndOffset = 0;
        if (endOffsetMap.get(topicPartition0) != null) {
            partition0EndOffset = endOffsetMap.get(topicPartition0).offset();
        }
        if(endOffsetMap.get(topicPartition1) != null){
            partition1EndOffset = endOffsetMap.get(topicPartition1).offset();
        }
        if(endOffsetMap.get(topicPartition2) != null){
            partition2EndOffset = endOffsetMap.get(topicPartition2).offset();
        }

        System.out.println("partition0StartOffset: " + partition0StartOffset + "partition0EndOffset: "+ partition0EndOffset);
        System.out.println("partition1StartOffset: " + partition1StartOffset + "partition1EndOffset: "+ partition1EndOffset);
        System.out.println("partition2StartOffset: " + partition2StartOffset + "partition2EndOffset: "+ partition2EndOffset);

        for(Map.Entry<TopicPartition,OffsetAndTimestamp> entry : startOffsetMap.entrySet()){
            System.out.println(entry);
            System.out.println(entry.getKey() +"    "+ entry.getValue());
        }
    }

}
