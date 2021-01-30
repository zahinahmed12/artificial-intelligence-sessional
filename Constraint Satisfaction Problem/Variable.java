import java.util.HashSet;

public class Variable {

    private int row;
    private int col;
    private int val;
    private int degree;
    private HashSet<Integer> domain;
    private HashSet<Variable> neighbour;

    public Variable(int x,int y,int v)
    {
        row=x;
        col=y;
        val=v;
        domain=new HashSet<>();
        neighbour=new HashSet<>();
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public int getVal() {
        return val;
    }

    public int getDegree() {
        return degree;
    }

    public void setDomain(HashSet<Integer> domain) {
        this.domain = domain;
    }

    public HashSet<Integer> getDomain() {
        return domain;
    }

    public void setNeighbour(HashSet<Variable> neighbour) {
        this.neighbour = neighbour;
    }

    public HashSet<Variable> getNeighbour() {
        return neighbour;
    }
}
