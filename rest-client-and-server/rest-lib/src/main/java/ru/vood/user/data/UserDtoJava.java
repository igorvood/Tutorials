package ru.vood.user.data;

import java.util.Objects;
import java.util.Set;

@Deprecated
public class UserDtoJava {

    private String name;
    private Integer age;
    private Set<UserDtoJava> relation;

    public UserDtoJava(String name, Integer age, Set<UserDtoJava> relation) {
        this.name = name;
        this.age = age;
        this.relation = relation;
    }

    public UserDtoJava() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<UserDtoJava> getRelation() {
        return relation;
    }

    public void setRelation(Set<UserDtoJava> relation) {
        this.relation = relation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDtoJava userDto = (UserDtoJava) o;
        return name.equals(userDto.name) &&
                age.equals(userDto.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
