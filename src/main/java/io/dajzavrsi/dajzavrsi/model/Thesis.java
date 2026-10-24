package io.dajzavrsi.dajzavrsi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

public class Thesis {
    private long id;
    private String title;
    private String studentName;
    private String status;

    public Thesis(Long id, String title, String studentName, String status){
        this.id=id;
        this.title=title;
        this.studentName=studentName;
        this.status=status;

    }
    public long getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getStudentName(){
        return studentName;
    }
    public String getStatus(){
        return status;
    }
}
