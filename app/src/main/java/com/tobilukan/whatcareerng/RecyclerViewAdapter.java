package com.tobilukan.whatcareerng;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by JUNED on 6/16/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;

    List<GetDataAdapter> getDataAdapter;

    ImageLoader imageLoader1;

    public RecyclerViewAdapter(List<GetDataAdapter> getDataAdapter, Context context){

        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {

        GetDataAdapter getDataAdapter1 =  getDataAdapter.get(position);

        imageLoader1 = ServerImageParseAdapter.getInstance(context).getImageLoader();

        imageLoader1.get(getDataAdapter1.getPostImageUrl(),
                ImageLoader.getImageListener(
                        Viewholder.PostImage,//Server Image
                        R.mipmap.ic_launcher,//Before loading server image the default showing image.
                        android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                )
        );

        Viewholder.cv.setId(getDataAdapter1.getPostID());
        Viewholder.PostTitle.setText(getDataAdapter1.getPostTitle());
        Viewholder.PostContent.setText(getDataAdapter1.getPostContent());
        Viewholder.PostImage.setImageUrl(getDataAdapter1.getPostImageUrl(), imageLoader1);

    }

    @Override
    public int getItemCount() {

        return getDataAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{


        public TextView PostTitle;
        public TextView PostContent;
        public NetworkImageView PostImage ;
        public CardView cv;

        public ViewHolder(View itemView) {

            super(itemView);


            PostTitle = (TextView) itemView.findViewById(R.id.tv_post_title) ;
            PostContent = (TextView) itemView.findViewById(R.id.tv_post_content) ;
            PostImage = (NetworkImageView) itemView.findViewById(R.id.iv_post_image) ;
            cv = (CardView) itemView.findViewById(R.id.cardview1);
        }
    }
}
