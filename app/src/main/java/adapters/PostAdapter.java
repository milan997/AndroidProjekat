package adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alowishusad.androidprojekat.PostsActivity;
import com.example.alowishusad.androidprojekat.R;
import com.example.alowishusad.androidprojekat.ReadPostActivity;

import java.util.ArrayList;

import model.Post;

public class PostAdapter  extends ArrayAdapter<Post>{

    private Context context;

    public PostAdapter(Context context, ArrayList<Post> posts) {
        super(context, 0, posts);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Post post = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_post, parent, false);
        }
        // Lookup view for data population
        final TextView tvName = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvHome = (TextView) convertView.findViewById(R.id.tvDescription);
        // Populate the data into the template view using the data object
        tvName.setText(post.getTitle());
        tvHome.setText(post.getDescription());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ReadPostActivity.class);
                intent.putExtra("post", post);
                context.startActivity(intent);
            }
        });



        // Return the completed view to render on screen
        return convertView;
    }
}
