package com.example.gsthub.Forum;


import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsthub.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.myviewholder> {

    Context context;
    List<Post> postList;
    String myUid;

    private DatabaseReference upvotesRef;
    private DatabaseReference postsRef;

    boolean mProcessUpvote = false;

    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
        myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        upvotesRef = FirebaseDatabase.getInstance().getReference().child("Upvotes");
        postsRef = FirebaseDatabase.getInstance().getReference().child("Posts");
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_post, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, final int position) {
        String uid = postList.get(position).getUid();
        String pEmail = postList.get(position).getpEmail();
        String pName = postList.get(position).getpName();
        String pTitle = postList.get(position).getpTitle();
        String pDescr = postList.get(position).getpDescr();
        String pImage = postList.get(position).getpImage();
        String pTimeStamp = postList.get(position).getpTime();
        String pId = postList.get(position).getpId();
        String pDp = postList.get(position).getpDp();
        String pLikes = postList.get(position).getpLikes();

        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(Long.parseLong(pTimeStamp));
        String pTime = DateFormat.format("dd/mm/yyyy hh:mm aa", cal).toString();

        holder.Name.setText(pName);
        holder.Title.setText(pTitle);
        holder.Description.setText(pDescr);
        holder.timeStamp.setText(pTime);
        holder.likes.setText(pLikes);

        setLikes(holder,pId);


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

        holder.upvoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final int pLikes = Integer.parseInt(postList.get(position).getpLikes());
               mProcessUpvote = true;

               final String postIde = postList.get(position).getpId();
               upvotesRef.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       if (mProcessUpvote) {
                           if (snapshot.child(postIde).hasChild(myUid)) {
                               postsRef.child(postIde).child("pLikes").setValue(""+(pLikes-1));
                               upvotesRef.child(postIde).child(myUid).removeValue();
                               mProcessUpvote = false;
                           }
                           else {
                               postsRef.child(postIde).child("pLikes").setValue(""+(pLikes+1));
                               upvotesRef.child(postIde).child(myUid).setValue("Upvoted");
                               mProcessUpvote = false;
                           }
                       }

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });
            }
        });
        holder.commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Comment", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setLikes(final myviewholder Holder, final String postKey) {
        upvotesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(postKey).hasChild(myUid)) {
                    Holder.upvoteBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_upvoted, 0, 0, 0);
                    Holder.upvoteBtn.setText("Upvoted");
                }
                else {
                    Holder.upvoteBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_baseline_arrow_upward_24, 0, 0, 0);
                    Holder.upvoteBtn.setText("Upvote");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    class myviewholder extends RecyclerView.ViewHolder {
        TextView Title, Description, timeStamp, Name, likes;
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
