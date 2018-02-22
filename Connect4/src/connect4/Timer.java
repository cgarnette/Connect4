package connect4;

public class Timer extends Thread{

	private Player a, b;
	Player current;
	private Main driver;
	boolean pause;
	boolean clicked;
	
	public Timer(Player a, Player b, Main driver) {
		this.a = a;
		this.b = b;
		this.driver = driver;
		pause = false;
		clicked = false;
		current = a;
	}
	@Override
	public void run() {
		
		driver.gameInfo.setText("Player: " + a.name + " Turn to Move");
		
		while(driver.getGameState()) {
			long time = System.currentTimeMillis();
			
			//Give each player 10 seconds to make a move
			int timeRemaining = 10000;
			int timer = 10;
			while(System.currentTimeMillis()-time < timeRemaining  && !clicked) {
				driver.moved = false;
				timer = 10 - Math.round((System.currentTimeMillis()-time)/1000);
				driver.clock.setText(""+timer+"");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					long paused = System.currentTimeMillis() - time;
					System.out.println("Paused");
					pause = true;
					while(pause) {
						try {
							Thread.sleep(500);
						} catch (InterruptedException e1) {

						}
					}
					System.out.println("Resumed");
					timeRemaining  -= paused;
					time = System.currentTimeMillis();
					
				}
			}
			
			if(!driver.moved){
				driver.AI.Move();
			}
			a.myTurn = !a.myTurn;
			b.myTurn = !b.myTurn;
			System.out.println("Switch");
			
			if(a.myTurn) {
				driver.gameInfo.setText("Player: " + a.name + " Turn to Move");
				current = a;
			}else {
				driver.gameInfo.setText("Player: " + b.name + " Turn to Move");
				current = b;
			}
			
			clicked = false;
			
		}
		
	}

}
