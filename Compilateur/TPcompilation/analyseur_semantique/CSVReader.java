import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.*;
import java.util.List;

public class CSVReader extends JFrame {
    private JTable table;
    private String[][] data=null;
    private String[] headers=null;
    public CSVReader() {
        setTitle("CSV Reader");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a table to display CSV data
        table = new JTable();

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Load CSV data into the table
        loadCSV("/home/rebhi/Desktop/table.tsv");
    }


   private void loadCSV(String fileName) {
        String line;
       // Initialize headers array
        List<String[]> dataList = new ArrayList<>(); // List to store data

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            // Read the data
            while ((line = br.readLine()) != null) {
                String[] rowData = line.split("\t"); // Use \t as delimiter for TSV

                if (headers == null) { // If headers are not initialized yet, treat the current row as headers
                    headers = rowData;
                } else {
                    dataList.add(rowData); // Otherwise, add the row to the data list
                }
            }

            // Convert the list to a 2D array
          data = new String[dataList.size()][headers.length];
            for (int i = 0; i < dataList.size(); i++) {
                data[i] = dataList.get(i);
            }

            /*for (int j=0;j< headers.length;j++)
                System.out.print(data[3][j]);*/
           /* for (int i=0;i<headers.length;i++) {
                System.out.println(i);
                System.out.println(headers[i]);}*/

            // Create a table model with the data and headers
            TableModel model = new DefaultTableModel(data, headers);

            // Set the table model
            table.setModel(model);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }  public  String [][]getData()
    {
        return data;
    }
    public String [] getHeaders()
    {
        return headers;
    }
}

