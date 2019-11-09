package com.cs.sis.sdk.ui;


import android.content.DialogInterface;
import android.widget.Toast;

import com.cs.sis.sdk.R;
import com.cs.sis.sdk.base.adapter.ViewHolder;
import com.cs.sis.sdk.base.ui.BaseListActivity;
import com.cs.sis.sdk.bean.SetBean;

/**
 * 设置
 */
public class SISSetActivity extends BaseListActivity<SetBean> {


   private String[] left={"仪器校零","断开链接","设置固定积分时间"};
   private String[] right={"","","200.0"};



  @Override
  protected int getListItemLayout() {
    return R.layout.sis_item_view_set_activity;
  }

  @Override
  protected void findViews() {
    initLeftTop("返回","设置");
    super.findViews();
  }

  @Override
  protected void requestData() {

    for (int i=0;i<left.length;i++){
      SetBean bean=new SetBean(left[i],right[i]);
      mData.add(bean);
    }
    notifyDataSetChanged();
  }

  @Override
  protected void convertView(ViewHolder helper, SetBean item, int position) {

    helper.setText(R.id.set_txt_left,item.getLeftStr());
    helper.setText(R.id.set_txt_right,item.getRightStr());
  }

  @Override
  protected void onListItemClick(SetBean item, int position) {

  }
}
