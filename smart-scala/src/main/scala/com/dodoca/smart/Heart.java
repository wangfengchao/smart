package com.dodoca.smart;

import javax.swing.*;
import java.awt.*;

/**
 * Created by  fc.w on 2017/2/14.
 */
public class Heart extends JFrame {

    private static final int WIN_WIDTH = 520;
    private static final int WIN_HEIGHT = 520;

    private static Dimension dimensionSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static int windowWidth = dimensionSize.width;
    private static int windowHeight = dimensionSize.height;

    public Heart() {
        super("❤ 有美一人，清扬婉兮。");
        this.setBackground(Color.BLACK);
        this.setLocation((windowWidth - WIN_WIDTH) / 2, (windowHeight - WIN_HEIGHT) / 2);
        this.setSize(WIN_WIDTH, WIN_HEIGHT);
        this.setLayout(getLayout());
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @Override
    public void paint(Graphics g) {
        //横纵坐标及半径
        double x, y, r;
        Image image = this.createImage(WIN_WIDTH, WIN_HEIGHT);
        Graphics pic = image.getGraphics();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                r = Math.PI / 45 + Math.PI / 45 * i * (1 - Math.sin(Math.PI / 45 * j)) * 18;
                x = r * Math.cos(Math.PI / 45 * j) * Math.sin(Math.PI / 45 * i) + WIN_WIDTH / 2;
                y = -r * Math.sin(Math.PI / 45 * j) + WIN_HEIGHT / 2;
                pic.setColor(Color.MAGENTA);
                pic.fillOval((int) x, (int) y, 2, 2);
            }
            g.drawImage(image, 0, 0, this);
        }
    }


    public static void main(String[] args) {
        new Heart();
    }


}
