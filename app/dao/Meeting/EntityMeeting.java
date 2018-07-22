package dao.Meeting;

import io.ebean.Finder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "meeting", schema = "rech_system", catalog = "")
public class EntityMeeting {
    private Timestamp meetingDate;
    private String announcements;

    public static Finder<Timestamp, dao.Meeting.EntityMeeting> find = new Finder<>(dao.Meeting.EntityMeeting.class);

    @Id
    @Column(name = "meeting_date", nullable = false)
    public Timestamp getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Timestamp meetingDate) {
        this.meetingDate = meetingDate;
    }

    @Basic
    @Column(name = "announcements", nullable = true, length = 45)
    public String getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(String announcements) {
        this.announcements = announcements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityMeeting that = (EntityMeeting) o;
        return Objects.equals(meetingDate, that.meetingDate) &&
                Objects.equals(announcements, that.announcements);
    }

    @Override
    public int hashCode() {

        return Objects.hash(meetingDate, announcements);
    }
}
