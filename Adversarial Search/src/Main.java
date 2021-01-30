import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Main {
    public static int size = 8;
    public static int ai_player = 0;
    public static int max_depth = 3;
    public static position best_from, best_to;
    public static Scanner scanner;

    public static int max_recursive(checker_board game_board, int depth, int alpha, int beta)
    {
        if(depth == max_depth)
        {
            return game_board.evaluate();
        }
        int val = Integer.MIN_VALUE;
        piece[][] board = game_board.getBoard();

        for(int i=0; i<size; i++)
        {
            for(int j=0; j<size; j++)
            {
                if(board[i][j].getPlayer() == 0)
                {
                    for(position to: board[i][j].getMoves())
                    {
                        checker_board new_board = new checker_board(game_board);
                        new_board.move_piece(0, new position(i,j), to);

                        int cur_val = min_recursive(new_board, depth+1, alpha, beta);
                        if (cur_val > val)
                        {
                            val = cur_val;
                            if (depth == 0)
                            {
                                best_from = new position(i, j);
                                best_to = new position(to.x, to.y);
                            }
                        }
                        if (val >= beta)
                        {
                            return val;
                        }
                        alpha = Math.max(alpha, val);
                    }
                }
            }
        }
        return val;
    }

    public static int min_recursive(checker_board game_board, int depth, int alpha, int beta)
    {
        if(depth == max_depth)
        {
            return game_board.evaluate();
        }
        int val = Integer.MAX_VALUE;
        piece[][] board = game_board.getBoard();

        for(int i=0; i<size; i++)
        {
            for(int j=0; j<size; j++)
            {
                if(board[i][j].getPlayer() == 1)
                {
                    for(position to: board[i][j].getMoves())
                    {
                        checker_board new_board = new checker_board(game_board);
                        new_board.move_piece(0, board[i][j].getPos(), to);

                        int cur_val = max_recursive(new_board, depth+1, alpha, beta);
                        if (cur_val < val)
                        {
                            val = cur_val;
                            if (depth == 0)
                            {
                                best_from = new position(i, j);
                                best_to = new position(to.x, to.y);
                            }
                        }
                        if (val <= alpha)
                        {
                            return val;
                        }
                        beta = Math.min(beta, val);
                    }
                }
            }
        }
        return val;
    }

    public static void send_move()
    {
        System.out.println(best_from);
        System.out.println(best_to);

        try {
            FileWriter out = new FileWriter("shared.txt");
            out.write("2\n" + best_from + best_to + "\n");
            out.close();
        }
        catch (Exception e) {
//            e.printStackTrace();
        }
    }

    public static void get_move() {
//        int x = scanner.nextInt();
//        int y = scanner.nextInt();
//        best_from = new position(x, y);
//        x = scanner.nextInt();
//        y = scanner.nextInt();
//        best_to = new position(x, y);
        while (true)
        {
            try {
                Scanner in = new Scanner(new File("shared.txt"));
                int who = in.nextInt();
                if (who == -1)
                {
                    System.exit(0);
                }
                else if (who == 1 - ai_player)
                {
                    int fx = in.nextInt();
                    int fy = in.nextInt();
                    int tx = in.nextInt();
                    int ty = in.nextInt();
                    best_from = new position(fx, fy);
                    best_to = new position(tx, ty);
                    System.out.println(best_from + " zahin " + best_to);
                    return;
                }
                in.close();
            }
            catch (Exception e) {
//                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        scanner = new Scanner(System.in);
        checker_board game_board;
        game_board = new checker_board(size);
//        game_board.print();
//        game_board.print_all_moves(ai_player);
        int current_player = 0;

        while(true)
        {
            if(ai_player == current_player)
            {
                best_from = null;
                best_to = null;
                int alpha = Integer.MIN_VALUE;
                int beta = Integer.MAX_VALUE;
                if(ai_player == 0)
                {
                    max_recursive(game_board, 0, alpha, beta);
                }
                else
                {
                    min_recursive(game_board, 0, alpha, beta);
                }
                send_move();
            }
            else
            {
                get_move();
            }

            game_board.move_piece(current_player, best_from, best_to);
            game_board.print();

            if(game_board.has_won(current_player))
            {
                System.out.println("winner: " + current_player);
                break;
            }
            current_player = 1 - current_player;
            if(game_board.has_won(current_player))
            {
                System.out.println("winner: " + current_player);
                break;
            }
        }
    }
}
