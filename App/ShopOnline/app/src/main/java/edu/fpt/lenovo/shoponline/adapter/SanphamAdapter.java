package edu.fpt.lenovo.shoponline.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.fpt.lenovo.shoponline.R;
import edu.fpt.lenovo.shoponline.model.Sanpham;

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.ItemHolder> {
    Context context;

    public SanphamAdapter(Context context, ArrayList<Sanpham> arraysanpham) {
        this.context = context;
        this.arraysanpham = arraysanpham;
    }

    ArrayList<Sanpham> arraysanpham;

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_san_phammoinhat, null);
        ItemHolder itemHolder = new ItemHolder(view);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Sanpham sanpham = arraysanpham.get(position);
        holder.tvtensp.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvgiasp.setText("Giá : " + decimalFormat.format(sanpham.getGiasanpham()) + "Đ");
        Picasso.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.erro)
                .error(R.drawable.erro)
                .into(holder.imgsanpham);
    }

    @Override
    public int getItemCount() {

        return arraysanpham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView imgsanpham;
        public TextView tvtensp, tvgiasp;

        public ItemHolder(View itemView) {
            super(itemView);

            imgsanpham = itemView.findViewById(R.id.imgSp);
            tvtensp = itemView.findViewById(R.id.tvtensp);
            tvgiasp = itemView.findViewById(R.id.tvgiasp);

        }
    }
}
