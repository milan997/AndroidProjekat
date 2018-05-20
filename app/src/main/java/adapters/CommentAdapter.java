package adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.alowishusad.androidprojekat.R;
import com.example.alowishusad.androidprojekat.ReadPostActivity;

import java.util.ArrayList;
import java.util.List;

import model.Comment;
import model.Post;

public class CommentAdapter extends ArrayAdapter<Comment> {

    private Context context;

    public CommentAdapter(Context context, List<Comment> comments) {
        super(context, 0, comments);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Comment comment = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);
        }
        // Lookup view for data population
        TextView tvName = convertView.findViewById(R.id.tvTitle);
        TextView tvHome = convertView.findViewById(R.id.tvDescription);
        TextView tvDate = convertView.findViewById(R.id.tvPostDate);
        TextView tvPopularity = convertView.findViewById(R.id.tvPopularity);
        TextView tvAuthor = convertView.findViewById(R.id.tvPosterName);
        // Populate the data into the template view using the data object
        tvName.setText(comment.getTitle());
        tvHome.setText(comment.getDescription());
        tvDate.setText(comment.getDate().toString().substring(3,16));
        tvAuthor.setText((comment.getAuthor() != null) ? comment.getAuthor().getUsername() : "noatuhor");

        String popularity = Integer.toString(comment.getPopularity());
        if (comment.getPopularity()> 0){
            tvPopularity.setTextColor(Color.GREEN);
        } else if(comment.getPopularity() < 0){
            tvPopularity.setTextColor(Color.RED);
        }
        tvPopularity.setText(popularity);


        // Return the completed view to render on screen
        return convertView;
    }
}
