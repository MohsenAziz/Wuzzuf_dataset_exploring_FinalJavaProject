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
import java.util.stream.Collectors;

@Controller
public class JobTitles {

    @RequestMapping("/jobtitles")
    public String countJobTitles(Model model) throws IOException {

        final Dataset<Row> wuzzufDataFrame = new ReadWuzzufData().readData();

        final Dataset<Row> countTitlesDF = wuzzufDataFrame.groupBy("Title").count();


////////////////////// converting the data frame to a map to send to html file//////////////////////
//////////////////////to loop on it and returning html file///////////




        Map<String, Integer> jobs = new HashMap<>();
        countTitlesDF.collectAsList().stream().forEach(row -> jobs.put(row.get(0).toString(), Integer.parseInt(row.get(1).toString())));
        Map<String, Integer>outout2 = jobs.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));



/////////////////////////// ploting a bar chart  ///////////////////////////////////////////////////////////////////



        ArrayList<String> keyList = new ArrayList<String>(outout2.keySet());
        ArrayList<Integer> valueList = new ArrayList<Integer>(outout2.values());


        CategoryChart chart = new CategoryChartBuilder().height(800).width(1500)
                .title("most popular jobs").xAxisTitle("job").yAxisTitle("Count")
                .build();
        chart.addSeries("most popular jobs", keyList.subList(0, 10), valueList.subList(0, 10));
        try{
            BitmapEncoder.saveBitmap(chart, "src\\main\\resources\\static\\most popular jobs bar chart.jpg", BitmapEncoder.BitmapFormat.JPG);
        }
        catch (IOException ex){
            System.out.println("There is no need to save the image again because it is already saved");
        }



        /////////////////////////// ploting a pie chart  /////////////////////////////////////////////////////////////////////////////




        PieChart chart1 = new PieChartBuilder().width(800).height(700).title("most popular jobs").build();
        Color[] sliceColors = new Color[]{new Color(50, 128, 180), new Color(25, 25, 26),  new Color(120, 22, 110), new Color(133, 50, 46), new Color(130, 80, 77), new Color(48, 15, 33), new Color(111, 55, 64), new Color(123, 134, 64), new Color(140, 106, 15),new Color(130, 100, 120)};
        chart1.getStyler().setSeriesColors(sliceColors);

        for (int i = 0; i < 10; i++) {
            chart1.addSeries(keyList.get(i), valueList.get(i));
        }
        try{
            BitmapEncoder.saveBitmap(chart1, "src\\main\\resources\\static\\most popular jobs pie chart.jpg", BitmapEncoder.BitmapFormat.JPG);
        }
        catch (IOException ex){
            System.out.println("There is no need to save the image again because it is already saved");
        }

        model.addAttribute("message", outout2);

        return "jobtitles";
    }


    }
