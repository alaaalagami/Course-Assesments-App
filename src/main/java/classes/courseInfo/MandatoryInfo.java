package classes.courseInfo;

import java.io.Serializable;

public class MandatoryInfo implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -1659409324357656737L;

  private String program;

  private String semester;

  private Integer academicYear;

  private String courseCode;

  private String courseTitle;

  private String instructorName;

  private String instructorEmail;

  private String tAName;

  private String tAEmail;

  public MandatoryInfo() {
    program = "";
    semester = "";
    academicYear = Integer.valueOf(2020);
    courseCode = "";
    courseTitle = "";
    instructorName = "";
    instructorEmail = "";
    tAName = "";
  }
  public MandatoryInfo(String program, String semester, int academicYear, String courseCode,
                     String courseTitle, String instructorName, String instructorEmail,
                     String tAName, String tAEmail) {
    this.program = program;
    this.semester = semester;
    this.academicYear = Integer.valueOf(academicYear);
    this.courseCode = courseCode;
    this.courseTitle = courseTitle;
    this.instructorName = instructorName;
    this.instructorEmail = instructorEmail;
    this.tAName = tAName;
    this.tAEmail = tAEmail;
  }

  public String getProgram() {
    return program;
  }

  public void setProgram(String program) {
    this.program = program;
  }

  public String getSemester() {
    return semester;
  }

  public void setSemester(String semester) {
    this.semester = semester;
  }

  public Integer getAcademicYear() {
    return academicYear;
  }

  public void setAcademicYear(Integer academicYear) {
    this.academicYear = academicYear;
  }

  public String getCourseCode() {
    return courseCode;
  }

  public void setCourseCode(String courseCode) {
    this.courseCode = courseCode;
  }

  public String getCourseTitle() {
    return courseTitle;
  }

  public void setCourseTitle(String courseTitle) {
    this.courseTitle = courseTitle;
  }

  public String getInstructorName() {
    return instructorName;
  }

  public void setInstructorName(String instructorName) {
    this.instructorName = instructorName;
  }

  public String getInstructorEmail() {
    return instructorEmail;
  }

  public void setInstructorEmail(String instructorEmail) {
    this.instructorEmail = instructorEmail;
  }

  public String getTAName() {
    return tAName;
  }

  public void setTAName(String tAName) {
    this.tAName = tAName;
  }

  public String getTAEmail() {
    return tAEmail;
  }

  public void setTAEmail(String tAEmail) {
    this.tAEmail = tAEmail;
  }

  @Override
  public String toString() {
    return "MandatoryInfo [academicYear=" + academicYear + ", courseCode=" + courseCode + ", courseTitle=" + courseTitle
        + ", instructorEmail=" + instructorEmail + ", instructorName=" + instructorName + ", program=" + program
        + ", semester=" + semester + ", tAEmail=" + tAEmail + ", tAName=" + tAName + "]";
  }
}