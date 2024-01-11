package com.example.matutor.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.matutor.R;
import com.example.matutor.data.allPost_data;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class posting_adapter extends FirestoreRecyclerAdapter<allPost_data, posting_adapter.postingHolder> {

    private OnItemClickListener mListener;

    public posting_adapter(@NonNull FirestoreRecyclerOptions<allPost_data> options) {
        super(options);
    }

    public interface OnItemClickListener {
        void onSeeMoreClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }


    @Override
    protected void onBindViewHolder(@NonNull postingHolder holder, int position, @NonNull allPost_data model) {
        Log.d("Firestore", "onBindViewHolder called at position: " + position);
        holder.postTitle.setText(model.getPostTitle());
        holder.postDescription.setText(model.getPostDescription());
        holder.userFirstname.setText(model.getUserFirstname());
        holder.userLastname.setText(model.getUserLastname());

        // Check if getPostTags() is not null before displaying tags
        if (model.getPostTags() != null) {
            Log.d("Firestore", "Post Tags size: " + model.getPostTags().size());
            holder.displayPostTags(model.getPostTags());
        } else {
            Log.d("Firestore", "Post Tags is null");
        }
        // Set click listener for the "See More" button
        holder.seeMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onSeeMoreClick(getSnapshots().getSnapshot(holder.getAdapterPosition()), holder.getAdapterPosition());
                }
            }
        });

    }


    // log the number of items in the adapter
    @Override
    public int getItemCount() {
        Log.d("Firestore", "getItemCount: " + super.getItemCount());
        return super.getItemCount();
    }

    @NonNull
    @Override
    public postingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new postingHolder(view);
    }

    // ViewHolder class
    class postingHolder extends RecyclerView.ViewHolder {
        TextView postTitle;
        TextView postDescription;
        TextView userFirstname;
        TextView userLastname;
        LinearLayout tagButtonsFrame;
        Button seeMoreButton;

        public postingHolder(@NonNull View itemView) {
            super(itemView);

            postTitle = itemView.findViewById(R.id.titlePosting);
            postDescription = itemView.findViewById(R.id.descPosting);
            userFirstname = itemView.findViewById(R.id.userFirstnamePosting);
            userLastname = itemView.findViewById(R.id.userLastnamePosting);
            tagButtonsFrame = itemView.findViewById(R.id.tagButtonsFrame);
            seeMoreButton = itemView.findViewById(R.id.seeMoreButtonPosting);
        }

        private void displayPostTags(List<String> postTags) {
            if (postTags != null) {
                tagButtonsFrame.removeAllViews();

                int maxButtonsPerRow = 3;

                for (int i = 0; i < Math.min(postTags.size(), maxButtonsPerRow); i++) {
                    Button userInterestTag = createTagButton(postTags.get(i));
                    tagButtonsFrame.addView(userInterestTag);
                }
            } else {
                Log.d("TAG", "postTags is null");
            }
        }


        private Button createTagButton(String tagName) {
            Button button = new Button(itemView.getContext());
            button.setText(tagName);
            return button;
        }
    }


}

