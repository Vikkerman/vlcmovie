package mpfk.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import mpfk.createGUI;

public class CustomMouseListener implements MouseListener {
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource().equals(createGUI.overlay.seekerBar) && SwingUtilities.isLeftMouseButton(e)) {
			positioning(e);
			MouseMotionTimer.resetTimer();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getSource().equals(createGUI.overlay.seekerBar) && SwingUtilities.isLeftMouseButton(e)) {
			positioning(e);
		}
	}

	public void positioning(MouseEvent e) {
		int borderWidth = 7;
		int wSeeker = createGUI.overlay.seekerBar.getWidth() - (2 * borderWidth);
		int progressSet = (int) Math.round((e.getPoint().x - borderWidth) / (wSeeker / 1000.0));
		createGUI.overlay.seekerBar.setValue(progressSet);
		createGUI.emp.controls().setPosition((float) progressSet / 1000);
		createGUI.overlay.seekerBar.repaint();
		createGUI.moviePanel.repaint();
		createGUI.overlay.repaint();
		createGUI.overlay.repaintBottomPanel();
	}
}
