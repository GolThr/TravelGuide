package com.golthr.travelguide;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2019/7/24 0024.
 */
//适配器
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<Article> articles;
    private int resourceId;
    private Context mcontext;
    public MyAdapter(){}
    public MyAdapter(Context context, List<Article> articles) {
        this.mcontext=context;
        this.articles = articles;
    }

    //创建viewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView imageView;
        public TextView word;
        public TextView time;
        public LinearLayout ll_card_item;


        public ViewHolder( View v ) {
            super(v);
            imageView=(ImageView) v.findViewById(R.id.image);
            name = (TextView) v.findViewById(R.id.name);
            word = (TextView) v.findViewById(R.id.word);
            time = (TextView) v.findViewById(R.id.time);
            ll_card_item = (LinearLayout) v.findViewById(R.id.ll_card_item);
        }
    }

    //创建ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (mcontext==null){
            mcontext=viewGroup.getContext();
        }
        View v = LayoutInflater.from(mcontext).inflate(R.layout.recyclerview_item, viewGroup, false);
        final ViewHolder holder=new ViewHolder(v);
        holder.ll_card_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Article article = articles.get(position);
                Intent intent=new Intent(mcontext,DetailActivity.class);
                intent.putExtra(DetailActivity.ARTICLE_ID, article.getArticle_id());
                mcontext.startActivity(intent);

            }
        });
        return holder;
    }

    //绑定数据
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Article article = articles.get(position);
        viewHolder.imageView.setImageResource(article.getImageId());
        viewHolder.name.setText(article.getName()+"  ");
        viewHolder.word.setText(article.getName());
        viewHolder.time.setText(article.getTime());

    }

    //数据总数
    @Override
    public int getItemCount() {
        return articles.size();
    }
}
