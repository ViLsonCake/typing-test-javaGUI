package com.project.JFrame;

import com.project.random.RandomWords;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class TypingTest extends JFrame implements KeyListener, ActionListener {

    private JTextArea textArea;
    private JPanel mainPanel;
    private JButton regenerateTextButton;
    private JRadioButton tenWords;
    private JRadioButton fifteenWords;
    private JRadioButton thirtyWords;
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
        this.setTitle("Typing test");
        this.addKeyListener(this);

        randomWords = new RandomWords();

        // Set random text
        setTestText(cursorCharacter + randomWords.randomSample(userTextLength));

        // JPanel config
        mainPanel = new JPanel();
        mainPanel.setSize(650, 450);
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 650, 450);
        mainPanel.setBackground(Color.decode(TestConsts.backgroundPanelColor));

        // Text area
        textArea = new JTextArea(8, 7);

        textArea.setBounds(110, 10, 520, 200);
        textArea.setEditable(false);
        textArea.setFont(new Font(TestConsts.mainFont, Font.PLAIN, 25));
        textArea.setText(testText);
        textArea.setBackground(Color.decode(TestConsts.backgroundTextAreaColor));
        textArea.setForeground(Color.decode(TestConsts.foregroundTextAreaColor));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFocusable(false);

        // Regenerate text button config
        regenerateTextButton = new JButton();

        regenerateTextButton.setBounds(110, 280, 150, 50);
        regenerateTextButton.setText("Regenerate");
        regenerateTextButton.setBorderPainted(false);
        regenerateTextButton.setFont(new Font(TestConsts.mainFont, Font.PLAIN, 20));
        regenerateTextButton.setBackground(Color.decode(TestConsts.buttonsBackgroundColor));
        regenerateTextButton.setForeground(Color.decode(TestConsts.regenerateButtonForegroundColor));
        regenerateTextButton.addActionListener(this);

        //JLabel result WPM config
        JLabel wordWPM = new JLabel();
        wordWPM.setText("WPM:");
        wordWPM.setFont(new Font(TestConsts.mainFont, Font.PLAIN, 28));
        wordWPM.setForeground(Color.white);
        wordWPM.setBounds(18, 10, 100, 60);

        JLabel resultWPM = new JLabel();
        resultWPM.setText("?");
        resultWPM.setFont(new Font(TestConsts.mainFont, Font.PLAIN, 28));
        resultWPM.setForeground(Color.white);
        resultWPM.setBounds(32, 55, 100, 60);

        // Radio buttons for length of text
        tenWords = new JRadioButton("10");
        fifteenWords = new JRadioButton("15");
        thirtyWords = new JRadioButton("30");

        // Set event listener for each radio buttons and other config
        tenWords.addActionListener(this);
        fifteenWords.addActionListener(this);
        thirtyWords.addActionListener(this);

        tenWords.setBounds(380, 280, 50, 50);
        fifteenWords.setBounds(430, 280, 50, 50);
        thirtyWords.setBounds(480, 280, 50, 50);

        tenWords.setBackground(Color.decode(TestConsts.buttonsBackgroundColor));
        fifteenWords.setBackground(Color.decode(TestConsts.buttonsBackgroundColor));
        thirtyWords.setBackground(Color.decode(TestConsts.buttonsBackgroundColor));

        // Merge buttons
        ButtonGroup groupOfRadioButtons = new ButtonGroup();
        groupOfRadioButtons.add(tenWords);
        groupOfRadioButtons.add(fifteenWords);
        groupOfRadioButtons.add(thirtyWords);

        // Set selected variable
        fifteenWords.setSelected(true);


        // Add elements
        mainPanel.add(textArea);
        mainPanel.add(regenerateTextButton);
        mainPanel.add(resultWPM);
        mainPanel.add(wordWPM);
        mainPanel.add(tenWords);
        mainPanel.add(fifteenWords);
        mainPanel.add(thirtyWords);

        this.add(mainPanel);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == tenWords)
            setUserTextLength(10);
        else if (e.getSource() == fifteenWords)
            setUserTextLength(15);
        else if (e.getSource() == thirtyWords)
            setUserTextLength(30);

        if (e.getSource() == regenerateTextButton) {
            try {
                setTestText(cursorCharacter + randomWords.randomSample(userTextLength));
                textArea.setText(testText);
            } catch (IOException | ParseException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
