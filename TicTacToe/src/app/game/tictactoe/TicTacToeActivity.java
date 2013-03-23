package app.game.tictactoe;


import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class TicTacToeActivity extends Activity {
	private Button turnBtn = null;
	private ArrayList<Button> buttons = new ArrayList<Button>();
	private Game game = new Game();
	private Boolean isSinglerPlayer = false;
	private Handler mHandler = new Handler(new MyHandlerCallback());
    private static final int MSG_COMPUTER_TURN = 1;
    private static final long COMPUTER_DELAY_MS = 500;
	
	private class MyHandlerCallback implements Callback {
        public boolean handleMessage(Message msg) {
            if (msg.what == MSG_COMPUTER_TURN) {
            	int [] pcMovement = game.computerMove();	          
            	Button pcMovementBtn = buttons.get(pcMovement[1] + pcMovement[0] * 3);
            	
            	pcMovementBtn.setText("0");
            	pcMovementBtn.setEnabled(false);          
            	setGameStatus("Your turn", "Computer won!");
            	
            	return true;
            }
            return false;
        }
    }
	
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
		
		isSinglerPlayer = getIntent().getStringExtra(MainActivity.GAME_MODE).equals("1p");	
		game.init();
		getAllButtons();	
		turnBtn = (Button) this.findViewById(R.id.player);
		turnBtn.setText("Player 1's turn");
		
		if (isSinglerPlayer) {
			game.vsComputer();
		}		
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
    	int thePlayerMadeTheMove = game.getPlayer();
    	
    	b.setEnabled(false);
        game.mark(Integer.parseInt(tag[0]), Integer.parseInt(tag[1]));
        
        if (isSinglerPlayer) {
        	b.setText("X");	 
        	
        	if (game.getGameStatus() == -1) {
        		turnBtn.setText("Computer's turn");
    			mHandler.sendEmptyMessageDelayed(MSG_COMPUTER_TURN, COMPUTER_DELAY_MS);
        	} else {
        		setGameStatus("", "You won!");
        	}
        } else {
        	b.setText(thePlayerMadeTheMove == 1 ? "X" : "O");  	 
       	 	setGameStatus("Player " +  game.getPlayer() + "'s turn", "Player " +  thePlayerMadeTheMove + "won!");
        }      
    } 
    
    public void setGameStatus(String turnMsg, String wonMsg) {
    	switch(game.getGameStatus()){
			case -1: 
				turnBtn.setText(turnMsg);
				break;
			case 1: 
				turnBtn.setText(wonMsg);
				disableButtons();
				break;
			case 0:
				turnBtn.setText("Game Ended with a tie");
				disableButtons();
				break;
    	}
    } 
}
