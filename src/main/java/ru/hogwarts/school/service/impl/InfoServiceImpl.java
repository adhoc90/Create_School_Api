package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.service.InfoService;

import java.util.stream.LongStream;
import java.util.stream.Stream;

@Service
public class InfoServiceImpl implements InfoService {

    private final Logger logger = LoggerFactory.getLogger(InfoServiceImpl.class);

    @Value("${server.port}")
    private String port;

    @Override
    public String getPort() {
        return port;
    }

    @Override
    public void getCalculateWithStream(int limit) {
        calculate(limit);
        calculateParallel(limit);
        calculateLongStream(limit);
    }

    private void calculate(int limit) {
        long start = System.currentTimeMillis();

        int sum = Stream.iterate(1, a -> a +1)
                .limit(limit)
                .reduce(0, (a, b) -> a + b );
        long end = System.currentTimeMillis();

        logger.info("Time 1: " + (end - start));

    }

    private void calculateParallel(int limit) {
        long start = System.currentTimeMillis();

        int sum = Stream.iterate(1, a -> a +1)
                .parallel()
                .limit(limit)
                .reduce(0, (a, b) -> a + b );
        long end = System.currentTimeMillis();

        logger.info("Time 2: " + (end - start));
    }

    private void calculateLongStream(int limit) {
        long start = System.currentTimeMillis();

        long sum = LongStream
                .range(1, limit)
                .sum();
        long end = System.currentTimeMillis();

        logger.info("Time 3: " + (end - start));
    }
}