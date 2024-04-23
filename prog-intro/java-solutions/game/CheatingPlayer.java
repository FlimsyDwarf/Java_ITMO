package game;

public class CheatingPlayer implements Player {
	@Override
	public Move move(Position position, final Cell cell) {
		final TicTacToeBoard board = (TicTacToeBoard) position;
		Move first = null;
		for (int r = 0; r < position.getRows(); r++) {
			for (int c = 0; c < position.getColumns(); c++) {
				final Move move = new Move(r, c, cell);
				if (position.isValid(move)) {
					if (first == null) {
						first = move;
					} else {
						board.makeMove(move);
					}
				}
			}
		}
		return first;
	}

}