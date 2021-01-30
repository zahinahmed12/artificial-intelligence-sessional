package javagame;
import org.lwjgl.input.Mouse;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.geom.Circle;


public class make_screen extends BasicGameState {

    public String mouse="black";
    Graphics gr;

    public make_screen(int state)
    {

    }
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
    {

    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
    {
        gr=g;
        //g.drawString(mouse,0,0);
        Image board=new Image("res/board8x8.png");
        g.drawImage(board,50, 80);

        g.setColor(Color.white);
        g.fillOval(56, 460,50, 50);
        g.fillOval(56, 398,50, 50);
        g.fillOval(56, 336,50, 50);
        g.fillOval(56, 273,50, 50);
        g.fillOval(56, 210,50, 50);
        g.fillOval(56, 148,50, 50);

        g.fillOval(494, 148, 50,50);
        g.fillOval(494, 210, 50,50);
        g.fillOval(494, 273, 50,50);
        g.fillOval(494, 336, 50,50);
        g.fillOval(494, 398, 50,50);
        g.fillOval(494, 460, 50,50);

        g.setColor(Color.black);
        g.fillOval(119,86,50,50);
        g.fillOval(182,86,50,50);
        g.fillOval(244,86,50,50);
        g.fillOval(307,86,50,50);
        g.fillOval(369,86,50,50);
        g.fillOval(431,86,50,50);

        g.fillOval(119,522,50,50);
        g.fillOval(182,522,50,50);
        g.fillOval(244,522,50,50);
        g.fillOval(307,522,50,50);
        g.fillOval(369,522,50,50);
        g.fillOval(431,522,50,50);

        g.setColor(Color.red);
        //g.drawLine(81,485,205,485);

    }

    public void update(GameContainer gc, StateBasedGame sbg,int beta) throws SlickException
    {
        int x=Mouse.getX();
        int y=Mouse.getY();
        //mouse ="Mouse position x: "+x+" y: "+y;

        if(x>56 && x<106 && y>91 && y<141)
        {
            if(Mouse.isButtonDown(0))
            {
                System.exit(0);
                //Graphics g=new Graphics();
                //g.setColor(Color.red);
                //gr.drawLine(81,116,205,116);
                /*if(x>56 && x<106 && y>91 && y<141)
                {

                }*/
            }
        }
    }

    @Override
    public int getID() {
        return -1;
    }
}
