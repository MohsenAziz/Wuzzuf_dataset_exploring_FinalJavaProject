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

        final Dataset<Row> wuzzufDF = new ReadWuzzufData().readData();

        List<String> schemaList = new ArrayList<>();
        Arrays.stream(wuzzufDF.schema().fields()).forEach(row -> schemaList.add(row.toString()));

        model.addAttribute("message", schemaList);

        return "schema";
    }
}