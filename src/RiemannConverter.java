import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Color;

public class RiemannConverter {

	public static void main(String[] args){
		
		try {
			BufferedImage img = ImageIO.read(new File("complexwarping.png"));
//			System.out.println(imageHeight);
			int imageHeight = img.getHeight();
			int imageWidth = img.getWidth();
			
			
			//makes output image
			BufferedImage transformed = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);

			//transforms each pixel
			for(int a = 0; a < imageHeight; a++){
				for(int b = 0; b < imageWidth; b++){
					
					//key:
					// (b,a) coordinates of original pixels
					// (j,i) scaled down and transformed coordinates
					// (x,y,z) points on riemann sphere
					// (finalX, finalY) rounded down versions of j and i to be used in constructing image
					
					//centers and scales down pixels
					double j = (b - (imageWidth/2.0))/100;
					double i = (a - (imageHeight/2.0))/100;
					
					//maps out all pixels to unit sphere
					double x = (2*j)/((j*j) + (i*i) + 1);
					double y = (2*i)/((i*i) + (j*j) + 1);
					double z = ((i*i) + (j*j) - 1)/((i*i) + (j*j) + 1);
					
					//puts it in complex plane
					ComplexNumber complexPoint = new ComplexNumber(((x)/(1-z)),((y)/(1-z)));
					ComplexNumber newCP = complexPoint.pow(new ComplexNumber(2,0)).multiply(new ComplexNumber(0.4,0.4));
//					ComplexNumber newCP = complexPoint.naturalExponent();
					x = newCP.toSphereX();
					y = newCP.toSphereY();
					z = newCP.toSphereZ();
//					System.out.println(x + " " + y  + " " + z);				
					// puts it back to 2d plane					
					j = (x)/(1-z);
					i = (y)/(1-z);
					
					// brings it back to its original position and scale
					j = Math.abs(((j*100) + (imageWidth/2.0))%imageWidth);
					i = Math.abs(((i*100) + (imageHeight/2.0))%imageHeight);
					//interpolation
					Color topLeft = new Color(img.getRGB( (int) Math.floor(j), (int) Math.floor(i)));
					Color topRight = new Color(img.getRGB( (int) Math.ceil(j)%imageWidth, (int) Math.floor(i)));
					Color bottomLeft = new Color(img.getRGB( (int) Math.floor(j), (int) Math.ceil(i)%imageHeight));
					Color bottomRight = new Color(img.getRGB( (int) Math.ceil(j)%imageWidth, (int) Math.ceil(i)%imageHeight));
					
					double horizontalDistance = j - Math.floor(j);
					double verticalDistance = i - Math.floor(i);
					//horizontal interpolation
					
					int topR = (int) (Math.round((topLeft.getRed() * (1 - horizontalDistance)) + (topRight.getRed() * (horizontalDistance))));
					int topG = (int) (Math.round((topLeft.getGreen() * (1 - horizontalDistance)) + (topRight.getGreen() * (horizontalDistance))));
					int topB = (int) (Math.round((topLeft.getBlue() * (1 - horizontalDistance)) + (topRight.getBlue() * (horizontalDistance))));

					int bottomR = (int) (Math.round((bottomLeft.getRed() * (1 - horizontalDistance)) + (bottomRight.getRed() * (horizontalDistance))));
					int bottomG = (int) (Math.round((bottomLeft.getGreen() * (1 - horizontalDistance)) + (bottomRight.getGreen() * (horizontalDistance))));
					int bottomB = (int) (Math.round((bottomLeft.getBlue() * (1 - horizontalDistance)) + (bottomRight.getBlue() * (horizontalDistance))));
					
					//vertical interpolation
					
					int finalR = (int) (Math.round((topR * (1 - verticalDistance)) + (bottomR * (verticalDistance))));
					int finalG = (int) (Math.round((topG * (1 - verticalDistance)) + (bottomG * (verticalDistance))));
					int finalB = (int) (Math.round((topB * (1 - verticalDistance)) + (bottomB * (verticalDistance))));
					
					Color finalColor = new Color(finalR, finalG, finalB);
					//
					int finalX = (int) Math.floorMod((Math.round(j)), imageWidth);
					int finalY = (int) Math.floorMod((Math.round(i)), imageHeight);
					//pray that everything is where it should be
					//System.out.println(finalX + " " + finalY);
					if(finalX >= 0 && finalY >= 0 && finalX < imageWidth && finalY < imageHeight){
//						transformed.setRGB(b, a, img.getRGB(finalX, finalY)); //nearest neighbor
						transformed.setRGB(b, a, finalColor.getRGB()); // bilinear interpolation
					}
					else{
//						transformed.setRGB(b, a, img.getRGB(b, a));
//						transformed.setRGB(b, a, Color.WHITE.getIntArgbPre());
					}
				}
			}
			//outputs
			File finished = new File("finished.png");
			ImageIO.write(transformed, "png", finished);
			System.out.println(finished.getAbsolutePath());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("file not found");
			e.printStackTrace();
		}
		

	}
}
