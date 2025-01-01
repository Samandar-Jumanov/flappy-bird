import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    // Game states and modes

    private enum GameState {
        MODE_SELECT,
        PLAYING
    }
    
    private enum GameMode {
        EASY,
        NORMAL,
        HARD
    }
    

    // default state and mode for game 
    private GameState currentState = GameState.MODE_SELECT;
    private GameMode currentMode = GameMode.EASY;
    
    // Window dimensions
    int boardWidth = 360;
    int boardHeight = 640;
    boolean gameOver = false;
    double score = 0;

    // Images
    Image backImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;
    Image modeSelectionBg;


    // Bird calculations
    int birdX = boardWidth / 8;
    int birdY = boardHeight / 2;
    int birdWidth = 34; // TO DO
    int birdHeight = 24; // TO DO

    // Pipes calculations
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    // Game elements
    Bird bird;
    Timer gameLoop;
    Timer placePipesTimer;
    ArrayList<Pipe> pipes;
    Random random = new Random();

    // Mode-specific settings
    private final int EASY_GRAVITY = 1;
    private final int NORMAL_GRAVITY = 2;
    private final int HARD_GRAVITY = 3;
    
    // Pipe speeds
    private final int EASY_PIPE_SPEED = -3;
    private final int NORMAL_PIPE_SPEED = -4;
    private final int HARD_PIPE_SPEED = -6;

    // Bird sizes for different modes
    private final int BIRD_EASY_WIDTH = 24;    // slightly wider for better proportion
    private final int BIRD_NORMAL_WIDTH = 30;  // medium size
    private final int BIRD_HARD_WIDTH = 34;    // largest size

    private final int BIRD_EASY_HEIGHT = 18;   // taller for better bird shape
    private final int BIRD_NORMAL_HEIGHT = 22; // medium height
    private final int BIRD_HARD_HEIGHT = 24;   // tallest


    // Mode settings 

    // Movement
    int velocityY = 0;
    int velocityX = -4;
    int gravity = 1;

    // Pipe class
    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int height = pipeHeight;
        int width = pipeWidth;
        boolean passed = false;
        Image img;

        Pipe(Image img) {
            this.img = img;
        }
    }

    // Bird class
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

    // Constructor
    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.blue);
        setFocusable(true);
        addKeyListener(this);

        // Load images
        backImg = new ImageIcon("images/bg.png").getImage();
        birdImg = new ImageIcon("images/bird.png").getImage();
        topPipeImg = new ImageIcon("images/top.png").getImage();
        bottomPipeImg = new ImageIcon("images/bottom.png").getImage();
        modeSelectionBg = new ImageIcon("images/mode.jpg").getImage();
        

        // Initialize bird and pipes
        bird = new Bird(birdImg);
        pipes = new ArrayList<Pipe>();

        // Initialize timers
        placePipesTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });

        gameLoop = new Timer(1000/60, this);
    }

    // Place pipes method
    public void placePipes() {
        int randomPipeY = (int) (pipeY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int space = pipeHeight / 4;

        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight + space;
        pipes.add(bottomPipe);
    }

    // Paint component
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    // Draw method
    public void draw(Graphics g) {
        if (currentState == GameState.MODE_SELECT) {
            g.drawImage(modeSelectionBg, 0, 0, boardWidth, boardHeight, null);
            drawModeSelect(g);
        } else {

            // Draw game elements
            g.drawImage(backImg, 0, 0, boardWidth, boardHeight, null);
            g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
            
            for (Pipe p : pipes) {
                g.drawImage(p.img, p.x, p.y, p.width, p.height, null);
            }
            
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.PLAIN, 32));
            
            if (gameOver) {
                g.drawString("Game over: " + String.valueOf((int) score), 10, 35);
            } else {
                g.drawString(String.valueOf((int) score), 10, 35);
            }
        }
    }

    // Mode selection screen
    private void drawModeSelect(Graphics g) {
        g.setColor(Color.green);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Select Mode", boardWidth/2 - 100, boardHeight/3);
        
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        int yOffset = boardHeight/2 - 50;
        
        for (GameMode mode : GameMode.values()) {
            if (mode == currentMode) {
                g.setColor(Color.green);
            } else {
                g.setColor(Color.white);
            }
            g.drawString(mode.toString(), boardWidth/2 - 50, yOffset);
            yOffset += 50;
        }
        
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.setColor(Color.white);
        g.drawString("Press UP/DOWN to select", boardWidth/2 - 100, boardHeight - 100);
        g.drawString("Press SPACE to start", boardWidth/2 - 80, boardHeight - 70);
    }

    // Move method
    public void move() {
        // Set gravity and velocity based on current mode
        int currentGravity = switch(currentMode) {
            case EASY -> EASY_GRAVITY;
            case NORMAL -> NORMAL_GRAVITY;
            case HARD -> HARD_GRAVITY;
        };
        
        velocityX = switch(currentMode) {
            case EASY -> EASY_PIPE_SPEED;
            case NORMAL -> NORMAL_PIPE_SPEED;
            case HARD -> HARD_PIPE_SPEED;
        };


        bird.width = switch(currentMode) {
              case EASY -> BIRD_EASY_WIDTH;
              case NORMAL -> BIRD_NORMAL_WIDTH;
              case HARD -> BIRD_HARD_WIDTH;
        };

        bird.height = switch(currentMode) {
            case EASY -> BIRD_EASY_HEIGHT;
            case NORMAL -> BIRD_NORMAL_HEIGHT;
            case HARD -> BIRD_HARD_HEIGHT;
      };


        
    
        
        velocityY += currentGravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);
        
        for (Pipe p : pipes) {
            p.x += velocityX;
            
            if (collision(bird, p)) {
                gameOver = true;
            }
            
            if (!p.passed && bird.x > p.x + p.width) {
                p.passed = true;
                score += 0.5;
            }
        }
        
        if (bird.y > boardHeight) {
            gameOver = true;
        }
    }

    // Collision detection
    boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width &&   //a's top left corner doesn't reach b's top right corner
               a.x + a.width > b.x &&   //a's top right corner passes b's top left corner
               a.y < b.y + b.height &&  //a's top left corner doesn't reach b's bottom left corner
               a.y + a.height > b.y;    //a's bottom left corner passes b's top left corner
    }

    // Action performed
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();

        if (gameOver) {
            gameLoop.stop();
            placePipesTimer.stop();
        }
    }

    // Key press handler
    @Override
    public void keyPressed(KeyEvent e) {
        if (currentState == GameState.MODE_SELECT) {
            // Updating modes based on arrow keys 
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP -> {
                    int ordinal = (currentMode.ordinal() - 1 + GameMode.values().length) % GameMode.values().length;
                    currentMode = GameMode.values()[ordinal];
                    repaint();
                }
                case KeyEvent.VK_DOWN -> {
                    int ordinal = (currentMode.ordinal() + 1) % GameMode.values().length;
                    currentMode = GameMode.values()[ordinal];
                    repaint();
                }
                case KeyEvent.VK_SPACE -> {
                    currentState = GameState.PLAYING;
                    startGame();
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -9;
            
            if (gameOver) {
                resetGame();
            }
        }
    }

    // Start game
    private void startGame() {
        gameLoop.start();
        placePipesTimer.start();
    }

    // Reset game
    private void resetGame() {
        bird.y = birdY;
        velocityY = 0;
        pipes.clear();
        gameOver = false;
        score = 0;
        currentState = GameState.MODE_SELECT;
        gameLoop.stop();
        placePipesTimer.stop();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
    
    // Main method to start the game
    public static void main(String[] args) {
        JFrame frame = new JFrame("Flappy Bird");
        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}