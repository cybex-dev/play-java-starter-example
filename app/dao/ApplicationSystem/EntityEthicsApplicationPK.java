package dao.ApplicationSystem;

import dao.NMU.EntityDepartment;
import models.ApplicationSystem.EthicsApplication;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Embeddable
public class EntityEthicsApplicationPK implements Serializable {
    private int applicationYear;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int applicationNumber;
    private String applicationType;
    private String departmentName;
    private String facultyName;

    @Column(name = "application_year")
    public int getApplicationYear() {
        return applicationYear;
    }

    public void setApplicationYear(int applicationYear) {
        this.applicationYear = applicationYear;
    }

    @Column(name = "application_number")
    public int getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(int applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    @Column(name = "application_type")
    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    @Column(name = "department_name")
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Column(name = "faculty_name")
    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityEthicsApplicationPK that = (EntityEthicsApplicationPK) o;
        return applicationYear == that.applicationYear &&
                applicationNumber == that.applicationNumber &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(facultyName, that.facultyName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(applicationYear, applicationNumber, applicationType, departmentName, facultyName);
    }

    public static dao.ApplicationSystem.EntityEthicsApplicationPK fromString(String id) {

        String[] split = id.split("_");
        Integer number = Integer.parseInt(split[0]);
        Integer year = Integer.parseInt(split[1]);

        EthicsApplication.ApplicationType type = EthicsApplication.ApplicationType.parse(split[2]);

        EntityDepartment dept = EntityDepartment.FromShortName(split[3]);

        List<EntityEthicsApplication> all = EntityEthicsApplication.find.all();
        Optional<EntityEthicsApplicationPK> first = all.stream()
                .filter(entityEthicsApplication -> {
                    int applicationYear = entityEthicsApplication.getApplicationYear();
                    int applicationNumber = entityEthicsApplication.getApplicationNumber();
                    EthicsApplication.ApplicationType applicationType = EthicsApplication.ApplicationType.parse(entityEthicsApplication.getApplicationType());
                    String department = entityEthicsApplication.getDepartmentName();
                    String facultyName = entityEthicsApplication.getFacultyName();

                    return number == applicationNumber &&
                            year == applicationYear &&
                            type.equals(applicationType) &&
                            facultyName.equals((dept == null) ? "Any" : dept.getFacultyName()) &&
                            department.equals(dept.getDepartmentName());
                })
                .map(EntityEthicsApplication::applicationPrimaryKey)
                .findFirst();
        return first.orElseThrow(null);
    }

    public String shortName(){
        EntityDepartment dept = EntityDepartment.findDepartmentByName(departmentName);
        if (dept == null){
            return null;
        }

        return applicationNumber + "_" + applicationYear + "_" + applicationType + "_" + dept.shortName();
    }
}
