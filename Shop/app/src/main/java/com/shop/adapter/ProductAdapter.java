package com.shop.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shop.R;
import com.shop.dao.SanPhamDAO;
import com.shop.model.Product;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Product> list;

    private SanPhamDAO sanPhamDAO;

    public ProductAdapter(Context context, ArrayList<Product> list, SanPhamDAO sanPhamDAO) {
        this.context = context;
        this.list = list;
        this.sanPhamDAO = sanPhamDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_product, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(list.get(position).getTensp());
        NumberFormat formatter = new DecimalFormat("#,###");
        double myNumber = list.get(position).getGiaban();
        String formattedNumber = formatter.format(myNumber);
        holder.txtPrice.setText(formattedNumber + " VNĐ");
        holder.txtQuantity.setText("Sl: " + list.get(position).getSoluong());

        holder.txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogUpdate(list.get(holder.getAdapterPosition()));
            }
        });

        //Xoá sp
        holder.txtDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDelete(list.get(holder.getAdapterPosition()).getTensp(),
                        list.get(holder.getAdapterPosition()).getMasp());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtName, txtPrice, txtQuantity, txtEdit, txtDel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtEdit = itemView.findViewById(R.id.txtEdit);
            txtDel = itemView.findViewById(R.id.txtDel);
        }
    }

    public void showDialogUpdate(Product product){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update,null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText edtTenSP = view.findViewById(R.id.edtTenSP);
        EditText edtGiaSP = view.findViewById(R.id.edtGiaSP);
        EditText edtSLSP = view.findViewById(R.id.edtSLSP);
        Button btnSuaSP = view.findViewById(R.id.btnSuaSP);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        //đưa data
        edtTenSP.setText(product.getTensp());
        edtGiaSP.setText(String.valueOf(product.getGiaban()));
        edtSLSP.setText(String.valueOf(product.getSoluong()));

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btnSuaSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int masp = product.getMasp();
                String tensp = edtTenSP.getText().toString();
                String giasp = edtGiaSP.getText().toString();
                String slsp = edtSLSP.getText().toString();
                if(tensp.length() == 0 || giasp.length() == 0 || slsp.length() == 0){
                    Toast.makeText(context,"Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    Product productChinhSua = new Product(masp,tensp, Integer.parseInt(giasp), Integer.parseInt(slsp));
                    boolean check = sanPhamDAO.chinhSuaSP(productChinhSua);
                    if(check){
                        Toast.makeText(context,"Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
                        list.clear();
                        list = sanPhamDAO.getDS();
                        notifyDataSetChanged();
                        alertDialog.dismiss();
                    }else {
                        Toast.makeText(context,"Chỉnh sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void showDialogDelete(String tensp, int masp){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn muốn xoá sản phẩm \""+tensp+"\" không?");
        builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean check = sanPhamDAO.XoaSP(masp);
                if(check){
                    Toast.makeText(context,"Xoá thành công", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list = sanPhamDAO.getDS();
                    notifyDataSetChanged();
                }else {
                    Toast.makeText(context,"Xoá thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Huỷ",null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
