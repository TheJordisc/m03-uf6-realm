package com.example.jordi.m03_uf6_realm.adapters;

import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jordi.m03_uf6_realm.R;
import com.example.jordi.m03_uf6_realm.model.Persona;

import java.util.HashSet;
import java.util.Set;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class MyRecyclerViewAdapter extends RealmRecyclerViewAdapter<Persona, MyRecyclerViewAdapter.MyViewHolder> {

    private Set<Integer> countersToDelete = new HashSet<>();
    Realm realm;

    public MyRecyclerViewAdapter(OrderedRealmCollection<Persona> data) {
        super(data, true);
        // Only set this if the model class has a primary key that is also a integer or long.
        // In that case, {@code getItemId(int)} must also be overridden to return the key.
        // See https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html#hasStableIds()
        // See https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html#getItemId(int)
        setHasStableIds(true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Persona obj = getItem(position);

        holder.contactName.setText(obj.getNom());
        holder.contactSurname.setText(obj.getCognom());
        holder.contactDNI.setText(obj.getDni());
        if (obj.getGenere().equals("M"))
            holder.contactGenere.setText("Home");
        else {
            holder.contactGenere.setText("Dona");
        }
        holder.contactDataNaixement.setText(obj.getEdat() + " anys");

        realm = Realm.getDefaultInstance();
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<Persona> result = realm.where(Persona.class).findAll();
                        result.deleteFromRealm(position);
                    }
                });
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        Persona persona = MyRecyclerViewAdapter.super.getItem(position);
                        //Intent editPersona = new Intent(MyRecyclerViewAdapter.this, ActivityNewContact.class)
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return super.getData().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView contactName;
        TextView contactSurname;
        TextView contactDNI;
        TextView contactGenere;
        TextView contactDataNaixement;
        AppCompatImageButton editButton;
        AppCompatImageButton deleteButton;

        MyViewHolder(View view) {
            super(view);
            contactName = view.findViewById(R.id.contact_name);
            contactSurname = view.findViewById(R.id.contact_surname);
            contactDNI = view.findViewById(R.id.contact_dni);
            contactGenere = view.findViewById(R.id.contact_genere);
            contactDataNaixement= view.findViewById(R.id.contact_data_naixement);
            editButton = view.findViewById(R.id.edit_button);
            deleteButton = view.findViewById(R.id.delete_button);
        }
    }
}