import java.nio.file.attribute.PosixFileAttributes;
import java.util.HashSet;
import java.util.LinkedList;

public class checker_board {

    private piece[][] board;
    private int size;
    private int pieces_count_1;
    private int pieces_count_2;

//    public HashSet<piece> all_pieces;

    //public HashSet<piece> player1;
    //public HashSet<piece> player2;

    public checker_board(int size)
    {
        this.size=size;
        board = new piece[size][size];
        pieces_count_1=pieces_count_2=2*(size-2);

        //player1=new HashSet<>();
        //player2=new HashSet<>();

        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                board[i][j]=new piece(-1,i,j);
            }
        }
        initialize_board();
        //set_all_moves();
    }

    public checker_board(checker_board new_board)
    {
        size = new_board.size;
        board = new piece[size][size];
        pieces_count_1 = new_board.pieces_count_1;
        pieces_count_2 = new_board.pieces_count_2;

        for(int i=0; i<size; i++)
        {
            for(int j=0; j<size; j++)
            {
                board[i][j] = new piece(new_board.getBoard()[i][j]);
            }
        }
    }

    public void initialize_board()
    {
        for(int i=1;i<size-1;i++)
        {
            board[i][0]=new piece(1,i,0);
        }
        for(int i=1;i<size-1;i++)
        {
            board[i][size-1]=new piece(1,i,size-1);
        }
        for(int i=1;i<size-1;i++)
        {
            board[0][i]=new piece(0,0,i);
        }
        for(int i=1;i<size-1;i++)
        {
            board[size-1][i]=new piece(0,size-1,i);
        }

        for(int i=1;i<size-1;i++)
        {
            set_valid_moves(board[i][0].getPos());
        }
        for(int i=1;i<size-1;i++)
        {
            set_valid_moves(board[i][size-1].getPos());
        }
        for(int i=1;i<size-1;i++)
        {
            set_valid_moves(board[0][i].getPos());
        }
        for(int i=1;i<size-1;i++)
        {
            set_valid_moves(board[size-1][i].getPos());
        }
    }

    public piece[][] getBoard() {
        return board;
    }

    public int existing_pieces(int player_id)
    {
        int count=0;

        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                if(board[i][j].getPlayer()==player_id)
                {
                    count++;
                }
            }
        }
        if(player_id==0) pieces_count_1=count;
        else if(player_id==1) pieces_count_2=count;

        return count;
    }

    /*public void capture_piece(int id, position from, position to)
    {
        int temp=board[to.x][to.y].getPlayer();
        board[to.x][to.y].setPlayer(id);

        board[from.x][from.y].setPlayer(-1);

        if(temp==1) pieces_count_2--;
        else if(temp==0) pieces_count_1--;
    }*/

    public void move_piece(int id, position from, position to)
    {
//        System.out.println(id + ": " + from + " >> " + to);
        int temp=board[to.x][to.y].getPlayer();

        if(!is_empty(to) && is_opponent(from, to))
        {
            if(temp==1) pieces_count_2--;
            else if(temp==0) pieces_count_1--;
        }

        board[to.x][to.y].setPlayer(id);
        board[from.x][from.y].setPlayer(-1);

        clear_moveLists();
        set_all_moves();
    }

    public boolean is_position_valid(position to)
    {
        return (to.x>=0 && to.y>=0 && to.x<size && to.y<size) ;
    }

    public int pieces_in_row(position p)
    {
        int count=0;

        for(int i=0;i<size;i++)
        {
            if(board[p.x][i].getPlayer()!=-1) count++;
        }

        return count;
    }

    public int pieces_in_col(position p)
    {
        int count=0;

        for(int i=0;i<size;i++)
        {
            if(board[i][p.y].getPlayer()!=-1) count++;
        }
        return count;
    }

    public int pieces_in_first_diagonal(position p)
    {
        int count=0;

        for(int i=p.x, j=p.y ; i>=0 && j<size ;i--, j++)
        {
            if(board[i][j].getPlayer()!=-1) count++;
        }
        for(int i=p.x+1, j=p.y-1 ; i<size && j>=0 ;i++, j--)
        {
            if(board[i][j].getPlayer()!=-1) count++;
        }

        return count;
    }

    public int pieces_in_second_diagonal(position p)
    {
        int count=0;

        for(int i=p.x, j=p.y ; i<size && j<size ;i++, j++)
        {
            if(board[i][j].getPlayer()!=-1) count++;
        }
        for(int i=p.x-1, j=p.y-1 ; i>=0 && j>=0 ;i--, j--)
        {
            if(board[i][j].getPlayer()!=-1) count++;
        }
        return count;
    }

    public boolean is_opponent(position from, position to)
    {
        boolean flag=false;
        int p1=board[from.x][from.y].getPlayer();
        int p2=board[to.x][to.y].getPlayer();

        if(p1!=p2) flag=true;

        return flag;
    }

    public boolean is_empty(position to)
    {
        if(board[to.x][to.y].getPlayer()==-1) return true;
        else return false;
    }
    public boolean own_piece(position to, int id)
    {
        if(board[to.x][to.y].getPlayer()==id) return true;
        else return false;
    }

    public boolean jump_over_opponent(position from, position to)
    {
        boolean flag=false;
        int p1=board[from.x][from.y].getPlayer();
        int p2= 1 - p1;

        if(from.go_right(to))
        {
            for(int i=from.y+1;i<to.y;i++)
            {
                if(board[from.x][i].getPlayer()==p2)
                {
                    flag=true;
                    break;
                }
            }
        }
        else if(from.go_left(to))
        {
            for(int i=from.y-1;i>to.y;i--)
            {
                if(board[from.x][i].getPlayer()==p2)
                {
                    flag=true;
                    break;
                }
            }
        }
        else if(from.go_up(to))
        {
            for(int i=from.x-1;i>to.x;i--)
            {
                if(board[i][from.y].getPlayer()==p2)
                {
                    flag=true;
                    break;
                }
            }
        }
        else if(from.go_down(to))
        {
            for(int i=from.x+1;i<to.x;i++)
            {
                if(board[i][from.y].getPlayer()==p2)
                {
                    flag=true;
                    break;
                }
            }
        }
        else if(from.upper_left(to))
        {
            for(int i=from.x-1, j=from.y-1 ; i>to.x && j>to.y ;i--, j--)
            {
                if(board[i][j].getPlayer()==p2)
                {
                    flag=true;
                    break;
                }
            }
        }
        else if(from.upper_right(to))
        {
            for(int i=from.x-1, j=from.y+1 ; i>to.x && j<to.y ;i--, j++)
            {
                if(board[i][j].getPlayer()==p2)
                {
                    flag=true;
                    break;
                }
            }
        }
        else if(from.lower_right(to))
        {
            for(int i=from.x+1, j=from.y+1 ; i<to.x && j<to.y ;i++, j++)
            {
                if(board[i][j].getPlayer()==p2)
                {
                    flag=true;
                    break;
                }
            }
        }
        else if(from.lower_left(to))
        {
            for(int i=from.x+1, j=from.y-1 ; i<to.x && j>to.y ;i++, j--)
            {
                if(board[i][j].getPlayer()==p2)
                {
                    flag=true;
                    break;
                }
            }
        }
        return flag;
    }

    public boolean is_move_valid(position from, position to)
    {
        boolean flag=false;
        int c = -1, diff = -1;

        if(is_position_valid(from) && is_position_valid(to) && !from.equal_pos(to))
        {
            if(from.equal_row(to))
            {
                c=pieces_in_row(from);
                diff=from.row_dist(to);
            }
            else if(from.equal_col(to))
            {
                c=pieces_in_col(from);
                diff=from.column_dist(to);
            }
            else if(from.upper_left(to) || from.lower_right(to))
            {
                c=pieces_in_second_diagonal(from);
                diff=from.diagonal_dist(to);
            }
            else if(from.upper_right(to) || from.lower_left(to))
            {
                c=pieces_in_first_diagonal(from);
                diff=from.diagonal_dist(to);
            }
            else return flag;

            if(diff==c)
            {
                flag = !jump_over_opponent(from,to) && (is_empty(to) || is_opponent(from,to));
            }
        }
//        if (flag) {
//            System.out.println(from + " -> " + to + ": " + c + ": " + diff);
//        }
        return flag;
    }

    public void set_valid_moves(position from)
    {
        if(!is_empty(from))
        {
            piece p=board[from.x][from.y];

            HashSet<position> dest=new HashSet<>();

            for(int i=from.y+1;i<size;i++)
            {
                dest.add(board[from.x][i].getPos());
            }
            for(int i=from.y-1;i>=0;i--)
            {
                dest.add(board[from.x][i].getPos());
            }
            for(int i=from.x+1;i<size;i++)
            {
                dest.add(board[i][from.y].getPos());
            }
            for(int i=from.x-1;i>=0;i--)
            {
                dest.add(board[i][from.y].getPos());
            }
            for(int i=from.x+1, j=from.y+1;i<size && j<size;i++, j++)
            {
                dest.add(board[i][j].getPos());
            }
            for(int i=from.x-1, j=from.y-1;i>=0 && j>=0;i--, j--)
            {
                dest.add(board[i][j].getPos());
            }
            for(int i=from.x+1, j=from.y-1;i<size && j>=0;i++, j--)
            {
                dest.add(board[i][j].getPos());
            }
            for(int i=from.x-1, j=from.y+1;i>=0 && j<size;i--, j++)
            {
                dest.add(board[i][j].getPos());
            }
            for(position to: dest)
            {
                if(is_move_valid(from,to))
                {
                    p.getMoves().add(to);
                }
            }
        }
    }

    public void set_all_moves()
    {
        position from;

        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                from=board[i][j].getPos();
                if(!is_empty(from))
                    set_valid_moves(from);
            }
        }
    }

    public void clear_moveLists()
    {
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                if(board[i][j].getMoves().size()!=0)
                    board[i][j].getMoves().clear();
            }
        }
    }

    public int find_area(int pid)
    {
        int area;
        int low_x=size,low_y=size, high_x=-1,high_y=-1;
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                int id=board[i][j].getPlayer();
                if(id==pid)
                {
                    if(low_x>i)
                    {
                      low_x=i;
                    }
                    if(high_x<i)
                    {
                        high_x=i;
                    }
                    if(low_y>j)
                    {
                        low_y=j;
                    }
                    if(high_y<j)
                    {
                        high_y=j;
                    }
                }
            }
        }
        area=((high_x-low_x)+1)*((high_y-low_y)+1);

        return area;
    }

    public int heuristic_area()
    {
        return find_area(1)-find_area(0);
    }

    public int find_mobility(int pid)
    {
        int mob=0;

        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                int id=board[i][j].getPlayer();
                if(id==pid)
                {
                    mob+=board[i][j].getMoves().size();
                }
            }
        }
        return mob;
    }

    public int heuristic_mobility()
    {
        return find_mobility(0)-find_mobility(1);
    }

    public int find_quad(int id)
    {
        int quad_count=0;
        int count;

        for(int i=0;i<size-1;i++)
        {
            for(int j=0;j<size-1;j++)
            {
                count=0;

                if(board[i][j].getPlayer()==id) count++;
                if(board[i][j+1].getPlayer()==id) count++;
                if(board[i+1][j].getPlayer()==id) count++;
                if(board[i+1][j+1].getPlayer()==id) count++;

                if(count>2) quad_count++;
            }
        }

        return quad_count;
    }

    public int heuristic_quad()
    {
        return find_quad(0)-find_quad(1);
    }

    public int find_density(int id)
    {
        double distance=0;
        HashSet<position> positions=new HashSet<>();

        double com_x=0, com_y=0;

        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                if(board[i][j].getPlayer()==id)
                {
                    com_x+=i;
                    com_y+=j;
                    positions.add(board[i][j].getPos());
                }
            }
        }
        com_x/=positions.size();
        com_y/=positions.size();

        for(position p:positions)
        {
            distance+=Math.sqrt((p.x-com_x)*(p.x-com_x)+(p.y-com_y)*(p.y-com_y));
        }
        return (int)distance;
    }

    public int heuristic_density()
    {
        return find_density(1)-find_density(0);
    }

    public int find_connectedness(int id)
    {
        int count=0;

        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                if(board[i][i].getPlayer()==id)
                {
                    position pos=board[i][j].getPos();
                    pos.set_adj_pos();

                    for(position p:pos.adj_pos)
                    {
                        if(is_position_valid(p) && own_piece(p,id))
                            count++;
                    }
                }
            }
        }
        return count;
    }

    public int heuristic_connectedness()
    {
        return find_connectedness(0)-find_connectedness(1);
    }

    public int evaluate() {
        // return heuristic_connectedness()+ heuristic_density() + heuristic_mobility() + heuristic_area() + heuristic_quad();
        return 10*heuristic_density() + heuristic_connectedness() + heuristic_mobility();
    }

    public position first_occurrence(int id)
    {
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                if(board[i][j].getPlayer()==id)
                {
                    return board[i][j].getPos();
                }
            }
        }
        return null;
    }

    public boolean has_won(int id)
    {
        boolean[][] visited =new boolean[size][size];

        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                visited[i][j]=false;
            }
        }
        position start=first_occurrence(id);
        visited[start.x][start.y]=true;

        LinkedList<position> queue=new LinkedList<>();
        queue.add(start);

        while(queue.size()!=0)
        {
            position p=queue.poll();

            p.set_adj_pos();
            for(position pos:p.adj_pos)
            {
                if(is_position_valid(pos) && own_piece(pos,id) && !visited[pos.x][pos.y])
                {
                    visited[pos.x][pos.y]=true;
                    queue.add(pos);
                }
            }
        }
        int current=0,count=0;
        if(id==0) current=pieces_count_1;
        else if(id==1) current=pieces_count_2;

        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                if(visited[i][j])
                    count++;
            }
        }
        return count==current;
    }

    public void print_all_moves(int player) {
        for(int i=0; i<size; i++) {
            for(int j = 0; j < size; j++) {
                if(board[i][j].getPlayer() == player) {
                    System.out.print(board[i][j].getPos() + " -> ");
                    for(position move: board[i][j].getMoves()) {
                        System.out.print(move + ", ");
                    }
                    System.out.println();
                }
            }
        }
    }

    public void print() {
        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
                if(board[i][j].getPlayer() == -1) {
                    System.out.print(". ");
                }
                else {
                    System.out.print(board[i][j].getPlayer() + " ");
                }
            }
            System.out.println();
        }
    }
}
