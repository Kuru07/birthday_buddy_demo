package com.example.birthdaybuddy;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
     private List<DataClass> list;
     private Context context;
     public Adapter(Context context, List<DataClass> list){
         this.list = list;
         this.context = context;
     }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvname, tvdate;
        ShapeableImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvname = itemView.findViewById(R.id.tvname);
            tvdate = itemView.findViewById(R.id.tvdate);
            imageView = itemView.findViewById(R.id.imageview);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataClass item = list.get(position);
        holder.tvname.setText(item.getName());
        holder.tvdate.setText(item.getBirthday());
        holder.imageView.setImageURI(Uri.parse(item.getImagepath()));

        holder.itemView.setOnLongClickListener(view -> {
            showpopupmenu(view, position);
            return true;
        });
    }
    private void showpopupmenu(View view, int position){
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.popupmenu);
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.delete)
                deletedata(position);
            return true;
        });
        popupMenu.show();
    }
    private void deletedata(int position){
        SharedPreferences sharedPreferences = context.getSharedPreferences("BirthdayData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int count = sharedPreferences.getInt("count",0);
        if (count == 0 || position>count){
            return;
        }
        editor.remove("name"+position+1);
        editor.remove("birthday"+position+1);
        editor.remove("imagePath"+position+1);

        for (int i =position; i < count; i++){
            String nextname = sharedPreferences.getString("name"+(i+2),null);
            String nextdate = sharedPreferences.getString("birthday"+(i+2),null);
            String nextimage = sharedPreferences.getString("imagePath"+(i+2),null);
            if(nextname != null);{
                editor.putString("name"+(i+1),nextname);
                editor.putString("birthday"+(i+1),nextdate);
                editor.putString("imagePath"+(i+1),nextimage);
            }
        }
        editor.remove("name"+count);
        editor.remove("birthday"+count);
        editor.remove("imagePath"+count);
        editor.putInt("count",--count);
        editor.apply();
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,list.size());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
