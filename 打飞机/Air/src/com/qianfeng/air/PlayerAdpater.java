package com.qianfeng.air;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class PlayerAdpater extends BaseAdapter{

	private Context context;
	private List<PlayerBean> list;
	
	public PlayerAdpater(Context context,List<PlayerBean> list){
		this.context=context;
		this.list=list;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView==null){
			convertView=LayoutInflater.from(context).inflate(R.layout.player_item,null);
		}
		
		TextView tv_rank=(TextView) convertView.findViewById(R.id.tv_ranking_item);
		TextView tv_name=(TextView) convertView.findViewById(R.id.tv_name_item);
		TextView tv_score=(TextView) convertView.findViewById(R.id.tv_score_item);
		
		tv_name.setText(list.get(position).getName());
		tv_score.setText(list.get(position).getScore()+"");
		
		tv_rank.setVisibility(View.INVISIBLE);
		
		switch(position){
		
		case 0:
			tv_rank.setText("1st");
			tv_rank.setVisibility(View.VISIBLE);
			break;
		case 1:
			tv_rank.setText("2nd");
			tv_rank.setVisibility(View.VISIBLE);
			break;
		case 2:
			tv_rank.setText("3rd");
			tv_rank.setVisibility(View.VISIBLE);
			break;
		}
		
		
		return convertView;
	}

}
