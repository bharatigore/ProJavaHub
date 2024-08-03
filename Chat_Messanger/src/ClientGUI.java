import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ClientGUI {
    private JFrame frame;
    private JTextArea textArea;
    private JTextField textField;
    private JButton sendButton;
    private JButton connectButton;
    private PrintStream ps;
    private BufferedReader br1;
    private Socket socket;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClientGUI());
    }

    public ClientGUI() {
        setupGUI();
    }

    private void setupGUI() {
        frame = new JFrame("Chat Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        textField = new JTextField(20);
        sendButton = new JButton("Send");
        connectButton = new JButton("Connect");

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(textField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(panel, BorderLayout.SOUTH);
        frame.getContentPane().add(connectButton, BorderLayout.NORTH);

        sendButton.addActionListener(e -> sendMessage());

        textField.addActionListener(e -> sendMessage());

        connectButton.addActionListener(e -> setupConnection());

        frame.setVisible(true);
    }

    private void setupConnection() {
        try {
            socket = new Socket("localhost", 2100);
            ps = new PrintStream(socket.getOutputStream());
            br1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(() -> receiveMessages()).start();
            textArea.append("Connected to server\n");

        } catch (IOException e) {
            textArea.append("Error connecting to server: " + e.getMessage() + "\n");
        }
    }

    private void sendMessage() {
        String message = textField.getText();
        if (!message.equals("") && ps != null) {
            ps.println(message);
            textArea.append("Client: " + message + "\n");
            textField.setText("");
        }
    }

    private void receiveMessages() {
        try {
            String message;
            while ((message = br1.readLine()) != null) {
                textArea.append("Server: " + message + "\n");
            }
        } catch (IOException e) {
            textArea.append("Disconnected from server: " + e.getMessage() + "\n");
        }
    }
}
