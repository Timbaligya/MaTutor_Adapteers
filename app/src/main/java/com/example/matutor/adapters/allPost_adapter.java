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
import android.util.Log;

import java.util.List;

public class allPost_adapter extends FirestoreRecyclerAdapter<allPost_data, allPost_adapter.postingHolder> {
    private String userType;
    private OnItemClickListener mListener;

    public allPost_adapter(@NonNull FirestoreRecyclerOptions<allPost_data> options/*, String userType*/) {
        super(options);
       // this.userType = userType;
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
        holder.postDesc.setText(model.getPostDescription());
        holder.userFirstname.setText(model.getUserFirstname());
        holder.userLastname.setText(model.getUserLastname());

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

    @NonNull
    @Override
    public postingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new postingHolder(view);
    }

    // ViewHolder class
    class postingHolder extends RecyclerView.ViewHolder {
        TextView postTitle;
        TextView postDesc;
        TextView userFirstname;
        TextView userLastname;
        TextView userLookType;
        LinearLayout tagButtonFrame;
        LinearLayout tagButtonFrame2;
        Button seeMoreButton;

        public postingHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views
            postTitle = itemView.findViewById(R.id.titlePosting);
            postDesc = itemView.findViewById(R.id.descPosting);
            userFirstname = itemView.findViewById(R.id.userFirstnamePosting);
            userLastname = itemView.findViewById(R.id.userLastnamePosting);
            userLookType = itemView.findViewById(R.id.findTypePosting);
            tagButtonFrame = itemView.findViewById(R.id.tagFramePosting);
            tagButtonFrame2 = itemView.findViewById(R.id.tagFrame2Posting);
            seeMoreButton = itemView.findViewById(R.id.seeMoreButtonPosting);
        }

        private void displayPostTags(List<String> postTags) {
            tagButtonFrame.removeAllViews();
            tagButtonFrame2.removeAllViews();

            int maxButtonsPerRow = 3;

            for (int i = 0; i < Math.min(postTags.size(), maxButtonsPerRow); i++) {
                Button userInterestTag = createTagButton(postTags.get(i));
                tagButtonFrame.addView(userInterestTag);
            }

            for (int i = maxButtonsPerRow; i < postTags.size(); i++) {
                Button userInterestTag = createTagButton(postTags.get(i));
                tagButtonFrame2.addView(userInterestTag);
            }
        }

        private Button createTagButton(String tag) {
            Button button = new Button(itemView.getContext());
            button.setText(tag);

            return button;
        }
    }
}
