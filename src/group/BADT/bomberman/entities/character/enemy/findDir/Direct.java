package group.BADT.bomberman.entities.character.enemy.findDir;

import java.util.Random;

public abstract class Direct {
	
	protected Random random = new Random();

	/**
	 * Thuật toán tìm đường đi
	 * @return hướng đi xuống/phải/trái/lên tương ứng với các giá trị 0/1/2/3
	 */
	public abstract int calculateDirection();
}
