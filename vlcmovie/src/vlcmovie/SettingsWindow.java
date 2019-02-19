package vlcmovie;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.Dialog;

import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class SettingsWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static JDialog settingsDialog;
	
	private static int activeTabIndex = 0;
	
	private final static String PATHSETTINGSPANEL = "Structure";
	private final static int EXTRAWINDOWWIDTH = 100;
    
    public static String mainDir = new LoadSettings().getSettings("mainDir");
	public static String movieDir = new LoadSettings().getSettings("movieDir");
	
	private static JTabbedPane tabbedPane = new JTabbedPane();
	private static JPanel settingsCard = new JPanel() {
	private static final long serialVersionUID = 1L;
		public Dimension getPreferredSize() {
            Dimension size = super.getPreferredSize();
            size.width += EXTRAWINDOWWIDTH;
            return size;
        }
    };

    private final String MENUBARCOLORINACTIVE = "#63B2B6";
    private final String MENUBARCOLORACTIVE = "#228388";
    private final String CLOSEBUTTONACTIVE = "#e04343";
    private final String CLOSEBUTTONINACTIVE = "#c75050";
    
    
    private JButton mainButton, tempButton;
    private JTextField mainTextField, tempTextField;
    private JFileChooser chooser;
	private ColoredMenuBar menuBar;
	private ColoredMenu closeMenu;
	
	static Point pointS = new Point();
    
	public SettingsWindow(int aIndex) {
		activeTabIndex = aIndex;
		
		settingsDialog = new JDialog(createGUI.frame, "Settings");
		settingsDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		createGUI.menuBar.setColor(createGUI.hex2Rgb(MENUBARCOLORINACTIVE));
		
		try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    }catch(Exception ex) {
	        ex.printStackTrace();
	    }
        
		createSettingsPanel();
 
        tabbedPane.addTab(PATHSETTINGSPANEL, settingsCard);
        tabbedPane.setSelectedIndex(activeTabIndex);
        
        menuBar = new ColoredMenuBar();
		menuBar.setColor(createGUI.hex2Rgb(MENUBARCOLORACTIVE));
		
		closeMenu = new ColoredMenu("Exit");
		closeMenu.setForeground(Color.WHITE);
		closeMenu.setColor(createGUI.hex2Rgb(CLOSEBUTTONINACTIVE));
		closeMenu.addMouseListener(new MouseListener() {
			boolean isSelected = false;

			@Override
			public void mouseClicked(MouseEvent arg0) {}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				closeMenu.setColor(createGUI.hex2Rgb(CLOSEBUTTONACTIVE));
				isSelected = true;
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				closeMenu.setColor(createGUI.hex2Rgb(CLOSEBUTTONINACTIVE));
				isSelected = false;
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				if (isSelected) {
					createGUI.menuBar.setColor(createGUI.hex2Rgb(MENUBARCOLORACTIVE));
					settingsDialog.dispose();
				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
		});

		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(closeMenu);
		
		settingsDialog.setJMenuBar(menuBar);
		settingsDialog.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        settingsDialog.setUndecorated(true);
        settingsDialog.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
		    	pointS.x = e.getX();
		    	pointS.y = e.getY();
			}
		});
        settingsDialog.addMouseMotionListener(new MouseMotionAdapter() {
	    	public void mouseDragged(MouseEvent e) {
	    		Point p = settingsDialog.getLocation();
	    		settingsDialog.setLocation(p.x + e.getX() - pointS.x, p.y + e.getY() - pointS.y);
	    	}
	    });
        settingsDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		settingsDialog.pack();
		settingsDialog.setLocationRelativeTo(null);
		settingsDialog.setResizable(false);
		settingsDialog.setVisible(true);
    }
	
	public void createSettingsPanel() {
		settingsCard.setBorder(new CompoundBorder(new TitledBorder("Settings"), new EmptyBorder(12, 0, 12, 0)));
        GroupLayout settingsLayout = new GroupLayout(settingsCard);
        settingsCard.setLayout(settingsLayout);
        settingsLayout.setAutoCreateGaps(true);
        settingsLayout.setAutoCreateContainerGaps(true);
        
        JLabel mainLabel = new JLabel("A program mappája: ");
        JLabel tempLabel = new JLabel("A filmek mappája: ");

        mainButton = new JButton("Change");

        tempButton = new JButton("Change");
        tempButton.addActionListener(this);
        
        mainButton.addActionListener(this);
        mainButton.setFocusable(false);
        tempButton.setFocusable(false);
        
        mainTextField = new JTextField(mainDir.replace("//", "\\"), 40);
        mainTextField.setEditable(false);
        
        tempTextField = new JTextField(movieDir.replace("//", "\\"), 40);
        tempTextField.setEditable(false);
        
		settingsLayout.setHorizontalGroup(
			settingsLayout.createSequentialGroup()
				.addGroup(settingsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(mainLabel)
					.addComponent(tempLabel)
				)
				.addGroup(settingsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(mainTextField)
					.addComponent(tempTextField)
				)
				.addGroup(settingsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(mainButton)
					.addComponent(tempButton)
				)
		);
		settingsLayout.setVerticalGroup(
			settingsLayout.createSequentialGroup()
				.addGroup(settingsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(mainLabel)
					.addComponent(mainTextField)
					.addComponent(mainButton)
				)
				.addGroup(settingsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(tempLabel)
					.addComponent(tempTextField)
					.addComponent(tempButton)
				)
		);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mainButton) {
			chooser = new JFileChooser(); 
			chooser.setCurrentDirectory(new java.io.File(mainDir));
			UIManager.put("FileChooser.openDialogTitleText", "Path");
			UIManager.put("FileChooser.openButtonText", "Save");
			UIManager.put("FileChooser.cancelButtonText", "Cancel");
			SwingUtilities.updateComponentTreeUI(chooser);
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);
			
			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
				new LoadSettings();
				mainDir = LoadSettings.changeSettings(chooser.getSelectedFile().toString(), "mainDir");
				mainTextField.setText(mainDir.replace("//", "\\"));
			} else {
				System.out.println("No Selection");
			}
		} else if (e.getSource() == tempButton) {
			chooser = new JFileChooser(); 
			chooser.setCurrentDirectory(new java.io.File(movieDir));
			UIManager.put("FileChooser.openDialogTitleText", "Path");
			UIManager.put("FileChooser.openButtonText", "Save");
			UIManager.put("FileChooser.cancelButtonText", "Cancel");
			SwingUtilities.updateComponentTreeUI(chooser);
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);
			
			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
				new LoadSettings();
				mainDir = LoadSettings.changeSettings(chooser.getSelectedFile().toString(), "tempDir");
				mainTextField.setText(mainDir.replace("//", "\\"));
			} else {
				System.out.println("No Selection");
			}
		}
		settingsDialog.pack();
		settingsDialog.revalidate();
		settingsDialog.repaint();
	}
}