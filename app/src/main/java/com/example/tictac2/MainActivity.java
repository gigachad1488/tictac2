package com.example.tictac2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{
    private GridLayout gridLayout;
    private Button[][] buttons;
    private boolean isPlayerX = true;
    private boolean gameEnded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.gridLayout);
        initializeButtons();
    }

    private void initializeButtons() {
        buttons = new Button[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new Button(this);
                buttons[i][j].setTextSize(24);
                buttons[i][j].setTag(R.id.gridCell, new int[]{i, j});
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onGridButtonClick((Button) view);
                    }
                });
                gridLayout.addView(buttons[i][j]);
            }
        }
    }

    private void onGridButtonClick(Button button) {
        if (gameEnded) {
            return;
        }

        int[] position = (int[]) button.getTag(R.id.gridCell);
        int row = position[0];
        int col = position[1];

        if (buttons[row][col].getText().toString().isEmpty()) {
            if (isPlayerX) {
                buttons[row][col].setText("X");
            } else {
                buttons[row][col].setText("O");
            }
            isPlayerX = !isPlayerX;

            if (checkForWin(row, col)) {
                String winner = isPlayerX ? "O" : "X";
                Toast.makeText(this, "Игрок " + winner + " выиграл!", Toast.LENGTH_SHORT).show();
                gameEnded = true;
            } else if (isBoardFull()) {
                Toast.makeText(this, "Ничья!", Toast.LENGTH_SHORT).show();
                gameEnded = true;
            }
        }
    }

    private boolean checkForWin(int row, int col) {
        return (buttons[row][0].getText().equals(buttons[row][1].getText()) && buttons[row][1].getText().equals(buttons[row][2].getText())) ||
                (buttons[0][col].getText().equals(buttons[1][col].getText()) && buttons[1][col].getText().equals(buttons[2][col].getText())) ||
                (row == col && buttons[0][0].getText().equals(buttons[1][1].getText()) && buttons[1][1].getText().equals(buttons[2][2].getText())) ||
                ((row + col) == 2 && buttons[0][2].getText().equals(buttons[1][1].getText()) && buttons[1][1].getText().equals(buttons[2][0].getText()));
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().toString().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    }