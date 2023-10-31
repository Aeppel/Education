package com.pervishkond.writersandreaders;

class Readers extends Thread {
    Runnable place;
    Server serverReader;

    Readers(Server server, String name) {
        serverReader = server;
        setName(name);
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
