package com.example.webapplicationtry3;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.util.Map;
import java.util.List;
import java.util.ArrayList;

@Controller
public class WebController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Map<String, Object> model) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping("/getTheatre")
    public String gettingTheatre(
            @RequestParam(name = "id", required = false, defaultValue = "1")
                    String id,
            @RequestParam(name = "name", required = false, defaultValue = "Input name")
                    String name,
            Map<String, Object> model) {
        model.put("id", id);
        model.put("name", name);
        return "theatreInfoById";

    }

    @GetMapping("/all")
    public String getAllTheatres(
                                 Map<String, Object> model) {//@ModelAttribute("theatreInfo") List<TheatreInfo> theatreInfoList,
        String url = "http://localhost:8080/api/theatre";
        RestTemplate restTemplate = new RestTemplate();
        String allTheatres = restTemplate.getForObject(url, String.class);
        model.put("allTheatres", allTheatres);
        System.out.println(allTheatres.toString());
/*
        TheatreInfo firstTheatre = new TheatreInfo(1, "Театр на Васильевском острове", "Адрес театра 1");
        TheatreInfo secondTheatre = new TheatreInfo(2, "Мариинский", "Адрес театра 2");
        TheatreInfo thirdTheatre = new TheatreInfo(3, "ТЮЗ", "Адрес театра 3");
        TheatreInfo fourthTheatre = new TheatreInfo(4, "Александринский театр", "Адрес театра 4");
        List<TheatreInfo> parsedJSON = new ArrayList<TheatreInfo>();

        parsedJSON.add(firstTheatre);
        parsedJSON.add(secondTheatre);
        parsedJSON.add(thirdTheatre);
        parsedJSON.add(fourthTheatre);
        model.put("parsedJSON", parsedJSON);*/


        TheatreInfo[] theatres = new TheatreInfo[0];
        ObjectMapper mapper = new ObjectMapper();
        try {
            theatres = mapper.readValue(allTheatres, TheatreInfo[].class);
            System.out.println(theatres[0].name);
            model.put("parsedJSON", theatres);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "allTheatres";
    }
    @GetMapping("/mainPage")
    public String mainPage(
            Map<String, Object> model) {
        return "mainPage";
    }

}