
import kareltherobot.*;

public class Quiz_1_Practice implements Directions {

	Robot robot = new Robot(1,1,East, infinity);

	public static void main(String[] args) {
		Quiz_1_Practice q = new Quiz_1_Practice();
		
		World.setDelay(10);
		World.setVisible(true);
		q.drawTriangle(5, 5);	
	}

	private void drawTriangle(int triangleHeight, int triangleBaseLength) {
		
		for (int length = 0; length < triangleHeight; length++) {
			faceNorth();
			for (int heightUp = 0; heightUp < triangleHeight; heightUp++) {
				robot.move();
				robot.putBeeper();
			}		
			slideRight();
			turnAround();
			for (int heightDown = 0; heightDown < triangleHeight; heightDown++) {
				robot.move();
			}	
			triangleHeight--;
		}

	}

	private void slideLeft() {
		robot.turnLeft();
		robot.move();
		turnRight();
	}
	
	private void slideRight() {
		turnRight();
		robot.move();
		robot.turnLeft();
	}

	private void faceNorth() {
		System.out.println("Turning north");
		if (robot.facingEast()) {
			robot.turnLeft();
		} else if (robot.facingSouth()) {
			turnAround();
		} else if (robot.facingWest()) {
			turnRight();
		}
	}

	private void turnAround() {
		robot.turnLeft();
		robot.turnLeft();	
	}

	private void turnRight() {
		robot.turnLeft();
		robot.turnLeft();
		robot.turnLeft();
	}
}





