package mpfk.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import mpfk.createGUI;

/**
 * Coloring JMenuBar
 * @author Vikker
 *
 */
public class ColoredMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	Color bgColor = Color.WHITE;
	private JMenu settingsMenu = null;
	private JMenuItem settingsMi = null;
	private JMenu exitMenu = null;
	private JMenuItem exitMi = null;
	private ColoredMenu closeXButton;
//	private final String MENUBARCOLORINACTIVE = "#63B2B6";
    private final String MENUBARCOLORACTIVE = "#228388";
	private final String CLOSEBUTTONACTIVE = "#e04343";
    private final String CLOSEBUTTONINACTIVE = "#c75050";

    public void setColor(String color) {
        bgColor = hex2Rgb(color);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(bgColor);
        g2d.fillRect(0, 0, getWidth() - 1, getHeight() - 1);

    }
    
    public static Color hex2Rgb(String colorStr) {
		return new Color(Integer.valueOf(colorStr.substring(1, 3), 16), Integer.valueOf(colorStr.substring(3, 5), 16), Integer.valueOf(colorStr.substring(5, 7), 16));
	}

	public void addSettingsMenu() {
    	settingsMenu = new JMenu("Settings");
		settingsMenu.setForeground(Color.WHITE);
		settingsMi = new JMenuItem("Settings");
		settingsMi.setMnemonic(KeyEvent.VK_S);
		settingsMi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		settingsMi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	createGUI.reloadMoviList();
            }
	    });
		settingsMenu.add(settingsMi);
		add(settingsMenu);
    }

	public void addExitMenu() {
		exitMenu = new JMenu("Exit");
		exitMenu.setForeground(Color.WHITE);
		exitMi = new JMenuItem("Exit");
		exitMi.setMnemonic(KeyEvent.VK_E);
		exitMi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
		exitMi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.exit(0);
            }
	    });
		exitMenu.add(exitMi);
		add(exitMenu);
	}
	
	public void addCloseXButton() {
		addCloseXButton(null);
	}

	public void addCloseXButton(JDialog parent) {
		closeXButton = new ColoredMenu("Exit");
		closeXButton.setForeground(Color.WHITE);
		closeXButton.setColor(hex2Rgb(CLOSEBUTTONINACTIVE));
		closeXButton.addMouseListener(new MouseListener() {
			boolean isSelected = false;

			@Override
			public void mouseClicked(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {
				((ColoredMenu) e.getSource()).setColor(hex2Rgb(CLOSEBUTTONACTIVE));
				isSelected = true;
			}

			@Override
			public void mouseExited(MouseEvent e) {
				((ColoredMenu) e.getSource()).setColor(hex2Rgb(CLOSEBUTTONINACTIVE));
				isSelected = false;
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (isSelected) {
					if (parent != null) {
						createGUI.menuBar.setColor(MENUBARCOLORACTIVE);
						parent.dispose();
					} else {
						System.exit(0);
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {}
			
		});
		add(closeXButton);
	}
}