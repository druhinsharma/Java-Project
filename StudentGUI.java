import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentGUI {
    private JFrame frame;
    private JTextField idField, nameField, ageField, searchField, deleteField;
    private JTextArea outputArea;
    private JLabel countLabel;
    private StudentManager manager;

    public StudentGUI() {
        manager = new StudentManager();
        frame = new JFrame("🎓 Student Record System");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font textFont = new Font("Consolas", Font.PLAIN, 13);

        // 🔼 Top Panel - Add Student
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        idField = new JTextField();
        nameField = new JTextField();
        ageField = new JTextField();
        JButton addButton = new JButton("➕ Add Student");

        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Student"));
        inputPanel.add(new JLabel("ID:")).setFont(labelFont);
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:")).setFont(labelFont);
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Age:")).setFont(labelFont);
        inputPanel.add(ageField);
        inputPanel.add(new JLabel(""));
        inputPanel.add(addButton);

        // 🖥️ Center - Output
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(textFont);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // 🔽 Bottom Panel - Search, Delete, Count
        JPanel actionPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        searchField = new JTextField();
        deleteField = new JTextField();
        JButton searchButton = new JButton("🔍 Search by ID");
        JButton deleteButton = new JButton("🗑️ Delete by ID");
        JButton viewButton = new JButton("📋 View All");
        countLabel = new JLabel("👥 Total Students: 0");

        actionPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        actionPanel.add(searchField);
        actionPanel.add(searchButton);
        actionPanel.add(deleteField);
        actionPanel.add(deleteButton);
        actionPanel.add(viewButton);
        actionPanel.add(countLabel);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(actionPanel, BorderLayout.SOUTH);

        // 🔁 Event Handlers
        addButton.addActionListener(e -> {
            try {
                String id = idField.getText().trim();
                String name = nameField.getText().trim();
                int age = Integer.parseInt(ageField.getText().trim());

                if (id.isEmpty() || name.isEmpty()) {
                    showMessage("All fields are required.");
                    return;
                }

                if (age < 16) {
                    showMessage("Age must be at least 16.");
                    return;
                }

                Student s = new Student(id, name, age);
                manager.addStudent(s);
                outputArea.setText("✅ Student Added: " + s + "\n");
                updateCount();

                idField.setText("");
                nameField.setText("");
                ageField.setText("");
            } catch (NumberFormatException ex) {
                showMessage("Age must be a number.");
            } catch (IllegalArgumentException ex) {
                showMessage(ex.getMessage());
            }
        });

        searchButton.addActionListener(e -> {
            String id = searchField.getText().trim();
            Student found = manager.findStudent(id);
            if (found != null) {
                outputArea.setText("✅ Found:\n" + found);
            } else {
                outputArea.setText("❌ Student not found.");
            }
        });

        deleteButton.addActionListener(e -> {
            String id = deleteField.getText().trim();
            Student found = manager.findStudent(id);
            if (found != null) {
                manager.deleteStudent(id);
                outputArea.setText("🗑️ Deleted: " + id);
                updateCount();
            } else {
                outputArea.setText("❌ Cannot delete. Student not found.");
            }
        });

        viewButton.addActionListener(e -> {
            java.util.List<Student> list = manager.getAllStudents();
            outputArea.setText("📋 Student List:\n");
            for (Student s : list) {
                outputArea.append(s.toString() + "\n");
            }
        });

        frame.setVisible(true);
    }

    private void updateCount() {
        countLabel.setText("👥 Total Students: " + manager.getTotalStudents());
    }

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(frame, msg);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentGUI());
    }
}
