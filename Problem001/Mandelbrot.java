import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

//Julia Sieruta

public class Mandelbrot 
{
	public static int mandelbrot(Complex c, int maxIter) 
	{
        Complex z = new Complex(0, 0);
        int n = 0;
        while (z.abs() <= 2 && n < maxIter) 
        {
            z = z.multiply(z).add(c);
            n++;
        }
        return n;
    }
	public static void generateImage(int width, int height, double reMin, double reMax, double imMin, double imMax, int maxIter)
	{
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) 
        {
            for (int y = 0; y < height; y++) 
            {
                double realPart = reMin + (x / (double) width) * (reMax - reMin);
                double imagPart = imMin + (y / (double) height) * (imMax - imMin);
                
                Complex c = new Complex(realPart, imagPart);

                int iterations = mandelbrot(c, maxIter);               
                
                if (iterations == maxIter) 
                {
                    image.setRGB(x, y, Color.BLACK.getRGB());
                } 
                else 
                {
                    float hue = (float) iterations / maxIter;
                    image.setRGB(x, y, Color.getHSBColor(hue, 1, 1).getRGB());
                }
            }
        }

        try 
        {
            File output = new File("C:\\Users\\julia\\OneDrive\\Pulpit\\mandel.png");
            ImageIO.write(image, "PNG", output);
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
		
	}
	
	public static double avgTime(int numIter, int width, int height, double reMin, double reMax, double imMin, double imMax, int maxIter)
	{
		long totalTime = 0;

		for(int i=0; i< numIter; i++)
		{
			long startTime = System.nanoTime();
			generateImage(width, height, reMin, reMax, imMin, imMax, maxIter);
			long endTime = System.nanoTime();
			
			totalTime += (endTime - startTime);
		}
		return totalTime/numIter/1000000000.0;
	}
	
	public static void plot(int numIter, double reMin, double reMax, double imMin, double imMax, int maxIter)
	{
		List<Integer> sizeList = Arrays.asList(32, 64, 128, 256, 512, 1024, 2048, 4096, 8192); 
		ArrayList<Double> average = new ArrayList<Double>();
		for (Integer i : sizeList) 
		{
			average.add(avgTime(numIter, i, i, reMin, reMax, imMin, imMax, maxIter));
		}

		String fileName = "C:\\Users\\julia\\OneDrive\\Pulpit\\Codes\\kodyy\\avg.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) 
        {
         
            for (int i = 0; i < sizeList.size(); i++) 
            {
                if (i < sizeList.size()) 
                {
                    writer.write(String.valueOf(sizeList.get(i)*sizeList.get(i)));
                }
                writer.write("\t");
                if (i < average.size()) 
                {
                    writer.write(String.valueOf(average.get(i)));
                }
                writer.newLine(); 
            }
        } catch (IOException e) 
        {
            e.printStackTrace();
        }

        System.out.println("Plik zapisany");
	}
	
	
	public static void main(String[] args) 
	{
		int width = 8192; 
        int height = 8192; 
        int maxIter = 200;

        double reMin = -2.1;
        double reMax = 0.6;
        double imMin = -1.2;
        double imMax = 1.2;
        
        //generateImage(width, height, reMin, reMax, imMin, imMax, maxIter);
        //System.out.println(avgTime(1, width, height, reMin, reMax, imMin, imMax, maxIter));
        plot(5, reMin, reMax, imMin, imMax, maxIter);
	}

}
