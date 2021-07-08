package net.codejava;

import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;


// Reading wuzzuf csv file using spark library and returning a dataframe object /////////////////
// which has no null values nor duplicate values///////////


public class ReadWuzzufData {
    public Dataset<Row> readData() {
        final SparkSession sparkSession = SparkSession.builder().appName("Wuzzuf ").master("local[1]")
                .getOrCreate();
        final DataFrameReader dataFrameReader = sparkSession.read();
        dataFrameReader.option("header", "true");
        Dataset<Row> wuzzufDataFrame = dataFrameReader.csv("Wuzzuf_Jobs.csv");
        wuzzufDataFrame = wuzzufDataFrame.select("Title", "Company", "Location", "Type", "Level", "YearsExp", "Country", "Skills");

        final Dataset<Row> wuzzufDFNoNulls = wuzzufDataFrame.na().drop();
        final Dataset<Row> wuzzufDFNoDuplicates = wuzzufDFNoNulls.dropDuplicates();

        return wuzzufDFNoDuplicates;
    }
}
