/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package juegotictactoe;

/**
 *
 * @author Usuario
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class JuegoTicTacToe extends JFrame implements ActionListener {
    private JButton[][] buttons;
    private boolean playerTurn;
    private boolean gameEnded;
    private int level;
    
    public JuegoTicTacToe(int level) {
        buttons = new JButton[3][3];
        playerTurn = true;
        gameEnded = false;
        this.level = level;
        
        JPanel panel = new JPanel(new GridLayout(3, 3));
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 80));
                buttons[i][j].addActionListener(this);
                panel.add(buttons[i][j]);
            }
        }
        
        add(panel);
        setTitle("Tic Tac Toe");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        
        if (button.getText().equals("") && !gameEnded) {
            if (playerTurn) {
                button.setText("X");
                button.setForeground(Color.BLUE);
            } else {
                button.setText("O");
                button.setForeground(Color.RED);
            }
            
            playerTurn = !playerTurn;
            checkGameStatus();
            
            if (!gameEnded && !playerTurn) {
                playMachineMove();
            }
        }
    }
    
    public void checkGameStatus() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (!buttons[i][0].getText().equals("") && buttons[i][0].getText().equals(buttons[i][1].getText()) && buttons[i][0].getText().equals(buttons[i][2].getText())) {
                gameEnded = true;
                showEndGameMessage(buttons[i][0].getText());
                return;
            }
        }
        
        // Check columns
        for (int i = 0; i < 3; i++) {
            if (!buttons[0][i].getText().equals("") && buttons[0][i].getText().equals(buttons[1][i].getText()) && buttons[0][i].getText().equals(buttons[2][i].getText())) {
                gameEnded = true;
                showEndGameMessage(buttons[0][i].getText());
                return;
            }
        }
        
        // Check diagonals
        if (!buttons[0][0].getText().equals("") && buttons[0][0].getText().equals(buttons[1][1].getText()) && buttons[0][0].getText().equals(buttons[2][2].getText())) {
            gameEnded = true;
            showEndGameMessage(buttons[0][0].getText());
            return;
        }
        
        if (!buttons[0][2].getText().equals("") && buttons[0][2].getText().equals(buttons[1][1].getText()) && buttons[0][2].getText().equals(buttons[2][0].getText())) {
            gameEnded = true;
            showEndGameMessage(buttons[0][2].getText());
            return;
        }
        
        // Check if it's a draw
        boolean isDraw = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    isDraw = false;
                    break;
                }
            }
        }
        
        if (isDraw) {
            gameEnded = true;
            showEndGameMessage("Draw");
        }
    }
    
    public void showEndGameMessage(String result) {
        int option = JOptionPane.showOptionDialog(this, result, "Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Restart", "Change Difficulty"}, null);
        
        if (option == 0) {
            resetGame();
        } else {
            openDifficultyMenu();
        }
    }
    
    public void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        
        playerTurn = true;
        gameEnded = false;
    }
    
    public void openDifficultyMenu() {
        String[] options = {"Easy", "Medium", "Hard"};
        int option = JOptionPane.showOptionDialog(this, "Select Difficulty", "Difficulty Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        
        if (option != -1) {
            level = option;
            resetGame();
        } else {
            resetGame();
        }
    }
    
    public void playMachineMove() {
        // Random move
        if (level == 0) {
            playRandomMove();
        } else if (level == 1) {
            playSmartMove();
        } else if (level == 2) {
            playHardMove();
        }
    }
    
    public void playRandomMove() {
        int row, col;
        
        do {
            row = (int) (Math.random() * 3);
            col = (int) (Math.random() * 3);
        } while (!buttons[row][col].getText().equals(""));
        
        buttons[row][col].setText("O");
        buttons[row][col].setForeground(Color.RED);
        
        playerTurn = !playerTurn;
        checkGameStatus();
    }
    
    public void playSmartMove() {
        // Play random move if available, otherwise play a random empty cell
        if (!playWinningMove("O")) {
            if (!playWinningMove("X")) {
                playRandomMove();
            }
        }
    }
    
    public void playHardMove() {
        // Play winning move if possible, otherwise play blocking move, otherwise play random move
        if (!playWinningMove("O")) {
            if (!playWinningMove("X")) {
                playRandomMove();
            }
        }
    }
    
    public boolean playWinningMove(String player) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(player) && buttons[i][0].getText().equals(buttons[i][1].getText()) && buttons[i][2].getText().equals("")) {
                buttons[i][2].setText("O");
                buttons[i][2].setForeground(Color.RED);
                playerTurn = !playerTurn;
                checkGameStatus();
                return true;
            }
            
            if (buttons[i][0].getText().equals(player) && buttons[i][2].getText().equals(player) && buttons[i][1].getText().equals("")) {
                buttons[i][1].setText("O");
                buttons[i][1].setForeground(Color.RED);
                playerTurn = !playerTurn;
                checkGameStatus();
                return true;
            }
            
            if (buttons[i][1].getText().equals(player) && buttons[i][2].getText().equals(player) && buttons[i][0].getText().equals("")) {
                buttons[i][0].setText("O");
                buttons[i][0].setForeground(Color.RED);
                playerTurn = !playerTurn;
                checkGameStatus();
                return true;
            }
        }
        
        // Check columns
        for (int i = 0; i < 3; i++) {
            if (buttons[0][i].getText().equals(player) && buttons[0][i].getText().equals(buttons[1][i].getText()) && buttons[2][i].getText().equals("")) {
                buttons[2][i].setText("O");
                buttons[2][i].setForeground(Color.RED);
                playerTurn = !playerTurn;
                checkGameStatus();
                return true;
            }
            
            if (buttons[0][i].getText().equals(player) && buttons[2][i].getText().equals(player) && buttons[1][i].getText().equals("")) {
                buttons[1][i].setText("O");
                buttons[1][i].setForeground(Color.RED);
                playerTurn = !playerTurn;
                checkGameStatus();
                return true;
            }
            
            if (buttons[1][i].getText().equals(player) && buttons[2][i].getText().equals(player) && buttons[0][i].getText().equals("")) {
                buttons[0][i].setText("O");
                buttons[0][i].setForeground(Color.RED);
                playerTurn = !playerTurn;
                checkGameStatus();
                return true;
            }
        }
        
        // Check diagonals
        if (buttons[0][0].getText().equals(player) && buttons[0][0].getText().equals(buttons[1][1].getText()) && buttons[2][2].getText().equals("")) {
            buttons[2][2].setText("O");
            buttons[2][2].setForeground(Color.RED);
            playerTurn = !playerTurn;
            checkGameStatus();
            return true;
        }
        
        if (buttons[0][0].getText().equals(player) && buttons[2][2].getText().equals(player) && buttons[1][1].getText().equals("")) {
            buttons[1][1].setText("O");
            buttons[1][1].setForeground(Color.RED);
            playerTurn = !playerTurn;
            checkGameStatus();
            return true;
        }
        
        if (buttons[1][1].getText().equals(player) && buttons[2][2].getText().equals(player) && buttons[0][0].getText().equals("")) {
            buttons[0][0].setText("O");
            buttons[0][0].setForeground(Color.RED);
            playerTurn = !playerTurn;
            checkGameStatus();
            return true;
        }
        
        if (buttons[0][2].getText().equals(player) && buttons[1][1].getText().equals(player) && buttons[2][0].getText().equals("")) {
            buttons[2][0].setText("O");
            buttons[2][0].setForeground(Color.RED);
            playerTurn = !playerTurn;
            checkGameStatus();
            return true;
        }
        
        if (buttons[0][2].getText().equals(player) && buttons[2][0].getText().equals(player) && buttons[1][1].getText().equals("")) {
            buttons[1][1].setText("O");
            buttons[1][1].setForeground(Color.RED);
            playerTurn = !playerTurn;
            checkGameStatus();
            return true;
        }
        
        if (buttons[1][1].getText().equals(player) && buttons[2][0].getText().equals(player) && buttons[0][2].getText().equals("")) {
            buttons[0][2].setText("O");
            buttons[0][2].setForeground(Color.RED);
            playerTurn = !playerTurn;
            checkGameStatus();
            return true;
        }
        
        return false;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                String[] options = {"Easy", "Medium", "Hard"};
                int option = JOptionPane.showOptionDialog(null, "Select Difficulty", "Difficulty Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                
                if (option != -1) {
                    new JuegoTicTacToe(option);
                }
            }
        });
    }
}
