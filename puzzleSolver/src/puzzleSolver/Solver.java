package puzzleSolver;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

public class Solver {
    public static void main(String[] args) {
//		System.out.println("number of arguments: " + args.length);
//		for (int i = 0; i < args.length; i++) {
//			System.out.println(args[i]);
//		}
        try {
            PuzzleBoard tmp = new PuzzleBoard("board3.txt");
            System.out.println("hello world!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (BadBoardException e) {
            throw new RuntimeException(e);
        }
        if (args.length < 2) {
            System.out.println("File names are not specified");
            System.out.println("usage: java " + MethodHandles.lookup().lookupClass().getName() + " input_file output_file");
            return;
        }
    }
}
