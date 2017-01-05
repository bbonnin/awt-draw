package io.millesabords.draw;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class DrawingPanel extends Canvas implements MouseListener, MouseMotionListener {

  private static final long serialVersionUID = 1L;

  private final List<List<Point>> lines = new LinkedList<List<Point>>();
  private final List<Color> colors = new LinkedList<Color>();

  // Attributes for double buffering
  private Graphics2D bufferGraphics;
  private Dimension bufferDim;
  private Image bufferImage;

  private final Random rand = new Random();

  public DrawingPanel() {
    addMouseListener(this);
    addMouseMotionListener(this);
  }

  public void clear() {
    lines.clear();
    colors.clear();
    repaint();
  }

  @Override
  public void update(Graphics g) {
    paint(g);
  }

  @Override
  public void paint(Graphics g) {
    final Dimension dim = getSize();

    if (bufferGraphics == null || dim.width != bufferDim.width || dim.height != bufferDim.height) {
      bufferDim = dim;
      bufferImage = createImage(getPreferredSize().width, getPreferredSize().height);
      bufferGraphics = (Graphics2D) bufferImage.getGraphics();
      bufferGraphics.setStroke(new BasicStroke(5.0f,
          BasicStroke.CAP_ROUND,
          BasicStroke.JOIN_ROUND));
      final RenderingHints hints = new RenderingHints(
          RenderingHints.KEY_ANTIALIASING,
          RenderingHints.VALUE_ANTIALIAS_ON);
      bufferGraphics.setRenderingHints(hints);
    }

    bufferGraphics.clearRect(0, 0, dim.width, dim.height);

    for (int i = 0; i < lines.size(); i++) {
      final List<Point> points = lines.get(i);
      bufferGraphics.setColor(colors.get(i));
      for (int j = 0; j < points.size() - 1; j++) {
        final Point p1 = points.get(j);
        final Point p2 = points.get(j + 1);
        bufferGraphics.drawLine(p1.x, p1.y, p2.x, p2.y);
      }
    }

    g.drawImage(bufferImage, 0, 0, this);
  }

  public void mousePressed(MouseEvent e) {
    final List<Point> points = new LinkedList<Point>();
    points.add(new Point(e.getX(), e.getY()));
    lines.add(points);
    colors.add(new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()));
  }

  public void mouseDragged(MouseEvent e) {
    lines.get(lines.size() - 1).add(new Point(e.getX(), e.getY()));
    repaint();
  }

  public void mouseReleased(MouseEvent e) {}

  public void mouseEntered(MouseEvent e) {}

  public void mouseExited(MouseEvent e) {}

  public void mouseClicked(MouseEvent e) {}

  public void mouseMoved(MouseEvent e) {}
}
