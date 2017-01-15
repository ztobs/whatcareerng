package com.tobilukan.whatcareerng;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tobilukan.whatcareerng.Base64CODEC;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PostViewHolder> {

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView postTitle;
        TextView postContent;
        ImageView postImage;


        PostViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            postTitle = (TextView)itemView.findViewById(R.id.post_title);
            postContent = (TextView)itemView.findViewById(R.id.post_content);
            postImage = (ImageView)itemView.findViewById(R.id.post_image);
        }
    }

    Bitmap bitmap;
    Base64CODEC base64CODEC;

    List<Post> posts;

    RVAdapter(List<Post> posts){
        this.posts = posts;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        PostViewHolder pvh = new PostViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PostViewHolder postViewHolder, int i) {
        postViewHolder.postTitle.setText(posts.get(i).postTitle);
        postViewHolder.postContent.setText(posts.get(i).postContent);
        try {
            URL url = new URL(posts.get(i).postImage);
            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            postViewHolder.postImage.setImageBitmap(image);
        } catch(IOException e) {
            System.out.println(e);
        }
        //postViewHolder.postImage.setImageResource(R.drawable.careerwoman);

        //base64CODEC = new Base64CODEC();
        //bitmap = base64CODEC.Base64ImageFromURL(posts.get(i).postImage);
        //postViewHolder.postImage.setImageBitmap(bitmap);




    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


}
