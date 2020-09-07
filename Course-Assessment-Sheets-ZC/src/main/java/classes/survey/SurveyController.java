package classes.survey;
import java.util.Optional;

import classes.customrowtypes.*;
import javafx.collections.ObservableList;


public class SurveyController {
    //constructor
    public SurveyController(){

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

    public Survey initSurvey(){
        return new Survey();
    }
    
    
    public Optional<String> validateData(ObservableList<StrRow> scores,
                                         String numberToPass,
                                         String satisfactoryCriteria,
                                         Survey entity){
        
        if(validateUpdateDouble(satisfactoryCriteria)){
            entity.setSatisfactoryCriteria(Double.parseDouble(satisfactoryCriteria));}
        else{
            return Optional.of("Please enter a valid satisfactory criteria");}

        if(validateUpdateDouble(numberToPass)){
            entity.setNumberToPass(Double.parseDouble(numberToPass));}
        else{
            return Optional.of("Please enter a valid number of students to pass");}

        for(StrRow data : scores){
            System.out.println(data.getStr(1).get());
            if(validateUpdateDouble(data.getStr(1).get())){
                entity.addScores(Double.parseDouble(data.getStr(1).get()));
            }
            else{
                return Optional.of("Please check that all scores are valid percentages");
            }}
        return Optional.empty();
        }
}