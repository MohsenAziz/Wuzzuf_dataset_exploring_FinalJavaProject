package net.codejava;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.knowm.xchart.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class Skills {

    @RequestMapping("/skills")
    public String countingSkills(Model model) throws IOException {

        final Dataset<Row> wuzzufDF = new ReadWuzzufData().readData();




 ////////////////////////   reading the last column as a list of array of strings  /////////////////////////////////////////
  ////////////// then converting it to ordinary list of strings where each string represents a single skill   ////////////////


        List<String[]> skills = new ArrayList<>();
        wuzzufDF.collectAsList().stream().forEach(row -> skills.add(row.get(7).toString().split(",")));
        List<String> skillList = new ArrayList<>();
        for (String[] array : skills) {
            for (String i: array) {
                skillList.add(i);
            }
        }
 /////////////////////////// creating a map from the list where the keys/////////////////////
        // ////////////// are represented as a set to make sure no skill is repeated (as a key ) and the value////////
       ////////////////////is how many times  this skill was repeated in the original list /////////////////////



        Set<String> set = new HashSet<>(skillList);

        Map<String, Integer> skillMap = new HashMap<>();

        set.stream().forEach(x -> skillMap.put(x, Collections.frequency(skillList, x)));


        ////////////////////// this time we already have our map so we are //////////////////////////////
        ////////////////// just sorting it as a stream then reputting it //////////////////////
        //////////////////// into a new map to send it as an attribute to html file//////////////////////
//////////////////////to loop on it and returning html file///////////



        Map<String, Integer> output4 = skillMap.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));


/////////////////////////// ploting a bar chart  //////////////////////////////////////////////////////


        ArrayList<String> keyList = new ArrayList<String>(output4.keySet());
        ArrayList<Integer> valueList = new ArrayList<Integer>(output4.values());


        CategoryChart chart = new CategoryChartBuilder().height(800).width(1500)
                .title("most popular skills").xAxisTitle("skill").yAxisTitle("Count")
                .build();
        chart.addSeries("most popular skills", keyList.subList(0, 10), valueList.subList(0, 10));
        BitmapEncoder.saveBitmap(chart, "D:\\P R O G R A M M I N G\\J A V A\\My java projects\\FirstWebApplicationServer\\src\\main\\resources\\static\\most popular skills bar chart.jpg", BitmapEncoder.BitmapFormat.JPG);



        ///////////////////////////////////    plotting a pie chart   ////////////////////////////////////////////////////



        PieChart chart1 = new PieChartBuilder().width(800).height(700).title("most popular skills").build();
        Color[] sliceColors = new Color[]{new Color(50, 128, 180), new Color(25, 25, 26),  new Color(120, 22, 110), new Color(133, 50, 46), new Color(130, 80, 77), new Color(48, 15, 33), new Color(111, 55, 64), new Color(123, 134, 64), new Color(140, 106, 15),new Color(130, 100, 120)};
        chart1.getStyler().setSeriesColors(sliceColors);

        for (int i = 0; i < 10; i++) {
            chart1.addSeries(keyList.get(i), valueList.get(i));
        }
        BitmapEncoder.saveBitmap(chart1, "D:\\P R O G R A M M I N G\\J A V A\\My java projects\\FirstWebApplicationServer\\src\\main\\resources\\static\\most popular skills pie chart.jpg", BitmapEncoder.BitmapFormat.JPG);




        model.addAttribute("message", output4);

        return "skills";
    }
}