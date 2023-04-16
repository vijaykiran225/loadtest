package org.vijay.tools.loadtester.processors;

import org.vijay.tools.loadtester.handlers.MyHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class Processor {



    public Map<Long, Integer> trigger(int count) {
        CyclicBarrier barrier = new CyclicBarrier(count);

        CountDownLatch latch = new CountDownLatch(count-1);

        Map<Long,Integer> metadata = new HashMap<>();

        List<Future<Long>> fs= new ArrayList<>();
        for (int i = 0; i < count-1; i++) {
            MyHandler h = new MyHandler(barrier,latch);
            FutureTask<Long> f = new FutureTask<>(h);
            fs.add(f);
            Thread t = new Thread(f);
            t.start();
        }

        try {
            Thread.sleep(200);
            System.err.println(barrier.getNumberWaiting());
            barrier.await();
            System.err.println("barrier cleared; latch waiting to join");
            latch.await();
            System.err.println("latch opened; getting metadata");
            for (Future<Long> f : fs) {
                Long time=0L;
                try {
                    time = f.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();

                }
                metadata.put(time,metadata.getOrDefault(time,0)+1);
            }
            System.err.println("exiting");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        int sum =0;
        for (Long aLong : metadata.keySet()) {
            sum+=metadata.get(aLong);
        }
        metadata.put(-1L,sum);
        return metadata;
    }
}
