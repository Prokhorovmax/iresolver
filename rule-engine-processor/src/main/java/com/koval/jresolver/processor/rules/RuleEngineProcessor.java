package com.koval.jresolver.processor.rules;

import java.io.IOException;

import com.koval.jresolver.common.api.bean.issue.Issue;
import com.koval.jresolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.jresolver.common.api.component.processor.IssueProcessor;
import com.koval.jresolver.common.api.configuration.Configuration;
import com.koval.jresolver.processor.rules.core.RuleEngine;
import com.koval.jresolver.processor.rules.core.impl.DroolsRuleEngine;


public class RuleEngineProcessor implements IssueProcessor {

  private final RuleEngine ruleEngine;

  public RuleEngineProcessor(Configuration configuration) throws IOException {
    this.ruleEngine = new DroolsRuleEngine(configuration.getProcessors().getRuleEngine().getRulesLocation());
  }

  @Override
  public void run(Issue issue, IssueAnalysingResult result) {
    setOriginalIssueToResults(issue, result);
    result.setProposals(ruleEngine.execute(issue));
  }
}
