package com.example.emsi_notif_sujet4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.emsi_notif_sujet4.Dao.DatabaseHelper;

public class treatementActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    private EditText informationDocument;
    private Spinner filiereDocument,niveauDocument,groupeDocument;
    private Button createNote;
    private ImageView precImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatement);

        informationDocument=(EditText) findViewById(R.id.information_document);
        filiereDocument=(Spinner)findViewById(R.id.filiere_document);
        niveauDocument=(Spinner)findViewById(R.id.niveau_document);
        groupeDocument=(Spinner) findViewById(R.id.groupe_document);
        precImage=(ImageView) findViewById(R.id.precDoc);
        createNote=(Button)findViewById(R.id.createDocument);
        myDb=new DatabaseHelper(this);


        precImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(treatementActivity.this,viewItemsActivity.class);
                startActivity(intent);

            }
        });

        createNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insererDocument();
            }
        });

    }
    private void insererDocument()
    {
        String information=informationDocument.getText().toString();
        String filiere=String.valueOf(filiereDocument.getSelectedItem());
        String niveau=String.valueOf(niveauDocument.getSelectedItem());
        String groupe=String.valueOf(groupeDocument.getSelectedItem());

        if(TextUtils.isEmpty(information)) Toast.makeText(this, "le champs Information est vide !", Toast.LENGTH_SHORT).show();
        else if(filiere.equals("filiere...")) Toast.makeText(this, "le champs filiere est vide !", Toast.LENGTH_SHORT).show();
        else if(niveau.equals("niveau...")) Toast.makeText(this, "le champs niveau est vide !", Toast.LENGTH_SHORT).show();
        else if(groupe.equals("groupe...")) Toast.makeText(this, "le champs groupe est vide !", Toast.LENGTH_SHORT).show();

        else {

            insertionData(information, filiere, niveau, groupe);
        }
    }
    private void insertionData(String info,String fil,String level,String grp)
    {

        System.out.println(" l'editeur de cette Note est  : "+SignInActivity.currentUserEd);
        boolean resultatReq=myDb.insertDataDocument(info,SignInActivity.currentUserEd,grp,fil,level);
        if(resultatReq==true)
        {
            System.out.println(" l'editeur de cette Note est  : "+SignInActivity.currentUserEd);

            Toast.makeText(treatementActivity.this,"Note Enregistrer !",Toast.LENGTH_LONG).show();


             Intent intent1 = new Intent(treatementActivity.this,viewItemsActivity.class);
            startActivity(intent1);
        }
        else{
            showMessage("Erreur ","la Note Que vous avez Ecrit n'est pas enregistrer ... Ressayer Une autre fois ");

        }
    }
    public void showMessage(String titre,String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(titre);
        builder.setMessage(message);
        builder.show();
    }

}
