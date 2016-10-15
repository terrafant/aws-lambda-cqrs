package com.uay.aws.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.uay.aws.domain.Order;

import java.util.Map;

public class DynamoDbService {

    private static final String AGGREGATED_CQRS = "aggregated-cqrs";
    private static final AmazonDynamoDBClient CLIENT = new AmazonDynamoDBClient().withRegion(Regions.EU_CENTRAL_1);
    private static final String KEY_ID = "id";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_RESERVED = "reserved";
    private static final String KEY_BOUGHT = "bought";
    private DynamoDB dynamoDB = new DynamoDB(CLIENT);
    private Table table = dynamoDB.getTable(AGGREGATED_CQRS);

    public void processOrders(LambdaLogger logger, Map<String, Order> orders) {
        if (orders.isEmpty()) return;
        logger.log("Sending to DynamoDB " + orders.size() + " orders");
        for (Order order : orders.values()) {
            Item item = table.getItem(KEY_ID, order.getItemName());
            processOrder(logger, order, item);
            table.putItem(item);
        }
    }

    private void processOrder(LambdaLogger logger, Order order, Item item) {
        switch (order.getStatus()) {
            case "new":
                item.withNumber(KEY_QUANTITY, item.getNumber(KEY_QUANTITY).longValue() - order.getQuantity());
                item.withNumber(KEY_RESERVED, item.getNumber(KEY_RESERVED).longValue() + order.getQuantity());
                break;
            case "cancelled":
                item.withNumber(KEY_QUANTITY, item.getNumber(KEY_QUANTITY).longValue() + order.getQuantity());
                item.withNumber(KEY_RESERVED, item.getNumber(KEY_RESERVED).longValue() - order.getQuantity());
                break;
            case "confirmed":
                item.withNumber(KEY_BOUGHT, item.getNumber(KEY_BOUGHT).longValue() + order.getQuantity());
                item.withNumber(KEY_RESERVED, item.getNumber(KEY_RESERVED).longValue() - order.getQuantity());
                break;
            default:
                logger.log("Unknown order status");
        }
    }
}
