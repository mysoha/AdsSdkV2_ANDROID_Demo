package vcc.viv.ads.demo.synthetic;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vcc.viv.ads.demo.databinding.ItemBasicBinding;
import vcc.viv.ads.transport.VccAds;

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.ViewHolder> {
    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    private List<String> adsInfo;
    private VccAds vccAds;

    /* **********************************************************************
     * Area : Constructor
     ********************************************************************** */
    public FormAdapter() {
        this.adsInfo = new ArrayList<>();
        vccAds = VccAds.getInstance();
    }

    /* **********************************************************************
     * Area : Override
     ********************************************************************** */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemBasicBinding binding = ItemBasicBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String id = adsInfo.get(position);
        if (holder.binding.replace.getParent() != null) {
            holder.binding.replace.removeAllViews();
        }
        holder.binding.title.setText(id);
        holder.binding.title.setBackgroundColor(Color.parseColor("#88000000"));
        vccAds.adAdd(holder.binding.replace, FormActivity.TAG, FormActivity.requestId, id, position);
    }

    @Override
    public int getItemCount() {
        try {
            return adsInfo.size();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    /* **********************************************************************
     * Area : Function - Public
     ********************************************************************** */
    public void setData(List<String> data) {
        if (adsInfo != null) {
            adsInfo.clear();
            adsInfo.addAll(data);
        }
        notifyDataSetChanged();
    }

    /* **********************************************************************
     * Area : Inner class
     ********************************************************************** */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemBasicBinding binding;

        public ViewHolder(ItemBasicBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}