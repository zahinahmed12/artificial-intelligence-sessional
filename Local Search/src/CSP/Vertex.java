package CSP;

import java.util.HashSet;

public class Vertex implements Comparable{
    int node_no;
    int node_degree;
    int node_sat_degree;
    int node_color;
    HashSet<Integer> neighbours;

    public Vertex()
    {
        node_no=0;
        node_sat_degree=0;
        node_color=0;
        node_degree=0;
        neighbours=new HashSet<>();
    }
    public void setNeighbours(int v)
    {
        neighbours.add(v);
    }

    public int getNode_degree() {
        return node_degree;
    }

    public int getNode_no() {
        return node_no;
    }

    public int getNode_sat_degree() {
        return node_sat_degree;
    }

    public int getNode_color() {
        return node_color;
    }

    @Override
    public int compareTo(Object o) {

        Vertex temp1=(Vertex) this;
        Vertex temp2=(Vertex) o;

        if(temp1.node_sat_degree==temp2.node_sat_degree)
        {
            if(temp1.node_degree>temp2.node_degree)
            {
                return -1;
            }
            else if(temp1.node_degree<temp2.node_degree)
            {
                return 1;
            }
            else return 0;
        }
        else if(temp1.node_sat_degree>temp2.node_sat_degree)
        {
            return -1;
        }
        else if(temp1.node_sat_degree<temp2.node_sat_degree)
        {
            return 1;
        }
        else return 0;
    }
}
