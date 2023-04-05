package fifteenpuzzle;

import java.util.Comparator;

public class BoardComparator implements Comparator<PuzzleBoard> {


    @Override
    public int compare(PuzzleBoard o1, PuzzleBoard o2) {
        return (int)(o1.cost - o2.cost);
    }
}
