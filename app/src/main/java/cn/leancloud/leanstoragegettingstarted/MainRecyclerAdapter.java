package cn.leancloud.leanstoragegettingstarted;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by BinaryHB on 11/24/15.
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder> {
  private Context mContext;
  private List<AVObject> mList;

  public MainRecyclerAdapter(List<AVObject> list, Context context) {
    this.mContext = context;
    this.mList = list;
  }

  @Override
  public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new MainViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_main, parent, false));
  }

  @Override
  public void onBindViewHolder(final MainViewHolder holder, final int position) {
    holder.mTitle.setText((CharSequence) mList.get(position).get("title"));
   // holder.mPrice.setText(mList.get(position).get("price") == null ? "￥" : "￥ " + mList.get(position).get("price"));
    holder.mPrice.setText(  new SimpleDateFormat().format(mList.get(position).getUpdatedAt()));
    holder.mName.setText(mList.get(position).getAVUser("owner") == null ? "" : mList.get(position).getAVUser("owner").getUsername());
    Picasso.with(mContext).load(mList.get(position).getAVFile("image") == null ? "www" : mList.get(position).getAVFile("image").getUrl()).transform(new RoundedTransformation(9, 0)).into(holder.mPicture);

    if(mList.get(position).get("description").equals(""))
    holder.mCheckBox.setChecked(false);
    else holder.mCheckBox.setChecked(true);
    holder.mCheckBox.setEnabled(false);

    holder.mItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(mContext, DetailActivity.class);
        intent.putExtra("goodsObjectId", mList.get(position).getObjectId());
        mContext.startActivity(intent);
      }
    });
    holder.mButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if(AVUser.getCurrentUser().equals(mList.get(position).getAVUser("owner")) )
        {

           mList.get(position).deleteInBackground();
           mList.remove(position);
          MainRecyclerAdapter.this.notifyDataSetChanged();
        }
        else {
          Toast.makeText(mContext,"只有本用户才可删除该项！",Toast.LENGTH_SHORT).show();
        }


      }
    });
  }

  @Override
  public int getItemCount() {
    return mList.size();
  }

  class MainViewHolder extends RecyclerView.ViewHolder {
    private TextView mName;
    private TextView mPrice;
    private TextView mTitle;
    private CardView mItem;
    private ImageView mPicture;
    private Button mButton;
    private CheckBox mCheckBox;

    public MainViewHolder(View itemView) {
      super(itemView);
      mName = (TextView) itemView.findViewById(R.id.name_item_main);
      mTitle = (TextView) itemView.findViewById(R.id.title_item_main);
      mPrice = (TextView) itemView.findViewById(R.id.price_item_main);
      mPicture = (ImageView) itemView.findViewById(R.id.picture_item_main);
      mItem = (CardView) itemView.findViewById(R.id.item_main);
      mButton=(Button) itemView.findViewById(R.id.item_delete);
      mCheckBox=(CheckBox)itemView.findViewById(R.id.name_item_checkBox);
    }
  }


}
