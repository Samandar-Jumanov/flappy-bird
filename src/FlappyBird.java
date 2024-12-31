import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;



// implements used when to copy certain structures of interface and it can be done multiple times
// extends used only for class to inherit some kind of properties from parent to child


public class FlappyBird extends JPanel implements   ActionListener , KeyListener{
    

    // dimensions of window

    int boardWidth = 360;
    int boardHeight = 640;

    //Images 

    Image backImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;



    // Bird cals  
    int birdX = boardWidth / 8;
    int birdY = boardHeight / 2;
    int birdWidth = 34;
    int birdHeight = 24;



    class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;

        Image img;


        Bird(Image img) {
           this.img = img;
        }
        
    }


    // game Logic 
      Bird bird;

      // game Timer 
      Timer gameLoop;



      // movements
      int velocityY = 0;
      int gravity = 1;

      FlappyBird() {
          setPreferredSize( new Dimension(boardWidth , boardHeight));
          setBackground(Color.blue);

          setFocusable(true);
          addKeyListener(this);

          //Load images 

          backImg = new ImageIcon("images/bg.png").getImage();
          birdImg = new ImageIcon("images/bird.png").getImage();
          topPipeImg = new ImageIcon("images/top.pg").getImage();
          bottomPipeImg = new ImageIcon("images/bottom.pg").getImage();

          //Bird 
           bird = new Bird(birdImg);

           // game Loop 
           gameLoop = new Timer(1000/60, this);
           gameLoop.start();
          
      }

      public void paintComponent( Graphics g ) {
          super.paintComponent(g);
          draw(g);

      }


      public void draw(Graphics g) {
        // background 
         g.drawImage(backImg, 0,0, boardWidth,  boardHeight , null);
         // bird 
         g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

      }


      public void move () {
          velocityY += gravity;
          bird.y += velocityY;       
          bird.y = Math.max(bird.y , 0);
              
      }

    @Override
    public void actionPerformed(ActionEvent e) {
           move();
           repaint();
    }


    @Override
    public void keyPressed(KeyEvent e) {
          if (e.getKeyCode() == KeyEvent.VK_SPACE) {
              velocityY = -6;
          }
    }


    @Override
    public void keyTyped(KeyEvent e) { }

   
    @Override
    public void keyReleased(KeyEvent e) { }


}
