import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ScholarRecordSystem extends JFrame implements ActionListener {

    private final JTextField idField, nameField, courseField, gradeField, searchField;
    private final JTextArea displayArea;
    private final JButton addBtn, viewBtn, deleteBtn, searchBtn, clearBtn;
    private final String FILE_NAME = "students.txt";

    public ScholarRecordSystem() {
        
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ignored) {}

        setTitle("Scholar Record Management System");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(12, 12));

        
        Font headerFont = new Font("Segos UI", Font.BOLD, 22);
        Font labelFont = new Font("Segos UI", Font.PLAIN, 14);
        Font fieldFont = new Font("Segos UI", Font.PLAIN, 14);
        Color accent = new Color(38, 166, 154);
        Color panelBg = new Color(250, 250, 250);

        JLabel header = new JLabel("  🎓 Scholar Records");
        header.setFont(headerFont);
        header.setOpaque(true);
        header.setBackground(panelBg);
        header.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        add(header, BorderLayout.NORTH);

       
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        left.setBackground(panelBg);

        Dimension fieldDim = new Dimension(300, 30);

        
        java.util.function.BiFunction<String, JTextField, JPanel> labeledRow = (labelText, field) -> {
            JLabel lbl = new JLabel(labelText);
            lbl.setFont(labelFont);
            lbl.setPreferredSize(new Dimension(140, 24));
            field.setFont(fieldFont);
            field.setMaximumSize(fieldDim);
            field.setPreferredSize(fieldDim);
            field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220)),
                    BorderFactory.createEmptyBorder(6, 8, 6, 8)
            ));
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
            row.setOpaque(false);
            row.add(lbl);
            row.add(field);
            return row;

        };

        left.add(labeledRow.apply("Student ID:", idField = new JTextField()));
        left.add(labeledRow.apply("Full Name:", nameField = new JTextField()));
        left.add(labeledRow.apply("Course:", courseField = new JTextField()));
        left.add(labeledRow.apply("Grades:", gradeField = new JTextField()));
        left.add(labeledRow.apply("Search by ID:", searchField = new JTextField()));
        left.add(Box.createRigidArea(new Dimension(0, 8)));

        
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        buttons.setOpaque(false);
        addBtn = new JButton("Add");
        viewBtn = new JButton("View");
        deleteBtn = new JButton("Delete");
        searchBtn = new JButton("Search");
        clearBtn = new JButton("Clear");

        JButton[] btns = new JButton[]{addBtn, viewBtn, deleteBtn, searchBtn, clearBtn};
        Color[] btnColors = new Color[]{accent, new Color(63, 81, 181), new Color(244, 67, 54), new Color(255, 152, 0), new Color(96, 125, 139)};
        for (int i = 0; i < btns.length; i++) {
            JButton b = btns[i];
            b.setFont(new Font("segos UI", Font.BOLD, 13));
            b.setForeground(Color.WHITE);
            b.setBackground(btnColors[i]);
            b.setOpaque(true);
            b.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
            b.setFocusPainted(false);
            buttons.add(b);
        }

        left.add(buttons);
        left.add(Box.createVerticalGlue());

        
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Console", Font.PLAIN, 13));
        displayArea.setBackground(Color.WHITE);
        displayArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(220,220,220)), "Student Records"));
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);

       
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, scrollPane);
        split.setResizeWeight(0.38);
        split.setDividerLocation(360);
        split.setOneTouchExpandable(true);
        split.setBorder(null);

        add(split, BorderLayout.CENTER);

        
        getContentPane().setBackground(panelBg);

        attachListeners();
    }

    private void attachListeners() {

        addBtn.addActionListener(this);
        viewBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        searchBtn.addActionListener(this);
        clearBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == addBtn) addStudent();
        else if (e.getSource() == viewBtn) viewStudents();
        else if (e.getSource() == deleteBtn) deleteStudent();
        else if (e.getSource() == searchBtn) searchStudent();
        else if (e.getSource() == clearBtn) displayArea.setText("");
    }

    private void addStudent() {
        
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String course = courseField.getText().trim();
        String grades = gradeField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || course.isEmpty() || grades.isEmpty()) {
            
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String[] gradeParts = grades.split(",");
            double total = 0;
            for (String g : gradeParts) total += Double.parseDouble(g.trim());
            double average = total / gradeParts.length;

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
                bw.write(id + "," + name + "," + course + "," + String.format("%.2f", average));
                bw.newLine();
                JOptionPane.showMessageDialog(this, "Student added successfully!\nAverage: " + String.format("%.2f", average));
                clearFields();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid grade input! Use numbers separated by commas.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving student record.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewStudents() {
        displayArea.setText("");
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                displayArea.append("ID: " + data[0] + " | Name: " + data[1] +
                        " | Course: " + data[2] + " | Average: " + data[3] + "\n");
            }
        } catch (IOException ex) {
            displayArea.setText("No records found.");
        }
    }

    private void deleteStudent() {
        String searchID = searchField.getText().trim();
        if (searchID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a student ID to delete.");
            return;
        }

        File inputFile = new File(FILE_NAME);
        File tempFile = new File("temp.txt");
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(searchID + ",")) {
                    found = true;
                    continue;
                }
                writer.write(line + System.lineSeparator());
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error deleting record.");
            return;
        }

        if (inputFile.delete() && tempFile.renameTo(inputFile)) {
            JOptionPane.showMessageDialog(this, found ? "Record deleted." : "Student not found.");
        }
    }

    private void searchStudent() {
        String searchID = searchField.getText().trim();
        if (searchID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a student ID to search.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(searchID + ",")) {
                    displayArea.setText("FOUND: " + line.replace(",", " | "));
                    return;
                }
            }
            displayArea.setText("No record found for ID: " + searchID);
        } catch (IOException ex) {
            displayArea.setText("Error reading records.");
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        courseField.setText("");
        gradeField.setText("");
        searchField.setText("");
    }
}