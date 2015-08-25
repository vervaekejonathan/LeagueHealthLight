/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leaguehealth;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 *
 * @author fest
 */
public class LeagueHealth {

    //private String imageName = "screencapture.jpg";
    private static String imageName = "health.jpg";

    private static void CaptureScreen() {
        try {
            Robot robot = new Robot();
            String format = "jpg";

            try {
                Thread.sleep(5000);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle captureRect = new Rectangle(776, 1056, 274, 1);
            BufferedImage screenFullImage = robot.createScreenCapture(captureRect);
            ImageIO.write(screenFullImage, format, new File(imageName));

        } catch (AWTException | IOException ex) {
            System.err.println(ex);
        }
    }

    public static int CalculateHealth() {
        int counter = 0;
        int total = 0;
        try {
            File file = new File(imageName);
            BufferedImage mapa = ImageIO.read(file);

            final int xmin = mapa.getMinX();
            final int ymin = mapa.getMinY();

            final int ymax = ymin + mapa.getHeight();
            final int xmax = xmin + mapa.getWidth();

            for (int i = xmin; i < xmax; i++) {
                for (int j = ymin; j < ymax; j++) {
                    int pixel = mapa.getRGB(i, j) & 0x00FFFFFF;
                    String hexPixel = "#" + Integer.toHexString(pixel);
                    Color color = Color.decode(hexPixel);

                    if (color.getGreen() < 50) {
                        counter++;
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
        if (counter > 255) {
            counter = 255;
        }
        return counter;
    }

    public static void main(String[] args) {
        try {

            SerialTest arduino = new SerialTest();
            arduino.Test();
            while (true) {
                CaptureScreen();
                int health = CalculateHealth();
                System.out.println(255 - health + "/" + 255);
                arduino.WriteData(255 - health);
                try {
                    Thread.sleep(1000);                 //1000 milliseconds is one second.
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
