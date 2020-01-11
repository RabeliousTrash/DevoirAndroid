package com.example.emsi_notif_sujet4.Models;

public class Personne {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String fullname;
    private int age;
    private String filiere;
    private String niveau;
    private String groupe;
    private String departement;
    private String typePersonne;


    public Personne() { }

    public Personne(String username, String password, String email, String fullname, int age, String filiere, String niveau, String groupe, String departement, String typePersonne) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.age = age;
        this.filiere = filiere;
        this.niveau = niveau;
        this.groupe = groupe;
        this.departement = departement;
        this.typePersonne = typePersonne;
    }

    public Personne(Long id, String username, String password, String email, String fullname, int age, String filiere, String niveau, String groupe, String departement, String typePersonne) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.age = age;
        this.filiere = filiere;
        this.niveau = niveau;
        this.groupe = groupe;
        this.departement = departement;
        this.typePersonne = typePersonne;
    }

    public Personne(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getTypePersonne() {
        return typePersonne;
    }

    public void setTypePersonne(String typePersonne) {
        this.typePersonne = typePersonne;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFiliere() {
        return filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }
}
