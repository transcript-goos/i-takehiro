package auctionsniper;

import javax.swing.SwingUtilities;

public class Main {

    public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";

    private MainWindow ui;

    public Main() throws Exception {
        startUserInterface();
    }

    public static void main(final String... args) throws Exception {
        final Main main = new Main();
    }

    private void startUserInterface() throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {

            @Override
            public void run() {
                ui = new MainWindow();
            }
        });
    }
}
