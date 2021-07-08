package design;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

class SliderUI extends BasicSliderUI {

	private int shorter = 0;

    public SliderUI(JSlider slider) {
        super(slider);

    }

    @Override
    public void paintTrack(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Rectangle t = trackRect;
        Rectangle t2 = thumbRect;
        int tw2 = t2.width / 2;

        g2d.setColor(new Color(45,45,45));       
        g2d.fillRect(0, t.height/2, t.width+tw2-shorter+5, 3);
        g2d.setColor(Color.WHITE);  
        int xv = t2.x-t.x+10;
        if(xv <0){
        	xv=0;
        }
        g2d.fillRect(0, t.height/2, xv, 3);
       
    }

    @Override
    public void paintThumb(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        Rectangle t = thumbRect;
        g2d.setColor(Color.WHITE);
//        int tw2 = t.width / 2;
        g2d.fillRect(t.x, t.y+2, t.width-shorter, t.height-4);
    }
    
}