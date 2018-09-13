/**
 * @author uka
 *
 */
public class Mediator {
	public boolean canPlace(int x, int y, int[][] map, int check) {
		if (map[x][y] != 0)
			return false;

		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if (i >= 0 && i < map.length && j >= 0 && j < map[i].length && map[i][j] != 0 && map[i][j] != check) {
					int difx = i - x;
					int dify = j - y;
					if (check(x + difx, y + dify, difx, dify, map, map[i][j]))
						return true;
				}
			}
		}

		return false;
	}

	private boolean check(int i, int j, int x, int y, int[][] map, int check) {
		if (i < 0 || i >= map.length || j < 0 || j >= map[i].length)
			return false;
		else if (map[i][j] == 0)
			return false;
		else if (map[i][j] != check)
			return true;
		else
			return check(i + x, j + y, x, y, map, check);
	}

	public void place(int x, int y, int[][] map, int check) {
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if (i >= 0 && i < map.length && j >= 0 && j < map[i].length && map[i][j] != 0 && map[i][j] != check) {
					int difx = i - x;
					int dify = j - y;
					if (check(x + difx, y + dify, difx, dify, map, map[i][j])) {
						placeHelper(x + difx, y + dify, difx, dify, map, check);
					}

				}
			}
		}
	}

	private void placeHelper(int i, int j, int x, int y, int[][] map, int check) {
		if (i < 0 || i >= map.length || j < 0 || j >= map[i].length)
			return;
		else if (map[i][j] == 0 || map[i][j] == check)
			 return;
		else if (map[i][j] != check) {
			map[i][j] = check;
			placeHelper(i + x, j + y, x, y, map, check);
		}
	}
}
