package auctionsniper;

import org.junit.After;
import org.junit.Test;

public class AuctionSniperEndToEndTest {

    private final FaceAuctionServer auction = new FaceAuctionServer(
            "item-54321");

    private final ApplicationRunner application = new ApplicationRunner();

    @Test
    public void sniperJoinsAuctionUntilAuctionCloses() throws Exception {
        auction.startSellingItem(); // ステップ 1
        application.startBiddingIn(auction); // ステップ 2
        auction.hasReceivedJoinRequestFromSniper(); // ステップ 3
        auction.announceClosed(); // ステップ 4
        application.showsSniperHasLostAuction(); // ステップ 5
    }

    // 追加のクリーンアップ
    @After
    public void stopAuction() {
        auction.stop();
    }

    @After
    public void stopApplication() {
        application.stop();
    }
}
