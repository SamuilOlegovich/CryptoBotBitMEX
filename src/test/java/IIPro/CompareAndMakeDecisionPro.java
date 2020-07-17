package IIPro;

import java.util.ArrayList;

public class CompareAndMakeDecisionPro {
    public static void main(String[] args) {
        ArrayList<String> pattern = new ArrayList<>();
        ArrayList<String> market = new ArrayList<>();

        pattern.add("BUY===0===SELL===1===AVERAGE===0.5===MAX===0.5===SIZE===281===ID===1014\n");
        pattern.add("{\"period\": \"M5\",\"preview\": \"0\",\"time\": \"2020-06-26 07:06:00\",\"price\": \"9173.0\",\"value\": \"-968678\",\"type\": \"DELTA_ZS_MIN_PLUS\",\"avg\": \"0\",\"dir\": \"-1\",\"open\": \"9174.0\",\"close\": \"9172.0\",\"high\": \"9174.5\",\"low\": \"9171.5\"}");
        pattern.add("{\"period\": \"M5\",\"preview\": \"1\",\"time\": \"2020-06-26 07:06:00\",\"price\": \"9172.0\",\"value\": \"475050\",\"type\": \"DELTA_ASK_HL\",\"avg\": \"0\",\"dir\": \"-1\",\"open\": \"9174.0\",\"close\": \"9172.0\",\"high\": \"9174.5\",\"low\": \"9171.5\"}");
        pattern.add("{\"period\": \"M15\",\"preview\": \"1\",\"time\": \"2020-06-26 07:05:00\",\"price\": \"9164.5\",\"value\": \"14551995\",\"type\": \"ASK\",\"avg\": \"6690397\",\"dir\": \"-1\",\"open\": \"9178.5\",\"close\": \"9174.0\",\"high\": \"9179.0\",\"low\": \"9150.0\"}" );
        pattern.add("{\"period\": \"M15\",\"preview\": \"1\",\"time\": \"2020-06-26 07:05:00\",\"price\": \"9164.5\",\"value\": \"29086551\",\"type\": \"BID\",\"avg\": \"7188808\",\"dir\": \"-1\",\"open\": \"9178.5\",\"close\": \"9174.0\",\"high\": \"9179.0\",\"low\": \"9150.0\"}" );
        pattern.add("{\"period\": \"M15\",\"preview\": \"1\",\"time\": \"2020-06-26 07:05:00\",\"price\": \"9164.5\",\"value\": \"43638546\",\"type\": \"VOLUME\",\"avg\": \"13931647\",\"dir\": \"-1\",\"open\": \"9178.5\",\"close\": \"9174.0\",\"high\": \"9179.0\",\"low\": \"9150.0\"}" );
        pattern.add("{\"period\": \"M15\",\"preview\": \"1\",\"time\": \"2020-06-26 07:05:00\",\"price\": \"9164.5\",\"value\": \"-14534556\",\"type\": \"DELTA_BID\",\"avg\": \"-4440315\",\"dir\": \"-1\",\"open\": \"9178.5\",\"close\": \"9174.0\",\"high\": \"9179.0\",\"low\": \"9150.0\"}" );
        pattern.add("{\"period\": \"M15\",\"preview\": \"1\",\"time\": \"2020-06-26 07:05:00\",\"price\": \"9163.5\",\"value\": \"-2268771\",\"type\": \"OPEN_POS_MINUS_HL\",\"avg\": \"0\",\"dir\": \"-1\",\"open\": \"9178.5\",\"close\": \"9174.0\",\"high\": \"9179.0\",\"low\": \"9150.0\"}" );
        pattern.add("{\"period\": \"M5\",\"preview\": \"0\",\"time\": \"2020-06-26 07:05:00\",\"price\": \"9163.5\",\"value\": \"747470\",\"type\": \"OI_ZS_MIN_MINUS\",\"avg\": \"0\",\"dir\": \"-1\",\"open\": \"9178.5\",\"close\": \"9174.0\",\"high\": \"9179.0\",\"low\": \"9150.0\"}" );
        pattern.add("{\"period\": \"M5\",\"preview\": \"1\",\"time\": \"2020-06-26 07:09:00\",\"price\": \"9163.0\",\"value\": \"537027\",\"type\": \"OPEN_POS_ASK_PLUS\",\"avg\": \"384893\",\"dir\": \"1\",\"open\": \"9160.5\",\"close\": \"9165.0\",\"high\": \"9165.0\",\"low\": \"9160.0\"}" );
        pattern.add("BIAS===SELL===-6.5===AVERAGE===2.9746835443038435===MAX===3.0===TIME===2020-06-26 07:15:00" );
        pattern.add("{\"period\": \"M5\",\"preview\": \"0\",\"time\": \"2020-06-26 07:10:00\",\"price\": \"9165.0\",\"value\": \"678184\",\"type\": \"DELTA_ASK_HL\",\"avg\": \"0\",\"dir\": \"1\",\"open\": \"9165.0\",\"close\": \"9167.5\",\"high\": \"9168.0\",\"low\": \"9164.5\"}" );
        pattern.add("{\"period\": \"M5\",\"preview\": \"0\",\"time\": \"2020-06-26 07:14:00\",\"price\": \"9156.5\",\"value\": \"-899524\",\"type\": \"DELTA_ZS_MIN_PLUS\",\"avg\": \"0\",\"dir\": \"1\",\"open\": \"9154.5\",\"close\": \"9158.5\",\"high\": \"9158.5\",\"low\": \"9154.5\"}" );
        pattern.add("{\"period\": \"M15\",\"preview\": \"1\",\"time\": \"2020-06-26 07:14:00\",\"price\": \"9156.5\",\"value\": \"1318763\",\"type\": \"DELTA_ZS_PLUS\",\"avg\": \"0\",\"dir\": \"1\",\"open\": \"9154.5\",\"close\": \"9158.5\",\"high\": \"9158.5\",\"low\": \"9154.5\"}" );
        pattern.add("{\"period\": \"M15\",\"preview\": \"1\",\"time\": \"2020-06-26 07:13:00\",\"price\": \"9155.0\",\"value\": \"2022265\",\"type\": \"OPEN_POS_ASK_PLUS\",\"avg\": \"755337\",\"dir\": \"1\",\"open\": \"9150.0\",\"close\": \"9155.0\",\"high\": \"9155.0\",\"low\": \"9149.0\"}" );
        pattern.add("{\"period\": \"M5\",\"preview\": \"0\",\"time\": \"2020-06-26 07:12:00\",\"price\": \"9140.5\",\"value\": \"116301\",\"type\": \"OI_ZS_MIN_MINUS\",\"avg\": \"0\",\"dir\": \"-1\",\"open\": \"9167.5\",\"close\": \"9150.0\",\"high\": \"9167.5\",\"low\": \"9134.0\"}" );
        pattern.add("{\"period\": \"M5\",\"preview\": \"0\",\"time\": \"2020-06-26 07:12:00\",\"price\": \"9140.5\",\"value\": \"-977403\",\"type\": \"OPEN_POS_BID_MINUS\",\"avg\": \"-426882\",\"dir\": \"-1\",\"open\": \"9167.5\",\"close\": \"9150.0\",\"high\": \"9167.5\",\"low\": \"9134.0\"}" );
        pattern.add("{\"period\": \"M15\",\"preview\": \"1\",\"time\": \"2020-06-26 07:12:00\",\"price\": \"9140.5\",\"value\": \"-1268917\",\"type\": \"OI_ZS_MINUS\",\"avg\": \"0\",\"dir\": \"-1\",\"open\": \"9167.5\",\"close\": \"9150.0\",\"high\": \"9167.5\",\"low\": \"9134.0\"}" );
        pattern.add("BIAS===SELL===-19.0===AVERAGE===4.452554744526424===MAX===8.5===TIME===2020-06-26 07:20:00" );
        pattern.add("{\"period\": \"M5\",\"preview\": \"1\",\"time\": \"2020-06-26 07:16:00\",\"price\": \"9164.5\",\"value\": \"-34587\",\"type\": \"OI_ZS_MINUS\",\"avg\": \"0\",\"dir\": \"1\",\"open\": \"9161.0\",\"close\": \"9164.5\",\"high\": \"9167.0\",\"low\": \"9160.5\"}" );
        pattern.add("{\"period\": \"M5\",\"preview\": \"1\",\"time\": \"2020-06-26 07:16:00\",\"price\": \"9164.5\",\"value\": \"1884288\",\"type\": \"OI_ZS_MIN_MINUS\",\"avg\": \"0\",\"dir\": \"1\",\"open\": \"9161.0\",\"close\": \"9164.5\",\"high\": \"9167.0\",\"low\": \"9160.5\"}" );
        pattern.add("{\"period\": \"M5\",\"preview\": \"1\",\"time\": \"2020-06-26 07:16:00\",\"price\": \"9163.5\",\"value\": \"-1299409\",\"type\": \"DELTA_ZS_MIN_PLUS\",\"avg\": \"0\",\"dir\": \"1\",\"open\": \"9161.0\",\"close\": \"9164.5\",\"high\": \"9167.0\",\"low\": \"9160.5\"}" );
        pattern.add("{\"period\": \"M5\",\"preview\": \"1\",\"time\": \"2020-06-26 07:19:00\",\"price\": \"9140.0\",\"value\": \"802311\",\"type\": \"OPEN_POS_BID_PLUS\",\"avg\": \"360048\",\"dir\": \"-1\",\"open\": \"9151.5\",\"close\": \"9139.5\",\"high\": \"9151.5\",\"low\": \"9139.0\"}" );
        pattern.add("{\"period\": \"M5\",\"preview\": \"1\",\"time\": \"2020-06-26 07:19:00\",\"price\": \"9140.0\",\"value\": \"611306\",\"type\": \"OPEN_POS_PLUS_HL\",\"avg\": \"0\",\"dir\": \"-1\",\"open\": \"9151.5\",\"close\": \"9139.5\",\"high\": \"9151.5\",\"low\": \"9139.0\"}" );
        pattern.add("{\"period\": \"M5\",\"preview\": \"1\",\"time\": \"2020-06-26 07:19:00\",\"price\": \"9140.0\",\"value\": \"1884288\",\"type\": \"OPEN_POS_PLUS\",\"avg\": \"607601\",\"dir\": \"-1\",\"open\": \"9151.5\",\"close\": \"9139.5\",\"high\": \"9151.5\",\"low\": \"9139.0\"}" );
        pattern.add("{\"period\": \"M5\",\"preview\": \"1\",\"time\": \"2020-06-26 07:19:00\",\"price\": \"9139.0\",\"value\": \"-1299409\",\"type\": \"DELTA_BID_HL\",\"avg\": \"0\",\"dir\": \"-1\",\"open\": \"9151.5\",\"close\": \"9139.5\",\"high\": \"9151.5\",\"low\": \"9139.0\"}" );




        market.add("BUY===0===SELL===1===AVERAGE===0.5===MAX===0.5===SIZE===281===ID===1014");
        market.add("{\"period\": \"M15\",\"preview\": \"01\",\"time\": \"2020-06-16 07:06:00\",\"price\": \"19173.0\",\"value\": \"-968678\",\"type\": \"DELTA_ZS_MIN_PLUS\",\"avg\": \"0\",\"dir\": \"-1\",\"open\": \"9174.0\",\"close\": \"9172.0\",\"high\": \"9174.5\",\"low\": \"9171.5\"}");
        market.add("{\"period\": \"M15\",\"preview\": \"11\",\"time\": \"2020-06-16 07:06:00\",\"price\": \"19172.0\",\"value\": \"475050\",\"type\": \"DELTA_ASK_HL\",\"avg\": \"0\",\"dir\": \"-1\",\"open\": \"9174.0\",\"close\": \"9172.0\",\"high\": \"9174.5\",\"low\": \"9171.5\"}");
        market.add("{\"period\": \"M115\",\"preview\": \"11\",\"time\": \"2020-06-16 07:05:00\",\"price\": \"19164.5\",\"value\": \"14551995\",\"type\": \"ASK\",\"avg\": \"6690397\",\"dir\": \"-1\",\"open\": \"9178.5\",\"close\": \"9174.0\",\"high\": \"9179.0\",\"low\": \"9150.0\"}" );
        market.add("{\"period\": \"M115\",\"preview\": \"11\",\"time\": \"2020-06-16 07:05:00\",\"price\": \"19164.5\",\"value\": \"29086551\",\"type\": \"BID\",\"avg\": \"7188808\",\"dir\": \"-1\",\"open\": \"9178.5\",\"close\": \"9174.0\",\"high\": \"9179.0\",\"low\": \"9150.0\"}" );
        market.add("{\"period\": \"M115\",\"preview\": \"11\",\"time\": \"2020-06-16 07:05:00\",\"price\": \"19164.5\",\"value\": \"43638546\",\"type\": \"VOLUME\",\"avg\": \"13931647\",\"dir\": \"-1\",\"open\": \"9178.5\",\"close\": \"9174.0\",\"high\": \"9179.0\",\"low\": \"9150.0\"}" );
        market.add("{\"period\": \"M115\",\"preview\": \"11\",\"time\": \"2020-06-16 07:05:00\",\"price\": \"19164.5\",\"value\": \"-14534556\",\"type\": \"DELTA_BID\",\"avg\": \"-4440315\",\"dir\": \"-1\",\"open\": \"9178.5\",\"close\": \"9174.0\",\"high\": \"9179.0\",\"low\": \"9150.0\"}" );
        market.add("{\"period\": \"M115\",\"preview\": \"11\",\"time\": \"2020-06-16 07:05:00\",\"price\": \"19163.5\",\"value\": \"-2268771\",\"type\": \"OPEN_POS_MINUS_HL\",\"avg\": \"0\",\"dir\": \"-1\",\"open\": \"9178.5\",\"close\": \"9174.0\",\"high\": \"9179.0\",\"low\": \"9150.0\"}" );
        market.add("{\"period\": \"M15\",\"preview\": \"01\",\"time\": \"2020-06-16 07:05:00\",\"price\": \"19163.5\",\"value\": \"747470\",\"type\": \"OI_ZS_MIN_MINUS\",\"avg\": \"0\",\"dir\": \"-1\",\"open\": \"9178.5\",\"close\": \"9174.0\",\"high\": \"9179.0\",\"low\": \"9150.0\"}" );
        market.add("{\"period\": \"M15\",\"preview\": \"11\",\"time\": \"2020-06-16 07:09:00\",\"price\": \"19163.0\",\"value\": \"537027\",\"type\": \"OPEN_POS_ASK_PLUS\",\"avg\": \"384893\",\"dir\": \"1\",\"open\": \"9160.5\",\"close\": \"9165.0\",\"high\": \"9165.0\",\"low\": \"9160.0\"}" );
        market.add("BIAS===SELL===-6.5===AVERAGE===2.9746835443038435===MAX===3.0===TIME===2020-06-26 07:19:00" );
        market.add("{\"period\": \"M15\",\"preview\": \"01\",\"time\": \"2020-06-16 07:10:00\",\"price\": \"19165.0\",\"value\": \"678184\",\"type\": \"DELTA_ASK_HL\",\"avg\": \"0\",\"dir\": \"1\",\"open\": \"9165.0\",\"close\": \"9167.5\",\"high\": \"9168.0\",\"low\": \"9164.5\"}" );
        market.add("{\"period\": \"M15\",\"preview\": \"01\",\"time\": \"2020-06-16 07:14:00\",\"price\": \"19156.5\",\"value\": \"-899524\",\"type\": \"DELTA_ZS_MIN_PLUS\",\"avg\": \"0\",\"dir\": \"1\",\"open\": \"9154.5\",\"close\": \"9158.5\",\"high\": \"9158.5\",\"low\": \"9154.5\"}" );
        market.add("{\"period\": \"M115\",\"preview\": \"11\",\"time\": \"2020-06-16 07:14:00\",\"price\": \"19156.5\",\"value\": \"1318763\",\"type\": \"DELTA_ZS_PLUS\",\"avg\": \"0\",\"dir\": \"1\",\"open\": \"9154.5\",\"close\": \"9158.5\",\"high\": \"9158.5\",\"low\": \"9154.5\"}" );
        market.add("{\"period\": \"M115\",\"preview\": \"11\",\"time\": \"2020-06-16 07:13:00\",\"price\": \"19155.0\",\"value\": \"2022265\",\"type\": \"OPEN_POS_ASK_PLUS\",\"avg\": \"755337\",\"dir\": \"1\",\"open\": \"9150.0\",\"close\": \"9155.0\",\"high\": \"9155.0\",\"low\": \"9149.0\"}" );
        market.add("{\"period\": \"M15\",\"preview\": \"01\",\"time\": \"2020-06-16 07:12:00\",\"price\": \"19140.5\",\"value\": \"116301\",\"type\": \"OI_ZS_MIN_MINUS\",\"avg\": \"0\",\"dir\": \"-1\",\"open\": \"9167.5\",\"close\": \"9150.0\",\"high\": \"9167.5\",\"low\": \"9134.0\"}" );
        market.add("{\"period\": \"M15\",\"preview\": \"01\",\"time\": \"2020-06-16 07:12:00\",\"price\": \"19140.5\",\"value\": \"-977403\",\"type\": \"OPEN_POS_BID_MINUS\",\"avg\": \"-426882\",\"dir\": \"-1\",\"open\": \"9167.5\",\"close\": \"9150.0\",\"high\": \"9167.5\",\"low\": \"9134.0\"}" );
        market.add("{\"period\": \"M115\",\"preview\": \"11\",\"time\": \"2020-06-16 07:12:00\",\"price\": \"19140.5\",\"value\": \"-1268917\",\"type\": \"OI_ZS_MINUS\",\"avg\": \"0\",\"dir\": \"-1\",\"open\": \"9167.5\",\"close\": \"9150.0\",\"high\": \"9167.5\",\"low\": \"9134.0\"}" );
        market.add("BIAS===SELL===-19.0===AVERAGE===4.452554744526424===MAX===8.5===TIME===2020-06-26 07:79:00" );
        market.add("{\"period\": \"M15\",\"preview\": \"11\",\"time\": \"2020-06-16 07:16:00\",\"price\": \"19164.5\",\"value\": \"-34587\",\"type\": \"OI_ZS_MINUS\",\"avg\": \"0\",\"dir\": \"1\",\"open\": \"9161.0\",\"close\": \"9164.5\",\"high\": \"9167.0\",\"low\": \"9160.5\"}" );
        market.add("{\"period\": \"M15\",\"preview\": \"11\",\"time\": \"2020-06-16 07:16:00\",\"price\": \"19164.5\",\"value\": \"1884288\",\"type\": \"OI_ZS_MIN_MINUS\",\"avg\": \"0\",\"dir\": \"1\",\"open\": \"9161.0\",\"close\": \"9164.5\",\"high\": \"9167.0\",\"low\": \"9160.5\"}" );
        market.add("{\"period\": \"M15\",\"preview\": \"11\",\"time\": \"2020-06-16 07:16:00\",\"price\": \"19163.5\",\"value\": \"-1299409\",\"type\": \"DELTA_ZS_MIN_PLUS\",\"avg\": \"0\",\"dir\": \"1\",\"open\": \"9161.0\",\"close\": \"9164.5\",\"high\": \"9167.0\",\"low\": \"9160.5\"}" );
        market.add("{\"period\": \"M15\",\"preview\": \"11\",\"time\": \"2020-06-16 07:19:00\",\"price\": \"19140.0\",\"value\": \"802311\",\"type\": \"OPEN_POS_BID_PLUS\",\"avg\": \"360048\",\"dir\": \"-1\",\"open\": \"9151.5\",\"close\": \"9139.5\",\"high\": \"9151.5\",\"low\": \"9139.0\"}" );
        market.add("{\"period\": \"M15\",\"preview\": \"11\",\"time\": \"2020-06-16 07:19:00\",\"price\": \"19140.0\",\"value\": \"611306\",\"type\": \"OPEN_POS_PLUS_HL\",\"avg\": \"0\",\"dir\": \"-1\",\"open\": \"9151.5\",\"close\": \"9139.5\",\"high\": \"9151.5\",\"low\": \"9139.0\"}" );
        market.add("{\"period\": \"M15\",\"preview\": \"11\",\"time\": \"2020-06-16 07:19:00\",\"price\": \"19140.0\",\"value\": \"1884288\",\"type\": \"OPEN_POS_PLUS\",\"avg\": \"607601\",\"dir\": \"-1\",\"open\": \"9151.5\",\"close\": \"9139.5\",\"high\": \"9151.5\",\"low\": \"9139.0\"}" );
        market.add("{\"period\": \"M15\",\"preview\": \"11\",\"time\": \"2020-06-16 07:19:00\",\"price\": \"19139.0\",\"value\": \"-1299409\",\"type\": \"DELTA_BID_HL\",\"avg\": \"0\",\"dir\": \"-1\",\"open\": \"9151.5\",\"close\": \"9139.5\",\"high\": \"9151.5\",\"low\": \"9139.0\"}" );


        new bitmex.Bot.model.strategies.iiPro.CompareAndMakeDecisionPro(market, pattern);
    }
}