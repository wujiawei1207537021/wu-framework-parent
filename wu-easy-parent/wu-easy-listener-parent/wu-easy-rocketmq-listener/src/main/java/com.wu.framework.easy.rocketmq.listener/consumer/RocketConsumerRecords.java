package com.wu.framework.easy.rocketmq.listener.consumer;

import com.wu.framework.easy.listener.core.consumer.ConsumerRecord;
import com.wu.framework.easy.listener.core.consumer.ConsumerRecords;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;


public class RocketConsumerRecords<Schema, Payload> implements ConsumerRecords<Schema, Payload> {


    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<ConsumerRecord<Schema, Payload>> iterator() {
        final Iterator<ConsumerRecord<Schema, Payload>> iterator = new Iterator<ConsumerRecord<Schema, Payload>>() {

            /**
             * Returns {@code true} if the iteration has more elements.
             * (In other words, returns {@code true} if {@link #next} would
             * return an element rather than throwing an exception.)
             *
             * @return {@code true} if the iteration has more elements
             */
            @Override
            public boolean hasNext() {
                return false;
            }

            /**
             * Returns the next element in the iteration.
             *
             * @return the next element in the iteration
             * @throws NoSuchElementException if the iteration has no more elements
             */
            @Override
            public ConsumerRecord<Schema, Payload> next() {
                return null;
            }
        };
        return iterator;
    }

    @Override
    public void forEach(Consumer<? super ConsumerRecord<Schema, Payload>> action) {
        ConsumerRecords.super.forEach(action);
    }
}
