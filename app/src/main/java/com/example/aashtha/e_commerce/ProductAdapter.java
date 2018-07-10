package com.example.aashtha.e_commerce;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

/**
 * Created by Belal on 10/18/2017.
 */
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private ArrayList<product> mExampleList;
    //this context we will use to inflate the layout
    private Context mCtx;
    //we are storing all the products in a list
    private List<product> productList;

    //getting the context and product list with constructor
    public ProductAdapter(Context mCtx, List<product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.r_layout, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        final product Product = productList.get(position);
        final String Noprod=mCtx.getResources().getString(R.string.nopro);
        //binding the data with the viewholder views
        holder.textViewTitle.setText(Product.getname());
        holder.textViewShortDesc.setText(Product.getdetails());
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(Product.getimgURL()));
        holder.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Product.getimgURL()==R.drawable.image2) {
                    Intent intent2 = new Intent(mCtx, Apple.class);
                    mCtx.startActivity(intent2);
                }
                else if(Product.getimgURL()==R.drawable.image3) {
                    Intent intent2 = new Intent(mCtx, OnePlus.class);
                    mCtx.startActivity(intent2);
                }
                else if(Product.getimgURL()== R.drawable.image6) {
                    Intent intent2 = new Intent(mCtx, Jbl.class);
                    mCtx.startActivity(intent2);
                }
                else if(Product.getimgURL()==R.drawable.image7) {
                    Intent intent2 = new Intent(mCtx, Sony.class);
                    mCtx.startActivity(intent2);
                }
                else if(Product.getimgURL()==R.drawable.image5) {
                    Intent intent2 = new Intent(mCtx, SkullC.class);
                    mCtx.startActivity(intent2);
                }
                else if(Product.getimgURL()==R.drawable.image4) {
                    Intent intent2 = new Intent(mCtx, SkullCe.class);
                    mCtx.startActivity(intent2);
                }
                else if(Product.getimgURL()==R.drawable.image8) {
                    Intent intent2 = new Intent(mCtx, Crossfire.class);
                    mCtx.startActivity(intent2);
                }
                else if(Product.getimgURL()==R.drawable.image9) {
                    Intent intent2 = new Intent(mCtx, HarryPotter.class);
                    mCtx.startActivity(intent2);
                }
                else if(Product.getimgURL()==R.drawable.image10) {
                    Intent intent2 = new Intent(mCtx, Fossils.class);
                    mCtx.startActivity(intent2);
                }
                else if(Product.getimgURL()==R.drawable.image11) {
                    Intent intent2 = new Intent(mCtx, Baggit.class);
                    mCtx.startActivity(intent2);
                }
                else  {
                    Toast.makeText(mCtx.getApplicationContext(),Noprod, Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    public void filterList(ArrayList<product> flist) {
        productList = flist;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return productList.size();

}

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc;
        ImageView imageView;
        RelativeLayout lay;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            imageView = itemView.findViewById(R.id.imageView);
            lay = itemView.findViewById(R.id.relative);
        }
    }
}
