package classes.courseInfo;

import classes.customrowtypes.*;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;

import java.util.Optional;
import classes.*;
import org.apache.commons.lang3.StringUtils;
public class CourseInfoController{

    public CourseInfoController(){

    }
    public CourseInfo initCourseInfo(){
        CourseInfo new_CInfo = new CourseInfo();
        return new_CInfo;
    }
    public void reviveGUI(ComboBox<String>programCB ,
    ComboBox<String>semesterCB, ComboBox<String> aYearCB,
                            ObservableList<StrRow> mandInfo,
                            ComboBox<String> requiredCB,
                            Spinner<Integer> creditSpinner,
                            ObservableList<StrRow> optionalInfo,
                            ObservableList<StrRow> scheduleLines,
                            ObservableList<StrRow> textBook,
                            ObservableList<StrRow> references,
                            ObservableList<StrRow> notes,
                            CourseInfo cInfo){
        programCB.getSelectionModel().select(cInfo.getMandInfo().getProgram());
        semesterCB.getSelectionModel().select(cInfo.getMandInfo().getSemester());
        aYearCB.getSelectionModel().select(cInfo.getMandInfo().getAcademicYear().toString());
        mandInfo.get(0).setStr(1, cInfo.getMandInfo().getCourseCode());     
        mandInfo.get(1).setStr(1, cInfo.getMandInfo().getCourseTitle());
        mandInfo.get(2).setStr(1, cInfo.getMandInfo().getInstructorName());
        mandInfo.get(3).setStr(1, cInfo.getMandInfo().getInstructorEmail());
        mandInfo.get(4).setStr(1, cInfo.getMandInfo().getTAName());
        mandInfo.get(5).setStr(1, cInfo.getMandInfo().getTAEmail());
        requiredCB.getSelectionModel().select((cInfo.getOpInfo().getRequired()
         == Required.REQUIRED)? "Required" : "Elective");
        creditSpinner.getValueFactory().setValue(cInfo.getOpInfo().getCredit());
        optionalInfo.get(0).setStr(1, cInfo.getOpInfo().getDescription());     
        optionalInfo.get(1).setStr(1, cInfo.getOpInfo().getPrerequisites());     
        optionalInfo.get(2).setStr(1, cInfo.getOpInfo().getRelatedTopics());     
        optionalInfo.get(3).setStr(1, cInfo.getOpInfo().getInstructorOffice());     
        optionalInfo.get(4).setStr(1, cInfo.getOpInfo().getTaOffice());     
        optionalInfo.get(5).setStr(1, cInfo.getOpInfo().getDiscussinGroup());
        scheduleLines.clear();
        for (int i =0; i < cInfo.getOpInfo().getScheduleLines().size(); i++){
            String[] newRow  ={String.format("Schedule Line %d", i+1),
             cInfo.getOpInfo().getScheduleLines().get(i)};
            scheduleLines.add(new StrRow(newRow));
        }
        textBook.clear();
        for (int i =0; i < cInfo.getOpInfo().getTextBooks().size(); i++){
            String[] newRow  ={String.format("Textbook %d", i+1),
             cInfo.getOpInfo().getTextBooks().get(i)};
             textBook.add(new StrRow(newRow));
        }
        references.clear();
        for (int i =0; i < cInfo.getOpInfo().getReferences().size(); i++){
            String[] newRow  ={String.format("Reference %d", i+1),
             cInfo.getOpInfo().getReferences().get(i)};
             references.add(new StrRow(newRow));
        }
        notes.clear();
        for (int i =0; i < cInfo.getOpInfo().getNotes().size(); i++){
            String[] newRow  ={String.format("Note %d", i+1),
             cInfo.getOpInfo().getNotes().get(i)};
             notes.add(new StrRow(newRow));
        }
                 
    }
    public Optional<String> validateUpdate(String program, String semester,
                                           Integer aYear,
                                           ObservableList<StrRow> mandInfo,
                                           Required required, Integer credit,
                                           ObservableList<StrRow> optionalInfo,
                                           ObservableList<StrRow> scheduleLines,
                                           ObservableList<StrRow> textBook,
                                           ObservableList<StrRow> references,
                                           ObservableList<StrRow> notes,
                                           CourseInfo cInfo){
        Optional<String> result = Optional.empty();
        if (program.equals("")){
            result = Optional.of("Program was not entered");
            return result;
        }
        
        if (semester.equals(""))
        {
            result = Optional.of("Semester was not entered");
            return result;
        }
        if (aYear.equals(Integer.valueOf(0))){
            result = Optional.of("Academic Year was not entered");
            return result;
        }
        String cCode = StringUtils.strip(mandInfo.get(0).getStr(1).get());
        if (!(StringUtils.split(cCode).length == 2))
        {
            result = Optional.of("Invalid course code data, has to consist of to parts"+
            ", like MATH 999 " + cCode + " was passed");
            return result;
        }
        
        String cTitle = StringUtils.strip(mandInfo.get(1).getStr(1).get());
        if (cTitle.equals("<CLICK ME TO EDIT>")){
            result = Optional.of("Course Title was not entered");
            return result;
            }
        String iName = StringUtils.strip(mandInfo.get(2).getStr(1).get());
        if (iName.equals("<CLICK ME TO EDIT>")){
            result = Optional.of("Instructor Name was not entered");
            return result;
            }
        String iEmail = StringUtils.strip(mandInfo.get(3).getStr(1).get());
        if (!(Utils.isValidEmailAddress(iEmail))){
            result = Optional.of("Instructor Email is not a valid email address");
            return result;
            }
        String taName = StringUtils.strip(mandInfo.get(4).getStr(1).get());
        if (taName.equals("<CLICK ME TO EDIT>")){
            result = Optional.of("TA Name was not entered");
            return result;
            }
        String taEmail = StringUtils.strip(mandInfo.get(5).getStr(1).get());
        if (!(Utils.isValidEmailAddress(taEmail))){
            result = Optional.of("TA Email is not a valid email address");
            return result;
            }
        cInfo.setMandInfo(new MandatoryInfo(program, semester,
                                            aYear, cCode, cTitle, iName,
                                            iEmail, taName, taEmail));
        cInfo.getOpInfo().setRequired(required);
        cInfo.getOpInfo().setCredit(credit);
                                    
        // Append optional values if available
        // ; values that have multiple entries
        // schedlude Lines, textbooks, references and notes.
        scheduleLines.forEach((StrRow row) -> {
            var new_sch = row.getStr(1).get();
            if ((new_sch.length() >0) && !new_sch.equals("<CLICK ME TO EDIT>"))
                cInfo.getOpInfo().getScheduleLines().add(new_sch);
            });
        textBook.forEach((StrRow row) -> {
            var new_sch = row.getStr(1).get();
            if ((new_sch.length() >0) && !new_sch.equals("<CLICK ME TO EDIT>"))
                cInfo.getOpInfo().getTextBooks().add(new_sch);
            });
        references.forEach((StrRow row) -> {
            var new_sch = row.getStr(1).get();
            if ((new_sch.length() >0) && !new_sch.equals("<CLICK ME TO EDIT>"))
                cInfo.getOpInfo().getReferences().add(new_sch);
            });
        notes.forEach((StrRow row) -> {
            var new_sch = row.getStr(1).get();
            if ((new_sch.length() >0) && !new_sch.equals("<CLICK ME TO EDIT>"))
                cInfo.getOpInfo().getReferences().add(new_sch);
            });
        String description = StringUtils.strip(optionalInfo.get(0).getStr(1).get());
        if (!(description.equals("<CLICK ME TO EDIT>"))){
            cInfo.getOpInfo().setDescription(description);
            }
        String prereqs = StringUtils.strip(optionalInfo.get(1).getStr(1).get());
        if (!(prereqs.equals("<CLICK ME TO EDIT>"))){
            cInfo.getOpInfo().setPrerequisites(prereqs);
            }
        String relatedTopics = StringUtils.strip(optionalInfo.get(2).getStr(1).get());
        if (!(relatedTopics.equals("<CLICK ME TO EDIT>"))){
            cInfo.getOpInfo().setRelatedTopics(relatedTopics);
            }
        String instructorOffice = StringUtils.strip(optionalInfo.get(3).getStr(1).get());
        if (!(instructorOffice.equals("<CLICK ME TO EDIT>"))){
            cInfo.getOpInfo().setInstructorOffice(instructorOffice);
            }
        String taOffice = StringUtils.strip(optionalInfo.get(4).getStr(1).get());
        if (!(taOffice.equals("<CLICK ME TO EDIT>"))){
            cInfo.getOpInfo().setTaOffice(taOffice);
            }
        String group = StringUtils.strip(optionalInfo.get(5).getStr(1).get());
        if (!(taOffice.equals("<CLICK ME TO EDIT>"))){
            cInfo.getOpInfo().setDiscussinGroup(group);
            }
        cInfo.setDataReady(true);
        return result;
    }
};