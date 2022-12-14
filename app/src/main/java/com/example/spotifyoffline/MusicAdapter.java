package com.example.spotifyoffline;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder> {
    private Context mcontext;
    private ArrayList<MusicFiles> mfiles;
    MusicAdapter(Context mcontext,ArrayList<MusicFiles> mfiles)
    {
        this.mfiles=mfiles;
        this.mcontext=mcontext;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater= LayoutInflater.from(mcontext);
        view=layoutInflater.inflate(R.layout.music_items,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
    holder.file_name.setText(mfiles.get(position).getTitle());
    byte[] image=getAlbumArt(mfiles.get(position).getPath());
    if(image!=null)
    {
        Glide.with(mcontext).asBitmap().load(image).into(holder.album_art);
    }
    else
    {
     Glide.with(mcontext).load(R.drawable.ic_launcher_foreground).into(holder.album_art);
    }
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(mcontext,Player.class);
            intent.putExtra("position",position);
            mcontext.startActivity(intent);
        }
    });
    }

    @Override
    public int getItemCount() {
        return mfiles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView file_name;
        ImageView album_art;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            file_name=itemView.findViewById(R.id.file_name);
            album_art=itemView.findViewById(R.id.music_img);
        }
    }
    private byte[] getAlbumArt(String uri)
    {
        MediaMetadataRetriever retriever=new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art=retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }
}
