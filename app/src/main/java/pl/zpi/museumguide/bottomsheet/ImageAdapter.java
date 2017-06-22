package pl.zpi.museumguide.bottomsheet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import pl.zpi.museumguide.R;
import pl.zpi.museumguide.data.domain.Author;
import pl.zpi.museumguide.data.domain.Work;

/**
 * Created by verat on 2017-05-05.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private Author author;

    public ImageAdapter(Context c)
    {
        mContext = c;
    }

    public void setAuthor(Author auth)
    {
        this.author = auth;
    }

    public int getCount() {
        return author == null ? 10 : author.getWorks().size();
    }

    public Object getItem(int position) {
        return author == null ? null : author.getWorks().get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View itemView = convertView;
        TextView text;
        ImageView image;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (itemView == null)
        {
            itemView = inflater.inflate(R.layout.gallery_item, null);
        }
        if(author != null)
        {
            Work work = author.getWorks().get(position);
            text = (TextView) itemView.findViewById(R.id.galleryText);
            image = (ImageView) itemView.findViewById(R.id.galleryPhoto);
            text.setText(work.getTitle());
            image.setImageResource(work.getIdDrawable());
        }
        return itemView;
    }
}



