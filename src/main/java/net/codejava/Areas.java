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
public class Areas{

    @RequestMapping("/areas")
    public String countAreas(Model model) throws IOException {

        final Dataset<Row> wuzzufDF = new ReadWuzzufData().readData();

        final Dataset<Row> countTitlesDF = wuzzufDF.groupBy("Location").count();



        ////////////////////// converting the data frame to a map to send to html file//////////////////////
//////////////////////to loop on it and returning html file///////////



        Map<String, Integer> areas= new HashMap<>();
        countTitlesDF.collectAsList().stream().forEach(row -> areas.put(row.get(0).toString(), Integer.parseInt(row.get(1).toString())));
        Map<String, Integer> output3 = areas.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));


/////////////////////////// ploting a bar chart  //////////////////////////////////////////////////////



        ArrayList<String> keyList = new ArrayList<String>(output3.keySet());
        ArrayList<Integer> valueList = new ArrayList<Integer>(output3.values());


        CategoryChart chart = new CategoryChartBuilder().height(800).width(1500)
                .title("most popular areas").xAxisTitle("area").yAxisTitle("Count")
                .build();
        chart.addSeries("most popular areas", keyList.subList(0, 10), valueList.subList(0, 10));
        BitmapEncoder.saveBitmap(chart, "D:\\P R O G R A M M I N G\\J A V A\\My java projects\\FirstWebApplicationServer\\src\\main\\resources\\static\\most popular areas bar chart.jpg", BitmapEncoder.BitmapFormat.JPG);



        ///////////////////////////////////    plotting a pie chart   /////////////////////////////////////////////////////////////////////////////////////////////


        PieChart chart1 = new PieChartBuilder().width(800).height(700).title("most popular jobs").build();
        Color[] sliceColors = new Color[]{new Color(50, 128, 180), new Color(25, 25, 26),  new Color(120, 22, 110), new Color(133, 50, 46), new Color(130, 80, 77), new Color(48, 15, 33), new Color(111, 55, 64), new Color(123, 134, 64), new Color(140, 106, 15),new Color(130, 100, 120)};
        chart1.getStyler().setSeriesColors(sliceColors);

        for (int i = 0; i < 10; i++) {
            chart1.addSeries(keyList.get(i), valueList.get(i));
        }
        BitmapEncoder.saveBitmap(chart1, "D:\\P R O G R A M M I N G\\J A V A\\My java projects\\FirstWebApplicationServer\\src\\main\\resources\\static\\most popular areas pie chart.jpg", BitmapEncoder.BitmapFormat.JPG);

        model.addAttribute("message", output3);

        return "areas";
    }


}
