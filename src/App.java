import javax.swing.*;


public class App {

      public static void main(String[] args) throws Exception {

           //dimensions of window

           int boardWidth = 360;
           int boardHeight = 640;


           // Setting up frame 

           JFrame frame  = new JFrame("Falppy bird");
          //    frame.setVisible(true);
           frame.setSize(boardWidth , boardHeight);
           frame.setLocationRelativeTo(null);
           frame.setResizable(false);
           frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

           FlappyBird flappyBird = new FlappyBird();
           frame.add(flappyBird);
           frame.pack();
           frame.setVisible(true);

      }
}