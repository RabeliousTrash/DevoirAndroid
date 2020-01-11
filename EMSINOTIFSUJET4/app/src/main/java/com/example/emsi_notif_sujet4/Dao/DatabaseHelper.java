package com.example.emsi_notif_sujet4.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.emsi_notif_sujet4.Models.Document;
import com.example.emsi_notif_sujet4.Models.Personne;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static  final String DATABASE_NAME="NOTIFEMSI.db";
    public static  final String TABLE_NAME="PERSONNES";
    public static final String  TABLE_NAME2="NOTES";
    public static  final String COL_ID="ID";
    public static  final String COL_USERNAME="USERNAME";
    public static  final String COL_PASSWORD="PASSWORD";
    public static  final String COL_EMAIL="EMAIL";
    public static  final String COL_AGE="AGE";
    public static  final String COL_FULLNAME="FULLNAME";
    public static  final String COL_GROUPE="GROUPE";
    public static  final String COL_FILIERE="FILIERE";
    public static  final String COL_NIVEAU="NIVEAU";
    public static  final String COL_TYPEPERSONNE="TYPEPERSONNE";
    //----------------------------------------------------------
    public static final String COL_INFORMATION="INFORMATION";
    public static final String COL_DOCUMENT="DOCUMENT";
    public static final String COL_EDITEUR="EDITEUR";
    public static final String COL_GROUPE_DOCUMENT="GROUPE";
    public static final String COL_FILIERE_DOCUMENT="FILIERE";
    public static final String COL_NIVEAU_DOCUMENT="NIVEAU";
    public static final String COL_DATECREATION="DATECREATION";


    public DatabaseHelper(@Nullable Context context) {
        super(context,DATABASE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_NAME +"(ID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME TEXT,PASSWORD TEXT,EMAIL TEXT,AGE INTEGER,FULLNAME TEXT,GROUPE TEXT,FILIERE TEXT,NIVEAU TEXT,TYPEPERSONNE TEXT)");
        db.execSQL("create table "+ TABLE_NAME2 +"(ID INTEGER PRIMARY KEY AUTOINCREMENT,INFORMATION TEXT,EDITEUR TEXT,GROUPE TEXT,FILIERE TEXT,NIVEAU TEXT,DATECREATION DATE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        onCreate(db);

    }

    // traitement  Table Document    Insert Document
    public boolean insertDataDocument(String informationDocument,String editeurDocument,String groupeDocument,String filiereDocument,String niveauDocument)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_INFORMATION,informationDocument);
        contentValues.put(COL_EDITEUR,editeurDocument);
        contentValues.put(COL_GROUPE_DOCUMENT,groupeDocument);
        contentValues.put(COL_FILIERE_DOCUMENT,filiereDocument);
        contentValues.put(COL_NIVEAU_DOCUMENT,niveauDocument);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        contentValues.put(COL_DATECREATION,dateFormat.format(new Date()));


        long resultQuery=db.insert(TABLE_NAME2,null,contentValues);

        return (resultQuery==-1)?false:true;
    }

    public boolean updateDataDocument(Document newDocument)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_ID,newDocument.getId());
        contentValues.put(COL_INFORMATION,newDocument.getInformation());
        contentValues.put(COL_EDITEUR,newDocument.getEditeur());
        contentValues.put(COL_GROUPE_DOCUMENT,newDocument.getGroupe());
        contentValues.put(COL_FILIERE_DOCUMENT,newDocument.getFiliere());
        contentValues.put(COL_NIVEAU_DOCUMENT,newDocument.getNiveau());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        contentValues.put(COL_DATECREATION,dateFormat.format(new Date()));
        String id= String.valueOf(newDocument.getId());

        long resultQueryUpdate=db.update(TABLE_NAME2,contentValues,"ID = ?",new String[] {id});
        return resultQueryUpdate ==-1 ? false : true;
    }

    //delete document
    public boolean deleteDocument(Long id)
    {
        String idCurrent=String.valueOf(id);
        SQLiteDatabase db=this.getWritableDatabase();
        long results=db.delete(TABLE_NAME2,"ID = ?",new String[] {idCurrent});
        return results == -1 ? false : true;
    }

    //Selectionner tout les documents Par Niveau De personne
    public Cursor searchByLevelDocument(String niveauDoc)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor resultQuery=db.rawQuery("SELECT * FROM NOTES WHERE NIVEAU = ?", new String[] {niveauDoc});
        return resultQuery;
    }


    // Selectionner Tout Les documents
    public Cursor getAllDocumentData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor resultQuery=db.rawQuery("SELECT * FROM NOTES",null);
        return resultQuery;
    }

    // Chercher Document par Editeur
    public Cursor getDocumentByEditor(String editeurDocument)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor resultQuery=db.rawQuery("SELECT * FROM NOTES WHERE EDITEUR = ?", new String[] {editeurDocument});
        return resultQuery;
    }

    //selectionner Documnent par filiere
    public Cursor searchByFiliereDocument(String filiere)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor resultQuery=db.rawQuery("SELECT * FROM NOTES WHERE FILIERE = ?", new String[] {filiere});
        return resultQuery;
    }


    // traitement Table Personne  Insert Personne
    public boolean insertData(String username,String password,String email,int age,String fullname,String groupe,String filiere,String niveau, String typePersonne)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_USERNAME,username);
        contentValues.put(COL_PASSWORD,password);
        contentValues.put(COL_EMAIL,email);
        contentValues.put(COL_AGE,age);
        contentValues.put(COL_FULLNAME,fullname);
        contentValues.put(COL_GROUPE,groupe);
        contentValues.put(COL_FILIERE,filiere);
        contentValues.put(COL_NIVEAU,niveau);
        contentValues.put(COL_TYPEPERSONNE,typePersonne);

        long results=db.insert(TABLE_NAME,null,contentValues);

        return (results==-1)?false:true;
    }


    // Selectionner Toute les Personnes
    public Cursor getAllData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor results=db.rawQuery("SELECT * FROM PERSONNES",null);
        return results;
    }

    // Selectionner une personne Par Login et Mot de passe
    public Cursor searchByLoginAndPassword(String login,String password)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor results=db.rawQuery("SELECT * FROM PERSONNES WHERE USERNAME = ? AND PASSWORD = ?", new String[] {login,password});
        return results;
    }

    //Selectionner une personne Par email
    public Cursor searchByEmail(String email)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor results=db.rawQuery("SELECT * FROM PERSONNES WHERE EMAIL = ?", new String[] {email});
        return results;
    }
    //TODO:  Methode Update á developper Pour Personne
    public boolean updatePersonne(Personne personne)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_USERNAME,personne.getUsername());
        contentValues.put(COL_PASSWORD,personne.getPassword());
        contentValues.put(COL_EMAIL,personne.getEmail());
        contentValues.put(COL_AGE,personne.getAge());
        contentValues.put(COL_FULLNAME,personne.getFullname());
        contentValues.put(COL_GROUPE,personne.getGroupe());
        contentValues.put(COL_FILIERE,personne.getFiliere());
        contentValues.put(COL_NIVEAU,personne.getNiveau());
        contentValues.put(COL_TYPEPERSONNE,personne.getTypePersonne());
        String id= String.valueOf(personne.getId());

        long resultQueryUpdate=db.update(TABLE_NAME,contentValues,"ID = ?",new String[] {id});
        return resultQueryUpdate ==-1 ? false : true;

    }

}
