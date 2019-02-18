package vlcmovie;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class CustomMouseListener implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e){}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e){
    	if (e.getSource().equals(createGUI.seekerProgressBar)) {
    		positioning(e);
    		MouseMotionTimer.resetTimer();
    	}
    }

    @Override
    public void mouseReleased(MouseEvent e){
    	if (e.getSource().equals(createGUI.seekerProgressBar)) {
    		positioning(e);
    	}
    }
    
    public void positioning(MouseEvent e) {
    	int borderWidth = 7;
		int wSeeker = createGUI.seekerProgressBar.getWidth() - (2 * borderWidth);
		int progressSet = (int) Math.round((e.getPoint().x - borderWidth)/(wSeeker/1000.0));
		createGUI.seekerProgressBar.setValue(progressSet);
		createGUI.emp.setPosition((float) progressSet / 1000);
		createGUI.seekerProgressBar.repaint();
		createGUI.moviePanel.repaint();
		createGUI.overlay.repaint();
		createGUI.bottomPanel.repaint();
    }
}
