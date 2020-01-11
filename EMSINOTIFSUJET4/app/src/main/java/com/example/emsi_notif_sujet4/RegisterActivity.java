package com.example.emsi_notif_sujet4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emsi_notif_sujet4.Dao.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    private ImageView imgPrec;
    private TextView createPersonneView;
    private EditText fullnameText,emailText,usernameText,passwordText,ageText;
    private Spinner typePersonne,filierePersonne,groupePersonne,departementPersonne,niveauText;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //  les champs  qui identifie une personne

        imgPrec=(ImageView) findViewById(R.id.precRegister);
        createPersonneView=(TextView) findViewById(R.id.createRegister);
         fullnameText=(EditText)findViewById(R.id.fullnameRegister);
        emailText=(EditText)findViewById(R.id.mailRegister);
        usernameText=(EditText)findViewById(R.id.usernameRegister);
        passwordText=(EditText)findViewById(R.id.passwordRegister);
        ageText=(EditText)findViewById(R.id.ageRegister);

        // spinners

        typePersonne=(Spinner) findViewById(R.id.spinnertypePersoRegister);
        filierePersonne=(Spinner) findViewById(R.id.filiereRegister);
        groupePersonne=(Spinner) findViewById(R.id.groupeRegister);
        departementPersonne=(Spinner) findViewById(R.id.departementRegister);
        niveauText=(Spinner) findViewById(R.id.niveauRegister);


        // loading Bar
        loadingBar= new ProgressDialog(this);



        myDb=new DatabaseHelper(this);

        imgPrec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        createPersonneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RegisterPersonne();

            }
        });
    }

        private void RegisterPersonne()
        {
            String usernamePerso=usernameText.getText().toString();
            String fullname=fullnameText.getText().toString();
            String password=passwordText.getText().toString();
            String email=emailText.getText().toString();
            String niveau=String.valueOf(niveauText.getSelectedItem());
            String t_Personne=String.valueOf(typePersonne.getSelectedItem());
            String f_Personne=String.valueOf(filierePersonne.getSelectedItem());
            String g_Personne=String.valueOf(groupePersonne.getSelectedItem());
            String d_Personne=String.valueOf(departementPersonne.getSelectedItem());
            int age= Integer.parseInt(ageText.getText().toString());

            if(TextUtils.isEmpty(fullname)) Toast.makeText(RegisterActivity.this,"le champ fullname est Vide !",Toast.LENGTH_SHORT).show();
            else if(TextUtils.isEmpty(usernamePerso)) Toast.makeText(this,"le champ username est Vide !",Toast.LENGTH_SHORT).show();
            else if(TextUtils.isEmpty(password)) Toast.makeText(this,"le champ password est Vide !",Toast.LENGTH_SHORT).show();
            else if(TextUtils.isEmpty(email)) Toast.makeText(this,"le champ email est Vide !",Toast.LENGTH_SHORT).show();
            else if(niveau.equals("niveau...")) Toast.makeText(this,"le champ niveau est Vide !",Toast.LENGTH_SHORT).show();
            else if(t_Personne.equals("personnes...")) Toast.makeText(this,"Veuillez Selectionner Un type De personne",Toast.LENGTH_SHORT).show();
            else if(f_Personne.equals("filiere...")) Toast.makeText(this,"Veuillez Selectionnez Votre Filiere",Toast.LENGTH_SHORT).show();
            else if(g_Personne.equals("groupe...")) Toast.makeText(this,"Veuillez Selectionnez Votre Groupe",Toast.LENGTH_SHORT).show();
            else if(d_Personne.equals("departement...")) Toast.makeText(this,"Veuillez Selectionnez Votre Departement",Toast.LENGTH_SHORT).show();
            else if(age ==0) Toast.makeText(this,"Veuillez inserer  Votre Age",Toast.LENGTH_SHORT).show();

            else
            {
                loadingBar.setTitle("Inscription Personne");
                loadingBar.setMessage("patientez ... cela peut prendre quelque seconde l :D ");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                insertPersonne(usernamePerso,fullname,password,email,niveau,t_Personne,f_Personne,g_Personne,d_Personne,age);
            }



        }

        private void insertPersonne(String usernameInserted,String fullnameInserted,String passwordInserted,String emailInserted,
                                    String niveauInserted,String typePersonneInserted,String filierePersonneInserted,
                                    String groupePersonneInserted,String departementPersonneInserted,int ageInserted)

        {
            boolean isInserted=myDb.insertData(usernameInserted, passwordInserted,emailInserted,ageInserted,fullnameInserted,groupePersonneInserted
                                    ,filierePersonneInserted,niveauInserted,typePersonneInserted);

            if(isInserted == true)
            {
                System.out.println("---------------------------------------------------------------------------------------");
                System.out.println(" Requetete Ajout Personne  : Succes");
                System.out.println("---------------------------------------------------------------------------------------");

                Toast.makeText(RegisterActivity.this,"Personne Enregistrer !",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(RegisterActivity.this, SignInActivity.class);
                startActivity(intent);
            }
            else
            {
                System.out.println("---------------------------------------------------------------------------------------");
                System.out.println(" Requetete Ajout Personne  : Echec");
                System.out.println("---------------------------------------------------------------------------------------");
                showMessage("Inscription","la Personne N'est pas enregistrer ... Veuillez Ressayer Une nouvelle fois ");
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
