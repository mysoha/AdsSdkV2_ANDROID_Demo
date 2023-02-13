package vcc.viv.ads.demo.synthetic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vcc.viv.ads.demo.databinding.ItemTagBinding;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {
    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    private final List<String> data;

    /* **********************************************************************
     * Area : Constructor
     ********************************************************************** */
    public TagAdapter() {
        this.data = new ArrayList<>();
    }

    /* **********************************************************************
     * Area : Override
     ********************************************************************** */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        ItemTagBinding binding = ItemTagBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.content.setText(data.get(position));
        holder.binding.remove.setOnClickListener(view -> {
            data.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        try {
            return data.size();
        } catch (Exception e) {
            return 0;
        }
    }

    /* **********************************************************************
     * Area : Function - Public
     ********************************************************************** */
    public List<String> getData() {
        return data;
    }

    public void addAdsId(String adsId) {
        if (adsId != null) {
            data.add(adsId);
            notifyDataSetChanged();
        }
    }

    public boolean isContain(String adsId) {
        try {
            return data.contains(adsId);
        } catch (Exception e) {
            return false;
        }
    }

    /* **********************************************************************
     * Area : Inner class
     ********************************************************************** */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemTagBinding binding;

        public ViewHolder(ItemTagBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}