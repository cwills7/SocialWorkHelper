package com.wills.carl.socialworkhelper.history;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wills.carl.socialworkhelper.R;

/**
 * Created by Carl on 1/15/2018.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private static final String TAG = HistoryAdapter.class.getSimpleName();

    final private ListItemClickListener mOnClickListener;

    private static int viewHolderCount;

    private int mNumberItems;

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

    public HistoryAdapter(int numberItems, ListItemClickListener listener){
        mNumberItems = numberItems;
        mOnClickListener = listener;
        viewHolderCount = 0;
    }


    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.history_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttach = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttach);
        HistoryViewHolder viewHolder = new HistoryViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder (HistoryViewHolder holder, int position){
        holder.bind(position);
    }

    @Override
    public int getItemCount(){
        return mNumberItems;
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView historyItemDate;
        TextView historyItemSummary;

        public HistoryViewHolder(View itemView){
            super(itemView);

            historyItemDate = (TextView) itemView.findViewById(R.id.tv_history_item_date);
            historyItemSummary = (TextView) itemView.findViewById(R.id.tv_history_item_summary);

            itemView.setOnClickListener(this);
        }

        void bind(int listIndex){
            //TODO: Replace this bind with binding the data of the items to the textviews
            historyItemDate.setText(String.valueOf(listIndex));
        }


        @Override
        public void onClick(View v){
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
