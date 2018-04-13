package com.example.jordi.m03_uf6_realm.adapters;

import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.jordi.m03_uf6_realm.R;
import com.example.jordi.m03_uf6_realm.model.Persona;

import java.util.HashSet;
import java.util.Set;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class MyRecyclerViewAdapter extends RealmRecyclerViewAdapter<Persona, MyRecyclerViewAdapter.MyViewHolder> {

    private boolean inDeletionMode = false;
    private Set<Integer> countersToDelete = new HashSet<>();

    public MyRecyclerViewAdapter(OrderedRealmCollection<Persona> data) {
        super(data, true);
        // Only set this if the model class has a primary key that is also a integer or long.
        // In that case, {@code getItemId(int)} must also be overridden to return the key.
        // See https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html#hasStableIds()
        // See https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html#getItemId(int)
        setHasStableIds(true);
    }

    void enableDeletionMode(boolean enabled) {
        inDeletionMode = enabled;
        if (!enabled) {
            countersToDelete.clear();
        }
        notifyDataSetChanged();
    }

    Set<Integer> getCountersToDelete() {
        return countersToDelete;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Persona obj = getItem(position);

        holder.contactName.setText(obj.getNom());

    }

//    @Override
//    public long getItemId(int index) {
//        //noinspection ConstantConditions
//        return getItem(index).getDni();
//    }



    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView contactName;
        TextView contactSurname;
        AppCompatImageButton editButton;
        AppCompatImageButton deleteButton;

        MyViewHolder(View view) {
            super(view);
            contactName = view.findViewById(R.id.contact_name);
            contactSurname = view.findViewById(R.id.contact_surname);
            editButton = view.findViewById(R.id.edit_button);
            deleteButton = view.findViewById(R.id.delete_button);
        }
    }
}