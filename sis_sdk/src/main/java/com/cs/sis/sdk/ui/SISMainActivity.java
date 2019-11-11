package com.cs.sis.sdk.ui;


import com.cs.sis.sdk.R;
import com.cs.sis.sdk.base.adapter.ViewHolder;
import com.cs.sis.sdk.base.ui.BaseListActivity;
import com.cs.sis.sdk.ui.view.MainListHeadView;

/**
 * author : ${CHENJIE} created at  2019-11-10 23:36 e_mail : chenjie_goodboy@163.com describle :
 */
public class SISMainActivity extends BaseListActivity<String> {

 private MainListHeadView mainListHeadView;

  @Override
  protected void findViews() {

    listView = $(R.id.listView);
    mainListHeadView=new MainListHeadView(mContext);
    listView.addHeaderView(mainListHeadView);
    super.findViews();
    initTop("获取权限","标题","设置");



  }

//  @Override
//  protected int getLayoutResId() {
//    return R.layout.activity_list_main;
//  }

  @Override
  protected int getListItemLayout() {
    return R.layout.sis_item_view_main_activity;
  }

  @Override
  protected void requestData() {

    for(int i=0;i<20;i++) {
      mData.add("Main "+i);
    }
    notifyDataSetChanged();
  }

  @Override
  protected void convertView(ViewHolder helper, String item, int position) {
    helper.setText(R.id.set_txt_left,item);
//    helper.setText(R.id.set_txt_right,item.getRightStr());
  }

  @Override
  protected void onListItemClick(String item, int position) {

  }
}
