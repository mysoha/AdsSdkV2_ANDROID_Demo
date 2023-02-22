package vcc.viv.ads.demo;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vcc.viv.ads.demo.databinding.ItemMainEndBinding;
import vcc.viv.ads.demo.databinding.ItemMainFirstBinding;
import vcc.viv.ads.demo.databinding.ItemMainMiddleBinding;
import vcc.viv.ads.demo.databinding.ItemMainNoneBinding;
import vcc.viv.ads.demo.databinding.ItemMainOneBinding;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    /* **********************************************************************
     * Area : Variable - Const
     ********************************************************************** */
    private final String TAG = MainAdapter.class.getSimpleName();

    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    private List<Data> data;
    private Callback callback;

    /* **********************************************************************
     * Area : Constructor
     ********************************************************************** */
    public MainAdapter(Callback callback) {
        super();
        this.callback = callback;

        createData();
    }

    /* **********************************************************************
     * Area : Override
     ********************************************************************** */
    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).viewType.ordinal();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewType type = ViewType.parse(viewType);
        switch (type) {
            case ONLY_ONE:
                return new OneViewHolder(ItemMainOneBinding.inflate(inflater, parent, false));
            case FIRST:
                return new FirstViewHolder(ItemMainFirstBinding.inflate(inflater, parent, false));
            case END:
                return new EndViewHolder(ItemMainEndBinding.inflate(inflater, parent, false));
            case MIDDLE:
                return new MiddleViewHolder(ItemMainMiddleBinding.inflate(inflater, parent, false));
            case NONE:
            default:
                return new NoneViewHolder(ItemMainNoneBinding.inflate(inflater, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Data item = data.get(position);
        holder.bindView(position, item);
        holder.itemView.setOnClickListener(v -> {
            if (callback != null) {
                callback.onItemClick(position, item);
            }
        });
    }

    /* **********************************************************************
     * Area : Function
     ********************************************************************** */
    private void createData() {
        data = new ArrayList<Data>() {{
            add(new Data(Type.BASIC, ViewType.NONE, -1));
            add(new Data(Type.BASIC, ViewType.FIRST, R.string.basic_banner));
            add(new Data(Type.BASIC, ViewType.MIDDLE, R.string.basic_popup));
            add(new Data(Type.BASIC, ViewType.MIDDLE, R.string.basic_in_page_default));
            add(new Data(Type.BASIC, ViewType.MIDDLE, R.string.basic_in_page_non));
            add(new Data(Type.BASIC, ViewType.MIDDLE, R.string.basic_welcome));
            add(new Data(Type.BASIC, ViewType.MIDDLE, R.string.basic_catfish));
            add(new Data(Type.BASIC, ViewType.END, R.string.basic_native_home));

            add(new Data(Type.MIX, ViewType.NONE, -1));
            add(new Data(Type.MIX, ViewType.FIRST, R.string.mix_scrollview));
            add(new Data(Type.MIX, ViewType.MIDDLE, R.string.mix_listview));
            add(new Data(Type.MIX, ViewType.MIDDLE, R.string.mix_recyclerview));
            add(new Data(Type.MIX, ViewType.MIDDLE, R.string.mix_recyclerviewhorizontal));
            add(new Data(Type.MIX, ViewType.END, R.string.mix_view_pager));

            add(new Data(Type.SYNTHETIC, ViewType.NONE, -1));
            add(new Data(Type.SYNTHETIC, ViewType.ONLY_ONE, R.string.synthetic_form));

            add(new Data(Type.CUSTOM_BROWSER, ViewType.NONE, -1));
            add(new Data(Type.CUSTOM_BROWSER, ViewType.FIRST, R.string.browser_form));
            add(new Data(Type.CUSTOM_BROWSER, ViewType.MIDDLE, R.string.browser_game));
            add(new Data(Type.CUSTOM_BROWSER, ViewType.MIDDLE, R.string.browser_ecommerce));
            add(new Data(Type.CUSTOM_BROWSER, ViewType.END, R.string.browser_livestream));

            add(new Data(Type.WEB_FORMAT, ViewType.NONE, -1));
            add(new Data(Type.WEB_FORMAT, ViewType.FIRST, R.string.web_top, "top"));
            add(new Data(Type.WEB_FORMAT, ViewType.MIDDLE, R.string.web_billboard, "billboard"));
            add(new Data(Type.WEB_FORMAT, ViewType.MIDDLE, R.string.web_medium, "medium"));
            add(new Data(Type.WEB_FORMAT, ViewType.MIDDLE, R.string.web_adx_native, "adxNative"));
            add(new Data(Type.WEB_FORMAT, ViewType.MIDDLE, R.string.web_adx_sponsor, "adxSponsor180x200"));
            add(new Data(Type.WEB_FORMAT, ViewType.MIDDLE, R.string.web_poster, "iposter"));
            add(new Data(Type.WEB_FORMAT, ViewType.MIDDLE, R.string.web_in_page, "inpage"));
            add(new Data(Type.WEB_FORMAT, ViewType.MIDDLE, R.string.web_lotus_shop_chat, "lotusshopchat"));
            add(new Data(Type.WEB_FORMAT, ViewType.MIDDLE, R.string.web_native, "native"));
            add(new Data(Type.WEB_FORMAT, ViewType.MIDDLE, R.string.web_native_video, "nativeVideo"));
            add(new Data(Type.WEB_FORMAT, ViewType.MIDDLE, R.string.web_native_livestream, "playerlive"));
            add(new Data(Type.WEB_FORMAT, ViewType.MIDDLE, R.string.web_deeplink, "deeplink"));
            add(new Data(Type.WEB_FORMAT, ViewType.MIDDLE, R.string.web_no_deeplink, "nodeeplink"));
            add(new Data(Type.WEB_FORMAT, ViewType.MIDDLE, R.string.web_true_view, "trueview"));
            add(new Data(Type.WEB_FORMAT, ViewType.MIDDLE, R.string.web_window_open, "windowopen"));
            add(new Data(Type.WEB_FORMAT, ViewType.MIDDLE, R.string.web_ad_rotate, "adsrotate"));
            add(new Data(Type.WEB_FORMAT, ViewType.MIDDLE, R.string.web_inject_javascript));
            add(new Data(Type.WEB_FORMAT, ViewType.MIDDLE, R.string.web_open_game));
            add(new Data(Type.WEB_FORMAT, ViewType.MIDDLE, R.string.web_welcome));
            add(new Data(Type.WEB_FORMAT, ViewType.MIDDLE, R.string.web_link_game, "linkgame"));
            add(new Data(Type.WEB_FORMAT, ViewType.MIDDLE, R.string.browser_livestream, "custombrowser_livestream"));
            add(new Data(Type.WEB_FORMAT, ViewType.MIDDLE, R.string.browser_form, "custombrowser_signup"));
            add(new Data(Type.WEB_FORMAT, ViewType.MIDDLE, R.string.browser_game, "custombrowser_game"));
            add(new Data(Type.WEB_FORMAT, ViewType.MIDDLE, R.string.browser_ecommerce, "custombrowser_tmdt"));
            add(new Data(Type.WEB_FORMAT, ViewType.MIDDLE, R.string.browser_execjs, "custombrowser_execjs"));
            add(new Data(Type.WEB_FORMAT, ViewType.END, R.string.web_web_game, "webgame"));

            add(new Data(Type.NONE, ViewType.NONE, -1));
        }};
    }

    /* **********************************************************************
     * Area : Inner Class - Data
     ********************************************************************** */
    public interface Callback {
        void onItemClick(int position, Data data);
    }

    public enum Type {
        NONE, BASIC, MIX, SYNTHETIC, ANDROID, WEB_FORMAT, CUSTOM_BROWSER
    }

    public enum ViewType {
        NONE, ONLY_ONE, FIRST, MIDDLE, END;

        public static ViewType parse(int index) {
            ViewType[] types = values();
            for (int i = 0; i < types.length; i++) {
                ViewType item = types[i];
                if (index == item.ordinal()) {
                    return item;
                }
            }
            return MIDDLE;
        }
    }

    public static class Data {
        public Type type;
        public ViewType viewType;
        public int info;
        public String extra;

        public Data(Type type, ViewType viewType, int info) {
            this(type, viewType, info, "");
        }

        public Data(Type type, ViewType viewType, int info, String extra) {
            this.type = type;
            this.viewType = viewType;
            this.info = info;
            this.extra = extra;
        }
    }

    /* **********************************************************************
     * Area : Inner Class - View Holder
     ********************************************************************** */
    public class OneViewHolder extends ViewHolder {
        private ItemMainOneBinding binding;

        public OneViewHolder(ItemMainOneBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void bindView(int index, Data data) {
            Resources resources = binding.getRoot().getResources();
            binding.title.setText(resources.getString(data.info));
        }
    }

    public class FirstViewHolder extends ViewHolder {
        private ItemMainFirstBinding binding;

        public FirstViewHolder(ItemMainFirstBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void bindView(int index, Data data) {
            Resources resources = binding.getRoot().getResources();
            binding.title.setText(resources.getString(data.info));
        }
    }

    public class MiddleViewHolder extends ViewHolder {
        private ItemMainMiddleBinding binding;

        public MiddleViewHolder(ItemMainMiddleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void bindView(int index, Data data) {
            Resources resources = binding.getRoot().getResources();
            binding.title.setText(resources.getString(data.info));
        }
    }

    public class EndViewHolder extends ViewHolder {
        private ItemMainEndBinding binding;

        public EndViewHolder(ItemMainEndBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void bindView(int index, Data data) {
            Resources resources = binding.getRoot().getResources();
            binding.title.setText(resources.getString(data.info));
        }
    }

    public class NoneViewHolder extends ViewHolder {
        private ItemMainNoneBinding binding;

        public NoneViewHolder(ItemMainNoneBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void bindView(int index, Data data) {
            if (data.type == Type.NONE) {
                binding.title.setVisibility(View.GONE);
                binding.title.setText("");
            } else {
                binding.title.setVisibility(View.VISIBLE);
                binding.title.setText(data.type.name());
            }
        }
    }

    public abstract class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }

        public abstract void bindView(int index, Data data);
    }
}