package app.game.tictactoe;


import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class TicTacToeActivity extends Activity {
	private Button turnBtn = null;
	private ArrayList<Button> buttons = new ArrayList<Button>();
	private Game game = new Game();
	
	private void getAllButtons() {
		TableLayout view = (TableLayout) this.findViewById(R.id.game_board);
		int childcount = view.getChildCount();
		
		for (int i = 0; i < childcount; i++){
		      TableRow tr = (TableRow) view.getChildAt(i);
		      for (int j = 0; j < tr.getChildCount(); j++){
		          buttons.add(((Button) tr.getChildAt(j)));
		      }
		}	
	}
	
	private void disableButtons() {
		for (Button b : buttons) {
			b.setEnabled(false);
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tic_tac_toe);
		
		game.init();
		getAllButtons();	
		turnBtn = (Button) this.findViewById(R.id.player);
		turnBtn.setText("Player 1's turn");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
    public void setText(View view) {
    	Button b = (Button) view;
    	String[] tag = b.getTag().toString().split("_");
    	int currentPlayer = game.getPlayer();
    	
    	b.setEnabled(false);
        b.setText(currentPlayer == 1 ? "x" : "O");
        game.mark(Integer.parseInt(tag[0]),Integer.parseInt(tag[1]));
        
        if (game.getGameStatus() == 1) {
            turnBtn.setText("Player " + currentPlayer + "won!");
            disableButtons();
        } else if (game.getGameStatus() == 0) {
            turnBtn.setText("Game Ended with a tie");
            disableButtons();
        } else {
            turnBtn.setText("Player " +  game.getPlayer() + "'s turn");
        }
    }   
}
