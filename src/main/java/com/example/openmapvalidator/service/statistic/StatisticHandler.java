package com.example.openmapvalidator.service.statistic;

import java.util.Map;

public interface StatisticHandler {
    Map<String, Integer> handle(String fileName);
}
