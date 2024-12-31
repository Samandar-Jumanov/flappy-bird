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


     //Pipes  cals

     int pipeX = boardWidth;
     int pipeY = 0;
     int pipeWidth = 64;
     int pipeHeight = 512;


     class Pipe {

         int x = pipeX;
         int y = pipeY;
         int height = pipeHeight;
         int width = pipeWidth;
         boolean passed = false;

         Image img;

         Pipe ( Image img ) {
               this.img = img;
         }

     }



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

      // Pipe times
      Timer placePipesTimer;


      // movements
      int velocityY = 0;
      int velocityX = -4;
      int gravity = 1;


       ArrayList<Pipe> pipes; // ?
       Random random = new Random();



      FlappyBird() {
          setPreferredSize( new Dimension(boardWidth , boardHeight));
          setBackground(Color.blue);

          setFocusable(true);
          addKeyListener(this);

          //Load images 

          backImg = new ImageIcon("images/bg.png").getImage();
          birdImg = new ImageIcon("images/bird.png").getImage();
          topPipeImg = new ImageIcon("images/top.png").getImage();
          bottomPipeImg = new ImageIcon("images/bottom.png").getImage();

          //Bird 
           bird = new Bird(birdImg);
           pipes = new ArrayList<Pipe>();

           placePipesTimer = new Timer(1500 ,  new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                         placePipes();
                    }
           });
           placePipesTimer.start();

           // game Loop 
           gameLoop = new Timer(1000/60, this);
           gameLoop.start();

           //Pipes 


           
          
      }


      public void placePipes ( ) {


         int  randomPipeY = (int) (pipeY - pipeHeight / 4 - Math.random() * ( pipeHeight / 2 ) ); 
         int space = pipeHeight / 4;
          
          Pipe topPipe = new Pipe(topPipeImg);
          topPipe.y = randomPipeY;
          pipes.add(topPipe);


          Pipe bottomPipe = new Pipe(bottomPipeImg);
          bottomPipe.y = topPipe.y + pipeHeight + space;
          pipes.add(bottomPipe);

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

         // Pipes 

         for ( Pipe p : pipes ) {
                 g.drawImage(p.img, p.x, p.y, p.width, p.height, null);
         }

      }


      public void move () {
          velocityY += gravity;
          bird.y += velocityY;       
          bird.y = Math.max(bird.y , 0);

          //Pipes
          for( Pipe p : pipes ) {
              p.x += velocityX;
          }
              
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
