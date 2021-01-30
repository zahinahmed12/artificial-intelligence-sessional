import java.io.IOException;
import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        int size=0;
        int [][] mat=new int[1][1];
        int lineCount=0;

        try{
           //File f=new File("test1.txt");

            File f=new File("d-10-08.txt.txt");
            FileReader fr=new FileReader(f);
            BufferedReader br=new BufferedReader(fr);

            String line;
            while((line=br.readLine())!=null)
            {
                lineCount++;
                if(lineCount==1)
                {
                    String l=line;
                    int len=l.length();
                    String s="";
                    for(int i=2;i<len-1;i++){
                        if(l.charAt(i)=='0' || l.charAt(i)=='1' ||l.charAt(i)=='2'||l.charAt(i)=='3'||l.charAt(i)=='4'
                                ||l.charAt(i)=='5'||l.charAt(i)=='6'||l.charAt(i)=='7'||l.charAt(i)=='8'||l.charAt(i)=='9')
                        {
                            //System.out.println(l.charAt(i));
                            s+=l.substring(i,i+1);
                            //System.out.println(s);
                        }
                    }
                    //System.out.println(s);
                    size=Integer.parseInt(s);
                    //System.out.println(size);
                    mat=new int[size][size];
                }
                int a=lineCount-4;
                if(lineCount>=4)
                {
                    StringTokenizer stringTokenizer=new StringTokenizer(line,", ");

                    int b=0;
                    while (stringTokenizer.hasMoreTokens())
                    {
                        String token=stringTokenizer.nextToken();
                        //System.out.println(token);
//                        if(b==size-1)
//                        {
//                            String t= stringTokenizer.nextToken();
//                            String temp="";
//                            int l=t.length();
//                            for(int i=0;i<l;i++)
//                            {
//                                //if(t.charAt(i)==' ')
//                                if(t.charAt(i)!='0' || t.charAt(i)!='1' ||t.charAt(i)!='2'||t.charAt(i)!='3'||t.charAt(i)!='4'
//                                        ||t.charAt(i)!='5'||t.charAt(i)!='6'||t.charAt(i)!='7'||t.charAt(i)!='8'||t.charAt(i)!='9')
//                                    break;
//
//                                temp+=t.substring(i,i+1);
//                            }
//                            System.out.println(t);
//                            mat[a][b]=Integer.parseInt(temp);
//                            a++;
//                            b=0;
//                        }
                        if(!token.equals("|") && !token.equals("|];"))
                        {
                            mat[a][b]=Integer.parseInt( token);
                            //System.out.println(mat[a][b]);
                            b++;
                        }

                    }
                }

            }
            fr.close();

        }catch (IOException e)
        {
            e.printStackTrace();
        }
        Board b=new Board(size);
        b.initializeBoard(mat);
        //b.printBoard();
        //b.printUnassignedVarList();
        //Variable v=new Variable(0,0,0);
        //b.getDomain(v);
        //b.getNeighbours(v);
        //b.forwardChecking();
        //b.backtracking();
        //b.MAC();
        //System.out.println(b.MAC());
        //System.out.println(b.forwardChecking());
        System.out.println(b.backtracking());
        b.printBoard();
        b.printStat();
    }
}
