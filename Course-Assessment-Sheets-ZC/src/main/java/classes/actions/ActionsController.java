package classes.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import classes.assessment.Assessment;
import classes.customrowtypes.*;
import javafx.collections.ObservableList;

public class ActionsController {
    //constructor
    public ActionsController(){

    }

    private boolean validateUpdateDouble(String currentValue) {
        try {
            Double.parseDouble(currentValue);
            return validatePercentage(Double.parseDouble(currentValue));
        } catch (Exception e) {
            return false;
        }
    }
    private boolean validatePercentage(Double currentValue) {
        if(currentValue<=100  && currentValue>=0){
            return true;
        }
        else{
            return false;
        }
    }

    public Actions initActions(){
        return new CLOactions();
    }

    public String generateConc(Double c1, Double c2, Double threshold){
        if(c1>= threshold && c2>=threshold){
            return "Satisfactory";
        }
        else if(c1< threshold && c2<threshold){
            return "Unsatisfactory";
        }
        return "Needs Action";
    }

    public Optional<String> validateData(ObservableList<StrRow> tableData,
                                            Double threshold,
                                            Actions entity){
        //Initialize local variables
        Assessment assessment = new Assessment();
        List<String> percievedProblem = new ArrayList<String>();
        List<String> actionPlan = new ArrayList<String>();
        //Get the size of the table
        Integer rowCount = tableData.size();
        //Loop over all rows
        for(Integer i = 0; i < rowCount; i++){
            // Add the passing criteria to the entity object if it is double
            if(validateUpdateDouble(tableData.get(i).getStr(1).get()) && validateUpdateDouble(tableData.get(i).getStr(2).get())){
                //get the passing criteria values
                Double[] entries = {Double.parseDouble(tableData.get(i).getStr(1).get()),
                                    Double.parseDouble(tableData.get(i).getStr(2).get())};
                // Add data iteratively
                assessment.addConclusion(generateConc(entries[0],entries[1],threshold));
                assessment.addPassingCriteria(Arrays.asList(entries));
                percievedProblem.add(tableData.get(i).getStr(6).get());
                actionPlan.add(tableData.get(i).getStr(7).get());
            }
            else{
                return Optional.of("Please enter a valid passing criteria");
            }
        }
    //set all data
    entity.setAssessment(assessment);
    entity.setPrecievedProblem(percievedProblem);
    entity.setActionPlan(actionPlan);
    return Optional.empty();
    }
}
     
