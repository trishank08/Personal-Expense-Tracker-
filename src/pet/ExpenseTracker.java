package pet;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExpenseTracker extends JFrame {

    private JTextField dateField, amountField, descriptionField;
    private JComboBox<String> categoryCombo;
    private DefaultTableModel tableModel;
    private JTable expenseTable;

    public ExpenseTracker() {
        setTitle("Personal Expense Tracker");
        setSize(1000, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.decode("#121212")); // Main frame

        // ---------------- Title Panel ----------------
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.decode("#4AAFD5")); // Vibrant blue
        titlePanel.setBorder(new LineBorder(Color.decode("#808080"), 2));
        titlePanel.setPreferredSize(new Dimension(1000, 90)); // taller title
        JLabel titleLabel = new JLabel("Personal Expense Tracker");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titlePanel.add(titleLabel);

        // ---------------- Form Panel ----------------
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBackground(Color.decode("#1C1C1C")); // Dark gray sidebar
        formPanel.setBorder(new LineBorder(Color.decode("#808080"), 2));
        formPanel.setPreferredSize(new Dimension(350, 300)); // sidebar style

        // Labels
        JLabel dateLabel = new JLabel("Date (yyyy-MM-dd):");
        JLabel categoryLabel = new JLabel("Category:");
        JLabel amountLabel = new JLabel("Amount:");
        JLabel descriptionLabel = new JLabel("Description:");

        dateLabel.setForeground(Color.WHITE);
        categoryLabel.setForeground(Color.WHITE);
        amountLabel.setForeground(Color.WHITE);
        descriptionLabel.setForeground(Color.WHITE);

        // Inputs
        dateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        styleTextField(dateField);

        categoryCombo = new JComboBox<>(new String[]{"Food", "Travel", "Shopping", "Entertainment", "Other"});
        styleComboBox(categoryCombo);

        amountField = new JTextField();
        styleTextField(amountField);
        descriptionField = new JTextField();
        styleTextField(descriptionField);

        // Buttons
        JButton addButton = new JButton("Add Expense");
        JButton clearButton = new JButton("Clear");

        styleAddButton(addButton);
        styleClearButton(clearButton);

        // Add components to form panel
        formPanel.add(dateLabel);
        formPanel.add(dateField);
        formPanel.add(categoryLabel);
        formPanel.add(categoryCombo);
        formPanel.add(amountLabel);
        formPanel.add(amountField);
        formPanel.add(descriptionLabel);
        formPanel.add(descriptionField);
        formPanel.add(addButton);
        formPanel.add(clearButton);

        // ---------------- Table ----------------
        String[] columns = {"Date", "Category", "Amount", "Description"};
        tableModel = new DefaultTableModel(columns, 0);
        expenseTable = new JTable(tableModel);

        // Table styling
        expenseTable.setFillsViewportHeight(true);
        expenseTable.setBackground(Color.decode("#1C1C1C"));
        expenseTable.setForeground(Color.WHITE);
        expenseTable.setSelectionBackground(Color.decode("#424242"));
        expenseTable.setSelectionForeground(Color.WHITE);
        expenseTable.setGridColor(Color.decode("#808080"));
        expenseTable.setRowHeight(28); // taller rows

        // Alternate row shading
        expenseTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    setBackground(row % 2 == 0 ? Color.decode("#1C1C1C") : Color.decode("#2A2A2A"));
                    setForeground(Color.WHITE);
                }
                return this;
            }
        });

        // Table header
        expenseTable.getTableHeader().setBackground(Color.decode("#E7A339")); // Orange-gold
        expenseTable.getTableHeader().setForeground(Color.BLACK);
        expenseTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        expenseTable.getTableHeader().setPreferredSize(new Dimension(expenseTable.getWidth(), 35));
        expenseTable.getTableHeader().setBorder(new LineBorder(Color.decode("#808080")));

        // Center align Amount column
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        expenseTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        JScrollPane tableScrollPane = new JScrollPane(expenseTable);
        tableScrollPane.getViewport().setBackground(Color.decode("#1C1C1C"));
        tableScrollPane.setBorder(new LineBorder(Color.decode("#808080"), 2));

        // Button actions
        addButton.addActionListener(e -> addExpense());
        clearButton.addActionListener(e -> clearFields());

        // ---------------- Layout ----------------
        setLayout(new BorderLayout(10, 10));
        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.WEST);
        add(tableScrollPane, BorderLayout.CENTER);
    }

    // ---------------- Styling Methods ----------------
    private void styleTextField(JTextField textField) {
        textField.setBackground(Color.decode("#2C2C2C"));
        textField.setForeground(Color.WHITE);
        textField.setCaretColor(Color.WHITE);
        textField.setBorder(BorderFactory.createLineBorder(Color.decode("#808080")));
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setBackground(Color.decode("#2C2C2C"));
        comboBox.setForeground(Color.WHITE);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setBorder(BorderFactory.createLineBorder(Color.decode("#808080")));
    }

    private void styleAddButton(JButton button) {
        button.setBackground(Color.decode("#4CAF50")); // green
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.decode("#808080"), 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#45A049")); // darker green
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#4CAF50"));
            }
        });
    }

    private void styleClearButton(JButton button) {
        button.setBackground(Color.decode("#E53935")); // red
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.decode("#808080"), 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#B71C1C")); // darker red
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#E53935"));
            }
        });
    }

    // ---------------- Functional Methods ----------------
    private void addExpense() {
        String date = dateField.getText().trim();
        String category = (String) categoryCombo.getSelectedItem();
        String amountStr = amountField.getText().trim();
        String description = descriptionField.getText().trim();

        if (date.isEmpty() || category.isEmpty() || amountStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Date, Category, and Amount are required fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            sdf.parse(date);

            tableModel.addRow(new Object[]{date, category, amount, description});
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Amount must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Date must be in yyyy-MM-dd format.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        dateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        categoryCombo.setSelectedIndex(0);
        amountField.setText("");
        descriptionField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ExpenseTracker tracker = new ExpenseTracker();
            tracker.setVisible(true);
        });
    }
}

