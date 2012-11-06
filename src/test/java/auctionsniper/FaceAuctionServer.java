package auctionsniper;

import java.text.Format;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class FaceAuctionServer {

    public static final String ITEM_ID_AS_LOGIN = "auction-%s";
    public static final String AUCTION_RESOURCE = "Auction";
    public static final String XMPP_HOSTNAME = "localhost";
    private static final String AUCTION_PASSWORD = "auction";

    private final String itemId;
    private final XMPPConnection connection;
    private Chat currentChat;

    public FaceAuctionServer(final String itemId) {
        this.itemId = itemId;
        this.connection = new XMPPConnection(XMPP_HOSTNAME);
    }

    public void startSellingItem() throws XMPPException {
        this.connection.connect();
        this.connection.login(String.format(ITEM_ID_AS_LOGIN, this.itemId),
                AUCTION_PASSWORD, AUCTION_RESOURCE);
        this.connection.getChatManager().addChatListener(
                new ChatManagerListener() {
                    @Override
                    public void chatCreated(final Chat chat,
                            final boolean createdLocally) {
                        FaceAuctionServer.this.currentChat = chat;
                    }
                });
    }

    public String getItemId() {
        return this.itemId;
    }

    public void hasReceivedJoinRequestFromSniper() {
        // TODO Auto-generated method stub

    }

    public void announceClosed() {
        // TODO Auto-generated method stub

    }

    public void stop() {
        // TODO Auto-generated method stub

    }

}
