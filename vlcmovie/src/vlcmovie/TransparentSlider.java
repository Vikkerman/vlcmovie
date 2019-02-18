package vlcmovie;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class TransparentSlider extends BasicProgressBarUI {

	@Override
	protected Dimension getPreferredInnerVertical() {
		return new Dimension(20, createGUI.movieCanvas.getHeight() - 200);
	}

	@Override
	protected Dimension getPreferredInnerHorizontal() {
		return new Dimension(createGUI.movieCanvas.getWidth() - 200, 20);
	}

	@Override
	protected void paintDeterminate(Graphics g, JComponent c) {
		Graphics2D g2d = (Graphics2D) g.create();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		g2d.setBackground(new Color(0, 0, 0, 0));
		g2d.clearRect(0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight());

		// Very light background, just for mouse event listening.

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int iStrokWidth = 12;
		g2d.setStroke(new BasicStroke(iStrokWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2d.setColor(new Color(0, 0, 0, 5));
		g2d.setBackground(progressBar.getBackground());

		int width = progressBar.getWidth();
		int height = progressBar.getHeight();

		RoundRectangle2D outline = new RoundRectangle2D.Double((iStrokWidth / 2), (iStrokWidth / 2),
				width - iStrokWidth, height - iStrokWidth, height, height);

		g2d.draw(outline);

		// White border around the seeking field

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		iStrokWidth = 3;
		g2d.setStroke(new BasicStroke(iStrokWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2d.setColor(progressBar.getBackground());
		g2d.setBackground(progressBar.getBackground());

		width = progressBar.getWidth();
		height = progressBar.getHeight();

		RoundRectangle2D outline2 = new RoundRectangle2D.Double((iStrokWidth / 2), (iStrokWidth / 2),
				width - iStrokWidth, height - iStrokWidth, height, height);

		g2d.draw(outline2);

		// White seeking line

		int iInnerHeight = height - (iStrokWidth * 4);
		int iInnerWidth = width - (iStrokWidth * 4);

		double dProgress = progressBar.getPercentComplete();
		if (dProgress < 0) {
			dProgress = 0;
		} else if (dProgress > 1) {
			dProgress = 1;
		}

		iInnerWidth = (int) Math.round(iInnerWidth * dProgress);

		int x = iStrokWidth * 2;
		int y = iStrokWidth * 2;

		Point2D start = new Point2D.Double(x, y);
		Point2D end = new Point2D.Double(x, y + iInnerHeight);

		float[] dist = { 0.0f, 0.25f, 1.0f };
		Color[] colors = { progressBar.getBackground(), progressBar.getBackground().brighter(),
				progressBar.getBackground().darker() };
		LinearGradientPaint p = new LinearGradientPaint(start, end, dist, colors);

		g2d.setPaint(p);

		RoundRectangle2D fill = new RoundRectangle2D.Double(iStrokWidth * 2, iStrokWidth * 2, iInnerWidth, iInnerHeight,
				iInnerHeight, iInnerHeight);

		g2d.fill(fill);

		g2d.dispose();
	}

	@Override
	protected void paintIndeterminate(Graphics g, JComponent c) {
		super.paintIndeterminate(g, c); // To change body of generated methods, choose Tools | Templates.
	}

}