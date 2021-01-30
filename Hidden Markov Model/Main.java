import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        int size,timer;
        System.out.print("ENTER BOARD SIZE: ");
        Scanner sc=new Scanner(System.in);
        size=sc.nextInt();
        System.out.println();
        Board board=new Board(size);
        board.initializeBoard();
        board.printBoard();
        //board.printGhostPosition();
        //board.revealGhost();

        while (true)
        {
            int op;
            System.out.print("\n1.ADVANCE TIME?\n2.SENSE A CELL? \n3. CATCH THE GHOST? ");

            Scanner sc1=new Scanner(System.in);
            op=sc1.nextInt();
            System.out.println();

            if(op==1)
            {
                System.out.print("\nADVANCE TIME? 1. YES 2. NO ");
                Scanner sc2=new Scanner(System.in);
                timer=sc2.nextInt();
                System.out.println();

                if(timer==1)
                {
                    board.advanceTime();
                    //board.printGhostPosition();
                    //board.revealGhost();
                }
                board.printBoard();
            }
            else if(op==2)
            {
                int a,b;
                System.out.print("\nENTER POSITION: ");
                System.out.print("\nENTER 'x': ");

                Scanner sc2=new Scanner(System.in);
                a=sc2.nextInt();
                //System.out.println();

                System.out.print("\nENTER 'y': ");
                Scanner sc3=new Scanner(System.in);
                b=sc3.nextInt();
                System.out.println();

                board.observeBoard(a,b);
                board.printBoard();
            }
            else if(op==3)
            {
                int a,b;
                System.out.print("\nENTER POSITION: ");
                System.out.print("\nENTER 'x': ");

                Scanner sc2=new Scanner(System.in);
                a=sc2.nextInt();
                //System.out.println();

                System.out.print("\nENTER 'y': ");
                Scanner sc3=new Scanner(System.in);
                b=sc3.nextInt();
                //System.out.println();
                boolean flag=board.catchGhost(a,b);
                if(flag){
                    System.out.println("YOU CAUGHT THE GHOST!\n");
                    break;
                }
                else
                    System.out.println("OOPS! WRONG ATTEMPT\n");

                board.printBoard();

            }
            else
                System.out.println("WRONG INPUT");

        }
    }
}
