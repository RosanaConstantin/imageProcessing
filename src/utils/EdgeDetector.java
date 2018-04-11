package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException; 
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
	
public class EdgeDetector {
  // truncate color component to be between 0 and 255
	public static int truncate(int a) {
		if      (a <   0) return 0;
		else if (a > 255) return 255;
		else              return a;
	}
	
	private static boolean getFileExtension(File file) {
		StopWatch timer = new StopWatch();
		timer.start();
        String fileName = file.getName();
        String extension = null;
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        	extension = fileName.substring(fileName.lastIndexOf(".")+1);
        if( extension == "bmp"){
        	timer.stop();
        	System.out.println("Time execution for getFileExtension method: " + timer.getElapsedTime() + " microsecs");
        	return true;
        } else {
        	timer.stop();
            System.out.println("Time execution for getFileExtension method: " + timer.getElapsedTime() + " microsecs");
        	return false;
        }
    }
	
	public static void main(String[] args) {
		StopWatch timer = new StopWatch();
		timer.start();
		@SuppressWarnings("unused")
		boolean isImage = false;
	    BufferedImage image = null;
	    BufferedImage finalImage = null;
	    File f = null;
	    @SuppressWarnings("unused")
		boolean isBMPExtension = false;
		String input = JOptionPane.showInputDialog(null, "Enter here the complete path of your image!");
	    JOptionPane.showMessageDialog(null,"You entered "+ input);
		f = new File(input); 
		try{
			isBMPExtension = getFileExtension(f);
			image = ImageIO.read(f);
			isImage = true;
			System.out.println("Reading complete. It is a image!");
		}catch(IOException e){
			System.out.println("Error: "+ e + " The file doesn\'t exist or isn\'t a bmp photo!");
		}
		finalImage = processImage(image);
		write(finalImage);
		timer.stop();
        System.out.println("Time execution for main function: " + timer.getElapsedTimeMillisec()+ " millisecs");
    
	}
	
	public static BufferedImage processImage(BufferedImage image){
		StopWatch timer = new StopWatch();
		timer.start();
	    int width;
	    int height;
	    
		int[][] filter1 = {
				{ -1,  0,  1 },
				{ -1,  0,  1 },
				{ -1,  0,  1 }
		};

		int[][] filter2 = {
				{  1,  1,  1 },
				{  0,  0,  0 },
				{ -1, -1, -1 }
		};
	    
		width = image.getWidth();
		height = image.getHeight();
	    BufferedImage mimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		for (int y = 1; y < height - 1; y++) {
			for (int x = 1; x < width - 1; x++) {
			
				int[][] gray = matrix.pixels(x, y, image);
				
				// apply filter
				int gray1 = pixel.getGray1(filter1, gray);
				int gray2 = pixel.getGray2(filter2, gray);
				
				int magnitude =truncate((int) Math.sqrt(gray1*gray1 + gray2*gray2));
				Color grayscale = new Color(magnitude, magnitude, magnitude);
				mimage.setRGB(x, y,grayscale.getRGB());
			}
		}
	    timer.stop();
        System.out.println("Time execution for processImage method: " + timer.getElapsedTime()/1000+ " millisecs");
		return mimage;
	}
	
	public static void write(BufferedImage image){
		StopWatch timer = new StopWatch();
		timer.start();
		File file = null;
	    @SuppressWarnings("unused")
		boolean isBMPExtension = false;
		String output = JOptionPane.showInputDialog(null, "Enter here the complete path where the processed image will be saved!");
		JOptionPane.showMessageDialog(null,"You entered "+ output);
	    file = new File(output);  //output file path
		 try{
			  isBMPExtension = getFileExtension(file);
		      ImageIO.write(image, "bmp", file);
		      System.out.println("Writing complete.");
		    }catch(IOException e){
		      System.out.println("Error: "+e);
		    }
	      timer.stop();
	      System.out.println("Time execution for write method: " +timer.getElapsedTimeMillisec()+ " millisecs");
	}
}


