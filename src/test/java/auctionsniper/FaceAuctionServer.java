package auctionsniper;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class FaceAuctionServer {

    public static final String ITEM_ID_AS_LOGIN = "auction-%s";
    public static final String AUCTION_RESOURCE = "Auction";
    public static final String XMPP_HOSTNAME = "localhost";
    private static final String AUCTION_PASSWORD = "auction";

    private final String itemId;
    private final XMPPConnection connection;
    private Chat currentChat;
    private SingleMessageListener messageListener = new SingleMessageListener();

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
                        chat.addMessageListener(messageListener);
                    }
                });
    }

    public String getItemId() {
        return this.itemId;
    }

    public void hasReceivedJoinRequestFromSniper() throws InterruptedException {
        this.messageListener.receivesAMessage();
    }

    public void announceClosed() throws XMPPException {
        this.currentChat.sendMessage(new Message());
    }

    public void stop() {
        this.connection.disconnect();
    }

    public class SingleMessageListener implements MessageListener {
        private final ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<Message>(
                1);

        @Override
        public void processMessage(final Chat chat, final Message message) {
            this.messages.add(message);
        }

        public void receivesAMessage() throws InterruptedException {
            assertThat("Message", this.messages.poll(5, TimeUnit.SECONDS),
                    is(notNullValue()));
        }
    }
}
