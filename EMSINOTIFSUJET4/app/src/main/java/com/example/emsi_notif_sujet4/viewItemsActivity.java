package com.example.emsi_notif_sujet4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emsi_notif_sujet4.Dao.DatabaseHelper;
import com.example.emsi_notif_sujet4.Models.Document;
import com.example.emsi_notif_sujet4.Models.DocumentAdapter;
import com.example.emsi_notif_sujet4.Models.DocumentAdapterCurrent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class viewItemsActivity extends AppCompatActivity {
    private ImageButton addImgDocument, messagingView,exitButton,exitButtonImage;
    private RecyclerView mRecyclerView,m1RecyclerView;
    private DocumentAdapter mAdapter;
    private DocumentAdapterCurrent m1Adapter;
    private RecyclerView.LayoutManager mLayoutManager,m1LayoutManager;
    private EditText mailDestText, subjectText, contentMessageText;
    private TextView send;
    private Button allNotes;
    DatabaseHelper myDb;
    public static ArrayList<Document> doc;
    public static String currentEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);

        messagingView = findViewById(R.id.messaging);
        addImgDocument = findViewById(R.id.imageButton);
        allNotes=findViewById(R.id.viewNotes);
        myDb = new DatabaseHelper(this);

        final ArrayList<Document> notesAvailabled = getNotes();

        doc=notesAvailabled;
        mRecyclerView = findViewById(R.id.recyleNotes);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new DocumentAdapter(notesAvailabled);



        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


//        Intent intent1 = getIntent();
//        if(intent1.getStringExtra("full")!= null) currentEditor = intent1.getStringExtra("full");
//        if(intent1.getStringExtra("foll") !=null) currentEditor = intent1.getStringExtra("foll");
//        if(intent1.getStringExtra("folll") !=null) currentEditor = intent1.getStringExtra("folll");
//        if(intent1.getStringExtra("follll") !=null) currentEditor = intent1.getStringExtra("follll");



        messagingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();

            }
        });


        allNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                seeNotes();

            }
        });


        mAdapter.setOnItemClickListener(new DocumentAdapter.OnItemClickListenner() {
            @Override
            public void onItemClick(int position) {
                Document doc = notesAvailabled.get(position);
                StringBuffer buffer = new StringBuffer();
                buffer.append(" \t\t\t\t Note \n\n");
                buffer.append("Information      : " + doc.getInformation() + "\n");
                buffer.append("Editeur          :" + doc.getEditeur() + "\n");
                buffer.append("filiere          :" + doc.getFiliere() + "\n");
                buffer.append("Groupe           :" + doc.getGroupe() + "\n");
                buffer.append("Niveau           :" + doc.getNiveau() + "\n");
                buffer.append("Date             :" + doc.getDateCreation() + "\n");
                showMessage("Affichage Note  numero " + position, buffer.toString());
            }
        });


        addImgDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = getIntent();
                System.out.println(" valeur de  l editeur : " + SignInActivity.currentUserEd);
                Intent intent = new Intent(viewItemsActivity.this, treatementActivity.class);
                startActivity(intent);
            }
        });
    }

    private void seeNotes()
    {

        final ArrayList<Document> editNotes=getNotesByEditor(SignInActivity.currentUserEd);
        final LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.exemple_item_admin, null);
        exitButton=view.findViewById(R.id.exitButton);
        m1RecyclerView=view.findViewById(R.id.recyleNotesAdmin);
        m1RecyclerView.setHasFixedSize(true);
        m1LayoutManager = new LinearLayoutManager(this);
        m1Adapter = new DocumentAdapterCurrent(editNotes);
        m1RecyclerView.setLayoutManager(m1LayoutManager);
        m1RecyclerView.setAdapter(m1Adapter);

        m1Adapter.setOnItemClickListener(new DocumentAdapterCurrent.OnItemClickListenner() {
            @Override
            public void onItemClick(int position) {
                Document doc = editNotes.get(position);
                showMessage("Affichage Note  numero " + position,doc.toString());


            }

            @Override
            public void onDeleteItemClick(int position) {
                Document doc=editNotes.get(position);
                System.out.println("id de l element a supprimer  : "+doc.getId());
                boolean etat=myDb.deleteDocument(doc.getId());
                if(etat == true) Toast.makeText(viewItemsActivity.this, "element Supprimer", Toast.LENGTH_SHORT).show();
                else Toast.makeText(viewItemsActivity.this, "element Non Supprimer ! ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(viewItemsActivity.this, viewItemsActivity.class);
                startActivity(intent);            }

            @Override
            public void onUpdateItemClick(int position) {

            }
        });


        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1=getIntent();
                System.out.println(" l editeur actuel est --->"+SignInActivity.currentUserEd);

                Intent intent = new Intent(viewItemsActivity.this, viewItemsActivity.class);
                startActivity(intent);
            }
        });

        AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).create();
        alertDialog.setCancelable(true);
        alertDialog.show();
        Intent intent1 = getIntent();
        System.out.println("l'editeur Current est  ---->"+SignInActivity.currentUserEd);



    }
    private void sendMail() {
        final LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.exemple_mail, null);
        mailDestText = view.findViewById(R.id.emailDestinataire);
        subjectText = view.findViewById(R.id.subject);
        contentMessageText = view.findViewById(R.id.contentMessage);
        send = view.findViewById(R.id.send);
        exitButtonImage=view.findViewById(R.id.exitButtonMail);

        exitButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewItemsActivity.this, viewItemsActivity.class);
                startActivity(intent);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mailDest = mailDestText.getText().toString();
                String subject = subjectText.getText().toString();
                String contentMessage = contentMessageText.getText().toString();

                if (TextUtils.isEmpty(mailDest))
                    Toast.makeText(inflater.getContext(), "veuillez Remplire Le Champ Email ", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(subject))
                    Toast.makeText(inflater.getContext(), "Veuillez saisir Le champ Subject ", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(contentMessage))
                    Toast.makeText(inflater.getContext(), "veuillez saisir un Message ! ", Toast.LENGTH_SHORT).show();

                else {
                    sendMailRecipe(mailDest, subject, contentMessage);
                }
            }
        });


        AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).create();
        alertDialog.setCancelable(true);
        alertDialog.show();

    }

    private void sendMailRecipe(String mail, String subject, String message) {
        Intent mEmailIntent = new Intent(Intent.ACTION_SEND);
        mEmailIntent.setData(Uri.parse("mailto:"));
        mEmailIntent.setType("text/plain");
        mEmailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{mail});
        mEmailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        mEmailIntent.putExtra(Intent.EXTRA_TEXT, message);


        try {

            startActivity(Intent.createChooser(mEmailIntent, "choisissez Un Client Email "));
            Toast.makeText(this, "mail Send Succefully ", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(viewItemsActivity.this, viewItemsActivity.class);
            startActivity(intent1);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<Document> getNotes() {
        ArrayList<Document> notes = new ArrayList<Document>();

        Cursor cursorDocument = myDb.getAllDocumentData();

        if (cursorDocument.getCount() == 0) {

            System.out.println("aucun Enregistrement");
        } else {
            while (cursorDocument.moveToNext()) {
                Document document = new Document();
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
                System.out.println("Premiere Date  :" + document.getDateCreation());
            }
        }
        return notes;

    }
    private ArrayList<Document> getNotesByEditor(String edi)
    {
        ArrayList<Document> notes = new ArrayList<Document>();

        Cursor cursorDocument = myDb.getDocumentByEditor(edi);

        if (cursorDocument.getCount() == 0) {

            System.out.println("aucun Enregistrement");
        } else {
            while (cursorDocument.moveToNext()) {
                Document document = new Document();
                document.setId(cursorDocument.getLong(0));
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
                System.out.println("Premiere Date  :" + document.getDateCreation());
            }
        }
        return notes;

    }


    public void showMessage(String titre, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(titre);
        builder.setMessage(message);
        builder.show();
    }
}
