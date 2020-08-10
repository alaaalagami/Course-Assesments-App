package classes.assessment;
import java.util.*;

public class Assessment {

  private ArrayList<List<Double>> passingCriteria = new ArrayList<List<Double>>();
  
  private List<String>  conclusion = new ArrayList<String>();

  public Assessment(){

  }

  public Assessment(ArrayList<List<Double>> passingCriteria, ArrayList<String> Conclusion){
    this.passingCriteria = passingCriteria;
    this.conclusion = Conclusion;
  }

  public ArrayList<List<Double>> getPassingCriteria() {
    return passingCriteria;
  }

  public void setPassingCriteria(ArrayList<List<Double>> passingCriteria) {
    this.passingCriteria = passingCriteria;
  }

  public List<String> getConclusion() {
    return conclusion;
  }
  public void addConclusion(String conc){
    this.conclusion.add(conc);
  }
  public void addPassingCriteria(List<Double> criteria){
    this.passingCriteria.add(criteria);
  }

}