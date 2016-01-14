package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by Ololoev on 12.01.2016.
 */
public class Age
{
    public Age() {
    }

    public Age(Person person, String age) {
        this.person = person;
        this.age = age;
    }

    @ManyToOne
    public Person getPerson() {
        return person;
    }

    @Column(name = "age")
    public String getAge() {
        return age;
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setAge(String age) {
        this.age = age;
    }

    private Long id;
    private Person person;
    private String age;
}
