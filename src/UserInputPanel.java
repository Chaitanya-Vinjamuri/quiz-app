import javax.swing.*;
import java.awt.*;

public class UserInputPanel extends JPanel {
    private JTextField idField, nameField;
    private JButton enterButton;

    public UserInputPanel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(200, 230, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Enter Your Details", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(30, 60, 90));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Label and text field for ID
        addLabelAndField("ID No:", gbc, 1, idField = new JTextField(20));

        // Label and text field for Name
        addLabelAndField("Name:", gbc, 2, nameField = new JTextField(20));

        // Enter button to submit user details
        enterButton = new JButton("Enter");
        enterButton.setBackground(new Color(100, 150, 255));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(enterButton, gbc);
    }

    // Utility method to add label and text field
    private void addLabelAndField(String labelText, GridBagConstraints gbc, int row, JTextField textField) {
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        add(textField, gbc);
    }

    public String getUserId() {
        return idField.getText();
    }

    public String getUserName() {
        return nameField.getText();
    }

    public JButton getEnterButton() {
        return enterButton;
    }
}
