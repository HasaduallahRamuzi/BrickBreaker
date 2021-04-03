import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.Timer;



public class main{
    private ImageIcon arcade;

    public static void main(String[] args) {
        JFrame f = new JFrame();
        Game game = new Game();
        f.setBounds(0,0,900,594);
        f.setTitle("Covid Brick Breaker 1902223");
        f.setResizable(false);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(game);
        ImageIcon img = new ImageIcon("arcade.jpg"); //https://pixels.com/featured/arcade-machine-screen-allan-swart.html
        JLabel background = new JLabel("",img,JLabel.CENTER);
        f.add(background);
        background.setBounds(0,0,900,594);
        File music = new File ("finalmusic.wav");
        PlayMusic(music);
        PlayMusic(music);
        /*
        The music was made by clipping youtube videos of Borris Johson thats been layered over
        a soundtrack that was given by a Musician friend of mine Quiana Aparentado.
        Email: quiananadine@gmail.com
        */





     /*The code above sets:
      a new JFrame called f
      calls the title "BrickBreaker"
      restricts the user from resizing the JFrame
      and when you exit the JFrame it will close the window.
      I created an image icon called img and played it in the Jlabel called background.
      I then set the bounds 900,594, the same size as the Jframe.
      I set the variable File music with the file name of the soundtrack that is in my src folder.
      When PlaySound(music) is executed the soundtrack is played when running the program.
      Calling it twice means it will play back to back.

      */



    }

    static void PlayMusic(File Sound){
        try{

            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(Sound));

            clip.start();

            Thread.sleep(clip.getMicrosecondLength()/1000);
        }catch(Exception e){

        }
    }

/*
The code above runs a try and catch block. We initialise clip and get clip from AudioSystem
(a built in class in java). "clip.open" we open the AudioSystem and getAudioInputStream we
put as an argument. clip.start() starts the clip.

The problem here is the program will run but terminate at line 56 and we wont be able to hear
the beautiful soundtrack.

So we make the program sleep, by doing Thread.sleep(clip.getMicrosecondLength()/1000)
Thread.sleep takes milli seconds and our length is in micro seconds so we devide by 1000.

*/

}

class Game extends JPanel implements KeyListener, ActionListener, MouseListener {

    private boolean enterCoin = false; //when true game plays
    private int score = 0; //player score, every time brick is broken goes up by 100
    private int totalBricks = 48; //total bricks used
    private int lives = 3; //each time ball is out of play lives decreases by 1. when lives ==0 Game is over
    private Timer time; //used time class for speed of ball
    private int delay = 10; //the lower the number the faster the ball travels

    private int PaddlePos = 310; //position of player on X axis
    private int ballPosx = 400; //position of ball on x axis
    private int BallPosy = 350; //position of ball on y axis
    private int ballXVelocity = -1; //direction of ball, i.e velocity
    private int ballyVelocity = -2; //direction of ball, i.e velocity
    private Bricks map; //2d array used to print out bricks, will go in depth in Bricks class and line 222 and Bricks java class
    private Menu menu; //menu java class

//All classes are initialised here


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        //play
        if (mx > Game.WIDTH / 2 + 390 && mx <= Game.WIDTH / 2 + 490) {
            if (my >= 150 && my <= 200) {
                Game.State = Game.STATE.GAME;


            }
        }

        if (mx > Game.WIDTH / 2 + 390 && mx <= Game.WIDTH / 2 + 490) {
            if (my >= 265 && my <= 320) {


            }
        }
        /*In the Menu Java class I made a rectangle called "Play". The above code changes the state to Game from menu
        when the mouse curse is in between the x values 390 and 490 and y values between 265 and 320. These are the bounds
        the MouseEvent can be executed. This was done using nested if statements.
         */

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    public static enum STATE {
        MENU,
        GAME
    }

    ;
    /*
    enum class allows us to have different codes running when the program is in different states. The menu state all code is in the Menu.java class. I implemented this in order to have a menu, for future iterations
    of this game I will use this for different levels .
     */

    public static STATE State = STATE.MENU;

    public Game() {
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        time = new Timer(delay, this);
        time.start();
        map = new Bricks(4, 12); //2d array 2 arguments row and col
        menu = new Menu();

    }


    public void paint(Graphics g) {
        g.setColor((Color.black));
        g.fillRoundRect(237, 35, 430, 430, 20, 20); //The game is played within a black rectangle that is fit flush so it looks like you are playing on the arcade screen

        if (State == STATE.GAME) {
            g.setColor(Color.white);
            setFont(new Font("courieer", Font.BOLD, 15));
            g.drawString("LIVES " + lives, 260, 70);
            //The code is only executed when the state is in Game. This Lives that can be found in the top left.


            g.setColor(Color.RED);
            g.fillOval(ballPosx, BallPosy, 9, 9); //ball


            g.setColor(Color.darkGray);
            g.fillRect(240, 45, 3, 400);
            g.fillRect(660, 45, 3, 400);
            g.fillRect(240, 45, 420, 3);//This is the grey border, top, left and right wall


            g.setColor(Color.white);
            g.fillRect(PaddlePos, 440, 75, 3); //paddle

            g.setColor(Color.white);
            setFont(new Font("courieer", Font.BOLD, 15));
            g.drawString("" + score, 620, 70);//score top right

            if (score > 900) {
                g.setColor(Color.green);
                setFont(new Font("courieer", Font.BOLD, 15));
                g.drawString("" + score, 620, 70);//if score >900 the color changes to green
            }
//

            if (BallPosy > 450) {
                enterCoin = false;
                ballPosx = 300;
                BallPosy = 200;
                lives--;//if ball goes out of bounds i.e y=400 then 1 is taken from the lives and ball is reset to (300,200)

                g.setColor(Color.white);
                setFont(new Font("courieer", Font.BOLD, 15));
                g.drawString("LIVES " + lives, 260, 70);
            }

            if (score > 0 && score < 400) {


                g.setColor(Color.white);
                setFont(new Font("courieer", Font.BOLD, 15));
                g.drawString("WEAR A MASK", 410, 70); //if score >0<400 prints "WEAR A MASK" AT (400,70)
            }

            if (score > 400 && score < 1100) {


                g.setColor(Color.white);
                setFont(new Font("courieer", Font.BOLD, 15));
                g.drawString("WASH YOUR HANDS", 390, 70);//if score >400<1100 prints "WASH YOUR HANDS" AT (390,70)
            }

            if (score > 1200 && score < 2300) {


                g.setColor(Color.white);
                setFont(new Font("courieer", Font.BOLD, 15));
                g.drawString("FOLLOW THE NEWS", 390, 70); //if score >1200<2300 prints "FOLLOW THE NEWS" AT (390,70)
            }


            if (score > 2400) {


                g.setColor(Color.white);
                setFont(new Font("courieer", Font.BOLD, 15));
                g.drawString("FOLLOW THE RULES", 385, 70); //if score >2400 prints "FOLLOW THE RULES" AT (385,70)
            }

            if (lives == 0) {
                enterCoin = false;
                ballXVelocity = 0;
                ballyVelocity = 0;
                g.setColor(Color.red);
                g.setFont(new Font("courieer", Font.BOLD, 50));
                g.drawString("GAME OVER", 295, 250);
                g.setColor(Color.red);
                g.setFont(new Font("courieer", Font.BOLD, 15));
                g.drawString("(Press Enter to Insert New Coin)", 335, 270);//if lives ==0 then Game over printed

            }

            if (score == 4800) {
                enterCoin = false;
                ballXVelocity = 0;
                ballyVelocity = 0;
                g.setColor(Color.red);
                g.setFont(new Font("courieer", Font.BOLD, 50));
                g.drawString("GAME OVER", 295, 250);
                g.setColor(Color.red);
                g.setFont(new Font("courieer", Font.BOLD, 35));
                g.drawString("Score " + score, 355, 300);
                g.setColor(Color.red);
                g.setFont(new Font("courieer", Font.BOLD, 15));
                g.drawString("(Press Enter to Insert New Coin)", 335, 320);//if score >4800, i.e. when bricks finished prints "GAME OVER"

            }


            map.draw((Graphics2D) g);//IF State == Game, the code above is executed and the bricks are printed

        } else {
            menu.render((Graphics2D) g); //else menu is printed


        }


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        time.start();
        if (enterCoin = true || State == STATE.GAME) {
            if (new Rectangle(ballPosx, BallPosy, 9, 9).intersects(new Rectangle(PaddlePos, 440, 75, 3))) {
                ballyVelocity = -ballyVelocity;
            }

            breakPoint:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 325;
                        int bricky = i * map.brickWidth + 90;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, bricky, brickWidth, brickHeight);
                        Rectangle ballHitBox = new Rectangle(ballPosx, BallPosy, 9, 9);
                        Rectangle brickRect = rect;

                        if (ballHitBox.intersects(brickRect)) {
                            map.setBrickScore(0, i, j);
                            totalBricks--;
                            score += 100;

                            if (ballPosx + 19 <= brickRect.x || ballPosx + 1 >= brickRect.x + brickRect.width) {
                                ballXVelocity = -ballXVelocity;
                            } else {
                                ballyVelocity = -ballyVelocity;
                            }
                            break breakPoint;

                        }

                    }
                }
            }

            ballPosx += ballXVelocity;
            BallPosy += ballyVelocity;
            if (ballPosx < 240) {
                ballXVelocity = -ballXVelocity;
            }
            if (BallPosy < 45) {
                ballyVelocity = -ballyVelocity;
            }
            if (ballPosx > 653) {
                ballXVelocity = -ballXVelocity;
            }
        }


        repaint();
    }
    /*
    Line 237: if enterCoin = true and State = Game then the following code is executed
    Line 238-239: A rectangle is created around the ball and paddle. If the intersect then the ball will repel opposite direction
    Line 240: if the content of the [i][j]>0, i.e if the bricks are printed then a rectangle is drawn in the dimension of each brick
    Line 241: Then if ball intersects bricks then the value is reduced to 0, total bricks is reduced by 1 and score is increased by 100
    Line 259: If ball pos hits a brick then the ball will go appropriate direction, the values were gotten from trial and error.
    Line 272-281: If the ball hits any of the walls, top, left and right then the velocity will change, opposite direction.


     */

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (PaddlePos >= 584) {
                PaddlePos = 584;
            } else {
                moveRight();
            }
        }
        /*
        The Key event allows the user to move the paddle across the x axis.
        The if statement is there to set bounds, if the player exceeds x 584 then it will keep it at 584.
        So the paddle cannot fly out of bounds.
         */

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (PaddlePos <= 245) {
                PaddlePos = 245;
            } else {
                moveLeft();
            }
            /*
            The Key event allows the user to move the paddle across the x axis.
            The if statement is there to set bounds, if the player exceeds bellow x 245 then it will keep it at 245.
            So the paddle cannot fly out of bounds.
            */
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!enterCoin) {
                enterCoin = true;
                ballPosx = 400;
                BallPosy = 350;
                ballXVelocity = -1;
                ballyVelocity = -2;
                PaddlePos = 310;
                score = 0;
                totalBricks = 48;
                lives = 3;
                map = new Bricks(4, 12);

                repaint();
            }
        }

        //If enter is pressed then it will reset the game, this is when Game is over from winning of losing the game

    }

    public void moveRight() {
        enterCoin = true;
        PaddlePos += 10;
        //If the right button is pressed then it will move 10 pixels to the right
    }

    public void moveLeft() {
        enterCoin = true;
        PaddlePos -= 10;
        //If the left button is pressed then it will move 10 pixels to the left
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }

    public class Bricks {
        public int map[][];
        public int brickWidth;
        public int brickHeight;

        public Bricks(int row, int col) {
            map = new int[row][col];
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[0].length; j++) {
                    map[i][j] = 1;
                }

            }
            brickWidth = 20;
            brickHeight = 20;

        }

        public void draw(Graphics2D g) {
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[0].length; j++) {
                    if (map[i][j] > 0) {
                        g.setColor(Color.lightGray);
                        g.fillRect(j * brickWidth + 325, i * brickHeight + 90, brickWidth, brickHeight);
                        g.setStroke(new BasicStroke(3));
                        g.setColor(Color.black);
                        g.drawRect(j * brickWidth + 325, i * brickHeight + 90, brickWidth, brickHeight);
                    }
                }
            }
        }

        public void setBrickScore(int value, int row, int col) {
            map[row][col] = value;
        }
    }
/*
If the 2d array value is >0 then line 21-29 is executed. Otherwise the map will be empty. This with if statements combined with
intersection function allowed me to do the following.

If map is initiated then map value = 1.
if ball hit box intersects with brick hit box map value is 0. This will break the brick in the wall but keep untouched bricks normal.
 */



    public class Menu {
        public Rectangle playButton = new Rectangle(Game.WIDTH / 2 + 390, 150, 100, 50); //Play button dimension


        public void render(Graphics2D g) {


            Font font1 = new Font("courieer", Font.BOLD, 50);
            g.setFont(font1);
            g.setColor(Color.white);
            g.drawString("BRICKBREAKER", 250, 100); //title

            Font font2 = new Font("courieer", Font.BOLD, 23);
            g.setFont(font2);
            g.setColor(Color.white);
            g.drawString("PLAY", 410, 185);
            g.draw(playButton); //Play button font inside the box

            Font font3 = new Font("Courieer", Font.BOLD, 10);
            g.setFont(font3);
            g.setColor(Color.white);
            g.drawString("In order to successfully fight coronavirus a few sacrifices have to be made", 270, 220);
            g.drawString("It is mandatory to wear a mask in all public areas", 325, 240);
            g.drawString("Mixing with other households is now not allowed", 325, 260);
            g.drawString("If you have symptoms it is advised to self isolate, away from family and friends", 260, 280);
            g.drawString("The NHS and WHO say to self isolate for 14 days or until a test comes negative", 260, 300);
            g.drawString("Symptoms include: A high temperature, a cough and loss of taste and smell", 260, 320);
            g.drawString("This game is to spread awareness and to help people get through lockdown", 260, 340);
            g.drawString("Although not a symptom, people may feel down, this is normal ", 300, 360);
            g.drawString("Unfortunately these hard times have taken a toll on people's mental health ", 270, 380);
            g.drawString("It is best to keep busy and do things that can make you happy ", 300, 400);
            g.drawString("be safe, take care of yourself and others", 350, 420);
            g.drawString("and most importantly smile!", 380, 440);
        /*
        code for message on the menu screen.
         */


        }


    }
}

