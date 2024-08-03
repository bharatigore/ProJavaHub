import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.logging.*;

public class ServerGUI {
    private static final Logger logger = Logger.getLogger(ServerGUI.class.getName());
    
    private JFrame frame;
    private JTextArea textArea;
    private JTextField textField;
    private JButton sendButton;
    private PrintStream ps;
    private BufferedReader br1;
    private Socket socket; // Declare socket at the class level

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ServerGUI());
    }

    public ServerGUI() {
        setupLogger();
        try {
            setupGUI();
            setupConnection();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error initializing server", e);
        }
    }

    private void setupLogger() {
        try {
            LogManager.getLogManager().reset();
            logger.setLevel(Level.ALL);

            ConsoleHandler ch = new ConsoleHandler();
            ch.setLevel(Level.SEVERE);
            logger.addHandler(ch);

            FileHandler fh = new FileHandler("server.log", true);
            fh.setLevel(Level.FINE);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error setting up logger", e);
        }
    }

    private void setupGUI() {
        frame = new JFrame("Chat Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        textField = new JTextField(20);
        sendButton = new JButton("Send");

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(textField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(panel, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> sendMessage());
        textField.addActionListener(e -> sendMessage());

        frame.setVisible(true);
    }

    private void setupConnection() throws Exception {
        ServerSocket ssobj = new ServerSocket(2100);
       

        socket = ssobj.accept(); // Initialize the socket variable
        textArea.append("Client connected\n");
        logger.info("Client connected");

        ps = new PrintStream(socket.getOutputStream());
        br1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        new Thread(this::receiveMessages).start();
    }

    private void sendMessage() {
        String message = textField.getText();
        if (!message.equals("")) {
            ps.println(message);
            textArea.append("Server: " + message + "\n");
            textField.setText("");
            logger.info("Sent message: " + message);
        }
    }

    private void receiveMessages() {
        try {
            String message;
            while ((message = br1.readLine()) != null) {
                textArea.append("Client: " + message + "\n");
                logger.info("Received message: " + message);
            }
        } catch (IOException e) {
            // Log a simplified disconnection message
            textArea.append("Client disconnected\n");
            logger.log(Level.WARNING, "Client disconnected: Connection reset");
        } finally {
            // Clean up resources
            try {
                if (br1 != null) br1.close();
                if (ps != null) ps.close();
                if (socket != null) socket.close(); // Use the class-level socket variable
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "Error closing resources", ex);
            }
        }
    }
}
