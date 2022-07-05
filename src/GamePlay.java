import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private boolean play;
    private int score = 0;
    private int totalBricks=21;
    private Timer Timer;
    private int delay=0;
    private int playerX = 310;
    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballDirX=-1;
    private int ballDirY=-2;
    private MapGenerator map;

    public GamePlay(){
        map = new MapGenerator(3,7);

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        Timer = new Timer(delay,this);
        Timer.start();
    }

    public void paint(Graphics g){
        g.setColor(Color.darkGray);
        g.fillRect(1,1,692,592);
        map.draw((Graphics2D) g);
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);

        g.setColor(Color.white);
        g.setFont(new Font("sans-serif",Font.BOLD,25));
        g.drawString(""+score,590,30);

        g.setColor(Color.blue);
        g.fillRect(playerX,550,100,8);

        g.setColor(Color.GREEN);
        g.fillOval(ballPosX,ballPosY,20,20);

        if(ballPosY>570){
            play = false;
            ballDirX = 0;
            ballDirY = 0;

            g.setColor(Color.BLUE);

            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("GAME OVER   Score:"+score,150,300);

            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Press enter to restart",150,340);
        }

        if (totalBricks ==0){
            play = false;
            ballDirX = -2;
            ballDirY = -1;

            g.setColor(Color.red);

            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("YOU WON!!   Score:"+score,150,300);

            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Press enter to restart",150,340);
        }
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Timer.start();
        if (play){
            if(new Rectangle(ballPosX,ballPosY,20,20).intersects(new Rectangle(playerX,550,100,8))){
                ballDirY = -ballDirY;
            }
            A:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j]>0){
                        int brickX = j* map.brickWidth + 80;
                        int brickY = i* map.brickHeigth + 50;
                        int bricksWidth  = map.brickWidth;
                        int bricksHeight = map.brickHeigth;

                        Rectangle rect = new Rectangle(brickX,brickY,bricksWidth,bricksHeight);
                        Rectangle ballRect = new Rectangle(ballPosX,ballPosY,20,20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)){
                            map.setBrickValue(0,i,j);
                            totalBricks--;
                            score+=5;
                            if (ballPosX+19 <= brickRect.x || ballPosX+1 >=brickRect.x+bricksWidth){
                                ballDirX =- ballDirX;
                            }else{
                                ballDirY =-ballDirY;
                            }
                            break A;
                        }
                    }
                    
                }
                
            }
            ballPosX +=ballDirX;
            ballPosY +=ballDirY;

            if(ballPosX<0){
                ballDirX = -ballDirX;
            }
            if(ballPosY<0){
                ballDirY = -ballDirY;
            }
            if(ballPosX>670){
                ballDirX = -ballDirX;
            }
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            if (playerX>=600){
                playerX=600;
            }else{
                moveRigth();
            }

        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT){
            if (playerX<10){
                playerX=10;
            }else{
                moveLeft();
            }

        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            if (!play){
                ballPosX = 120;
                ballPosY = 350;

                ballDirX = -1;
                ballDirY = -2;

                score = 0;
                playerX = 310;

                totalBricks = 21;
                map=new MapGenerator(3,7);
                repaint();
            }

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void moveRigth(){
        play = true;
        playerX+=20;
    }

    public void moveLeft(){
        play = true;
        playerX-=20;
    }
}
