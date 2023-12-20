# sorting-algorithms-visualizer-java
This is a simple java .classpath project that visualizes sorting algorithms using Java AWT and Java Swing libraries. 
Each algorithm that is shown right below is portrated as a diagram that can run the algorithm on a random array and compare the algorithms efficiency and time consumed.
Results are saved to a MySql database and provided visualy through a GUI statistics section for better comparison. 

This application was made to help developers understand better sorting algorithms and their functionalities through a visualizer that lets the developers also see the time-space complexity and compare the algorithms. It helps developers decide when to use which algorith comparing their time space complexity.

Algorithms used: 
🔶 Quick Sort
🔶 Bubble Sort
🔶 Heap Sort
🔶 Merge Sort
🔶 Insertion Sort
🔶 Selection Sort
🔶 Shell Sort

**GUI : Java AWT**

GUI Demo: 
![image](https://github.com/SheLearningCode/sorting-algorithms-visualizer-java/assets/91334629/33c6138f-1fd0-48fc-9dc3-0ecaec9a87b7)


The user starts the program either through the .jar file or through the Eclipse IDE. 
Now the user interface opens, which shows the following:

💠On the left side, there are four checkboxes, which are checked by default.
💠Below each checkbox, there is a dropdown menu that allows the user to select the algorithm to be displayed.
💠Below that, there is a slider that determines the speed at which the algorithms run.
💠The user can choose the size of the array to be sorted through an input field.
💠At the bottom, there are the buttons "Start", "Stop", and "New".
💠On the right side, there are four fields, each displaying a visualization of an algorithm. The array to be sorted is represented by vertical bars, which correspond to the values in the array.
💠Each field displays the name of the selected algorithm, the size of the array, the runtime, and the adjusted runtime (the actual runtime of the algorithm without interruptions for visualization).
💠The user has the option to activate or deactivate the fields using the checkboxes.
💠The dropdown menus allow the user to select the sorting algorithms to be displayed.
💠When the user clicks the "Start" button, the algorithms start sorting.
💠Clicking the "Stop" button interrupts the algorithm. If the "Start" button is clicked again, the algorithm resumes from where it was interrupted.
💠The user can fill a sorted array with new random values by clicking the "New" button.


UML Diagrams: 
![image](https://github.com/SheLearningCode/sorting-algorithms-visualizer-java/assets/91334629/1f1d6c2a-d540-404c-8c3e-8fb263dcb101)




