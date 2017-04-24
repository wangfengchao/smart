package com.smart.kafka;

import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * kafka producer
 * Created by fc.w on 2017/4/14.
 */
public class KafkaProducerTest extends Thread {

    private static Logger LOG = LoggerFactory.getLogger(KafkaProducerTest.class);

    private final KafkaProducer<String, String> producer;
    private final String topic;
    private final boolean isAsync;

    public KafkaProducerTest(String topic, boolean isAsync) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "h5:9092,h6:9092,h7:9092");
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "MsgProducer");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        this.producer = new KafkaProducer<String, String>(properties);
        this.topic = topic;
        this.isAsync = isAsync;
    }

    @Override
    public void run() {
        int msgNo = 0;

        while (true) {
            String msg = "Msg: " + msgNo;
            String key = msgNo + "";
            if (isAsync) {//异步
                System.out.println(key);
                producer.send(new ProducerRecord<String, String>(this.topic,msg));
//                producer.send(new ProducerRecord<String, String>(this.topic, key, msg));
            } else {//同步
                producer.send(new ProducerRecord<String, String>(this.topic, key, msg),
                        new KafkaProducerCallback(System.currentTimeMillis(), key, msg));
            }
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送消息后的回调函数
     */
    class KafkaProducerCallback implements Callback {

        private final long startTime;
        private final String key;
        private final String msg;

        public KafkaProducerCallback(long startTime, String key, String msg) {
            this.startTime = startTime;
            this.key = key;
            this.msg = msg;
        }

        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            if (recordMetadata != null) {
                System.out.println(msg + " be sended to partition no : " + recordMetadata.partition());
            }
        }
    }

    public static void main(String[] args) {
        new KafkaProducerTest("recommend_group_topic",true).start();
    }

}
