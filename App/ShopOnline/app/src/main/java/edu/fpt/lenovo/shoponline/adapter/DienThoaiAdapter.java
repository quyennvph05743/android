package edu.fpt.lenovo.shoponline.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.fpt.lenovo.shoponline.R;
import edu.fpt.lenovo.shoponline.model.Sanpham;

public class DienThoaiAdapter extends BaseAdapter {
    Context context;

    public DienThoaiAdapter(Context context, ArrayList<Sanpham> arrayDT) {
        this.context = context;
        this.arrayDT = arrayDT;
    }

    ArrayList<Sanpham> arrayDT;

    @Override
    public int getCount() {
        return arrayDT.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayDT.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class ViewHolder {
        private ImageView ImgDT;
        private TextView TvDT;
        private TextView TvgiaDT;
        private TextView TvmotaDT;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null ;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_dt, null);
            viewHolder.TvDT = convertView.findViewById(R.id.tvtenDT);
            viewHolder.TvgiaDT = convertView.findViewById(R.id.tvgiaDT);
            viewHolder.TvmotaDT = convertView.findViewById(R.id.tvmotaDT);
            viewHolder.ImgDT = convertView.findViewById(R.id.imgDT);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Sanpham sanpham = (Sanpham) getItem(position);
        viewHolder.TvDT.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.TvgiaDT.setText("Giá : " + decimalFormat.format(sanpham.getGiasanpham()) + "Đ");
        viewHolder.TvmotaDT.setMaxLines(2);
        viewHolder.TvmotaDT.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.TvmotaDT.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.erro)
                .error(R.drawable.erro)
                .into(viewHolder.ImgDT);
        return null;
    }
}
