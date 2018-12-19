/*********************************************************************************************************                                                                                                       
 *  CSCI 473/504       Java Final Project         Fall 2018                                              *                                           
 *                                                                                                       *
 *  Programmer's Name : Sandeep Alla (z1821331)                                                          *
 *                                                                                                       *
 *  Date Due  : December 10th, 2018      File : SortPanel.java                                           *
 *                                                                                                       *   
 *                                                                                                       *
 *                                                                                                       *
 *********************************************************************************************************/
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class SortPanel extends JPanel
{
    //Required panel components and variables
    private final JButton populateArrayBtn;
    private final JButton sortBtn;
    private final JButton threadPR;
    private final JLabel initialLabel;
    private final JLabel sortDirectionLB;
    private final JLabel sleepLB;
    private final JComboBox<String> sortSelectionCB;
    private final JComboBox<String> selectSleepTimeCB;
    private final JComboBox<String> sortDirectionCB;
    private final JComboBox<String> initialOrderings;
    private final JButton stopBtn;
    private final SortAnimationPanel animationPanel;
    private static final int width = 480;
    private static final int height = 510;
    private static final String[] sortingTechniques = {"Heap Sort","Selection Sort","Insertion Sort"};
    private static final String[] sleepTime = {"Slow", "Medium", "Fast"};
    private static final String[] initialOrder = {"Random", "Ascending", "Descending"};
    private static final String[] sortDirec = {"Ascending","Descending"};
    private int[] randomIntegers;
    private String selectedSort;
    private Thread thread;
    private String selectedSleepTime;
    private boolean isThreadStart = false;
    
    //Constructor
    public SortPanel()
    {        
        //Creating a new SortAnimationPanel and adding it to the JPanel
        animationPanel = new SortAnimationPanel();
        //Width and height of the Panel
        animationPanel.setPreferredSize(new Dimension(width,height));
        add(animationPanel);
        
        //Creating a Label and adding it to the panel
        initialLabel = new JLabel("Initial Order:");
        add(initialLabel);
        
        //Creating ComboBox for initial order and adding it to the JPanel
        initialOrderings = new JComboBox<String>(initialOrder);
        add(initialOrderings);
        
        //Creating a button to populate array and adding it to JPanel
        populateArrayBtn = new JButton("Populate Array");        
        add(populateArrayBtn);
        
        //Creating a button to sort
        sortBtn = new JButton("Sort");       
        add(sortBtn);
        //Disabling the sort button
        sortBtn.setEnabled(false);
        
        //Creating a Combobox for selecting the type of sort
        sortSelectionCB = new JComboBox<String>(sortingTechniques);        
        add(sortSelectionCB);       
        
        //Creating a Label and adding it to the panel
        sleepLB = new JLabel("Sleep Time:");
        add(sleepLB);
        
        //Creating a combobox to select the sleep time
        selectSleepTimeCB = new JComboBox<String>(sleepTime);
        add(selectSleepTimeCB);
        
        //Creating a Label and adding it to the panel
        sortDirectionLB = new JLabel("Sort Direction:");
        add(sortDirectionLB);
        
        //Creating a Combo box to select the direction of the sort
        sortDirectionCB = new JComboBox<String>(sortDirec);
        add(sortDirectionCB);
        
        //Creating a button to pause or resume the sort operation
        threadPR = new JButton("Pause");
        add(threadPR);
        
        //Creating a button to stop the sort operation in the middle
        stopBtn = new JButton("Stop");
        add(stopBtn);
        
        //Disabling the pause button
        threadPR.setEnabled(false);
        
        //Creating event handlers for the Panel components
        EventHandler handler = new EventHandler();
        populateArrayBtn.addActionListener(handler);
        sortBtn.addActionListener(handler);
        sortSelectionCB.addActionListener(handler);
        threadPR.addActionListener(handler);
        sortDirectionCB.addActionListener(handler);
        stopBtn.addActionListener(handler);
        
    }
    
    //Event Handler class
    private class EventHandler implements ActionListener
    {
      @Override
      public void actionPerformed(ActionEvent event)
      {
          //If the user clicks on populatearray button
         if (event.getSource() == populateArrayBtn)
         {
             
             //Initializing the randomintegers array length equal to the width of the panel
            randomIntegers = new int[width];
            int max = height;
            int min = 1;
            //Loops through the random integers array and places a random number in each and every index
            for(int i = 0; i < randomIntegers.length; i++)
            {
                Random random = new Random();
                randomIntegers[i] = random.nextInt(max + 1 - min) + min;
            }
            
            //If the user select the initial order to be in ascending, applying sort
            if((String)initialOrderings.getSelectedItem() == "Ascending")
            {
                Arrays.sort(randomIntegers);
            }
            else if((String)initialOrderings.getSelectedItem() == "Descending")
            {
                //If the user select the initial order to be descending the sort the array and reverse it
                Arrays.sort(randomIntegers);
                for(int i = 0; i < randomIntegers.length / 2; i++)
                {
                    int temp = randomIntegers[i];
                    randomIntegers[i] = randomIntegers[randomIntegers.length - i - 1];
                    randomIntegers[randomIntegers.length - i - 1] = temp;
                }
            }            
            
            //Calling the repaint()
            animationPanel.repaint();
            //Disabling the populate array button
            populateArrayBtn.setEnabled(false);
            //Enabling the sort button
            sortBtn.setEnabled(true);
         }
            
            
        //If the user selects sort button
        if (event.getSource() == sortBtn)
        {
            sortBtn.setEnabled(false);
            //Calling the start method 
            animationPanel.start();
            threadPR.setEnabled(true);
            //Setting the isThreadStart as true
            isThreadStart = true;                          
        }
                      
        //If the user selects Pause or resume button
        if (event.getSource() == threadPR)
        {
            //If the text on the button is pause
            if("Pause".equals(threadPR.getText()))
            {   
                //Calling the suspend method of the thread
                thread.suspend();              
                //Changing the text on the button to Resume
                threadPR.setText("Resume");
            }else if("Resume".equals(threadPR.getText()))
            {
                //If the text on the button is Resume
                //Call the resume method of the thread
                thread.resume();            
                //Changing the text on the button to pause
                threadPR.setText("Pause");
            }         
        }
        
        //If the user selects the Stop button
        if(event.getSource() == stopBtn)
        {
            //If the thread is started then call the interrupt method to stop the thread
            if(isThreadStart)
                thread.interrupt();
            
            //Disabling the pause/resume button
            threadPR.setEnabled(false);    
            //enable the populate array button
            populateArrayBtn.setEnabled(true);
        }            
         
      }
    }

    //SortAnimationPanel class extending JPanel and implements Runnable
    private class SortAnimationPanel extends JPanel implements Runnable
    {
        private int sTime = 0;
        
        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            //Setting the background of the panel to white
            setBackground(Color.WHITE);
            //Setting the color of the lines to blue
            g.setColor(Color.BLUE);
                       
            //If the array is not null and length > 0
            if(randomIntegers != null && randomIntegers.length > 0)
            {
                //To sort in Ascending direction else Descending
                if((String)sortDirectionCB.getSelectedItem() == "Ascending")
                {
                    for(int i = 0; i < randomIntegers.length; i++)
                    {                
                        g.drawLine(i, height, i, height-randomIntegers[i]);                                     
                    }
                }
                else 
                {
                    for(int i = 0; i < randomIntegers.length; i++)
                    {                
                        g.drawLine(i, height, i, randomIntegers[i]);                                    
                    }
                }
            }      
            
        }
        
        //Method to create a new thread
        public void start()
        {
            thread = new Thread(this, "Thread1");
            thread.start();
        }
        
        @Override
        public void run()
        {
            selectedSort = (String)sortSelectionCB.getSelectedItem();
            selectedSleepTime = (String)selectSleepTimeCB.getSelectedItem();
            
            //Setting the sTime to the time selected by the user 
            if(null != selectedSleepTime)
            {
                switch (selectedSleepTime) 
                {
                    case "Slow":
                        sTime = 5000;
                        break;
                    case "Medium":
                        sTime = 1000;
                        break;
                    case "Fast":
                        sTime = 100;
                        break;
                    default:
                        break;
                }
            }
            
            //Depending the type of sort selected calling that particular sort
            if(null != selectedSort)
            {
                switch (selectedSort) 
                {
                    case "Selection Sort":
                        try {
                            SelectionSort selectionSort = new SelectionSort();
                            selectionSort.sort(randomIntegers,sTime);                        
                        }
                        catch (InterruptedException e){                    
                        }
                        break;
                    case "Heap Sort":
                        try{
                            HeapSort heapSort = new HeapSort(); 
                            heapSort.sort(randomIntegers, sTime);
                        }
                        catch (InterruptedException e){                    
                        }
                        break;
                    case "Insertion Sort":
                        try
                        {
                            InsertionSort insertionSort = new InsertionSort();
                            insertionSort.sort(randomIntegers,sTime);
                        }
                        catch (InterruptedException e){                    
                        }
                        break;
                    default:
                        break;
                }
            }          
        }
        
        //SelectionSort Class
        public class SelectionSort
        {            
            public void sort(int[] data, int sTime) throws InterruptedException
            {
                int n = data.length; 
  
                // One by one move boundary of unsorted subarray 
                for (int i = 0; i < n-1; i++) 
                {    
                     // Find the minimum element in unsorted array 
                    int min_idx = i; 
                    for (int j = i+1; j < n; j++) 
                    {
                        if (data[j] < data[min_idx]) 
                             min_idx = j; 
                    
                    }
                    // Swap the found minimum element with the first 
                    // element 
                    int temp = data[min_idx]; 
                    data[min_idx] = data[i]; 
                    data[i] = temp; 
                    repaint();
                    thread.sleep(sTime);               
                } 
                populateArrayBtn.setEnabled(true);
            }
            
        }
        
        //HeapSort class
        public class HeapSort 
        { 
            public void sort(int arr[], int sTime) throws InterruptedException
            { 
                int n = arr.length; 
  
                // Build heap (rearrange array) 
                for (int i = n / 2 - 1; i >= 0; i--) 
                        heapify(arr, n, i, sTime); 
  
                // One by one extract an element from heap 
                for (int i=n-1; i>=0; i--) 
                { 
                    // Move current root to end 
                    int temp = arr[0]; 
                    arr[0] = arr[i]; 
                    arr[i] = temp; 
  
                    // call max heapify on the reduced heap 
                    heapify(arr, i, 0, sTime); 
                }       
            } 
  
            // To heapify a subtree rooted with node i which is 
            // an index in arr[]. n is size of heap 
            void heapify(int arr[], int n, int i, int sTime) throws InterruptedException
            { 
                int largest = i; // Initialize largest as root 
                int l = 2*i + 1; // left = 2*i + 1 
                int r = 2*i + 2; // right = 2*i + 2 
  
                // If left child is larger than root 
                if (l < n && arr[l] > arr[largest]) 
                    largest = l; 
  
                // If right child is larger than largest so far 
                if (r < n && arr[r] > arr[largest]) 
                    largest = r; 
  
                // If largest is not root 
                if (largest != i) 
                { 
                    int swap = arr[i]; 
                    arr[i] = arr[largest]; 
                    arr[largest] = swap; 
  
                    repaint();
                    //Setting the sleep
                    thread.sleep(sTime);
                    // Recursively heapify the affected sub-tree 
                    heapify(arr, n, largest, sTime); 
                } 
            }   
        }
        
        //InsertionSort class
        public class InsertionSort
        {
            public void sort(int[] data, int sTime) throws InterruptedException
            {
                int n = data.length; 
                for (int i=1; i<n; ++i) 
                { 
                    int key = data[i]; 
                    int j = i-1;   
           
                    while (j>=0 && data[j] > key) 
                    { 
                         data[j+1] = data[j]; 
                        j = j-1; 
                        
                        
                    } 
                    data[j+1] = key; 
                    repaint();
                    //Setting the sleep
                    thread.sleep(sTime);
                } 
                populateArrayBtn.setEnabled(true);
            }
        }
    }
}
      
    

