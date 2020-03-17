package com.hengshitong.shualianzhifs.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hengshitong.shualianzhifs.R;
import com.hengshitong.shualianzhifs.entmy.Oil;

import java.util.List;

public class XuanAdapter extends BaseAdapter {
    private int selectedPosition=8000;
    private Context mContext;
    private   List<Oil.gustims> guns;;
    public XuanAdapter( List<Oil.gustims> guns, Context mContext) {
        this.guns = guns;
        this.mContext = mContext;
    }

    /**
     * 返回item的个数
     * @return
     */
    @Override
    public int getCount() {
        return guns.size();
    }

    /**
     * 返回每一个item对象
     * @param i
     * @return
     */
    @Override
    public Object getItem(int i) {
        return guns.get(i);
    }


    public void clearSelection(int position) {

        selectedPosition = position;

    }


    /**
     * 返回每一个item的id
     * @param i
     * @return
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * 暂时不做优化处理，后面会专门整理BaseAdapter的优化
     * @param i
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(mContext).inflate(R.layout.youqbai_items,viewGroup,false);
        TextView tv_phone = (TextView) view.findViewById(R.id.duos);
        Log.e("sdfsd",guns.size()+"=");
        tv_phone.setText(guns.get(i).getValue());
//        此处需要返回view 不能是view中某一个


        if(selectedPosition==i){
            tv_phone.setTextColor(Color.parseColor("#0D7DFB"));
            tv_phone.setBackgroundResource(R.drawable.shap_bai10_lv);
        }else{
            tv_phone.setTextColor(Color.parseColor("#333333"));
            tv_phone.setBackgroundResource(R.drawable.shap_bai5_baobai);      }


        return view;
    }
}

