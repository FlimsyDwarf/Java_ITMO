package game;

import java.util.Random;
public class RandomPlayer implements Player {
    private final Random random;

    public RandomPlayer(final Random random) {
        this.random = random;
    }

    public RandomPlayer() {
        this(new Random());
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        int r = random.nextInt(position.getRows());
        int c = random.nextInt(position.getColumns());
        final Move move = new Move(r, c, cell);
        if (position.isValid(move)) {
            return move;
        } else {
            throw new RuntimeException();
        }
    }
}
