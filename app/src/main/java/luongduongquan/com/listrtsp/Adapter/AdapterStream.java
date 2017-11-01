package luongduongquan.com.listrtsp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import luongduongquan.com.listrtsp.Model.StreamItem;
import luongduongquan.com.listrtsp.R;

/**
 * Created by luong.duong.quan on 11/1/2017.
 */

public class AdapterStream extends BaseAdapter {

	Context mContext;
	int mLayout;
	List<StreamItem> mListStreamItem;

	public AdapterStream(){

	}

	public AdapterStream (Context context, int layout, List<StreamItem> listStreamItem){
		mContext = context;
		mLayout = layout;
		mListStreamItem = listStreamItem;
	}

	public static class ViewHolder{
		// Để là "static" để có thể gọi được các biến trong cái class này mà ko cần getter/setter
		TextView tvName;

	}

	@Override
	public int getCount() {
		return mListStreamItem.size();
	}

	@Override
	public Object getItem(int i) {
		return mListStreamItem.get(i);
	}

	@Override
	public long getItemId(int i) {
		return mListStreamItem.get(i).getId();
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {

		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View viewRow = view;

		if(viewRow == null){
			// Nếu 1 dòng nào chưa được load bao giò thì sẽ nhảy vô đây.
			// Còn nếu đã load rồi thì nó sẽ nhảy xuống chỗ "ViewHolder viewHolder = (ViewHolder) viewRow.getTag();" ở duói.
			viewRow = inflater.inflate(mLayout,viewGroup,false);
			ViewHolder viewHolder = new ViewHolder();

			viewHolder.tvName  = (TextView) viewRow.findViewById(R.id.tvName);

			viewRow.setTag(viewHolder); // chỗ này để lưu trũ lại cái viewHolder để sau này ko cần load vẽ lại mà chỉ cần gọi lên xài.
		}

		ViewHolder viewHolder = (ViewHolder) viewRow.getTag(); // sau khi đã tạo holder ở trên thì nó sẽ lấy ra thôi.
		viewHolder.tvName.setText(mListStreamItem.get(i).getName());


		return viewRow;
	}
}
