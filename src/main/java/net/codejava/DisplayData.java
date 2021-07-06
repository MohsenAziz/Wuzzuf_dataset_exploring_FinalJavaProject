package net.codejava;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class DisplayData {
    @RequestMapping("/displaydata")
    public String displayingData(Model model) {

        final Dataset<Row> wuzzufDF = new ReadWuzzufData().readData();

        List<String[]> displayingData = new ArrayList<>();
        wuzzufDF.collectAsList().stream().forEach(row -> displayingData.add(row.toString().split(",")));

        model.addAttribute("message", displayingData);

        return "readdata";
    }
}