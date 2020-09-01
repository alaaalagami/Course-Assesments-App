package classes.syllabus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import classes.courseInfo.CourseInfo;
import classes.courseInfo.Required;
import classes.definitions.CLO;
import classes.definitions.SO;
import classes.definitions.Topic;
import classes.grades.Grades;


public class SyllabusController {
    //constructor
    public SyllabusController(){

    }
    
    public File generateSyllabus(CourseInfo cInfo, CLO clo, Topic tp,
                                SO so, Grades grades, String baseP, InputStream imPath){
    // if (!(cInfo.isDataReady() && clo.isDataReady() && tp.isDataReady() &&
    //         so.isDataReady() && grades.getDataReady()))
    //     return;
    File yourFile = new File("");
    try{
        String basePath = URLDecoder.decode(baseP, "UTF-8");
        Files.copy(imPath,
          Paths.get(new File(basePath, "im.png").getPath()), StandardCopyOption.REPLACE_EXISTING);
        yourFile = new File(basePath, "CAShsyll.html");
        yourFile.createNewFile();
        FileOutputStream htmlfile = new FileOutputStream(yourFile);
        htmlfile.write(initHtml().getBytes());
        htmlfile.write(progrname(cInfo.getMandInfo().getProgram()).getBytes());
        htmlfile.write(cnameTitle(cInfo.getMandInfo().getCourseCode(),
                                  cInfo.getMandInfo().getCourseTitle(),
                                  cInfo.getOpInfo().getCredit(),
                                  cInfo.getMandInfo().getSemester(),
                                  cInfo.getMandInfo().getAcademicYear(),
                                  cInfo.getOpInfo().getRequired()).getBytes());
        htmlfile.write(description(cInfo.getOpInfo().getDescription()).getBytes());
        htmlfile.write(prereqHTML(cInfo.getOpInfo().getPrerequisites()).getBytes());
        htmlfile.write(relatedTopicsHTML(cInfo.getOpInfo().getRelatedTopics()).getBytes());
        htmlfile.write(staff(cInfo.getMandInfo().getInstructorName(),
                            cInfo.getOpInfo().getInstructorOffice(),
                            cInfo.getMandInfo().getTAName(),
                            cInfo.getOpInfo().getTaOffice()).getBytes());
        htmlfile.write(schedules(cInfo.getOpInfo().getScheduleLines()).getBytes());
        htmlfile.write(textbooks(cInfo.getOpInfo().getTextBooks()).getBytes());
        htmlfile.write(references(cInfo.getOpInfo().getReferences()).getBytes());
        htmlfile.write(dgroup(cInfo.getOpInfo().getDiscussinGroup()).getBytes());
        htmlfile.write(gradeHTML(grades.getCategory(),grades.getGrade()).getBytes());
        htmlfile.write(topics(tp.getDefList()).getBytes());
        htmlfile.write(notesHTML(cInfo.getOpInfo().getNotes()).getBytes());
        htmlfile.write(closHTML(clo.getDefList()).getBytes());
        htmlfile.write(sosHTML(so.getDefList()).getBytes());
        htmlfile.write(getSignature(cInfo.getMandInfo().getInstructorName()).getBytes());
        htmlfile.write(("</tbody></table></body></html>").getBytes());
        htmlfile.close();
    }
    catch(FileNotFoundException e){
        System.out.println("File not found");
        return null;
    }
    catch(IOException e){
        System.out.println("io exccp");
        return null;
    }
    return yourFile;
    }

    private String initHtml() {
        return "<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.0 Transitional//EN'>\n" +
        "<html><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>\n" +
            "<title></title>	\n" +
            "<style type='text/css'>\n" +
                "body,div,table,thead,tbody,tfoot,tr,th,td,p { font-family:'Calibri'; font-size:x-small }\n" +
                "a.comment-indicator:hover + comment { background:#ffd; position:absolute; display:block; border:1px solid black; padding:0.5em;  } \n" +
                "a.comment-indicator { background:red; display:inline-block; border:1px solid black; width:0.5em; height:0.5em;  } \n" +
                "comment { display:none;  } \n" +
            "</style>\n" +
            
        "</head>\n"+ 

    "<body style=' width: 696px;'>" + 
    "<table cellspacing='0' border='0'>" + 
        "<tbody><tr>" + 
            "<td colspan='4' rowspan='1' height='73' align='left' valign='bottom'><br><img src='" + 
                "im.png' width='323' height='58' hspace='23' vspace='9'>" + 
            "</td>" + 
        "</tr>" + 
        "<tr height='27'></tr>" + 
            "<td colspan='10' height='23' align='center' valign='bottom'><b><font face='Times New Roman' size='4' color='#000000'>Zewail City of Science and Technology</font></b></td>" + 
        "</tr>" + 
        "<tr>" + 
            "<td colspan='10' height='23' align='center' valign='bottom'><b><font face='Times New Roman' size='4' color='#000000'>University of Science and Technology</font></b></td>" + 
        "</tr>";
    }

    private String progrname(String pname){
        return "<tr><td colspan='10' height='23' align='center' valign='bottom'><b><font face='Times New Roman' size='4' color='#000000'>" + 
        pname + "</font></b></td></tr>";
    }

    private String cnameTitle(String cCode, String cTitle, int cr,
                 String sem, int ayear, Required req){
        return String.format("<tr><td colspan='10' height='23' align='center' valign='bottom'><b><font face='Times New Roman' size='4' color='#000000'>%s, %s, %d Cr., %s %d, %s</font></b></td></tr>",
        cCode, cTitle, cr, sem, ayear, (req == Required.REQUIRED)? "Required": "Elective");
    }

    private String description(String desc){
        return "" + 
        "<tr height='18'></tr>" + 
        "<tr>" + 
            "<td colspan='3' height='18' align='left' valign='bottom'><b><font face='Times New Roman' color='#000000'>Course Description</font></b></td>" + 
            "<td align='left' valign='bottom'><font face='Times New Roman' color='#000000'><br></font></td>" + 
        "</tr>" + 
        "<tr>" + 
            "<td colspan='10' rowspan='1' height='183' align='left' valign='top'><font face='Times New Roman' color='#000000'>" + 
            desc + 
        "</font></td>" + 
        "</tr>" + 
        "<tr height='18'></tr>";
    }

    private String prereqHTML(String prereqs){
        return "<tr>" +
		"<td colspan='2' height='18' align='left' valign='middle'><b><font face='Times New Roman' color='#000000'>Prerequisites</font></b></td>" +
		String.format("<td colspan='8' align='left' valign='bottom'><font face='Times New Roman' color='#000000'>%s</font></td>", prereqs) +
        "</tr>";
    }

    private String relatedTopicsHTML(String topics){
        return "<tr>" +
		"<td colspan='2' height='18' align='left' valign='middle'><b><font face='Times New Roman' color='#000000'>Related topics</font></b></td>" +
		String.format("<td colspan='8' align='left' valign='bottom'><font face='Times New Roman' color='#000000'>%s</font></td>", topics) +
        "</tr>";
    }

    private String staff(String instructor, String instructorOffice, String ta, String taOffice){
        return "<tr>" +
            "<td colspan='2' height='18' align='left' valign='middle'><b><font face='Times New Roman' color='#000000'>Instructor</font></b></td>" +
            String.format("<td colspan='8' align='left' valign='bottom'><font face='Times New Roman' color='#000000'>%s, Room #: %s</font></td>",
             instructor, instructorOffice) +
            "</tr>" +
            "<tr>" +
            "<td colspan='2' height='18' align='left' valign='middle'><b><font face='Times New Roman' color='#000000'>Teaching Assistant</font></b></td>" +
            String.format("<td colspan='8' align='left' valign='bottom'><font face='Times New Roman' color='#000000'>%s, Room #: %s</font></td>",
             ta, taOffice) +
            "</tr>";
    }
    private String schedules(List<String> scheduleLines){
        String res = "<tr>"+
		"<td colspan='2' height='18' align='left' valign='middle'><b><font face='Times New Roman' color='#000000'>Schedule:</font></b></td>" +
        "<td colspan='8' rowspan='1' align='left' valign='top'><font face='Times New Roman' color='#000000'><br>";
        for (int i =0; i < scheduleLines.size();i++){
            String s = scheduleLines.get(i);
            res +=  String.format("%02d.", i) + s + "<br>";
        }
        res += "</font></td></tr>";
        return res;
    }
    private String textbooks(List<String> textbooks){
        String res = "<tr>"+
		"<td colspan='2' height='18' align='left' valign='middle'><b><font face='Times New Roman' color='#000000'>Textbooks:</font></b></td>" +
        "<td colspan='8' rowspan='1' align='left' valign='top'><font face='Times New Roman' color='#000000'><br>";
        // for (String s : textbooks){
        //     res +=  s + "<br>";
        for (int i =0; i < textbooks.size();i++){
            String s = textbooks.get(i);
            res +=  String.format("%02d.", i) + s + "<br>";
        }
        res += "</font></td></tr>";
        return res;
    }

    private String references(List<String> references){
        String res = "<tr>"+
		"<td colspan='2' height='18' align='left' valign='middle'><b><font face='Times New Roman' color='#000000'>References:</font></b></td>" +
        "<td colspan='8' rowspan='1' align='left' valign='top'><font face='Times New Roman' color='#000000'><br>";
        // for (String s : references){
        for (int i =0; i < references.size();i++){
            String s = references.get(i);
            res +=  String.format("%02d.", i) + s + "<br>";
        }
        res += "</font></td></tr>";
        return res;
    }
    
    private String dgroup(String dGroup){
        return 
        "<tr>" + 
		"<td colspan='2' height='18' align='left' valign='middle'><b><font face='Times New Roman' color='#000000'>Discussion Group</font></b></td>" + 
		String.format("<td colspan='8' align='left' valign='bottom' sdval='0' sdnum='1033;'><font face='Times New Roman' color='#000000'>%s</font></td>", dGroup) + 
	    "</tr>";
    }

    private String topics(List<String> topics){
        String res = "<tr>"+
		"<td colspan='2' height='18' align='left' valign='middle'><b><font face='Times New Roman' color='#000000'>Topics:</font></b></td>" +
        "<td colspan='8' rowspan='1' align='left' valign='top'><font face='Times New Roman' color='#000000'><br>";
        // for (String s : topics){
        for (int i =0; i < topics.size();i++){
            String s = topics.get(i);
            res +=  String.format("%02d.", i) + s + "<br>";
        }
        res += "</font></td></tr>";
        return res;
    }
    private String notesHTML(List<String> Notes){
        String res = "<tr>"+
        "<td colspan='3' height='18' align='left' valign='bottom'><b><font face='Times New Roman' color='#000000'>Notes</font></b></td>" +
        "<td align='left' valign='bottom'><font face='Times New Roman' color='#000000'><br></font></td>" +
        "</tr>" +
        "<tr>" +
        "<td height='18' align='left' valign='bottom'><font face='Times New Roman' color='#000000'><br></font></td>" +
        "<td colspan='9' rowspan='1' align='left' valign='top'><font face='Times New Roman' color='#000000'>";
        for (int i =0; i < Notes.size();i++){
            String s = Notes.get(i);
            res +=  String.format("%02d.", i) + s + "<br>";
        }
        return res + "</font></td></tr>";
    }
    private String closHTML(List<String> defList) {
        String res = 
            "<tr height='18' ></tr>" +
            "<tr>" +
            "<td colspan='10' height='18' align='left' valign='middle'><b><font face='Times New Roman' color='#000000'>Course Learning Objectives</font></b></td>" +
            "</tr>" +
            "<tr>" +
            "<td colspan='10' height='27' align='left' valign='top'><font face='Times New Roman' color='#000000'>Upon the completion of this course, students should be able to:</font></td>" +
            "</tr>" +
            "<tr>" +
            "<td height='18' align='left' valign='bottom'><font face='Times New Roman' color='#000000'><br></font></td>" +
            "<td colspan='9' rowspan='1' align='left' valign='top'><font face='Times New Roman' color='#000000'>";
        ;
        for (int i =0; i < defList.size();i++){
            String s = defList.get(i);
            res +=  String.format("%02d.", i) + s + "<br>";
        }
        return res + "</font></td></tr><tr height='18' ></tr>";
    }
    private String sosHTML(List<String> defList) {
        String res = 
        "<tr>" +
        "<td colspan='10' height='22' align='left' valign='middle'><b><font face='Times New Roman' size='3' color='#000000'>Course addresses the following program outcomes:</font></b></td>" +
        "</tr>" +
        "<tr>" +
        "<td height='18' align='left' valign='left'><font face='Times New Roman' color='#000000'>";
        for (int i = 0; i < defList.size();i++){
            res += String.format("SO %02d, ", i);
        }
        return res + "</font></td> </tr> <tr height='18' ></tr>";
    }
    private String getSignature(String instName){
        return String.format("<tr>" +
        "<td colspan='7' height='18' align='left' valign='middle'><font face='Times New Roman' color='#000000'>Prepared by %s, %s.</font></td>" +
        "</tr>", instName, new SimpleDateFormat("MMMM yyyy").format(new Date())) ;
    }
    private String gradeHTML(List<String> category, List<String> grade) {
        String res = 	
        "<tr height='18' ></tr>" +
        "<tr>" +
        "<td colspan='2' height='18' align='left' valign='middle'><b><font face='Times New Roman' color='#000000'>Grading</font></b></td>" +
        "<td colspan='2' align='left' valign='middle'><font face='Times New Roman'></font></td>" +
        "<td align='right' valign='middle' sdval='5' sdnum='1033;'><font face='Times New Roman' color='#000000'></font></td>" +
        "<td align='left' valign='middle'><b><font face='Times New Roman' color='#000000'></font></b></td>" +
        "</tr>";
        for (int i =0; i < category.size();i++){
            String cat = category.get(i);
            String grd = grade.get(i);
            res +=  String.format(
        "<tr>" +
        "<td height='18' align='left' valign='middle'><br></td>" +
        "<td colspan='2' align='left' valign='middle'><font face='Times New Roman'>%s</font></td>" +
        "<td align='right' valign='middle' sdval='5' sdnum='1033;'><font face='Times New Roman' color='#000000'>%s</font></td>" +
        "<td align='left' valign='middle'><b><font face='Times New Roman' color='#000000'>%%</font></b></td>" +
        "</tr>" , cat, grd );
        }
        return res;
    }
}