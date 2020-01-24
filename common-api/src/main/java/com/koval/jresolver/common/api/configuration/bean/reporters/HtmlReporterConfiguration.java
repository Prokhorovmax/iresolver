package com.koval.jresolver.common.api.configuration.bean.reporters;


public class HtmlReporterConfiguration {

  private boolean openBrowser;
  private String htmlTemplateFileName;
  private String outputFile;

  public boolean isOpenBrowser() {
    return openBrowser;
  }

  public void setOpenBrowser(boolean openBrowser) {
    this.openBrowser = openBrowser;
  }

  public String getHtmlTemplateFileName() {
    return htmlTemplateFileName;
  }

  public void setHtmlTemplateFileName(String htmlTemplateFileName) {
    this.htmlTemplateFileName = htmlTemplateFileName;
  }

  public String getOutputFile() {
    return outputFile;
  }

  public void setOutputFile(String outputFile) {
    this.outputFile = outputFile;
  }

  @Override
  public String toString() {
    return "HtmlReporterConfiguration{"
        + "openBrowser=" + openBrowser
        + ", htmlTemplateFileName='" + htmlTemplateFileName + '\''
        + ", outputFile='" + outputFile + '\''
        + '}';
  }
}
