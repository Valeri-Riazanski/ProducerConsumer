package com.val.riazanski;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int wordValue = 2;
        Book book;
        book = new Book(wordValue);
        ProducerConsumer producerConsumer;
        producerConsumer = new ProducerConsumer(book.getList());
        Runnable t0 = () -> {
            String threadName = Thread.currentThread().getName();
            for (int i = 0; i < 30; i++) {
                String str = book.createUpperWord(3);
                try {
                    producerConsumer.produce(str);
                    System.out.println(ConsoleColors.PURPLE + threadName + Thread.currentThread().getState() + producerConsumer.getBuffer().toString() + ConsoleColors.RESET);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable t1 = () -> {
            String threadName = Thread.currentThread().getName();
            for (int i = 0; i < 30; i++) {
                try {
                    producerConsumer.consume();
                    System.out.println(ConsoleColors.GREEN + threadName + Thread.currentThread().getState() + producerConsumer.getBuffer().toString() + ConsoleColors.RESET);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(t0);
        service.submit(t1);
        service.shutdown();
    }
}
