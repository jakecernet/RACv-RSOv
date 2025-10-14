/* Sestavite FX javanski program, ki bo izrisoval Kochov fraktal,. Koda naj implementira DF postopek (diskusija o teh vrstah postopkov poteka/bo pri urah teorije) : najprej naj izračuna vse potrebne točke in nato izriše zahtevan fraktal.
Izvajanje postopka na vsaki stopnji izvajanja naj poteka kot:
1.	daljico razdelimo na tri enako dolge kose,
2.	srednji kos razpolovimo in nad njim razpnemo enakostranični trikotnik,
3.	ponovimo predhodni dve točki za vsako izmed štirih dobljenih daljic.
 
v pomoč naj vam bo naslednji kos kode:

public void drawFractal (int x1, int y1, int x5, int y5,
                            Graphics g)
   {
      int deltaX, deltaY, x2, y2, x3, y3, x4, y4;

      if ( java.lang.Math.abs(x5 - x1)<7 )
         g.drawLine (x1, y1, x5, y5);
      else
      {
         deltaX = x5 - x1;  
         deltaY = y5 - y1;

         x2 = x1 + deltaX / 3;  
         y2 = y1 + deltaY / 3;  
         
        double SQ = java.lang.Math.sqrt(3.0) / 6;  // višina

         x3 = (int) ((x1+x5)/2 + SQ * (y1-y5));
         y3 = (int) ((y1+y5)/2 + SQ * (x5-x1));  

         x4 = x1 + deltaX * 2/3;  
         y4 = y1 + deltaY * 2/3;  

         drawFractal (x1, y1, x2, y2, g);
         drawFractal (x2, y2, x3, y3, g);
         drawFractal (x3, y3, x4, y4, g);
         drawFractal (x4, y4, x5, y5, g);
      }
   }

Sama realizacija programa naj bo taka, da bo omogočal izris fraktala določene stopnje. Pri tem naj bo stopnja 1 ravna črta, stopnja 2 kot je narisano na podani sliki, … Z mastnim tiskom v kodi je označen pogoj za končanje rekurzivnega postopka ( distanca med končnima točkama daljice po x-u pod 7 pik/pixlov). Tega bo potrebno spremeniti v npr. stopnja > 1 in dodati glavi metode dodaten parameter 'stopnja' ….
Kochovo snežinko sestavljajo trije Kochovi fraktali, povezani v enakostraničen trikotnik. Rezultat naloge je lahko tudi kochova snežinka izbrane stopnje. */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import java.util.Scanner;
import java.lang.Math;

public class naloga2 extends Application {
    private int stopnja = 10; // Privzeta stopnja fraktala

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        Scene scene = new Scene(pane, 800, 800);
        primaryStage.setTitle("Kochov Fraktal - Stopnja " + stopnja);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Koordinati začetne in končne točke
        int x1 = 100, y1 = 400;
        int x5 = 700, y5 = 400;

        // Risanje Kochovega fraktala
        drawFractal(x1, y1, x5, y5, pane, stopnja);
    }

    public void drawFractal(int x1, int y1, int x5, int y5, Pane pane, int stopnja) {
        if (stopnja <= 1) {
            Line line = new Line(x1, y1, x5, y5);
            line.setStroke(Color.BLACK);
            pane.getChildren().add(line);
        } else {
            int deltaX = x5 - x1;
            int deltaY = y5 - y1;

            int x2 = x1 + deltaX / 3;
            int y2 = y1 + deltaY / 3;

            double SQ = Math.sqrt(3.0) / 6; // višina

            int x3 = (int) ((x1 + x5) / 2 + SQ * (y1 - y5));
            int y3 = (int) ((y1 + y5) / 2 + SQ * (x5 - x1));

            int x4 = x1 + deltaX * 2 / 3;
            int y4 = y1 + deltaY * 2 / 3;

            drawFractal(x1, y1, x2, y2, pane, stopnja - 1);
            drawFractal(x2, y2, x3, y3, pane, stopnja - 1);
            drawFractal(x3, y3, x4, y4, pane, stopnja - 1);
            drawFractal(x4, y4, x5, y5, pane, stopnja - 1);
        }
    }
}