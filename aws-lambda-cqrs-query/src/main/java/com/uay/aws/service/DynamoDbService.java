package com.uay.aws.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.uay.aws.domain.AggregatedItem;

import java.util.List;
import java.util.stream.Collectors;

public class DynamoDbService {

    private static final String AGGREGATED_CQRS = "aggregated-cqrs";
    private static final AmazonDynamoDBClient CLIENT = new AmazonDynamoDBClient().withRegion(Regions.EU_CENTRAL_1);
    private DynamoDB dynamoDB = new DynamoDB(CLIENT);
    private Table table = dynamoDB.getTable(AGGREGATED_CQRS);

    public List<AggregatedItem> retrieveItem() {
        ScanResult scanResult = CLIENT.scan(new ScanRequest().withTableName(AGGREGATED_CQRS));
        return scanResult.getItems().stream().map(item -> new AggregatedItem(
                item.get("itemName").getS(),
                item.get("quantity").getN(),
                item.get("bought").getN(),
                item.get("reserved").getN()
        )).collect(Collectors.toList());
    }
}
