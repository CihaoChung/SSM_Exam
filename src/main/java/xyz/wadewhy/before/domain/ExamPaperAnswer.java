package xyz.wadewhy.before.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.wadewhy.after.bus.domain.Question;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamPaperAnswer {

    private Question question;//试题集
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_exampaperanswer.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_exampaperanswer.examId
     *
     * @mbg.generated
     */
    private Integer examid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_exampaperanswer.examPaperId
     *
     * @mbg.generated
     */
    private Integer exampaperid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_exampaperanswer.studentId
     *
     * @mbg.generated
     */
    private Integer studentid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_exampaperanswer.questionId
     *
     * @mbg.generated
     */
    private Integer questionid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_exampaperanswer.answer
     *
     * @mbg.generated
     */
    private String answer;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bus_exampaperanswer.isCorrect
     *
     * @mbg.generated
     */
    private Integer iscorrect;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_exampaperanswer.id
     *
     * @return the value of bus_exampaperanswer.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_exampaperanswer.id
     *
     * @param id the value for bus_exampaperanswer.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_exampaperanswer.examId
     *
     * @return the value of bus_exampaperanswer.examId
     *
     * @mbg.generated
     */
    public Integer getExamid() {
        return examid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_exampaperanswer.examId
     *
     * @param examid the value for bus_exampaperanswer.examId
     *
     * @mbg.generated
     */
    public void setExamid(Integer examid) {
        this.examid = examid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_exampaperanswer.examPaperId
     *
     * @return the value of bus_exampaperanswer.examPaperId
     *
     * @mbg.generated
     */
    public Integer getExampaperid() {
        return exampaperid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_exampaperanswer.examPaperId
     *
     * @param exampaperid the value for bus_exampaperanswer.examPaperId
     *
     * @mbg.generated
     */
    public void setExampaperid(Integer exampaperid) {
        this.exampaperid = exampaperid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_exampaperanswer.studentId
     *
     * @return the value of bus_exampaperanswer.studentId
     *
     * @mbg.generated
     */
    public Integer getStudentid() {
        return studentid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_exampaperanswer.studentId
     *
     * @param studentid the value for bus_exampaperanswer.studentId
     *
     * @mbg.generated
     */
    public void setStudentid(Integer studentid) {
        this.studentid = studentid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_exampaperanswer.questionId
     *
     * @return the value of bus_exampaperanswer.questionId
     *
     * @mbg.generated
     */
    public Integer getQuestionid() {
        return questionid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_exampaperanswer.questionId
     *
     * @param questionid the value for bus_exampaperanswer.questionId
     *
     * @mbg.generated
     */
    public void setQuestionid(Integer questionid) {
        this.questionid = questionid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_exampaperanswer.answer
     *
     * @return the value of bus_exampaperanswer.answer
     *
     * @mbg.generated
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_exampaperanswer.answer
     *
     * @param answer the value for bus_exampaperanswer.answer
     *
     * @mbg.generated
     */
    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bus_exampaperanswer.isCorrect
     *
     * @return the value of bus_exampaperanswer.isCorrect
     *
     * @mbg.generated
     */
    public Integer getIscorrect() {
        return iscorrect;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bus_exampaperanswer.isCorrect
     *
     * @param iscorrect the value for bus_exampaperanswer.isCorrect
     *
     * @mbg.generated
     */
    public void setIscorrect(Integer iscorrect) {
        this.iscorrect = iscorrect;
    }


}