package io.github.blaney83.todo.datamodel;

import javafx.collections.FXCollections;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

public class TDData {
    private static TDData instance = new TDData();
    private static String filename = "TDListItems.txt";

    private List<TDItem> tdItems;
    private DateTimeFormatter formatter;

    public static TDData getInstance() {
        return instance;
    }

    private TDData() {
        formatter = DateTimeFormatter.ofPattern("E, MMM dd, yyyy");
    }

    public List<TDItem> getTdItems() {
        return tdItems;
    }

    public void addTDItem(TDItem item){
        tdItems.add(item);

    }

    public void loadTDItems() throws IOException {
        tdItems = FXCollections.observableArrayList();
        Path path = Paths.get(filename);
        BufferedReader br = Files.newBufferedReader(path);

        String input;
        try {
            int count = 0;
            while ((input = br.readLine()) != null) {
                System.out.println(count);
                count++;
                String[] itemPieces = input.split("%%%");
                String shortDescription = itemPieces[0];
                String details = itemPieces[1];
                String deadline = itemPieces[2];
                LocalDate date = LocalDate.parse(deadline);

                TDItem tdItem = new TDItem(shortDescription, details, date);
                tdItems.add(tdItem);

            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    public void saveTDItems() throws IOException {
        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);
        try {
            Iterator<TDItem> iterator = tdItems.iterator();
            while (iterator.hasNext()) {
                TDItem item = iterator.next();
                StringBuilder sb = new StringBuilder(item.getShortDescription());
                sb.append("%%%" + item.getDetails() + "%%%" + item.getDeadline().toString() +"%%%");
                bw.write(sb.toString());
                bw.newLine();
            }
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }
}
