package hussainshaikh.com.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    boolean gameActive = true;

    int activePlayer = 0;
    int [] gameState = {2,2,2, 2,2,2, 2,2,2};

    int [][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void PlayerTap(View view) {
        Button buttonClick = (Button) view;
        int tappedButton = Integer.parseInt(buttonClick.getTag().toString());
        //for resetgame
        if (!gameActive){
            gameReset();
        }

        if (gameState[tappedButton] == 2){
            gameState[tappedButton] = activePlayer;
            buttonClick.setTranslationY(-1000f);
            if (activePlayer == 0){
                buttonClick.setText("x");
                activePlayer = 1;

                TextView Status = findViewById(R.id.status_bar);
                Status.setText("O's Turn - Tap to play");

            }else {
                buttonClick.setText("O");
                activePlayer = 0;

                TextView Status = findViewById(R.id.status_bar);
                Status.setText("x's Turn - Tap to play");
            }
            buttonClick.animate().translationYBy(1000f).setDuration(300);

        }
        //Check any player has won
        for (int[]winPosition: winPositions){
            if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
            gameState[winPosition[1]] == gameState[winPosition[2]] &&
            gameState[winPosition[0]] !=2){
                //Some body has won
                String wneer;
                gameActive = false;

                if (gameState[winPosition[0]] == 0){
                    wneer = "X has won";
                }else {
                    wneer = "O has won";
                }
                //Winn