package huffman_coding_1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;
import javafx.geometry.Pos;

public class HuffmanCodingFX extends Application {
    private Map<Character, String> huffmanCode = new HashMap<>();
    private Node root;

    private Node buildHuffmanTree(Map<Character, Integer> freqMap) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }

        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();

            Node parent = new Node(left.frequency + right.frequency, left, right);
            pq.add(parent);
        }

        return pq.poll();
    }

    private void generateCodes(Node node, String code) {
        if (node == null) return;

        if (node.left == null && node.right == null) {
            huffmanCode.put(node.character, code);
        }

        generateCodes(node.left, code + "0");
        generateCodes(node.right, code + "1");
    }

    public String compress(String input) {
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : input.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }

        root = buildHuffmanTree(freqMap);
        generateCodes(root, "");

        StringBuilder encoded = new StringBuilder();
        for (char c : input.toCharArray()) {
            encoded.append(huffmanCode.get(c));
        }

        return encoded.toString();
    }

    public String decompress(String encoded) {
        StringBuilder decoded = new StringBuilder();
        Node current = root;

        for (char bit : encoded.toCharArray()) {
            current = (bit == '0') ? current.left : current.right;

            if (current.left == null && current.right == null) {
                decoded.append(current.character);
                current = root;
            }
        }

        return decoded.toString();
    }

    private void drawTree(GraphicsContext gc, Node node, double x, double y, double xOffset, double yOffset) {
        if (node == null) return;

        if (node.left != null) {
            gc.strokeLine(x, y, x - xOffset, y + yOffset);
            drawTree(gc, node.left, x - xOffset, y + yOffset, xOffset / 2, yOffset);
        }

        if (node.right != null) {
            gc.strokeLine(x, y, x + xOffset, y + yOffset);
            drawTree(gc, node.right, x + xOffset, y + yOffset, xOffset / 2, yOffset);
        }

        String label = (node.character == '\0') ? String.valueOf(node.frequency) : node.character + ":" + node.frequency;
        gc.setFill(Color.LIGHTBLUE);
        gc.fillOval(x - 15, y - 15, 30, 30);
        gc.setFill(Color.BLACK);
        gc.strokeOval(x - 15, y - 15, 30, 30);
        gc.fillText(label, x - 20, y - 25);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Huffman Coding");

        Label inputLabel = new Label("Enter text to compress:");
        TextArea inputTextArea = new TextArea();
        inputTextArea.setPromptText("Enter your text here");
        inputTextArea.setWrapText(true);

        Button compressBtn = new Button("Compress");
        Button decompressBtn = new Button("Decompress");

        Label encodedLabel = new Label("Compressed text:");
        TextArea encodedTextArea = new TextArea();
        encodedTextArea.setEditable(false);
        encodedTextArea.setWrapText(true);

        Label decodedLabel = new Label("Decompressed text:");
        TextArea decodedTextArea = new TextArea();
        decodedTextArea.setEditable(false);
        decodedTextArea.setWrapText(true);

        Canvas treeCanvas = new Canvas(800, 400);
        GraphicsContext gc = treeCanvas.getGraphicsContext2D();

        VBox rootLayout = new VBox(10);
        rootLayout.setPadding(new Insets(15));
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.getChildren().addAll(
                inputLabel, inputTextArea, compressBtn, encodedLabel,
                encodedTextArea, decompressBtn, decodedLabel, decodedTextArea, treeCanvas
        );

        compressBtn.setOnAction(event -> {
            String input = inputTextArea.getText();
            if (input.isEmpty()) {
                showAlert("Input Error", "Please enter text to compress.");
                return;
            }
            String compressed = compress(input);
            encodedTextArea.setText(compressed);
            decodedTextArea.clear();
            gc.clearRect(0, 0, treeCanvas.getWidth(), treeCanvas.getHeight());
            if (root != null) {
                drawTree(gc, root, treeCanvas.getWidth() / 2, 50, 200, 50);
            } else {
                showAlert("Error", "Huffman Tree is not constructed properly.");
            }
        });

        decompressBtn.setOnAction(event -> {
            String encoded = encodedTextArea.getText();
            if (encoded.isEmpty()) {
                showAlert("Input Error", "Please compress text first.");
                return;
            }
            String decompressed = decompress(encoded);
            decodedTextArea.setText(decompressed);
        });

        Scene scene = new Scene(rootLayout, 850, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    static class Node implements Comparable<Node> {
        char character;
        int frequency;
        Node left, right;

        public Node(char character, int frequency) {
            this.character = character;
            this.frequency = frequency;
        }

        public Node(int frequency, Node left, Node right) {
            this.character = '\0';
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(Node other) {
            return this.frequency - other.frequency;
        }
    }
}
