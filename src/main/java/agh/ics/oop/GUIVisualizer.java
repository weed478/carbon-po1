package agh.ics.oop;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class GUIVisualizer {
    private final JTextArea textArea;
    private final Queue<String> maps = new LinkedList<>();

    public GUIVisualizer(String initialMap) {
        JFrame frame = new JFrame();

        textArea = new JTextArea();
        textArea.setFont(Font.decode(Font.MONOSPACED));
        textArea.setBounds(0, 0, 500, 500);
        textArea.setText(initialMap);
        frame.add(textArea);

        JButton nextButton = new JButton("Move");
        nextButton.setBounds(500, 0, 80, 30);
        nextButton.addActionListener(e -> drawNext());
        frame.add(nextButton);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public void pushMap(String map) {
        maps.add(map);
    }

    public void drawNext() {
        if (!maps.isEmpty()) {
            textArea.setText(maps.remove());
        }
    }
}
