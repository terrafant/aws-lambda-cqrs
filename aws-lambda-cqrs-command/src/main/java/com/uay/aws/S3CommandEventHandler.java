package com.uay.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uay.aws.domain.Order;
import com.uay.aws.service.JdbcService;
import com.uay.aws.service.S3Service;
import com.uay.aws.service.SqsService;

import java.io.IOException;
import java.sql.SQLException;

public class S3CommandEventHandler implements RequestHandler<S3Event, String> {

    private JdbcService jdbcService = new JdbcService();
    private S3Service s3Service = new S3Service();
    private SqsService sqsService = new SqsService();
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public String handleRequest(S3Event event, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("event = " + event.toJson());

        for (S3EventNotificationRecord record : event.getRecords()) {
            logger.log("record = " + record.getEventName());
            try {
                processRecord(logger, record);
            } catch (IOException | SQLException e) {
                logger.log(e.toString());
            }
        }

        logger.log("Finished CQRS command");
        return "Finished processing event";
    }

    private void processRecord(LambdaLogger logger, S3EventNotificationRecord record) throws IOException, SQLException {
        String s3ObjectKey = getS3ObjectKey(record);

        String data = s3Service.getS3Object(logger, getS3EventBucketName(record), s3ObjectKey);
        Order order = mapper.readValue(data, Order.class);

        jdbcService.persistEvent(logger, order.getId(), data);
        sqsService.sendQueueMessage(logger, data);
    }

    private static String getS3ObjectKey(S3EventNotificationRecord record) {
        return record.getS3().getObject().getKey();
    }

    private static String getS3EventBucketName(S3EventNotificationRecord record) {
        return record.getS3().getBucket().getName();
    }
}

