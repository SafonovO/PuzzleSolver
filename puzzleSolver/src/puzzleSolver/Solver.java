package puzzleSolver;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

public class Solver {
    public static void main(String[] args) {

        String currentDir = System.getProperty("user.dir");
        System.out.println(currentDir);
        String fileName = args[0];
        String filePath = currentDir.substring(0, currentDir.lastIndexOf(File.separator, currentDir.lastIndexOf(File.separator) - 1)) + File.separator + "data" + File.separator + fileName;
        try {
           // PuzzleBoard tmp = new PuzzleBoard(args[0]);
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
