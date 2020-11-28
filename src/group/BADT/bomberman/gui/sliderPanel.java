package group.BADT.bomberman.gui;

import javax.swing.*;
import java.awt.*;

public class sliderPanel extends JPanel {
    private JSlider slider;
    public sliderPanel () {
        setLayout(new GridLayout());
        slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 25);
        slider.setMinorTickSpacing(5);
        slider.setMajorTickSpacing(20);

        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        add(slider);
        setPreferredSize(new Dimension(0, 40));
    }
}
