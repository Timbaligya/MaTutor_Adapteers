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
import com.example.matutor.data.selectPost_data;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

public class selectPost_adapter extends FirestoreRecyclerAdapter<selectPost_data, selectPost_adapter.selectedPostHolder> {

    public selectPost_adapter(@NonNull FirestoreRecyclerOptions<selectPost_data> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull selectedPostHolder holder, int position, @NonNull selectPost_data model) {
        holder.postTitle.setText(model.getPostTitle());
        holder.postDesc.setText(model.getPostDesc());
        holder.userFullname.setText(model.getUserFullname());
        holder.displayInterestTags(model.getInterestTags());
    }

    @Override
    public selectedPostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_created_posts, parent, false);
        return new selectedPostHolder(view);
    }

    public void deletePost(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class selectedPostHolder extends RecyclerView.ViewHolder {
        TextView postTitle;
        TextView postDesc;
        TextView userFullname;
        LinearLayout tagInterestFrame; //for tags


        public selectedPostHolder(@NonNull View itemView) {
            super(itemView);

            postTitle = itemView.findViewById(R.id.postingTitleTextView);
            postDesc = itemView.findViewById(R.id.postingDescTextView);
            userFullname = itemView.findViewById(R.id.userFullnameTextView);
            tagInterestFrame = itemView.findViewById(R.id.tagInterestFrame); // for tags
        }

        private void displayInterestTags(List<String> postTags) {
            // Clear existing buttons and layouts
            tagInterestFrame.removeAllViews();

            int maxButtonsPerRow = 3;

            for (int i = 0; i < Math.min(postTags.size(), maxButtonsPerRow); i++) {
                Button userInterestTag = createTagButton(postTags.get(i));
                tagInterestFrame.addView(userInterestTag);
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
