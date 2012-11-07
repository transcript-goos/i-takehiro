package auctionsniper;

import javax.swing.SwingUtilities;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class Main {

    public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";

    private static final int ARG_HOSTNAME = 0;
    private static final int ARG_USERNAME = 1;
    private static final int ARG_PASSWORD = 2;
    private static final int ARG_ITEM_ID = 3;
    public static final String AUCTION_RESOURCE = "Auction";
    public static final String ITEM_ID_AS_LOGIN = "auction-%s";
    public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/"
            + AUCTION_RESOURCE;

    private MainWindow ui;

    public Main() throws Exception {
        startUserInterface();
    }

    public static void main(final String... args) throws Exception {
        final Main main = new Main();
        final XMPPConnection connection = connectTo(args[ARG_HOSTNAME],
                args[ARG_USERNAME], args[ARG_PASSWORD]);
        final Chat chat = connection.getChatManager().createChat(
                auctionId(args[ARG_ITEM_ID], connection),
                new MessageListener() {

                    @Override
                    public void processMessage(final Chat aChat,
                            final Message message) {
                        // まだ何もしていない
                    }
                });
        chat.sendMessage(new Message());
    }

    private void startUserInterface() throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {

            @Override
            public void run() {
                ui = new MainWindow();
            }
        });
    }

    private static XMPPConnection connectTo(final String hostname,
            final String username, final String password) throws XMPPException {
        final XMPPConnection connection = new XMPPConnection(hostname);
        connection.connect();
        connection.login(username, password, AUCTION_RESOURCE);
        return connection;
    }

    private static String auctionId(final String itemId,
            final XMPPConnection connection) {
        return String.format(AUCTION_ID_FORMAT, itemId,
                connection.getServiceName());
    }
}
