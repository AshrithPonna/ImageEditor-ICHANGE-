//Importing various needed Java libraries.

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

//Initializing class to contain all sub-methods used later on.

public class ImageEditorPanel extends JPanel implements KeyListener {

    //Global Variables.

    //2-D array that stores image.

    Color[][] pixels;
    int imageLoc = 0;
    final int BASE_ROW = 0;
    final int ONE_SUBTRACT = 1;
    final int STORAGE_CAP = 100000;
    Color[][][] resetStorageArray = new Color[STORAGE_CAP][10][10];

    //Reads Image File.

    public ImageEditorPanel() {

        //Printing out simple starting dialogue.

        System.out.println("All Rights Reserved 'IChange' 2024 LLC" + "\n");
        System.out.println("Welcome to IChange, an image editor app that has the ability to change various constituents of an image such as:");
        System.out.println("1. Horizonal Placement.\n" + "2. Vertical Placement.\n" + "3. Image Grayscaling.\n" + "4. Image Blur.\n" + "5. Contrast Scaler." );
        System.out.println("There are many more editing filters to come. Enjoy! \n"); 

        BufferedImage imageIn = null;
        try {
            imageIn = ImageIO.read(new File("AndroidLogo.jpg"));
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
        pixels = makeColorArray(imageIn);
        setPreferredSize(new Dimension(pixels[0].length, pixels.length));
        setBackground(Color.BLACK);

        //Command to validate key types.

        addKeyListener(this);
    }

    public void paintComponent(Graphics g) {

        //Paints the array pixels onto the screen.

        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[0].length; col++) {
                g.setColor(pixels[row][col]);
                g.fillRect(col, row, 1, 1);
            }
        }
        resetStorageArray[imageLoc] = pixels;
    }

    //Method that runs all image editing methods independently.
    //Currently unused due to implementation of Key Events.

    public void run() {
    }

    //Method that flips image horizontally (Right-to-Left --> Left-to-Right)

    public Color[][] flipHorizontal(Color horizontalStart[][]){
        Color newHorizontal[][] = new Color[horizontalStart.length][horizontalStart[BASE_ROW].length];

        //For loops that iterate through all pixels in an image.

        for (int r = 0; r < horizontalStart.length; r++){
            for (int c = 0; c < horizontalStart[r].length; c++){
                newHorizontal[r][horizontalStart[r].length-c-ONE_SUBTRACT] = horizontalStart[r][c];
            }
        }
        return newHorizontal;
    }

    //Method that flips image vertically (Down-to-Up --> Up-to-Down)

    public Color[][] flipVertical(Color verticalStart[][]){
        Color newVertical[][] = new Color[verticalStart.length][verticalStart[BASE_ROW].length];

        //For loops that iterate through all pixels in an image.

        for (int r = 0; r < verticalStart.length; r++){
            for (int c = 0; c < verticalStart[r].length; c++){
                newVertical[verticalStart.length-r-ONE_SUBTRACT][c] = verticalStart[r][c];
            }
        }
        return newVertical;
    }

    //Method that shades all pixels presented in an image to a tone of gray.

    public Color[][] grayScale(Color grayStart[][]){
        int rCol = 0;
        int gCol = 0;
        int bCol = 0;
        int averageVal = 0;
        final int NUM_RGB = 3;
        Color newGray[][] = new Color [grayStart.length][grayStart[BASE_ROW].length];

        //For loops that iterate through all pixels in an image.

        for (int r = 0; r < grayStart.length; r++){
            for (int c = 0; c < grayStart[r].length; c++){
                rCol = grayStart[r][c].getRed();
                gCol = grayStart[r][c].getGreen();
                bCol = grayStart[r][c].getBlue();
                averageVal = (rCol + gCol + bCol)/NUM_RGB;
                newGray[r][c] = new Color(averageVal,averageVal,averageVal);
            }
        }
        return newGray;
    }

    //Method that applies a consistently increasing blurs effect to the image.

    public Color[][] blur(Color blurStart[][]){
        Color newBlur[][] = new Color [blurStart.length][blurStart[BASE_ROW].length];
        int blurArea = 81;
        int squareDistance = (int)Math.sqrt(blurArea)/2;
        int boxCount = 0;
        int redCol = 0;
        int greenCol = 0;
        int blueCol = 0;

        //For loops that iterate through the surrounding pixels of a center pixel of an image.

        for (int r = 0; r < blurStart.length; r++){
            for (int c = 0; c < blurStart[r].length; c++){
                for (int row = r - squareDistance; row < r + squareDistance; row++ ){
                    for (int col = c - squareDistance; col < c + squareDistance; col++){
                        if (row >= 0 && row < blurStart.length && col >=0 && col < blurStart[BASE_ROW].length){
                            redCol = redCol + blurStart[row][col].getRed();;
                            greenCol = greenCol + blurStart[row][col].getGreen();
                            blueCol = blueCol + blurStart[row][col].getBlue();
                            boxCount++;
                        }
                    }
                }
                newBlur[r][c] = new Color (redCol/boxCount,greenCol/boxCount,blueCol/boxCount);
                redCol = 0;
                greenCol = 0;
                blueCol = 0;
                boxCount = 0;
            }
        }
        return newBlur;    
    }

    //Method that increases the contrast of pixels presented in the set image respectively to RGB values.

    public Color[][] contrastChanger(Color contrastStart[][]){
        Color newContrast[][] = new Color [contrastStart.length][contrastStart[BASE_ROW].length];
        int redCol = 0;
        int greenCol = 0;
        int blueCol = 0;

        //For loops that iterate through all pixels in the set image.

        for (int r = 0; r < contrastStart.length; r++){
            for (int c = 0; c < contrastStart[BASE_ROW].length; c++){

                //Conditionals that check for respective RGB values.

                if (contrastStart[r][c].getRed() > 127){
                    redCol = 250;
                }else{
                    redCol = 5;
                }
                if (contrastStart[r][c].getGreen() > 127){
                    greenCol = 250;
                }else{
                    greenCol = 5;
                }
                if (contrastStart[r][c].getBlue() > 127){
                    blueCol = 250;
                }else{
                    blueCol = 5;
                }
                newContrast[r][c] = new Color(redCol,greenCol,blueCol);
            }
        }
        return newContrast;
    }

    //Method used to revert version of an image.

    public Color[][] resetEdit(Color currentImage[][]){
        Color beforeEdit[][] = new Color[currentImage.length][currentImage[BASE_ROW].length];

        //For loop that checks for all pixels in a set image.

        for (int r = 0; r < currentImage.length; r++){
            for (int c = 0; c < currentImage[r].length; c++){
                beforeEdit[r][c] = currentImage[r][c];
            }
        }
        return beforeEdit;
    }

    //Method that initializes color in an image.    

    public Color[][] makeColorArray(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        Color[][] result = new Color[height][width];
        
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Color c = new Color(image.getRGB(col, row), true);
                result[row][col] = c;
            }
        }
        return result;
    }

   //Unused Key Event.

    @Override
    public void keyPressed(KeyEvent e) {
    }

    //Method that checks if a key has been typed.

    @Override
    public void keyTyped(KeyEvent e) {

        //Start horizontally flipping image if true.

        if (e.getKeyChar() == 'd'){
            pixels = flipHorizontal(pixels); 
            imageLoc++;
            resetStorageArray[imageLoc] = pixels;
            repaint();

        //Start vertically flipping image if true.

        }else if (e.getKeyChar() == 'w'){
            pixels = flipVertical(pixels);
            imageLoc++;
            resetStorageArray[imageLoc] = pixels;
            repaint();

        //Start grayscaling image if true.

        }else if (e.getKeyChar() == 'g'){
            pixels = grayScale(pixels);
            imageLoc++;
            resetStorageArray[imageLoc] = pixels;
            repaint();

        //Start blurring image if true.

        }else if (e.getKeyChar() == 'b'){
            pixels = blur(pixels);
            imageLoc++;
            resetStorageArray[imageLoc] = pixels;
            repaint();

        //Start changing contrast of image if true.

        }else if (e.getKeyChar() == 'c'){
            pixels = contrastChanger(pixels);
            imageLoc++;
            resetStorageArray[imageLoc] = pixels;
            repaint();

        //Start reverting process of image to earlier versions if true.

        }else if (e.getKeyChar() == 'r'){
            imageLoc--;

            //Conditionals that check for out of bounds.

            if (imageLoc < 0){
                System.out.println("[System]: This is the base image. No additional edits can be reverted.");
                imageLoc = 0;
            }else{
                pixels = resetEdit(resetStorageArray[imageLoc]);
                repaint();
            }
        }
    }

    //Unused Key Event.

    @Override
    public void keyReleased(KeyEvent e) {
    }
}


