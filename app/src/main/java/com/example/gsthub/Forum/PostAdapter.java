package com.example.gsthub.Forum;


import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsthub.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;


import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.myviewholder> {

    Context context;
    List<Post> postList;

    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_post, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        String uid = postList.get(position).getUid();
        String pEmail = postList.get(position).getpEmail();
        String pName = postList.get(position).getpName();
        String pTitle = postList.get(position).getpTitle();
        String pDescr = postList.get(position).getpDescr();
        String pImage = postList.get(position).getpImage();
        String pTimeStamp = postList.get(position).getpTime();
        String pId = postList.get(position).getpId();
        String pDp = postList.get(position).getpDp();

        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(Long.parseLong(pTimeStamp));
        String pTime = DateFormat.format("dd/mm/yyyy hh:mm aa", cal).toString();

        holder.Name.setText(pName);
        holder.Title.setText(pTitle);
        holder.Description.setText(pDescr);
        holder.timeStamp.setText(pTimeStamp);


        try {
            Picasso.get().load(pDp).placeholder(R.drawable.ic_graduation).into(holder.userDP);
        }
        catch (Exception e) {

        }

        if (pImage.equals("noImage")) {
            holder.postImage.setVisibility(View.GONE);

        }
        else {
            try {
                Picasso.get().load(pImage).into(holder.postImage);
            }
            catch (Exception e) {

            }
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    class myviewholder extends RecyclerView.ViewHolder {
        TextView Title, Description, timeStamp, Name;
        ImageView userDP, postImage;
        Button upvoteBtn, commentBtn;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.PostTitle);
            Description = itemView.findViewById(R.id.Description);
            timeStamp = itemView.findViewById(R.id.timeStamp);
            Name = itemView.findViewById(R.id.Username);
            userDP = itemView.findViewById(R.id.userDp);
            postImage = itemView.findViewById(R.id.postImage);
            upvoteBtn = itemView.findViewById(R.id.upvote_button);
            commentBtn = itemView.findViewById(R.id.comment_button);
        }
    }



}
