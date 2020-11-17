package com.val.riazanski;

import java.util.ArrayList;
import java.util.List;

public class ProducerConsumer<T> {
    //    fields
    private static final int BUFFER_MAX = 9;
    private List<T> buffer = new ArrayList<T>();
//    constructors
    public ProducerConsumer(List list) {
        this.buffer = list;
    }

    //    methods
    synchronized void produce(T value) throws InterruptedException {
        while (buffer.size() == BUFFER_MAX) {
            wait();
        }
        buffer.add(value);
        notifyAll();
    }

    synchronized T consume() throws InterruptedException {
        while (buffer.size() == 0) {
            wait();
        }
        T result = buffer.remove(0);
        notifyAll();
        return result;
    }
    public List getBuffer() {
        return this.buffer;
    }
}
