package com.extoxesses.flowershop.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="flower")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Flower implements Serializable {

    private static final long serialVersionUID = 5246812518358784875L;

    @Id
    private String code;
    @NonNull
    private String name;
    @OneToMany(mappedBy = "flowerCode", fetch = FetchType.LAZY)
    List<Bundle> bundles;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Bundle> getBundles() {
        return bundles;
    }

    public void setBundles(List<Bundle> bundles) {
        this.bundles = bundles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Flower flower = (Flower) o;
        return Objects.equals(code, flower.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

}
