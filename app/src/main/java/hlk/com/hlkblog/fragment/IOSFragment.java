package hlk.com.hlkblog.fragment;

/**
 * ios
 * Created by user on 2017/1/5.
 */

public class IOSFragment extends AndroidFragment {

//    @Override
//    protected void initData() {
//        if (page == 1) {
//            resultses.clear();
//        }
//        mRefreshLayout.setRefreshing(true);
//        HttpUtils.get("http://gank.io/api/data/iOS/10/" + page, new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                isScrool = true;
//                closeRefresh();
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                isScrool = true;
//                gankBeen = JSON.parseObject(response, GankBean.class);
//                resultses.addAll(gankBeen.results);
//                closeRefresh();
//                adapter.upData(resultses);
//            }
//        });
//    }
}
