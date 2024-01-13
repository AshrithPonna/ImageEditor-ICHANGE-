//Importing associated Java libraries.

import javax.swing.JFrame;

//Class to hold sub-methods.

public class ImageEditorRunner {

  //Method creating base frame for image.

  public static void main(String[] args) {
    JFrame f = new JFrame("Photo Editor V.2.1"); 
    ImageEditorPanel p = new ImageEditorPanel();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.add(p);
    f.pack();
    f.setVisible(true);
    p.setFocusable(true);
    p.requestFocusInWindow();
    p.run();
  }
}
