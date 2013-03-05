package app.game.tictactoe;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class TicTacToeActivity extends Activity {
	private Button turnBtn = null;
	private Game game = new Game();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tic_tac_toe);
		game.init();	
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
    	int x = Integer.parseInt(tag[0]);
    	int y = Integer.parseInt(tag[1]);
    	int currentPlayer = game.getPlayer();
    	
    	b.setEnabled(false);
        b.setText(currentPlayer == 1 ? "x" : "O");
        game.mark(x,y);
        
        if (game.getGameStatus() == 1) {
            turnBtn.setText("Player " + currentPlayer + "won!");
        } else if (game.getGameStatus() == 0) {
            turnBtn.setText("Game Ended with a tie");
        } else {
            turnBtn.setText("Player " + currentPlayer + "'s turn");
        }
    }
    
}
