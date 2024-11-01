package com.example.demoapp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Audited

@Table(name="Adn")
public class Adn extends Base{

    @Column(name="adn_values")
    private String adnList;

    private boolean isMutant;

    public  void setAdnList(List<String> adnList){
        this.adnList =String.join(",", adnList);
    }

    public List<String> getAdnList() {
        return Arrays.asList(this.adnList.split(",")); // Convertir a List
    }
    public String getAdnListAsString(){
        return this.adnList;
    }

}
