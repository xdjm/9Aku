package com.xd.commander.aku.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.xd.commander.aku.R;
import com.xd.commander.aku.bean.Section;

import java.util.List;
public class AdapterItem_sort extends RecyclerView.Adapter<AdapterItem_sort.ContactViewHolder> {

    private List<String> mName;
    private List<String> mHan;
    private LayoutInflater mInflater;
    private AdapterItem_scroll mContactScrollerAdapter;

    public AdapterItem_sort(Context c, List<String> contacts, List<String> mHans,AdapterItem_scroll contactScrollerAdapter) {
        mName = contacts;
        mHan = mHans;
        mInflater = LayoutInflater.from(c);
        mContactScrollerAdapter = contactScrollerAdapter;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contact = mInflater.inflate(R.layout.item_sort, parent, false);
        return new ContactViewHolder(contact);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        String contact = mName.get(position);
        String han = mHan.get(position);
        holder.mName.setText(contact);
        holder.mHan.setText(han);
        Section s = mContactScrollerAdapter.fromItemIndex(position);
        if (s.getIndex() == position) {
            holder.title.setText(s.getTitle());
        } else {
            holder.title.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return mName.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {

        private TextView mName;
        private TextView title;
        private TextView mHan;
        public ContactViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title_index);
            mName = (TextView) itemView.findViewById(R.id.tv0);
            mHan = (TextView)itemView.findViewById(R.id.tv1);
        }
    }
}
