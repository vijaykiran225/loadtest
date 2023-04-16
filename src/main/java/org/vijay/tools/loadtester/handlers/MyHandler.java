package org.vijay.tools.loadtester.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class MyHandler extends Thread implements Callable<Long> {
    private final CyclicBarrier barrier;
    private final CountDownLatch latch;

    public MyHandler(CyclicBarrier barrier, CountDownLatch latch) {
        this.barrier = barrier;
        this.latch = latch;
    }

    @Override
    public Long call() throws Exception {
        try {
            barrier.await();
            long l = System.currentTimeMillis();
            System.out.println("start " + this.getName() + " , at " + l);
            latch.countDown();
//            doHttp();
            return l;
        } catch (InterruptedException | BrokenBarrierException ignored) {
        }

        return 0L;

    }

    private void doHttp() throws IOException {
        // Create a neat value object to hold the URL
        URL url = new URL("http://localhost:3000/heartbeat");

// Open a connection(?) on the URL(??) and cast the response(???)
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

// Now it's "open", we can set the request method, headers etc.
        connection.setRequestProperty("accept", "application/json");

// This line makes the request
        InputStream responseStream = connection.getInputStream();

// Manually converting the response body InputStream to APOD using Jackson
        BufferedReader br = new BufferedReader(new InputStreamReader((responseStream)));
        String content;
        StringBuilder data = new StringBuilder();
        while((content = br.readLine()) != null){
            data.append(content);
        }

// Finally we have the response
//            System.out.println(data.toString());
    }
}
