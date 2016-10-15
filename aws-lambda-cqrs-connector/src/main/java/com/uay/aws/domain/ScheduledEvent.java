package com.uay.aws.domain;

import com.amazonaws.util.json.Jackson;

import java.util.List;
import java.util.Map;

/*
    {
  "account": "123456789012",
  "region": "us-east-1",
  "detail": {},
  "detail-type": "Scheduled Event",
  "source": "aws.events",
  "time": "1970-01-01T00:00:00Z",
  "id": "cdc73f9d-aea9-11e3-9d5a-835b769c0d9c",
  "resources": [
    "arn:aws:events:us-east-1:123456789012:rule/my-schedule"
  ]
}
     */
public class ScheduledEvent {

    private String account;
    private String region;
    private Map detail;
    private String detailType;
    private String source;
    private String time;
    private String id;
    private List<String> resources;

    public ScheduledEvent(String account, String region, Map detail, String detailType, String source, String time, String id, List<String> resources) {
        this.account = account;
        this.region = region;
        this.detail = detail;
        this.detailType = detailType;
        this.source = source;
        this.time = time;
        this.id = id;
        this.resources = resources;
    }

    public ScheduledEvent() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Map getDetail() {
        return detail;
    }

    public void setDetail(Map detail) {
        this.detail = detail;
    }

    public String getDetailType() {
        return detailType;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }

    public String toJson() {
        return Jackson.toJsonString(this);
    }
}
