import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Vector;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {

    static long Time_for_displacement=0;
    static long Time_for_Manhattan=0;

    public static void main(String[] args) throws Exception {
        Nodes a;
        Nodes b;
        int[][] first=new int[4][4];
        int[][] second=new int[4][4];
        //Scanner sc=new Scanner(System.in);

        Vector<Integer> pr=new Vector<>();
        Vector<Integer> gl=new Vector<>();

        Vector<Integer> list=new Vector<>();

        Path filePath = Paths.get("C:\\Users\\lenovo\\IdeaProjects\\15_puzzle_problem4\\src\\input.txt");
        Scanner scanner = new Scanner(filePath);
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                list.add(scanner.nextInt());
            } else {
                scanner.next();
            }
        }
        int n;
        n=list.get(0);
        //for(int i=0;i<16;i++) pr.add(list.get(i));
        for(int i=1;i<=16;i++) gl.add(list.get(i));

        int index1=-1;
        for(int i=0;i<4;i++)
        {
            for(int k=0;k<4;k++)
            {
                //int el=sc.nextInt();
                //first[i][j]=el;
                index1++;
                second[i][k]=gl.get(index1);
            }
        }

        int count=0;

        //System.out.println(list.size());
        for(int i=17;i<=list.size();i++)
        {
            if(count<16)
            {
                if(i<list.size()) pr.add(list.get(i));
                count++;
            }
            else
            {
               // System.out.println(i);
                i=i-1;
                System.out.println("PUZZLE "+(i/16-1));
                first=new int[4][4];
                int index=-1;
                for(int j=0;j<4;j++)
                {
                    for(int k=0;k<4;k++)
                    {
                        //int el=sc.nextInt();
                        //first[i][j]=el;
                        index++;
                        first[j][k]=pr.get(index);
                    }
                }
                /*for(int j=0;j<4;j++)
                {
                    for(int k=0;k<4;k++)
                    {
                        //int el=sc.nextInt();
                        //first[i][j]=el;
                        //index++;
                        System.out.print(first[j][k]+ " ");
                        System.out.println();
                    }
                }*/
                a=new Nodes(first,second);
                //a.print_a_node();
                Check_Solvable check=new Check_Solvable();
                check.setList1(pr);
                check.setList2(gl);
                check.setFirst(first);
                check.setSecond(second);

                boolean solvable=check.solvable_or_not();

                if(!solvable)
                {
                    System.out.println("\nPUZZLE_NOT_SOLVABLE");
                    pr.clear();
                }

                else {
                    System.out.println("\nTHIS_PUZZLE_IS_SOLVABLE\n");

                    System.out.println("FOR MISPLACED TILES HEURISTICS:\n");
                    long startTime1= System.currentTimeMillis();
                    //Main ext=new Main();
                    A_Star_Search_Algorithm as1=new A_Star_Search_Algorithm(a);
                    b=as1.search_using_displacement_heuristics();
                    //b=as.search_using_Manhattan_heuristics();
                    as1.print_everything(b);
                    as1.print_path(b);
                    long finishTime1=System.currentTimeMillis();
                    Time_for_displacement=finishTime1-startTime1;
                    System.out.println("Total time for misplaced tiles: "+Time_for_displacement/1000.0+" s\n");

                    System.out.println("\nFOR MANHATTAN HEURISTICS:\n");
                    long startTime= System.currentTimeMillis();
                    A_Star_Search_Algorithm as=new A_Star_Search_Algorithm(a);
                    b=as.search_using_Manhattan_heuristics();
                    //b=as.search_using_Manhattan_heuristics();
                    as.print_everything(b);
                    as.print_path(b);
                    long finishTime=System.currentTimeMillis();
                    Time_for_Manhattan=finishTime-startTime;
                    System.out.println("Total time for manhattan: "+Time_for_Manhattan/1000.0+" s\n");

                    System.out.println("Time difference: "+Math.abs(Time_for_displacement-Time_for_Manhattan)+" Millis");

                    pr.clear();
                    //count=0;

                }
                pr.clear();
                count=0;


            }
        }

    }



}

