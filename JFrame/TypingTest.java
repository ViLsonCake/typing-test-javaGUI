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
    private JButton startTest;
    private JRadioButton tenWords;
    private JRadioButton fifteenWords;
    private JRadioButton thirtyWords;
    private JLabel wordWPM;
    private JLabel resultWPM;
    private ButtonGroup groupOfRadioButtons;
    private String testText;
    private char cursorCharacter = '|';
    private RandomWords randomWords;
    private String userInput = "";
    private int currentCursorIndex = 0;
    private boolean keyDoNotPressed = true;
    private boolean testCompleted = false;
    private boolean textNowCorrect = true;
    private long startTestTime, endTestTime;
    private int userTextLength = 15;

    public TypingTest() throws IOException, ParseException {
        // Frame config
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(650, 450);
        this.setLayout(null);
        this.setResizable(false);
        this.setTitle("Typing test");
        this.addKeyListener(this);
        this.setFocusable(true);

        randomWords = new RandomWords();

        // Set random text
        setTestText(cursorCharacter + randomWords.randomSample(userTextLength));

        // JPanel config
        mainPanel = new JPanel();
        mainPanel.setSize(650, 450);
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 650, 450);
        mainPanel.setBackground(Color.decode(TestConsts.backgroundPanelColor));
        mainPanel.addKeyListener(this);

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
        regenerateTextButton.setFont(new Font(TestConsts.mainFont, Font.PLAIN, 15));
        regenerateTextButton.setBackground(Color.decode(TestConsts.buttonsBackgroundColor));
        regenerateTextButton.setForeground(Color.decode(TestConsts.regenerateButtonForegroundColor));
        regenerateTextButton.addActionListener(this);

        startTest = new JButton();
        startTest.setText("Start");
        startTest.setBounds(545, 280, 80, 50);
        startTest.setFont(new Font(TestConsts.mainFont, Font.PLAIN, 20));
        startTest.setBackground(Color.decode(TestConsts.buttonsBackgroundColor));
        startTest.setForeground(Color.decode(TestConsts.regenerateButtonForegroundColor));
        startTest.addActionListener(this);

        //JLabel result WPM config
        wordWPM = new JLabel();
        wordWPM.setText("WPM:");
        wordWPM.setFont(new Font(TestConsts.mainFont, Font.PLAIN, 28));
        wordWPM.setForeground(Color.white);
        wordWPM.setBounds(18, 10, 100, 60);

        resultWPM = new JLabel();
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
        groupOfRadioButtons = new ButtonGroup();
        groupOfRadioButtons.add(tenWords);
        groupOfRadioButtons.add(fifteenWords);
        groupOfRadioButtons.add(thirtyWords);

        // Set selected variable
        fifteenWords.setSelected(true);

        // Add elements
        mainPanel.add(textArea);
        mainPanel.add(regenerateTextButton);
        mainPanel.add(startTest);
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

    public void setStartTestTime(long time) {
        this.startTestTime = time;
    }

    public void setEndTestTime(long time) {
        this.endTestTime = time;
    }

    public String getTestText() {
        return testText;
    }

    public long toSecond(long timeInMillis) {
        return timeInMillis / 1000;
    }

    public boolean checkCorrect() {
        return testText.contains(userInput);
    }

    public int countOfCharacters() {
        int count = 0;

        for (String word : testText.split(" ")) {
            count += word.length();
        }

        return count;
    }

    public double calculateWPM(long time) {
        return ((double) countOfCharacters() / 5) / ((double) time / 60);
    }

    public boolean testIsCompleted() {
        return (userInput + cursorCharacter).equals(testText);
    }

    public void deleteLastChar() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(userInput);

        if (stringBuilder.length() >= 1)
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        userInput = stringBuilder.toString();
    }

    public void cursorToNextCharacter() {
        char [] charArrayFromText = testText.toCharArray();

        // Swap values
        char buffer = charArrayFromText[currentCursorIndex];
        charArrayFromText[currentCursorIndex] = charArrayFromText[currentCursorIndex + 1];
        charArrayFromText[currentCursorIndex + 1] = buffer;

        if ((currentCursorIndex + 3) <= charArrayFromText.length)
            currentCursorIndex++;

        String output = "";

        for (char character : charArrayFromText) {
            output += character;
        }

        // Update visible text
        this.testText = output;
        textArea.setText(testText);
    }

    public void cursorToPreviousCharacter() {
        if (currentCursorIndex == 0)
            return;

        char [] charArrayFromText = testText.toCharArray();

        // Swap values
        char buffer = charArrayFromText[currentCursorIndex - 1];
        charArrayFromText[currentCursorIndex - 1] = charArrayFromText[currentCursorIndex];
        charArrayFromText[currentCursorIndex] = buffer;

        currentCursorIndex--;

        String output = "";


        for (char character : charArrayFromText) {
            output += character;
        }

        // Update visible text
        this.testText = output;
        textArea.setText(testText);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // If test is completed, don't register key released
        if (testCompleted)
            return;

        // All letters, space, period, and apostrophe
        if ((e.getKeyCode() >= 65 && e.getKeyCode() <= 90) || (e.getKeyCode() == 32)
                || (e.getKeyCode() == 190) || (e.getKeyCode() == 222)) {
            if (keyDoNotPressed) {
                setStartTestTime(toSecond(System.currentTimeMillis()));
                keyDoNotPressed = false;
            }

            // If current character is correct
            if (textNowCorrect) {
                userInput = userInput.concat(String.valueOf(e.getKeyChar()));
                cursorToNextCharacter();
            }

            // Complete test case
            if (testIsCompleted()) {
                textArea.setForeground(Color.GREEN);
                setEndTestTime(toSecond(System.currentTimeMillis()));
                resultWPM.setText(String.valueOf((int) calculateWPM(endTestTime - startTestTime)));
                testCompleted = true;
                regenerateTextButton.setFocusable(true);
                startTest.setFocusable(true);
                tenWords.setFocusable(true);
                fifteenWords.setFocusable(true);
                thirtyWords.setFocusable(true);
                wordWPM.setFocusable(true);
                resultWPM.setFocusable(true);
                mainPanel.setFocusable(true);
                return;
            }

            if (checkCorrect()) {
                textNowCorrect = true;
                textArea.setForeground(Color.decode(TestConsts.foregroundTextAreaColor));
            } else {
                textArea.setForeground(Color.RED);
                textNowCorrect = false;
            }

        // Backspace
        } else if (e.getKeyCode() == 8) {
            deleteLastChar();
            cursorToPreviousCharacter();

            if (checkCorrect()) {
                textNowCorrect = true;
                textArea.setForeground(Color.decode(TestConsts.foregroundTextAreaColor));
            } else {
                textArea.setForeground(Color.RED);
                textNowCorrect = false;
            }
        }
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
                textArea.setText(getTestText());
                userInput = "";
                currentCursorIndex = 0;
                textNowCorrect = true;
                keyDoNotPressed = true;
                textArea.setForeground(Color.decode(TestConsts.foregroundTextAreaColor));
            } catch (IOException | ParseException ex) {
                throw new RuntimeException(ex);
            }
        }

        if (e.getSource() == startTest) {
            testCompleted = false;
            regenerateTextButton.setFocusable(false);
            startTest.setFocusable(false);
            tenWords.setFocusable(false);
            fifteenWords.setFocusable(false);
            thirtyWords.setFocusable(false);
            wordWPM.setFocusable(false);
            resultWPM.setFocusable(false);
            mainPanel.setFocusable(false);
        }
    }
}
