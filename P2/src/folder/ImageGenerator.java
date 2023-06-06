package folder;

import java.awt.Color;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.util.*;
import java.awt.Point;
import java.awt.geom.Ellipse2D;


import folder.Element.EColor;

public class ImageGenerator {
    int minX = Integer.MAX_VALUE;
    int minY = Integer.MAX_VALUE;
    int maxX = Integer.MIN_VALUE;
    int maxY = Integer.MIN_VALUE;

    private void setLowestCoords(List<Element2d> list)
    {
        // Loop through the list of elements
        for (Element2d element : list) {
            // Check if the x value is lower than the current minimum x
            if (element.x < minX) {
                minX = element.x;
            }
            // Check if the x value is higher than the current maximum x
            if (element.x > maxX) {
                maxX = element.x;
            }
            // Check if the y value is lower than the current minimum y
            if (element.y < minY) {
                minY = element.y;
            }
            // Check if the y value is higher than the current maximum y
            if (element.y > maxY) {
                maxY = element.y;
            }
        }
    }
    
    public int numOfOverlaps(List<Element2d> list, Element2d current)
    {
        int overlaps = 0;

        for(Element2d _current : list)
        {
            if(current == _current)
            {
                break;
            }

            if(_current.x == current.x && _current.y == current.y)
            {
                overlaps++;
            }
        }

        return overlaps;
    }

    public BufferedImage createImage(List<Element2d> elements, double minX, double minY, double maxX, double maxY, int numOverlaps, int numContacts, double fitness) {
        // Calculate the size of each grid slot and the margin
        int cellSize = 64;
        int marginSize = 32;
        int cellOverlayOffset = 8;
        int labelOffset = 8; // Offset for label positioning
    
        // Calculate the width and height of the image based on the grid layout
        int imageWidth = (int) Math.ceil((maxX - minX + 1) * cellSize + (maxX - minX) * marginSize) + 50;
        int imageHeight = (int) Math.ceil((maxY - minY + 1) * cellSize + (maxY - minY) * marginSize) + 150; // Increase height by 200 pixels
    
        // Create a BufferedImage object with the calculated width and height
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
    
        // Create a Graphics2D object from the BufferedImage to draw on it
        Graphics2D g2d = image.createGraphics();
    
        // Set the background color of the image
        g2d.setBackground(Color.WHITE);
        g2d.clearRect(0, 0, imageWidth, imageHeight);
    
        // Set the drawing color for cells, lines, and text
        g2d.setColor(Color.BLACK);
    
        // Draw the first line of text in red color
        g2d.setColor(Color.RED);
        g2d.drawString("Overlaps: "+numOverlaps, 10, imageHeight-30);
    
        // Draw the second line of text in red color
        g2d.drawString("Contacts: "+numContacts, 10, imageHeight-15);

        // Draw the average fitness
        g2d.drawString("Fitness: "+ fitness, 10, imageHeight);
    
        // Reset the drawing color back to black
        g2d.setColor(Color.BLACK);
    
        // Loop through the elements and draw connection lines
        for (int i = 0; i < elements.size() - 1; i++) {
            Element2d element = elements.get(i);
            Element2d nextElement = elements.get(i + 1);
    
            // Calculate the x and y coordinates for the current element and the next element
            int x = (int) Math.round((element.x - minX) * (cellSize + marginSize));
            int y = (int) Math.round((element.y - minY) * (cellSize + marginSize));
            int nextX = (int) Math.round((nextElement.x - minX) * (cellSize + marginSize));
            int nextY = (int) Math.round((nextElement.y - minY) * (cellSize + marginSize));
    
            // Calculate the center coordinates of the current cell and the next cell
            int centerX = x + cellSize / 2;
            int centerY = y + cellSize / 2;
            int nextCenterX = nextX + cellSize / 2;
            int nextCenterY = nextY + cellSize / 2;
    
            // Draw a line from the current cell center to the next cell center
            g2d.drawLine(centerX, centerY, nextCenterX, nextCenterY);
        }
    
        // Loop through the elements and draw cells
        for (int i = 0; i < elements.size(); i++) {
            Element2d element = elements.get(i);
    
            // Calculate the x and y coordinates for the element on the grid
            int x = (int) Math.round((element.x - minX) * (cellSize + marginSize));
            int y = (int) Math.round((element.y - minY) * (cellSize + marginSize));

            // offset when there are overlaps
            x = x+cellOverlayOffset*numOfOverlaps(elements, element);
            y = y+cellOverlayOffset*numOfOverlaps(elements, element);
    
            // Calculate the center coordinates of the cell
            int centerX = x + cellSize / 2;
            int centerY = y + cellSize / 2;
    
            // Determine the color of the cell based on the element's color value
            Color cellColor = element.color == EColor.BLACK ? Color.BLACK : Color.WHITE;
    
            
            g2d.setColor(Color.BLACK);
            g2d.fillRect(x , y , cellSize , cellSize );
            // Draw the filled cell square with a 2-pixel margin
            g2d.setColor(cellColor);
            g2d.fillRect(x + 2, y + 2, cellSize - 4, cellSize - 4);

        }
    
        for (int i = 0; i < elements.size(); i++) {
            Element2d element = elements.get(i);

             // Calculate the x and y coordinates for the element on the grid
             int x = (int) Math.round((element.x - minX) * (cellSize + marginSize));
             int y = (int) Math.round((element.y - minY) * (cellSize + marginSize));

            // Draw the label with the element count in white color
            g2d.setColor(Color.RED);

            int overlaps = numOfOverlaps(elements, element)+1;

            int labelX = x + labelOffset*overlaps;
            int labelY = y + cellSize - labelOffset;
            g2d.drawString(String.valueOf(i) + ",", labelX, labelY);
    
        }

        // Dispose the Graphics2D object to release resources
        g2d.dispose();
    
        return image;
    }

    public void saveImageToPNG(BufferedImage image, String fileName) {
        try {
            File outputFile = new File(fileName);
            ImageIO.write(image, "PNG", outputFile);
            System.out.println("Image saved successfully: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error saving image: " + e.getMessage());
        }
    }

    public ImageGenerator(Folder folder,String filename )
    {
        // Get the element 2d list
        List<Element2d> list = folder.convertToElement2dList(folder.getHead());
        setLowestCoords(list); // Get the boundaries

        folder.calcFitnessAndOverlaps(false);
        BufferedImage bi = createImage(list, minX, minY, maxX, maxY, folder.getOverlaps(), folder.getContacts(), folder.getFitness());

        saveImageToPNG(bi, filename);
    }
}
