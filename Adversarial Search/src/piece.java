import java.util.ArrayList;

public class piece {

    private int player;
    //private int pos_x;
    //private int pos_y;
    private position pos;
    private ArrayList<position> moves;

    public piece(int player,int x,int y)
    {
        this.player = player;
        //pos_x=x;
        //pos_y=y;
        pos=new position(x,y);
        moves=new ArrayList<>();
    }

    public piece(piece p) {

        player = p.player;
        pos=new position(p.pos.x, p.pos.y);
        moves = new ArrayList<>();
        for (position move: p.moves)
        {
            moves.add(new position(move));
        }
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public position getPos() {
        return pos;
    }

    public void setPos(position pos) {
        this.pos = pos;
    }

    public ArrayList<position> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<position> moves) {
        this.moves = moves;
    }

    /* public int getPos_x() {
        return pos_x;
    }

    public int getPos_y() {
        return pos_y;
    }
    public void setPos_x(int pos_x) {
        this.pos_x = pos_x;
    }

    public void setPos_y(int pos_y) {
        this.pos_y = pos_y;
    }*/

}
