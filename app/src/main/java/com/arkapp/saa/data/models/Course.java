package com.arkapp.saa.data.models;

import com.google.firebase.Timestamp;

/**
 * Created by Abdul Rehman on 24-07-2020.
 * Contact email - abdulrehman0796@gmail.com
 */
public class Course {
    String id;
    String title;
    String school;
    String outline;
    Timestamp startDate;
    Timestamp endDate;
    String duration;
    String fees;
    String category;
    String jobSector;

    public Course(String id, String title, String school, String outline,
                  Timestamp startDate, Timestamp endDate, String duration, String fees,
                  String category, String jobSector) {
        this.id = id;
        this.title = title;
        this.school = school;
        this.outline = outline;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.fees = fees;
        this.category = category;
        this.jobSector = jobSector;
    }

    public Course() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getJobSector() {
        return jobSector;
    }

    public void setJobSector(String jobSector) {
        this.jobSector = jobSector;
    }


}
