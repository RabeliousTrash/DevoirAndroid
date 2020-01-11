package com.example.emsi_notif_sujet4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.emsi_notif_sujet4.Dao.DatabaseHelper;
import com.example.emsi_notif_sujet4.Models.Document;
import com.example.emsi_notif_sujet4.Models.DocumentAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class viewItemsStudentActivity extends AppCompatActivity {
    private Spinner nivSpinner,filSpinner;
    private EditText editEditor;
    private Button searchNiv,searchFil,searchEd,searchAll;
    private RecyclerView mRecyclerView;
    private DocumentAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    DatabaseHelper myDb;
    public static ArrayList<Document> currentDoc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items_student);
        Intent intent1=getIntent();
        String editeur=intent1.getStringExtra("full");
        System.out.println(" valeur de  l editeur Student  : "+editeur);

        nivSpinner=findViewById(R.id.spinnerNivStudent);
        filSpinner=findViewById(R.id.spinnerFiliereStudent);
        editEditor=findViewById(R.id.editEditorText);

        searchNiv=findViewById(R.id.searchNiveauButton);
        searchFil=findViewById(R.id.searchImageButtonFiliere);
        searchEd=findViewById(R.id.searchEditorButton);
        searchAll=findViewById(R.id.searchAllButton);

        myDb=new DatabaseHelper(this);

        mRecyclerView=findViewById(R.id.recyleNotesStudent);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);



        searchNiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String niveau=String.valueOf(nivSpinner.getSelectedItem());
                if(niveau.equals("niveau...")) Toast.makeText(viewItemsStudentActivity.this, "saisissez Un Niveau ", Toast.LENGTH_SHORT).show();
                else
                {

                    final ArrayList<Document> notes=new ArrayList<Document>();

                    Cursor cursorDocument=myDb.searchByLevelDocument(niveau);

                    if(cursorDocument.getCount() == 0)
                    {

                        System.out.println("aucun Enregistrement");
                    }else
                    {
                        while(cursorDocument.moveToNext())
                        {
                            Document document=new Document();
                            document.setInformation(cursorDocument.getString(1));
                            document.setEditeur(cursorDocument.getString(2));
                            document.setGroupe(cursorDocument.getString(3));
                            document.setFiliere(cursorDocument.getString(4));
                            document.setNiveau(cursorDocument.getString(5));
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                document.setDateCreation(dateFormat.parse(cursorDocument.getString(6)));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            notes.add(document);
                            System.out.println("Premiere Date  :"+document.getDateCreation());
                        }
                    }
                    mAdapter=new DocumentAdapter(notes);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);


                    mAdapter.setOnItemClickListener(new DocumentAdapter.OnItemClickListenner() {
                        @Override
                        public void onItemClick(int position) {
                            Document doc=notes.get(position);
                            StringBuffer buffer = new StringBuffer();
                            buffer.append(" \t\t\t\t Note \n\n");
                            buffer.append("Information      : "+doc.getInformation()+"\n");
                            buffer.append("Editeur          :"+doc.getEditeur()+"\n");
                            buffer.append("filiere          :"+doc.getFiliere()+"\n");
                            buffer.append("Groupe           :"+doc.getGroupe()+"\n");
                            buffer.append("Niveau           :"+doc.getNiveau()+"\n");
                            buffer.append("Date             :"+doc.getDateCreation()+"\n");
                            showMessage("Affichage Note  numero "+position,buffer.toString());
                        }
                    });


                }
            }
        });



        searchFil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String filiere=String.valueOf(filSpinner.getSelectedItem());
                if(filiere.equals("filiere...")) Toast.makeText(viewItemsStudentActivity.this, "saisissez Une Filiére ", Toast.LENGTH_SHORT).show();
                else
                {

                    final ArrayList<Document> notes=new ArrayList<Document>();

                    Cursor cursorDocument=myDb.searchByFiliereDocument(filiere);

                    if(cursorDocument.getCount() == 0)
                    {

                        System.out.println("aucun Enregistrement");
                    }else
                    {
                        while(cursorDocument.moveToNext())
                        {
                            Document document=new Document();
                            document.setInformation(cursorDocument.getString(1));
                            document.setEditeur(cursorDocument.getString(2));
                            document.setGroupe(cursorDocument.getString(3));
                            document.setFiliere(cursorDocument.getString(4));
                            document.setNiveau(cursorDocument.getString(5));
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                document.setDateCreation(dateFormat.parse(cursorDocument.getString(6)));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            notes.add(document);
                            System.out.println("Premiere Date  :"+document.getDateCreation());
                        }
                    }
                    mAdapter=new DocumentAdapter(notes);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);


                    mAdapter.setOnItemClickListener(new DocumentAdapter.OnItemClickListenner() {
                        @Override
                        public void onItemClick(int position) {
                            Document doc=notes.get(position);
                            StringBuffer buffer = new StringBuffer();
                            buffer.append(" \t\t\t\t Note \n\n");
                            buffer.append("Information      : "+doc.getInformation()+"\n");
                            buffer.append("Editeur          :"+doc.getEditeur()+"\n");
                            buffer.append("filiere          :"+doc.getFiliere()+"\n");
                            buffer.append("Groupe           :"+doc.getGroupe()+"\n");
                            buffer.append("Niveau           :"+doc.getNiveau()+"\n");
                            buffer.append("Date             :"+doc.getDateCreation()+"\n");
                            showMessage("Affichage Note  numero "+position,doc.toString());
                        }
                    });


                }



            }
        });



        searchEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String editor=editEditor.getText().toString();
                if(TextUtils.isEmpty(editor)) Toast.makeText(viewItemsStudentActivity.this, "saisissez un Editeur ", Toast.LENGTH_SHORT).show();
                else
                {

                    final ArrayList<Document> notes=new ArrayList<Document>();

                    Cursor cursorDocument=myDb.getDocumentByEditor(editor);

                    if(cursorDocument.getCount() == 0)
                    {

                        System.out.println("aucun Enregistrement");
                    }else
                    {
                        while(cursorDocument.moveToNext())
                        {
                            Document document=new Document();
                            document.setInformation(cursorDocument.getString(1));
                            document.setEditeur(cursorDocument.getString(2));
                            document.setGroupe(cursorDocument.getString(3));
                            document.setFiliere(cursorDocument.getString(4));
                            document.setNiveau(cursorDocument.getString(5));
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                document.setDateCreation(dateFormat.parse(cursorDocument.getString(6)));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            notes.add(document);
                            System.out.println("Premiere Date  :"+document.getDateCreation());
                        }
                    }
                    mAdapter=new DocumentAdapter(notes);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);


                    mAdapter.setOnItemClickListener(new DocumentAdapter.OnItemClickListenner() {
                        @Override
                        public void onItemClick(int position) {
                            Document doc=notes.get(position);
                            StringBuffer buffer = new StringBuffer();
                            buffer.append(" \t\t\t\t Note \n\n");
                            buffer.append("Information      : "+doc.getInformation()+"\n");
                            buffer.append("Editeur          :"+doc.getEditeur()+"\n");
                            buffer.append("filiere          :"+doc.getFiliere()+"\n");
                            buffer.append("Groupe           :"+doc.getGroupe()+"\n");
                            buffer.append("Niveau           :"+doc.getNiveau()+"\n");
                            buffer.append("Date             :"+doc.getDateCreation()+"\n");
                            showMessage("Affichage Note  numero "+position,buffer.toString());
                        }
                    });


                }

            }
        });


        searchAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ArrayList<Document> notes=new ArrayList<Document>();

                Cursor cursorDocument=myDb.getAllDocumentData();

                if(cursorDocument.getCount() == 0)
                {

                    System.out.println("aucun Enregistrement");
                }else
                {
                    while(cursorDocument.moveToNext())
                    {
                        Document document=new Document();
                        document.setInformation(cursorDocument.getString(1));
                        document.setEditeur(cursorDocument.getString(2));
                        document.setGroupe(cursorDocument.getString(3));
                        document.setFiliere(cursorDocument.getString(4));
                        document.setNiveau(cursorDocument.getString(5));
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            document.setDateCreation(dateFormat.parse(cursorDocument.getString(6)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        notes.add(document);
                        System.out.println("Premiere Date  :"+document.getDateCreation());
                    }
                }
                mAdapter=new DocumentAdapter(notes);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);


                mAdapter.setOnItemClickListener(new DocumentAdapter.OnItemClickListenner() {
                    @Override
                    public void onItemClick(int position) {
                        Document doc=notes.get(position);
                        StringBuffer buffer = new StringBuffer();
                        buffer.append(" \t\t\t\t Note \n\n");
                        buffer.append("Information      : "+doc.getInformation()+"\n");
                        buffer.append("Editeur          :"+doc.getEditeur()+"\n");
                        buffer.append("filiere          :"+doc.getFiliere()+"\n");
                        buffer.append("Groupe           :"+doc.getGroupe()+"\n");
                        buffer.append("Niveau           :"+doc.getNiveau()+"\n");
                        buffer.append("Date             :"+doc.getDateCreation()+"\n");
                        showMessage("Affichage Note  numero "+position,buffer.toString());
                    }
                });



            }
        });

    }

    private void showMessage(String titre,String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(titre);
        builder.setMessage(message);
        builder.show();
    }
}


