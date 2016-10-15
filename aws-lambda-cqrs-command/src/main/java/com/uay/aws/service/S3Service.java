package com.uay.aws.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import org.apache.commons.codec.Charsets;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

public class S3Service {

    private AmazonS3Client s3Client = new AmazonS3Client().withRegion(Regions.EU_CENTRAL_1);

    public String getS3Object(LambdaLogger logger, String bucketName, String key) throws IOException {
        logger.log("bucketName = " + bucketName + ", key = " + key);

        S3Object s3Object = s3Client.getObject(bucketName, key);
        logger.log("s3Object = " + s3Object);
        return IOUtils.toString(s3Object.getObjectContent(), Charsets.UTF_8);
    }

}
