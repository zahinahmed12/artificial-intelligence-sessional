package javagame;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Game extends StateBasedGame{

    public static final String gamename="Lines of Action";
    public static final int screen=-1;

    public Game(String gamename)
    {
        super(gamename);
        this.addState(new make_screen(screen));
    }
    public void initStatesList(GameContainer gc) throws SlickException
    {
        this.getState(screen).init(gc,this);
        this.enterState(screen);
    }

    public static void main(String[] args) {

        AppGameContainer appgc;
        try
        {
            appgc=new AppGameContainer(new Game(gamename));
            appgc.setDisplayMode(600,600,false);

            appgc.start();

        }catch (SlickException e)
        {
            e.printStackTrace();
        }
    }
}
