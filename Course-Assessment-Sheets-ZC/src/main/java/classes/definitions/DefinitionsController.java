package classes.definitions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import classes.customrowtypes.*;
import javafx.collections.ObservableList;

public class DefinitionsController {

    public DefinitionsController() {
    }
    public CLO initClo(){
        return new CLO();
    }
    public SO initSo(){
        return new SO();
    }
    public Topic initTopic(){
        return new Topic();
    }
    public void reviveGUI(ObservableList<StrRow> topics,
                        ObservableList<StrRow> clos,
                        ObservableList<StrRowWithBool> sOs,
                        Topic topicEntity, CLO cloEntity, SO soEntity){
    topics.clear();
    for (int i =0; i < topicEntity.getDefList().size(); i++){
        String[] newRow  ={String.format("Tp %d", i+1),
        topicEntity.getDefList().get(i)};
        topics.add(new StrRow(newRow));
    }   
    clos.clear();
    for (int i =0; i < cloEntity.getDefList().size(); i++){
        String[] newRow  ={String.format("CLO %d", i+1),
        cloEntity.getDefList().get(i)};
        clos.add(new StrRow(newRow));
    } 
    sOs.clear();
    for (int i =0; i < soEntity.getDefList().size(); i++){
        String[] newRow  ={String.format("SO %d", i+1),
        soEntity.getDefList().get(i)};
        sOs.add(new StrRowWithBool(newRow, soEntity.getLinked().get(i)));
    }                         
    }
    public Optional<String> validateData(ObservableList<StrRow> topics,
                                         ObservableList<StrRow> clos,
                                         ObservableList<StrRowWithBool> sOs,
                                         Topic topicEntity, CLO cloEntity, SO soEntity)
    {
        Optional<String> result = Optional.empty();

        List<String> newTopicList = new ArrayList<String>();
        topics.forEach((StrRow row) -> {
            var new_topic = row.getStr(1).get();
            if ((new_topic.length() >0) && !new_topic.equals("<CLICK ME TO EDIT>"))
                newTopicList.add(new_topic);
            });
        if (newTopicList.isEmpty()){
            result = Optional.of("Topics are empty, You need to fill at least one");
            return result;
        }
        else
            topicEntity.setDefList(newTopicList);
        
        List<String> newCloList = new ArrayList<String>();
        clos.forEach((StrRow row) -> {
            var new_topic = row.getStr(1).get();
            if ((new_topic.length() >0) && !new_topic.equals("<CLICK ME TO EDIT>"))
                newCloList.add(new_topic);
            });
        if (newCloList.isEmpty()){
            result = Optional.of("CLOs are empty, You need to fill at least one");
            return result;
        }
        else
            cloEntity.setDefList(newCloList);
        
        List<String> newSoList = new ArrayList<String>();
        List<Boolean> newSoLinkedList = new ArrayList<Boolean>();
        sOs.forEach((StrRowWithBool row) -> {
            var new_so = row.getStr(1).get();
            if ((new_so.length() >0) && !new_so.equals("<CLICK ME TO EDIT>"))
                newSoList.add(new_so);
                newSoLinkedList.add(row.getBool().get());
            });
        if (newSoList.isEmpty()){
            result = Optional.of("SOs are empty, You need to fill at least one");
            return result;
        }
        else
            soEntity.setDefList(newSoList);
            soEntity.setLinked(newSoLinkedList);

        topicEntity.setDataReady(true);
        cloEntity.setDataReady(true);
        soEntity.setDataReady(true);
        return result;
    }
}