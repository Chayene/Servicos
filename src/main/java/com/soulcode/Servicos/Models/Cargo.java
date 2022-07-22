package com.soulcode.Servicos.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment do mysql
    private Integer idCargo;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column (nullable = false, length = 100)
    private String descricao;

    @Column (nullable = false)
    private Double salario;

//@JsonIgnore
//@ManyToOne
//@JoinColumn (name = "idFuncionario")
//private Funcionario funcionario;


    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }
}
