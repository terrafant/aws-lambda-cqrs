package com.uay.aws.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class SqsService {

    private static final String QUEUE_URL = "QUEUE_URL";

    public void sendQueueMessage(LambdaLogger logger, String messageBody) {
        logger.log("Sending " + messageBody);
        AmazonSQS sqs = new AmazonSQSClient().withRegion(Regions.EU_CENTRAL_1);

        sqs.sendMessage(new SendMessageRequest()
                .withQueueUrl(QUEUE_URL)
                .withMessageBody(messageBody));
    }

}
