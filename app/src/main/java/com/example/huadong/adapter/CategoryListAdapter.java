package com.example.huadong.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huadong.R;
import com.example.huadong.bean.NewsMode;

import java.util.List;

/**
 * Created by 陈振聪 on 2017/3/30.
 */
public class CategoryListAdapter extends BaseAdapter {
    private List<NewsMode> list;
    private LayoutInflater inflater;
    private Context mContext ;

    public CategoryListAdapter(Context context,List<NewsMode> list) {
        this.mContext = context ;
        this.list = list;
        this.inflater = LayoutInflater.from(mContext);
    }

    public void setDateChange(List<NewsMode>list ) {
        this.list = list;
        this.notifyDataSetChanged();
    }

/*    public void loadMoreData(List<NewsMode> moreData) {
        int lastPosition = list.size();
        list.addAll(lastPosition, moreData);
        notifyItemRangeInserted(lastPosition, moreData.size());
    }*/

    @Override
    public int getCount() {
        return list.size() ;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewsMode newsMode = (NewsMode) getItem(position);
        View view ;
        ViewHolder holder ;
        if (convertView == null){
            view = inflater.inflate(R.layout.list_item , null) ;
            holder = new ViewHolder() ;
            holder.imageView = (ImageView) view.findViewById(R.id.list_image);
            holder.titleText = (TextView) view.findViewById(R.id.list_title);
            holder.timeText = (TextView) view.findViewById(R.id.list_time);
            view.setTag(holder);

        }else {
            view = convertView ;
            holder = (ViewHolder) view.getTag();
        }

        Glide.with(mContext).load(newsMode.getImgurl()).into(holder.imageView) ;
        holder.titleText.setText(newsMode.getTitle());
        holder.timeText.setText(newsMode.getTime());
        return view ;
    }

    class ViewHolder{
        ImageView imageView ;
        TextView titleText ;
        TextView timeText ;
    }
}
