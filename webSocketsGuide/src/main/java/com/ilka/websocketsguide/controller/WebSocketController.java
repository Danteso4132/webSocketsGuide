package com.ilka.websocketsguide.controller;

//import com.ilka.websocketsguide.entity.Greeting;
//import com.ilka.websocketsguide.entity.HelloMessage;
import com.ilka.websocketsguide.entity.Matrix;
import com.ilka.websocketsguide.entity.Pointer;
import com.ilka.websocketsguide.entity.cells.*;
import com.ilka.websocketsguide.service.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@org.springframework.stereotype.Controller
@EnableAsync
@EnableScheduling
public class WebSocketController {

    @Autowired
    Matrix matrix;


    @Autowired
    MessageSender messageSender;


//    @MessageMapping("/hello")
//    @SendTo("/topic/greetings")
//    public Greeting greeting(HelloMessage message) throws Exception {
//        Thread.sleep(1000); // simulated delay
//        System.out.println("greeting");
//        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
//    }



    @MessageMapping("/randomize")
    public void randomizeField(){
        System.out.println("Randomizing matrix");
        matrix.updateMatrixWithRandomField();
        messageSender.sendIntMatrix();
    }

    @MessageMapping("/drawOnCell")
    public void draw(Pointer p){
        //System.out.println("Got pointer " + p);
        if (p.getColorCode() == 0){
            EmptyElement emptyElement = new EmptyElement(p.getX(), p.getY());
            matrix.getMatrix()[p.getX()][p.getY()] = new Cell(emptyElement);
        }
        else if (p.getColorCode() == 1) {
            SandElement sandElement = new SandElement(p.getX(), p.getY());
            matrix.getMatrix()[p.getX()][p.getY()] = new Cell(sandElement);
        }
        else if (p.getColorCode() == 2){
            StoneElement stoneElement = new StoneElement(p.getX(), p.getY());
            matrix.getMatrix()[p.getX()][p.getY()] = new Cell(stoneElement);
        }
        else if (p.getColorCode() == 3){
            WaterElement waterElement = new WaterElement(p.getX(), p.getY());
            matrix.getMatrix()[p.getX()][p.getY()] = new Cell(waterElement);
        }
    }
}
