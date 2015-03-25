package snpclip;

import java.awt.Toolkit;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Dimension;

import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.ImageIcon;

public class Application {
    boolean packFrame = false;

    /**
     * Construct and show the application.
     */
    MainFrame frame = null;

    private JWindow splashScreen = null;
    private JLabel splashLabel = null;

    // The preferred size of the demo
    private static final int PREFERRED_WIDTH = 1050;
    private static final int PREFERRED_HEIGHT = 600;

   public Application() throws Exception{

       frame = new MainFrame();

       frame.setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
       frame.setMinimumSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));

       createSplashScreen();
       showSplashScreen();

       // Show the demo and take down the splash screen. Note that
       // we again must do this on the GUI thread using invokeLater.
       SwingUtilities.invokeLater(new Runnable() {
           public void run() {

               frame.initializeFrame();

               for (int i = 0; i < 5; i++) {
                   try {
                       Thread.sleep(500);
                   } catch (InterruptedException e) {
                   }
               }
               // Validate frames that have preset sizes
               // Pack frames that have useful preferred size info, e.g. from their layout
               if (packFrame) {
                   frame.pack();
               } else {
                   frame.validate();
               }

               // Center the window
               Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
               Dimension frameSize = frame.getSize();
               if (frameSize.height > screenSize.height) {
                   frameSize.height = screenSize.height;
               }
               if (frameSize.width > screenSize.width) {
                   frameSize.width = screenSize.width;
               }

               frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

               frame.setVisible(true);
               hideSplash();
           }
       });
    }


    /**
     * Show the spash screen while the rest of the demo loads
     */
    public void createSplashScreen() throws Exception{
        splashLabel = new JLabel(createImageIcon("splash.png"));

            splashScreen = new JWindow(frame);
            splashScreen.getContentPane().add(splashLabel);
            splashScreen.pack();
            Rectangle screenRect = frame.getGraphicsConfiguration().getBounds();

            splashScreen.setLocation(screenRect.x + screenRect.width/2 - splashScreen.getSize().width/2, screenRect.y + screenRect.height/2 - splashScreen.getSize().height/2);
    }

    public void showSplashScreen() {
                splashScreen.setVisible(true);

    }
    /**
     * pop down the spash screen
     */
    public void hideSplash() {
            splashScreen.setVisible(false);
            splashScreen = null;
            splashLabel = null;
    }

    /**
     * Creates an icon from an image contained in the "images" directory.
     */
    public ImageIcon createImageIcon(String filename) throws Exception{
        return new ImageIcon(((Application.class.getResource(filename)).toURI()).toURL());
    }

    /**
     * Application entry point.
     *
     * @param args String[]
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.
                                             getSystemLookAndFeelClassName());

                    new Application();

                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }
        });
    }
}
