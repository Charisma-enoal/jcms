package com.jcms.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "jcms_agenda", schema = "jcms", catalog = "")
public class JcmsAgendaEntity {
    private long agendaId;
    private String userName;
    private Long agendaKey;
    private String agendaTitle;
    private Integer agendaAllDay;
    private Timestamp agendaStart;
    private Timestamp agendaEnd;
    private String agendaUrl;
    private String agendaClassName;
    private Integer agendaEditable;
    private String agendaSource;
    private String agendaColor;
    private String agendaBorderColor;
    private String agendaTextColor;
    private String agendaBackgroundColor;

    @Id
    @Column(name = "agendaId")
    public long getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(long agendaId) {
        this.agendaId = agendaId;
    }

    @Basic
    @Column(name = "userName")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "agendaKey")
    public Long getAgendaKey() {
        return agendaKey;
    }

    public void setAgendaKey(Long agendaKey) {
        this.agendaKey = agendaKey;
    }

    @Basic
    @Column(name = "agendaTitle")
    public String getAgendaTitle() {
        return agendaTitle;
    }

    public void setAgendaTitle(String agendaTitle) {
        this.agendaTitle = agendaTitle;
    }

    @Basic
    @Column(name = "agendaAllDay")
    public Integer getAgendaAllDay() {
        return agendaAllDay;
    }

    public void setAgendaAllDay(Integer agendaAllDay) {
        this.agendaAllDay = agendaAllDay;
    }

    @Basic
    @Column(name = "agendaStart")
    public Timestamp getAgendaStart() {
        return agendaStart;
    }

    public void setAgendaStart(Timestamp agendaStart) {
        this.agendaStart = agendaStart;
    }

    @Basic
    @Column(name = "agendaEnd")
    public Timestamp getAgendaEnd() {
        return agendaEnd;
    }

    public void setAgendaEnd(Timestamp agendaEnd) {
        this.agendaEnd = agendaEnd;
    }

    @Basic
    @Column(name = "agendaUrl")
    public String getAgendaUrl() {
        return agendaUrl;
    }

    public void setAgendaUrl(String agendaUrl) {
        this.agendaUrl = agendaUrl;
    }

    @Basic
    @Column(name = "agendaClassName")
    public String getAgendaClassName() {
        return agendaClassName;
    }

    public void setAgendaClassName(String agendaClassName) {
        this.agendaClassName = agendaClassName;
    }

    @Basic
    @Column(name = "agendaEditable")
    public Integer getAgendaEditable() {
        return agendaEditable;
    }

    public void setAgendaEditable(Integer agendaEditable) {
        this.agendaEditable = agendaEditable;
    }

    @Basic
    @Column(name = "agendaSource")
    public String getAgendaSource() {
        return agendaSource;
    }

    public void setAgendaSource(String agendaSource) {
        this.agendaSource = agendaSource;
    }

    @Basic
    @Column(name = "agendaColor")
    public String getAgendaColor() {
        return agendaColor;
    }

    public void setAgendaColor(String agendaColor) {
        this.agendaColor = agendaColor;
    }


    @Basic
    @Column(name = "agendaBorderColor")
    public String getAgendaBorderColor() {
        return agendaBorderColor;
    }

    public void setAgendaBorderColor(String agendaBorderColor) {
        this.agendaBorderColor = agendaBorderColor;
    }

    @Basic
    @Column(name = "agendaTextColor")
    public String getAgendaTextColor() {
        return agendaTextColor;
    }

    public void setAgendaTextColor(String agendaTextColor) {
        this.agendaTextColor = agendaTextColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JcmsAgendaEntity that = (JcmsAgendaEntity) o;
        return agendaId == that.agendaId &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(agendaKey, that.agendaKey) &&
                Objects.equals(agendaTitle, that.agendaTitle) &&
                Objects.equals(agendaAllDay, that.agendaAllDay) &&
                Objects.equals(agendaStart, that.agendaStart) &&
                Objects.equals(agendaEnd, that.agendaEnd) &&
                Objects.equals(agendaUrl, that.agendaUrl) &&
                Objects.equals(agendaClassName, that.agendaClassName) &&
                Objects.equals(agendaEditable, that.agendaEditable) &&
                Objects.equals(agendaSource, that.agendaSource) &&
                Objects.equals(agendaColor, that.agendaColor) &&
                Objects.equals(agendaBorderColor, that.agendaBorderColor) &&
                Objects.equals(agendaTextColor, that.agendaTextColor);
    }

    @Override
    public int hashCode() {

        return Objects.hash(agendaId, userName, agendaKey, agendaTitle, agendaAllDay, agendaStart, agendaEnd, agendaUrl, agendaClassName, agendaEditable, agendaSource, agendaColor, agendaBorderColor, agendaTextColor);
    }

    @Basic
    @Column(name = "agendaBackgroundColor")
    public String getAgendaBackgroundColor() {
        return agendaBackgroundColor;
    }

    public void setAgendaBackgroundColor(String agendaBackgroundColor) {
        this.agendaBackgroundColor = agendaBackgroundColor;
    }
}
