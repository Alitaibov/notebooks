package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by user on 27.05.14.
 */
@Entity
public class Person
{
    public Person() {
    }

    public Person(String name)
    {
        this.name = name;
        this.phones = new HashSet<>();
        this.age = new Age();
    }

    public Person(String name, Set<Phone> phones, Age age) {
        this.name = name;
        this.phones = phones;
        this.age = age;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @OneToMany(mappedBy = "person")
    public Set<Phone> getPhones() {
        return phones;
    }

    @OneToMany(mappedBy = "person")
    public Age getAge() {
        return age;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;

        if (obj instanceof Person)
            return name.equals(((Person)obj).getName());

        return false;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    private Long id;
    private String name;
    private Set<Phone> phones;
    private Age age;
    //private String lastname;

}
