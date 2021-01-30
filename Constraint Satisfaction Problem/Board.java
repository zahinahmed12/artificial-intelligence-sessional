//import javax.swing.text.html.HTMLDocument;
import java.util.HashSet;
//import java.util.Iterator;
import java.util.LinkedList;

public class Board {

    private int size;
    private Variable [][]board ;
    private HashSet<Variable> unassignedVar;
    private int nodeCount;
    private int backtrackCount;

    public Board(int size)
    {
        this.size=size;
        board=new Variable[size][size];
        nodeCount=0;
        backtrackCount=0;
    }
    public void initializeBoard(int [][] arr)
    {
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                board[i][j]=new Variable(i,j,arr[i][j]);
//                if(arr[i][j]==0)
//                {
////                    for(int k=1;k<=size;k++)
////                    {
////                        if(isValid(i,j,k)) board[i][j].getDomain().add(k);
////                    }
//                    unassignedVar.add(board[i][j]);
//                }
            }
        }
        //setNeighbours();
    }

//    public void setNeighbours()
//    {
//        for (int i=0;i<size;i++)
//        {
//            for(int j=0;j<size;j++)
//            {
//                if(board[i][j].getVal()==0)
//                {
//                    for(int m=0;m<size;m++)
//                    {
//                        if(m!=i)
//                        {
//                            if(board[m][j].getVal()==0)
//                                board[i][j].getNeighbour().add(board[m][j]);
//                        }
//                    }
//                    for(int m=0;m<size;m++)
//                    {
//                        if(m!=j)
//                        {
//                            if(board[i][m].getVal()==0)
//                                board[i][j].getNeighbour().add(board[i][m]);
//                        }
//                    }
//                }
//            }
//        }
//    }

    public boolean notAllAssigned()
    {
        /*if(unassignedVar.size()!=0)
            return true;
        return false;*/
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                if(board[i][j].getVal()==0)
                    return true;
            }
        }
        return false;
    }

    public boolean isValid(int r, int c, int v)
    {
        for(int i=0;i<size;i++)
        {
            if(board[r][i].getVal()==v)
                return false;
        }
        for(int i=0;i<size;i++)
        {
            if(board[i][c].getVal()==v)
                return false;
        }
        return true;
    }

    public void setUnassignedVar()
    {
        unassignedVar=new HashSet<>();
        for(int i=0;i<size;i++)
        {
            for (int j=0;j<size;j++)
            {
                if(board[i][j].getVal()==0)
                {
                    unassignedVar.add(board[i][j]);
                }
            }
        }
    }
    public void printUnassignedVarList()
    {
        setUnassignedVar();
        for(Variable v:unassignedVar)
        {
            System.out.println(v.getRow()+" "+v.getCol()+" "+v.getVal());
        }
    }

    public HashSet<Variable> getNeighbours(Variable v) {

        HashSet<Variable> neighbours=new HashSet<>();
        int row=v.getRow();
        int col=v.getCol();

        for(int m=0;m<size;m++)
        {
            if(m!=row)
            {
                if(board[m][col].getVal()==0)
                    neighbours.add(board[m][col]);
            }
        }
        for(int m=0;m<size;m++)
        {
            if(m!=col)
            {
                if(board[row][m].getVal()==0)
                    neighbours.add(board[row][m]);
            }
        }
//        System.out.println(neighbours.size());
//        for(Variable n:neighbours)
//        {
//            System.out.println(n.getRow()+" "+n.getCol());
//        }
        return neighbours;
    }

    public HashSet<Integer> getDomain(Variable v)
    {
        HashSet<Integer> domain=new HashSet<>();
        int row=v.getRow();
        int col=v.getCol();
        for(int i=1;i<=size;i++)
        {
            if(isValid(row,col,i))
            {
                domain.add(i);
            }
        }
//        for(Integer i:domain)
//        {
//            System.out.print(i+" ");
//        }
        board[row][col].setDomain(domain);
        v.setDomain(domain);
        return domain;
    }
    public void setAllDomain()
    {
        setUnassignedVar();
        for(Variable v:unassignedVar)
        {
            getDomain(v);
        }
    }

    public Variable heuristicDomddeg()
    {
        setUnassignedVar();
        int min=99999;

        Variable var=null;
        for(Variable v: unassignedVar)
        {
            int domainSize=getDomain(v).size();
            int forwardDegree=getNeighbours(v).size();
            if(forwardDegree==0)
                return v;
                //forwardDegree=1;

            int x=domainSize/forwardDegree;
            if(x<min)
            {
                min=x;
                var=v;
            }
        }
        return var;
    }

    public Variable heuristicBrelaz()
    {
        setUnassignedVar();
        int minDomain=99999;
        int maxDegree=-99999;

        Variable var=null;
        for(Variable v: unassignedVar)
        {
            int domainSize=getDomain(v).size();
            int forwardDegree=getNeighbours(v).size();

            if(minDomain>domainSize)
            {
                minDomain=domainSize;
                maxDegree=forwardDegree;
                var=v;
            }
            else if(minDomain==domainSize)
            {
                if(forwardDegree>maxDegree)
                {
                    maxDegree=forwardDegree;
                    var=v;
                }
            }
        }
        return var;
    }

    public Variable heuristicBrelaz2()
    {
        setUnassignedVar();
        int minDomain=99999;
        int minDegree=-99999;

        Variable var=null;
        for(Variable v: unassignedVar)
        {
            int domainSize=getDomain(v).size();
            int forwardDegree=getNeighbours(v).size();

            if(minDomain>domainSize)
            {
                minDomain=domainSize;
                minDegree=forwardDegree;
                var=v;
            }
            else if(minDomain==domainSize)
            {
                if(forwardDegree<minDegree)
                {
                    minDegree=forwardDegree;
                    var=v;
                }
            }
        }
        return var;
    }

    public Variable heuristicSDF()
    {
        setUnassignedVar();
        int min=999999;

        Variable var=null;
        for(Variable v: unassignedVar)
        {
            int domainSize=getDomain(v).size();

            if(domainSize<min)
            {
                min=domainSize;
                var=v;
            }
        }
        return var;
    }

    public void printBoard()
    {
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                System.out.print(" "+board[i][j].getVal()+" ");
            }
            System.out.println();
        }
    }
    public boolean backtracking()
    {
        if(!notAllAssigned())
            return true;
        //setUnassignedVar();
       // Iterator<Variable> itr=unassignedVar.iterator();
        //Variable var=itr.next();
        Variable var=heuristicSDF();
        //HashSet<Integer> values=getDomain(var);
        //nodeCount++;

        for (int i=1;i<=size;i++)
        {
            nodeCount++;
            //boolean consistent=isConsistent(var);
            if(isValid(var.getRow(),var.getCol(),i))
            {
                var.setVal(i);
                if(backtracking())
                    return true;
                var.setVal(0);
                backtrackCount++;
            }
        }
        return false;
    }

    public boolean forwardChecking()
    {
        if(!notAllAssigned())
            return true;
        Variable var=heuristicBrelaz();
        HashSet<Integer> values=getDomain(var);
        //nodeCount++;

        for (Integer i:values)
        {
            nodeCount++;
            var.setVal(i);
            boolean consistent=isConsistent(var);

            //System.out.println("hello");
            if(consistent && forwardChecking())
                return true;

            var.setVal(0);
            backtrackCount++;
        }
        return false;
    }

    public boolean isConsistent(Variable var)
    {
        //boolean consistent=true;

        HashSet<Variable> neighbours=getNeighbours(var);

        for(Variable v: neighbours)
        {
            HashSet<Integer> domain=getDomain(v);

//            if(domain.contains(var.getVal()))
//            {
//                if(domain.size()==1)
//                    return false;
//                //else
//                    //domain.remove(var.getVal());
//            }
            if(domain.size()==0)
                return false;
        }
        return true;
    }

    public void printStat()
    {
        System.out.println("Number of nodes: "+nodeCount);
        System.out.println("Number of backtracks: "+backtrackCount);

    }

    public boolean macConsistent(Variable var)
    {
        HashSet<Variable> neighbours=getNeighbours(var);

        LinkedList<Variable> queue=new LinkedList<>();
        for(Variable v: neighbours)
        {
            queue.add(v);
//            HashSet<Integer> domain=getDomain(v);
//
//            if(domain.size()==0)
//                return false;
//            HashSet<Variable> n=getNeighbours(v);
//            for(Variable vc:n)
//                neighbours.add(vc);

        }
        while(queue.size()!=0)
        {
            //System.out.println("hello");
            Variable v=queue.poll();
            int prevDomainSize=v.getDomain().size();
            HashSet<Integer> domain=getDomain(v);
            int newDomainSize=domain.size();
            if(prevDomainSize!=newDomainSize)
            {
                //System.out.println("hello");
                if(domain.size()==0)
                {
                    //backtrackCount++;
                    return false;
                }
                HashSet<Variable> n=getNeighbours(v);
                for(Variable vc:n)
                    queue.add(vc);
            }

        }
        return true;
    }
    public boolean MAC()
    {
        if(!notAllAssigned())
            return true;
        setAllDomain();
        Variable var=heuristicSDF();
        HashSet<Integer> values=getDomain(var);
        //nodeCount++;

        for (Integer i:values)
        {
            nodeCount++;
            var.setVal(i);
            boolean consistent=macConsistent(var);

            if(consistent && MAC())
                return true;

            var.setVal(0);
            //if(!consistent)
                backtrackCount++;
        }
        return false;
    }
}
