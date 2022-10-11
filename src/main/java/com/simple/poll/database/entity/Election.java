package com.simple.poll.database.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.validator.constraints.Length;

import com.simple.poll.config.database.postgres.PostgreSQLEnumType;

@Entity
@Table(name = "elections")
@TypeDef(name = "state", typeClass = PostgreSQLEnumType.class)
@DynamicInsert(value = false)
@DynamicUpdate(value = true)
public class Election extends BaseEntity {
    
    @Column(nullable = false,name = "electionName", unique = true)
    @Length(min=1, max=50)
    private String name;
    
    @Type(type = "state")
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ElectionState")
    private ElectionState state;
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy="election", fetch = FetchType.EAGER)
    private List<Candidate> candidates;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ElectionState getState() {
        return state;
    }

    public void setState(ElectionState state) {
        this.state = state;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }
}
