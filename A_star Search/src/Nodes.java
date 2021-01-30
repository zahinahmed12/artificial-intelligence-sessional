import java.util.Arrays;
import java.util.Vector;

public class Nodes {

    private int g_cost;
    private Nodes parent_node;
    private int[][] puzzle_board;
    private int[][] final_puzzle_board;

    private int f_value;


    public Nodes(int a[][],int b[][])
    {
        puzzle_board=new int[4][4];
        final_puzzle_board=new int[4][4];

        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                puzzle_board[i][j]=a[i][j];
                final_puzzle_board[i][j]=b[i][j];
            }
        }
        parent_node=null;
        g_cost=0;
        f_value=0;

    }

    public int getF_value() {
        return f_value;
    }

    public void setF_value(int f_value) {
        this.f_value = f_value;
    }

    public void setG_cost(int g_cost) {
        this.g_cost = g_cost;
    }

    public int getG_cost() {
        return g_cost;
    }

    public int[][] getFinal_puzzle_board() {
        return final_puzzle_board;
    }

    public void setFinal_puzzle_board(int[][] final_puzzle_board) {
        this.final_puzzle_board = final_puzzle_board;
    }

    public Nodes getParent_node() {
        return parent_node;
    }

    public void setParent_node(Nodes parent_node) {
        this.parent_node = parent_node;
    }

    public int[][] getPuzzle_board() {
        return puzzle_board;
    }

    public void setPuzzle_board(int[][] puzzle_board) {
        this.puzzle_board = puzzle_board;
    }

    void print_a_node()
    {
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                //System.out.print(" ");
                if(puzzle_board[i][j]==0) System.out.print(" ");
                else System.out.print(puzzle_board[i][j]);
                System.out.print(" ");
            }
            System.out.println();
            System.out.println();

        }
        //System.out.println(getG_cost());
    }

    boolean final_state_or_not()
    {
        boolean flag=true;
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                if(puzzle_board[i][j]!=final_puzzle_board[i][j])
                {
                    flag=false;
                    return flag;
                    //break;

                }

            }
        }

        return flag;
    }

    int displacement_heuristics()
    {
        int h_val=0;
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                if(puzzle_board[i][j]!=0 && puzzle_board[i][j]!=final_puzzle_board[i][j])
                {
                    h_val++;

                }

            }

        }
        return h_val;

    }

    int Manhattan_heuristics()
    {
        int h_value=0;

        int x1=0,y1=0,x2=0,y2=0;

        for(int i=1;i<=15;i++)
        {
            for(int j=0;j<4;j++)
            {
                for(int k=0;k<4;k++)
                {
                    if(puzzle_board[j][k]==i)
                    {
                        x1=j;
                        y1=k;
                        break;
                    }
                }
            }
            for(int j=0;j<4;j++)
            {
                for(int k=0;k<4;k++)
                {
                    if(final_puzzle_board[j][k]==i)
                    {
                        x2=j;
                        y2=k;
                        break;
                    }
                }
            }
            int dist=Math.abs(x1-x2)+ Math.abs(y1-y2);
            h_value=h_value+dist;
        }

        return h_value;
    }

    Vector<Nodes> create_children_nodes()
    {
        Vector<Nodes> list_of_nodes= new Vector<>();

        int[][] temp=new int[4][4];

        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                temp[i][j]=puzzle_board[i][j];

            }

        }
        int x=0,y=0;

        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                if(puzzle_board[i][j]==0)
                {
                    x=i;
                    y=j;
                    break;

                }
            }
        }
        if(x-1>=0)
        {
            int temp2=puzzle_board[x-1][y];

            temp[x-1][y]=0;

            temp[x][y]=temp2;

            Nodes child= new Nodes(temp,final_puzzle_board);

            list_of_nodes.add(child);

            temp2=puzzle_board[x-1][y];

            temp[x-1][y]=temp2;

            temp[x][y]=0;

        }
        if(x+1<4)
        {
            int temp2=puzzle_board[x+1][y];

            temp[x+1][y]=0;

            temp[x][y]=temp2;

            Nodes child= new Nodes(temp,final_puzzle_board);

            list_of_nodes.add(child);

            temp2=puzzle_board[x+1][y];

            temp[x+1][y]=temp2;

            temp[x][y]=0;

        }
        if(y-1>=0)
        {
            int temp2=puzzle_board[x][y-1];

            temp[x][y-1]=0;

            temp[x][y]=temp2;

            Nodes child= new Nodes(temp,final_puzzle_board);

            list_of_nodes.add(child);

            temp2=puzzle_board[x][y-1];

            temp[x][y-1]=temp2;

            temp[x][y]=0;
        }
        if(y+1<4)
        {
            int temp2=puzzle_board[x][y+1];

            temp[x][y+1]=0;

            temp[x][y]=temp2;

            Nodes child= new Nodes(temp,final_puzzle_board);

            list_of_nodes.add(child);

            temp2=puzzle_board[x][y+1];

            temp[x][y+1]=temp2;

            temp[x][y]=0;
        }

        return  list_of_nodes;
    }

    @Override
    public boolean equals(Object o) {
       /* if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Nodes nodes = (Nodes) o;

        if (!Arrays.deepEquals(puzzle_board, nodes.puzzle_board)) return false;
        return Arrays.deepEquals(final_puzzle_board, nodes.final_puzzle_board);*/

       int [][] b1=this.puzzle_board;
       Nodes n= (Nodes) o;
       int [][] b2=((Nodes) o).puzzle_board;

       for(int i=0;i<4;i++)
       {
           for (int j=0;j<4;j++)
           {
               if(b1[i][j]!=b2[i][j])
                   return false;
           }
       }
       return true;
    }


    @Override
    public int hashCode() {

        //int result = g_cost;
        int result = 0;
        int [][] b1=this.puzzle_board;
        //int [][] b2=this.final_puzzle_board;
        //result = 31 * result + (parent_node != null ? parent_node.hashCode() : 0);
        ///result = 31 * result + Arrays.deepHashCode(puzzle_board);
        //result = 31 * result + Arrays.deepHashCode(final_puzzle_board);
        for(int i=0;i<4;i++)
        {
            for (int j=0;j<4;j++)
            {
                result=result*199+31*b1[i][j]*(i+1)*(j+1);

            }
        }
        return result;

    }
}

