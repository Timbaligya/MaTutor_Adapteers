package com.example.matutor.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matutor.R;
import com.example.matutor.data.createdPost_data;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class createdPost_adapter extends FirestoreRecyclerAdapter<createdPost_data, createdPost_adapter.createdPostHolder> {
    private OnCloseButtonClickListener closeButtonClickListener;

    public createdPost_adapter(@NonNull FirestoreRecyclerOptions<createdPost_data> options, OnCloseButtonClickListener listener) {
        super(options);
        this.closeButtonClickListener = listener;
    }


    @Override
    protected void onBindViewHolder(@NonNull createdPostHolder holder, int position, @NonNull createdPost_data model) {
        holder.postTitle.setText(model.getPostTitle());
        holder.postDesc.setText(model.getPostDesc());
        holder.userFirstname.setText(model.getUserFirstname());
        holder.userLastname.setText(model.getUserLastname());
        holder.displayPostTags(model.getPostTags());

        holder.closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (closeButtonClickListener != null) {
                    closeButtonClickListener.onCloseButtonClick(getSnapshots().getSnapshot(holder.getAdapterPosition()), holder.getAdapterPosition());
                }
            }
        });
    }

    public interface OnCloseButtonClickListener {
        void onCloseButtonClick(DocumentSnapshot documentSnapshot, int position);
    }

    @Override
    public createdPostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_created_posts, parent, false);
        return new createdPostHolder(view);
    }

    public void deletePost(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class createdPostHolder extends RecyclerView.ViewHolder {
        TextView postTitle;
        TextView postDesc;
        TextView userFirstname;
        TextView userLastname;
        LinearLayout tagButtonsFrame; //for tags
        LinearLayout tagButtonsFrame2; // for tags
        Button closeButton;

        public createdPostHolder(@NonNull View itemView) {
            super(itemView);

            postTitle = itemView.findViewById(R.id.postTitleTextViewCP);
            postDesc = itemView.findViewById(R.id.postDescTextViewCP);
            userFirstname = itemView.findViewById(R.id.userFirstnameCP);
            userLastname = itemView.findViewById(R.id.userLastnameCP);
            tagButtonsFrame = itemView.findViewById(R.id.tagButtonsFrame); // for tags
            tagButtonsFrame2 = itemView.findViewById(R.id.tagButtonsFrame2); // for tags
            closeButton = itemView.findViewById(R.id.closeButton);
        }

        private void displayPostTags(List<String> postTags) {
            // Clear existing buttons and layouts
            tagButtonsFrame.removeAllViews();
            tagButtonsFrame2.removeAllViews();

            int maxButtonsPerRow = 3;

            for (int i = 0; i < Math.min(postTags.size(), maxButtonsPerRow); i++) {
                Button userInterestTag = createTagButton(postTags.get(i));
                tagButtonsFrame.addView(userInterestTag);
            }
            for (int i = maxButtonsPerRow; i < postTags.size(); i++) {
                Button userInterestTag = createTagButton(postTags.get(i));
                tagButtonsFrame2.addView(userInterestTag);
            }
        }

        //Create the buttons
        private Button createTagButton(String tagName) {
            Button button = new Button(itemView.getContext());
            button.setText(tagName);
            return button;
        }
    }
}
