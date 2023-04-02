package puzzleSolver;


import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Solver {
    static public HashSet<PuzzleBoard>closedList  = new HashSet<PuzzleBoard>(10000);
   static  PriorityQueue<PuzzleBoard> openList = new PriorityQueue<PuzzleBoard>(10000,new BoardComparator());

public static void step(PuzzleBoard input){
    openList.addAll(input.makeMove());
    closedList.add(input);
}

    public static void main(String[] args) {

        if(System.console() != null) {
            String currentDir = System.getProperty("user.dir");
            String filePath = currentDir.substring(0, currentDir.lastIndexOf(File.separator, currentDir.lastIndexOf(File.separator))) + File.separator + args[0];
            try {
                PuzzleBoard tmp = new PuzzleBoard(filePath);
                System.out.println(args[0]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (args.length < 2) {
                System.out.println("File names are not specified");
                System.out.println("usage: java " + MethodHandles.lookup().lookupClass().getName() + " input_file output_file");
            }
        }
        else{
            try {
                step(new PuzzleBoard(System.getProperty("user.dir")+File.separator +"src"+File.separator+ "puzzleSolver"+File.separator+"testcases"+ File.separator+args[0]));
                int i=0;
                while(!openList.isEmpty())
                {
                    PuzzleBoard current = openList.poll();
                    if(current.isGoalState()) {
                        System.out.println(current.path);
                        break;
                    }
                    step(current);
                    if(i%500000 ==0){
                        System.out.println("somethign");
                    }
                    i++;
                }
                System.out.println("AWD");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (args.length < 2) {
                System.out.println("File names are not specified");
                System.out.println("usage: java " + MethodHandles.lookup().lookupClass().getName() + " input_file output_file");
            }
        }
    }
}