package luongduongquan.com.listrtsp;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by luong.duong.quan on 11/1/2017.
 */

public class CustomVideo extends VideoView {
	public CustomVideo(Context context) {
		super(context);
	}

	public CustomVideo(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomVideo(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	/**
	 * Resize video view by using SurfaceHolder.setFixedSize(...). See {@link android.view.SurfaceHolder#setFixedSize}
	 * @param width
	 * @param height
	 */
	public void setFixedVideoSize(int width, int height)
	{
		getHolder().setFixedSize(width, height);
	}
}
