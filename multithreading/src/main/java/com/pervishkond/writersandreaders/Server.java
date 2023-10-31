package com.pervishkond.writersandreaders;

import java.util.LinkedList;

class Server {
    Runnable place;
    LinkedList<Runnable> writersQueue = new LinkedList<>();
    LinkedList<Runnable> readersQueue = new LinkedList<>();
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

    synchronized boolean getWriterQueueSize() {
        return writersQueue.size() != 0;
    }

    synchronized boolean getReaderQueuesSize() {
        return readersQueue.size() != 0;
    }

    synchronized boolean isEmptyWriterQueue() {
        return writersQueue.isEmpty();
    }

    synchronized boolean isEmptyReadersQueue() {
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
