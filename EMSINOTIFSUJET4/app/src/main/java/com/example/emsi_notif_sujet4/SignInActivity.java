package com.example.emsi_notif_sujet4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emsi_notif_sujet4.Dao.DatabaseHelper;
import com.example.emsi_notif_sujet4.Models.Personne;

public class SignInActivity extends AppCompatActivity {
    public static final String FULLNAME_PERSO = "com.example.emsi_notif_sujet4.FULLNAME_PERSO";

    private ImageView imgSin;
    private TextView buttonSignIn,resetPassword,resetButton;
    private EditText loginUser,passwordUser,emailReset,resetPasswordText,confirmTextPassword;
    DatabaseHelper myDb;
    public static String currentUserEd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        imgSin=(ImageView) findViewById(R.id.precedentSignIn);
        buttonSignIn=(TextView) findViewById(R.id.Connexion);
        loginUser=(EditText) findViewById(R.id.usrusr);
        passwordUser=(EditText) findViewById(R.id.pswrd);
        resetPassword=findViewById(R.id.pswOublier);
        myDb=new DatabaseHelper(this);



        imgSin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                authentification();

            }
        });
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });


    }

    private void showDialog()
    {
        final LayoutInflater inflater = LayoutInflater.from(this);
        View view= inflater.inflate(R.layout.exemple_reset_password,null);

        resetButton=view.findViewById(R.id.resetConnection);
        emailReset=view.findViewById(R.id.emailReset);
        resetPasswordText=view.findViewById(R.id.resetpassword);
        confirmTextPassword=view.findViewById(R.id.resetpasswordagain);


        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mailReset=emailReset.getText().toString();
                String rPassword=resetPasswordText.getText().toString();
                String cPassword=confirmTextPassword.getText().toString();

                if(TextUtils.isEmpty(mailReset))  Toast.makeText(inflater.getContext(), "le champs Email est Vide ", Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(rPassword)) Toast.makeText(inflater.getContext(), "le champ Password est vide !", Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(cPassword)) Toast.makeText(inflater.getContext(), "le champ Reset Password est vide !", Toast.LENGTH_SHORT).show();

                else{
                        if(rPassword.equals(cPassword))
                        {
                            Cursor cursormailReset=myDb.searchByEmail(mailReset);
                            Personne resetPersonne=new Personne();

                            if(cursormailReset.getCount() == 0)  showMessage("Reset Password ","Email saisie est incorrect ! ");
                            else
                            {
                                while(cursormailReset.moveToNext())
                                {
                                    resetPersonne.setId(cursormailReset.getLong(0));
                                    resetPersonne.setUsername(cursormailReset.getString(1));
                                    resetPersonne.setPassword(cursormailReset.getString(2));
                                    resetPersonne.setEmail(cursormailReset.getString(3));
                                    resetPersonne.setAge(cursormailReset.getInt(4));
                                    resetPersonne.setFullname(cursormailReset.getString(5));
                                    resetPersonne.setGroupe(cursormailReset.getString(6));
                                    resetPersonne.setFiliere(cursormailReset.getString(7));
                                    resetPersonne.setNiveau(cursormailReset.getString(8));
                                    resetPersonne.setTypePersonne(cursormailReset.getColumnName(9));

                                }

                                resetPersonne.setPassword(rPassword);
                                boolean updatePassword=myDb.updatePersonne(resetPersonne);

                                if(updatePassword == true)
                                    {
                                        Toast.makeText(inflater.getContext(), "Person Password Updated ! ", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignInActivity.this, SignInActivity.class);
                                        startActivity(intent);
                                    }


                            }

                        }else {
                            Toast.makeText(inflater.getContext(), "password Incorrect !", Toast.LENGTH_SHORT).show();
                        }
                    }

            }
        });

        AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).create();
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    private void authentification()
    {
        String login=loginUser.getText().toString();
        String password=passwordUser.getText().toString();

        if(TextUtils.isEmpty(login)) Toast.makeText(this, "le champ Username est vide !", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(password)) Toast.makeText(this, "le champs Password est Vide !", Toast.LENGTH_SHORT).show();
        else{
                verfierCompteUtilisateur(login,password);
            }

    }
    private void verfierCompteUtilisateur(String usernameCurrentPerson,String passwordCurrentPerson)
    {
        Cursor currentPerson=myDb.searchByLoginAndPassword(usernameCurrentPerson,passwordCurrentPerson);
        Personne currentUser= new Personne();

            if(currentPerson.getCount() == 0)
            {
                Toast.makeText(this, "Nom ou mdp Incorrect  ressayer Une autre fois ! ", Toast.LENGTH_SHORT).show();
                showMessage("Authentification ","Username ou Mot de passe Incorrect ! ");


                System.out.println(currentUser.getUsername() +" ---> "+currentUser.getPassword() );

            }
            else
            {
                while(currentPerson.moveToNext())
                {

                    currentUser.setUsername(currentPerson.getString(1));
                    currentUser.setPassword(currentPerson.getString(2));
                    currentUser.setFullname(currentPerson.getString(5));
                    currentUser.setTypePersonne(currentPerson.getString(9));
                }

                System.out.println(currentUser.getUsername() +" ---> "+currentUser.getPassword() +"------>"+currentUser.getFullname() );
                String full=currentUser.getFullname();
                currentUserEd=currentUser.getFullname();
                Toast.makeText(this, "Succes", Toast.LENGTH_SHORT).show();
                if(currentUser.getTypePersonne().equals("Etudiant"))
                {
                    Intent intent = new Intent(SignInActivity.this, viewItemsStudentActivity.class);
                    intent.putExtra("full",full);
                    startActivity(intent);
                }else
                {
                    Intent intent = new Intent(SignInActivity.this, viewItemsActivity.class);
                    intent.putExtra("full",full);
                    startActivity(intent);

                }

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
