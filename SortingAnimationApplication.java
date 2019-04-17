import javax.swing.JFrame;
import java.awt.GridLayout;

public class SortingAnimationApplication extends JFrame
{  
  // Creating variables for SortPanel class
  private final SortPanel panelOne;
  private final SortPanel panelTwo;
  
  public SortingAnimationApplication() 
  {
    // Naming the window
    super("Sorting Animation Application");   
    //Setting the layout to gridLayout
    setLayout(new GridLayout(1, 2));   
    
    //Creating new objects
    panelOne = new SortPanel();
    panelTwo = new SortPanel();  

    //Adding the 2 panels to JFrame
    add(panelOne);
    add(panelTwo);
    
    }
  
  //Main
  public static void main(String[] args)
  {
    // Creating an object for the animationApplication
    SortingAnimationApplication animationApp = new SortingAnimationApplication();
    //Setting the default close operation
    animationApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //Setting the Width and height of the Frame
    animationApp.setSize(1000, 650);
    //Setting the application visible
    animationApp.setVisible(true);
  }
}
