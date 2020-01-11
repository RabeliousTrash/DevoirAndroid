package com.example.emsi_notif_sujet4.Models;

import java.util.Date;

public class Document {
    private Long id;
    private String information;

    private String editeur;
    private String groupe;
    private String filiere;
    private String niveau;
    private Date dateCreation;

    public Document() {
    }

    public Document(String editeur, String filiere, Date dateCreation) {
        this.editeur = editeur;
        this.filiere = filiere;
        this.dateCreation = dateCreation;
    }

    public Document(String information, String editeur, String groupe, String filiere, String niveau, Date dateCreation) {
        this.information = information;
        this.editeur = editeur;
        this.groupe = groupe;
        this.filiere = filiere;
        this.niveau = niveau;
        this.dateCreation = dateCreation;
    }

    public Document(String information, String editeur, String groupe, String filiere, String niveau) {
        this.information = information;
        this.editeur = editeur;
        this.groupe = groupe;
        this.filiere = filiere;
        this.niveau = niveau;
    }

    public Document(Long id, String information, String editeur, String groupe, String filiere, String niveau) {
        this.id = id;
        this.information = information;
        this.editeur = editeur;
        this.groupe = groupe;
        this.filiere = filiere;
        this.niveau = niveau;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }


    public String getEditeur() {
        return editeur;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
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


    @Override
    public String toString() {
        String str="";
        str+="\t - Information   =>\t"+information+"  \n\n";
        str+="\t - Editeur       =>\t"+editeur+"  \n\n";
        str+="\t - groupe        =>\t"+groupe+"   \n\n";
        str+="\t - filiere       =>\t"+filiere+"  \n\n";
        str+="\t - niveau        =>\t"+niveau+"   \n\n";
        str+="\t - Date          =>\t"+dateCreation+"  \n";
        return str;
    }
}
