package com.example.pcpconf18.pcpcoin.custom;

import com.codahale.metrics.Histogram;
import io.pcp.parfait.MonitoredCounter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyHistogram {

//    private Map<MonitoredCounter> bucketCounter = new ArrayList<>();


    public MyHistogram(int buckets, int maxValue, Histogram histogram) {

        histogram.getSnapshot().getValues();

        for (int i = 0; i < buckets; i++) {

        }
    }

    private static class Bucket {
        private final int from;
        private final int to;

        public Bucket(int from, int to) {
            this.from = from;
            this.to = to;
//            MonitoredCounter  counter = new MonitoredCounter("");
        }

        boolean fitsInBucket(int value) {
            return false;
        }
    }
}
