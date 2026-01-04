package org.pustoslov;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.util.ResourceBundle;

public class TextCaseConverter extends JFrame {
  private final JTextArea textField;
  private final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

  public TextCaseConverter() {
    JButton toUpperCaseButton = new JButton(resourceBundle.getString("button.toUpper"));
    JButton toLowerCaseButton = new JButton(resourceBundle.getString("button.toLower"));
    JButton pasteButton = new JButton(resourceBundle.getString("button.paste"));
    JButton copyButton = new JButton(resourceBundle.getString("button.copy"));

    setTitle(resourceBundle.getString("label.title"));
    setSize(600, 250);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    textField = new JTextArea();
    textField.setFont(new Font("Arial", Font.PLAIN, 16));
    textField.setCaretColor(textField.getDisabledTextColor());

    JScrollPane scrollPane = new JScrollPane(textField);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    buttonPanel.add(toUpperCaseButton);
    buttonPanel.add(toLowerCaseButton);
    buttonPanel.add(pasteButton);
    buttonPanel.add(copyButton);

    setLayout(new BorderLayout(10, 10));
    add(new JLabel(resourceBundle.getString("label.instruction")), BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);

    ((JComponent) getContentPane())
            .setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

    toUpperCaseButton.addActionListener(e -> convertToUpperCase());
    toLowerCaseButton.addActionListener(e -> convertToLowerCase());
    pasteButton.addActionListener(e -> pasteFromClipboard());
    copyButton.addActionListener(actionEvent -> copyToClipboard());
  }

  private void convertToUpperCase() {
    String text = textField.getText();
    if (!text.isEmpty()) {
      textField.setText(text.toUpperCase());
    } else {
      showToast(resourceBundle.getString("toast.emptyWindow"), false, 1000);
    }
  }

  private void convertToLowerCase() {
    String text = textField.getText();
    if (!text.isEmpty()) {
      textField.setText(text.toLowerCase());
    } else {
      showToast(resourceBundle.getString("toast.emptyWindow"), false, 1000);
    }
  }


  private void copyToClipboard() {
    String text = textField.getText();
    if (!text.isEmpty()) {
      StringSelection stringSelection = new StringSelection(text);
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      clipboard.setContents(stringSelection, null);
      showToast(resourceBundle.getString("toast.copied"), true, 1000);
    } else {
      showToast(resourceBundle.getString("toast.emptyWindow"), false, 1000);
    }
  }

  private void pasteFromClipboard() {
    try {
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      String text = (String) clipboard.getData(DataFlavor.stringFlavor);
      if (text != null && !text.isEmpty()) {
        textField.setText(text);
      } else {
        showToast(resourceBundle.getString("toast.emptyClipboard"), false, 1000);
      }
    } catch (Exception ex) {
      showToast(resourceBundle.getString("toast.unknownError"), false, 1000);
    }
  }

  private void showToast(String message, boolean isOK, int delay) {
    JDialog toast = new JDialog(this, false);
    toast.setUndecorated(true);
    toast.getContentPane().setLayout(new BorderLayout());

    JLabel label = new JLabel(message, SwingConstants.CENTER);
    label.setForeground(Color.WHITE);
    label.setBackground(isOK ? new Color(46, 125, 50) : new Color(198, 40, 40));
    label.setOpaque(true);
    label.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    label.setFont(new Font("Arial", Font.BOLD, 14));

    toast.getContentPane().add(label, BorderLayout.CENTER);
    toast.pack();

    Point location = getLocation();
    toast.setLocation(location.x + getWidth() / 2 - toast.getWidth() / 2,
            location.y + getHeight() / 2 - toast.getHeight() / 2);

    toast.setVisible(true);

    new Timer(delay, e -> toast.dispose()).start();
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {

      try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }

      TextCaseConverter converter = new TextCaseConverter();
      converter.setVisible(true);
    });
  }
}