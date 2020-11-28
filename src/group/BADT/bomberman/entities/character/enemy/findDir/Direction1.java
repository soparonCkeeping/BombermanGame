package group.BADT.bomberman.entities.character.enemy.findDir;

public class Direction1 extends Direct {

	@Override
	public int calculateDirection() {
		// TODO: cài đặt thuật toán tìm đường đi
		return random.nextInt(4);
	}

}
