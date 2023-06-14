package com.pervishkond.writersAndReaders;

import java.util.ArrayList;

public class Main {
    public static synchronized void main(String[] args) throws InterruptedException {
        Server server = new Server(2);
        Writers writers = new Writers(server);
        Readers readers = new Readers(server);
        Thread reader1 = new Thread(readers);
        Thread reader2 = new Thread(readers);
        Thread writers1 = new Thread(writers);
        writers.setName("writers1");
        readers.setName("reader1");
        reader1.setName("reader2");
        reader2.setName("reader3");
        writers1.setName("writers2");
        writers.start();
        reader1.start();
        readers.start();
        writers1.start();
        reader2.start();
        Thread.sleep(1000);
    }
}

class Server {
    Runnable place;
    ArrayList<Runnable> writersQueue = new ArrayList<>();
    ArrayList<Runnable> readersQueue = new ArrayList<>();
    private final int placeInWriterServerAmount;
    private final int placeInReaderServerAmount;

    Server(int placesAmount) {
        writersQueue.add(place);
        addReadersPlaces(placesAmount);
        placeInWriterServerAmount = writersQueue.size();
        placeInReaderServerAmount = readersQueue.size();
    }

    private void addReadersPlaces(int placesAmount) {
        for (int i = 0; i < placesAmount; i++) {
            readersQueue.add(place);
        }
    }

    synchronized Boolean getWriterQueueSize() {
        return writersQueue.size() != 0;
    }

    synchronized Boolean getReaderQueuesSize() {
        return readersQueue.size() != 0;
    }

    synchronized Boolean isEmptyWriterQueue() {
        return writersQueue.isEmpty();
    }

    synchronized Boolean isEmptyReadersQueue() {
        return readersQueue.isEmpty();
    }

    synchronized void getPlaceWriterServer() {
        writersQueue.remove(place);
    }

    synchronized void getPlaceReaderServer() {
        readersQueue.remove(place);
    }

    synchronized int getQueueSize(String name) {
        if (name.equalsIgnoreCase("readersQueue")) {
            return placeInReaderServerAmount;
        } else {
            return placeInWriterServerAmount;
        }
    }

    synchronized String getReadersQueueName() {
        return "readersQueue";
    }

    synchronized String getWritersQueueName() {
        return "writersQueue";
    }

    synchronized void isWriterServerBusy() {
        while (isEmptyWriterQueue() || readersQueue.size() < getQueueSize(getReadersQueueName())) {
            System.out.println(Thread.currentThread().getName() + " Ждёт");
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    synchronized void isReaderServerBusy() {
        while (isEmptyReadersQueue() || writersQueue.size() < getQueueSize(getWritersQueueName())) {
            System.out.println(Thread.currentThread().getName() + " Ждёт");
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    synchronized void releaseReaderPlace(Runnable place) {
        readersQueue.add(place);
        notify();
    }

    synchronized void releaseWriterPlace(Runnable place) {
        writersQueue.add(place);
        notify();
    }
}

class Writers extends Thread {
    Runnable place;
    Server serverWriter;

    Writers(Server server) {
        serverWriter = server;
    }

    public void run() {
        while (true) {
            if (serverWriter.getQueueSize(serverWriter.getReadersQueueName()) == serverWriter.readersQueue.size() && serverWriter.getWriterQueueSize()) {
                serverWriter.getPlaceWriterServer();
                System.out.println(Thread.currentThread().getName() + " Пишет");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + " Завершил");
                serverWriter.releaseWriterPlace(place);
                try {
                    sleep(1000 * (int) (Math.random() * 5));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                serverWriter.isWriterServerBusy();
            }
        }
    }
}

class Readers extends Thread {
    Runnable place;
    Server serverReader;

    Readers(Server server) {
        serverReader = server;
    }

    public void run() {
        while (true) {
            if (serverReader.getReaderQueuesSize() && serverReader.getQueueSize(serverReader.getWritersQueueName()) == serverReader.writersQueue.size()) {
                serverReader.getPlaceReaderServer();
                System.out.println(Thread.currentThread().getName() + " Читает");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + " Завершил");
                serverReader.releaseReaderPlace(place);
                try {
                    sleep(1000 * (int) (Math.random() * 5));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                serverReader.isReaderServerBusy();
            }
        }
    }
}