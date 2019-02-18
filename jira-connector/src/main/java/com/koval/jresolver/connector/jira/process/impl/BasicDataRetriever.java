package com.koval.jresolver.connector.jira.process.impl;

import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.koval.jresolver.connector.jira.bean.JiraIssue;
import com.koval.jresolver.connector.jira.bean.JiraSearchResult;
import com.koval.jresolver.connector.jira.client.JiraClient;
import com.koval.jresolver.connector.jira.deliver.DataConsumer;
import com.koval.jresolver.connector.jira.process.DataRetriever;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;


public class BasicDataRetriever implements DataRetriever {

  private static final Logger LOGGER = LoggerFactory.getLogger(BasicDataRetriever.class);

  private final JiraClient jiraClient;
  private final DataConsumer dataConsumer;
  private final String jql;
  private final int maxResults;
  private int startAt;
  private final int delayAfterEveryRequest;
  private final int maxIssues;
  private double status;
  private boolean isComplete;

  public BasicDataRetriever(JiraClient jiraClient, DataConsumer dataConsumer, String jql, int maxResults, int startAt, int delayAfterEveryRequest, int maxIssues) {
    this.jiraClient = jiraClient;
    this.dataConsumer = dataConsumer;
    this.jql = jql;
    this.maxResults = maxResults;
    this.startAt = startAt;
    this.delayAfterEveryRequest = delayAfterEveryRequest;
    this.maxIssues = maxIssues;
  }

  @Override
  public void start() {
    status = 0.0;
    isComplete = false;
    LOGGER.info("Receiving data started.");
    while (!isComplete) {
      JiraSearchResult searchResult = jiraClient.searchByJql(jql, maxResults, startAt);

      Iterator<Issue> issueIterator = searchResult.getIssues().iterator();

      if (!issueIterator.hasNext()) {
        LOGGER.info("All data was collected. Stop receiving.");
        break;
      }

      int index = 0;
      while (issueIterator.hasNext()) {
        BasicIssue basicIssue = issueIterator.next();
        JiraIssue issue = jiraClient.getIssueByKey(basicIssue.getKey());
        dataConsumer.consume(issue);
        index++;
        LOGGER.info("Progress: " + (startAt + index) + "/" + searchResult.getTotal() + " Issue: " + issue.getKey());
        if (startAt + index >= maxIssues) {
          LOGGER.info("Max issues number was reached. Stop receiving.");
          isComplete = true;
          break;
        }
      }

      setStatus((double)(startAt + index) / searchResult.getTotal());
      startAt += maxResults;
      delay();
    }
    LOGGER.info("Receiving data stopped.");
  }

  @Override
  public void stop() {
    isComplete = true;
  }

  private void delay() {
    try {
      Thread.sleep(delayAfterEveryRequest);
      LOGGER.info("Waiting for " + delayAfterEveryRequest + "ms ...");
    } catch (InterruptedException ignored) {
    }
  }

  private void setStatus(double status) {
    this.status = status;
  }

  @Override
  public double getStatus() {
    return status;
  }
}
