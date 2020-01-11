package com.example.emsi_notif_sujet4.Models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emsi_notif_sujet4.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder> {
    private ArrayList<Document> documentArrayList;
    private OnItemClickListenner mListener;
    public interface OnItemClickListenner
    {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListenner listener)
    {
        mListener=listener;
    }
    public static class DocumentViewHolder extends RecyclerView.ViewHolder
    {
        public TextView filiereText,editorText,dateText,typeNivText;
    //Declaration des Champs qui se trouve Dans le layout Example_item
        public DocumentViewHolder(@NonNull View itemView, final OnItemClickListenner listenner) {
            super(itemView);
            filiereText=itemView.findViewById(R.id.textViewFiliere);
            editorText=itemView.findViewById(R.id.namePersonne);
            dateText=itemView.findViewById(R.id.dateCreation);
            typeNivText=itemView.findViewById(R.id.textViewNiveauPersonne);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(listenner != null)
                    {
                        int position=getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listenner.onItemClick(position);
                        }
                    }
                }
            });
        }
    }


    public DocumentAdapter(ArrayList<Document> documents) {
        documentArrayList=documents;
    }

    @NonNull
    @Override
    //Relier la page XML Concu Avec le Recycler Vue
    public DocumentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.exemple_item,parent,false);
        DocumentViewHolder doc=new DocumentViewHolder(v,mListener);
        return doc;
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentViewHolder holder, int position) {
        Document currentDocument= documentArrayList.get(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        holder.filiereText.setText(currentDocument.getFiliere());
        holder.editorText.setText(currentDocument.getEditeur());
        holder.dateText.setText(dateFormat.format(currentDocument.getDateCreation()));
        holder.typeNivText.setText(currentDocument.getNiveau());
    }

    @Override
    public int getItemCount() {
        return documentArrayList.size();
    }


}
