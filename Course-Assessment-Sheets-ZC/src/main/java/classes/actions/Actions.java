package classes.actions;

import java.util.ArrayList;
import java.util.List;

import classes.assessment.Assessment;

public abstract class Actions {

  private List<String>  precievedProblem = new ArrayList<String>();
  private List<String>  actionPlan = new ArrayList<String>();
  private Assessment assessment;
  protected Boolean dataReady = false;

  public List<String>  getPrecievedProblem() {
    return precievedProblem;
  }

  public void setPrecievedProblem(List<String>  precievedProblem) {
    this.precievedProblem = precievedProblem;
  }

  public List<String>  getActionPlan() {
    return actionPlan;
  }

  public void setActionPlan(List<String>  actionPlan) {
    this.actionPlan = actionPlan;
  }

  public Assessment getAssessment() {
    return assessment;
  }

  public void setAssessment(Assessment assessment) {
    this.assessment = assessment;
  }
  public Boolean isDataReady() {
    return dataReady;
  }

  public void setDataReady(Boolean dataReady) {
  this.dataReady = dataReady;
  }

  
}