import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SortingApp extends JFrame {
    private ArrayList<Integer> dataList = new ArrayList<>();
    private JTextField countField, sortedField;
    private JTextArea dataListArea;
    private JRadioButton bubbleSort, selectionSort, quickSort;

    public SortingApp() {
        setTitle("Aplikasi Sorting Data");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(204, 204, 255));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Jumlah Data:"), gbc);

        countField = new JTextField(5);
        gbc.gridx = 1;
        add(countField, gbc);

        JButton generateButton = new JButton("Generate Data");
        gbc.gridx = 2;
        add(generateButton, gbc);
        generateButton.addActionListener(e -> generateData());

        // Display area for generated data
        dataListArea = new JTextArea(3, 20);
        dataListArea.setEditable(false);
        dataListArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        add(new JScrollPane(dataListArea), gbc);

        // Sorting algorithm selection
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Algoritma Sorting:"), gbc);

        bubbleSort = new JRadioButton("Bubble");
        selectionSort = new JRadioButton("Selection");
        quickSort = new JRadioButton("Quick");
        ButtonGroup sortGroup = new ButtonGroup();
        sortGroup.add(bubbleSort);
        sortGroup.add(selectionSort);
        sortGroup.add(quickSort);
        
        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        sortPanel.add(bubbleSort);
        sortPanel.add(selectionSort);
        sortPanel.add(quickSort);
        sortPanel.setBackground(new Color(204, 204, 255));
        
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        add(sortPanel, gbc);

        // Sort button and sorted result display
        JButton sortButton = new JButton("Sort");
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(sortButton, gbc);
        sortButton.addActionListener(e -> sortData());

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Hasil Sorting:"), gbc);

        sortedField = new JTextField(20);
        sortedField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        add(sortedField, gbc);

        // Reset button
        JButton resetButton = new JButton("Reset");
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        add(resetButton, gbc);
        resetButton.addActionListener(e -> resetData());
    }

    private void generateData() {
        try {
            int count = Integer.parseInt(countField.getText());
            dataList.clear();
            Random random = new Random();
            for (int i = 0; i < count; i++) {
                dataList.add(random.nextInt(100)); // Random number between 0 and 99
            }
            updateDataList();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid integer for the count.");
        }
    }

    private void updateDataList() {
        dataListArea.setText(dataList.toString());
    }

    private void sortData() {
        if (dataList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No data to sort. Please generate data first.");
            return;
        }

        if (bubbleSort.isSelected()) {
            bubbleSort(dataList);
        } else if (selectionSort.isSelected()) {
            selectionSort(dataList);
        } else if (quickSort.isSelected()) {
            dataList = quickSort(new ArrayList<>(dataList));
        } else {
            JOptionPane.showMessageDialog(this, "Please select a sorting algorithm.");
            return;
        }
        sortedField.setText(dataList.toString());
    }

    private void bubbleSort(ArrayList<Integer> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    Collections.swap(list, j, j + 1);
                }
            }
        }
    }

    private void selectionSort(ArrayList<Integer> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (list.get(j) < list.get(minIdx)) {
                    minIdx = j;
                }
            }
            Collections.swap(list, i, minIdx);
        }
    }

    private ArrayList<Integer> quickSort(ArrayList<Integer> list) {
        if (list.size() <= 1) return list;
        int pivot = list.get(list.size() / 2);
        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() / 2) continue;
            if (list.get(i) <= pivot) left.add(list.get(i));
            else right.add(list.get(i));
        }
        ArrayList<Integer> sorted = new ArrayList<>(quickSort(left));
        sorted.add(pivot);
        sorted.addAll(quickSort(right));
        return sorted;
    }

    private void resetData() {
        dataList.clear();
        countField.setText("");
        sortedField.setText("");
        dataListArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SortingApp app = new SortingApp();
            app.setVisible(true);
        });
    }
}
