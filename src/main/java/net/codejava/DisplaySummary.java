package net.codejava;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DisplaySummary {
    @RequestMapping("/displaysummarys")
    public String summaryOfData(Model model) {

        final Dataset<Row> wuzzufDF = new ReadWuzzufData().readData();

        List<String> summaryList = new ArrayList<>();
        wuzzufDF.describe().collectAsList().forEach(row -> summaryList.add(row.toString()));

        model.addAttribute("message", summaryList);

        return "summary";
    }

}


