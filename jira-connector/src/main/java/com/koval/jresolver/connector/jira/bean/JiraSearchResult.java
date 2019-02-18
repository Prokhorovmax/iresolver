package com.koval.jresolver.connector.jira.bean;

import java.util.ArrayList;
import java.util.Collection;

import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;


public class JiraSearchResult {

  private int startIndex;
  private int maxResults;
  private int total;
  private Collection<Issue> issues;

  public JiraSearchResult() {
  }

  public JiraSearchResult(SearchResult searchResult) {
    this.startIndex = searchResult.getStartIndex();
    this.maxResults = searchResult.getMaxResults();
    this.total = searchResult.getTotal();
    this.issues = issuesToCollection(searchResult.getIssues());
  }

  private Collection<Issue> issuesToCollection(final Iterable<? extends Issue> iterable) {
    Collection<Issue> result = new ArrayList<>();
    iterable.forEach(result::add);
    return result;
  }

  public int getStartIndex() {
    return startIndex;
  }

  public void setStartIndex(int startIndex) {
    this.startIndex = startIndex;
  }

  public int getMaxResults() {
    return maxResults;
  }

  public void setMaxResults(int maxResults) {
    this.maxResults = maxResults;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public Collection<Issue> getIssues() {
    return issues;
  }

  public void setIssues(Collection<Issue> issues) {
    this.issues = issues;
  }
}
