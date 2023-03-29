package puzzleSolver;


import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Solver {
    HashSet<PuzzleBoard> closedList;
    PriorityQueue<PuzzleBoard> openList;

    //TODO Implement A METHOD THAT ENQUEUES POSSIBLE NEW STATES INTO openList and puts current state into closedList
    public static void main(String[] args) {

        if (System.console() != null) {
            String currentDir = System.getProperty("user.dir");
            String filePath = currentDir.substring(0, currentDir.lastIndexOf(File.separator, currentDir.lastIndexOf(File.separator))) + File.separator + args[0];
            try {
                PuzzleBoard tmp = new PuzzleBoard(filePath);
                System.out.println("hello world!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (BadBoardException e) {
                throw new RuntimeException(e);
            }
            if (args.length < 2) {
                System.out.println("File names are not specified");
                System.out.println("usage: java " + MethodHandles.lookup().lookupClass().getName() + " input_file output_file");
            }
        }
        else{
            try {
                PuzzleBoard tmp = new PuzzleBoard(System.getProperty("user.dir")+File.separator +"src"+File.separator+ args[0]);
                tmp.makeMove(0);
                System.out.println("AMOGUS");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (BadBoardException e) {
                throw new RuntimeException(e);
            }
            if (args.length < 2) {
                System.out.println("File names are not specified");
                System.out.println("usage: java " + MethodHandles.lookup().lookupClass().getName() + " input_file output_file");
            }
        }
    }
}