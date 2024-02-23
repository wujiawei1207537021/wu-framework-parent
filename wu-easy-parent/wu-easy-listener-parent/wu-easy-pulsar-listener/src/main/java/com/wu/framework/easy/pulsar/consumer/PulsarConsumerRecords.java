package com.wu.framework.easy.pulsar.consumer;

import com.wu.framework.easy.listener.core.consumer.ConsumerRecord;
import com.wu.framework.easy.listener.core.consumer.ConsumerRecords;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.Messages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class PulsarConsumerRecords implements ConsumerRecords<Object, String> {


    private final Messages records;

    public PulsarConsumerRecords(Messages records) {
        this.records = records;
    }

    public Messages records() {
        return records;
    }


    /**
     * Returns an iterator over elements of columnType {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<ConsumerRecord<Object, String>> iterator() {
        final Iterator<Message> iterator = records.iterator();

        List<ConsumerRecord<Object, String>> messageList = new ArrayList<>();

        while (iterator.hasNext()) {
            final Message next = iterator.next();
            messageList.add(new PulsarConsumerRecord(null, new String(next.getData()), next));
        }
        return messageList.iterator();
    }


}
