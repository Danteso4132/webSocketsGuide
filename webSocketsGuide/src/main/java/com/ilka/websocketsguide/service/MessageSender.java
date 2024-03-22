package com.ilka.websocketsguide.service;

import com.google.gson.Gson;
import com.ilka.websocketsguide.entity.Matrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@EnableAsync
@EnableScheduling
public class MessageSender {


    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private Matrix matrix;


    public void sendIntMatrix() {
        int[][] intMatrix = matrix.getPlainMatrix();
        Gson gson = new Gson();
        String jsonMatrix = gson.toJson(intMatrix);
        simpMessagingTemplate.convertAndSend("/intMatrix", jsonMatrix);
    }

    @Scheduled(fixedRate = 200) // Send the matrix every 5 seconds
    public void sendIntMatrixPeriodically() {
        sendIntMatrix();
    }
}

