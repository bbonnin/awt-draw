package io.millesabords.draw;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Draw extends Frame {

  private static final long serialVersionUID = 1L;

  public Draw() {
    super("Draw");

    setSize(500, 500);

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        dispose();
      }
    });

    setLayout(new BorderLayout());

    final DrawingPanel panel = new DrawingPanel();
    add(BorderLayout.CENTER, panel);

    final Button btn = new Button("Clear");
    btn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        panel.clear();
      }
    });
    add(BorderLayout.NORTH, btn);
  }


  public static void main(String[] args) {
    final Draw draw = new Draw();
    draw.setVisible(true);
  }

}
