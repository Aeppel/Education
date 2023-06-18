package com.pervishkond.writersandreaders;

public class Main {
    public static synchronized void main(String[] args) {
        Server server = new Server(2);

        Writers writers = new Writers(server, "writers1");
        Thread writers1 = new Thread(writers, "writers2");

        Readers readers = new Readers(server, "reader1");
        Thread reader1 = new Thread(readers, "reader2");
        Thread reader2 = new Thread(readers, "reader3");

        writers.start();
        writers1.start();

        reader1.start();
        readers.start();
        reader2.start();

    }
}

