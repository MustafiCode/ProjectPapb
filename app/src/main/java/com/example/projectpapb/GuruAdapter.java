package com.example.projectpapb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class GuruAdapter extends RecyclerView.Adapter<GuruAdapter.CustomViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<Guru> guru;
    private Context context;

    public GuruAdapter(Context context){
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewtype) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.row_view, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String nama = guru.get(position).getNama();
        final String mapel = guru.get(position).getMapel();
        holder.editNama.setText(nama);
        holder.editMapel.setText(mapel);


        holder.btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(guru.get(position).getNama()==holder.editNama.getText().toString())
                {   guru.get(position).setNama(holder.editNama.getText().toString());
                    guru.get(position).setMapel(holder.editMapel.getText().toString());
                    updateitem(guru.get(position));
                }
                else{

                    deleteitem(guru.get(position).getNama());
                    guru.get(position).setNama(holder.editNama.getText().toString());
                    guru.get(position).setMapel(holder.editMapel.getText().toString());
                    updateitem(guru.get(position));

                }
                notifyDataSetChanged();

            }
        });
        holder.btndelete.setOnClickListener((View.OnClickListener) v -> {

            deleteitem(guru.get(position).getMapel());

            notifyDataSetChanged();

        });

        holder.cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // do something here !!
                return false;
            }
        });

    }


    private void updateitem(Guru guru) {
        //getting the specified dosen reference

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("guru").child(guru.getNama());


        dR.setValue(guru);


        Toast.makeText(context, "Guru Updated", Toast.LENGTH_SHORT).show();

    }


    private void deleteitem(String id) {
        //getting the specified dosen reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("guru").child(id);

        //removing data
        dR.removeValue();


        Toast.makeText(context, "Guru deleted", Toast.LENGTH_SHORT).show();

    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return guru.size();
    }

    public void addItem(ArrayList<Guru> mData) {
        this.guru = mData;
        notifyDataSetChanged();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView editNama, editMapel;
        private Button btnupdate, btndelete;
        private CardView cv;

        public CustomViewHolder(View itemView) {
            super(itemView);

            editNama = (TextView) itemView.findViewById(R.id.textView2);
            editMapel = (TextView) itemView.findViewById(R.id.textView3);
           // btnupdate = (Button) itemView.findViewById(R.id.btn_update);
           // btndelete = (Button) itemView.findViewById(R.id.btn_delete);
            //cv = (CardView) itemView.findViewById(R.id.cv);


        }

    }
}
