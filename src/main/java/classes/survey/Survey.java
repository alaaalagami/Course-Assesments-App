package classes.survey;

import java.util.ArrayList;
import java.util.List;

public class Survey {

  private List<Double> scores = new ArrayList<Double>();
  private Double satisfactoryCriteria;
  private Double numberToPass;
  protected Boolean dataReady = false;

  public Survey(){
           
  }

  public Survey(List<Double> scores, Double satisfactoryCriteria,
                Double numberToPass){
    this.scores = scores;
    this.satisfactoryCriteria = satisfactoryCriteria;
    this.numberToPass = numberToPass;        
  }

  public List<Double> getScores() {
    return scores;
  }

  public void setScores(List<Double> scores) {
    this.scores = scores;
  }

  public void addScores(Double score){
    this.scores.add(score);
  }

  public Double scoreSum(List<Double> scores){
    Double sum = 0.0;
    for (Double i : scores){
      sum += i;}
    return sum;
  }

  public Double getSatisfactoryCriteria() {
    return satisfactoryCriteria;
  }

  public void   setSatisfactoryCriteria(Double satisfactoryCriteria) {
    this.satisfactoryCriteria = satisfactoryCriteria;
  }

  public Double getNumberToPass() {
    return numberToPass;
  }

  public void setNumberToPass(Double numberToPass) {
    this.numberToPass = numberToPass;
  }
  public Boolean isDataReady() {
    return dataReady;
  }

  public void setDataReady(Boolean dataReady) {
  this.dataReady = dataReady;
  }

}