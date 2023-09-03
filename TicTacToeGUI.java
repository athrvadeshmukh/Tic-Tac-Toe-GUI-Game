import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI extends JFrame implements ActionListener {
    private JButton[][] buttons;
    private char currentPlayer = 'X';
    private int playerXScore = 0;
    private int playerOScore = 0;
    private JLabel scoreLabel;

    public TicTacToeGUI(int boardSize) {
        setTitle("Tic-Tac-Toe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 450);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(boardSize, boardSize));
        buttons = new JButton[boardSize][boardSize];

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                buttons[row][col] = new JButton("");
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].setActionCommand(row + "-" + col);
                buttons[row][col].addActionListener(this);
                boardPanel.add(buttons[row][col]);
            }
        }

        add(boardPanel, BorderLayout.CENTER);

        scoreLabel = new JLabel("Player X: " + playerXScore + "  Player O: " + playerOScore, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        add(scoreLabel, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String[] rowCol = button.getActionCommand().split("-");
        int row = Integer.parseInt(rowCol[0]);
        int col = Integer.parseInt(rowCol[1]);

        if (buttons[row][col].getText().equals("")) {
            buttons[row][col].setText(Character.toString(currentPlayer));
            buttons[row][col].setEnabled(false);
            if (checkWin(row, col)) {
                JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!");
                updateScore(currentPlayer);
                resetBoard();
            } else if (isBoardFull()) {
                JOptionPane.showMessageDialog(this, "The game is a draw!");
                resetBoard();
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            }
        }
    }

    private boolean checkWin(int row, int col) {
        return checkRow(row) || checkColumn(col) || checkDiagonal() || checkAntiDiagonal();
    }

    private boolean checkRow(int row) {
        for (int col = 0; col < buttons.length; col++) {
            if (!buttons[row][col].getText().equals(Character.toString(currentPlayer))) {
                return false;
            }
        }
        return true;
    }

    private boolean checkColumn(int col) {
        for (int row = 0; row < buttons.length; row++) {
            if (!buttons[row][col].getText().equals(Character.toString(currentPlayer))) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDiagonal() {
        for (int i = 0; i < buttons.length; i++) {
            if (!buttons[i][i].getText().equals(Character.toString(currentPlayer))) {
                return false;
            }
        }
        return true;
    }

    private boolean checkAntiDiagonal() {
        for (int i = 0; i < buttons.length; i++) {
            if (!buttons[i][buttons.length - 1 - i].getText().equals(Character.toString(currentPlayer))) {
                return false;
            }
        }
        return true;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons.length; col++) {
                if (buttons[row][col].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetBoard() {
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons.length; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setEnabled(true);
            }
        }
        currentPlayer = 'X';
    }

    private void updateScore(char player) {
        if (player == 'X') {
            playerXScore++;
        } else {
            playerOScore++;
        }
        scoreLabel.setText("Player X: " + playerXScore + "  Player O: " + playerOScore);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int boardSize = Integer.parseInt(JOptionPane.showInputDialog("Enter the size of the board (e.g., 3 for a 3x3 board):"));
            new TicTacToeGUI(boardSize).setVisible(true);
        });
    }
}

