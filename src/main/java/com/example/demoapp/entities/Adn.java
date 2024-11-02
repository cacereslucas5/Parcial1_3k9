package com.example.demoapp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
//import org.hibernate.envers.Audited;

import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
//@Audited

@Table(name="Adn")
public class Adn extends Base{

    @Column(name="adn_values")
    private String adnList;

    private boolean isMutant;

    public  void setAdnList(List<String> adnList){
        this.adnList =String.join(",", adnList);
        this.isMutant =esMutante(adnList);
    }


    public List<String> getAdnList() {
        return Arrays.asList(this.adnList.split(",")); // Convertir a List
    }
    public String getAdnListAsString(){
        return this.adnList;
    }


    public boolean esMutante(List<String> dna){
        if (dna==null || dna.size() == 0) {
            throw new IllegalArgumentException("La lista no puede ser vacia");
        }

        int n = dna.size();
        String caracteresPermitidos = "ATCG";

        for (String secuencia : dna) {
            if (secuencia.length() != n) {
                throw new IllegalArgumentException("La matriz debe ser de NxN");
            }
            for (char c : secuencia.toCharArray()) {
                if (caracteresPermitidos.indexOf(c) == -1) {

                    throw new IllegalArgumentException("La secuencia de ADN contiene caracteres no válidos: " + c);
                }
            }
        }

        int cont = 0;
        char secuenciaEncon='0';

        for (int i = 0; i < n; i++) {
            for (int f = 0; f < n; f++) {
                if ((f + 3) < n && dna.get(i).charAt(f) != secuenciaEncon && dna.get(i).charAt(f) == dna.get(i).charAt(f + 3) && dna.get(i).charAt(f) == dna.get(i).charAt(f + 2) && dna.get(i).charAt(f) == dna.get(i).charAt(f + 1)) {
                    secuenciaEncon=dna.get(i).charAt(f);
                    cont++;

                    if(cont>1){return true;}
                }
                if ((f + 3) < n && dna.get(f).charAt(i) != secuenciaEncon && dna.get(f).charAt(i) == dna.get(f + 3).charAt(i) && dna.get(f).charAt(i) == dna.get(f + 2).charAt(i) && dna.get(f).charAt(i) == dna.get(f + 1).charAt(i)) {
                    secuenciaEncon=dna.get(f).charAt(i);
                    cont++;

                    if(cont>1){return true;}
                }
                if ((i + 3) < n && (f + 3) < n && dna.get(f).charAt(i) != secuenciaEncon && dna.get(f).charAt(i) == dna.get(f + 3).charAt(i + 3) && dna.get(f).charAt(i) == dna.get(f + 2).charAt(i + 2) && dna.get(f).charAt(i) == dna.get(f + 1).charAt(i + 1)) {
                    secuenciaEncon=dna.get(f).charAt(i);
                    cont++;

                    if(cont>1){return true;}
                }

                if ((i - 3) >= 0 && (f + 3) < n && dna.get(f).charAt(i) != secuenciaEncon && dna.get(f).charAt(i) == dna.get(f + 3).charAt(i - 3) && dna.get(f).charAt(i) == dna.get(f + 2).charAt(i - 2) && dna.get(f).charAt(i) == dna.get(f + 1).charAt(i - 1)) {
                    secuenciaEncon=dna.get(f).charAt(i);
                    cont++;

                    if(cont>1){return true;}
                }
            }
        }

        return false;
    }

}
