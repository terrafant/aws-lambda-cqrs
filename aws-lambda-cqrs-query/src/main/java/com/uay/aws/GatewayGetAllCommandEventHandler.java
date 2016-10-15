package com.uay.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.uay.aws.domain.AggregatedItem;
import com.uay.aws.service.DynamoDbService;

import java.util.List;

public class GatewayGetAllCommandEventHandler implements RequestHandler<Object, List<AggregatedItem>> {

    private DynamoDbService dynamoDbService = new DynamoDbService();

    @Override
    public List<AggregatedItem> handleRequest(Object event, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("event = " + event.toString());

        List<AggregatedItem> aggregatedItems = dynamoDbService.retrieveItem();
        logger.log("aggregatedItems = " + aggregatedItems);;

        logger.log("Finished CQRS query");
        return aggregatedItems;
    }
}

