package CSP;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        String x="";
        String y="";
        String str_file="";

        PrintWriter myFile=new PrintWriter(new FileWriter("1605057_output.txt",true));


        System.out.println("         Best Known Solution            My scheme 1 (Largest Degree+ Kempe Chain)              My scheme 2 (DSatur + Kempe Chain)");
        str_file+="         Best Known Solution            My scheme 1 (Largest Degree+ Kempe Chain)              My scheme 2 (DSatur + Kempe Chain)";
        System.out.println();
        str_file+="\n";
        System.out.println("                                                 Largest Degree    Kempe Chain                       DSatur            Kempe Chain ");
        str_file+="                                                 Largest Degree    Kempe Chain                       DSatur            Kempe Chain ";
        str_file+="\n";
        System.out.println("          Timeslots    Penalty      Timeslots   Previous Penalty  Reduced Penalty      Timeslots   Previous Penalty  Reduced Penalty   ");
        str_file+="          Timeslots    Penalty      Timeslots   Previous Penalty  Reduced Penalty      Timeslots   Previous Penalty  Reduced Penalty   ";
        str_file+="\n";

        /*try{
            //FileWriter fw=new FileWriter("1605057_output.txt");
         myFile.write(str_file);
            str_file="";
        }catch (IOException e)
        {
            e.printStackTrace();
        }*/

        int tests=5;
        while(tests>0)
        {
            if(tests==5)
            {
                x="car-s-91.crs";
                y="car-s-91.stu";
            }
            else if(tests==4)
            {
                x="car-f-92.crs";
                y="car-f-92.stu";
            }
            else if(tests==3)
            {
                x="kfu-s-93.crs";
                y="kfu-s-93.stu";
            }
            else if(tests==2)
            {
                x="tre-s-92.crs";
                y="tre-s-92.stu";
            }
            else
            {
                x="yor-f-83.crs";
                y="yor-f-83.stu";
            }
            tests--;

            int vertices=0;
            int stuCount =0;

            try
            {
                File f=new File(x);
                FileReader fr=new FileReader(f);
                BufferedReader br=new BufferedReader(fr);

                String line;
                while((line=br.readLine())!=null)
                {
                    StringTokenizer s= new StringTokenizer(line," ");
                    vertices=Integer.parseInt(s.nextToken());
                }
                fr.close();

            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            //System.out.println(vertices);
            Courses c=new Courses(vertices);

            try
            {
                File f2=new File(y);
                FileReader fr2=new FileReader(f2);
                BufferedReader br2=new BufferedReader(fr2);

                String line2;
                while((line2=br2.readLine())!=null)
                {
                    stuCount++;
                    StringTokenizer s2= new StringTokenizer(line2," ");
                    //vertices=Integer.parseInt(s.nextToken());
                    ArrayList<Integer> alist=new ArrayList<>(s2.countTokens());

                    int tokens=s2.countTokens();

                    while (s2.hasMoreTokens())
                    {
                        // System.out.print(s2.nextToken());
                        alist.add(Integer.parseInt(s2.nextToken()));
                    }
                    c.constructGraph(alist,tokens);
                    //System.out.println();
                }
                fr2.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            //System.out.println(stuCount);
            //c.print();
            c.time_scheduling_using_largest_degree();
            c.print_after_largest_degree();

            if(tests==4)
            {
                c.print_sol_file("car-s-91");
            }
            else if(tests==3)
            {
                c.print_sol_file("car-f-92");
            }
            else if(tests==2)
            {
                c.print_sol_file("kfu-s-93");
            }
            else if(tests==1)
            {
                c.print_sol_file("tre-s-92");
            }
            else
            {
                c.print_sol_file("yor-f-83");
            }

            c.DSat_algorithm();
            c.print_after_dsat();

            c.setStudents(stuCount);
            try
            {
                File f3=new File(y);
                FileReader fr3=new FileReader(f3);
                BufferedReader br3=new BufferedReader(fr3);

                String line3;
                int countLine=-1;
                while((line3=br3.readLine())!=null)
                {
                    countLine++;
                    StringTokenizer s3= new StringTokenizer(line3," ");
                    //vertices=Integer.parseInt(s.nextToken());
                    ArrayList<Integer> alist=new ArrayList<>(s3.countTokens());

                    int tokens=s3.countTokens();

                    while (s3.hasMoreTokens())
                    {
                        // System.out.print(s2.nextToken());
                        alist.add(Integer.parseInt(s3.nextToken()));
                    }
                    c.set_courses(alist,tokens,countLine);
                    //System.out.println();
                }
                fr3.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            //c.print_courses();
            c.penalty_after_largest_degree();
            c.penalty_after_dsat();

            c.Kempe_Chain_after_largest_degree();
            c.Kempe_Chain_after_DSatur();
            //c.Kempe_Chain_after_largest_degree();
            if(tests==4)
            {
                System.out.println("CAR91         35       4.42           "+c.getMax_timeslot_largest_degree()+"         "+c.getPenalty_largest_degree()+"         "+c.getPrev_penalty_largest_degree()+"              "+c.getMax_timeslot_dsat()+"          "+c.getPenalty_dsatur()+"         "+c.getPrev_penalty_dsatur());
                str_file+="CAR91         35       4.42           "+c.getMax_timeslot_largest_degree()+"         "+c.getPenalty_largest_degree()+"         "+c.getPrev_penalty_largest_degree()+"              "+c.getMax_timeslot_dsat()+"          "+c.getPenalty_dsatur()+"         "+c.getPrev_penalty_dsatur();
                str_file+="\n";
            }

            else if(tests==3)
            {
                System.out.println("CAR92         32       3.74           "+c.getMax_timeslot_largest_degree()+"         "+c.getPenalty_largest_degree()+"         "+c.getPrev_penalty_largest_degree()+"              "+c.getMax_timeslot_dsat()+"          "+c.getPenalty_dsatur()+"         "+c.getPrev_penalty_dsatur());
                str_file+="CAR92         32       3.74           "+c.getMax_timeslot_largest_degree()+"         "+c.getPenalty_largest_degree()+"         "+c.getPrev_penalty_largest_degree()+"              "+c.getMax_timeslot_dsat()+"          "+c.getPenalty_dsatur()+"         "+c.getPrev_penalty_dsatur();
                str_file+="\n";
            }
            else if(tests==2)
            {
                System.out.println("KFU93         20      12.96           "+c.getMax_timeslot_largest_degree()+"         "+c.getPenalty_largest_degree()+"         "+c.getPrev_penalty_largest_degree()+"              "+c.getMax_timeslot_dsat()+"          "+c.getPenalty_dsatur()+"         "+c.getPrev_penalty_dsatur());
                str_file+="KFU93         20      12.96           "+c.getMax_timeslot_largest_degree()+"         "+c.getPenalty_largest_degree()+"         "+c.getPrev_penalty_largest_degree()+"              "+c.getMax_timeslot_dsat()+"          "+c.getPenalty_dsatur()+"         "+c.getPrev_penalty_dsatur();
                str_file+="\n";
            }
            else if(tests==1)
            {
                System.out.println("TRE92         23       7.75           "+c.getMax_timeslot_largest_degree()+"         "+c.getPenalty_largest_degree()+"         "+c.getPrev_penalty_largest_degree()+"              "+c.getMax_timeslot_dsat()+"          "+c.getPenalty_dsatur()+"         "+c.getPrev_penalty_dsatur());
                str_file+="TRE92         23       7.75           "+c.getMax_timeslot_largest_degree()+"         "+c.getPenalty_largest_degree()+"         "+c.getPrev_penalty_largest_degree()+"              "+c.getMax_timeslot_dsat()+"          "+c.getPenalty_dsatur()+"         "+c.getPrev_penalty_dsatur();
                str_file+="\n";
            }
            else
            {
                System.out.println("YOR83         21      34.84           "+c.getMax_timeslot_largest_degree()+"         "+c.getPenalty_largest_degree()+"         "+c.getPrev_penalty_largest_degree()+"              "+c.getMax_timeslot_dsat()+"          "+c.getPenalty_dsatur()+"         "+c.getPrev_penalty_dsatur());
                str_file+="YOR83         21      34.84           "+c.getMax_timeslot_largest_degree()+"         "+c.getPenalty_largest_degree()+"         "+c.getPrev_penalty_largest_degree()+"              "+c.getMax_timeslot_dsat()+"          "+c.getPenalty_dsatur()+"         "+c.getPrev_penalty_dsatur();
                str_file+="\n";
            }

        }
        //System.out.println(str_file);
        myFile.write(str_file);
        myFile.flush();
        myFile.close();

    }
}
