package com.example.to_do_listbyesya;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Task> tasks;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;

    public RecyclerViewAdapter(Context context, List<Task> list) {
        this.tasks = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.nameView.setText(task.getName());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameView;

        ViewHolder(View view) {
            super(view);
            nameView = view.findViewById(R.id.nameTextView);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public String getItem(int id) {
        return tasks.get(id).getName();
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public static class SpacesItemDecoration extends RecyclerView.ItemDecoration
    {
        public SpacesItemDecoration() {}

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
        {
            //добавить переданное кол-во пикселей отступа снизу
            outRect.top = 10;
            outRect.left = 20;
            outRect.right = 20;
            outRect.bottom = 10;
        }
    }

}
