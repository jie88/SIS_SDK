package com.cs.sis.sdk.base.ui;


import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cs.sis.sdk.R;
import com.cs.sis.sdk.base.adapter.SISQuickAdapter;
import com.cs.sis.sdk.base.adapter.ViewHolder;
import com.cs.sis.sdk.view.SISPullToRefreshView;

import java.util.ArrayList;
import java.util.List;

/**
 * author : ${CHENJIE} created at  2019-11-07 23:42 e_mail : chenjie_goodboy@163.com describle :
 */
public abstract class BaseListActivity<T> extends BaseActivity {

  private ListView listView;
  protected List<T> mData = new ArrayList<>();
  protected SISQuickAdapter<T> listAdapter;

  protected SISPullToRefreshView pullToRefreshView;
  protected boolean isRefresh = false;//是否可刷新

  protected int currentPage = 1;


  @Override
  protected int getLayoutResId() {
    return R.layout.activity_list_base;
  }

  @Override
  protected void findViews() {
    pullToRefreshView = $(R.id.pull_refresh);
    listView = $(R.id.listView);
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (isListEmpty(mData)) {
          return;
        }
        onListItemClick(mData.get(position), position);
      }
    });

    pullToRefreshView.setEnablePullLoadMoreDataStatus(true);
    pullToRefreshView.setOnFooterRefreshListener(new SISPullToRefreshView.OnFooterRefreshListener() {
      @Override
      public void onFooterRefresh(SISPullToRefreshView view) {
        if (isRefresh) {
          currentPage++;
          loadMoreData();
        } else {
          showToast("无更多数据");
        }
        pullToRefreshView.onFooterRefreshComplete();
      }
    });
    initListView();
    requestData();
  }


  /**
   * 加载下一页数据
   */
  protected void loadMoreData() {
  }

  private void initListView() {
    if ( getListItemLayout() <= 0) {
      return;
    }
    listAdapter = new SISQuickAdapter<T>(mContext, mData, getListItemLayout()) {
      @Override
      public void convert(ViewHolder helper, T item, int position) {
        convertView(helper, item, position);
      }
    };
    listView.setAdapter(listAdapter);
  }


  protected abstract int getListItemLayout();

  protected abstract void requestData();

  protected abstract void convertView(ViewHolder helper, T item, int position);

  protected abstract void onListItemClick(T item, int position);



  /**
   * 刷新数据
   */
  protected void notifyDataSetChanged() {
    if (listAdapter != null) {
      listAdapter.notifyDataSetChanged();
    }
  }

  /**
   * 设置列表分割线
   *
   * @param color
   */
  protected void setListDivider(int color) {
    if (color < 0) {
      return;
    }
    listView.setDivider(new ColorDrawable(color));
  }

  /**
   * 设置列表分割线高度
   *
   * @param height
   */
  protected void setListDividerHeight(int height) {
    if (height <= 0) {
      return;
    }
    listView.setDividerHeight(height);
  }

  /**
   * 判断集合为空
   *
   * @param list
   * @return
   */
  public  boolean isListEmpty(List list) {
    if (list == null || list.size() == 0) {
      return true;
    }
    return false;
  }

}
