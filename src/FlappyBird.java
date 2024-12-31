import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class FlappyBird extends JPanel{
    

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

      FlappyBird() {
          setPreferredSize( new Dimension(boardWidth , boardHeight));
          setBackground(Color.blue);

          //Load images 

          backImg = new ImageIcon("images/bg.png").getImage();
          birdImg = new ImageIcon("images/bird.png").getImage();
          topPipeImg = new ImageIcon("images/top.pg").getImage();
          bottomPipeImg = new ImageIcon("images/bottom.pg").getImage();

          //Bird 
           bird = new Bird(birdImg);
          
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


}
