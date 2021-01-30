
import java.util.Random;

public class Board {

    private int size;
    private double[][] board;

    private double oneStepProb=0.20;
    private double diagonalStepProb=0.045;
    private double inPlaceProb=0.019;

    private int ghostX;
    private int ghostY;

    private int nearDist=2;
    private int farDist=5;

    private String gr="GREEN";
    private String yl="YELLOW";
    private String rd="RED";

    private double[][] color=new double[][]{{0.8,0.2,0.0},
                                            {0.2,0.6,0.2},
                                            {0.0,0.2,0.8}};

    public Board(int size)
    {
        this.size=size;
        board=new double[size][size];
        setGhostPosition();
    }

    public void initializeBoard()
    {
        double val=(1.0/(size*size))*100;
        val=Math.floor(val*100)/100;

        for (int i=0;i<size;i++)
        {
            for (int j=0;j<size;j++)
            {
                board[i][j]=val;
            }
        }
    }
    public void printBoard()
    {
        System.out.println("        0            1           2            3            4             5            6           7            8");
        System.out.println("       -----------------------------------------------------------------------------------------------------------");
        for (int i=0;i<size;i++)
        {
            System.out.print(" "+i+"|");
            for (int j=0;j<size;j++)
            {
                System.out.print("   "+board[i][j]+"      ");
            }
            System.out.println();
            //System.out.println();
        }
    }
    public boolean isPositionValid(int x, int y)
    {
        return (x>=0 && x<size && y>=0 && y<size);
    }

    public void setGhostPosition()
    {
        Random rand=new Random();
        ghostX=rand.nextInt(size);
        ghostY=rand.nextInt(size);
    }
    public void printGhostPosition()
    {
        System.out.println("\nGhost position=  x: "+ghostX+",   y: "+ghostY);
    }

    public void moveGhost()
    {
        int x=ghostX,y=ghostY,idx;
        double[] Array = new double[]{ 0.2,0.2,0.045,0.2,0.019,0.045,0.2,0.045,0.2,0.2,0.2,0.2,0.2,0.2,0.2 };
        Random rand=new Random();
        idx=rand.nextInt(15);
        if(Array[idx]==0.2)
        {
            Random r=new Random();
            int i=r.nextInt(4);
            if(i==0) {
                x= ghostX==0 ? ghostX+1 : ghostX-1;
                y=ghostY;
            }
            else if(i==1) {
                x=ghostX;
                y= ghostY==0 ? ghostY+1 : ghostY-1;
            }
            else if(i==2) {
                x=ghostX;
                y= ghostY==size-1 ? ghostY-1 : ghostY+1;
            }
            else if(i==3) {
                x= ghostX==size-1 ? ghostX-1 : ghostX+1;
                y=ghostY;
            }
        }
        else if(Array[idx]==0.045)
        {
            Random r=new Random();
            int i=r.nextInt(4);
            if(i==0) {
                x=ghostX-1;
                y=ghostY-1;
            }
            else if(i==1) {
                x=ghostX-1;
                y=ghostY+1;
            }
            else if(i==2) {
                x=ghostX+1;
                y=ghostY-1;
            }
            else if(i==3) {
                x=ghostX+1;
                y=ghostY+1;
            }
        }
        if(isPositionValid(x,y))
        {
            ghostX=x;
            ghostY=y;
        }
    }

    public void advanceTime()
    {
        moveGhost();
        double[][] arr=new double[size][size];

        for(int i=0;i<size;i++) {
            for(int j=0;j<size;j++) {
                double v, sum=0.0;
                if(isPositionValid(i+1,j)) {
                    v=board[i+1][j]/100;
                    sum+=v*oneStepProb;
                }
                if(isPositionValid(i-1,j)) {
                    v=board[i-1][j]/100;
                    sum+=v*oneStepProb;
                }
                if(isPositionValid(i,j+1)) {
                    v=board[i][j+1]/100;
                    sum+=v*oneStepProb;
                }
                if(isPositionValid(i,j-1)) {
                    v=board[i][j-1]/100;
                    sum+=v*oneStepProb;
                }
                if(isPositionValid(i,j)) {
                    v=board[i][j]/100;
                    sum+=v*inPlaceProb;
                }
                if(isPositionValid(i+1,j+1)) {
                    v=board[i+1][j+1]/100;
                    sum+=v*diagonalStepProb;
                }
                if(isPositionValid(i-1,j+1)) {
                    v=board[i-1][j+1]/100;
                    sum+=v*diagonalStepProb;
                }
                if(isPositionValid(i+1,j-1)) {
                    v=board[i+1][j-1]/100;
                    sum+=v*diagonalStepProb;
                }
                if(isPositionValid(i-1,j-1)) {
                    v=board[i-1][j-1]/100;
                    sum+=v*diagonalStepProb;
                }
                sum=sum*100;
                sum=Math.floor(sum*100)/100;
                //board[i][j]=sum;
                arr[i][j]=sum;
            }
        }
        for(int i=0;i<size;i++) {
            for(int j=0;j<size;j++) {
                board[i][j]=arr[i][j];
            }
        }
    }

    public int ManhattanDistance(int x1, int y1, int x2, int y2)
    {
        return Math.abs(x1-x2)+Math.abs(y1-y2);
    }

    public String senseCellColor(int a, int b)
    {
        int dist=ManhattanDistance(ghostX,ghostY,a,b);
        String[] arr;
        Random rand=new Random();
        String str;
        if(dist>farDist)
        {
            arr=new String[]{gr,gr,yl,gr,gr};
            int idx=rand.nextInt(5);
            str=arr[idx];
            System.out.println("\nSensor shows "+arr[idx]+" color in cell ("+a+","+b+")");
        }
        else if(dist<=nearDist)
        {
            arr=new String[]{rd,rd,yl,rd,rd};
            int idx=rand.nextInt(5);
            str=arr[idx];
            System.out.println("\nSensor shows "+arr[idx]+" color in cell ("+a+","+b+")");
        }
        else
        {
            arr=new String[]{yl,gr,yl,rd,yl};
            int idx=rand.nextInt(5);
            str=arr[idx];
            System.out.println("\nSensor shows "+arr[idx]+" color in cell ("+a+","+b+")");
        }
        return str;
    }

    public void observeBoard(int a, int b)
    {
        String str=senseCellColor(a,b);
        int f;
        if(str.equals(gr))
            f=0;
        else if(str.equals(rd))
            f=2;
        else f=1;
        double[][] arr=new double[size][size];

        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                int d;
                double prob,v;
                int dist=ManhattanDistance(a,b,i,j);
                if(dist>5)
                    d=0;
                else if(dist<=2)
                    d=2;
                else d=1;

                prob=color[d][f];
                v=board[i][j]/100;
                arr[i][j]=prob*v;
            }
        }
        double sum=0.0;
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                sum+=arr[i][j];
            }
        }
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                arr[i][j]/=sum;
                arr[i][j]*=100;
                arr[i][j]=Math.floor(arr[i][j]*100)/100;
            }
        }
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                board[i][j]=arr[i][j];
            }
        }

    }

    public boolean catchGhost(int a, int b)
    {
        if(a!=ghostX || b!=ghostY)
        {
            board[a][b]=0.0;

            double sum=0.0;
            for(int i=0;i<size;i++)
            {
                for(int j=0;j<size;j++)
                {
                    sum+=board[i][j];
                }
            }
            for(int i=0;i<size;i++)
            {
                for(int j=0;j<size;j++)
                {
                    board[i][j]/=sum;
                    board[i][j]*=100;
                    board[i][j]=Math.floor(board[i][j]*100)/100;
                }
            }

            return false;
        }
        return true;
    }

    public String getCellColor(int a, int b)
    {
        int dist=ManhattanDistance(ghostX,ghostY,a,b);
        String[] arr;
        Random rand=new Random();
        String str;
        if(dist>farDist)
        {
            arr=new String[]{gr,gr,yl,gr,gr};
            int idx=rand.nextInt(5);
            str=arr[idx];
            //System.out.println("\nSensor shows "+arr[idx]+" color in cell ("+a+","+b+")");
        }
        else if(dist<=nearDist)
        {
            arr=new String[]{rd,rd,yl,rd,rd};
            int idx=rand.nextInt(5);
            str=arr[idx];
            //System.out.println("\nSensor shows "+arr[idx]+" color in cell ("+a+","+b+")");
        }
        else
        {
            arr=new String[]{yl,gr,yl,rd,yl};
            int idx=rand.nextInt(5);
            str=arr[idx];
            //System.out.println("\nSensor shows "+arr[idx]+" color in cell ("+a+","+b+")");
        }
        if(str.equals("GREEN"))
            str="G";
        else if(str.equals("YELLOW"))
            str="Y";
        else str="R";
        return str;
    }

    public void revealGhost()
    {
        String[][] colours=new String [size][size];

        for (int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                colours[i][j]=getCellColor(i,j);
            }
        }

        System.out.println("        0            1           2            3            4             5            6           7            8");
        System.out.println("       -----------------------------------------------------------------------------------------------------------");
        for (int i=0;i<size;i++)
        {
            System.out.print(" "+i+"|");
            for (int j=0;j<size;j++)
            {
                System.out.print("     "+colours[i][j]+"       ");
            }
            System.out.println();
            //System.out.println();
        }

    }


}
