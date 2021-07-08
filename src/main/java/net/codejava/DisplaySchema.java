package net.codejava;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class DisplaySchema {
    @RequestMapping("/displayschema")
    public String schema(Model model) {

        final Dataset<Row> wuzzufDataFrame = new ReadWuzzufData().readData();

        List<String> schemaList = new ArrayList<>();
        Arrays.stream(wuzzufDataFrame.schema().fields()).forEach(row -> schemaList.add(row.toString()));

        wuzzufDataFrame.printSchema();

        model.addAttribute("message", schemaList);

        return "schema";
    }
}