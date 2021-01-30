import java.util.LinkedList;

public class position {

    public int x;
    public int y;
    public LinkedList<position> adj_pos;

    public position(int x, int y)
    {
        this.x=x;
        this.y=y;
        adj_pos=new LinkedList<>();
    }

    public position(position pos)
    {
        this.x = pos.x;
        this.y = pos.y;
        adj_pos=new LinkedList<>();
    }

    public void set_adj_pos()
    {
        position p=new position(x-1,y);
        adj_pos.add(p);
        p=new position(x+1,y);
        adj_pos.add(p);
        p=new position(x,y-1);
        adj_pos.add(p);
        p=new position(x,y+1);
        adj_pos.add(p);
        p=new position(x+1,y+1);
        adj_pos.add(p);
        p=new position(x+1,y-1);
        adj_pos.add(p);
        p=new position(x-1,y+1);
        adj_pos.add(p);
        p=new position(x-1,y-1);
        adj_pos.add(p);
    }

    public boolean equal_pos(position p)
    {
        return (this.x==p.x && this.y==p.y);
    }

    public boolean equal_row(position p)
    {
        return this.x==p.x;
    }

    public boolean equal_col(position p)
    {
        return this.y==p.y;
    }

    public boolean go_right(position p)
    {
        return (this.x==p.x && this.y<p.y);
    }

    public boolean go_up(position p)
    {
        return (this.y==p.y && this.x>p.x);
    }

    public boolean go_left(position p)
    {
       return (this.x==p.x && this.y>p.y);
    }

    public boolean go_down(position p)
    {
        return (this.y==p.y && this.x<p.x);
    }

    public boolean upper_left(position p)
    {
        return (this.x>p.x && this.y>p.y && this.x-p.x==this.y-p.y);
    }

    public boolean lower_right(position p)
    {
        return (this.x<p.x && this.y<p.y && p.x-this.x==p.y-this.y);
    }

    public boolean upper_right(position p)
    {
        return (this.x>p.x && this.y<p.y && this.x-p.x==p.y-this.y);
    }

    public boolean lower_left(position p)
    {
        return (this.x<p.x && this.y>p.y && p.x-this.x==this.y-p.y);
    }

    public int row_dist(position p)
    {
        return Math.abs(this.y-p.y);
    }

    public int column_dist(position p)
    {
        return Math.abs(this.x-p.x);
    }

    public int diagonal_dist(position p)
    {
        return Math.abs(this.x-p.x);
    }

    @Override
    public String toString() {
        return x + " " + y + " ";
    }
}
