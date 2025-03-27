public class Map {
	private Tile[][] m;
	public Map(int r, int c) {
		m = new Tile[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				m[i][j] = new Tile(i, j, '.');
			}
		}
	}
	public void set(int r, int c, Tile t) {
		m[r][c] = t;
	}
	public Tile get(int r, int c) {
		if (r < 0 || c < 0 || r >= m.length || c >= m[0].length) {
			return new Tile(r, c, '@');
		} return m[r][c];
	}
	public void print() {
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[i].length; j++) {
				System.out.print(m[i][j].getType());
			}System.out.println();
		}
	}
}