import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
 
public class CalculateRectArea {

    public static void main(String[] args) {
           
        int width = 0;
        int length = 0;
               
        try {
		    //read the length from console
		    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		   
		    System.out.println("Please enter length of a rectangle");
		    length = Integer.parseInt(br.readLine());

		    //read the width from console
		    System.out.println("Please enter width of a rectangle");
		    width = Integer.parseInt(br.readLine());             
               
        } catch(NumberFormatException ne) {
                System.out.println("Invalid value" + ne);
                System.exit(0);
        } catch(IOException ioe) {
                System.out.println("IO Error :" + ioe);
                System.exit(0);
        }
       
        /*
         * Area of a rectangle is
         * length * width
        */
       
        int area = length * width;
       
        System.out.println("Area of a rectangle is " + area);
    }
               
}
