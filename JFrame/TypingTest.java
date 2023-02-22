package com.project.JFrame;

import com.project.random.RandomWords;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class TypingTest extends JFrame implements KeyListener {

    private JTextArea textArea;
    private String testText;
    private char cursorCharacter = '|';
    private RandomWords randomWords;

    private int userTextLength = 15;

    public TypingTest() throws IOException, ParseException {
        // Frame config
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(650, 450);
        this.setLayout(null);
        this.setResizable(false);
        this.setTitle("Key listener");
        this.addKeyListener(this);

        randomWords = new RandomWords();

        // Set random text
        setTestText(cursorCharacter + randomWords.randomSample(userTextLength));

        // Text area
        textArea = new JTextArea(5, 7);

        textArea.setBounds(50, 50, 525, 200);
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 25));
        textArea.setText(testText);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFocusable(false);

        // Add elements
        this.add(textArea);
        this.setVisible(true);
    }

    // Setters
    public void setTestText(String testText) {
        this.testText = testText;
    }

    public void setUserTextLength(int length) {
        this.userTextLength = length;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
