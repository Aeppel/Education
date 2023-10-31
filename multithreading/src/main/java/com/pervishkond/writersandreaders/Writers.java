package com.pervishkond.writersandreaders;

class Writers extends Thread {
    Runnable place;
    Server serverWriter;

    Writers(Server server, String name) {
        serverWriter = server;
        setName(name);
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
