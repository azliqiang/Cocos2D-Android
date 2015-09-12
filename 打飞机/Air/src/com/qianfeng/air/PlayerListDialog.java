package com.qianfeng.air;


import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

import com.qianfeng.air.data.PlayerDao;


public class PlayerListDialog extends Dialog{
	
	private Context context;
	private ListView listView;
	private Button button;

	public PlayerListDialog(Context context) {
		super(context);
		this.context=context;
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setCancelable(false);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		View view=LayoutInflater.from(context).inflate(R.layout.player_list, null);
		LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		
		setContentView(view, params);
		
		PlayerDao dao = new PlayerDao(context);
		
		List<PlayerBean> list=dao.findAll();
		
		PlayerAdpater adpater=new PlayerAdpater(context,list);
		
		
		listView = (ListView) findViewById(R.id.listView_playerList);
		
		button = (Button) findViewById(R.id.btn_cancel_playerList);
		
		listView.setAdapter(adpater);
		
		
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				PlayerListDialog.this.cancel();
				
			}
		});
	}
}
