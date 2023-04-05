package fifteenpuzzle;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Solver {
    static public HashSet<PuzzleBoard>closedList  = new HashSet<PuzzleBoard>(100000);
   static  PriorityQueue<PuzzleBoard> openList = new PriorityQueue<PuzzleBoard>(100000,new BoardComparator());

public static void step(PuzzleBoard input){
    input.makeMove();
    closedList.add(input);
}


    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("File names are not specified");
            System.out.println("usage: java " + MethodHandles.lookup().lookupClass().getName() + " input_file output_file");
            return;
        }

        if(System.console() != null) {
            try {
                File myObj = new File(args[1]);
                step(new PuzzleBoard(args[0]));
                int i=0;
                while(!openList.isEmpty())
                {
                    PuzzleBoard current = openList.poll();
                    if(current.isGoalState()) {
                        System.out.println(current.path);
                        FileWriter myWriter = new FileWriter(myObj.getName());
                        myWriter.write(current.path);
                        myWriter.close();
                        break;
                    }
                    step(current);
                    if(i%500000 ==0 && i/500000>0){
                        System.out.println("500k iterations");
                    }
                    i++;
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            try {
                File myObj = new File(args[1]);
                long start = System.nanoTime();
                step(new PuzzleBoard(args[0]));
                int i=0;
                while(!openList.isEmpty())
                {
                    PuzzleBoard current = openList.poll();
                    if(current.isGoalState()) {
                        long elapsedTime = (System.nanoTime() - start)/1000000;
                        System.out.println(current.path);
                        System.out.println(elapsedTime);
                        FileWriter myWriter = new FileWriter(myObj.getName());
                        myWriter.write(current.path);
                        myWriter.close();
                        break;
                    }
                    step(current);
                    if(i%500000 ==0 && i/500000>0){
                        System.out.println("500k iterations");
                    }
                    i++;
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}