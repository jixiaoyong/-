/**
* 在指定view上方展示PopupWindow
* 参考：https://www.jianshu.com/p/12f53c931eda
*/
fun showPop(view: View, msg: String) {
        var pop: PopupWindow? = null
        val popView = layoutInflater.inflate(R.layout.layout_pop, null)
        popView.findViewById<TextView>(R.id.text)?.text = msg
        pop = PopupWindow(popView)
        pop.width = ViewGroup.LayoutParams.WRAP_CONTENT
        pop.height = ViewGroup.LayoutParams.WRAP_CONTENT
        pop.contentView?.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)

        if (pop.isShowing) {
            pop.dismiss()
        } else {
            val popupWidth = pop.contentView.measuredWidth
            val popupHeight = pop.contentView.measuredHeight

            Log.d("TAG", "popupWidth is $popupWidth,popupHeight is $popupHeight")

            val location = IntArray(2)
            Log.d("TAG", "location[0]:${location.contentToString()};location[0] + view.width / 2 - popupWidth / 2 ==  ${location[0] + view.width / 2 - popupWidth / 2}")
            view.getLocationInWindow(location)
            pop.showAtLocation(view, Gravity.TOP or Gravity.LEFT, location[0] + view.width / 2 - popupWidth / 2,
                    location[1] - popupHeight)
        }
    }
    
    /**
    * layout_pop.xml
    */
    
    <?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:orientation="vertical">

    <TextView
        android:id="@+id/text"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:background="@drawable/toast_pop_bg"
        android:maxWidth="600dp"
        android:ellipsize="end"
        android:maxLines="1"
        android:padding="20dp"
        android:gravity="center"
        android:minWidth="150dp"
        android:textColor="#fff"
        android:textSize="48px" />

    <ImageView
        android:layout_width="16dp"
        android:layout_height="20dp"
        android:layout_marginTop="-10dp"
        android:layout_gravity="center"
        android:background="@drawable/ic_arrow_down" />

</LinearLayout>
