package com.canuniv;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test {

    public static void main(String[] args) {

        try {
            //Take a wikipedia link which have a table of universities. This method parses the table and returns a set of UniversityDocument objects
            Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/List_of_universities_in_Canada").get();

            // Select the wikitable class which is common for tables in Wikipedia
            Elements tables = doc.select("table.wikitable");

            // Loop through each table (assuming multiple tables for different provinces)
            for (Element table : tables) {
                Elements rows = table.select("tr");

                // Skip the first row (headers) and iterate over each row
                for (int i = 1; i < rows.size(); i++) {
                    Element row = rows.get(i);
                    Elements cols = row.select("td");

                    // Some tables may have a different structure
                    if (!cols.isEmpty()) {
                        String universityName = cols.get(0).text().trim();
                        String location = cols.size() > 1 ? cols.get(1).text().trim() : "Location not found";

                        System.out.println("University Name: " + universityName + ", Location: " + location);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
